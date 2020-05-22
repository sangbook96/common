package vn.sangdv.demmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import vn.sangdv.common.BaseActivity
import vn.sangdv.demmo.footer.Footer


class MainActivity : BaseActivity() {

    private var arrListFooter=ArrayList<Footer>()
    private var mAdapter:MyFragmentAdapter?=null
    private var mListFragment= ArrayList<Fragment>()
    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        if (isOnline()){
            showToast("Connected")
        }else{
            showToast("Disconnected")
        }
        addList()
    }
    private fun addList(){
        arrListFooter.add(
            Footer(
                "Trang chủ",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Tìm kiếm",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Điện thoại",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Điện tử, điện lạnh",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Phụ kiện",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Máy ảnh",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Điện gia dụng",
                R.drawable.ic_home
            )
        )
        arrListFooter.add(
            Footer(
                "Tiêu dùng thực phẩm",
                R.drawable.ic_home
            )
        )
        mSlidingTabs.setViewPager(mViewPager)
        mSlidingTabs.setTabs(arrListFooter)
        mAdapter = MyFragmentAdapter(supportFragmentManager)
//        mListFragment.add(FragmentOne())
//        mListFragment.add(FragmentTwo())
//        mListFragment.add(FragmentThree())
//        mListFragment.add(FragmentThree())
//        mListFragment.add(FragmentThree())
//        mListFragment.add(FragmentThree())
//        mListFragment.add(FragmentThree())

        mSlidingTabs.addOnPageChangeListener()
        mViewPager.adapter = mSlidingTabs.setupAdapter(mAdapter,mListFragment)

    }
    /*
    private fun setupNavigationController() {
        listTab = FragmentNavigationController.navigationController(supportFragmentManager, R.id.fragment_container)
        listTab!!.setPresentStyle(PresentStyle.ACCORDION_LEFT)
        listTab!!.setInterpolator(AccelerateDecelerateInterpolator())
//        listTab!!.presentFragment(FragmentFirst())

        currentNavigationController = listTab
    }
    override fun onBackPressed() {
        if (currentNavigationController == null) {
            super.onBackPressed()
        }
        if (!currentNavigationController!!.dismissFragment()) {
            super.onBackPressed()
        }
    }

     */
}
