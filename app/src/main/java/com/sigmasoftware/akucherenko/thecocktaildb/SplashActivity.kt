package com.sigmasoftware.akucherenko.thecocktaildb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val maimIntent = Intent(this, MainActivity::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            startActivity(maimIntent)
        }

    }
}