package io.ilyasin.logonex.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsWithCategory(category: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    fun deleteAllProducts()

    @Transaction
    suspend fun replaceProducts(products: List<ProductEntity>) {
        deleteAllProducts()
        insertAll(products)
    }
}