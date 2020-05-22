package vn.sangdv.demmo.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.sangdv.common.BaseFragment
import vn.sangdv.demmo.R

class DetailFragment : BaseFragment(){
    override fun initView() {
        //process data
        mMainActivity!!.showToast("I'm SangDv")
    }

    override fun layoutView(): Int {
        return R.layout.fragment_detail
    }
}