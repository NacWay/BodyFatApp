package com.malov.bodyfat

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.slider.Slider
import java.text.DecimalFormat
import kotlin.math.roundToInt


class fatForMale : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fan_for_male, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast, view?.findViewById(R.id.linearLayout))
        val myToast = Toast(view.context)

        fun doToast(s: String, waist: Int) {
            val text : TextView = layout.findViewById(R.id.textToast)
            text.text = s
            val image : ImageView = layout.findViewById(R.id.imageToast)
            image.setImageResource(waist)
            myToast.duration = Toast.LENGTH_LONG
            myToast.setGravity(Gravity.TOP,0,0)
            myToast.view = layout
            myToast.show()
        }

        val noticeWaist : ImageButton = view.findViewById(R.id.noticeWaist)
        noticeWaist.setOnClickListener{
            doToast("В наименее широком месте живота. \nНе втягивайте при этом живот!", R.drawable.waistman)
        }

        val noticeNeck : ImageButton = view.findViewById(R.id.noticeNeck)
        noticeNeck.setOnClickListener{
            doToast("Начните с горла и слегка опустите портновский сантиметр спереди. \nНе раздувайте шею!", R.drawable.neck)
        }

        val button : Button = view.findViewById(R.id.button)
        val sliderHeight : Slider = view.findViewById(R.id.seekHeight)
        val textHeight : TextView = view.findViewById(R.id.valuetextHight)

        val sliderWaist : Slider = view.findViewById(R.id.seekWaist)
        val textWaist : TextView = view.findViewById(R.id.valuetextWaist)

        val sliderNeck : Slider = view.findViewById(R.id.seekNeck)
        val textNeck : TextView = view.findViewById(R.id.valuetextNeck)

        var height : Int = 50
        var waist : Int = 50
        var neck : Int = 50

        fun upDate(){
            height = doSlide(sliderHeight, textHeight)
            waist = doSlide(sliderWaist, textWaist)
            neck = doSlide(sliderNeck, textNeck)
        }

        upDate()

        val result : Double = 86.010 * (Math.log((waist-neck).toDouble()) / Math.log(10.0)) - 97.684*(Math.log(height.toDouble()) / Math.log(10.0)) - 78.387


        button.setOnClickListener{
            upDate()
            val result : Double = 495 / (1.0324 - 0.19077 * Math.log10((waist - neck).toDouble()) + 0.15456 * Math.log10(
                height.toDouble()
            )) - 450
            DecimalFormat("#0.00").format(result)
            val builder = AlertDialog.Builder(view.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.fragment_fat_for_female, null)
            builder.setCancelable(true)
                .setTitle("XYI")
                .setMessage("%твоего вонючего жира = $result \nheigh = $height \n waist = $waist \n neck = $neck Подобные измерения не высчитывают точный процент жира в организме, это только примерные оценки. Проверка гидростатической плотности (гидростатическое взвешивание) — единственный метода, дающий точные результаты.\n" +
                        "Любой служащий ВМС может рассказать вам об ошибках в результате применения этого метода, поэтому при наличии вопросов вам следует обращаться к врачу.")
            builder.show()
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
}
