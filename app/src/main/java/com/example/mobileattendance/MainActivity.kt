package com.example.mobileattendance

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mobileattendance.util.Companion
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.addsubj.view.*

class MainActivity : AppCompatActivity() {
    val data = mutableListOf<String>()
    val cv = ContentValues()
    lateinit var db : SQLiteDatabase
    lateinit var ss : Cursor
    lateinit var myadapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        db = Database(applicationContext).readableDatabase
        ss = db.rawQuery("SELECT * FROM Subject",null)

        data.clear()
        while(ss.moveToNext())
        {
            data.add(ss.getString(0))
        }

        myadapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,data)
        subList.adapter = myadapter

        addSubj.setOnClickListener{
            val dialogview = LayoutInflater.from(this).inflate(R.layout.addsubj, null)
            val builder = AlertDialog.Builder(this).setView(dialogview).setTitle("Add Subject")
            val showdialog = builder.show()

            dialogview.dialogAddBtn.setOnClickListener{
                val sname = dialogview.dialogSubNameEt.getText().toString().toLowerCase()
                if (TextUtils.isEmpty(sname))
                {
                    Toast.makeText(this, "Subject Field is Null¡¡", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    if (data.contains(sname))
                    {
                        showdialog.dismiss()
                        val alrtbuild = android.app.AlertDialog.Builder(this)
                        alrtbuild.setTitle("Same Name")
                        alrtbuild.setMessage("Subject Name you have Entered is already Created")
                        alrtbuild.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                        val Dialog = alrtbuild.create()
                        Dialog.show()
                    }
                    else
                    {
                        data.add(dialogview.dialogSubNameEt.text.toString().toLowerCase())
                        val tableName = dialogview.dialogSubNameEt.text.toString().toLowerCase()
                        db.execSQL("CREATE TABLE $tableName (_id INTEGER PRIMARY KEY AUTOINCREMENT, Stud_roll INTEGER,Stud_name TEXT)")

                        cv.put("Subject",tableName)
                        db.insert("Subject",null,cv)

                        myadapter.notifyDataSetChanged()
                        showdialog.dismiss()
                    }
                }
            }
            dialogview.dialogCancelBtn.setOnClickListener {
                showdialog.dismiss()
            }
        }

        subList.setOnItemLongClickListener { adapterView, view, i, l ->
            val sub = subList.getItemAtPosition(i).toString()
            val alrtbuild = android.app.AlertDialog.Builder(this)
            alrtbuild.setTitle("Delete Subject")
            alrtbuild.setMessage("Are you sure you want to Delete Subject?")
            alrtbuild.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                db.execSQL("DROP TABLE IF EXISTS $sub")
                db.delete("Subject","Subject = ?", arrayOf(sub))
                data.remove(sub)
                myadapter.notifyDataSetChanged()
            })
            alrtbuild.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            val Dialog = alrtbuild.create()
            Dialog.show()

            return@setOnItemLongClickListener true
        }

        subList.setOnItemClickListener { adapterView, view, i, l ->

            val sub = subList.getItemAtPosition(i)
            Companion.sub = sub.toString()
            startActivity(Intent(this,sub_UI::class.java))
        }
        super.onResume()
    }
}
