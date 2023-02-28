package com.crk.newsreaderapplication.data.local

import androidx.room.TypeConverter
import com.crk.newsreaderapplication.domain.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}