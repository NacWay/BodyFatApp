package com.malov.bodyfat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.slider.Slider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


class Aqua : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aqua, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Aqua")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "Aqua")
        }

        val btnCloseFr : Button = view.findViewById(R.id.btnClose)

        btnCloseFr.setOnTouchListener(object : OnSwipeTouchListener(view.context){
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                return super.onTouch(view, motionEvent)
                doCloseFragment()
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
                doCloseFragment()
            }
        })

        val radioGroup : RadioGroup = view.findViewById(R.id.radioGroupDay)
        val sliderWeight : Slider = view.findViewById(R.id.seekWeight)
        val textWeight : TextView = view.findViewById(R.id.valuetextWeight)
        val button : Button = view.findViewById(R.id.button)

        var weight : Int = 60
        var day : Int = 30


        fun upDate(){
            weight = doSlide(sliderWeight, textWeight)
            radioGroup.clearCheck()
            radioGroup.setOnCheckedChangeListener{ group, checkedId ->
                group.findViewById<RadioButton>(checkedId)?.apply {
                    when{
                        text == "Обычный день" -> day = 30
                        text == "Тренировочный день" -> day = 35
                        text == "Жаркий день" -> day = 40
                    }
                 }
                }
            }
        upDate()

        button.setOnClickListener{
            upDate()
            val res : Int = weight*day
            val valInCup : Int = res / 250
            val builder = AlertDialog.Builder(view.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialogaqua, view?.findViewById(R.id.dialogShape))
            val textRes : TextView = dialogLayout.findViewById(R.id.valueAqua)
            val textCup : TextView = dialogLayout.findViewById(R.id.valueAquaCup)
            textCup.text = "= $valInCup стаканов воды"
            textRes.text = "$res мл"
             builder
                 .setView(dialogLayout)
                 .show()
        }
    }

private fun doSlide(slider: Slider, text: TextView) : Int {
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }
            override fun onStopTrackingTouch(slider: Slider) {
                text.text = "${slider.value.toInt()}"
            }
        })
        slider.addOnChangeListener { slider, value, fromUser ->
            text.text = "${slider.value.toInt()}"
            val vibe: Vibrator = view?.context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibe.vibrate(50)
        }
        return slider.value.toInt()
    }
fun doCloseFragment(){
    val vibe: Vibrator = view?.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibe.vibrate(20)
    val anim : Animation = AnimationUtils.loadAnimation(view?.context,  R.anim.closefragment)
    view?.startAnimation(anim)
    Handler(Looper.getMainLooper()).postDelayed(
        {
            startActivity(Intent(view?.context, Activity_2::class.java))
            getActivity()?.overridePendingTransition(R.anim.nullanim, R.anim.nullanim)
        }, 200)
}
}