package com.xiaxiayige.animationdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtv1.setOnClickListener(this)
        txtv2.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.txtv1->{
                startActivity(Intent(this,ShakeActivity::class.java))
            }
            R.id.txtv2->{
                startActivity(Intent(this,BesselActivity::class.java))
            }
        }
    }
}