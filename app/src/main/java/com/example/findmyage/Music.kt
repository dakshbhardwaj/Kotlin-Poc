package com.example.findmyage

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Music : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    private var ispaused: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.music);


        val start = findViewById<Button>(R.id.start)
        val pause = findViewById<Button>(R.id.pause)


        start.setOnClickListener {
            if(ispaused){
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start();
                ispaused = false;
                Toast.makeText(this,"media playing",Toast.LENGTH_SHORT).show()
            } else {
                mediaPlayer = MediaPlayer.create(this, R.raw.sample)
                Log.i("Music ", "$")
                mediaPlayer.start();
            }
        }

        pause.setOnClickListener {
            ispaused= true;
            mediaPlayer.pause();
        }

    }
}