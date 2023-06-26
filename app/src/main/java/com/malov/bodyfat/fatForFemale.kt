package com.malov.bodyfat

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.google.android.material.slider.Slider

class fatForFemale : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fat_for_female, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button : Button = view.findViewById(R.id.button)
        val sliderHeight : Slider = view.findViewById(R.id.seekHeight)
        val textHeight : TextView = view.findViewById(R.id.valuetextHight)

        val sliderWaist : Slider = view.findViewById(R.id.seekWaist)
        val textWaist : TextView = view.findViewById(R.id.valuetextWaist)

        val sliderNeck : Slider = view.findViewById(R.id.seekNeck)
        val textNeck : TextView = view.findViewById(R.id.valuetextNeck)

        val sliderHips : Slider = view.findViewById(R.id.seekHips)
        val textHips : TextView = view.findViewById(R.id.valuetextHips)
        
        button.setOnClickListener{
            val height : MutableSet<Int> = mutableSetOf(doSlide(sliderHeight, textHeight))
            val waist : MutableSet<Int> = mutableSetOf(doSlide(sliderWaist, textWaist))
            val neck : MutableSet<Int> = mutableSetOf(doSlide(sliderNeck, textNeck))
            val hips : MutableSet<Int> = mutableSetOf(doSlide(sliderHips, textHips))
            val builder = AlertDialog.Builder(view.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.fragment_fat_for_female, null)
            builder.setCancelable(true)
                .setTitle("XYI")
                .setMessage(" heigh = $height \n waist = $waist \n neck = $neck \n hips = $hips")
            builder.show()
        }

    }

    private fun doSlide(slider: Slider, text: TextView) : Int {
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }
            override fun onStopTrackingTouch(slider: Slider) {
                text.text = "${slider.value.toInt()} кг"
            }
        })
        slider.addOnChangeListener { slider, value, fromUser ->
            text.text = "${slider.value.toInt()} кг"
            val vibe: Vibrator = view?.context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibe.vibrate(50)
        }
        return slider.value.toInt()
    }


}
