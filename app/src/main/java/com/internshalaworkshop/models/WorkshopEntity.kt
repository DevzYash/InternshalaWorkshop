package com.internshalaworkshop.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workshops")
data class WorkshopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workshopName: String,
    val description: String,
    val date: String
)
