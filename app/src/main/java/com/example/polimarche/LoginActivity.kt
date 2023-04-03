package com.example.polimarche

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Managers.Adapters.DataModelTeamMembers
import com.example.polimarche.Managers.Adapters.TeamMembersAdapterManager


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_managers_main_team)

        /*
        var listWorkshopAreas = mutableListOf(
            WorkshopArea("Telaio"),
            WorkshopArea("D"),
            WorkshopArea("C"),
            WorkshopArea("BB"),
            WorkshopArea("E"),
            WorkshopArea("AA"),
            WorkshopArea("N"),
        )
        var listMemberTeam = mutableListOf(
            TeamMember("1097931 : Alessandro"),
            TeamMember("1088392 : Francesco")
        )

         */

        val listMembers = mutableListOf(
            DataModelTeamMembers("Telaio", 0),
            DataModelTeamMembers("Aereodinamica", 0),
            DataModelTeamMembers("1097931 : Alessandro", 1),
            DataModelTeamMembers("1088392 : Francesco", 1),
            DataModelTeamMembers("Marketing", 0),
            DataModelTeamMembers("Elettronica", 0),
            DataModelTeamMembers("BOH", 0),
            DataModelTeamMembers("AA", 0),
            DataModelTeamMembers("N", 0),
            DataModelTeamMembers("1097931 : Alessandro", 1),
            DataModelTeamMembers("1088392 : Francesco", 1)
        )

        val adapter = TeamMembersAdapterManager(listMembers)
        var recyclerView = findViewById<RecyclerView>(R.id.list_members_workshop_areas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
/*
        setContentView(R.layout.activity_login_interface)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT

        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        val signIn: ImageButton = findViewById(R.id.signInButton)
        signIn.setOnClickListener {
            /*
            This section acquire the text written in
            the 2 different boxes of the activity_login_interface
            that, inside the OnClickListener method of the button,
            will compare with the values saved inside the database
             */
            val matriculation: String = findViewById<TextInputEditText>(R.id.MatricolaInput).text.toString()
            val password: String = findViewById<TextInputEditText>(R.id.PasswordInput).text.toString()

            val passwordEncrypted = enctyptSha256(password)
            /*
             TODO: CREARE LA CONNESSIONE AL DATABASE PER CONFRONTARE I MEMBRI
             TODO: E DETERMINARE SE SI E' LOGGATO UN CAPOREPARTO O UN RESPONSABILE
             */
            Intent(this, ManagersMain::class.java).also {
                it.putExtra("EXTRA_MATRICULATION", matriculation)
                it.putExtra("EXTRA_PASSWORD", passwordEncrypted)
                startActivity(it)
            }
        }

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

    /*
        This method return the password used inside
        the login interface encrypted in sha-256
     */
    private fun enctyptSha256(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }


}
 */
