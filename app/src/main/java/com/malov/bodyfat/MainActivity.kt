package com.malov.bodyfat

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.malov.bodyfat.DataBase.DataBase


class MainActivity : AppCompatActivity() {
    private var dbHelper : DataBase = DataBase(this)
    private lateinit var prefs: SharedPreferences

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text : TextView = findViewById(R.id.textView)
        text.visibility = GONE

        prefs = this.getSharedPreferences("name", Context.MODE_APPEND)

        val enterName : EditText = findViewById(R.id.enterName)
        enterName.visibility = GONE

        val btnContinue : Button = findViewById(R.id.buttonContinue)
        btnContinue.visibility = GONE

        val welcomeText : ImageView = findViewById(R.id.welcomeText)
        val anim = welcomeText.animate()
            .translationY(-800F)
            .setDuration(1200)
        anim.start()


        anim.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { TODO("Not yet implemented") }
            override fun onAnimationEnd(animation: Animator) {
                if(prefs.getString("name", "12")==null) {
                    enterName.visibility = VISIBLE
                    btnContinue.visibility = VISIBLE
                } else {
                    text.visibility = VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            startActivity(Intent(applicationContext, activity_2::class.java))
                            overridePendingTransition(R.anim.rotate,R.anim.rotate);
                        },
                        700 // value in milliseconds
                    )


                }
            }
            override fun onAnimationCancel(animation: Animator) { TODO("Not yet implemented") }
            override fun onAnimationRepeat(animation: Animator) { TODO("Not yet implemented") }
        })

            enterName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val editPref: SharedPreferences.Editor = prefs.edit()
                    editPref.putString("name", "${enterName.text.toString()}  ")
                    btnContinue.setOnClickListener{
                        editPref.commit()
                        Toast.makeText( this@MainActivity,"Привет ${prefs.getString("name", "12")}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        text.setText(prefs.getString("name","12"))
    }



    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}