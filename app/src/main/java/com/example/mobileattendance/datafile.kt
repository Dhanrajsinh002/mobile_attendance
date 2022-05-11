package com.example.mobileattendance

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_datafile.*
import java.io.File
import java.lang.Exception
import java.util.*

class datafile : AppCompatActivity() {
    val sub = Companion.Companion.sub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datafile)
    }

    override fun onResume() {
        supportActionBar?.title = "Export Data File"
        val hlp = Database(applicationContext)
        val db = hlp.readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub",null)

        export.setOnClickListener {
            if (TextUtils.isEmpty(expsroll.text.toString()))
            {
                Toast.makeText(this, "Roll No. field should not be Empty.", Toast.LENGTH_SHORT).show()
            }
            else if (rec.moveToLast())
            {
                if (rec.getString(1) < expsroll.text.toString())
                {
                    val alrt = AlertDialog.Builder(this)
                    alrt.setTitle("Warning!!")
                    alrt.setMessage("Entered Roll No ${expsroll.text} is not available in Database, Last Roll No in Database is ${rec.getString(1)}")
                    alrt.setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                    val dialog = alrt.create()
                    dialog.show()
                }
                else
                {
                    gen()
                }
            }
        }
        super.onResume()
    }

    fun gen(){
        val hlp = Database(applicationContext)
        val db = hlp.readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub",null)

        val col = 1..rec.columnCount
        val alrtbuild = AlertDialog.Builder(this)
        val name = db.rawQuery("SELECT * FROM $sub WHERE Stud_roll = ?", arrayOf(expsroll.text.toString()))

        alrtbuild.setTitle("Warning!!")
        if(name.moveToFirst())
        {
            alrtbuild.setMessage("You have Select Student = ${name.getString(2).toString()}'s Data to Write in File..")
        }
        alrtbuild.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
            try
            {
                if (name.moveToFirst())
                {
                    val arr = arrayListOf<String>("Subject      :  " + sub + "\n\n" + "Generated on  :  " + Date() + "\n\n")
                    for (x in col)
                    {
                        arr.add(name.getColumnName(x) + "    :    " + name.getString(x) + "\n")
                        val file = File(getExternalFilesDir(null), "File.txt")
                        file.delete()
                        file.writeText(arr.toString())
                        Toast.makeText(this, arr.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } catch (E: Exception) {
                Toast.makeText(this, "Fiile Exported Successfully!!", Toast.LENGTH_SHORT).show()
            }
        })
        alrtbuild.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() })
        val Dialog = alrtbuild.create()
        Dialog.show()
    }
}