package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import com.softsquared.template.kotlin.src.signup.DataSource.SignUpService

class SignupCheckActivity : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_check)

        var edt_name = findViewById<EditText>(R.id.et_nickname)
        var signup_btn = findViewById<Button>(R.id.signUp_btn)
        var agree_check = findViewById<CheckBox>(R.id.agree_check)
        var authority_check = findViewById<CheckBox>(R.id.authority_check)

        var nickname = edt_name.text.toString()



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


            override fun onSignUpSuccess(code: Int, result: String?) {
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
        var userlist = intent.getStringArrayListExtra("text")
        var id = userlist?.get(0).toString()
        var pw = userlist?.get(1).toString()
        var nickname = edt_name.text.toString()

        return SignUpForm(email = id, password = pw, nickname = nickname)
    }

    private fun startActivityLogin(){
        val intent = Intent(this,SignupSuccessActivity::class.java)
        startActivity(intent)

        finish()
    }
}

