package com.example.hogwartsguide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class HouseListActivity : Activity() {
    companion object {
        private const val TAG = "HouseListActivity"
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_HOUSE = "EXTRA_HOUSE"
    }

    private val houses = listOf(
        House("gryffindor", R.string.gryffindor, R.string.gryffindor_motto, "🦁", R.drawable.bg_gryffindor),
        House("slytherin", R.string.slytherin, R.string.slytherin_motto, "🐍", R.drawable.bg_slytherin),
        House("ravenclaw", R.string.ravenclaw, R.string.ravenclaw_motto, "🦅", R.drawable.bg_ravenclaw),
        House("hufflepuff", R.string.hufflepuff, R.string.hufflepuff_motto, "🦡", R.drawable.bg_hufflepuff)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)
        Log.d(TAG, "onCreate: house list created")

        // Получаю имя с прошлого экрана и показываю его над списком факультетов.
        val name = intent.getStringExtra(EXTRA_NAME) ?: getString(R.string.welcome_default)
        findViewById<TextView>(R.id.tvWelcome).text = name.uppercase()
        val container = findViewById<LinearLayout>(R.id.houseContainer)
        val inflater = LayoutInflater.from(this)

        // Беру один шаблон карточки и заполняю его данными каждого факультета.
        houses.forEach { house ->
            val card = inflater.inflate(R.layout.item_house, container, false)
            card.findViewById<TextView>(R.id.tvHouseIcon).apply {
                text = house.icon
                setBackgroundResource(house.cardColor)
            }
            card.findViewById<TextView>(R.id.tvHouseName).setText(house.nameRes)
            card.findViewById<TextView>(R.id.tvHouseMotto).setText(house.mottoRes)
            card.setOnClickListener {
                Log.i(TAG, "House selected: ${house.key}")
                // Передаю имя и ключ выбранного факультета на экран с подробностями.
                val nextScreen = Intent(this, HouseDetailActivity::class.java)
                nextScreen.putExtra(EXTRA_NAME, name)
                nextScreen.putExtra(EXTRA_HOUSE, house.key)
                startActivity(nextScreen)
            }
            container.addView(card)
        }
    }

    // Так я вижу в Logcat, когда список факультетов становится активным или закрывается.
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: house list is visible") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: ready to choose a house") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: house list is losing focus") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: house list is no longer visible") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy: house list closed") }
}

// Для каждой карточки я храню название, значок и цвета вместе в одной модели.
private data class House(
    val key: String,
    val nameRes: Int,
    val mottoRes: Int,
    val icon: String,
    val cardColor: Int
)
