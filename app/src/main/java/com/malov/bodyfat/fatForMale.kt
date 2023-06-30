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


class fatForMale : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fat_for_male, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast, view?.findViewById(R.id.toastShape))
        val myToast = Toast(view.context)

        fun doToast(s: String, layoutForToast: Int) {
            val text : TextView = layout.findViewById(R.id.textToast)
            text.text = s
            val image : ImageView = layout.findViewById(R.id.imageToast)
            image.setImageResource(layoutForToast)
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

        button.setOnClickListener{
            upDate()

            var result : Double = 495 / (1.0324 - 0.19077 * Math.log10((waist - neck).toDouble()) + 0.15456 * Math.log10(height.toDouble())) - 450

            val builder = AlertDialog.Builder(view.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialog, view?.findViewById(R.id.dialogShape))

            val categoryFat : String = categoryFat(result)
            val resStr : String = DecimalFormat("#0.00").format(result)
            val text : TextView = dialogLayout.findViewById(R.id.resDialog)
            text.text = "Процент жира: $resStr% \nУ вас: $categoryFat \n"

            builder.setCancelable(true)
                .setView(dialogLayout)
            builder.show()
        }

    }

    private fun doSlide(slider: Slider, text: TextView) : Int {
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }
            override fun onStopTrackingTouch(slider: Slider) {
                text.text = "${slider.value.toInt()} см"
            }
        })
        slider.addOnChangeListener { slider, value, fromUser ->
            text.text = "${slider.value.toInt()} см"
            val vibe: Vibrator = view?.context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibe.vibrate(50)
        }
        return slider.value.toInt()
    }

    private fun categoryFat(result: Double) : String{
        var string : String = "Ошибка определения"
        when{
            result in 2.00..6.00 -> string = "Необходимый жир — это минимум, который нужен для выживания"
            result in 6.01..14.00 -> string = "Атлетическое телосложение"
            result in 14.01..18.00 -> string = "Спортивное телосложение"
            result in 18.01..36.00 -> string = "Обычное телосложение"
            result >= 36.01 -> string = "Ожирение"
        }
        return string
    }

}
