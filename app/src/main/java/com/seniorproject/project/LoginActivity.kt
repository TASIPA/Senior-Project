package com.seniorproject.project

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth //Declare the connection to firebase authentication
    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        auth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(this)

        LoginBtn.setOnClickListener {
            login()
        }
        registerBtn.setOnClickListener {//change to signup page once user clicks
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        forgetBtn.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() { //Login page
        var email = emailText.text.toString() //put the input data to email variable
        var pass = passText.text.toString() //put the input data to pass variable

        //check the possible conditions of user errors
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.error = "Please enter a VALID Email format"
            return
        } else if (pass.length < 5) {
            passText.error = "Password should be greater than 5 characters"
            return
        } else if (pass.isEmpty()) {
            passText.error = "Please enter a password!"
            return
        } else {
            //when it is not error, it will set dialog once user press log in while comparing user's data
            dialog.setMessage("Please Wait..")
            dialog.show()
            dialog.setCanceledOnTouchOutside(false)
            auth.signInWithEmailAndPassword(email, pass)//pass value to firebase authentication
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {//check if it contains this user in the authentication data
                        dialog.cancel()
                        Toast.makeText(this, "Successfully Login", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)//move to the restaurant interface : our kt name not matched!
                        finish()
                    } else {
                        dialog.cancel()
                        Toast.makeText(this, "Failed to Login", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}

