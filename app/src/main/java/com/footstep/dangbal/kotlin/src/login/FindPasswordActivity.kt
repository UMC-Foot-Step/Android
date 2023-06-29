package com.footstep.dangbal.kotlin.src.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivityFindPasswordBinding
import com.footstep.dangbal.kotlin.src.login.DataFile.Findpassword
import com.footstep.dangbal.kotlin.src.login.LoginDataSource.NetworkDataSource

class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding>(ActivityFindPasswordBinding::inflate){

    var id_check = false
    var nickname_check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.findId.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.findId.length() > 8) {
                    id_check = true
                    id_nickname_check()

                }
                else{
                    id_check = false
                    id_nickname_check()

                }
            }
        })

        binding.findIdNickname.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.findIdNickname.length()>3){
                    nickname_check = true
                    id_nickname_check()

                }
                else{
                    nickname_check =false
                    id_nickname_check()
                }

            }

        })



        binding.findPasswordBack.setOnClickListener {
            val intent = Intent(this,LoginProcessActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.nextBtn.setOnClickListener {
            findpassword()
        }
    }//onCreate


    private fun findpassword(){
        NetworkDataSource().findpassword(getUser(),object : FindPasswordView{
            override fun onFindSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        nextActivity()
                    }
                }
            }

            override fun onFindFailure(message: String) {

            }

        })
    }

    private fun getUser():Findpassword{
        var et_email = findViewById<EditText>(R.id.find_id)
        var et_nickname = findViewById<EditText>(R.id.find_id_nickname)
        var email = et_email.text.toString()
        var nickname = et_nickname.text.toString()

        return Findpassword(email = email, username = nickname)
    }

    private fun nextActivity(){
        val intent = Intent(this,FindSuccessActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun id_nickname_check(){
        if(id_check){
            if(nickname_check){
                binding.nextBtn.setBackgroundResource(R.drawable.solid_button)
                binding.nextBtn.setTextColor(Color.parseColor("#ffffff"))
                binding.nextBtn.isClickable = true
                binding.nextBtn.isEnabled = true
            }
            else{
                binding.nextBtn.setBackgroundResource(R.drawable.login_button)
                binding.nextBtn.isClickable = false
                binding.nextBtn.isEnabled = false
            }
        }else{
            binding.nextBtn.setBackgroundResource(R.drawable.login_button)
            binding.nextBtn.isClickable = false
            binding.nextBtn.isEnabled = false
        }



    }






}