package com.example.findmyage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import java.lang.Exception

class DatabaseManager {
    val dbName = "MyNotes"
    val dbTable = "Notes"
    val columnId = "ID"
    val columnTitle = "Title"
    val columnDesc = "Description"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable + " ("+
            columnId + " INTEGER PRIMARY KEY,"+
            columnTitle + " TEXT," +
            columnDesc + " TEXT);"
    var sqlDB :SQLiteDatabase?=null

    constructor(context: Context){
        val database = DatabaseHelperNotes(context)
        sqlDB = database.writableDatabase

    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{
        var context: Context?=null
        constructor(context : Context):super(context,dbName, null , dbVersion){
            this.context = context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context,"Database Created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop Table IF EXISTS " + dbTable)
        }

    }

    fun insertValues(values : ContentValues) : Long{
        return sqlDB!!.insert( dbTable, "", values)
    }

    fun Query(projection :Array<String>,selection: String, selectionArgs:Array<String>, sortOrder: String):Cursor{
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = dbTable
        return queryBuilder.query(sqlDB,projection,selection,selectionArgs,null,null,sortOrder)
    }

    fun deleteNote(selection: String, selectionArgs: Array<String>):Int{
        return sqlDB!!.delete(dbTable,selection,selectionArgs)
    }

    fun updateNote(values: ContentValues, selection: String,selectionArgs: Array<String>): Boolean {
//        return sqlDB!!.update(dbTable, values, selection, selectionArgs)
       try {
           sqlDB!!.execSQL(
               "UPDATE Notes SET Description = '" + values.getAsString("Description").toString() + "', Title = '" + values.getAsString(
                   "Title"
               ).toString() + "' WHERE ID=" + selectionArgs[0]+";"
           )
           return true
       }catch (ex: Exception){
            return false
       }
     }

}