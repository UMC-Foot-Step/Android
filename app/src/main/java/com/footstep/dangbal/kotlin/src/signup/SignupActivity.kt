package com.footstep.dangbal.kotlin.src.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivitySignupBinding
import com.footstep.dangbal.kotlin.src.login.LoginProcessActivity
import com.footstep.dangbal.kotlin.src.main.MainActivity
import com.footstep.dangbal.kotlin.util.SaveSignInId
import com.footstep.dangbal.kotlin.util.SaveSignInPw
import com.footstep.dangbal.kotlin.util.getSignInId
import com.footstep.dangbal.kotlin.util.getSignInPw

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
                    if(etSignUpId.length()>8){
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
                   if(etSignUpPw.length() in 8..12){
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
            if(getSignInId()!=null && getSignInPw()!=null){
                etSignUpId.setText(getSignInId())
                etSignUpPw.setText(getSignInPw())
            }
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

        with(binding){
            signupBack.setOnClickListener {
                val intent = Intent(this@SignupActivity,LoginProcessActivity::class.java)
                startActivity(intent)
            }
        }

    }//onCreate


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