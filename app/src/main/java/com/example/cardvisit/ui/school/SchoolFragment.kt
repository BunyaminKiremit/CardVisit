package com.example.cardvisit.ui.school

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cardvisit.R
import com.example.cardvisit.adapter.GroupAdapter
import com.example.cardvisit.databinding.FragmentSchoolBinding
import com.example.cardvisit.models.Card


class SchoolFragment : Fragment() {

    companion object {
        fun newInstance() = SchoolFragment()
    }

    private var _binding: FragmentSchoolBinding? = null
    private val binding get() = _binding!!

    private lateinit var schoolViewModel: SchoolViewModel
    private lateinit var groupAdapter: GroupAdapter
    private var cards: List<Card> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSchoolBinding.inflate(inflater, container, false)
        val root: View = binding.root

        schoolViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(SchoolViewModel::class.java)

        groupAdapter = GroupAdapter(requireContext(), R.layout.list_group, cards)
        binding.listSchool.adapter = groupAdapter

        schoolViewModel.getAllCardsByGroup("Okul") { fetchedCards ->
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