package ru.adepteXiao.goodsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.adepteXiao.goodsapp.GoodsApp
import ru.adepteXiao.goodsapp.databinding.ActivityAddGoodParamsBinding
import ru.adepteXiao.goodsapp.models.Good
import ru.adepteXiao.goodsapp.models.dbModel.DatabaseModelFactory
import ru.adepteXiao.goodsapp.models.dbModel.DbModel
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.exp

/**
 *   Активность, отвечающая за добавление параметров товара.
 */
class AddParamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGoodParamsBinding

    private val dbModel: DbModel by viewModels  {
        DatabaseModelFactory(
            (application as GoodsApp).database.goodDao()
        )
    }

    private lateinit var barcode: String
    private var name: String? = null
    private var fromDate: String? = null
    private var toDate: String? = null

    /**
     *  Метод, извлекающий данные из намерения (Intent), переданные из предыдущей активности
     *  (сканирование), и сохраняет их в соответствующих свойствах.
     */
    private fun handleScan() {
        intent.getStringExtra("barcode")?.let{barcode = it}
        intent.getStringExtra("name")?.let{name = it}
        intent.getStringExtra("fromDate")?.let{fromDate = it}
        intent.getStringExtra("toDate")?.let{toDate = it}
    }

    /**
     *  Метод проверки формата даты и преобразования его в объект Calendar.
     */
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

    /**
     *  Вызывается при успешном добавлении товара в базу данных. Вставляет товар в базу данных с
     *  помощью метода insertGood() модели базы данных
     */
    private fun success(good: Good) {
        dbModel.insertGood(good)
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        Log.d(
            "debug",
            "${binding.editGoodName.text}; " +
                    "${binding.editExpirationDate.text}; " +
                    "${binding.editProductionDate.text}"
        )
    }

    /**
     *  Метод, вызываемый при создании активности
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoodParamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleScan()

        name?.let {
            binding.editGoodName.setText(it)
        }
        fromDate?.let {
            binding.editProductionDate.setText(it)
        }
        toDate?.let {
            binding.editExpirationDate.setText(it)
        }


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
                    success(Good(barcode, name.toString(), prodDateVal, expDateVal))
                }
            } else {
                Toast.makeText(applicationContext, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}