package com.example.ashu.hometownlocation;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatList extends AppCompatActivity {


    public static ArrayList <String> selectedUser = new ArrayList<String>();
    public static ArrayList <String> selectedUserEmail = new ArrayList<String>();
    ArrayList<FirebaseDB> users = new ArrayList<FirebaseDB>();
    public static ArrayList <String> chatState = new ArrayList<String>();
    public static ArrayList <String> chatCountry = new ArrayList<String>();

    FirebaseAuth firebaseAuth;
    FirebaseUser u;
    static String currentUser;
    static String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        firebaseAuth = FirebaseAuth.getInstance();
        u = firebaseAuth.getCurrentUser();
        Log.i("Test", u.getEmail());
        String s = "msg";
        SampleTask st = new SampleTask();

        st.execute();
        //getAllUsersFromFirebase();
        //updateList();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulist, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.Logout:
                firebaseAuth.signOut();
                Intent n = new Intent(ChatList.this,MainActivity.class);
                startActivity(n);
                ///item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class SampleTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            //code to write getPosition
            selectedUser.clear();
            selectedUserEmail.clear();

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("personRegistered")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren()
                                    .iterator();
                            //List<FirebaseDB> users = new ArrayList<>();
                            while (dataSnapshots.hasNext()) {
                                DataSnapshot dataSnapshotChild = dataSnapshots.next();
                                FirebaseDB user = dataSnapshotChild.getValue(FirebaseDB.class);
                                if (!TextUtils.equals(user.getEmail(),
                                        FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                    ChatList.this.users.add(user);
                                    Log.i("Selected User", user.getNickname());
                                    Log.i("test email", FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                    selectedUser.add(user.getNickname());
                                    selectedUserEmail.add(user.getEmail());
                                    chatState.add(user.getState());
                                    chatCountry.add(user.getCountry());

                                }else
                                {
                                    Log.i("Current User", user.getNickname());
                                    currentUser = user.getNickname();
                                    currentUserEmail = user.getEmail();
                                }
                            }
                            ChatList.this.updateList();
                            // All users are retrieved except the one who is currently logged
                            // in device.
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Unable to retrieve the users.
                            Log.i("user not found","" );
                        }
                    });
            Log.i("users",Integer.toString(selectedUser.size()) );
            //publishProgress();
           // ChatList.this.updateList();
            return (null);

        }

        protected void onPostExecute(Void unused) {
            Toast.makeText(ChatList.this, "Post", Toast.LENGTH_SHORT)
                    .show();
            Log.i("users in post",Integer.toString(selectedUser.size()) );
            //ChatList.this.updateList();

        }
        protected void onPreExecute() {
            Toast.makeText(ChatList.this, "Pre", Toast.LENGTH_SHORT)
                    .show();
            Log.i("users in pre",Integer.toString(selectedUser.size()) );
            //ChatList.this.updateList();

        }
        /*protected void onProgressUpdate() {
            //super.onProgressUpdate(values);
            Toast.makeText(ChatList.this,"Fetching from firebase" , Toast.LENGTH_SHORT).show();

        }*/
    }


    /*public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("personRegistered")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren()
                                .iterator();
                        //List<FirebaseDB> users = new ArrayList<>();
                        while (dataSnapshots.hasNext()) {
                            DataSnapshot dataSnapshotChild = dataSnapshots.next();
                            FirebaseDB user = dataSnapshotChild.getValue(FirebaseDB.class);
                            if (!TextUtils.equals(user.getEmail(),
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                ChatList.this.users.add(user);
                                Log.i("Selected User", user.getNickname());
                                Log.i("test email", FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                selectedUser.add(user.getNickname());
                                selectedUserEmail.add(user.getEmail());
                            }else
                            {
                                Log.i("Current User", user.getNickname());
                                currentUser = user.getNickname();
                                currentUserEmail = user.getEmail();
                            }
                        }
                        // All users are retrieved except the one who is currently logged
                        // in device.
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to retrieve the users.
                        Log.i("user not found"," user not found" );
                    }
                });
    }
*/
    public void updateList()
    {
        ListView ls = (ListView) findViewById(R.id.listView);
        //setListAdapter(adapter);
        Log.i("updateList", Integer.toString(selectedUser.size()));

        //Log.i("current user",ChatList.this.currentUser);
        //Log.i("selected user", item);
        //Log.i("current user email", ChatList.this.currentUserEmail);
        //Log.i("select",ChatList.this.)
        //Log.i("selected user email", item1);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedUser);

        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                final String item1 = (String) parent.getItemAtPosition(position);

                Intent n = new Intent(ChatList.this, ChatRoom.class);

                //
                Log.i("current user",ChatList.this.currentUser);
                Log.i("selected user", item);
                Log.i("current user email", ChatList.this.currentUserEmail);
                Log.i("selected user email", item1);

                n.putExtra("current user",ChatList.this.currentUser);
                n.putExtra("selected user", item);
                n.putExtra("current user email", ChatList.this.currentUserEmail);
                n.putExtra("selected user email", item1);
                startActivity(n);
            }
        });
    }

    public void mapv(View view)
    {
        Intent n = new Intent(this, ChatMapView.class);

        startActivity(n);
    }
}
