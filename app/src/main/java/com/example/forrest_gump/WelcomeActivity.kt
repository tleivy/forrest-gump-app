package com.example.forrest_gump

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_acivity)

        val rotateAnim = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.rotate
        )
        val slideIn = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_out
        )
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(rotateAnim)
        animationSet.addAnimation(slideIn)

        val animThread = HandlerThread("animThread")
        animThread.start()
        Handler(animThread.looper).postDelayed({
            findViewById<TextView>(R.id.welcome_title).startAnimation(animationSet)
        }, 2000) // Delay for 2 seconds


        // Start the MainActivity after 4.6 seconds
        val mainActivityThread = HandlerThread("mainActivityThread")
        mainActivityThread.start()
        Handler(animThread.looper).postDelayed({
            val nextActivity = Intent(this, MainActivity::class.java)
            startActivity(nextActivity)
            finish()
        }, 3300) // Delay for 3.3 seconds
    }
}