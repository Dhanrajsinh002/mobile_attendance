package com.example.mobileattendance

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_update_student.*

class update_student : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)
    }

    override fun onResume() {
        val subname:String = Companion.sub
        supportActionBar?.title = "Update Student Data in "+subname

        val sub = Companion.Companion.sub
        val cv = ContentValues()
        val db = Database(applicationContext).readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub",null)

        if (rec.moveToFirst()){
            updtsroll.setText(rec.getString(1))
            updtsname.setText(rec.getString(2))
        }
        else
        {
            Toast.makeText(this, "No Records Found¡¡", Toast.LENGTH_SHORT).show()
        }

        updtnext.setOnClickListener {
            if (rec.moveToNext()){
                updtsroll.setText(rec.getString(1))
                updtsname.setText(rec.getString(2))
            }
            else{
                Toast.makeText(this, "No Records Found¡¡", Toast.LENGTH_SHORT).show()
            }
        }

        updtprevious.setOnClickListener {
            if (rec.moveToPrevious()){
                updtsroll.setText(rec.getString(1))
                updtsname.setText(rec.getString(2))
            }
            else{
                Toast.makeText(this, "No Records Found¡¡", Toast.LENGTH_SHORT).show()
            }
        }

        update.setOnClickListener {
            if (TextUtils.isEmpty(updtsroll.text.toString()) or  TextUtils.isEmpty(updtsname.text.toString()))
            {
                Toast.makeText(this, "Student RollNo and Student Name should not be empty¡¡", Toast.LENGTH_SHORT).show()
            }
            else
            {
                cv.put("Stud_roll",updtsroll.text.toString())
                cv.put("Stud_name",updtsname.text.toString())

                db.update("$sub",cv,"_id = ?", arrayOf(rec.getString(0)))
                Toast.makeText(this, "Updated Successfully!!", Toast.LENGTH_SHORT).show()
                cv.clear()
            }
        }

        super.onResume()
    }
}