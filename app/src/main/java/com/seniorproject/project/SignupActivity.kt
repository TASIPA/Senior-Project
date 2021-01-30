package com.seniorproject.project

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth= FirebaseAuth.getInstance()
        dialog= ProgressDialog(this)
        supportActionBar!!.hide()
        nextBtn.setOnClickListener {
            first.visibility=INVISIBLE
            second.visibility= VISIBLE
        }

        SignupBtn.setOnClickListener {
            register()
        }
    }
    private fun register() {
        var email = emailSignupText.text.toString() //store the input
        var pass = passSignupText.text.toString() //store the input
        var cpass = repassSignupText.text.toString() //store the input

        //check the possible condition of user errors
        if (email.isEmpty()) {
            emailSignupText.error = "Please enter your Email"
            return
        }
        if (pass.isEmpty()) {
            passSignupText.error = "Please enter a password"
            return}
        if (cpass.isEmpty()) {
            repassSignupText.error = "Please confirm your password"
            return
        }
        if (cpass != pass) {
            Toast.makeText(this, "Password is Not Matched", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignupText.error = "Invalid Email Format"
            return
        }
        if (pass.length < 5) {
            passSignupText.error = "Password should be greater than 5 Characters"
            return
        }

        //check whether the checkBox is tick or not, if not user won't be able to register

        if(!checkBox.isChecked){
            Toast.makeText(this, "Agree & Read TOS", Toast.LENGTH_SHORT).show()
            return
        }
        //set the dialog running to the user when it creating account to the firebase authentication

        dialog.setMessage("..Creating Account..")
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)

        auth.createUserWithEmailAndPassword(email, pass) //function to create the account by passing the value that we collected
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialog.cancel()//stop the dialog running when created account in the authentication successfully
                    Toast.makeText(this, "Account Created", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)//back to the login page
                    startActivity(intent)
                    finish()
                } else {
                    dialog.cancel()
                    Toast.makeText(this, "Failed to create Account", Toast.LENGTH_LONG).show()
                }
            })
    }
}
