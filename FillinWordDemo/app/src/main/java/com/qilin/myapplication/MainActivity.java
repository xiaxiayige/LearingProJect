package com.qilin.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private EditText edit_query;
    //    private String mString = "aaaaa <font color='#FF6A6A'><a href='#1'><u> #1 </u> </font></a>bbbbbbb" +
//            "<font color='#FF6A6A'><a href='#2'><u> #2 </u> </font></a>";
    private String mString = "hello my name is  (  1 )  ,what's your name ( 2 ) ";
    private String clickString;
    String staaaar;
    int offset;
    int pre;
    int lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text);
        edit_query = (EditText) findViewById(R.id.edit_query);
        tv.setText(mString);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        offset = tv.getOffsetForPosition(event.getX(), event.getY());
                        staaaar = tv.getText().toString();
                        pre = findPreFlag(staaaar, offset);
                        lst = findLstFlag(staaaar, offset);
                        if (pre != -1 && lst != -1) {
                            String str = staaaar.substring(pre + 1, lst);
                            if (!TextUtils.isEmpty(str)) {
                                edit_query.setText(str.trim());
                            }

                            edit_query.post(new Runnable() {
                                @Override
                                public void run() {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            });


                            edit_query.setVisibility(View.VISIBLE);
                            edit_query.requestFocus();
                        } else {
                            edit_query.setVisibility(View.GONE);
                            edit_query.setText(null);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        edit_query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) { // 模拟器返回0 为了能正常相应事件 这里应该填写 EditorInfo.IME_ACTION_DONE
                    String s = edit_query.getText().toString().trim();
                    String str = tv.getText().toString();
                    StringBuffer stringBuffer = new StringBuffer(str);
                    tv.setText(stringBuffer.replace(pre + 1, lst, s).toString());
                    edit_query.setVisibility(View.GONE);
                    edit_query.setText(null);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

    }

    public int findPreFlag(String sourceStr, int offSet) {
        boolean flag = false;
        int coord = -1;
        char preFlag = '(';
        char lstFlag = ')';
        String newStr = sourceStr.substring(0, offSet);
        for (int i = newStr.length() - 1; i > 0; i--) {
            char ch = newStr.charAt(i);
            if (ch == preFlag) {
                coord = i;
                flag = true;
                Log.i("MainActivity", "findPreFlag: true " + ch);
                break;
            } else if (ch == lstFlag) {
                flag = false;
                Log.i("MainActivity", "findPreFlag: false " + ch);
                break;
            }
        }
        return coord;
    }

    public int findLstFlag(String sourceStr, int offSet) {
        boolean flag = false;
        char preFlag = '(';
        char lstFlag = ')';
        int coord = -1;
        for (int i = offSet; i < sourceStr.length() - 1; i++) {
            char ch = sourceStr.charAt(i);
            if (ch == preFlag) {
                flag = false;
                Log.i("MainActivity", "findLstFlag: false " + ch);
                break;
            } else if (ch == lstFlag) {
                Log.i("MainActivity", "findLstFlag: true " + ch);
                coord = i;
                flag = true;
                break;
            }
        }
        return coord;
    }

}
