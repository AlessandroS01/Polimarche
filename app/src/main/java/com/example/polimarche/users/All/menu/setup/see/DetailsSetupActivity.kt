package com.example.polimarche.users.all.menu.setup.see

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.polimarche.databinding.ActivityGeneralDetailsSetupBinding
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel

class DetailsSetupActivity: AppCompatActivity() {

    private val setupViewModel: SetupViewModel by viewModels()

    private lateinit var setup: DataSetup

    private lateinit var binding : ActivityGeneralDetailsSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGeneralDetailsSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        // Impostare la finestra corrente in uno stato in cui gli input utente non sono intercettabili o gestiti
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        // Valore di fallback (-1) da restituire nel caso in cui l'extra non sia presente nell'intent.
        val setupCode = intent.getIntExtra("SETUP_CODE", -1)


        setupViewModel.setupList.observe(this) {
                // Ripristinare la normale interattività agli input utente
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            // Tutti i dati del setup trovati tramite codice del setup
                val filteredList = setupViewModel.setupList.value?.filter { it.code == setupCode }
                    ?.toMutableList()!!

            // Assegna il primo elemento della lista filteredList alla variabile setup
            // Se filteredList è nullo, l'espressione restituisce null.
                this.setup = filteredList?.get(0)!!
                binding.setupCodeSeeSetup.text = "Setup code: ${this.setup.code}"

                binding.frontRightWheelCodeSeeSetup.text = this.setup.frontRightWheel.code.toString()
                binding.frontLeftWheelCodeSeeSetup.text = this.setup.frontLeftWheel.code.toString()
                binding.rearRightWheelCodeSeeSetup.text = this.setup.rearRightWheel.code.toString()
                binding.rearLeftWheelCodeSeeSetup.text = this.setup.rearLeftWheel.code.toString()

                binding.frontEndBalanceBrake.text = this.setup.frontBalance.brake.toString()
                binding.frontEndBalanceWeight.text = this.setup.frontBalance.weight.toString()

                binding.backEndBalanceBrake.text = this.setup.backBalance.brake.toString()
                binding.backEndBalanceWeight.text = this.setup.backBalance.weight.toString()

                binding.frontWingHoleSeeSetup.text = this.setup.frontWingHole

                binding.frontDamperCodeSeeSetup.text = this.setup.frontDamper.code.toString()
                binding.backDamperCodeSeeSetup.text = this.setup.backDamper.code.toString()

                binding.frontSpringCodeSeeSetup.text = this.setup.frontSpring.code.toString()
                binding.backSpringCodeSeeSetup.text = this.setup.backSpring.code.toString()

                binding.eventPreferredSeeSetup.text = this.setup.preferredEvent

                var notes = ""

            // Itera attraverso gli elementi della lista notes nell'oggetto setup e crea una stringa notes
            // che contiene tutti i valori delle note separati da un carattere di nuova riga (\n)
                this.setup.notes.forEachIndexed { index, s ->
                    notes += if (index == this.setup.notes.size - 1) s else "${s}\n"
                }

                binding.notesSeeSetup.text = notes

            }

        val backButton : ImageButton = binding.backButtonDetailSetup
        backButton.setOnClickListener {
            finish()
        }

        /*
        Consente all'utente di navigare dai dettagli di un setup al frame che
        riporta tutti i dettagli della singola ruota.
         */
        val frontRightWheel: ImageView = binding.frontRightWheelDetails
        val frontLeftWheel: ImageView = binding.frontLeftWheelDetails
        val rearRightWheel: ImageView = binding.rearRightWheelDetails
        val rearLeftWheel: ImageView = binding.rearLeftWheelDetails
        frontRightWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Front right")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        frontLeftWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Front left")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        rearRightWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Rear right")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        rearLeftWheel.setOnClickListener {
            Intent(this, DetailsWheelActivity::class.java).apply {
                this.putExtra("WHEEL_POSITION", "Rear left")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }

        /*
        Consente all'utente di navigare dai dettagli di un setup al frame che
        riporta tutti i dettagli di ogni singolo damper.
         */
        val frontDamper: ImageView = binding.frontDamperDetails
        val backDamper: ImageView = binding.backDamperDetails
        frontDamper.setOnClickListener {
            Intent(this, DetailsDamperActivity::class.java).apply {
                this.putExtra("DAMPER_POSITION", "Front")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        backDamper.setOnClickListener {
            Intent(this, DetailsDamperActivity::class.java).apply {
                this.putExtra("DAMPER_POSITION", "End")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }

        /*
        Consente all'utente di navigare dai dettagli di un setup al frame che
        riporta tutti i dettagli di ogni singolo spring.
         */
        val frontSpring: ImageView = binding.frontSpringDetails
        val backSpring: ImageView = binding.backSpringDetails
        frontSpring.setOnClickListener {
            Intent(this, DetailsSpringActivity::class.java).apply {
                this.putExtra("SPRING_POSITION", "Front")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
            }
        }
        backSpring.setOnClickListener {
            Intent(this, DetailsSpringActivity::class.java).apply {
                this.putExtra("SPRING_POSITION", "Back")
                this.putExtra("SETUP_CODE", setup.code)
                startActivity(this)
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
}