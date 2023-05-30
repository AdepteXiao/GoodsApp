package ru.adepteXiao.goodsapp.models.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.adepteXiao.goodsapp.models.Converters

/**
 * Сущность, представляющая товар в базе данных.
 *
 * @property barcode Штрих-код товара.
 * @property goodName Наименование товара.
 * @property fromDate Дата начала срока годности товара.
 * @property toDate Дата окончания срока годности товара.
 */
@Entity(tableName = "goods")
data class GoodEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "barcode")
    var barcode: String,

    @ColumnInfo(name = "good_name")
    var goodName: String,

    @ColumnInfo(name = "fromDate")
    var fromDate: String,

    @ColumnInfo(name = "toDate")
    var toDate: String
)