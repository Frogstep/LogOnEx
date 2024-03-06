package io.ilyasin.logonex.data.db

import androidx.room.TypeConverter

class Converters {
    private val delimiter = "|"

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(delimiter)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(delimiter)
    }
}