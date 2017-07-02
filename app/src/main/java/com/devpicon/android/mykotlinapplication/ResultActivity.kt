package com.devpicon.android.mykotlinapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by Armando on 30/6/2017.
 */
class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)

        val name = intent.getStringExtra("name")
        Toast.makeText(this@ResultActivity, "Se recibió $name!!", Toast.LENGTH_SHORT).show()
    }

}