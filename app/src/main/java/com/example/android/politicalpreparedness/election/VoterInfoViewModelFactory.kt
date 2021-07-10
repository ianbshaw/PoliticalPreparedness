package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.VoterInfoRepository

class VoterInfoViewModelFactory(private val repository: VoterInfoRepository, private val election: Election): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(repository, election) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}