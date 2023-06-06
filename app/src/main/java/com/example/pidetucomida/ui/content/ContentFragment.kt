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
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.FragmentContentBinding
import com.example.pidetucomida.model.product.ProductResponse
import com.example.pidetucomida.ui.content.adapter.ProductsAdapter
import com.example.pidetucomida.ui.content.adapter.ProductsViewHolder
import com.example.pidetucomida.ui.detail.DetailActivity
import com.example.pidetucomida.utils.Constants

class ContentFragment : Fragment() {

    private val viewModel: FragmentContentViewModel by viewModels()

    private var type: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentContentBinding.inflate(inflater, container, false)

        setUpView(binding)

        setUpObservable(binding)

        getAdapter(binding)

        return binding.root
    }

    private fun setUpObservable(binding: FragmentContentBinding) {
        viewModel.productsByType.observe(viewLifecycleOwner) { modelList ->
            updateAdapter(binding, modelList)
        }

        viewModel.loadingFormState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.pbDetail.visibility = View.VISIBLE
                binding.tvLoading.text = getString(R.string.loading)
                binding.tvLoading.visibility = View.VISIBLE
            } else {
                binding.pbDetail.visibility = View.GONE
                binding.tvLoading.visibility = View.GONE
            }
        }
        viewModel.setError.observe(viewLifecycleOwner) {
            if (it.equals(R.string.time_out_exception)) {
                binding.tvNoConnection.visibility = View.VISIBLE
                binding.tvNoConnection.text = getString(it)
                binding.ivNoConnection.visibility = View.VISIBLE
            } else if(it.equals(R.string.connect_exception)) {
                binding.tvNoConnection.visibility = View.VISIBLE
                binding.tvNoConnection.text = getString(it)
                binding.ivNoConnection.visibility = View.VISIBLE
                binding.ivNoConnection.setImageResource(R.drawable.ic_no_connection)
            }else{
                binding.ivNoConnection.visibility = View.GONE
                binding.tvNoConnection.visibility = View.GONE
            }

        }
    }

    private fun updateAdapter(
        binding: FragmentContentBinding,
        modelList: MutableList<ProductResponse>
    ) {
        (binding.rvProducts.adapter as ProductsAdapter?)?.apply {
            addAll(modelList)
        }
    }

    private fun setUpView(binding: FragmentContentBinding) {
        viewModel.getProductsByType(type)
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
                    intent.putExtra(Constants.PRODUCT_ID, model.idProducto)
                    startActivity(intent)
                }

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
                this.type = type
            }
    }
}
