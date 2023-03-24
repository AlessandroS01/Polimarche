package com.example.mobileprogramming

import java.security.MessageDigest
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Login(matriculationNumber: String, password: String) {

    init {
        val encryptedPassword = sha256(password)
        println(encryptedPassword)
        val conn = getConnection()
        try {
            val stmt = conn?.createStatement()
            val rs = stmt?.executeQuery("SELECT * FROM member")
            while (rs?.next() == true) {
                val name = rs.getString("password")
                // Do something with the data
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            conn?.close()
        }
    }

    /*
        This method return the password used inside
        the login interface already encrypted in sha-256
     */
    private fun sha256(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }


    fun getConnection(): Connection? {
        var conn: Connection? = null
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Set up the connection parameters
            val url = "jdbc:mysql://localhost:3306/polimarche"
            val user = "root"
            val password = "lollinaro2014"
            // Establish the connection
            conn = DriverManager.getConnection(url, user, password)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } catch (ex: ClassNotFoundException) {
            ex.printStackTrace()
        }
        return conn
    }
}