package io.ilyasin.logonex.data.db

import androidx.room.TypeConverter

/**
 * Used to convert list of urls to a single string and vice versa
 */
class Converters {
    private val delimiter = "|" // delimiter to separate urls

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(delimiter)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(delimiter)
    }
}