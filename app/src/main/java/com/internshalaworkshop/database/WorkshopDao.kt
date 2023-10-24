package com.internshalaworkshop.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.internshalaworkshop.models.WorkshopEntity

@Dao
interface WorkshopDao {

    @Insert
    suspend fun insertWorkshop(workshop: WorkshopEntity)

    @Query("SELECT * FROM workshops")
    suspend fun getAllWorkshops(): List<WorkshopEntity>

    @Query("SELECT * FROM workshops WHERE id = :workshopId")
    suspend fun getWorkshopById(workshopId: Long): WorkshopEntity?
}
