package com.example.dm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val addButton = findViewById<View>(R.id.add)
        val multiButton = findViewById<View>(R.id.multi)
        val divideButton = findViewById<View>(R.id.divie)
        val subtractButton = findViewById<View>(R.id.sub)

        // Set click listeners for each button
        addButton.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "+")
            startActivity(calInt)
        }

        multiButton.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "*")
            startActivity(calInt)
        }

        divideButton.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "/")
            startActivity(calInt)
        }

        subtractButton.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)
            calInt.putExtra("cals", "-")
            startActivity(calInt)
        }
    }
}
