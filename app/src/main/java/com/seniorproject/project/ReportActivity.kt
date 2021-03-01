package com.seniorproject.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_report.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
class ReportActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")
        val formatted = current.format(formatter)
        dateShowReport.text = "$formatted"
        // access the items of the list
        val languages = resources.getStringArray(R.array.Topics)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.topicReport)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    R.layout.custom_spinner, languages)
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)

            spinner.adapter = adapter
            var set:Int


            spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    val errorText = topicReport.selectedView as TextView

                    button2.setOnClickListener {
                        if (position == 0){
                            errorText.error = ""
                            errorText.requestFocus()
                            Toast.makeText(this@ReportActivity, "Please select another topics", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@ReportActivity,
                            getString(R.string.selected_item) + " " +
                                    "" + languages[position], Toast.LENGTH_SHORT).show()
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
    }

    private fun showPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select a photo"),PICK_IMAGE_REQUEST)
        //first argument = target (in this case, its var intent)
    }


}