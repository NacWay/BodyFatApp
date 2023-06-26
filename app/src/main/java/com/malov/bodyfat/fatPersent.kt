package com.malov.bodyfat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment


class fatPersent : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fat_persent, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val femalebtn : Button = view.findViewById(R.id.femalebtn)
        val malebtn : Button = view.findViewById(R.id.malebtn)
        val frame_layout : FrameLayout = view.findViewById(R.id.frame_layout)
        val btnCloseFr : Button = view.findViewById(R.id.btnClose)
        val fat_forFemale = fatForFemale()
        val fat_forMale =fatForMale()
        setFragment(fat_forFemale)

            femalebtn.setOnClickListener{
                setFragment(fat_forFemale)
            }
            malebtn.setOnClickListener{
                setFragment(fat_forMale)
            }
        /*btnCloseFr.setOnClickListener{
            doCloseFragment()
        }

         */

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


    }
    fun setFragment(fr: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fr)
            commit()
        }
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

    override fun onPause() {
        super.onPause()
    }
}