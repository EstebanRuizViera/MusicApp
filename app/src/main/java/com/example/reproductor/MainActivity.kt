package com.example.reproductor

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.buttonPlay

class MainActivity : AppCompatActivity() {

    private var posicion = 0
    //indica la posicion actual en la lista de canciones
    private var canciones= Array<String>(5){i ->0.toString()}
    private var miMediaPlayer:MediaPlayer?=null
    private var option = 1

    var musicIsPlay = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestToServer()

        buttonChangeColor.setOnClickListener {
            change()
        }

    }

    private fun change(){
        if(option==1){
            option=2
        }else{
            option=1
        }
        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val Queue = Volley.newRequestQueue(this)

        val url = "http://192.168.0.157:40000/selectBadground"+option
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                panelPrincipal.setBackgroundColor(response.getInt("color"))
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this,"Error del servidor",Toast.LENGTH_SHORT).show()
            }
        )
        // Add the request to the RequestQueue.
        Queue.add(jsonObjectRequest)


    }

     private fun playOrPause(){
        if(musicIsPlay ==1){
            miMediaPlayer?.pause()
            musicIsPlay=2
        }else{
            miMediaPlayer?.start()
            musicIsPlay=1
        }
    }

    private fun siguiente(){
        if(posicion < 5){
            if(musicIsPlay == 1){
                miMediaPlayer?.stop()
                //posicion++

                miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
                miMediaPlayer?.setOnPreparedListener(
                    null
                )
                Toast.makeText(this,canciones[posicion],Toast.LENGTH_SHORT).show()
                textViewName.setText(canciones[posicion])

            }else{
                //posicion++
                miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
                miMediaPlayer?.setOnPreparedListener(
                    null
                )

                Toast.makeText(this,canciones[posicion],Toast.LENGTH_SHORT).show()
                textViewName.setText(canciones[posicion])
            }
        } else{
            miMediaPlayer?.stop()
            Toast.makeText(this,"No ahi más canciones",Toast.LENGTH_SHORT).show()
            posicion=4
            miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
            miMediaPlayer?.setOnPreparedListener(
                null
            )
        }
    }

    private fun anterior(){
        if(posicion >= 0){
            // el 5 corresponde a la longitud de las canciones
            if(musicIsPlay==1){
                miMediaPlayer?.stop()
                //posicion--

                //requestToServer()
                miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
                miMediaPlayer?.setOnPreparedListener(
                    null
                )
                Toast.makeText(this,canciones[posicion],Toast.LENGTH_SHORT).show()
                textViewName.setText(canciones[posicion])

            }else{
                //posicion--

                //requestToServer()
                miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
                miMediaPlayer?.setOnPreparedListener(
                    null
                )
                Toast.makeText(this,canciones[posicion],Toast.LENGTH_SHORT).show()
                textViewName.setText(canciones[posicion])
            }
        } else{
            miMediaPlayer?.stop()
            Toast.makeText(this,"No ahi más canciones",Toast.LENGTH_SHORT).show()
            posicion=0
            miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
            miMediaPlayer?.setOnPreparedListener(
                null
            )
        }
    }



    //CODIGO DESECHABLE
    private fun prepareAudio(){
        val url = "http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3" // your URL here
        var uri = Uri.parse(url)
        miMediaPlayer?.apply {
            setDataSource(url)
            prepare()
        }
    }

    private fun requestToServer() {
        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val Queue = Volley.newRequestQueue(this)

        val url = "http://192.168.0.157:40000/musicList"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                canciones[0] = response.getJSONArray("list").getJSONObject(0).getString("music")
                canciones[1] = response.getJSONArray("list").getJSONObject(1).getString("music")
                canciones[2] = response.getJSONArray("list").getJSONObject(2).getString("music")
                canciones[3] = response.getJSONArray("list").getJSONObject(3).getString("music")
                canciones[4] = response.getJSONArray("list").getJSONObject(4).getString("music")
                textViewName.setText(canciones[posicion])
                miMediaPlayer = MediaPlayer.create(this,Uri.parse("http://192.168.0.157:40000/music/"+canciones[posicion]+".mp3"))
                miMediaPlayer?.setOnPreparedListener(
                    null
                )


                buttonForward.setOnClickListener{
                    if(posicion<4){
                        posicion++
                    }
                    siguiente()

                }
                buttonPlay.setOnClickListener{
                    playOrPause()
                }
                buttonRewind.setOnClickListener{
                    if(posicion>0){
                        posicion--
                    }
                    anterior()
                }
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this,"Error del servidor",Toast.LENGTH_SHORT).show()
            }
        )
        // Add the request to the RequestQueue.
        Queue.add(jsonObjectRequest)
    }

}

