package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_emanat)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val btnRegister = findViewById<AppCompatButton>(R.id.btnRegister)
        val mail = findViewById<TextInputLayout>(R.id.etMail)
        val password = findViewById<TextInputLayout>(R.id.etPassword)
        val passwordRepeat = findViewById<TextInputLayout>(R.id.etPasswordRepeat)
        val signin = findViewById<AppCompatButton>(R.id.btnSignin)

        btnRegister.setOnClickListener {
            if (mail.editText?.text?.isNotEmpty() ==true && password.editText?.text?.isNotEmpty() ==true
                && passwordRepeat.editText?.text?.isNotEmpty() == true) {
                if (password.editText!!.text.toString().equals(passwordRepeat.editText!!.text.toString())){
                        createUser(mail.editText!!.text.toString(), password.editText!!.text.toString())
                }else{
                    Toast.makeText(this, "Təkrar şifrəni düzgün daxil edin", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Bütün məlumatları daxil edin", Toast.LENGTH_SHORT).show()
            }
        }

        signin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUser(mail: String, password: String) {

        showProgressbar()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0.isSuccessful){
                        hideProgressbar()
                        Toast.makeText(this@RegisterActivity, "Istifadəçi yaradıldı",Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        hideProgressbar()
                        Toast.makeText(this@RegisterActivity, "Məlumatları düzgün doldurduğunuzdan əmin olun"+ p0.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

    }

    private fun hideProgressbar(){
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        progressbar.visibility = View.INVISIBLE
    }
    private fun showProgressbar(){
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        progressbar.visibility = View.VISIBLE
    }
}