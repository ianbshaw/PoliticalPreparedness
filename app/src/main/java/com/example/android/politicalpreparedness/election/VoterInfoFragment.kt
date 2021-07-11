package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.repository.VoterInfoRepository

class VoterInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val args = navArgs<VoterInfoFragmentArgs>()
        val election = args.value.argElection

        val database = ElectionDatabase.getInstance(requireActivity().application)
        val repository = VoterInfoRepository(database)

        val viewModelFactory = VoterInfoViewModelFactory(repository, election)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(VoterInfoViewModel::class.java)

        val binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        viewModel.electionInfoUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openElectionInfoUrlDone()
            }
        })

        viewModel.votingLocationFinderUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openVotingLocationFinderUrlDone()
            }
        })

        viewModel.ballotInfoUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openBallotInfoUrlDone()
            }
        })


        viewModel.savedElection.observe(viewLifecycleOwner, Observer {
            binding.followElectionButton.visibility = View.VISIBLE
            when (it == null) {
                true -> {
                    binding.followElectionButton.text = getString(R.string.follow_election_button_text)
                }
                false -> {
                    binding.followElectionButton.text = getString(R.string.unfollow_election_button_text)
                }
            }
        })

        return binding.root
    }

    private fun startActivityWithUrlIntentUsing(urlStr: String) {
        val uri: Uri = Uri.parse(urlStr)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}