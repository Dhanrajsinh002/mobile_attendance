package com.example.mobileattendance

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_delete_student.*

class delete_student : AppCompatActivity() {
    val sub = Companion.Companion.sub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_student)
    }

    override fun onResume() {

        supportActionBar?.title = "Delete Student From "+Companion.Companion.sub
        val hlp = Database(applicationContext)
        val db = hlp.readableDatabase
        val rec = db.rawQuery("SELECT * FROM $sub",null)

        if (rec.moveToFirst())
        {
            delsroll.setText(rec.getString(1))
            delsname.setText(rec.getString(2))
        }
        else
        {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            delprevious.isEnabled = false
            delnext.isEnabled = false
            delete.isEnabled = false
        }

        delnext.setOnClickListener {
            if (rec.moveToNext())
            {
                delsroll.setText(rec.getString(1))
                delsname.setText(rec.getString(2))
            }
            else
            {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        delprevious.setOnClickListener {
            if (rec.moveToPrevious())
            {
                delsroll.setText(rec.getString(1))
                delsname.setText(rec.getString(2))
            }
            else
            {
                Toast.makeText(this, "no Data Found", Toast.LENGTH_SHORT).show()
            }
        }

        delete.setOnClickListener {
            val alrt = AlertDialog.Builder(this)
            alrt.setTitle("Delete Data!!")
            alrt.setMessage("Are You sure you want to Delete Data?")
            alrt.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                db.delete("$sub", "_id = ?", arrayOf(rec.getString(0)))
                Toast.makeText(this, "Data Delete Successfully", Toast.LENGTH_SHORT).show()
            })
            alrt.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            val Dialog = alrt.create()
            Dialog.show()
        }
        super.onResume()
        rec.requery()
    }
}