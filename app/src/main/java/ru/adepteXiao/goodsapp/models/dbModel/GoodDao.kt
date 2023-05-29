package ru.adepteXiao.goodsapp.models.dbModel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(goodEntity: GoodEntity)

    @Delete
    suspend fun delete(goodEntity: GoodEntity)

    @Update
    suspend fun update(goodEntity: GoodEntity)

    @Query("SELECT * from goods")
    fun getAll(): Flow<List<GoodEntity>>

    @Query("SELECT COUNT(*) from goods WHERE barcode = :barcode")
    fun hasItemWithBarcode(barcode: String): Int
}