package com.malov.bodyfat

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
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.Slider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class Burningcalories : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    var time: Int = 150
    var weight: Int = 80
    var sex: Int = 0  // 1-female, 0- male

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_burningcalories, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Burningcalories")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "Burningcalories")
        }


        val btnCloseFr: Button = view.findViewById(R.id.btnClose)

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast, view?.findViewById(R.id.toastShape))

        btnCloseFr.setOnTouchListener(object : OnSwipeTouchListener(view.context) {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                return super.onTouch(view, motionEvent)
                doCloseFragment()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                doCloseFragment()
            }
        })

        val button: Button = view.findViewById(R.id.button)

        val sliderWeight: Slider = view.findViewById(R.id.seekWeight)
        val textWeight: TextView = view.findViewById(R.id.valuetextWeight)

        val sliderTime: Slider = view.findViewById(R.id.seekTime)
        val textTime: TextView = view.findViewById(R.id.valuetextTime)

        val switchOnOff: SwitchCompat = view.findViewById(R.id.switchOnOff)



        fun upDate() {
            time = doSlide(sliderTime, textTime)
            weight = doSlide(sliderWeight, textWeight)

            switchOnOff.setOnCheckedChangeListener { _, cheked ->
                when {
                    cheked -> {
                        sex = 0
                    }
                    else -> {
                        sex = 1
                    }
                }
            }
        }
        upDate()



        button.setOnClickListener {
            val builder = AlertDialog.Builder(view.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(
                R.layout.dialogburning,
                view.findViewById(R.id.dialogShape)
            )
            val walk: TextView = dialogLayout.findViewById(R.id.walk)
            val walk6: TextView = dialogLayout.findViewById(R.id.walk6)
            val run10: TextView = dialogLayout.findViewById(R.id.run10)
            val run12: TextView = dialogLayout.findViewById(R.id.run12)
            val run15: TextView = dialogLayout.findViewById(R.id.run15)
            val cycle20: TextView = dialogLayout.findViewById(R.id.cycle20)
            val cycle25: TextView = dialogLayout.findViewById(R.id.cycle25)
            val aerobic: TextView = dialogLayout.findViewById(R.id.aerobic)
            val swiming25: TextView = dialogLayout.findViewById(R.id.swiming25)
            val swiming40: TextView = dialogLayout.findViewById(R.id.swiming40)
            val skiing : TextView = dialogLayout.findViewById(R.id.skiing)
            val tenis: TextView = dialogLayout.findViewById(R.id.tenis)
            val bascetboll: TextView = dialogLayout.findViewById(R.id.bascetboll)
            val dance: TextView = dialogLayout.findViewById(R.id.dance)
            val struggle: TextView = dialogLayout.findViewById(R.id.struggle)
            val bouling: TextView = dialogLayout.findViewById(R.id.bouling)
            val anhletic: TextView = dialogLayout.findViewById(R.id.atlenic)
            val football: TextView = dialogLayout.findViewById(R.id.football)
            val boating: TextView = dialogLayout.findViewById(R.id.boating)
            val shoping: TextView = dialogLayout.findViewById(R.id.shoping)
            val dealmaking: TextView = dialogLayout.findViewById(R.id.dealmaking)
            val driving: TextView = dialogLayout.findViewById(R.id.driving)
            val housworker: TextView = dialogLayout.findViewById(R.id.housworker)

            upDate()

            val walkIndex= 0.0414
            val walk6Index= 0.0644
            val run10Index= 0.1732
            val run12Index= 0.2133
            val run15Index= 0.2533
            val cycle20Index= 0.0879
            val cycle25Index= 0.0920
            val aerobicIndex= 0.194
            val swiming25Index= 0.09
            val swiming40Index= 0.2667
            val skiingIndex= 0.1199
            val tenisIndex= 0.1102
            val bascetbollIndex= 0.1389
            val danceIndex= 0.0756
            val struggleIndex= 0.1732
            val boulingIndex= 0.0463
            val athleticIndex= 0.0904
            val boatingIndex= 0.2133
            val footballIndex= 0.2133
            val shopingIndex= 0.0617
            val dealmakingIndex= 0.0234
            val drivingIndex= 0.0311
            val housworkerIndex= 0.048


            if(sex==0){
                walk.text = calculate(walkIndex)
                walk6.text = calculate(walk6Index)
                run10.text = calculate(run10Index)
                run12.text = calculate(run12Index)
                run15.text = calculate(run15Index)
                cycle20.text = calculate(cycle20Index)
                cycle25.text = calculate(cycle25Index)
                aerobic.text = calculate(aerobicIndex)
                swiming25.text = calculate(swiming25Index)
                swiming40.text = calculate(swiming40Index)
                skiing.text = calculate(skiingIndex)
                tenis.text = calculate(tenisIndex)
                bascetboll.text = calculate(bascetbollIndex)
                dance.text = calculate(danceIndex)
                struggle.text = calculate(struggleIndex)
                bouling.text = calculate(boulingIndex)
                anhletic.text = calculate(athleticIndex)
                boating.text = calculate(boatingIndex)
                football.text = calculate(footballIndex)
                shoping.text = calculate(shopingIndex)
                dealmaking.text = calculate(dealmakingIndex)
                driving.text = calculate(drivingIndex)
                housworker.text = calculate(housworkerIndex)
            } else {
                walk.text = calculate(0.0392)
                walk6.text = calculate(0.0611)
                run10.text = calculate(0.1653)
                run12.text = calculate(0.2036)
                run15.text = calculate(0.2418)
                cycle20.text = calculate(0.0838)
                cycle25.text = calculate(0.0877)
                aerobic.text = calculate(0.1911)
                swiming25.text = calculate(0.0855)
                swiming40.text = calculate(0.2543)
                skiing.text = calculate(0.1144)
                tenis.text = calculate(0.1049)
                bascetboll.text = calculate(0.1329)
                dance.text = calculate(0.0721)
                struggle.text = calculate(0.1653)
                bouling.text = calculate(0.0392)
                anhletic.text = calculate(0.0661)
                boating.text = calculate(0.2036)
                football.text = calculate(0.2036)
                shoping.text = calculate(0.0553)
                dealmaking.text = calculate(0.022)
                driving.text = calculate(0.0295)
                housworker.text = calculate(0.0456)
            }

            builder
                .setView(dialogLayout)
                .show()

        }
    }


    fun calculate(index: Double): String{
        return (Math.round(index * weight * time)).toString()
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
    fun doCloseFragment() {
        val vibe: Vibrator = view?.context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(20)
        val anim: Animation = AnimationUtils.loadAnimation(view?.context, R.anim.closefragment)
        view?.startAnimation(anim)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(view?.context, Activity_2::class.java))
                getActivity()?.overridePendingTransition(R.anim.nullanim, R.anim.nullanim)
            }, 200
        )
    }
}