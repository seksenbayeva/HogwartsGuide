package com.example.hogwartsguide

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class HogwartsQuizActivity : Activity() {
    companion object { private const val TAG = "HogwartsQuizActivity" }

    private lateinit var answerButtons: List<Button>
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Log.d(TAG, "onCreate: quiz screen created")

        resultText = findViewById(R.id.tvQuizResult)
        answerButtons = listOf(
            findViewById<Button>(R.id.btnAnswerAccio),
            findViewById<Button>(R.id.btnAnswerLumos),
            findViewById<Button>(R.id.btnAnswerReparo)
        )

        // Для каждого варианта я отмечаю, является ли он правильным ответом
        answerButtons.forEach { button ->
            button.setOnClickListener {
                val isCorrect = button.id == R.id.btnAnswerLumos
                Log.i(TAG, "Answer selected: ${button.text}; correct: $isCorrect")
                showResult(isCorrect)
            }
        }

        findViewById<Button>(R.id.btnTryAgain).setOnClickListener {
            Log.i(TAG, "Quiz restarted")
            resetQuiz()
        }
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            Log.i(TAG, "Returning from the quiz")
            finish()
        }
    }

    // После ответа я показываю результат и временно отключаю варианты
    private fun showResult(isCorrect: Boolean) {
        val message = if (isCorrect) R.string.quiz_correct else R.string.quiz_wrong
        resultText.setText(message)
        answerButtons.forEach { it.isEnabled = false }
    }

    // Кнопка повтора очищает результат и снова включает все варианты
    private fun resetQuiz() {
        resultText.text = ""
        answerButtons.forEach { it.isEnabled = true }
    }

    // По этим сообщениям я могу отследить жизненный цикл викторины в Logcat.
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: quiz is visible") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: ready to answer") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: quiz is losing focus") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: quiz is no longer visible") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy: quiz closed") }
}
