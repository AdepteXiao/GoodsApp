package ru.adepteXiao.goodsapp.models

import java.util.Calendar

/**
 * Модель данных для товара.
 *
 * @property barcode Штрих-код товара.
 * @property name Наименование товара.
 * @property fromDate Дата начала срока годности товара.
 * @property toDate Дата окончания срока годности товара.
 */
data class Good(
    var barcode: String,
    var name: String,
    var fromDate: Calendar,
    var toDate: Calendar
)