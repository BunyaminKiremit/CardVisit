package com.example.cardvisit.ui.work

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cardvisit.R
import com.example.cardvisit.adapter.GroupAdapter
import com.example.cardvisit.databinding.FragmentWorkBinding
import com.example.cardvisit.models.Card


class WorkFragment : Fragment() {

    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!

    private lateinit var workViewModel: WorkViewModel
    private lateinit var groupAdapter: GroupAdapter
    private var cards: List<Card> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        workViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(WorkViewModel::class.java)

        groupAdapter = GroupAdapter(requireContext(), R.layout.list_group, cards)
        binding.listWork.adapter = groupAdapter

        workViewModel.getAllCardsByGroup("İş") { fetchedCards ->
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