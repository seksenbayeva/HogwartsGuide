package com.example.hogwartsguide

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class HouseDetailActivity : Activity() {
    companion object { private const val TAG = "HouseDetailActivity" }

    // В этой модели я храню всё, что нужно показать про один факультет
    private data class HouseInfo(
        val name: Int, val about: Int, val traits: Int, val founder: Int,
        val mascot: Int, val commonRoom: Int, val icon: String, val color: Int
    )

    private val details = mapOf(
        "gryffindor" to HouseInfo(R.string.gryffindor, R.string.about_gryffindor, R.string.traits_gryffindor, R.string.founder_gryffindor, R.string.mascot_gryffindor, R.string.room_gryffindor, "🦁", R.drawable.bg_gryffindor),
        "slytherin" to HouseInfo(R.string.slytherin, R.string.about_slytherin, R.string.traits_slytherin, R.string.founder_slytherin, R.string.mascot_slytherin, R.string.room_slytherin, "🐍", R.drawable.bg_slytherin),
        "ravenclaw" to HouseInfo(R.string.ravenclaw, R.string.about_ravenclaw, R.string.traits_ravenclaw, R.string.founder_ravenclaw, R.string.mascot_ravenclaw, R.string.room_ravenclaw, "🦅", R.drawable.bg_ravenclaw),
        "hufflepuff" to HouseInfo(R.string.hufflepuff, R.string.about_hufflepuff, R.string.traits_hufflepuff, R.string.founder_hufflepuff, R.string.mascot_hufflepuff, R.string.room_hufflepuff, "🦡", R.drawable.bg_hufflepuff)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        Log.d(TAG, "onCreate: house details created")

        // Забираю имя и выбранный факультет из Intent предыдущего экрана
        val name = intent.getStringExtra(HouseListActivity.EXTRA_NAME) ?: getString(R.string.welcome_default)
        val houseKey = intent.getStringExtra(HouseListActivity.EXTRA_HOUSE)
        val house = details[houseKey]
        if (house == null) {
            Log.e(TAG, "No house details found for key: $houseKey")
            Toast.makeText(this, R.string.house_error, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Показываю полученные с предыдущего экрана имя и выбранный факультет
        findViewById<TextView>(R.id.tvHouseName).setText(house.name)
        findViewById<TextView>(R.id.tvWelcome).text = getString(R.string.detail_welcome, name)
        findViewById<TextView>(R.id.tvAbout).setText(house.about)
        findViewById<TextView>(R.id.tvTraits).setText(house.traits)
        findViewById<TextView>(R.id.tvFounder).setText(house.founder)
        findViewById<TextView>(R.id.tvMascot).setText(house.mascot)
        findViewById<TextView>(R.id.tvCommonRoom).setText(house.commonRoom)
        findViewById<TextView>(R.id.tvHouseIcon).apply {
            text = house.icon
            setBackgroundResource(house.color)
        }
        Log.i(TAG, "Showing ${getString(house.name)} details for $name")
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            Log.i(TAG, "Returning to the house list")
            finish()
        }
    }

    // Эти записи помогают понять, когда карточка факультета открыта или закрыта
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart: house details are visible") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume: house details are ready to read") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause: house details are losing focus") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop: house details are no longer visible") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy: house details closed") }
}
