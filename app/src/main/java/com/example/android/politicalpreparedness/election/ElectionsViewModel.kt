package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(private val repository: ElectionRepository): ViewModel() {

    val upcomingElections = repository.upcomingElections
    val savedElections = repository.savedElections

    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election>
        get() = _navigateToVoterInfo

    init {
        refreshElections()
    }

    private fun refreshElections() {
        viewModelScope.launch {
            try {
                repository.refreshElections()
            } catch (e: Exception) {
                // TODO show toast
              //  Timber.i("refreshElections: ${e.localizedMessage}")
            }
        }
    }

    fun navigateToVoterInfoAbout(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun navigationToVoterInfoDone() {
        _navigateToVoterInfo.value = null
    }

}