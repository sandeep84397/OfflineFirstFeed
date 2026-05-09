package com.sandeep.kmpcore.data.local.adapters

import app.cash.sqldelight.ColumnAdapter

val booleanAdapter: ColumnAdapter<Boolean, Long> = object : ColumnAdapter<Boolean, Long> {
    override fun decode(databaseValue: Long): Boolean = databaseValue != 0L
    override fun encode(value: Boolean): Long = if (value) 1L else 0L
}

inline fun <reified E : Enum<E>> enumAdapter(): ColumnAdapter<E, String> =
    object : ColumnAdapter<E, String> {
        override fun decode(databaseValue: String): E = enumValueOf(databaseValue)
        override fun encode(value: E): String = value.name
    }
