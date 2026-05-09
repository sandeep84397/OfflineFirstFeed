package com.sandeep.kmpcore.data.local.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant

val instantAdapter: ColumnAdapter<Instant, Long> = object : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant =
        Instant.fromEpochMilliseconds(databaseValue)

    override fun encode(value: Instant): Long = value.toEpochMilliseconds()
}
