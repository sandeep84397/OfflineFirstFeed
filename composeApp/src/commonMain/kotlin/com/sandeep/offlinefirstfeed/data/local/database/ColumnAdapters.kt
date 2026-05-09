package com.sandeep.offlinefirstfeed.data.local.database

import app.cash.sqldelight.ColumnAdapter
import com.sandeep.kmpcore.data.local.adapters.enumAdapter
import com.sandeep.offlinefirstfeed.domain.model.SyncStatus

internal val syncStatusAdapter: ColumnAdapter<SyncStatus, String> = enumAdapter()
