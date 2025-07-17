package com.example.chatbot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {
    RecyclerView airc;
    EditText aiet;
    ImageButton aibt;
    List<Message> messagesList;

    MessageAdapter adapter;

    String urlStr = "http://192.168.1.10:11434/api/generate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initItem();
        initListener();


    }

    private void initListener() {

        aibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = aiet.getText().toString();
                addToChat(question, Message.Sent_By_User);
                aiet.setText("");
                addToChat(Message.Typing, Message.Typing);
                connectModel(question);

            }
        });

    }

    private void addToChat(String message, String sendBy) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.trim().isEmpty()) return;
                messagesList.add(new Message(message, sendBy));
                adapter.notifyDataSetChanged();
                airc.smoothScrollToPosition(adapter.getItemCount());

            }
        });

    }

    private void connectModel(String question) {
        new Thread(()-> {

            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                String json = "{ \"model\": \"deepseek-r1:7b\", \"prompt\": \"" + question + "\" }";


                OutputStream os = conn.getOutputStream();
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {

                    Matcher matcher = java.util.regex.Pattern.compile("\"response\":\"(.*?)\"").matcher(line);

                    while(matcher.find()){
                            String chunk = matcher.group(1)
                                    .replace("\\n", "\n")
                                    .replace("\\\"", "\"")
                                    .replace("\\u003c", "<")
                                    .replace("\\u003e", ">");
                            response.append(chunk);
                        }
                    }
                br.close();

                addResponse(response.toString());
                System.out.println("✅ Final cleaned response: " + response.toString());

            } catch (IOException e) {
                e.printStackTrace();
                addResponse("⚠️ Error: " + e.getMessage());

            }
        }).start();

    }

    void addResponse(String  response){

        response = response
                .replaceAll("(?i)<think>", "")  // remove <think> or <THINK>
                .replaceAll("(?i)</think>", "")
                .trim();

        runOnUiThread(()->{
            if(!messagesList.isEmpty()&& messagesList.get(messagesList.size()-1).getSendby().equals(Message.Typing)){
                messagesList.remove(messagesList.size()-1);
                adapter.notifyItemRemoved(messagesList.size());
            }
        });

        System.out.println("Model response: " + response);
        addToChat(response, Message.Sent_By_Model);


    }

    private void initItem() {

        airc=findViewById(R.id.rv_ai);
        aiet=findViewById(R.id.et_ai);
        aibt=findViewById(R.id.sent);
        messagesList = new ArrayList<>();
        adapter = new MessageAdapter(messagesList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        airc.setAdapter(adapter);
        llm.setStackFromEnd(true);
        airc.setLayoutManager(llm);


    }
}