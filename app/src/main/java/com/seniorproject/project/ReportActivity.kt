package com.seniorproject.project


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_report.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
class ReportActivity : AppCompatActivity() {

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

            spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                 //do smth to send data
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@ReportActivity, "Please select one of the topics", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}