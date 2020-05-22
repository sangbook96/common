package vn.sangdv.demmo;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    public static int pos = 0;

    private List<Fragment> myFragments= new ArrayList();

    public MyFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> myFrags) {
        super(fm);
        myFragments = myFrags;
    }

    @Override
    public Fragment getItem(int position) {

        return myFragments.get(position);

    }

    @Override
    public int getCount() {

        return myFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);

        String PageTitle = "";

        switch(pos)
        {
            case 0:
                PageTitle = "page 1";
                break;
            case 1:
                PageTitle = "page 2";
                break;
            case 2:
                PageTitle = "page 3";
                break;
            case 3:
                PageTitle = "page 4";
                break;
            case 4:
                PageTitle = "page 5";
                break;
            case 5:
                PageTitle = "page 6";
                break;
            case 6:
                PageTitle = "page 7";
                break;
        }
        return PageTitle;
    }

    public static int getPos() {
        return pos;
    }
    public void addFragment(Fragment fragment){
        myFragments.add(fragment);
    }
    public static void setPos(int pos) {
        MyFragmentAdapter.pos = pos;
    }
}

