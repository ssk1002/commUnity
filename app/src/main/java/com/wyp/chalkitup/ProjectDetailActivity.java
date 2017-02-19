package com.wyp.chalkitup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

public class ProjectDetailActivity extends AppCompatActivity {
    private ProjectItem projectItem;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        projectItem = getIntent().getParcelableExtra("project");
        mUser = getIntent().getParcelableExtra("mUser");
        ((TextView)findViewById(R.id.nameTxt)).setText(projectItem.name);
        ((TextView)findViewById(R.id.descriptionTxt)).setText(projectItem.description);
        ((TextView)findViewById(R.id.costTxt)).setText(projectItem.cost);
        Button button = (Button) findViewById(R.id.chatBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectDetailActivity.this, ChatActivity.class);
                intent.putExtra("mUser", mUser);
                intent.putExtra("project", projectItem);
                startActivity(intent);
            }
        });
    }
}
