package pe.devpicon.android.intents

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class IntentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intents)

        val btn1 = findViewById(R.id.button1) as Button
        // Aquí hay un ejemplo de cómo implementar anónimamente una interface
        btn1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val url = "http://www.google.com"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

        })

        val btn2 = findViewById(R.id.button2) as Button
        // En este ejemplo, se está empleando un lamba
        btn2.setOnClickListener({ _ ->
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:5549592073")))
        })

        // En este ejemplo, se ha simplificado el lamba
        val btn3 = findViewById(R.id.button3) as Button
        btn3.setOnClickListener({
            val gmm = Uri.parse("google.streetview:cbll=19.332273,-99.190092")
            val intent = Intent(Intent.ACTION_VIEW, gmm)
            intent.`package` = "com.google.android.apps.maps"
            startActivity(intent)
        })

        val btn4 = findViewById(R.id.button4) as Button
        btn4.setOnClickListener({
            val texto = "Este texto se va a enviar a algún lugar"
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, texto)
            intent.type = "text/plain"
            val chooser = Intent.createChooser(intent, "Escoge un app")
            val resolveActivity: ComponentName? = intent.resolveActivity(packageManager)

            if (resolveActivity != null) {
                startActivity(chooser)
            }

        })
        val btn5 = findViewById(R.id.btnLogin) as Button
        btn5.setOnClickListener({
            val edtUser = findViewById(R.id.editText) as EditText
            val name = edtUser.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this@IntentsActivity, "Está vacío!!!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@IntentsActivity, ResultActivity::class.java)
                intent.putExtra(Constants.INTENT_KEY_USERNAME, name)
                startActivityForResult(intent, Constants.REQUEST_CODE_SECOND_ACTIVITY)
            }
        })

        val btn6 = findViewById(R.id.bntFoto)
        btn6.setOnClickListener {
            // Se valida si se cuenta con los permisos para abrir la cámara
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP &&
                    ContextCompat
                            .checkSelfPermission
                            (this@IntentsActivity,
                                    Manifest
                                            .permission
                                            .CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@IntentsActivity, arrayOf(Manifest
                        .permission.CAMERA), Constants.PERMISSION_REQUEST_CAMERA)
            } else {
                captureImage()
            }
        }

    }

    private fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, Constants.REQUEST_CODE_FOTO)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Constants.REQUEST_CODE_SECOND_ACTIVITY == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data?.getStringExtra(Constants.INTENT_KEY_SALUDO)
                Toast.makeText(this@IntentsActivity, result, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@IntentsActivity, "Le di a back!!", Toast.LENGTH_SHORT).show()
            }
        } else if (Constants.REQUEST_CODE_FOTO == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val extras = data?.extras
                val imageBitmap = extras?.get("data") as Bitmap
                val imgPhoto = findViewById(R.id.imageView) as ImageView
                imgPhoto.setImageBitmap(imageBitmap)
            } else {
                Toast.makeText(this@IntentsActivity, "No se pudo cargar la imagen", Toast
                        .LENGTH_SHORT).show()
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.PERMISSION_REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImage()
                } else {
                    Toast.makeText(this@IntentsActivity, "No se brindó el permiso para emplear la" +
                            " cámara", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
