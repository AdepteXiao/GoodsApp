package ru.adepteXiao.goodsapp.models

import androidx.room.TypeConverter
import ru.adepteXiao.goodsapp.models.dbModel.GoodEntity
import java.util.Calendar

class Converters {

    @TypeConverter
    fun calendarToString(calendar: Calendar): String {
        return calendar.timeInMillis.toString()
    }

    @TypeConverter
    fun stringToCalendar(value: String): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value.toLong()
        return calendar
    }


    @TypeConverter
    fun goodToGoodEntity(good: Good): GoodEntity {
        return GoodEntity(
            good.barcode,
            good.name,
            calendarToString(good.fromDate),
            calendarToString(good.toDate)
        )
    }

    @TypeConverter
    fun goodEntityToGood(goodEntity: GoodEntity) : Good {
        return Good(
            goodEntity.barcode,
            goodEntity.goodName,
            stringToCalendar(goodEntity.fromDate),
            stringToCalendar(goodEntity.toDate)
        )
    }
}