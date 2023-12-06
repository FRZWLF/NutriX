package com.sem.nutrix

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieListener


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie)
        lottieAnimationView.setAnimation(R.raw.splash_loading)

        lottieAnimationView.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
                //startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            }

            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish() // Close the splash screen activity
            }

            override fun onAnimationCancel(animation: Animator) {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animator) {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }
        })

    }
}
