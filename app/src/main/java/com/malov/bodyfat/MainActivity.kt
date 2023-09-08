package com.malov.bodyfat

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //FirebaseApp.initializeApp(this@MainActivity)
        firebaseAnalytics = Firebase.analytics

        database = Firebase.database.reference

        askNotificationPermission()

        val text: TextView = findViewById(R.id.textView)
        text.visibility = GONE

        prefs = this.getSharedPreferences("name", Context.MODE_APPEND)

        val enterName: EditText = findViewById(R.id.enterName)
        enterName.visibility = GONE

        val btnContinue: Button = findViewById(R.id.buttonContinue)
        btnContinue.visibility = GONE

        val welcomeText: ImageView = findViewById(R.id.welcomeText)
        val anim = welcomeText.animate()
            .translationY(-500F)
            .setDuration(1200)
        anim.start()


        anim.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator) {
                if (prefs.getString("name", "12") == "12") {
                    enterName.visibility = VISIBLE
                    btnContinue.visibility = VISIBLE
                } else {
                    text.visibility = VISIBLE
                    goSecondActvt()
                }
            }
            override fun onAnimationCancel(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(animation: Animator) {
                TODO("Not yet implemented")
            }
        })

        enterName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val editPref: SharedPreferences.Editor = prefs.edit()
                editPref.putString("name", "${enterName.text} ")
                btnContinue.setOnClickListener {
                    editPref.commit()
                    Toast.makeText(
                        this@MainActivity,
                        "Привет, ${prefs.getString("name", "12")}",
                        Toast.LENGTH_SHORT
                    ).show()
                    if(isConnected) {
                        database.push().setValue("${prefs.getString("name", "12")}")
                        getDataFromDb()
                    } else {
                        Toast.makeText(this@MainActivity, "No Internet connection", Toast.LENGTH_LONG).show()
                    }
                    goSecondActvt()
                }
            }
        })
        text.setText(prefs.getString("name", "12"))
    }

    fun goSecondActvt(){
        Handler(Looper.getMainLooper()).postDelayed(        //немного замедляем поток чтобы показать начальную анимацию
            {
                startActivity(Intent(applicationContext, Activity_2::class.java))

                overridePendingTransition(
                    R.anim.change_acrivity2,
                    R.anim.change_acrivity1
                )
                finish()  // запрещаем обратный переход к этой активити после перехода ко 2активити
            },
            700 // value in milliseconds
        )
    }

    // Запрос разрешения на показ уведомлений
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            )
            else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val Context.isConnected: Boolean
        get() {
            return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

    //read database
    fun getDataFromDb(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               for (ds in dataSnapshot.children.reversed()){
                    Toast.makeText(this@MainActivity, ds.value.toString(), Toast.LENGTH_SHORT).show()
                   break
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, databaseError.toException().toString(), Toast.LENGTH_SHORT).show()
            }
        }
        database.addValueEventListener(postListener)
    }
}