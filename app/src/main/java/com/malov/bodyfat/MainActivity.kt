package com.malov.bodyfat

import android.animation.Animator
import android.annotation.SuppressLint
import android.icu.util.ULocale.getName
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.malov.bodyfat.DataBase.DataBase

class MainActivity : AppCompatActivity() {
    private var name : String = ""

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterName : EditText = findViewById(R.id.enterName)
        enterName.visibility = GONE

        val btnContinue : Button = findViewById(R.id.buttonContinue)
        btnContinue.visibility = GONE

        val welcomeText : ImageView = findViewById(R.id.welcomeText)
        val anim = welcomeText.animate()
            .translationY(-800F)
            .setDuration(2000)
     //   enterName.visibility = VISIBLE
        anim.start()

        anim.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { TODO("Not yet implemented") }
            override fun onAnimationEnd(animation: Animator) {
                if(name=="") {
                    enterName.visibility = VISIBLE
                    btnContinue.visibility = VISIBLE
                }
            }
            override fun onAnimationCancel(animation: Animator) { TODO("Not yet implemented") }
            override fun onAnimationRepeat(animation: Animator) { TODO("Not yet implemented") }
        })

        enterName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                name = enterName.text.toString()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = enterName.text.toString()
            }
        })

       btnContinue.setOnClickListener{
           enterName.setText("$name+1")
       }

    }



    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}