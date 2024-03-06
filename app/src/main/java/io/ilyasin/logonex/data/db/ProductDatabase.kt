package io.ilyasin.logonex.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "products_database"

@Database(entities = [ProductEntity::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao

    companion object {
        fun createDB(context: Context): ProductDatabase {
            return Room.databaseBuilder(
                context,
                ProductDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
