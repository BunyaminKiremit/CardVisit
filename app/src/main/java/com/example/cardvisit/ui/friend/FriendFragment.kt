package com.example.cardvisit.ui.friend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cardvisit.R
import com.example.cardvisit.adapter.GroupAdapter
import com.example.cardvisit.databinding.FragmentFriendBinding
import com.example.cardvisit.models.Card


class FriendFragment : Fragment() {

    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!

    private lateinit var friendViewModel: FriendViewModel
    private lateinit var groupAdapter: GroupAdapter
    private var cards: List<Card> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        friendViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(FriendViewModel::class.java)

        groupAdapter = GroupAdapter(requireContext(), R.layout.list_group, cards)
        binding.listFriend.adapter = groupAdapter

        friendViewModel.getAllCardsByGroup("Arkadaş") { fetchedCards ->
            requireActivity().runOnUiThread {
                cards = fetchedCards
                groupAdapter.clear()
                groupAdapter.addAll(cards)
                groupAdapter.notifyDataSetChanged()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}