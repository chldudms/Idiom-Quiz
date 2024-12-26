package com.example.idiomquiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

// 사자성어와 설명을 저장하는 데이터 클래스
data class Idiom(val answer: String, val q: String)

class MainActivity : AppCompatActivity() {

    // 사자성어 리스트
    private val idioms = listOf(
        Idiom("지피지기", "상대방과 자신을 알면 백전백승이다."),
        Idiom("이심전심", "마음에서 마음으로 전하는 것."),
        Idiom("가장자리", "모든 일의 시작이나 끝."),
        Idiom("사필귀정", "모든 일은 결국 바른길로 돌아간다."),
        Idiom("전념불망", "공부나 일에 열중하여 몰두함."),
        Idiom("맹신고명", "무언가를 절대적으로 신뢰함.")
    )

    private var currentQIndex = 0  // 현재 문제의 인덱스
        private var correct = 0   // 맞춘 문제 개수
    private var isAnswer = false   // 정답 확인 여부를 추적하는 변수

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ui 요소와 연결
        val question: TextView = findViewById(R.id.question)  // 사자성어 설명 표시
        val card : LinearLayout = findViewById(R.id.card)
        val editText: EditText = findViewById(R.id.EditText)            // 정답 입력 필드
        val checkButton: Button = findViewById(R.id.checkButton)                    // 제출/다음 버튼
        val result: TextView = findViewById(R.id.result) // 결과 표시 텍스트
        val restartButton: Button = findViewById(R.id.restartButton)                // 다시하기 버튼 (처음에는 보이지 않음)

        restartButton.visibility = Button.GONE  // 처음에 다시하기 버튼은 숨김


        // 첫 번째 문제를 화면에 표시
        showNewQuiz(question, editText, result, checkButton)

        // 제출/다음 버튼 클릭 리스너
        checkButton.setOnClickListener {
            if (!isAnswer) {
                // 제출 버튼 눌렸을 때
                val userAnswer = editText.text.toString()
                if (checkAnswer(userAnswer)) {
                    result.text = "정답입니다!"
                    result.setTextColor(0xFF00FF00.toInt())  // 정답일 때 초록색 (Green)
                    correct++  // 맞춘 문제 개수 증가
                } else {
                    result.text = "틀렸습니다. 정답: ${idioms[currentQIndex].answer}"
                    result.setTextColor(0xFFFF0000.toInt())  // 오답일 때 빨간색
                }
                checkButton.text = "다음 문제"
                isAnswer = true
            } else {
                // 다음 문제로 넘어가기
                currentQIndex++
                if (currentQIndex < idioms.size) {
                    showNewQuiz(question, editText, result, checkButton)
                    editText.text.clear()
                    checkButton.text = "제출"
                    isAnswer = false
                } else {
                    // 퀴즈가 끝났을 때
                    question.visibility = TextView.GONE
                    editText.visibility = EditText.GONE
                    card.visibility = LinearLayout.GONE
                    result.text = "퀴즈가 끝났습니다! 맞춘 개수: $correct/${idioms.size}"
                    result.setTextColor(0xFF000000.toInt())  // 결과는 검은색으로
                    checkButton.visibility = Button.GONE  // 제출/다음 버튼 숨김
                    restartButton.visibility = Button.VISIBLE  // 다시하기 버튼 표시
                }
            }
        }

        // 다시하기 버튼 클릭 리스너: 퀴즈를 처음부터 다시 시작
        restartButton.setOnClickListener {
            currentQIndex = 0
            correct = 0
            checkButton.visibility = Button.VISIBLE
            restartButton.visibility = Button.GONE
            question.visibility = TextView.VISIBLE //문제, 입력창, 카드 보이기
            editText.visibility = EditText.VISIBLE
            card.visibility = LinearLayout.VISIBLE
            showNewQuiz(question, editText, result, checkButton)
            editText.text.clear()
            checkButton.text = "제출"
            isAnswer = false
        }
    }

    // 새로운 문제를 화면에 표시하는 함수
    private fun showNewQuiz(
        explanationTextView: TextView,
        answerEditText: EditText,
        resultTextView: TextView,
        checkButton: Button
    ) {
        resultTextView.text = ""  // 결과 텍스트 초기화
        val currentIdiom = idioms[currentQIndex]
        explanationTextView.text = currentIdiom.q  // 설명을 텍스트뷰에 설정
    }

    // 사용자가 입력한 답이 정답인지 확인하는 함수
    private fun checkAnswer(userAnswer: String): Boolean {
        return userAnswer.trim() == idioms[currentQIndex].answer.trim()  // 공백을 제거하고 비교
    }
}
