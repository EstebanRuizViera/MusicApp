package com.example.reproductor

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.get
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        requestToServer()

    }

    private fun requestToServer() {

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val Queue = Volley.newRequestQueue(this)

        val url = "http://192.168.103.41:40000/image/musica.jpg"

        val jsonObjectRequest = ImageRequest(url,
            Response.Listener<Bitmap> { response ->
                imageViewMusic.setImageBitmap(response)
            },0,0, Bitmap.Config.ARGB_8888,
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )

        // Add the request to the RequestQueue.
        Queue.add(jsonObjectRequest)
    }

}
