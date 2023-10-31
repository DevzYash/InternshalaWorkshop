package com.internshalaworkshop.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "applied_workshops",
    foreignKeys = [ForeignKey(
        entity = UsersEntity::class,
        parentColumns = ["email"],
        childColumns = ["userEmail"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class AppliedWorkshopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workshopName:String,
    val description: String,
    val date: String,
    val userEmail: String
)
