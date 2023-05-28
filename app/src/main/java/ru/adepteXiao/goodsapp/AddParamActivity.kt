package ru.adepteXiao.goodsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.adepteXiao.goodsapp.databinding.ActivityAddGoodParamsBinding
import ru.adepteXiao.goodsapp.databinding.ActivityMainBinding

class AddParamActivity : AppCompatActivity() {

    companion object {
        const val RESULT = "RESULT"
    }

    private lateinit var binding: ActivityAddGoodParamsBinding

    private fun handleScan(): String? {
        val result = intent.getStringExtra(RESULT)
        if (result != null) {
            return result.toString()
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoodParamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val qrText = handleScan()

        binding.buttonConfirm.setOnClickListener {
            val name = binding.editGoodName.text
            val prodDate = binding.editProductionDate.text
            val expDate = binding.editExpirationDate.text
            if ( name.isEmpty() || prodDate.isEmpty() || expDate.isEmpty()) {
                Toast.makeText(applicationContext, "Заполните все поля!", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                Log.d(
                    "debug",
                    "${binding.editGoodName.text}; ${binding.editExpirationDate.text}; ${binding.editProductionDate.text}"
                )
            }
        }


    }
}