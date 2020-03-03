package com.example.test

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    var isLastNumeric: Boolean = false //Checks if the last character is a number
    var isLastPointer: Boolean = false //Checks if the last character is a pointer
    var stateError: Boolean = false //Error catching variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onNumber(view: View) { //Function of number input
        if (stateError) {
            textView.text = (view as Button).text
            stateError = false
        } else {
            textView.append((view as Button).text)
        }
        isLastNumeric = true
        if (isLastNumeric && !textView.text.endsWith("0"))
            answer.text = arithmetic().toString()
    }

    fun onDecimal(view: View) { //Function of pointer input
        if (!isLastPointer && !stateError) {
            if (!isLastNumeric) {
                textView.append("0")
            }
            textView.append((view as Button).text)
            isLastPointer = true
            isLastNumeric = false
        }
    }

    fun onOperator(view: View) { //Function of operator input
        if (isLastNumeric && !stateError) {
            textView.append((view as Button).text)
            isLastPointer = false
            isLastNumeric = false
        }
    }

    fun onClean(view: View) { //Function of clear
        textView.text = ""
        answer.text = ""
        isLastPointer = false
        isLastNumeric = false
        stateError = false
    }

    fun onBackspace(view: View) { //Function to delete the last character of a string
        textView.text = textView.text.toString().dropLast(1)
    }

    @SuppressLint("SetTextI18n")
    fun onResult(view: View) { //Output function
        if (isLastNumeric && !stateError) {
            try {
                textView.text = arithmetic().toString()
                answer.text = ""
                isLastPointer = true
            } catch (ex: ArithmeticException) {
                textView.text = "Error"
                stateError = true
                isLastNumeric = false
            }
        }
    }

    private fun arithmetic():Double { //Math function
        val txt = textView.text.toString()
        val expression = ExpressionBuilder(txt).build() //Library to convert text to equation
        return expression.evaluate() //Answer
    }
}
