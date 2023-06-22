package com.malov.bodyfat

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction

class activity_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val calcBtn1 : Button = findViewById(R.id.btn1)
        val calcBtn2 : Button = findViewById(R.id.btn2)
        val frameLayout : FrameLayout = findViewById(R.id.frame_layout)
        frameLayout.setBackgroundResource(R.drawable.cornerforfragment)


        val fatPersent : fatPersent = fatPersent()
        val ft : FragmentTransaction = supportFragmentManager.beginTransaction()

        calcBtn2.setOnClickListener{view ->
            ft.add(R.id.frame_layout, fatPersent)
            ft.commit()
        }
    }
}