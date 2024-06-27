package com.jingyuan.capstone.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jingyuan.capstone.DTO.Firebase.ChatRoomFDTO;
import com.jingyuan.capstone.R;

public class ChatActivity extends AppCompatActivity {
    ImageButton backBtn, sendBtn;
    String shopDoc;
    EditText messageInput;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ChatRoomFDTO chatroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        backBtn = findViewById(R.id.back_btn);
        sendBtn = findViewById(R.id.send_message_btn);
        messageInput = findViewById(R.id.chat_input);
        backBtn.setOnClickListener(v -> {
            getOnBackPressedDispatcher();
        });
//        Intent i = getIntent();
//        shopDoc = i.getStringExtra("shopDoc");
        setUpChatroom();
    }

    public void setUpChatroom() {
        firestore.collection("Chatroom")
                .whereEqualTo("userDoc", "mAuth.getUid()").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            chatroom = new ChatRoomFDTO();
                            chatroom.setUserDoc("mAuth.getUid()");
                            chatroom.setShopDoc("shopDoc");
                            chatroom.setTimeCreated(Timestamp.now());
                            Log.d("GACHI", chatroom.getTimeCreated().toString());
                            firestore.collection("Chatroom").add(chatroom);
                        }
                        else {
                            DocumentSnapshot snap = task.getResult().getDocuments().get(0);
                            chatroom = snap.toObject(ChatRoomFDTO.class);
                        }
                    }
                });
    }
}