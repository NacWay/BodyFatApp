package com.malov.bodyfat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

class Activity_2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        FirebaseApp.initializeApp(this@Activity_2)

        val calcBtn1: Button = findViewById(R.id.btn1)
        val calcBtn2: Button = findViewById(R.id.btn2)
        val calcBtn3: Button = findViewById(R.id.btn3)
        val calcBtn4: Button = findViewById(R.id.btn4)
        val frameLayout: FrameLayout = findViewById(R.id.frame_layout)
        val mainFrame: RelativeLayout = findViewById(R.id.mainFraim)
        val fatPersent = FatPersent()
        val cal = Cal()
        val aqua = Aqua()
        val idealWeight = IdealWeight()
        val ideaBtn : ImageButton = findViewById(R.id.ideaBtn)
        val animup: Animation = AnimationUtils.loadAnimation(this@Activity_2, R.anim.ideaup)
        ideaBtn.startAnimation(animup)

        ideaBtn.setOnClickListener{
            val build  = AlertDialog.Builder(this@Activity_2)
            val dialogLayout = layoutInflater.inflate(R.layout.dialogidea, findViewById(R.id.dialogShape))
            build.setView(dialogLayout).show()
        }

        calcBtn1.setOnClickListener {
            openCloseFragment(frameLayout, mainFrame, cal, calcBtn3, calcBtn4)
        }

        calcBtn2.setOnClickListener {
            openCloseFragment(frameLayout, mainFrame, fatPersent, calcBtn3, calcBtn4)
        }

        calcBtn3.setOnClickListener{
            openCloseFragment(frameLayout, mainFrame, aqua, calcBtn3, calcBtn4)
        }

        calcBtn4.setOnClickListener{
            openCloseFragment(frameLayout, mainFrame, idealWeight, calcBtn3, calcBtn4)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        btn3.isEnabled=true
        btn4.isEnabled=true
    }

    fun openCloseFragment(
        frameLayout: FrameLayout,
        mainFrame: RelativeLayout,
        fragment: Fragment,
        calcBtn3: Button,
        calcBtn4: Button
    ) {
        val vibe: Vibrator = this@Activity_2?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(20)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            addToBackStack(null)
            commit()
            val anim: Animation =
            AnimationUtils.loadAnimation(this@Activity_2, R.anim.openfragment)
           // frameLayout.visibility= View.VISIBLE
            frameLayout.startAnimation(anim)
            calcBtn3.isEnabled = false
            calcBtn4.isEnabled = false
        }
        mainFrame.setOnClickListener {
            vibe.vibrate(20)
            calcBtn3.isEnabled = true
            calcBtn4.isEnabled = true
            supportFragmentManager.beginTransaction().apply {
                remove(fragment)
                val anim1: Animation =
                    AnimationUtils.loadAnimation(this@Activity_2, R.anim.closefragment)
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

