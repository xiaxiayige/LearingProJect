package com.qilin.AnimationLearing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qilin.AnimationLearing.adapter.AnimtionListAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyle_id;

    private String[] animationArr={"ValueAnimtionActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyle_id= (RecyclerView) findViewById(R.id.recyle_id);
        recyle_id.setLayoutManager(new LinearLayoutManager(this));
        AnimtionListAdapter adapter=new AnimtionListAdapter(animationArr);
        recyle_id.setAdapter(adapter);
    }


}
