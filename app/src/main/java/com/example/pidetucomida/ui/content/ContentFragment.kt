package com.example.pidetucomida.ui.content

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pidetucomida.databinding.FragmentContentBinding
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.ui.content.adapter.ProductsAdapter
import com.example.pidetucomida.ui.detail.DetailActivity
import com.example.pidetucomida.utils.Constants

class ContentFragment : Fragment() {

    private val viewModel: FragmentContentViewModel by viewModels()

    private var type: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = FragmentContentBinding.inflate(inflater,container,false)

        setUpView(binding)

        setUpObservable(binding)

        getAdapter(binding)

        return binding.root
    }
    private fun setUpObservable(binding: FragmentContentBinding){
        viewModel.productsByType.observe(viewLifecycleOwner) { modelList ->
            updateAdapter(binding, modelList)
            }
        }

    private fun updateAdapter(binding: FragmentContentBinding, modelList: MutableList<ProductResponse>) {
        (binding.rvProducts.adapter as ProductsAdapter?)?.apply {
            addAll(modelList)
        }
    }

    private fun setUpView(binding: FragmentContentBinding) {
        viewModel.getProductsByType(type)
//        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(text: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(text: String?): Boolean {
//                text?.let {
//                    viewModel.getFavouriteTouristResources(it, resourceIsFavourite)
//                }
//                return false
//            }
//        })
    }

    private fun getAdapter(binding: FragmentContentBinding) {
        binding.rvProducts.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val myProductsAdapter = ProductsAdapter(
            resourceList,
            requireContext(),
            object : ProductsViewHolder.OnClickListener {

                override fun onClick(model: ProductResponse) {
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra(Constants.PRODUCT_RESPONSE, model)
                    startActivity(intent)
                }

//                override fun onClickFavorite(id: String, isFavorite: Boolean) {
//                    viewModel.onFavoriteResource(id, isFavorite)
//                }
//
//                override fun onUpdateItems(mutableList: MutableList<TouristResourceModel>) {
//                    if (mutableList.isEmpty()){
//                        binding.tvWithoutResult.visibility = View.VISIBLE
//                    }else{
//                        binding.tvWithoutResult.visibility = View.GONE
//                    }
//                }
            }
        )
        binding.rvProducts.adapter = myProductsAdapter
    }

    companion object {
        private lateinit var resourceList: MutableList<ProductResponse>
        @JvmStatic
        fun newInstance(modelList: MutableList<ProductResponse>, type: String) =
            ContentFragment().apply {
                resourceList = modelList
                this.type=type
            }
    }
    }
