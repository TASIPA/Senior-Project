package com.seniorproject.project
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.name
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.io.IOException
import java.util.*

class Profile : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var dbReference: DatabaseReference? = null

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        ID = UUID.randomUUID().toString()
        val gg = "https://firebasestorage.googleapis.com/v0/b/senior-project-c45a0.appspot.com/o/"

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database?.reference!!.child("profile")
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
        DoneBtn.setOnClickListener {
            addPictoDB()
            finish()
        }
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

    private fun uploadFile() {
        if (filePath != null){//so that know the picture location
            Toast.makeText(applicationContext, "Uploading...", Toast.LENGTH_SHORT).show()
            val imageRef = storageReference!!.child("profileimages/"+ ID)
            //directory name on the firebase name image...+ the unique ID creation so make sure it is not having the same name
            imageRef.putFile(filePath!!)//upload file function!! "filepath" is the picture location
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "File uploaded", Toast.LENGTH_SHORT).show()
                    imageRef.downloadUrl.addOnSuccessListener {
                        imageURL2 = it.toString()
                        //Log.d("aa",it.toString())
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount //calculate the progress!
                    Toast.makeText(applicationContext, "Uploaded "+progress.toInt()+"%..", Toast.LENGTH_SHORT).show()
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
            //profile.setImageBitmap(bitmap) //xml
            uploadFile()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
    private fun addPictoDB(){
//        val gg = "https://firebasestorage.googleapis.com/v0/b/senior-project-c45a0.appspot.com/o/"
//        imageURL = gg+"reportimages%2F"+ID+"?alt=media"+"&token=9bc2f931-c8fa-4de7-b7fb-f1b1cfc53fd2"

        val currentUser = auth.currentUser
        val currentUserDB = dbReference?.child(currentUser?.uid!!)
        currentUserDB?.child("picurl")?.setValue(imageURL2)
    }
    
}