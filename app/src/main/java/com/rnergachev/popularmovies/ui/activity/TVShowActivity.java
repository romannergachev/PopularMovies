package com.rnergachev.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.TVShow;
import com.rnergachev.popularmovies.ui.fragment.TVShowFragment;
import com.rnergachev.popularmovies.ui.presenter.TVShowActivityPresenter;
import com.rnergachev.popularmovies.ui.view.TVShowView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity with the details of the selected tvShow
 *
 * Created by rnergachev on 27/01/2017.
 */

public class TVShowActivity extends AppCompatActivity implements TVShowView {
    private TVShow tvShow;

    private PagerAdapter pagerAdapter;


    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject
    TVShowActivityPresenter TVShowActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        ButterKnife.bind(this);

        PopularMoviesApplication application = (PopularMoviesApplication) getApplication();
        application.appComponent.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        tvShow = getIntent().getParcelableExtra(getString(R.string.extra_tv_show));
        getSupportActionBar().setTitle(tvShow.getName());


        TVShowActivityPresenter.setTVShow(tvShow);
        TVShowActivityPresenter.setView(this);

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (TVShowActivityPresenter.getCount() - position < 4) {
                    TVShowActivityPresenter.fetchSimilarShows(tvShow.getId());
                }

                getSupportActionBar().setTitle(TVShowActivityPresenter.getTVShow(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        postponeEnterTransition();

        TVShowActivityPresenter.fetchSimilarShows(tvShow.getId());
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                supportFinishAfterTransition();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    @Override
    public void notifyDataChanged() {
        pagerAdapter.notifyDataSetChanged();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TVShowFragment.getInstance(position);

        }

        @Override
        public int getCount() {
            return TVShowActivityPresenter.getCount();
        }
    }

}
