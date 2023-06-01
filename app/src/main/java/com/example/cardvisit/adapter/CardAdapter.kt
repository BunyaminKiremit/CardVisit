package com.example.cardvisit.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.cardvisit.databinding.ListLayoutBinding

import com.example.cardvisit.models.Card

class CardAdapter(context: Context, resource: Int, objects: List<Card>) :
    ArrayAdapter<Card>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ListLayoutBinding

        val view: View = if (convertView == null) {
            binding = ListLayoutBinding.inflate(inflater, parent, false)
            binding.root
        } else {
            binding = ListLayoutBinding.bind(convertView)
            convertView
        }


        val card: Card = getItem(position) ?: Card(null,"", "", "","","")

        binding.rGroup.text=card.cardGroup
        binding.rName.text = card.name
        binding.rSname.text = card.surname
        binding.rPhone.text = card.phone
        binding.rAdress.text = card.address

        return view
    }
}