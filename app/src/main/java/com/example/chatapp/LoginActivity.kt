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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val mail = findViewById<TextInputLayout>(R.id.etMailLogin)
        val passsword = findViewById<TextInputLayout>(R.id.etPasswordLogin)
        val signup = findViewById<AppCompatButton>(R.id.btnSignup)
        val loginBtn = findViewById<AppCompatButton>(R.id.btnLogin)
        val forgotPassword = findViewById<AppCompatButton>(R.id.btnForgotPassword)

        signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {

            val mailCredential = mail.editText?.text.toString()
            val passwordCredential = passsword.editText?.text.toString()

            if(mailCredential.isNotEmpty() == true && passwordCredential.isNotEmpty() == true){
                showProgressbar()


                val credential = EmailAuthProvider.getCredential(mailCredential,passwordCredential)

                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(object:OnCompleteListener<AuthResult> {
                        override fun onComplete(p0: Task<AuthResult>) {
                            if(p0.isSuccessful){
                                hideProgressbar()
                                Toast.makeText(this@LoginActivity, "Xoş gəlmisiniz", Toast.LENGTH_SHORT).show()
                                var intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intentToMain)
                                finish()
                            }else{
                                hideProgressbar()
                                Toast.makeText(this@LoginActivity, "Məlumatlar yanlışdır", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })


            }else{
                Toast.makeText(this, "Bütün məlumatları daxil edin", Toast.LENGTH_SHORT).show()
            }
        }


        forgotPassword.setOnClickListener{
            var dialogPassReset = ForgotPassFragment()
            dialogPassReset.show(supportFragmentManager, "showdialog")
        }


    }




//    private fun verificateMail() {
//        val user = FirebaseAuth.getInstance().currentUser
//
//        user?.sendEmailVerification()?.addOnCompleteListener {
//            OnCompleteListener<Void> { p0 ->
//                if(p0.isSuccessful){
//                    Toast.makeText(this@LoginActivity, "Mail sent, check your mailbox", Toast.LENGTH_SHORT).show()
//                    finishAffinity()
//                }else{
//                    Toast.makeText(this@LoginActivity, "Mail couldn't send", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun hideProgressbar(){
        val progressbar = findViewById<ProgressBar>(R.id.progressBarLogin)
        progressbar.visibility = View.INVISIBLE
    }
    private fun showProgressbar(){
        val progressbar = findViewById<ProgressBar>(R.id.progressBarLogin)
        progressbar.visibility = View.VISIBLE
    }
}