package com.internshalaworkshop.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.internshalaworkshop.models.AppliedWorkshopEntity

@Dao
interface AppliedWorkshopDao {
    @Insert
     fun insertAppliedWorkshop(appliedWorkshop: AppliedWorkshopEntity)

    @Query("SELECT * FROM applied_workshops WHERE userEmail = :userEmail")
     fun getAppliedWorkshopsByUserEmail(userEmail: String): List<AppliedWorkshopEntity>

     @Delete
     fun deteleAppliedWorkshop(appliedWorkshop: AppliedWorkshopEntity)
}
