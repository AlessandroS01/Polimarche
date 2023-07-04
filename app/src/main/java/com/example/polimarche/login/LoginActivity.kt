package com.example.polimarche.login

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inizializza Firebase
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_login)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT

        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutLoginSignIn, loginFragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }
        /*
        val firebaseFirestore = FirebaseFirestore.getInstance()
        // Assuming you have initialized the Firebase Firestore instance and have a reference to the existing item
        val existingItemRef = firebaseFirestore.collection("setup").document("HL231voQtS0EslAVU0o3")

        val arrayDocument: MutableList<String> = mutableListOf("zjjOVwZLA3QuI4CN8xQk", "cxahVhfu3PH8PyNsVshM", "3CTDoR5G3hCqDdYPEvkM")

        arrayDocument.forEach { doc ->
            // Create a new document reference for the destination location
        val newItemRef = firebaseFirestore.collection("setup").document(doc)

        // Retrieve the existing item
        existingItemRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                // Get the data from the existing item
                val existingItemData = documentSnapshot.data

                // Create a new document with the same data as the existing item
                if (existingItemData != null) {
                    newItemRef.set(existingItemData)
                        .addOnSuccessListener {
                            // The new item has been successfully created
                            // You can perform any additional operations here if needed
                        }
                        .addOnFailureListener {
                            // An error occurred while creating the new item
                            // Handle the error accordingly
                        }
                }
            } else {
                // The existing item does not exist or could not be retrieved
                // Handle the case accordingly
            }
        }.addOnFailureListener { exception ->
            // An error occurred while retrieving the existing item
            // Handle the error accordingly
        }
        }

 */





    }

    /*
        This method is used to set the status bar
        completely transparent but keeping the icon at the top
        of the layout
     */
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onBackPressed(){
        moveTaskToBack(false);
    }


}
