package com.example.pakhi

import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//class SplashActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        auth = Firebase.auth
//
//        // Your original GIF animation code (unchanged)
//        Thread {
//            val source = ImageDecoder.createSource(resources, R.drawable.dustor_gif)
//            val drawable: Drawable = ImageDecoder.decodeDrawable(source)
//            val imageView: ImageView = findViewById<ImageView>(R.id.dustor_gif)
//            imageView.post {
//                imageView.setImageDrawable(drawable)
//                (drawable as? AnimatedImageDrawable)?.start()
//            }
//        }.start()
//
//        // Set up button listeners (unchanged)
//        findViewById<Button>(R.id.signInButton).setOnClickListener {
//            startActivity(Intent(this, SignUpActivity::class.java))
//            finish()
//        }
//
//        findViewById<Button>(R.id.logInButton).setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//
//        // Check auth status after 1.5 seconds (let splash screen show)
//        Handler(Looper.getMainLooper()).postDelayed({
//            auth.currentUser?.let { user ->
//                // User is logged in - go to MainActivity
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//            // If not logged in, do nothing (stay on splash screen)
//        }, 1500)
//    }
//}

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth 


        Thread {
            val source = ImageDecoder.createSource(resources, R.drawable.dustor_gif)
            val drawable: Drawable = ImageDecoder.decodeDrawable(source)
            val imageView: ImageView = findViewById<ImageView>(R.id.dustor_gif)
            imageView.post {
                imageView.setImageDrawable(drawable)
                (drawable as? AnimatedImageDrawable)?.start()
            }
        }.start()


        val firstButton : Button = findViewById(R.id.signInButton)
firstButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        val secondButton : Button = findViewById(R.id.logInButton)
        secondButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        Handler(Looper.getMainLooper()).postDelayed({
            auth.currentUser?.let { user ->

                user.reload().addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {

                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            } ?: run {
            }
        }, 1500)
    }
}


//

//
//class SplashActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        auth = FirebaseAuth.getInstance()
//
//        val firstButton : Button = findViewById(R.id.signInButton)
//        firstButton.setOnClickListener{
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        val secondButton : Button = findViewById(R.id.logInButton)
//        secondButton.setOnClickListener{
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
////
//
//
//        Thread{
//            val source=ImageDecoder.createSource(
//                resources,R.drawable.dustor_gif
//            )
//            val drawable : Drawable = ImageDecoder.decodeDrawable(source)
//            val imageView: ImageView = findViewById<ImageView>(R.id.dustor_gif)
//            imageView.post{
//                imageView.setImageDrawable(drawable)
//                (drawable as? AnimatedImageDrawable)?.start()
//            }
//        }.start()
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            auth.currentUser?.let {
//                // Only redirect if user is logged in
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            })
//
////            val currentUser = auth.currentUser
//
//
//
////        Handler().postDelayed({
////            if(currentUser==null) {
////                val intent = Intent(this, LoginActivity::class.java)
////                startActivity(intent)
////            }
////            else {
////                val intent = Intent(this, MainActivity::class.java)
////                startActivity(intent)
////            }
////            finish()
////        }, 3000)
////        if(currentUser != null)
////        {
////            val intent = Intent(this, MainActivity::class.java)
////            startActivity(intent)
////            finish()
////        }
//    })
//}}