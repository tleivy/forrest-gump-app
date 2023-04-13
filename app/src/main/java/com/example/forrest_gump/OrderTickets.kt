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
        val calendarListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            dateButton.text = "${dayOfMonth}/${month}/${year}"
        }

        val datePickerDialog = DatePickerDialog(
            this,
            calendarListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        dateButton.setOnClickListener {
            datePickerDialog.show()
        }

        val mainActivity = Intent(this, MainActivity::class.java)
        val checkoutDialog: AlertDialog.Builder = AlertDialog.Builder(this)

        val checkoutButton = findViewById<Button>(R.id.checkout_btn)
        checkoutButton.setOnClickListener {
            if (dateButton.text.equals(resources.getString(R.string.pick_a_date))) {
                datePickerDialog.show()
            } else popCheckoutAlert(checkoutDialog, mainActivity, adultNum, childNum)
        }
    }

    private fun popCheckoutAlert(
        dialog: AlertDialog.Builder, mainActivity: Intent,
        adultNum: Int, childNum: Int
    ) {
        /** This function pops a checkout dialog based on the order details. */

        val total = adultNum * 5 + childNum * 2
        val orderSummery = resources.getString(R.string.order_summery)
        val adults = resources.getString(R.string.adults)
        val children = resources.getString(R.string.children)
        val totalOf = resources.getString(R.string.total_of)
        val currency = resources.getString(R.string.currency)
        val orderConfirmation = resources.getString(R.string.conf_order)

        val checkoutMsg = "${orderSummery}:\n" +
                "${adultNum} ${adults}, ${childNum} ${children}\n" +
                "${totalOf}: ${total}${currency}.\n" +
                "${orderConfirmation}."

        val thanksDialog: AlertDialog.Builder = AlertDialog.Builder(this)

        dialog.apply {
            setTitle(R.string.checkout_title)
            setMessage(checkoutMsg)
            setCancelable(false)
            setIcon(R.drawable.check_icon)

            setPositiveButton(R.string.yes) { _, _ ->

                thanksDialog.apply {
                    setTitle(R.string.thanks)
                    setMessage("")
                    setCancelable(false)
                    setIcon(R.drawable.check_icon)
                    setPositiveButton(R.string.ok) { _, _ -> startActivity(mainActivity) }
                    thanksDialog.create().show()

                }

            }
            setNegativeButton(R.string.no) { _, _ -> } // Do nothing, return to order

            dialog.create().show()
        }

    }
}