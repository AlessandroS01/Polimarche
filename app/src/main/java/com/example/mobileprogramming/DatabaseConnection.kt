package com.example.mobileprogramming

import java.sql.SQLException
import java.sql.DriverManager

class DatabaseConnection {

    fun openConnection(vararg params: String?) {
        try{
            val connection = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7608943",
                "sql7608943", "mcwqGyUN5h")
            println("OK DSFVGBHNJMKJNHBGVFCDFVGBHNJHBGVFCDCFGHJHGFCDXFGHJHGFDFGHHGF")
        }
        catch (e : SQLException){
            e.printStackTrace()
        }


    }
}