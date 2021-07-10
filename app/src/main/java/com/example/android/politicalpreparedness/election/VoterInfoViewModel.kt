package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.VoterInfoRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel( private val repository: VoterInfoRepository
                          , val election: Election
) : ViewModel() {

    val state = repository.state

    val savedElection = repository.savedElection

    private var _electionInfoUrl = MutableLiveData<String>()
    val electionInfoUrl: LiveData<String>
        get() = _electionInfoUrl

    private var _votingLocationFinderUrl = MutableLiveData<String>()
    val votingLocationFinderUrl: LiveData<String>
        get() = _votingLocationFinderUrl

    private var _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    init {
        getVoterInfo()
    }

    private fun getVoterInfo() {
        viewModelScope.launch {
            try {
                val dummyAddress = "Modesto"
                repository.refreshVoterInfo(dummyAddress, election.id)
                repository.getElectionById(election.id)
            } catch (e: Exception) {
                // TODO show toast
              // Timber.e("getVoterInfo.exception: ${e.localizedMessage}")
            }
        }
    }

    fun openElectionInfoUrl() {
        _electionInfoUrl.value = state.value?.electionAdministrationBody?.electionInfoUrl
    }

    fun openElectionInfoUrlDone() {
        _electionInfoUrl.value = null
    }

    fun openVotingLocationFinderUrl() {
        _votingLocationFinderUrl.value = state.value?.electionAdministrationBody?.votingLocationFinderUrl
    }

    fun openVotingLocationFinderUrlDone() {
        _votingLocationFinderUrl.value = null
    }

    fun openBallotInfoUrl() {
        _ballotInfoUrl.value = state.value?.electionAdministrationBody?.ballotInfoUrl
    }

    fun openBallotInfoUrlDone() {
        _ballotInfoUrl.value = null
    }

    fun toggleFollow() {
        viewModelScope.launch {
            if (savedElection.value == null) {
                repository.saveElection(election)
                savedElection.value = election
            } else {
                repository.deleteElection(election)
                savedElection.value = null
            }
        }
    }
    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}