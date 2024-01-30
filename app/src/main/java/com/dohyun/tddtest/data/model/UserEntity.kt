package com.dohyun.tddtest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userId: String,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String?
)
