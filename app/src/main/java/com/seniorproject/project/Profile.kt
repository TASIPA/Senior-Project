package com.seniorproject.project
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var dbReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database?.reference!!.child("profile")

        loadProfile()
    }
    
    private fun loadProfile(){
        
        val user = auth.currentUser
        val userref = dbReference?.child(user?.uid!!)
        
        userref?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                name.text = snapshot.child("name").value.toString()
                username.text = snapshot.child("username").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    
}