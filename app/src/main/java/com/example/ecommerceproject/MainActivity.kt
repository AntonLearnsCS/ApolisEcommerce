package com.example.ecommerceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.ecommerceproject.activities.LoginActivity
import com.example.ecommerceproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Looper runs the runnables in the Message queue (like a convayer belt)
        //and a Handler allows you to introduce runnables such as the intent below to the Message queue
        //Q: If a Handler is used to post runnables from a background thread to the main thread, then
        //are we saying that the value inside postDelayed is initially running on a separate thread?
        // https://www.youtube.com/watch?v=fZTJflHJoBY&ab_channel=Codetutor
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 500) // 3000 is the delayed time in milliseconds.
    }
}