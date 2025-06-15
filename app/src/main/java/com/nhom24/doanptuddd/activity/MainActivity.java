package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.fragment.ComicFragment;
import com.nhom24.doanptuddd.fragment.NovelFragment;
import com.nhom24.doanptuddd.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SearchView searchView;
    private NavController navController;
    private ImageView navheaderimage;
    private TextView nav_header_title, nav_header_subtitle;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search_view);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        View headerView = navigationView.getHeaderView(0);
        navheaderimage = headerView.findViewById(R.id.nav_header_image);
        nav_header_title = headerView.findViewById(R.id.nav_header_title);
        nav_header_subtitle = headerView.findViewById(R.id.nav_header_subtitle);

        String name = sessionManager.getAccountName();
        String emai = sessionManager.getGmail();
        String avatar = sessionManager.getAvatar();
        if (avatar != null) {
            Glide.with(this)
                    .load(avatar)
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .into(navheaderimage);
        } else {
            navheaderimage.setImageResource(R.drawable.img_avatar);
        }

        nav_header_title.setText(name != null ? name : "User Name");
        nav_header_subtitle.setText(emai != null ? emai : "user@example.com");


        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_novel,
                R.id.navigation_comic, R.id.navigation_account)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNav, navController);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();
        setupSearchView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý tìm kiếm khi nhấn Enter
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Cập nhật tìm kiếm khi nhập liệu
                return false;
            }
        });
    }

    private void performSearch(String query) {
        // Lấy đích hiện tại từ NavController
        int currentDestinationId = navController.getCurrentDestination().getId();

        // Tạo Bundle chứa search_query
        Bundle bundle = new Bundle();
        bundle.putString("search_query", query);

        // Điều hướng hoặc gửi query đến fragment hiện tại
        if (currentDestinationId == R.id.navigation_novel) {
            // Đã ở NovelFragment, gửi query qua setArguments
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
            if (navHostFragment != null) {
                Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();
                if (currentFragment instanceof NovelFragment) {
                    currentFragment.setArguments(bundle);
                    ((NovelFragment) currentFragment).performSearch(query);
                }
            }
        } else if (currentDestinationId == R.id.navigation_comic) {
            // Tương tự cho ComicFragment (nếu có)
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
//            if (navHostFragment != null) {
//                Fragment currentFragment = navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment();
//                if (currentFragment instanceof ComicFragment) {
//                    currentFragment.setArguments(bundle);
//                    ((ComicFragment) currentFragment).performSearch(query);
//                }
//            }
        } else {
            // Nếu không phải NovelFragment hoặc ComicFragment, điều hướng đến NovelFragment
            navController.navigate(R.id.navigation_novel, bundle);
        }
    }
}