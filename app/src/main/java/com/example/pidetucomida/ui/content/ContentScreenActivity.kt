package com.example.pidetucomida.ui.content

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityContentScreenBinding
import com.example.pidetucomida.ui.cart.CartActivity
import com.example.pidetucomida.ui.content.adapter.ViewPagerAdapterContent
import com.example.pidetucomida.ui.login.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ContentScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentScreenBinding
    private lateinit var preferences:SharedPreferences
    private var email=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        binding= ActivityContentScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences= getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        email = preferences.getString("correo", "").toString()
        setupToolbar()
        setupListener()
        setupView()

    }

    private fun setupToolbar(){
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)
        binding.toolBar.ibCart.visibility= View.VISIBLE

        if (email!=""){
            binding.toolBar.ibBack.setImageResource(R.drawable.ic_menu)
            createMenu()
        }else{
            binding.toolBar.ibBack.setOnClickListener {
                onBackPressed()
            }
        }

        binding.toolBar.ibCart.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }


    }

    private fun setupView(){
        val adapter = ViewPagerAdapterContent(supportFragmentManager, lifecycle)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf(), getString(R.string.burguer)), R.string.burguers)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf(), getString(R.string.Kebab)), R.string.Kebab)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf(), getString(R.string.pizza)), R.string.pizza)
        adapter.addFragment(ContentFragment.newInstance(mutableListOf(), getString(R.string.hotdog)), R.string.hotdog)


        binding.wpMain.adapter=adapter
        binding.tlMain.tabMode= TabLayout.MODE_SCROLLABLE

        TabLayoutMediator(binding.tlMain, binding.wpMain){ tab, position ->
            tab.setText(adapter.getPageTitleId(position))
        }.attach()

    }

    override fun onBackPressed() {

        if (email!="") {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            builder.setTitle(R.string.logout)
            builder.setMessage(R.string.confirm_logout)
            builder.setPositiveButton(R.string.yes) { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                val editor=preferences.edit()
                editor.clear()
                editor.apply()
            }
            builder.setNegativeButton(R.string.no) { _, _ ->

            }
            val dialog = builder.create()
            dialog.show()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun createMenu() {
        val drawerLayout = binding.drawerLayout
        val navigationDrawer = binding.navigationDrawer
        binding.toolBar.ibBack.setOnClickListener {
            drawerLayout.openDrawer(navigationDrawer)
        }
    }

    private fun setupListener(){
        binding.btnLogout.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                val editor = preferences.edit()
                editor.clear()
                editor.apply()
        }
    }

}