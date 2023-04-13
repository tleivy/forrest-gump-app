package com.example.forrest_gump

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_acivity)

        val rotateAnim = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.rotate
        )
        val slideOutAnim = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_out
        )
        val welcomeTitle = findViewById<TextView>(R.id.welcome_title)

        rotateAnim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                welcomeTitle.startAnimation(slideOutAnim)
            }
            override fun onAnimationStart(animation: Animation) {
                // Do nothing
            }
            override fun onAnimationRepeat(animation: Animation) {
                // Do nothing
            }
        })

        slideOutAnim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                welcomeTitle.text = ""
            }
            override fun onAnimationStart(animation: Animation) {
                // Do nothing
            }
            override fun onAnimationRepeat(animation: Animation) {
                // Do nothing
            }
        })

        // Start animations chain
        val animThread = HandlerThread("animThread")
        animThread.start()
        Handler(animThread.looper).postDelayed({
            welcomeTitle.startAnimation(rotateAnim)
        }, 1000) // Delay for 1 second

        // Start the MainActivity after 4.6 seconds
        val mainActivityThread = HandlerThread("mainActivityThread")
        mainActivityThread.start()
        Handler(animThread.looper).postDelayed({
            val nextActivity = Intent(this, MainActivity::class.java)
            startActivity(nextActivity)
            finish()
        }, 2000) // Delay for 2 seconds
    }
}