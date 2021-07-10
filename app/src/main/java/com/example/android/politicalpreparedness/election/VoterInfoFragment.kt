package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.repository.ElectionRepository

class VoterInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val database = ElectionDatabase.getInstance(requireActivity().application)
        val repository = ElectionRepository(database)

        val viewModelFactory = ElectionsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ElectionsViewModel::class.java)

        val binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val upcomingElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            viewModel.navigateToVoterInfoAbout(election)
        })
        binding.upcomingElectionList.adapter = upcomingElectionListAdapter
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer { electionList ->
            electionList?.let {
                upcomingElectionListAdapter.submitList(electionList)
            }
        })

        val savedElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            viewModel.navigateToVoterInfoAbout(election)
        })
        binding.savedElectionList.adapter = savedElectionListAdapter
        viewModel.savedElections.observe(viewLifecycleOwner, Observer { electionList ->
            electionList?.let {
                savedElectionListAdapter.submitList(electionList)
            }
        })

        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { election ->
            election?.let {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election, election.division))
                viewModel.navigationToVoterInfoDone()
            }
        })

        return binding.root

    }

    //TODO: Create method to load URL intents

}