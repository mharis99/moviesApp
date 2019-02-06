package com.haris.android.movies.presentation.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.haris.android.movies.presentation.AppConst;
import com.haris.android.movies.presentation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.btn_LoadMovies)
    Button btn_LoadMovies;
    @Bind(R.id.btn_FilterMovies)
    Button btn_FilterMovies;
    @Bind(R.id.btn_PopularMovies)
    Button btn_PopularMovies;
    @Bind(R.id.btn_TopMovies)
    Button btn_TopMovies;
    @Bind(R.id.btn_SearchMovies)
    Button btn_SearchMovies;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    ListView mDrawerList;


    private String[] mSideMenuItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);
        setActionBarIcon();
        initDrawer();


    }


    /*
    * To initialize the adapter for side menu items
    */
    private void initDrawer() {

        mSideMenuItems = getResources().getStringArray(R.array.side_menu_items);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSideMenuItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


    }


    /*
    * this method set the action bar icon to menu item to open drawer
    */
    private void setActionBarIcon() {

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                toggleSideMenu();
                break;
        }

        return true;
    }


    /*
    * this method toggles the drawer
    */
    private void toggleSideMenu() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }

    }


    /*
    * The click listner for ListView in the navigation drawer
    */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    /**
     * called when an item is selected from sidemenu drawer
     */
    private void selectItem(int position) {

        // update selected item, then close the drawer
        mDrawerList.setItemChecked(position, false);
        mDrawerLayout.closeDrawer(mDrawerList);

        switch (position) {

            case AppConst.SelectionType.LIST_MOVIES:

                navigateToMovieList();
                break;

            case AppConst.SelectionType.FILTER_MOVIES:
                navigateToFilterMovieList();

                break;

            case AppConst.SelectionType.POPULAR_MOVIES:
                navigateToPopularMovieList();

                break;

            case AppConst.SelectionType.TOP_MOVIES:
                navigateToTopMovieList();

                break;

            case AppConst.SelectionType.SEARCH_MOVIES:
                navigateToMovieSearch();

                break;
        }


    }


    /**
     * Goes to the movie list screen.
     */
    @OnClick(R.id.btn_LoadMovies)
    void navigateToMovieList() {
        this.navigator.navigateToMovieList(this);
    }

    /**
     * Goes to the movie list screen with min and max year filter
     */
    @OnClick(R.id.btn_FilterMovies)
    void navigateToFilterMovieList() {
        this.navigator.navigateToMovieListWithFilter(this);
    }

    /**
     * Goes to Popular Movies List Screen
     */
    @OnClick(R.id.btn_PopularMovies)
    void navigateToPopularMovieList() {
        this.navigator.navigateToPopularMovies(this);
    }

    /**
     * Goes to top Movies List Screen
     */
    @OnClick(R.id.btn_TopMovies)
    void navigateToTopMovieList() {
        this.navigator.navigateToTopMovies(this);
    }

    /**
     * Goes to Movie Search Screen
     */
    @OnClick(R.id.btn_SearchMovies)
    void navigateToMovieSearch() {
        this.navigator.navigateToMovieSearch(this);
    }

}
