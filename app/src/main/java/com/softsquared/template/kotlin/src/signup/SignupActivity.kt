package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding

class SignupActivity : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        var signUp_id = findViewById<EditText>(R.id.et_signUp_id)
        var signUp_pw = findViewById<EditText>(R.id.et_signUp_pw)
        var next_btn = findViewById<Button>(R.id.next_btn)

        var id_pw_list = ArrayList<String>()

        id_pw_list.add(signUp_id.text.toString())
        id_pw_list.add(signUp_pw.text.toString())


        next_btn.setOnClickListener {
            val intent = Intent(this,SignupCheckActivity::class.java)
            intent.putExtra("text",id_pw_list)

            startActivity(intent)
        }




    }
}