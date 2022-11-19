package com.baharudin.inews.data.local.converter

import androidx.room.TypeConverter
import com.baharudin.inews.data.local.entity.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source) : String {
        return source.name
    }
    @TypeConverter
    fun toSource(nama : String) : Source {
        return Source(nama, nama)
    }
}