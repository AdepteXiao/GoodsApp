package ru.adepteXiao.goodsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.adepteXiao.goodsapp.databinding.ActivityMainBinding
import ru.adepteXiao.goodsapp.models.dbModel.DatabaseModelFactory
import ru.adepteXiao.goodsapp.models.dbModel.DbModel
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {

    private val dbModel: DbModel by viewModels  {
        DatabaseModelFactory(
            (application as GoodsApp).database.goodDao()
        )
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScan.setOnClickListener {
            val intent = Intent("ru.adepteXiao.goodsapp.ACTION_SCAN")
//            val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)
        }


    }
}