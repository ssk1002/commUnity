package com.wyp.chalkitup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.wyp.chalkitup.adapters.MessageAdapter;
import com.wyp.chalkitup.models.MessageItem;
import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private User mUser;
    private ProjectItem project;
    private List<MessageItem> messageItems = new ArrayList<>();
    private FirebaseDatabase database;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();
        mUser = getIntent().getParcelableExtra("mUser");
        project = getIntent().getParcelableExtra("project");
        messageAdapter = new MessageAdapter(this, messageItems, mUser.id);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(messageAdapter);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() != 0) {
                    MessageItem messageItem = new MessageItem(mUser.id, mUser.name, mUser.photoUrl, editText.getText().toString(), new Date());
                    DatabaseReference reference = database.getReference();
                    DatabaseReference reference1 = reference.child(Globals.MESSAGES).child(project.id).push();
//                    DatabaseReference reference2 = reference1.child(project.id).push();
                    messageItem.messageId = reference1.getKey();
                    reference1.setValue(messageItem);
                    editText.setText("");
                }
            }
        });
        loadData();
    }

    private void loadData() {
        DatabaseReference reference = database.getReference().child(Globals.MESSAGES).child(project.id);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);
                Log.d("ChatActivity", messageItem.message);
                messageItems.add(messageItem);
                messageAdapter.notifyDataSetChanged();
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

            }
        });

    }
}
