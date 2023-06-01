package com.example.cardvisit.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cardvisit.Detail

import com.example.cardvisit.R
import com.example.cardvisit.adapter.CardAdapter
import com.example.cardvisit.databinding.FragmentHomeBinding
import com.example.cardvisit.models.Card


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cardAdapter: CardAdapter
    private var cards: List<Card> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(HomeViewModel::class.java)

        cardAdapter = CardAdapter(requireContext(), R.layout.list_layout, cards)
        binding.cardList.adapter = cardAdapter

        homeViewModel.getAllCards { fetchedCards ->
            requireActivity().runOnUiThread {
                cards = fetchedCards.takeLast(10)
                cardAdapter.clear()
                cardAdapter.addAll(cards)
                cardAdapter.notifyDataSetChanged()
            }
        }
        binding.cardList.setOnItemClickListener { parent, view, position, id ->
            val selectedCard = cards[position]
            val intent = Intent(requireContext(), Detail::class.java)
            val bundle = Bundle()
            intent.putExtra("selectedCardNid", selectedCard.nid.toString())
            intent.putExtra("selectedCardCardGroup", selectedCard.cardGroup)
            intent.putExtra("selectedCardName", selectedCard.name)
            intent.putExtra("selectedCardSurName", selectedCard.surname)
            intent.putExtra("selectedCardPhone", selectedCard.phone)
            intent.putExtra("selectedCardAdrees", selectedCard.address)
            intent.putExtras(bundle)
            startActivity(intent)
        }





        binding.txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // İstenilen işlemler
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = binding.txtSearch.text.toString()

                homeViewModel.getAllCards { fetchedCards ->
                    requireActivity().runOnUiThread {
                        val filteredCards = if (searchText.isNotEmpty()) {
                            fetchedCards.filter { card ->
                                card.name!!.contains(searchText, ignoreCase = true)
                            }
                        } else {
                            fetchedCards
                        }

                        cards = filteredCards
                        cardAdapter.clear()
                        cardAdapter.addAll(cards)
                        cardAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    homeViewModel.getAllCards { fetchedCards ->
                        requireActivity().runOnUiThread {
                            // Metin boş olduğunda tüm kartları göster
                            cards = fetchedCards.takeLast(10)
                            cardAdapter.clear()
                            cardAdapter.addAll(cards)
                            cardAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

