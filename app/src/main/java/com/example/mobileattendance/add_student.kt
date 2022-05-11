package com.example.mobileattendance

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_add_student.*
import java.text.SimpleDateFormat
import java.util.*

class add_student : AppCompatActivity() {
    val dtfrmt = SimpleDateFormat("dd_MM_yyyy")
    val df = dtfrmt.format(Date())
    val cv = ContentValues()
    val sub = Companion.Companion.sub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
    }

    override fun onResume() {
        supportActionBar?.title = "Add Student in "+Companion.Companion.sub

        val hlp = Database(applicationContext)
        val db = hlp.readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub", null)

        if (rec.moveToLast())
        {
            null0.setText("Last Roll No. is "+rec.getString(1).toString())
        }
        else
        {
            null0.setText("Start From Roll No. 1")
            addsroll.setText("1")
        }

        insert.setOnClickListener {
            if (TextUtils.isEmpty(addsroll.text.toString()) or  TextUtils.isEmpty(addsname.text.toString()))
            {
                Toast.makeText(this, "Student RollNo and Student Name should not be empty¡¡", Toast.LENGTH_SHORT).show()
            }
            else
            {
                cv.put("Stud_roll",addsroll.text.toString())
                cv.put("Stud_name",addsname.text.toString().toLowerCase())

                db.insert("$sub",null,cv)
                Toast.makeText(this, "${addsroll.text}\n${addsname.text}\nAdd Successfully!!", Toast.LENGTH_SHORT).show()
                cv.clear()
                reset()
            }
            null0.setText("")
        }
        super.onResume()
    }

    fun reset() {
        addsroll.setText("")
        addsname.setText("")
    }
}
