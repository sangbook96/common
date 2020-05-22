package vn.sangdv.demmo

import androidx.fragment.app.Fragment
import vn.sangdv.common.nav.FragmentNavigatorAdapter
import vn.sangdv.demmo.footer.Footer

class FragmentAdapter(var TAB:ArrayList<Footer>) :FragmentNavigatorAdapter{
    private var mListFragment = ArrayList<Fragment>()
    override fun onCreateFragment(position: Int): Fragment {
        return mListFragment.get(position)
    }

    override fun getTag(position: Int): String {
        return mListFragment.get(position).tag+TAB.get(position).title
    }

    override fun getCount(): Int {
        return TAB.size
    }

}