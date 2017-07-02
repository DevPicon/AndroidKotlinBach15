package com.devpicon.android.mykotlinapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtUserName = findViewById(R.id.edt_user_name) as EditText
        val btnClickMe = findViewById(R.id.btn_click_me) as Button

        btnClickMe.setOnClickListener({
            val name = edtUserName.text.toString()

            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        })
    }
}
