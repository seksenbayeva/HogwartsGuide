package com.example.hogwartsguide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : Activity() {
    companion object { private const val TAG = "MainActivity" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: start screen created")

        val nameInput = findViewById<EditText>(R.id.etName)
        // Если имя пустое, показываю подсказку и не открываю список факультетов
        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val name = nameInput.text.toString().trim()
            if (name.isBlank()) {
                Log.w(TAG, "Name is empty, so I cannot continue")
                Toast.makeText(this, R.string.name_error, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Явно указываю следующий экран и передаю туда введённое имя
            Log.i(TAG, "Opening the house list for: $name")
            val intent = Intent(this, HouseListActivity::class.java)
            intent.putExtra(HouseListActivity.EXTRA_NAME, name)
            startActivity(intent)
        }

        // Эти две кнопки открывают дополнительные разделы прямо с главного экрана
        findViewById<Button>(R.id.btnSpellbook).setOnClickListener {
            Log.i(TAG, "Opening the spellbook")
            startActivity(Intent(this, SpellbookActivity::class.java))
        }
        findViewById<Button>(R.id.btnQuiz).setOnClickListener {
            Log.i(TAG, "Opening the Hogwarts quiz")
            startActivity(Intent(this, HogwartsQuizActivity::class.java))
        }
    }

    // Здесь записываю, когда главный экран появляется, сворачивается и закрывается.
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: start screen is visible") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: ready for name input") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: start screen is losing focus") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: start screen is no longer visible") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy: start screen closed") }
}
