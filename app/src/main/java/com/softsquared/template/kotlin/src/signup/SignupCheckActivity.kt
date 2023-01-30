package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.databinding.ActivitySignupCheckBinding
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpUser
import com.softsquared.template.kotlin.src.signup.DataSource.SignUpService

class SignupCheckActivity : BaseActivity<ActivitySignupCheckBinding>(ActivitySignupCheckBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_check)

        var edt_name = findViewById<EditText>(R.id.et_nickname)
        var signup_btn = findViewById<Button>(R.id.signUp_btn)
        var agree_check = findViewById<CheckBox>(R.id.agree_check)
        var authority_check = findViewById<CheckBox>(R.id.authority_check)


        intent.getSerializableExtra("userData") as SignUpUser



        agree_check.setOnClickListener {
            val intent = Intent(this,SignupInfoActivity::class.java)
            startActivity(intent)
        }

        authority_check.setOnClickListener {
            //팝업박스 작업
        }

        signup_btn.setOnClickListener {
            signUp()

        }



    }

    private fun signUp(){
        SignUpService().signUp(getUser(),object : SignUpView{
            override fun onSignUpSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        showCustomToast(result!!)
                        startActivityLogin()
                    }

                }
            }

            override fun onSignUpFailure(message: String?) {
                Toast.makeText(this@SignupCheckActivity,message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getUser():SignUpForm {
        var edt_name = findViewById<EditText>(R.id.et_nickname)




        var nickname = edt_name.text.toString()

        return SignUpForm(email = "", password = "", nickname = nickname)
    }

    private fun startActivityLogin(){
        val intent = Intent(this,SignupSuccessActivity::class.java)
        startActivity(intent)

        finish()
    }
}

