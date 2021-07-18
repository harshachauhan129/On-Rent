package au.edu.jcu.flatOnRent.onrent.helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> tabtitles=new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public  void addFragments(Fragment fragment,String titles)
    {

        this.fragments.add(fragment);
        this.tabtitles.add(titles);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();

    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabtitles.get(position);
    }
}
