package com.example.forrest_gump

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import java.util.Calendar

class OrderTickets : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_tickets)

        // Update num of tickets
        var adultNum = 0
        var childNum = 0

        val adultsNumPicker = findViewById<NumberPicker>(R.id.adults_num)
        adultsNumPicker.maxValue = 10
        adultsNumPicker.minValue = 0

        val childrenNumPicker = findViewById<NumberPicker>(R.id.children_num)
        childrenNumPicker.maxValue = 10
        childrenNumPicker.minValue = 0

        val totalPrice = findViewById<TextView>(R.id.total_price)
        adultsNumPicker.setOnValueChangedListener { _, _, newVal ->
            adultNum = newVal
            totalPrice.text = "${adultNum * 5 + childNum * 2}"
        }
        childrenNumPicker.setOnValueChangedListener { _, _, newVal ->
            childNum = newVal
            totalPrice.text = "${adultNum * 5 + childNum * 2}"
        }

        // Pick a date
        val dateButton = findViewById<TextView>(R.id.date_picker)

        val calendar = Calendar.getInstance()
        val calendarListener = DatePickerDialog.OnDateSetListener {view, year, month, dayOfMonth ->
            dateButton.text = "${dayOfMonth}/${month}/${year}"
        }

        val datePickerDialog = DatePickerDialog(
            this,
            calendarListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )

        dateButton.setOnClickListener {
            datePickerDialog.show()
        }

        val mainActivity = Intent(this, MainActivity::class.java)
        val checkoutDialog: AlertDialog.Builder = AlertDialog.Builder(this)

        checkoutDialog.apply {
            setTitle(R.string.checkout_title)
            setCancelable(false)
            setIcon(R.drawable.check_icon)
            setPositiveButton(R.string.yes) { _, _ -> startActivity(mainActivity) }
            setNegativeButton(R.string.no) { _, _ -> } // Do nothing, return to order
        }

        val checkoutButton = findViewById<Button>(R.id.checkout_btn)
        checkoutButton.setOnClickListener {
            if (dateButton.text.equals(R.string.pick_a_date)) {
                println(dateButton.text)
                println(R.string.pick_a_date)

                datePickerDialog.show()
            } else checkoutDialog.create().show()
        }
    }

}