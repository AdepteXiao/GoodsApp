package ru.adepteXiao.goodsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.adepteXiao.goodsapp.databinding.ActivityMainBinding
import ru.adepteXiao.goodsapp.models.dbModel.DatabaseModelFactory
import ru.adepteXiao.goodsapp.models.dbModel.DbModel
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.adepteXiao.goodsapp.GoodsApp
import ru.adepteXiao.goodsapp.R
import ru.adepteXiao.goodsapp.models.Converters
import ru.adepteXiao.goodsapp.models.Good
import ru.adepteXiao.goodsapp.models.dbModel.AllGoodsGotListener
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Locale

/**
 * Основная активность приложения GoodsApp.
 * Отвечает за создание основного пользовательского интерфейса и навигацию.
 */

class MainActivity : AppCompatActivity(), AllGoodsGotListener {

    private val dbModel: DbModel by viewModels  {
        DatabaseModelFactory(
            (application as GoodsApp).database.goodDao()
        )
    }

    /**
     * Binding экземпляр для layout активности.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Адаптер, используемый для отображения списка товаров в RecyclerView.
     */
    private lateinit var adapter: GoodsRecycleListAdapter


    /**
     * Метод, вызываемый при создании мэйн активити
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale("ru", "RU"))

        adapter = GoodsRecycleListAdapter(object : CardClickListener {

            /**
             * Метод, вызываемый при нажатии на карточку товара
             */
            override fun onCardClick(view: View, position: Int) {
                val good = adapter.goodsList[position]
                val intent = Intent("ru.adepteXiao.goodsapp.ACTION_ADD_PARAMS")
                intent.putExtra("barcode", good.barcode)
                intent.putExtra("name", good.name)
                intent.putExtra("fromDate",
                    good.fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                intent.putExtra("toDate",
                    good.toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                startActivity(intent)
            }
            /**
             * Метод, вызываемый при нажатии на элемент удаления
             */
            override fun onDelButtonClick(view: View, position: Int) {
                dbModel.deleteGood(adapter.goodsList[position])
                getGoods()
            }
        })


        binding.btnScan.setOnClickListener {
            val intent = Intent("ru.adepteXiao.goodsapp.ACTION_SCAN")
//            val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)
        }

        getGoods()
        val root: View = binding.root
        val recyclerView = root.findViewById<RecyclerView>(R.id.goods_list)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter

    }

    /**
     * Метод получения всех товаров из базы данных
     */
    fun getGoods() {
        dbModel.getAllGoods(this)
    }

    /**
     * Метод обновления адаптера
     */
    override fun onGoodsGot(goods: MutableList<Good>) {
        adapter.clearGoods()
        for (good in goods) {
            adapter.addGood(good)
        }
    }

}