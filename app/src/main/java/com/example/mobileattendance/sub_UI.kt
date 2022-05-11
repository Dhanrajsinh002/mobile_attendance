package com.example.mobileattendance

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_sub__u_i.*
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class sub_UI : AppCompatActivity() {
    val dtfrmt = SimpleDateFormat("dd_MM_yyyy")
    val df = dtfrmt.format(Date())
    val ysdf = dtfrmt.format(Date(System.currentTimeMillis() - (1000*60*60*24)))
    val sub = Companion.Companion.sub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub__u_i)
    }

    override fun onResume() {
        supportActionBar?.title = Companion.Companion.sub

        val hlp = Database(applicationContext)
        val db = hlp.readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub",null)

        try {
            db.execSQL("ALTER TABLE $sub  ADD COLUMN [$df] TEXT DEFAULT 0")
        }catch (e: Exception){

        }

        val arr = mutableListOf<String>()

        val stringBuilder = StringBuilder()
        while (rec.moveToNext())
        {
            stringBuilder.clear()
            stringBuilder.append("\nStudent RollNo. :    "+rec.getString(1)+"\n")
            stringBuilder.append("Student Name  :    "+rec.getString(2)+"\n")
            try {
                stringBuilder.append("$ysdf     :    "+rec.getString(rec.getColumnIndex("$ysdf"))+"\n")
            }catch (e:Exception){

            }
            stringBuilder.append("$df     :    "+rec.getString(rec.getColumnIndex("$df"))+"\n")
            arr.add(stringBuilder.toString())
        }

        val adptr = ArrayAdapter(this,android.R.layout.simple_list_item_1,arr)
        subuiList.adapter = adptr
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.takesat -> startActivity(Intent(this,StudentAttendance::class.java))
            R.id.addsdata -> startActivity(Intent(this,add_student::class.java))
            R.id.updtsdata -> startActivity(Intent(this,update_student::class.java))
            R.id.dltsdata -> startActivity(Intent(this,delete_student::class.java))
            R.id.expfile -> startActivity(Intent(this,datafile::class.java))
            R.id.about -> startActivity(Intent(this,about::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
