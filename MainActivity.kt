package com.example.myapplication
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private var currentInput: String = ""
    private var operator: String? = null
    private var firstOperand: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Set up the TextView for display
        textView = findViewById(R.id.textView)

        // Set up GridLayout and apply window insets for padding adjustment
        val buttonLayout = findViewById<GridLayout>(R.id.buttonLayout)
        ViewCompat.setOnApplyWindowInsetsListener(buttonLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up click listeners for all buttons
        setupButtonClickListeners()
    }

    private fun enableEdgeToEdge() {
        // Implement edge-to-edge functionality here if needed
        // This method can be extended to handle edge-to-edge features
    }

    private fun setupButtonClickListeners() {
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonEquals,
            R.id.buttonClear
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (val text = button.text.toString()) {
            "C" -> {
                currentInput = ""
                operator = null
                firstOperand = null
                textView.text = "0"
            }
            "=" -> {
                if (firstOperand != null && operator != null && currentInput.isNotEmpty()) {
                    val result = performCalculation(firstOperand!!.toDouble(), currentInput.toDouble(), operator!!)
                    textView.text = result.toString()
                    currentInput = result.toString()
                    operator = null
                    firstOperand = null
                }
            }
            "+", "-", "*", "/" -> {
                if (currentInput.isNotEmpty()) {
                    firstOperand = currentInput
                    operator = text
                    currentInput = ""
                }
            }
            else -> {
                currentInput += text
                textView.text = currentInput
            }
        }
    }

    private fun performCalculation(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
            else -> Double.NaN
        }
    }
}

