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

}