package com.example.studentmanager_excercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var studentIdEditText: EditText
    private lateinit var student: Student
    private var position: Int = -1  // To store the position of the student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        nameEditText = findViewById(R.id.nameEditText)
        studentIdEditText = findViewById(R.id.studentIdEditText)

        // Receive the student object and position from the Intent
        student = intent.getParcelableExtra("student")!!
        position = intent.getIntExtra("position", -1)

        // Populate the EditText fields with the current student's data
        nameEditText.setText(student.name)
        studentIdEditText.setText(student.studentId)
    }

    fun onSaveClick(view: View) {
        val newName = nameEditText.text.toString()
        val newStudentId = studentIdEditText.text.toString()

        if (newName.isNotEmpty() && newStudentId.isNotEmpty()) {
            // Create a new Student object with the updated data
            val updatedStudent = student.copy(name = newName, studentId = newStudentId)

            // Return the updated student and position to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("editedStudent", updatedStudent)
            resultIntent.putExtra("position", position)  // Pass back the position
            setResult(RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
        }
    }
}

