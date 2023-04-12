package com.example.forrest_gump

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ticketsBtn = findViewById<Button>(R.id.tickets_btn)
        fun animateTicketsBtn() {
            ticketsBtn.animate().rotationX(18F).setDuration(3000).withEndAction {
                ticketsBtn.animate().rotationX(-18F).setDuration(3000).withEndAction {
                    animateTicketsBtn()
                }
            }
        }
        animateTicketsBtn()

        ticketsBtn.setOnClickListener {
            val orderTicketsActivity = Intent(this, OrderTickets::class.java)
            startActivity(orderTicketsActivity)
        }
    }
}