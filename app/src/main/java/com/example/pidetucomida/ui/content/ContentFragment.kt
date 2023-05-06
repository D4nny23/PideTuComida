package com.example.pidetucomida.ui.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.pidetucomida.databinding.FragmentContentBinding
import com.example.pidetucomida.model.product.ProductResponse

class ContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentContentBinding.inflate(layoutInflater, container,false)
        setupView(binding)
        return binding.root
    }

    private fun setupView(binding: FragmentContentBinding){

    }

    private fun setupAdapter(){

    }
    companion object {
        private lateinit var resourceList: MutableList<ProductResponse>
        @JvmStatic
        fun newInstance(modelList: MutableList<ProductResponse>?) =
            ContentFragment().apply {
                if (modelList != null) {
                    resourceList=modelList
                }
                }
            }
    }
