package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElection(election: Election)

    @Query("SELECT * FROM election_table")
    fun getElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id = :id")
    fun getElectionById(id: Int): Election

    @Delete
    fun deleteElection(election: Election)

    //TODO: Add clear query

}