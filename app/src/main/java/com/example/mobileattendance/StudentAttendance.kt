package com.example.mobileattendance

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_student_attendance.*
import java.text.SimpleDateFormat
import java.util.*

class StudentAttendance : AppCompatActivity() {
    val dtfrmt = SimpleDateFormat("dd_MM_yyyy")
    val df = dtfrmt.format(Date())
    val cv = ContentValues()
    val sub = Companion.Companion.sub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_attendance)

    }

    override fun onResume() {
        supportActionBar?.title = "Take Attendance in "+Companion.Companion.sub

        val hlp = Database(applicationContext)
        val db = hlp.readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub", null)

        if (rec.moveToFirst()){
            sroll.setText(rec.getString(1))
            sname.setText(rec.getString(2))
        }

        recnext.setOnClickListener {
            if (rec.moveToNext()){
                sroll.setText(rec.getString(1))
                sname.setText(rec.getString(2))
            }
            else{
                Toast.makeText(this, "No Data Found¡¡", Toast.LENGTH_SHORT).show()
            }
        }

        recprevious.setOnClickListener {
            if (rec.moveToPrevious()){
                sroll.setText(rec.getString(1))
                sname.setText(rec.getString(2))
            }
            else{
                Toast.makeText(this, "No Data Found¡¡", Toast.LENGTH_SHORT).show()
            }
        }

        absent.setOnClickListener {
            sat.setText("Absent")
            cv.put("[$df]",sat.text.toString())
            db.update("$sub",cv,"_id = ?", arrayOf(rec.getString(0)))
            Toast.makeText(this, "Absent Done!!", Toast.LENGTH_SHORT).show()
            rec.requery()
            cv.clear()
        }
        present.setOnClickListener {
            sat.setText("Present")
            cv.put("[$df]",sat.text.toString())
            db.update("$sub",cv,"_id = ?", arrayOf(rec.getString(0)))
            Toast.makeText(this, "Present Done!!", Toast.LENGTH_SHORT).show()
            rec.requery()
            cv.clear()
        }
        leave.setOnClickListener {
            sat.setText("Leave")
            cv.put("[$df]",sat.text.toString())
            db.update("$sub",cv,"_id = ?", arrayOf(rec.getString(0)))
            Toast.makeText(this, "Leave Done!!", Toast.LENGTH_SHORT).show()
            rec.requery()
            cv.clear()
        }
        super.onResume()
    }
}