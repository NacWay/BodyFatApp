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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AlertDialogLayout
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.Slider


class IdealWeight : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ideal_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val button : Button = view.findViewById(R.id.button)

        val sliderOld : Slider = view.findViewById(R.id.seekOld)
        val textOld : TextView = view.findViewById(R.id.valuetextOld)

        val sliderHeight : Slider = view.findViewById(R.id.seekHeight)
        val textHeight : TextView = view.findViewById(R.id.valuetextHight)

        val sliderWeight : Slider = view.findViewById(R.id.seekWeight)
        val textWeight : TextView = view.findViewById(R.id.valuetextWeight)

        val switchOnOff : SwitchCompat = view.findViewById(R.id.switchOnOff)

        var old : Int = 40
        var height : Int = 130
        var weight : Int = 60
        var sex : Int = 0  // 1-female, 0- male

        fun upDate(){
            old = doSlide(sliderOld, textOld)
            height = doSlide(sliderHeight, textHeight)
            weight = doSlide(sliderWeight, textWeight)

            switchOnOff.setOnCheckedChangeListener{ _, cheked ->
                when{
                    cheked-> { sex = 0 }
                    else -> { sex = 1 }
                }
            }
        }

        upDate()

        button.setOnClickListener{
            val  builder = AlertDialog.Builder(view.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialogidialweight, view?.findViewById(R.id.dialogShape))
            val btnWhyText : Button = dialogLayout.findViewById(R.id.whyText)
            builder.setView(dialogLayout).show()
            btnWhyText.setOnClickListener{
                val builder1 = AlertDialog.Builder(dialogLayout.context)
                builder1.setMessage(R.string.endIdealWeight).show()
            }

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