package ru.adepteXiao.goodsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.adepteXiao.goodsapp.databinding.ActivityAddGoodParamsBinding
import java.util.Locale
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

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
//    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
//    dateFormat.isLenient = false
//    try {
//        val productionDate = dateFormat.parse(date.toString())
//    } catch (e: ParseException) {
//        // Некорректный формат даты
//        Toast.makeText(applicationContext, "Некорректный формат даты", Toast.LENGTH_SHORT).show()
//    }
    private fun validateDate(date: String): Calendar? {
        val format = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        format.isLenient = false

        return try {
            val parsedDate = format.parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = parsedDate!!
            calendar
        } catch (e: Exception) {
            null
        }
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
            if (!(name.isNullOrEmpty() || prodDate.isNullOrEmpty() || expDate.isNullOrEmpty())) {
                val prodDateVal = validateDate(prodDate.toString())
                val expDateVal = validateDate(expDate.toString())
                if ((prodDateVal == null) || (expDateVal == null)) {
                    Toast.makeText(applicationContext,
                        "Неверный формат даты", Toast.LENGTH_SHORT).show()
                } else if (prodDateVal > expDateVal) {
                    Toast.makeText(applicationContext,
                        "Дата производства > Годен до", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    Log.d(
                        "debug",
                        "${binding.editGoodName.text}; " +
                                "${binding.editExpirationDate.text}; " +
                                "${binding.editProductionDate.text}"
                    )
                }
            } else {
                Toast.makeText(applicationContext, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}