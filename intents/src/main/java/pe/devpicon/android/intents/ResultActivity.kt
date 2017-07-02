package pe.devpicon.android.intents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * Created by Armando on 30/6/2017.
 */
class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)

        val extras = intent.extras
        if (extras.containsKey(Constants.INTENT_KEY_USERNAME)) {
            var user: String = extras.getString(Constants.INTENT_KEY_USERNAME)
            val txtUserName = findViewById(R.id.txt_user_name) as TextView
            txtUserName.text = user

            val btn1 = findViewById(R.id.btn1) as Button
            btn1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val returnIntent = Intent()
                    returnIntent.putExtra(Constants.INTENT_KEY_SALUDO, "Hi everyone!")
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
            })
        }

    }

}