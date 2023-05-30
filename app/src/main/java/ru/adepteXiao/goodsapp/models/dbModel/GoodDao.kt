package ru.adepteXiao.goodsapp.models.dbModel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) для работы с товарами в базе данных.
 */
@Dao
interface GoodDao {
    /**
     * Вставляет товар в базу данных или заменяет его, если он уже существует.
     *
     * @param goodEntity Сущность товара.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goodEntity: GoodEntity)

    /**
     * Удаляет товар из базы данных.
     *
     * @param goodEntity Сущность товара.
     */
    @Delete
    suspend fun delete(goodEntity: GoodEntity)

    /**
     * Обновляет информацию о товаре в базе данных.
     *
     * @param goodEntity Сущность товара.
     */
    @Update
    suspend fun update(goodEntity: GoodEntity)

    /**
     * Получает все товары из базы данных.
     *
     * @return [Flow] списка всех сущностей товаров.
     */
    @Query("SELECT * from goods")
    fun getAll(): Flow<List<GoodEntity>>

    /**
     * Проверяет наличие товара с указанным штрих-кодом в базе данных.
     *
     * @param barcode Штрих-код товара.
     * @return [Flow] списка сущностей товаров с указанным штрих-кодом.
     */
    @Query("SELECT * from goods WHERE barcode = :barcode")
    fun hasItemWithBarcode(barcode: String): Flow<List<GoodEntity>>
}