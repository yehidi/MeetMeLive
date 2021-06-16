package com.example.meetmelive.chat;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.meetmelive.R;
import com.example.meetmelive.model.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ChatFragment extends Fragment {
    EditText input;
    String sendersEmail;
    //TextView userName;
    View view;
    private FirebaseListAdapter<ChatMessage> adapter;
    public ChatFragment(){ }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_chat, container, false);
        input =view.findViewById(R.id.chatFragment_input);
        //userName=view.findViewById(R.id.user_name);
        //String name=ChatFragmentArgs.fromBundle(getArguments()).getUserName();
        //Log.d("Name",name);
        //userName.setText(name);
        // Inflate the layout for this fragment
        sendersEmail=ChatFragmentArgs.fromBundle(getArguments()).getSendersEmail();
        Log.d("CHATS", sendersEmail);
        FloatingActionButton fab =view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                // Read the input field and push a new instance
//                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chats")
                        .child(User.getInstance().getUsername())
                        .child(sendersEmail)
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chats")
                        .child(sendersEmail)
                        .child(User.getInstance().getUsername())
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                Log.d("TAG","the message is:"+input.getText().toString());
                // Clear the input
                input.setText("");
            }
        });
        displayChatMessages();
        return view;

    }
    void displayChatMessages() {
        ListView listOfMessages = view.findViewById(R.id.list_of_messages);
        //RecyclerView listOfMessages = (RecyclerView)findViewById(R.id.list_of_messages);
        Query query = FirebaseDatabase.getInstance().getReference().child("chats").child(User.getInstance().getUsername()).child(sendersEmail);
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .setLayout(R.layout.chat_message)
                .setLifecycleOwner(this)
                .build();
        adapter = new FirebaseListAdapter<ChatMessage>(options){
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of chat_message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageTime = v.findViewById(R.id.message_time);
                TextView messageUser = v.findViewById(R.id.message_user);
                //TextView messageUserSender=v.findViewById(R.id.message_user_sender);
                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());//model.getMessageUser()
                Log.d("messageUser", String.valueOf(messageUser));
                //messageUser.setText(sendersEmail);//
                //messageUserSender.setText(sendersEmail);
                //Log.d("messageUserSender", String.valueOf(messageUserSender));
                // Format the date before showing it
               // messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
                messageTime.setText(model.getMessageTime());
                Log.d("TAG","the message is: "+messageText);
            }
        };

        listOfMessages.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}