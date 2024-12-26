package com.example.idiomquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton: Button = findViewById(R.id.startButton)

        // 시작 버튼 클릭 리스너
        startButton.setOnClickListener {
            // QuizActivity로 전환
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // 현재 액티비티 종료
        }
    }
}
