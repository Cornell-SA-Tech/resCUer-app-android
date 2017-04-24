package cornell.sa.rescuer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiweini on 3/9/17.
 */
public class pageAdapter extends FragmentPagerAdapter{
    private List<Fragment> frags = new ArrayList<>();
    private SupportMapFragment mf;

    int numTab;

    public pageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numTab = NumOfTabs;
        frags.add(new actions());
        frags.add(new guide());
        frags.add(new BlueLight());
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return frags.get(0);
            case 1:
                return frags.get(1);
            case 2:
                return frags.get(2);
            default:
                return frags.get(0);
        }
    }

    @Override
    public int getCount() {
        return numTab;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 2:
                return "BlueLight";
            case 1:
                return "Emergency guide";
            case 0:
                return "Actions";
            default:
                return "Emergency guide";
        }
    }
}
