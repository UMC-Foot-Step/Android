package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.databinding.ActivitySignupBinding
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpUser
import com.softsquared.template.kotlin.util.SaveSignInId
import com.softsquared.template.kotlin.util.SaveSignInPw

class SignupActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    var id_check = false
    var pw_check = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding){
            etSignUpId.addTextChangedListener (object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if(etSignUpId.length()>5){
                        id_check = true
                        checknext()
                    }
                    else{
                        id_check = false
                        checknext()
                    }
                }
            })

            etSignUpPw.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                   if(etSignUpPw.length()>4){
                       pw_check = true
                       checknext()
                   }
                    else{
                        pw_check = false
                       checknext()
                   }
                }

            })

        }

        with(binding){
            nextBtn.setOnClickListener {
//                val user = SignUpUser(
//                    email = etSignUpId.text.toString(),
//                    password = etSignUpPw.text.toString()
//                )
//                val intent = Intent(this@SignupActivity,SignupCheckActivity::class.java)
//                intent.putExtra("userData",user)
                val intent = Intent(this@SignupActivity,SignupCheckActivity::class.java)
                SaveSignInId(etSignUpId.text.toString())
                SaveSignInPw(etSignUpPw.text.toString())
                startActivity(intent)
            }

        }

    }


    private fun checknext(){

        if(id_check){
            if(pw_check){
                binding.nextBtn.isEnabled = true
                binding.nextBtn.setTextColor(Color.parseColor("#ffffff"))
            }
            else{
                binding.nextBtn.isEnabled = false
                binding.nextBtn.setTextColor(Color.parseColor("#000000"))
            }
        }
        else {
            binding.nextBtn.isEnabled = false
            binding.nextBtn.setTextColor(Color.parseColor("#000000"))
        }

    }
}