package com.example.mobileprogramming

import java.sql.SQLException
import java.sql.DriverManager

class DatabaseConnection {

    val url = "jdbc:mysql://sql7.freesqldatabase.com/sql7608943"
    val user = "sql7608943"
    val password = "mcwqGyUN5h"

    fun openConnection(vararg params: String?) {
        try{
            val connection = DriverManager.getConnection(url, user, password)
            println("OK DSFVGBHNJMKJNHBGVFCDFVGBHNJHBGVFCDCFGHJHGFCDXFGHJHGFDFGHHGF")
            val statm = connection.createStatement()
            val reslt = statm.executeQuery("select * from member")
            while(reslt.next()){
                println(reslt.getBigDecimal("matriculation_number"))
            }
        }
        catch (e : SQLException){
            e.printStackTrace()
        }


    }
}