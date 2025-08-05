package com.example.hesapmakinesi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var resultText: EditText
    private     var firstNumber = 0
    private var isNewNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        resultText = findViewById(R.id.resultText)
        resultText.setText("0")

        setupNumberButtons()
        setupOperationButtons()
    }

    private fun setupNumberButtons() {
        val numberIds = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberIds) {
            findViewById<Button>(id).setOnClickListener {
                val number = (it as Button).text.toString()
                if (isNewNumber) {
                    resultText.setText(number)
                    isNewNumber = false
                } else {
                    resultText.append(number)
                }
            }
        }
    }

    private fun setupOperationButtons() {
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            resultText.setText("0")
            firstNumber = 0
            isNewNumber = true
        }

        findViewById<Button>(R.id.btnToplama).setOnClickListener {
            firstNumber += resultText.text.toString().toInt()
            resultText.setText(firstNumber.toString())
            isNewNumber = true
        }
    }
}