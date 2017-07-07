package com.qilin.AnimationLearing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.qilin.AnimationLearing.view.WordJumpJumpJumpLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */

public class WordJumpAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtv_value1, txtv_value2, txtv_value3;
    WordJumpJumpJumpLayout wordJumpAnimationLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordjumpanimation);
        txtv_value1 = (TextView) findViewById(R.id.txtv_value1);
        txtv_value2 = (TextView) findViewById(R.id.txtv_value2);
        txtv_value3 = (TextView) findViewById(R.id.txtv_value3);
        wordJumpAnimationLayout = (WordJumpJumpJumpLayout) findViewById(R.id.wordjump);
        txtv_value1.setOnClickListener(this);
        txtv_value2.setOnClickListener(this);
        txtv_value3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtv_value1:
                List<String> list = new ArrayList<>();
                list.add("hello");
                list.add("word");
                wordJumpAnimationLayout.setWord(list);
                break;
            case R.id.txtv_value2:
                List<String> list2 = new ArrayList<>();
                list2.add("hello");
                list2.add("hello");
                list2.add("word");
                wordJumpAnimationLayout.setWord(list2);
                break;
            case R.id.txtv_value3:
                List<String> list3 = new ArrayList<>();
                list3.add("my");
                list3.add("name");
                list3.add("is");
                list3.add("kang");
                list3.add("kang");
                wordJumpAnimationLayout.setWord(list3);
                break;
        }
    }
}
