package com.redrock.date2.moudel.date;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.redrock.date2.R;
import com.redrock.date2.model.DateModel;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/8/7.
 */
@RequiresPresenter(DatePresenter.class)
public class DateFragment extends BeamFragment<DatePresenter> {

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.vp_date)
    ViewPager vpDate;

    int curTypeId;

    private DateFragmentListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.date_fragment, container, false);
        ButterKnife.inject(this, rootView);
        setAdapter(mAdapter = new DateFragmentListAdapter(getChildFragmentManager()));

        vpDate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) curTypeId = 0;
                else curTypeId = DateModel.getInstance().getDateTypeFather()[position - 1].getId();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
    }

    public void showMain() {
        if (vpDate != null)
            vpDate.setCurrentItem(0);
    }

    public void setAdapter(PagerAdapter adapter) {
        vpDate.setAdapter(adapter);
        tabs.setViewPager(vpDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filtrate) {
            Intent i = new Intent(getActivity(), FiltrateActivity.class);
            i.putExtra("id", curTypeId);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //viewpager的adapter
    class DateFragmentListAdapter extends FragmentPagerAdapter {

        public DateFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new DateListFragment();
            Bundle b = new Bundle();
            if (position == 0) b.putInt("id", 0);
            else b.putInt("id", DateModel.getInstance().getDateTypeFather()[position - 1].getId());
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            return DateModel.getInstance().getDateTypeFather() == null ? 0 : DateModel.getInstance().getDateTypeFather().length+1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return "热门";
            else return DateModel.getInstance().getDateTypeFather()[position - 1].getName();
        }
    }
}
