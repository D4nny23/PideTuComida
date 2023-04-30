package com.example.pidetucomida.ui.content

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapterContent(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){

    private val mFragmentList= ArrayList<Fragment>()
    private val mFragmentTitleList= ArrayList<String>()

    override fun getItemCount(): Int =mFragmentList.size

    override fun createFragment(position: Int): Fragment= mFragmentList[position]

    fun addFragment(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

}