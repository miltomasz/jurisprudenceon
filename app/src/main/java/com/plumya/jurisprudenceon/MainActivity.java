package com.plumya.jurisprudenceon;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;
import com.parse.PushService;
import com.plumya.jurisprudenceon.fragments.AllRoomsFragment;
import com.plumya.jurisprudenceon.fragments.ICRoomFragment;
import com.plumya.jurisprudenceon.fragments.IKCourtRoomFragment;
import com.plumya.jurisprudenceon.fragments.IPUSiSPRoomFramgment;
import com.plumya.jurisprudenceon.fragments.IWRoomFragment;
import com.plumya.jurisprudenceon.fragments.PSRoomFragment;


import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerManager.getLogger();

    public static final String EXTRA_POSITION = "position";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;

    @InjectView(R.id.left_drawer)
    ListView drawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mCourtRooms;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        logger.d("MainActivity created..");

        mCourtRooms = getResources().getStringArray(R.array.court_rooms_array);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mCourtRooms));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set a custom shadow that overlays the main content when the drawer opens
        drawer.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawer.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (savedInstanceState == null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            position = savedInstanceState.getInt(EXTRA_POSITION);
        }
        
        selectItem(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.content_frame, fragment(position))
                                   .addToBackStack(null)
                                   .commit();

        if (position > 0) {
            setTitle(mCourtRooms[position]);
        } else {
            setTitle(R.string.app_name);
        }

        drawerList.setItemChecked(position, true);
        drawer.closeDrawer(drawerList);
    }

    private Fragment fragment(int position) {
        switch (position) {
            case 0 : return AllRoomsFragment.newInstance(position);
            case 1 : return ICRoomFragment.newInstance(position);
            case 2 : return IKCourtRoomFragment.newInstance(position);
            case 3 : return IPUSiSPRoomFramgment.newInstance(position);
            case 4 : return IWRoomFragment.newInstance(position);
            case 5 : return PSRoomFragment.newInstance(position);
            default: return AllRoomsFragment.newInstance(position);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getSupportActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
