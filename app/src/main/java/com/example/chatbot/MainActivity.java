//package com.example.chatbot;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//
//public class MainActivity extends AppCompatActivity {
//    RecyclerView airc;
//    EditText aiet;
//    ImageButton aibt;
//    List<Message> messagesList;
//
//    MessageAdapter adapter;
//
//    String urlStr = "http://lynnlynnhtuns-mac.local:5000/ask";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initItem();
//        initListener();
//
//
//
//
//    }
//
//    private void initListener() {
//
//        aibt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String question = aiet.getText().toString();
//                addToChat(question, Message.Sent_By_User);
//                aiet.setText("");
//                addToChat(Message.Typing, Message.Typing);
//                connectModel(question);
//
//            }
//        });
//
//    }
//
//    private void addToChat(String message, String sendBy) {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (message.trim().isEmpty()) return;
//                messagesList.add(new Message(message, sendBy));
//                adapter.notifyDataSetChanged();
//                airc.smoothScrollToPosition(adapter.getItemCount());
//
//            }
//        });
//
//    }
//
////    private void connectModel(String question) {
////        new Thread(()-> {
////
////            try {
////                URL url = new URL(urlStr);
////                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////                conn.setRequestMethod("POST");
////                conn.setRequestProperty("Content-Type", "application/json");
////                conn.setDoOutput(true);
////                conn.setDoInput(true);
////                conn.setRequestProperty("Content-Type", "application/json");
////
////
////                String json = "{ \"question\": \"" + question + "\" }";
////
////
////                OutputStream os = conn.getOutputStream();
////                byte[] input = json.getBytes("utf-8");
////                os.write(input, 0, input.length);
////
////                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////                StringBuilder response = new StringBuilder();
////                String line;
////                while ((line = br.readLine()) != null) {
////
////                    Matcher matcher = java.util.regex.Pattern.compile("\"response\":\"(.*?)\"").matcher(line);
////
////                    while(matcher.find()){
////                            String chunk = matcher.group(1)
////                                    .replace("\\n", "\n")
////                                    .replace("\\\"", "\"")
////                                    .replace("\\u003c", "<")
////                                    .replace("\\u003e", ">");
////                            response.append(chunk);
////                        }
////                    }
////                br.close();
////
////                addResponse(response.toString());
////                System.out.println("✅ Final cleaned response: " + response.toString());
////
////            } catch (IOException e) {
////                e.printStackTrace();
////                addResponse("⚠️ Error: " + e.getMessage());
////
////            }
////        }).start();
////
////    }
//
//    private void connectModel(String question) {
//        new Thread(() -> {
//            try {
//                URL url = new URL(urlStr);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setDoOutput(true);
//
//                String json = "{ \"model\": \"gemma:2b\", \"prompt\": \"" + question + "\" }";
//
//                OutputStream os = conn.getOutputStream();
//                os.write(json.getBytes("utf-8"));
//                os.flush();
//                os.close();
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//                StringBuilder response = new StringBuilder();
//                String line;
//
//                while ((line = br.readLine()) != null) {
//                    // Each line is a complete JSON object like: {"response":"Hi","done":false}
//                    try {
//                        JSONObject jsonObj = new JSONObject(line);
//                        if (jsonObj.has("response")) {
//                            String chunk = jsonObj.getString("response");
//                            response.append(chunk);
//                            Log.d("DEBUG", "Raw line: " + line);
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println("🔎 HTTP Response Code: " + conn.getResponseCode());
//
//
//                br.close();
//
//                addResponse(response.toString());
//                System.out.println("✅ Final cleaned response: " + response.toString());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                addResponse("⚠️ Error: " + e.getMessage());
//            }
//            Log.d("DEBUG", "Sending question: " + question);
//            Log.d("DEBUG", "Posting to: " + urlStr);
//
//        }).start();
//    }
//
//
//    void addResponse(String  response){
//
//        response = response
//                .replaceAll("(?i)<think>", "")  // remove <think> or <THINK>
//                .replaceAll("(?i)</think>", "")
//                .trim();
//
//        runOnUiThread(()->{
//            if(!messagesList.isEmpty()&& messagesList.get(messagesList.size()-1).getSendby().equals(Message.Typing)){
//                messagesList.remove(messagesList.size()-1);
//                adapter.notifyItemRemoved(messagesList.size());
//            }
//        });
//
//        Log.d("DEBUG", "Response collected: " + response);
//        addToChat(response, Message.Sent_By_Model);
//
//
//    }
//
//    private void initItem() {
//
//        airc=findViewById(R.id.rv_ai);
//        aiet=findViewById(R.id.et_ai);
//        aibt=findViewById(R.id.sent);
//        messagesList = new ArrayList<>();
//        adapter = new MessageAdapter(messagesList);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        airc.setAdapter(adapter);
//        llm.setStackFromEnd(true);
//        airc.setLayoutManager(llm);
//
//
//    }
//}

package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.Adapter.MessageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView airc;
    EditText aiet;
    ImageButton aibt;
    List<Message> messagesList;

    MessageAdapter adapter;

    // We'll set this dynamically after discovery
    String urlStr = "http://127.0.0.1:5000/ask"; // Default fallback URL

    // UDP discovery constants - must match your Flask server
    private static final int DISCOVERY_PORT = 12345;
    private static final String DISCOVERY_MESSAGE_REQUEST = "CHATBOT_SERVER_DISCOVERY_REQUEST";
    private static final String DISCOVERY_MESSAGE_RESPONSE_PREFIX = "CHATBOT_SERVER_IP:";
    private static final int DISCOVERY_TIMEOUT_MS = 5000; // 5 seconds timeout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initItem();
        initListener();

        // === NEW: Start the server discovery process on app start ===
        discoverServerIp();
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);// Opens notification access settings


    }

    private void initListener() {
        aibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = aiet.getText().toString();
                if (question.trim().isEmpty()) {
                    return;
                }
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

    // === NEW: The UDP Discovery method ===
    private void discoverServerIp() {
        new Thread(() -> {
            try {
                // Use DatagramSocket for UDP communication
                DatagramSocket socket = new DatagramSocket();
                socket.setBroadcast(true);

                // Create the broadcast packet with the request message
                byte[] sendData = DISCOVERY_MESSAGE_REQUEST.getBytes();
                // Broadcast to all devices on the network
                InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcastAddress, DISCOVERY_PORT);

                // Send the broadcast
                socket.send(sendPacket);
                Log.d("UDP_DISCOVERY", "Broadcasted discovery message.");

                // Wait for a response from the server
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.setSoTimeout(DISCOVERY_TIMEOUT_MS); // Set a timeout to prevent an infinite hang

                socket.receive(receivePacket);
                String response = new String(receivePacket.getData()).trim();

                // Check if the response contains our expected prefix
                if (response.startsWith(DISCOVERY_MESSAGE_RESPONSE_PREFIX)) {
                    String discoveredIp = response.substring(DISCOVERY_MESSAGE_RESPONSE_PREFIX.length());
                    urlStr = "http://" + discoveredIp + ":5000/ask";
                    Log.d("UDP_DISCOVERY", "Server found at: " + urlStr);

                    // Optional: show a toast or a log message on the UI thread that the server was found
                    // runOnUiThread(() -> Toast.makeText(MainActivity.this, "Server found!", Toast.LENGTH_SHORT).show());
                }

                socket.close();

            } catch (Exception e) {
                Log.e("UDP_DISCOVERY", "Discovery failed: " + e.getMessage());
                // Fallback to the default URL or handle the error
                runOnUiThread(() -> {
                    // You could add an error message to the chat here
                    addToChat("⚠️ Failed to find server. Using default URL: " + urlStr, Message.Sent_By_Model);
                });
            }
        }).start();
    }


    private void connectModel(String question) {
        new Thread(() -> {
            try {
                URL url = new URL(urlStr); // urlStr is now dynamically set
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Use the correct JSON format for your Flask server
                String json = "{ \"prompt\": \"" + question + "\" }";

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes("utf-8"));
                os.flush();
                os.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(line);
                        if (jsonObj.has("response")) {
                            String chunk = jsonObj.getString("response");
                            response.append(chunk);
                            Log.d("DEBUG", "Raw line: " + line);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("DEBUG", "HTTP Response Code: " + conn.getResponseCode());

                br.close();

                addResponse(response.toString());

            } catch (IOException e) {
                Log.e("HTTP_REQUEST", "Error connecting to server: " + e.getMessage(), e);
                addResponse("⚠️ Error connecting to server: " + e.getMessage());
            }
        }).start();
    }


    void addResponse(String  response){
        response = response
                .replaceAll("(?i)<think>", "")  // remove <think> or <THINK>
                .replaceAll("(?i)</think>", "")
                .trim();

        runOnUiThread(()->{
            if(!messagesList.isEmpty() && messagesList.get(messagesList.size()-1).getSendby().equals(Message.Typing)){
                messagesList.remove(messagesList.size()-1);
                adapter.notifyItemRemoved(messagesList.size());
            }
        });

        Log.d("DEBUG", "Response collected: " + response);
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