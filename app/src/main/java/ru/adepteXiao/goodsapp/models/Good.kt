package ru.adepteXiao.goodsapp.models

import java.util.Calendar

data class Good(
    var barcode: String,
    var name: String,
    var fromDate: Calendar,
    var toDate: Calendar
)