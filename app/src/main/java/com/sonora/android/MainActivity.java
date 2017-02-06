package com.sonora.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.sonora.android.fragments.FeedFragment;
import com.sonora.android.fragments.MenusFragment;
import com.sonora.android.fragments.ShoppingListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // Views
    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.pager) protected ViewPager mPager;
    @BindView(R.id.tabs) protected TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Butter Knife for the activity
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        // Set up ViewPager
        setupViewPager(mPager);

        mTabLayout.setupWithViewPager(mPager);

        // Add tab icons
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_folder);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_receipt);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_person);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_sign_out:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, SplashscreenActivity.class));
                // Override for smooth transition
                overridePendingTransition(0, 0);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method adds tabs to the passed ViewPager
     * @param viewPager ViewPager to which to add tabs
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add fragments
        // TODO: Replace with unique fragments
        adapter.addFragment(FeedFragment.newInstance()); // Home
        adapter.addFragment(MenusFragment.newInstance()); // User Menus
        adapter.addFragment(ShoppingListFragment.newInstance()); // User Shopping Lists
        adapter.addFragment(FeedFragment.newInstance()); // Profile
        viewPager.setAdapter(adapter);
    }

    // Adapter class for view pager tabs
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}
