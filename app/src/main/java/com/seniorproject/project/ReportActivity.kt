package com.seniorproject.project

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_report.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.Snapshot
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.seniorproject.project.R.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_report.back_btn
import kotlinx.android.synthetic.main.activity_restaurant.*
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
class ReportActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1234
    private var filePath: Uri? = null
    private val progress: ProgressDialog? = null
    var intopicName: String? = null
    var inreporttime: TextView? = null
    var indescription: TextView? = null
    var buttonsend: Button? = null
    var topic: String? = null
    var timestamp: String? = null
    var reporttime: String? = null
    var description: String? = null
    var imageURL: String? = null
    var imageURL1: String? = null
    var ID:String?=null
    var INUserEmail: String? = null
    var userEmail : String? = null

    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var dbReference: DatabaseReference? = null

    internal var storage:FirebaseStorage? = null
    internal var storageReference: StorageReference? = null

    var dateFormat = SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH)
    var timeFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_report)
        supportActionBar?.hide()

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbReference = database?.reference!!.child("profile")

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")
        val formatted = current.format(formatter)

        buttonsend = findViewById<View>(id.button2) as Button
        inreporttime = findViewById<View>(id.dateShowReport) as TextView
        indescription = findViewById<View>(id.DescText) as EditText
        ID = UUID.randomUUID().toString()

        val languages = resources.getStringArray(array.Topics)
        loadProfile()

        // access the spinner
        val spinner = findViewById<Spinner>(id.topicReport)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                layout.custom_spinner, languages)
            adapter.setDropDownViewResource(layout.custom_spinner_dropdown)

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    val errorText = topicReport.selectedView as TextView
                    intopicName = topicReport.selectedItem.toString()

                    button2.setOnClickListener {
                        if (position == 0){
                            errorText.error = ""
                            errorText.requestFocus()
                            Toast.makeText(this@ReportActivity, "Please select one topic", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            topic = intopicName
                            timestamp = "$formatted"
                            reporttime = inreporttime!!.text.toString()
                            description = indescription!!.text.toString()
                            imageURL = imageURL1
                            userEmail = INUserEmail
                            SendRequest().execute()
                        }
                    }

                    return
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@ReportActivity, "Please select one of the topics", Toast.LENGTH_SHORT).show()

                }
            }

            button.setOnClickListener{
                showPhoto()
            }
        }

        //dateShowReport.setInputType(InputType.TYPE_NULL)
        dateShowReport.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = dateFormat.format(selectedDate.time)

                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    val time = timeFormat.format(selectedTime.time)
                    Toast.makeText(this, "selected time: "+time,Toast.LENGTH_SHORT).show()

                    dateShowReport.text = date+" "+time
                },
                    now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)
                timePicker.show()

                Toast.makeText(this, "selected date: "+date, Toast.LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        back_btn.setOnClickListener {
            finish()
        }
    }

    inner class SendRequest :
        AsyncTask<String?, Void?, String>() {
        override fun onPreExecute() {}
        override fun onPostExecute(result: String) {
            Toast.makeText(
                applicationContext, result,
                Toast.LENGTH_LONG
            ).show()
        }

        override fun doInBackground(vararg params: String?): String {
            return try {
                val url =
                    URL("https://script.google.com/macros/s/AKfycbxFPYDlxlvIsff8hRBVxyPSMoWA3ZhNIrPb1EgsmX-6phH4dbDHwIT3sv3DACfmwQx6fw/exec")
                // https://script.google.com/macros/s/AKfycbyuAu6jWNYMiWt9X5yp63-hypxQPlg5JS8NimN6GEGmdKZcIFh0/exec
                val postDataParams = JSONObject()


                //    String usn = Integer.toString(i);
                val id = "1CSlf7YjepuKrtKGuFr-GJU1azjcn5YV7YHEOUnxQqAQ"
                postDataParams.put("topic", topic)
                postDataParams.put("timestamp", timestamp)
                postDataParams.put("reporttime", reporttime)
                postDataParams.put("desc", description)
                postDataParams.put("img", imageURL)
                postDataParams.put("id", id)
                postDataParams.put(("email"),userEmail)
                Log.e("params", postDataParams.toString())
                val conn = url.openConnection() as HttpURLConnection
                conn.readTimeout = 15000
                conn.connectTimeout = 15000
                conn.requestMethod = "POST"
                conn.doInput = true
                conn.doOutput = true
                val os = conn.outputStream
                val writer = BufferedWriter(
                    OutputStreamWriter(os, "UTF-8")
                )
                writer.write(getPostDataString(postDataParams))
                writer.flush()
                writer.close()
                os.close()
                val responseCode = conn.responseCode
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val `in` = BufferedReader(InputStreamReader(conn.inputStream))
                    val sb = StringBuffer("")
                    val intent = Intent(this@ReportActivity,ReportSucess::class.java)
                    intent.putExtra("ReportID", ID)
                    startActivity(intent)
                    var line: String? = ""
                    while (`in`.readLine().also { line = it } != null) {
                        sb.append(line)
                        break
                    }
                    `in`.close()
                    sb.toString()
                } else {
                    return("false : $responseCode")
                }
            } catch (e: Exception) {
                return("Exception: " + e.message)
            }
        }
    }

    @Throws(Exception::class)
    fun getPostDataString(params: JSONObject): String {
        val result = StringBuilder()
        var first = true
        val itr = params.keys()
        while (itr.hasNext()) {
            val key = itr.next()
            val value = params[key]
            if (first) first = false else result.append("&")
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }
        return result.toString()
    }

    private fun uploadFile() {
        if (filePath != null){
            Toast.makeText(applicationContext, "Uploading...", Toast.LENGTH_SHORT).show()
            val imageRef = storageReference!!.child("reportimages/"+ ID)
            //directory name on the firebase name image...+ the unique ID creation so make sure it is not having the same name
            imageRef.putFile(filePath!!)//upload file function!! "filepath" is the picture location
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "File uploaded", Toast.LENGTH_SHORT).show()
                    imageRef.downloadUrl.addOnSuccessListener {
                        imageURL1 = it.toString()
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount //calculate the progress!
                    Toast.makeText(applicationContext, "Uploaded "+progress.toInt()+"%..",Toast.LENGTH_SHORT).show()
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
            attachIMG.setImageBitmap(bitmap) //xml
            uploadFile()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    private fun loadProfile(){

        val user = auth.currentUser
        val userref = dbReference?.child(user?.uid!!)

        userref?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                INUserEmail = snapshot.child("email").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}

