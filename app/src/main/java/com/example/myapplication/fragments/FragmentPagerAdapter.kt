package com.example.myapplication.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val tabList: ArrayList<Fragment> = ArrayList()
    private val tabTitleList: ArrayList<String> = ArrayList()

    fun addFragment(fragment: Fragment, title: String) {
        tabList.add(fragment)
        tabTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? = tabTitleList[position]

    override fun getItem(position: Int) : Fragment = tabList[position]

    override fun getCount(): Int = tabList.size
}