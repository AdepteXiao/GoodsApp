package ru.adepteXiao.goodsapp.models

import androidx.room.TypeConverter
import ru.adepteXiao.goodsapp.models.dbModel.GoodEntity
import java.util.Calendar

/**
 * Класс, предоставляющий преобразователи для конвертации между различными типами данных.
 */
class Converters {
    /**
     * Преобразует объект типа [Calendar] в строку.
     *
     * @param calendar Объект типа [Calendar] для преобразования.
     * @return Строковое представление объекта типа [Calendar].
     */
    @TypeConverter
    fun calendarToString(calendar: Calendar): String {
        return calendar.timeInMillis.toString()
    }

    /**
     * Преобразует строку в объект типа [Calendar].
     *
     * @param value Строковое представление объекта типа [Calendar].
     * @return Объект типа [Calendar], восстановленный из строки.
     */
    @TypeConverter
    fun stringToCalendar(value: String): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value.toLong()
        return calendar
    }

    /**
     * Преобразует объект типа [Good] в объект типа [GoodEntity].
     *
     * @param good Объект типа [Good] для преобразования.
     * @return Объект типа [GoodEntity], полученный из объекта типа [Good].
     */
    @TypeConverter
    fun goodToGoodEntity(good: Good): GoodEntity {
        return GoodEntity(
            good.barcode,
            good.name,
            calendarToString(good.fromDate),
            calendarToString(good.toDate)
        )
    }

    /**
     * Преобразует объект типа [GoodEntity] в объект типа [Good].
     *
     * @param goodEntity Объект типа [GoodEntity] для преобразования.
     * @return Объект типа [Good], полученный из объекта типа [GoodEntity].
     */
    @TypeConverter
    fun goodEntityToGood(goodEntity: GoodEntity): Good {
        return Good(
            goodEntity.barcode,
            goodEntity.goodName,
            stringToCalendar(goodEntity.fromDate),
            stringToCalendar(goodEntity.toDate)
        )
    }
}
