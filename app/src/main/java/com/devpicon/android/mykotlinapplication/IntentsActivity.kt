package com.devpicon.android.mykotlinapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.devpicon.android.mykotlinapplication.IntentsActivity.companion.INTENT_KEY_SALUDO
import com.devpicon.android.mykotlinapplication.IntentsActivity.companion.REQUEST_CODE_SECOND_ACTIVITY

class IntentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intents)

        val btn1 = findViewById(R.id.button1) as Button
        btn1.setOnClickListener({
            val url = "http://www.google.com"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        })

        val btn2 = findViewById(R.id.button2) as Button
        btn2.setOnClickListener({
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:5549592073")))
        })

        val btn3 = findViewById(R.id.button3) as Button
        btn3.setOnClickListener({
            val gmm = Uri.parse("google.streetview:cbll=19.332273,-99.190092")
            val intent = Intent(Intent.ACTION_VIEW, gmm)
            intent.`package`  = "com.google.android.apps.maps"
            startActivity(intent)
        })

        val btn4 = findViewById(R.id.button4) as Button
        btn4.setOnClickListener({
            val texto = "Este texto se va a enviar a algún lugar"
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, texto)
            intent.type = "text/plain"
            val chooser = Intent.createChooser(intent, "Escoge un app")
            val resolveActivity : ComponentName? = intent.resolveActivity(packageManager)

            if(resolveActivity != null){
                startActivity(chooser)
            }

        })
        val btn5 = findViewById(R.id.btnLogin) as Button
        btn5.setOnClickListener({
            val edtUser = findViewById(R.id.editText) as EditText
            val name = edtUser.text.toString()
            if(name.isEmpty()){
                Toast.makeText(this@IntentsActivity, "Está vacío!!!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@IntentsActivity, ResultActivity::class.java)
                intent.putExtra("name2", name)
                startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY)

            }
        })

    }

    object companion {
        const val REQUEST_CODE_SECOND_ACTIVITY = 9999
        const val INTENT_KEY_SALUDO = "saludos"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(REQUEST_CODE_SECOND_ACTIVITY == requestCode){
            if(resultCode == Activity.RESULT_OK){
                val result = data?.getStringExtra(INTENT_KEY_SALUDO)
                Toast.makeText(this@IntentsActivity, result, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@IntentsActivity, "Le di a back!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
