package com.example.chatapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class ForgotPassFragment : DialogFragment() {


    var mContext:FragmentActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_forgot_pass, container, false)

        var btnSend =  view.findViewById<AppCompatButton>(R.id.btnPswResetSend)

        var btnCancel  = view.findViewById<AppCompatButton>(R.id.btnPswResetLegv)
        var mail = view.findViewById<TextInputLayout>(R.id.etMailPswReset)

        mContext = activity
        btnCancel?.setOnClickListener {
            dialog?.dismiss()
        }

        btnSend!!.setOnClickListener {
            if(mail?.editText?.text.toString().isNotBlank()){
                FirebaseAuth.getInstance().sendPasswordResetEmail(mail?.editText?.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(mContext,"Şifrə e-mailinizə göndərildi", Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                    }else{
                        Toast.makeText(mContext, "Şifrə göndərilərkən xəta baş verdi",Toast.LENGTH_LONG).show()
                        dialog?.dismiss()
                    }
                }
            }else{
                Toast.makeText(mContext, "İstifadə etdiyiniz mailinizi yazın",Toast.LENGTH_LONG).show()
            }

        }

        return view

    }


}