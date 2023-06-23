package com.malov.bodyfat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
        val mainFrame : FrameLayout = findViewById(R.id.mainFraim)
        frameLayout.visibility = GONE
        val fatPersent = fatPersent()
        val ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.frame_layout, fatPersent)
        ft.addToBackStack(null)
        ft.commit()


        calcBtn2.setOnClickListener{view ->
            frameLayout.visibility = VISIBLE
            val anim : Animation = AnimationUtils.loadAnimation(this,  R.anim.openfragment)
            frameLayout.startAnimation(anim)
            mainFrame.isEnabled = true   //делаем активным нажатие на mainFraim
            mainFrame.setOnClickListener{view ->
                frameLayout.visibility = GONE
                val anim : Animation = AnimationUtils.loadAnimation(this,  R.anim.closefragment)
                frameLayout.startAnimation(anim)
                mainFrame.isEnabled = false //делаем не активным нажатие на mainFraim(чтобы при невидимом фрагменте не вызывалась анимация закрытия)
            }
      }
    }
}