package com.example.hogwartsguide

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class SpellbookActivity : Activity() {
    companion object { private const val TAG = "SpellbookActivity" }

//    override fun onCreate(savedInstanceState: Bundle?) — это функция, кот Android вызывает, когда создаёт экран Activity
//    - override означает, что класс экрана переопределяет стандартную функцию Android
//    - onCreate — её имя: здесь экран впервые настраивают
//    - savedInstanceState: Bundle? — данные, кот Android может передать, если экран восстанавливается после пересоздания. ? означает, что параметр иногда бывает пустым (null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spellbook)
        Log.d(TAG, "onCreate: spellbook screen created")

        val spellInfo = findViewById<TextView>(R.id.tvSpellInfo)

        // При нажатии показываю короткое объяснение выбранного заклинания
        findViewById<Button>(R.id.btnLumos).setOnClickListener {
            showSpell(spellInfo, R.string.spell_lumos, R.string.spell_lumos_info)
        }
        findViewById<Button>(R.id.btnAccio).setOnClickListener {
            showSpell(spellInfo, R.string.spell_accio, R.string.spell_accio_info)
        }
        findViewById<Button>(R.id.btnReparo).setOnClickListener {
            showSpell(spellInfo, R.string.spell_reparo, R.string.spell_reparo_info)
        }
        findViewById<Button>(R.id.btnWingardium).setOnClickListener {
            showSpell(spellInfo, R.string.spell_wingardium, R.string.spell_wingardium_info)
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            Log.i(TAG, "Returning from the spellbook")
            finish()
        }
    }

    //название и описание из strings.xml, чтобы все тексты были в ресурсах
    private fun showSpell(view: TextView, nameRes: Int, descriptionRes: Int) {
        val name = getString(nameRes)
        view.text = "$name\n\n${getString(descriptionRes)}"
        Log.i(TAG, "Spell selected: $name")
    }

    // По этим сообщениям могу отследить жизненный цикл экрана в Logcat
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: spellbook is visible") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: ready to browse spells") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: spellbook is losing focus") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: spellbook is no longer visible") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy: spellbook closed") }
}
