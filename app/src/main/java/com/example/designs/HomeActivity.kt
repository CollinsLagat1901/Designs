package com.example.designs

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    private lateinit var floorSpinner: Spinner
    private lateinit var wingSpinner: Spinner
    private lateinit var roomSpinner: Spinner
    private lateinit var membersTextView: TextView
    private lateinit var paymentMethodSpinner: Spinner
    private lateinit var subscriptionDurationSpinner: Spinner

    private val floors = arrayOf("Floor 1", "Floor 2", "Floor 3")
    private val wings = arrayOf("Left Wing", "Right Wing")
    private val leftWingRooms = Array(5) { "L${it + 1}" }
    private val rightWingRooms = Array(5) { "R${it + 1}" }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        floorSpinner = findViewById(R.id.floor_spinner)
        wingSpinner = findViewById(R.id.wing_spinner)
        roomSpinner = findViewById(R.id.room_spinner)
        membersTextView = findViewById(R.id.members_text_view)
        paymentMethodSpinner = findViewById(R.id.payment_method_spinner)
        subscriptionDurationSpinner = findViewById(R.id.subscription_duration_spinner)

        setupFloorSpinner()

        floorSpinner.onItemSelectedListener = FloorSpinnerListener()
        wingSpinner.onItemSelectedListener = WingSpinnerListener()
        roomSpinner.onItemSelectedListener = RoomSpinnerListener()

        setupPaymentMethodSpinner()
        setupSubscriptionDurationSpinner()
    }

    private fun setupFloorSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, floors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floorSpinner.adapter = adapter
    }

    private fun setupWingSpinner(selectedFloor: String) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wings)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wingSpinner.adapter = adapter

        // Determine available wings based on selected floor
        if (selectedFloor == "Floor 1") {
            // Allow both wings on Floor 1
            wingSpinner.isEnabled = true
        } else {
            // For other floors, only allow Left Wing
            wingSpinner.setSelection(0)
            wingSpinner.isEnabled = false
        }
    }

    private fun setupRoomSpinner(selectedWing: String) {
        val rooms = if (selectedWing == "Left Wing") leftWingRooms else rightWingRooms
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rooms)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roomSpinner.adapter = adapter
    }

    private fun setupPaymentMethodSpinner() {
        val paymentMethods = arrayOf("Mpesa", "PayPal")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentMethodSpinner.adapter = adapter

        paymentMethodSpinner.onItemSelectedListener = PaymentMethodSpinnerListener()
    }

    private fun setupSubscriptionDurationSpinner() {
        val subscriptionDurations = arrayOf("9000 for 3 months", "3500 for 1 month")
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, subscriptionDurations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subscriptionDurationSpinner.adapter = adapter
    }

    inner class FloorSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedFloor = floors[position]
            setupWingSpinner(selectedFloor)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    inner class WingSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedWing = wings[position]
            setupRoomSpinner(selectedWing)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    inner class RoomSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Handle room selection here
            // For demonstration purposes, let's just display a toast message
            Toast.makeText(
                this@HomeActivity,
                "You are almost done. Please proceed with the payment.",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    inner class PaymentMethodSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Handle payment method selection here
            val selectedMethod = parent?.getItemAtPosition(position).toString()
            when (selectedMethod) {
                "Mpesa" -> {
                    // Display an alert message
                    showMpesaPrompt()
                }

                "PayPal" -> {
                    // Open PayPal website
                    openPayPalWebsite()
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        private fun showMpesaPrompt() {
            // Display an alert message for Mpesa payment
            // For demonstration purposes, let's just display a toast message
            Toast.makeText(
                this@HomeActivity,
                "You will receive a prompt shortly to enter your Mpesa PIN.",
                Toast.LENGTH_SHORT
            ).show()
            // You can implement your Mpesa payment logic here
        }

        private fun openPayPalWebsite() {
            // Open PayPal website
            val paypalUri = Uri.parse("https://www.paypal.com/ke/home")
            val paypalIntent = Intent(Intent.ACTION_VIEW, paypalUri)
            startActivity(paypalIntent)
        }
    }
}

