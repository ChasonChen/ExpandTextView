package com.chasonc.expandtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chasonc.library.ExpandTextView;

public class MainActivity extends AppCompatActivity {

    ExpandTextView etvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etvContent = findViewById(R.id.etv_content);
        etvContent.setExpandContent("爱好脑残爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残的" +
                "拿手的女女爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残的拿手" +
                "的女女爱好脑残的拿手的女女的拿手的女爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残的拿手的女女爱好脑残" +
                "的拿手的女女女");
    }
}
