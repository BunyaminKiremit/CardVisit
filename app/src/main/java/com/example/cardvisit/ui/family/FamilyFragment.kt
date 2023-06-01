package com.example.cardvisit.ui.family

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cardvisit.R
import com.example.cardvisit.adapter.GroupAdapter
import com.example.cardvisit.databinding.FragmentFamilyBinding
import com.example.cardvisit.models.Card


class FamilyFragment : Fragment() {

    companion object {
        fun newInstance() = FamilyFragment()
    }

    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!


    private lateinit var familyViewModel: FamilyViewModel
    private lateinit var groupAdapter: GroupAdapter
    private var cards: List<Card> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        familyViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(FamilyViewModel::class.java)

        groupAdapter = GroupAdapter(requireContext(), R.layout.list_group, cards)
        binding.listFamily.adapter = groupAdapter

        familyViewModel.getAllCardsByGroup("Aile") { fetchedCards ->
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