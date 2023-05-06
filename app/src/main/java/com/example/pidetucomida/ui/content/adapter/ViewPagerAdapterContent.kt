package com.example.pidetucomida.ui.content.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pidetucomida.R

class ViewPagerAdapterContent(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

    private val mFragmentList= ArrayList<Fragment>()
    private val mFragmentTitleList= ArrayList<Int>()

    override fun getItemCount(): Int =mFragmentList.size

    override fun createFragment(position: Int): Fragment= mFragmentList[position]

    fun addFragment(fragment: Fragment, title: Int){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
    fun getPageTitleId(position: Int)= mFragmentTitleList[position]

    fun setBackground(position: Int): Int = when(getPageTitleId(position)){
        R.string.burguers ->R.color.dark_red
        R.string.pizza ->R.color.gray
        R.string.Kebab ->R.color.purple_200
        R.string.comida_de_la_casa ->R.color.black
        else -> R.color.black
    }

}