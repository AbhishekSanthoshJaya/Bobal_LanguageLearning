package com.aby.capstone_quasars_bobal.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aby.capstone_quasars_bobal.R
import com.aby.capstone_quasars_bobal.onboarding.screens.*


class ViewPagerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentlist = arrayListOf<Fragment>(
                FirstScreen(),
                SecondScreen(),
                ThirdScreen(),
                FourthScreen(),
                GetStartedScreen()
        )

        val adapter = ViewPagerAdapter(
                fragmentlist,
                requireActivity().supportFragmentManager,
                lifecycle
        )
        return view

    }

}