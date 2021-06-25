package com.seniorproject.project
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

import java.io.IOException
import java.util.*

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var dbReference: DatabaseReference
    private lateinit var dialog: ProgressDialog

    private val PICK_IMAGE_REQUEST = 1234
    private var filePath: Uri? = null
    var imageURL: String? = null
    var imageURL2: String? = null
    var ID:String?=null
    var gg:String?=null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        dialog = ProgressDialog(this)

        ID = UUID.randomUUID().toString()
        val gg = "https://firebasestorage.googleapis.com/v0/b/senior-project-c45a0.appspot.com/o/"

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database.reference.child("profile")
        cancel.setOnClickListener {
            finish()
        }
        ChangePassBtn.setOnClickListener {
            val intent = Intent(this, UpdatePassword::class.java)
            startActivity(intent)
        }
        ChangePicBtn.setOnClickListener {
            showPhoto()
        }
        proAct_img.setOnClickListener {
            showPhoto()
        }
        DoneBtn.setOnClickListener {

            if (Firstname.text.toString().isEmpty() || Lastname.text.toString().isEmpty() || username.text.toString().isEmpty() || phoneNum.text.toString().isEmpty()){
                Toast.makeText(this,"Please do not leave the information empty",Toast.LENGTH_SHORT).show()
            }
            else{
                val currentUserDB = dbReference.child(auth.currentUser!!.uid!!)
                currentUserDB.child("firstname").setValue(Firstname.text.toString())
                currentUserDB.child("lastname").setValue(Lastname.text.toString())
                currentUserDB.child("username").setValue(username.text.toString())
                currentUserDB.child("phone").setValue(phoneNum.text.toString())
                if (imageURL2 != null ){
                    val currentUserDB = dbReference.child(auth.currentUser!!.uid!!)
                    currentUserDB.child("picurl").setValue(imageURL2)
                }
                finish()
            }

//            if (imageURL2 != null ){
//                val currentUserDB = dbReference.child(auth.currentUser!!.uid!!)
//                currentUserDB.child("picurl").setValue(imageURL2)
//                finish()
//            }
//            finish()
        }

        //Firstname.hint = "fhsdofesf"

        loadProfile()
    }
    
    private fun loadProfile(){
        
        val user = auth.currentUser
        val userref = dbReference.child(user?.uid!!)
        
        userref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                Firstname.setText(snapshot.child("firstname").value.toString())
                Lastname.setText(snapshot.child("lastname").value.toString())
                username.setText(snapshot.child("username").value.toString())
                phoneNum.setText(snapshot.child("phone").value.toString())
                if (snapshot.child("picurl").exists()){
                    var profilePic = snapshot.child("picurl").value.toString()
                    Picasso.get().load(profilePic).into(proAct_img)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun uploadFile() {
        if (filePath != null){//so that know the picture location
            //Toast.makeText(applicationContext, "Uploading...", Toast.LENGTH_SHORT).show()
            val imageRef = storageReference!!.child("profileimages/"+ ID)
            //directory name on the firebase name image...+ the unique ID creation so make sure it is not having the same name
            imageRef.putFile(filePath!!)//upload file function!! "filepath" is the picture location
                .addOnSuccessListener {
                    dialog.dismiss()
                    imageRef.downloadUrl.addOnSuccessListener {
                        imageURL2 = it.toString()
//
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
//                    val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount //calculate the progress!
//                    Toast.makeText(applicationContext, "Uploaded "+progress.toInt()+"%..", Toast.LENGTH_SHORT).show()
                    dialog.setMessage("Uploading...")
                    dialog.show()
                    dialog.setCanceledOnTouchOutside(false)
                }
        }
    }

    private fun showPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select a photo"),PICK_IMAGE_REQUEST)
        //first argument = target (in this case, its var intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {//picture return as the 'data'
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!=null && data.data!=null)
            filePath = data.data
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath) //to get bitmap
            proAct_img.setImageBitmap(bitmap) //xml
            uploadFile()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}