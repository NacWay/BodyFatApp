package com.malov.bodyfat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider


class cal : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cal, container, false)
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCloseFr : Button = view.findViewById(R.id.btnClose)

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast, view?.findViewById(R.id.toastShape))

        val noticeGroupWork : ImageButton = view.findViewById(R.id.noticeGroupWork)
        noticeGroupWork.setOnClickListener{
            val builder = AlertDialog.Builder(view.context)
            val res = view.context.resources
            builder.setCancelable(true)
                .setMessage(res.getString(R.string.explanationForWorkGroup))
                .setCancelable(true)
                .setPositiveButton("Понятно"){ dialog, _ ->
                    dialog.cancel()
                }
            builder.show()
        }

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

        val radioGroup : RadioGroup = view.findViewById(R.id.radioGroup)
        val radioText : TextView = view.findViewById(R.id.valuetextGroupWork)

        val radioGroupSex : RadioGroup = view.findViewById(R.id.radioGroupSex)

        var old : Int = 40
        var height : Int = 130
        var weight : Int = 60
        var groupWork : Int = 5
        var sex : Int = 1

        fun upDate(){
            old = doSlide(sliderOld, textOld)
            height = doSlide(sliderHeight, textHeight)
            weight = doSlide(sliderWeight, textWeight)
            radioGroup.clearCheck()
            radioGroup.setOnCheckedChangeListener{ group, checkedId ->
                group.findViewById<RadioButton>(checkedId)?.apply {
                    radioText.text = text
                    when{
                        text == "I" -> groupWork = 1
                        text == "II" -> groupWork = 2
                        text == "III" -> groupWork = 3
                        text == "IV" -> groupWork = 4
                        text == "V" -> groupWork = 5
                    }
                }
            }
            radioGroupSex.clearCheck()
            radioGroupSex.setOnCheckedChangeListener{ group, checkedId ->
                group.findViewById<RadioButton>(checkedId)?.apply {
                    if (text == "ЖЕН") sex = 1 else sex = 2
                }
            }
        }
        upDate()

        button.setOnClickListener{
            upDate()
            val builder = AlertDialog.Builder(view.context)
            val dialogLayout = inflater.inflate(R.layout.dialogcal, view?.findViewById(R.id.dialogShape))

            val proteinCcalText : TextView = dialogLayout.findViewById(R.id.proteinCcalText)
            val proteinGramText : TextView = dialogLayout.findViewById(R.id.proteinGramText)
            val proteinFitoText : TextView = dialogLayout.findViewById(R.id.proteinFitoText)

            val fatsCcalText : TextView = dialogLayout.findViewById(R.id.fatsCcalText)
            val fatsGramText : TextView = dialogLayout.findViewById(R.id.fatsGramText)

            val carboCcalText : TextView = dialogLayout.findViewById(R.id.carboCcalText)
            val carboGramText : TextView = dialogLayout.findViewById(R.id.carboGramText)

            val allCcalText : TextView = dialogLayout.findViewById(R.id.allCcalText)
            val allGramText : TextView = dialogLayout.findViewById(R.id.allGramText)

            val VOO : TextView = dialogLayout.findViewById(R.id.VOO)
            val SDDP : TextView = dialogLayout.findViewById(R.id.SDDP)

            val resSet : ArrayList<Double> = calculate(weight, height, old, groupWork, sex)
            proteinCcalText.text = "${(resSet[0]*4).toInt()} ккал"
            proteinGramText.text = "${(resSet[0]).toInt()} грамм"
            proteinFitoText.text = "(В том числе растительные: ${(resSet[1]*4).toInt()} ккал / ${(resSet[1]).toInt()} грамм)"

            fatsCcalText.text = "${(resSet[2]*9).toInt()} ккал"
            fatsGramText.text = "${(resSet[2]).toInt()} грамм"

            carboCcalText.text = "${(resSet[3]*4).toInt()} ккал"
            carboGramText.text = "${(resSet[3]).toInt()} грамм"

            allCcalText.text = "${(resSet[4]).toInt()} ккал"
            allGramText.text = "${(resSet[0]+resSet[2]+resSet[3]).toInt()} грамм"

            VOO.text = "Расчеты величины основного обмена (ВОО), по формуле Харриса-Бенедикта: ${(resSet[5]).toInt()} ккал"
            SDDP.text = "СДДП(Специфическое динамическое действие пищи): ${(resSet[6]).toInt()} ккал"

            builder
                .setView(dialogLayout)
                .show()

            /*
            proteinCcalText.text = "Белки: ${resSet[0]}грамм/${resSet[0]*4}ккал;\n" +
                    "В том числе растительные: ${resSet[1]}грамм/${resSet[1]*4}ккал;\n" +
                    "Жиры: ${resSet[2]}грамм/${resSet[0]*9}ккал;\n" +
                    "Углеводы: ${resSet[3]}грамм/${resSet[3]*4}ккал;\n" +
                    "Всего грамм: ${resSet[0]+resSet[2]+resSet[3]}грамм;\n"+
                    "Всего коллорий: ${resSet[4]}ккал;\n"+
                    "Расчеты величины основного обмена (ВОО), по формуле Харриса-Бенедикта: ${resSet[5]}ккал\n"+
                    "СДДП(Специфическое динамическое действие пищи): ${resSet[6]}ккал\n"

             */

        }
    }

    private fun calculate(weight: Int, height: Int, old: Int, groupWork: Int, sex: Int) : ArrayList<Double> {
        //list[0] = белки(общ), list[1] = белки растительные в т.ч(белки общ) list[2] = жиры, list[3] = углеводы, list[4] = всего ккал, list[5]= ВОО, list[6] = СДДП
        val listOfNutriens = ArrayList<Double>()
        val VOO : Double = 655.1+(9.6*weight)+(1.9*height)-(4.7*old)
        val SDDP : Double = VOO*0.1
        when{
            sex == 1 -> when{
                groupWork == 1 ->
                    if (old in 18..29) {     listOfNutriens.add(64.0);listOfNutriens.add(34.0);listOfNutriens.add(67.0);listOfNutriens.add(289.0)}
                    else if(old in 30..39) { listOfNutriens.add(59.0);listOfNutriens.add(33.0);listOfNutriens.add(63.0);listOfNutriens.add(274.0)}
                    else if(old in 40..59) { listOfNutriens.add(58.0);listOfNutriens.add(32.0);listOfNutriens.add(60.0);listOfNutriens.add(257.0)}
                groupWork == 2->
                    if (old in 18..29) {     listOfNutriens.add(66.0);listOfNutriens.add(36.0);listOfNutriens.add(73.0);listOfNutriens.add(318.0)}
                    else if(old in 30..39) { listOfNutriens.add(65.0);listOfNutriens.add(36.0);listOfNutriens.add(72.0);listOfNutriens.add(311.0)}
                    else if(old in 40..59) { listOfNutriens.add(63.0);listOfNutriens.add(35.0);listOfNutriens.add(70.0);listOfNutriens.add(305.0)}
                groupWork == 3->
                    if (old in 18..29) {     listOfNutriens.add(76.0);listOfNutriens.add(42.0);listOfNutriens.add(73.0);listOfNutriens.add(378.0)}
                    else if(old in 30..39) { listOfNutriens.add(74.0);listOfNutriens.add(41.0);listOfNutriens.add(72.0);listOfNutriens.add(372.0)}
                    else if(old in 40..59) { listOfNutriens.add(72.0);listOfNutriens.add(40.0);listOfNutriens.add(70.0);listOfNutriens.add(366.0)}
                groupWork == 4->
                    if (old in 18..29) {     listOfNutriens.add(87.0);listOfNutriens.add(48.0);listOfNutriens.add(87.0);listOfNutriens.add(452.0)}
                    else if(old in 30..39) { listOfNutriens.add(84.0);listOfNutriens.add(46.0);listOfNutriens.add(85.0);listOfNutriens.add(432.0)}
                    else if(old in 40..59) { listOfNutriens.add(82.0);listOfNutriens.add(45.0);listOfNutriens.add(83.0);listOfNutriens.add(417.0)}
                groupWork == 5->
                    if (old in 18..29) {     listOfNutriens.add(87.0);listOfNutriens.add(48.0);listOfNutriens.add(87.0);listOfNutriens.add(452.0)}
                    else if(old in 30..39) { listOfNutriens.add(84.0);listOfNutriens.add(46.0);listOfNutriens.add(85.0);listOfNutriens.add(432.0)}
                    else if(old in 40..59) { listOfNutriens.add(82.0);listOfNutriens.add(45.0);listOfNutriens.add(83.0);listOfNutriens.add(417.0)}
                }
            sex == 2 -> when{
                groupWork == 1 ->
                    if (old in 18..29) {     listOfNutriens.add(72.0);listOfNutriens.add(40.0);listOfNutriens.add(81.0);listOfNutriens.add(358.0)}
                    else if(old in 30..39) { listOfNutriens.add(68.0);listOfNutriens.add(37.0);listOfNutriens.add(77.0);listOfNutriens.add(335.0)}
                    else if(old in 40..59) { listOfNutriens.add(65.0);listOfNutriens.add(36.0);listOfNutriens.add(70.0);listOfNutriens.add(303.0)}
                groupWork == 2->
                    if (old in 18..29) {     listOfNutriens.add(80.0);listOfNutriens.add(44.0);listOfNutriens.add(93.0);listOfNutriens.add(411.0)}
                    else if(old in 30..39) { listOfNutriens.add(77.0);listOfNutriens.add(42.0);listOfNutriens.add(88.0);listOfNutriens.add(387.0)}
                    else if(old in 40..59) { listOfNutriens.add(72.0);listOfNutriens.add(40.0);listOfNutriens.add(83.0);listOfNutriens.add(366.0)}
                groupWork == 3->
                    if (old in 18..29) {     listOfNutriens.add(94.0);listOfNutriens.add(52.0);listOfNutriens.add(110.0);listOfNutriens.add(484.0)}
                    else if(old in 30..39) { listOfNutriens.add(89.0);listOfNutriens.add(49.0);listOfNutriens.add(105.0);listOfNutriens.add(462.0)}
                    else if(old in 40..59) { listOfNutriens.add(84.0);listOfNutriens.add(46.0);listOfNutriens.add(98.0);listOfNutriens.add(432.0)}
                groupWork == 4->
                    if (old in 18..29) {     listOfNutriens.add(108.0);listOfNutriens.add(59.0);listOfNutriens.add(128.0);listOfNutriens.add(566.0)}
                    else if(old in 30..39) { listOfNutriens.add(102.0);listOfNutriens.add(56.0);listOfNutriens.add(120.0);listOfNutriens.add(528.0)}
                    else if(old in 40..59) { listOfNutriens.add(96.0);listOfNutriens.add(53.0);listOfNutriens.add(113.0);listOfNutriens.add(499.0)}
                groupWork == 5->
                    if (old in 18..29) {     listOfNutriens.add(117.0);listOfNutriens.add(64.0);listOfNutriens.add(154.0);listOfNutriens.add(586.0)}
                    else if(old in 30..39) { listOfNutriens.add(111.0);listOfNutriens.add(61.0);listOfNutriens.add(144.0);listOfNutriens.add(550.0)}
                    else if(old in 40..59) { listOfNutriens.add(104.0);listOfNutriens.add(57.0);listOfNutriens.add(137.0);listOfNutriens.add(524.0)}
            }
            }
        listOfNutriens.add(listOfNutriens[0]*4 + listOfNutriens[2]*9 + listOfNutriens[3]*4 +SDDP)
        listOfNutriens.add(VOO)
        listOfNutriens.add(SDDP)
        return listOfNutriens
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
                startActivity(Intent(view?.context, activity_2::class.java))
                getActivity()?.overridePendingTransition(R.anim.nullanim, R.anim.nullanim)
            }, 200
        )
    }
}