package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    var isLastNumeric: Boolean = false
    var isLastPointer: Boolean = false
    var stateError: Boolean = false
    var isOpenBkt: Boolean = false
    var isCloseBkt: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onNumber(view: View) {
        if (stateError) {
            textView.text = (view as Button).text
            stateError = false
        } else {
            textView.append((view as Button).text)
        }
        isLastNumeric = true
        isCloseBkt = false
        isOpenBkt = false
        if (isLastNumeric && !textView.text.endsWith("0"))
            answer.text = arifmetic().toString()
    }

    fun onDecimal(view: View) {
        if (!isLastPointer && !stateError) {
            if (!isLastNumeric) {
                textView.append("0")
            }
            textView.append((view as Button).text)
            isLastPointer = true
            isLastNumeric = false
            isCloseBkt = false
            isOpenBkt = false
        }
    }

    fun onOperator(view: View) {
        if (isLastNumeric && !stateError) {
            textView.append((view as Button).text)
            isLastPointer = false
            isLastNumeric = false
            isCloseBkt = false
            isOpenBkt = false
        }
    }

    fun onClean(view: View) {
        textView.text = ""
        answer.text = ""
        isLastPointer = false
        isLastNumeric = false
        isCloseBkt = false
        isOpenBkt = false
        stateError = false
    }

    fun onBackspace(view: View) {
        textView.text = textView.text.toString().dropLast(1)
    }

    fun onResult(view: View) {
        if (isLastNumeric && !stateError) {
            try {
                textView.text = arifmetic().toString()
                answer.text = ""
                isLastPointer = true
            } catch (ex: ArithmeticException) {
                textView.text = "Error"
                stateError = true
                isLastNumeric = false
            }
        }
    }

    fun arifmetic():Double {
        val txt = textView.text.toString()
        val expression = ExpressionBuilder(txt).build()
        return expression.evaluate()
    }
}
