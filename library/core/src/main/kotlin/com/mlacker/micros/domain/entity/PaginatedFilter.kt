package com.mlacker.micros.domain.entity

class PaginatedFilter {

    var page = 0
        set(value) {
            field = if (value > 0) value - 1 else value
        }
    val start: Int
        get() = page * size
    var size = 10
    var sort: String = ""
    var order: String = ""
    var filters = mutableMapOf<String, String>()

    fun containsFilter(key: String): Boolean {
        return filters.containsKey(key)
    }

    fun getFilter(key: String): String? {
        return filters[key]
    }

    fun setFilter(key: String, value: String) {
        filters[key] = value
    }
}