package com.example.studentmanager_excercise

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var studentListView: ListView
    private var studentList: MutableList<Student> = mutableListOf()

    companion object {
        private const val ADD_STUDENT_REQUEST = 1
        private const val EDIT_STUDENT_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentListView = findViewById(R.id.studentListView)

        // Sử dụng adapter tùy chỉnh
        val adapter = StudentAdapter(this, R.layout.student_item, studentList)
        studentListView.adapter = adapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(studentListView)

        // Kiểm tra và hiển thị danh sách sinh viên
        loadStudentData(adapter)
    }

    private fun loadStudentData(adapter: StudentAdapter) {
        // Thêm dữ liệu mẫu vào danh sách
        studentList.addAll(getStudentData())
        adapter.notifyDataSetChanged()
    }

    private fun getStudentData(): List<Student> {
        return listOf(
            Student("Nguyễn Văn An", "SV001"),
            Student("Trần Thị Bảo", "SV002"),
            Student("Lê Hoàng Cường", "SV003"),
            Student("Phạm Thị Dung", "SV004"),
            Student("Đỗ Minh Đức", "SV005"),
            Student("Vũ Thị Hoa", "SV006"),
            Student("Hoàng Văn Hải", "SV007"),
            Student("Bùi Thị Hạnh", "SV008"),
            Student("Đinh Văn Hùng", "SV009"),
            Student("Nguyễn Thị Linh", "SV010"),
            Student("Phạm Văn Long", "SV011"),
            Student("Trần Thị Mai", "SV012"),
            Student("Lê Thị Ngọc", "SV013"),
            Student("Vũ Văn Nam", "SV014"),
            Student("Hoàng Thị Phương", "SV015"),
            Student("Đỗ Văn Quân", "SV016"),
            Student("Nguyễn Thị Thu", "SV017"),
            Student("Trần Văn Tài", "SV018"),
            Student("Phạm Thị Tuyết", "SV019"),
            Student("Lê Văn Vũ", "SV020")

        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, ADD_STUDENT_REQUEST)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_STUDENT_REQUEST) {
                val newStudent = data?.getParcelableExtra<Student>("newStudent")
                newStudent?.let {
                    studentList.add(it)
                    val adapter = studentListView.adapter as StudentAdapter
                    adapter.notifyDataSetChanged()
                }
            }
            if (requestCode == EDIT_STUDENT_REQUEST) {
                val editedStudent = data?.getParcelableExtra<Student>("editedStudent")
                val position = data?.getIntExtra("position", -1) ?: -1
                editedStudent?.let {
                    if (position != -1) {
                        // Update the student at the specified position
                        studentList[position] = it
                        val adapter = studentListView.adapter as StudentAdapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }




    // Context menu - Hiển thị khi nhấn lâu vào một sinh viên
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý khi người dùng chọn một mục trong context menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedStudent = studentList[info.position]

        return when (item.itemId) {
            R.id.edit -> {
                // Pass the student and position to EditStudentActivity
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("student", selectedStudent)
                intent.putExtra("position", info.position)  // Pass the position
                startActivityForResult(intent, EDIT_STUDENT_REQUEST)
                true
            }
            R.id.remove -> {
                // Remove the student from the list
                studentList.removeAt(info.position)
                val adapter = studentListView.adapter as StudentAdapter
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}
