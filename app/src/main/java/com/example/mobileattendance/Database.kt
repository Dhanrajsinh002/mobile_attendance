package com.example.mobileattendance

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context):SQLiteOpenHelper(context,"Attendance",null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE Subject (Subject TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}