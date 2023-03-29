package com.example.polimarche

import java.sql.Connection
import java.sql.SQLException
import java.sql.DriverManager

object Database {
    fun getConnection(): Connection? {
        var conn: Connection? = null
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            val url = "jdbc:mysql://sql7.freesqldatabase.com/sql7608943?useSSL=false"
            conn = DriverManager.getConnection(url, "sql7608943", "mcwqGyUN5h")
            println("OK")
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return conn
    }
}