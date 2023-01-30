package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.databinding.ActivitySignupBinding
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpUser

class SignupActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var id = findViewById<EditText>(R.id.et_signUp_id)
        var pw = findViewById<EditText>(R.id.et_signUp_pw)
        var next = findViewById<Button>(R.id.next_btn)

        val email = id.text.toString()
        val password = pw.text.toString()


        val user = SignUpUser(email=email,password=password)



        next.setOnClickListener {
            val intent = Intent(this,SignupCheckActivity::class.java)
            intent.putExtra("userData", user)
            Log.d("Tester", "onCreate: $user")
            startActivity(intent)
        }




    }
}