package com.example.dm

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var TimeTextView: TextView
    private lateinit var QuestionTextView: TextView
    private lateinit var ScoreTextView: TextView
    private lateinit var AlertTextView: TextView
    private lateinit var FinalScoreTextView: TextView
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button

    private lateinit var countDownTimer: CountDownTimer
    private val random = Random
    private var a = 0
    private var b = 0
    private var indexOfCorrectAnswer = 0
    private val answers = ArrayList<Int>()
    private var points = 0
    private var totalQuestions = 0
    private lateinit var cals: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views after setContentView
        TimeTextView = findViewById(R.id.TimeTextView)
        QuestionTextView = findViewById(R.id.QuestionTextView)
        ScoreTextView = findViewById(R.id.ScoreTextView)
        AlertTextView = findViewById(R.id.AlertTextView)

        btn0 = findViewById(R.id.button0)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)

        cals = intent.getStringExtra("cals")!!

        start()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun start() {
        NextQuestion(cals)
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                TimeTextView.text = (p0 / 1000).toString() + "s"
            }

            override fun onFinish() {
                TimeTextView.text = "Time Up !"
                openDialog()
            }
        }.start()
    }

    private fun NextQuestion(cal: String) {
        a = random.nextInt(10)
        b = random.nextInt(10)
        QuestionTextView.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3) {
            if (indexOfCorrectAnswer == i) {
                when (cal) {
                    "+" -> answers.add(a + b)
                    "-" -> answers.add(a - b)
                    "*" -> answers.add(a * b)
                    "÷" -> {
                        if (b != 0) answers.add(a / b)
                        else answers.add(0) // Or handle this case differently
                    }
                }
            } else {
                var wrongAnswer = random.nextInt(20) + 1
                while (wrongAnswer == a + b || wrongAnswer == a - b || wrongAnswer == a * b || wrongAnswer == a / b) {
                    wrongAnswer = random.nextInt(20) + 1
                }
                answers.add(wrongAnswer)
            }
        }

        btn0.text = "${answers[0]}"
        btn1.text = "${answers[1]}"
        btn2.text = "${answers[2]}"
        btn3.text = "${answers[3]}"

        // Set tags for buttons
        btn0.tag = 0
        btn1.tag = 1
        btn2.tag = 2
        btn3.tag = 3
    }

    fun optionSelect(view: View?) {
        totalQuestions++
        if (indexOfCorrectAnswer.toString() == view?.tag.toString()) {
            points++
            AlertTextView.text = "Correct"
        } else {
            AlertTextView.text = "Wrong"
        }
        ScoreTextView.text = "$points/$totalQuestions"
        NextQuestion(cals)
    }

    fun PlayAgain(view: View?) {
        points = 0
        totalQuestions = 0
        ScoreTextView.text = "$points/$totalQuestions"
        countDownTimer.start()
    }

    private fun openDialog() {
        val inflate = LayoutInflater.from(this)
        val winDialog = inflate.inflate(R.layout.win_layout, null)
        FinalScoreTextView = winDialog.findViewById(R.id.FinalScoreTextView)
        val btnPlayAgain = winDialog.findViewById<Button>(R.id.buttonPlayAgain)
        val btnBack = winDialog.findViewById<Button>(R.id.btnBack)
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setView(winDialog)
        FinalScoreTextView.text = "$points/$totalQuestions"
        btnPlayAgain.setOnClickListener { PlayAgain(it) }
        btnBack.setOnClickListener { onBackPressed() }
        val showDialog = dialog.create()
        showDialog.show()
    }
}
