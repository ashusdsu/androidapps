package com.example.ashu.hometownlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {
    String currentUser;
    String selectedUser;
    String LinkXY, LinkYX, msg, currentUserEmail, selectedUserEmail;
    EditText msgtxt;
    TextView reName;
    ArrayList<Chat> msgList = new ArrayList<>();
    private MsgAdapter msgAdapter;
    LinearLayoutManager layoutManager;
    RecyclerView chatRecycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Bundle bundle = getIntent().getExtras();
        currentUser = bundle.getString("current user");
        selectedUser = bundle.getString("selected user");
        currentUserEmail = bundle.getString("current user email");
        selectedUserEmail = bundle.getString("selected user email");

        //Verifying if we got the data
        Log.i("CRcurrent User", currentUser);
        Log.i("CRselected User", selectedUser);
        Log.i("CRcurrent email", currentUserEmail);
        Log.i("CRselected email", selectedUserEmail);

        LinkXY = currentUser+"_"+selectedUser;
        LinkYX = selectedUser+"_"+currentUser;

        reName = (TextView) this.findViewById(R.id.recID);
        msgtxt = (EditText) this.findViewById(R.id.msgText);
        //getActionBar().setTitle(selectedUser);

        reName.setText(selectedUser);
        chatRecycle = (RecyclerView) this.findViewById(R.id.chatrow);
        layoutManager = new LinearLayoutManager(this);
        getMessageFromFirebase(LinkXY, LinkYX);


    }

    public void sendMsg(View view)
    {
        msg = msgtxt.getText().toString();
        Chat chat = new Chat();
        chat.sender = currentUser;
        chat.receiver = selectedUser;
        chat.senderUid = currentUserEmail;
        chat.receiverUid = selectedUserEmail;
        //chat.senderUid = ;
        //chat.receiverUid = selectedUid;
        chat.message = msg;
        chat.timestamp = new Date();
        if (chat.message != null)
        {
            Log.i("sending message", chat.message);
            sendMessageToFirebase(chat);
            msgtxt.setText("");
        }
        else
        {
            Toast.makeText(this, "Please Type Message", Toast.LENGTH_SHORT).show();
        }
    }

    public void getMessageFromFirebase(final String LinkXY, final String LinkYX) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();

        databaseReference.child("Messages")
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(LinkXY)) {
                            Log.e("receiveMessage", "getMessageFromFirebaseUser: " + LinkXY + " exists");
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Messages")
                                    .child(LinkXY)
                                    .addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            // Chat message is retreived.
                                            Chat chat = dataSnapshot.getValue(Chat.class);
                                            makeChatList(dataSnapshot);

                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Unable to get message.
                                        }
                                    });
                        } else if (dataSnapshot.hasChild(LinkYX)) {
                            Log.e("receiveMessage", "getMessageFromFirebaseUser: " + LinkYX + " exists");
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Messages")
                                    .child(LinkYX)
                                    .addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            // Chat message is retreived.
                                            Chat chat = dataSnapshot.getValue(Chat.class);
                                            makeChatList(dataSnapshot);

                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Unable to get message.
                                        }
                                    });
                        } else {
                            Log.e("receiveMessage", "getMessageFromFirebaseUser: no such room available");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to get message
                    }
                });

    }

        public void makeChatList(DataSnapshot dataSnapshot){
        Chat chat = dataSnapshot.getValue(Chat.class);
        Log.i("message ", chat.message);
        msgList.add(chat);

            Log.i("msgList size", Integer.toString(msgList.size()));
            //setting the recycler view

            msgAdapter = new MsgAdapter(msgList);
            msgAdapter.notifyDataSetChanged();
            chatRecycle.setLayoutManager(layoutManager);
            chatRecycle.setItemAnimator(new DefaultItemAnimator());
            chatRecycle.setAdapter(msgAdapter);
    }


    public void sendMessageToFirebase(final Chat chat) {
        final String LinkXY = chat.sender + "_" + chat.receiver;
        final String LinkYX = chat.receiver + "_" + chat.sender;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();

        databaseReference.child("Messages")
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(LinkXY)) {
                            Log.e("sendMessage", "sendMessageToFirebaseUser: " + LinkXY + " exists");
                            databaseReference.child("Messages")
                                    .child(LinkXY)
                                    .child(String.valueOf(chat.timestamp))
                                    .setValue(chat);
                        } else if (dataSnapshot.hasChild(LinkYX)) {
                            Log.e("sendMessage", "sendMessageToFirebaseUser: " + LinkYX + " exists");
                            databaseReference.child("Messages")
                                    .child(LinkYX)
                                    .child(String.valueOf(chat.timestamp))
                                    .setValue(chat);
                        } else {
                            Log.e("sendMessage", "sendMessageToFirebaseUser: success");
                            databaseReference.child("Messages")
                                    .child(LinkXY)
                                    .child(String.valueOf(chat.timestamp))
                                    .setValue(chat);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to send message.
                    }
                });
    }



}
