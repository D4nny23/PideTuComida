package com.example.pidetucomida.ui.content

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityContentScreenBinding
import com.example.pidetucomida.ui.content.adapter.ViewPagerAdapterContent
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ContentScreenActivity : AppCompatActivity() {

    private lateinit var viewModel: ContentViewModel

    private lateinit var binding: ActivityContentScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        binding= ActivityContentScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this)[ContentViewModel::class.java]
        setupToolbar()

        setupView()
        setupObservable()
        viewModel.searchProducts()

    }

    private fun setupToolbar(){
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)

        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupView(){
        val adapter = ViewPagerAdapterContent(supportFragmentManager, lifecycle)
        adapter.addFragment(ContentFragment.newInstance(viewModel.listProductBurgers.value), R.string.burguers)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf()), R.string.Kebab)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf()), R.string.pizza)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf()), R.string.comida_de_la_casa)

        binding.wpMain.adapter=adapter
        binding.tlMain.tabMode= TabLayout.MODE_SCROLLABLE

        TabLayoutMediator(binding.tlMain, binding.wpMain){ tab, position ->
            tab.setText(adapter.getPageTitleId(position))
        }.attach()

        binding.wpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.wpMain.setBackgroundColor(ContextCompat.getColor(this@ContentScreenActivity, adapter.setBackground(position)))
            }
        })

    }

    private fun setupObservable(){
        viewModel.listProduct.observe(this) { productList ->
            productList.let {
//                    binding.textView.text=productList.joinToString(",")

            }
        }
    }

}