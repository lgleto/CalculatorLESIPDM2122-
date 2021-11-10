package ipca.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.text.isDigitsOnly

enum class Operator {
    add,
    divide,
    subtract,
    multiply
}

class MainActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var textViewDisplay : TextView

    var itemCalcs : MutableList<ItemCalc> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDisplay = findViewById<TextView>(R.id.textViewDisplay)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val button0 = findViewById<Button>(R.id.button0)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        val buttonPlus     = findViewById<Button>(R.id.buttonPlus    )
        val buttonMinus    = findViewById<Button>(R.id.buttonMinus   )
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide   = findViewById<Button>(R.id.buttonDivide  )
        val buttonEqual    = findViewById<Button>(R.id.buttonEqual   )


        findViewById<Button>(R.id.buttonAC).setOnClickListener {
            textViewDisplay.text = "0"
        }

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        button0.setOnClickListener(this)
        buttonDot.setOnClickListener(this)
        buttonPlus    .setOnClickListener(this)
        buttonMinus   .setOnClickListener(this)
        buttonMultiply.setOnClickListener(this)
        buttonDivide  .setOnClickListener(this)
        buttonEqual   .setOnClickListener {
            //doOperation()
        }
    }



    override fun onClick(view: View?) {
        val buttonPressed = view as Button

        val textPressed = buttonPressed.text.toString()
        if (textPressed.isDigitsOnly()){
            itemCalcs.lastOrNull()?.let{
                if (it is ItemNumber){
                    val newValue  = "${it.valueNumber}${textPressed}"
                    it.valueNumber = newValue.toInt()
                }
                else if (it is ItemOperator){
                    itemCalcs.add(ItemNumber(textPressed.toInt()))
                }
            }?:run{
                itemCalcs.add(ItemNumber(textPressed.toInt()))
            }
        }else {
            itemCalcs.lastOrNull()?.let{
                if (it is ItemNumber){
                    itemCalcs.add(ItemOperator(textPressed))
                }

                if (it is ItemOperator){
                    it.value = textPressed
                }
            }
        }

        var display = ""
        var result = 0
        var index = 0
        var lastOp : ItemOperator? = null
        for(i in itemCalcs){
            display += i.value
            if (index == 0 ){
                result = (i as ItemNumber).valueNumber!!
                index ++
                continue
            }
            if (index == 1){
                lastOp = (i as ItemOperator)
                index ++
                continue
            }
            if (i is ItemNumber){
                if( lastOp?.value == "+") result += i.valueNumber?:0
                if( lastOp?.value == "-") result -= i.valueNumber?:0
                if( lastOp?.value == "*") result *= i.valueNumber?:0
                if( lastOp?.value == "/") result /= i.valueNumber?:0
                index ++
                continue
            }
            if (i is ItemOperator){
                lastOp = (i as ItemOperator)
                index ++
                continue
            }

        }

        textViewDisplay.text = display
        findViewById<TextView>(R.id.textViewResult).text = "$result"

    }
}