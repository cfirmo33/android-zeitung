package com.segunfamisa.zeitung

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.segunfamisa.zeitung.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.container,
                MainFragment(), MainFragment::class.toString())
            .commit()
    }
}
