package com.malov.bodyfat.DataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context
) : SQLiteOpenHelper(context, db_name, null, db_ver) {
    companion object {
        private val db_name: String = "myDb"
        private val db_ver: Int = 1
        private val db_table: String = "personName"
        private val db_colum: String = "personNum"
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?) {
        val query: String = String.format("CREATE TABLE $db_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, $db_colum TEXT NOT NULL);")
            db!!.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        val query: String = String.format("DELETE TABLE IF EXISTS $db_table")
//        db!!.execSQL(query)
//        onCreate(db)
    }

    fun addUserName(name: String){
        val db : SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(db_colum, name)
        db.insert(db_table, null, values)
        db.close()
    }

    fun getUserName(): String{
        var listName : ArrayList<String> = ArrayList()
        val db : SQLiteDatabase = this.readableDatabase
        var cursor : Cursor = db.query(db_table, arrayOf(db_colum), null,null,null,null,null )
        while (cursor.moveToNext()){
            var index : Int = cursor.getColumnIndex(db_colum)
            listName.add(cursor.getString(index))
        }
        cursor.close()
        db.close()
        val name : String = listName[0]
        return name
    }
}
