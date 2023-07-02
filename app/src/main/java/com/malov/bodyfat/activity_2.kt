package com.malov.bodyfat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment

class activity_2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val calcBtn1: Button = findViewById(R.id.btn1)
        val calcBtn2: Button = findViewById(R.id.btn2)
        val calcBtn3: Button = findViewById(R.id.btn3)
        val frameLayout: FrameLayout = findViewById(R.id.frame_layout)
        val mainFrame: RelativeLayout = findViewById(R.id.mainFraim)
        val fatPersent = fatPersent()
        val cal = cal()
        val aqua = aqua()

        calcBtn1.setOnClickListener {
            openCloseFragment(frameLayout, mainFrame, cal, calcBtn3)
        }

        calcBtn2.setOnClickListener {
            openCloseFragment(frameLayout, mainFrame, fatPersent, calcBtn3)
        }

        calcBtn3.setOnClickListener{
            openCloseFragment(frameLayout, mainFrame, aqua, calcBtn3)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val btn3 = findViewById<Button>(R.id.btn3)
        btn3.isEnabled=true
    }

    fun openCloseFragment(
        frameLayout: FrameLayout,
        mainFrame: RelativeLayout,
        fragment: Fragment,
        calcBtn: Button
    ) {
        val vibe: Vibrator = this@activity_2?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(20)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            addToBackStack(null)
            commit()
            val anim: Animation =
                AnimationUtils.loadAnimation(this@activity_2, R.anim.openfragment)
            frameLayout.startAnimation(anim)
            calcBtn.isEnabled = false
        }
        mainFrame.setOnClickListener {
            vibe.vibrate(20)
            calcBtn.isEnabled = true
            supportFragmentManager.beginTransaction().apply {
                remove(fragment)
                val anim1: Animation =
                    AnimationUtils.loadAnimation(this@activity_2, R.anim.closefragment)
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

