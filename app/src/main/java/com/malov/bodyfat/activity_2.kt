package com.malov.bodyfat

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class activity_2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)


        val calcBtn1: Button = findViewById(R.id.btn1)
        val calcBtn2: Button = findViewById(R.id.btn2)
        val frameLayout: FrameLayout = findViewById(R.id.frame_layout)
        val mainFrame: FrameLayout = findViewById(R.id.mainFraim)
        val fatPersent = fatPersent()
        val vibe: Vibrator = this@activity_2?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        calcBtn2.setOnClickListener {
            vibe.vibrate(20)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_layout, fatPersent)
                addToBackStack(null)
                commit()
                val anim: Animation =
                    AnimationUtils.loadAnimation(this@activity_2, R.anim.openfragment)
                frameLayout.startAnimation(anim)
            }
            mainFrame.setOnClickListener {
                vibe.vibrate(20)
                supportFragmentManager.beginTransaction().apply {
                    remove(fatPersent)
                    val anim1: Animation =  AnimationUtils.loadAnimation(this@activity_2, R.anim.closefragment)
                    frameLayout.startAnimation(anim1)
                    Handler(Looper.getMainLooper()).postDelayed(        //немного замедляем поток чтобы показать начальную анимацию
                        {
                            commit()
                        },

                        200
                    )
                }
            }
        }
    }
}
