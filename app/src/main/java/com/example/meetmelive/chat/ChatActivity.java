//package com.example.meetmelive.chat;
//
//import android.os.Bundle;
//import android.text.format.DateFormat;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.meetmelive.R;
//import com.firebase.ui.database.FirebaseListAdapter;
//import com.firebase.ui.database.FirebaseListOptions;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.Objects;
//
//public class ChatActivity extends AppCompatActivity {
//    //Chat
//    EditText input;
//    private FirebaseListAdapter<ChatMessage> adapter;
//    DatabaseReference mChatData, mUserData;
//    String username = "null";
//    FirebaseAuth mAuth;
//    String sendersEmail;
//    //Chat
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        //sendersEmail=ChatActivityArgs.fromBundle().getUserEmail();
//        //Chat
//        FloatingActionButton fab =
//                (FloatingActionButton)findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                input = (EditText)findViewById(R.id.input);
//
//                // Read the input field and push a new instance
//                // of ChatMessage to the Firebase database
//                FirebaseDatabase.getInstance()
//                        .getReference()
//                        .push()
//                        .setValue(new ChatMessage(input.getText().toString(),
//                                FirebaseAuth.getInstance()
//                                        .getCurrentUser()
//                                        .getDisplayName())
//                        );
//                Log.d("TAG","the message is:"+input.getText().toString());
//                // Clear the input
//                input.setText("");
//            }
//        });
//
//        mAuth = FirebaseAuth.getInstance();
//        mChatData = FirebaseDatabase.getInstance().getReference().child("chats");
//        mUserData = FirebaseDatabase.getInstance().getReference().child(Objects.requireNonNull(mAuth.getUid()));
//
//        displayChatMessages();
//
//        mUserData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                username = Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//        //Chat
//        // Load chat room contents
//        //displayChatMessages();
//        //Chat
//    }
//    //Chat
//    void displayChatMessages() {
//        //Omri
//        //db.collection("userProfileData").document(User.getInstance().getEmail()).collection("chats").document(User.getInstance().getUsername()).set() //user sender
//        //Omri
//        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
//        //RecyclerView listOfMessages = (RecyclerView)findViewById(R.id.list_of_messages);
//        Query query = FirebaseDatabase.getInstance().getReference();//.child("chats")
//        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
//                .setQuery(query, ChatMessage.class)
//                .setLayout(R.layout.chat_message)
//                .setLifecycleOwner(this)
//                .build();
//        adapter = new FirebaseListAdapter<ChatMessage>(options){
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//                // Get references to the views of chat_message.xml
//                TextView messageText = v.findViewById(R.id.message_text);
//                TextView messageTime = v.findViewById(R.id.message_time);
//                TextView messageUser = v.findViewById(R.id.message_user);
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
//                Log.d("TAG","the message is: "+messageText);
//            }
//        };
//
//        listOfMessages.setAdapter(adapter);
//
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
//    //Chat
//
//}