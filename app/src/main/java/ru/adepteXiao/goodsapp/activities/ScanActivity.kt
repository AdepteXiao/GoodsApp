package ru.adepteXiao.goodsapp.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.zxing.Result
import ru.adepteXiao.goodsapp.GoodsApp
import ru.adepteXiao.goodsapp.models.Good
import ru.adepteXiao.goodsapp.models.dbModel.DatabaseModelFactory
import ru.adepteXiao.goodsapp.models.dbModel.DbModel
import ru.adepteXiao.goodsapp.models.dbModel.ItemCheckListener


/**
 *  Активность, отвечающая за сканирование штрихкодов с помощью камеры устройства.
 */
class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, ItemCheckListener {
    private var scannerView: ZXingScannerView? = null

    lateinit var barcode: String

    private val dbModel: DbModel by viewModels  {
        DatabaseModelFactory(
            (application as GoodsApp).database.goodDao()
        )
    }

    /**
     *  Метод, вызываемяй  при создании активности. Инициализирует ZXingScannerView и
     *  устанавливает его в качестве контента активности. Устанавливает разрешение
     *  на использование камеры.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)

        setPermission()
    }

    /**
     *  Метод, обрабатывающий результат сканирования штрихкода. Получает значение штрихкода
     *  и вызывает метод checkInDb() модели базы данных для проверки наличия товара в базе данных.
     */
    override fun handleResult(p0: Result?) {
        barcode = p0.toString()
        Log.d("BARCODE DEBUG", barcode)
        dbModel.checkInDb(this, barcode)
    }

    /**
     *  Метод вызываемый при проверке наличия товара в базе данных. Создает намерение
     *  (Intent) для перехода на активность AddParamActivity и передает информацию
     *  о штрихкоде и имени товара.
     */
    override fun onItemChecked(response: Good?) {
        val intent = Intent(applicationContext, AddParamActivity::class.java)
        intent.putExtra("barcode", barcode)
        response?.let { intent.putExtra("name", it.name) }
        startActivity(intent)
    }

    /**
     *  Метод, вызываемый при возобновлении активности. Устанавливает ZXingScannerView
     *  в качестве обработчика результатов сканирования и запускает камеру для сканирования.
     */
    override fun onResume() {
        super.onResume()

        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    /**
     *  Метод, вызываемый при остановке активности. Останавливает камеру и возвращает результат назад.
     */
    override fun onStop() {
        super.onStop()
        scannerView?.stopCamera()
        onBackPressed()
    }

    /**
     *  Метод, проверяющий наличие разрешения на использование камеры. Если разрешение
     *  не предоставлено, вызывает метод makeRequest() для запроса разрешения.
     */
    private fun setPermission() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    /**
     *   Метод, запрашивающий разрешение на использование камеры у пользователя.
     */
    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            1
        )
    }

    /**
     *  Метод, вызываемый после получения результата запроса разрешения на использование камеры.
     *  Если разрешение не предоставлено, отображает сообщение об ошибке.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "You need camera permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }


}