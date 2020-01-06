package com.tubespbp.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tubespbp.DAO.accountDAO;
import com.tubespbp.Fragment.AccounSettingActivity;
import com.tubespbp.Fragment.AddBookFragment;
import com.tubespbp.Fragment.BookListFragment;
import com.tubespbp.Fragment.BorrowedListFragment;
import com.tubespbp.Fragment.LocationFragment;
import com.tubespbp.Fragment.RemoveBookFragment;
import com.tubespbp.Fragment.RequestListFragment;
import com.tubespbp.Fragment.ReturnBookFragment;
import com.tubespbp.R;

import java.util.List;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView user,role;
    View hViewl;
    private List<accountDAO> account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hViewl = navigationView.getHeaderView(0);
        user = (TextView)hViewl.findViewById(R.id.show_username);
        role = (TextView)hViewl.findViewById(R.id.show_role);
        user.setText(getIntent().getStringExtra("username"));
        role.setText(getIntent().getStringExtra("role"));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AdminActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new BookListFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_booklist);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Bundle bundle = new Bundle();
        String username = getIntent().getStringExtra("username");
        String role = getIntent().getStringExtra("role");
        bundle.putString("user", username );
        bundle.putString("jabatan",role);
        switch (menuItem.getItemId()){
            case R.id.nav_booklist :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new BookListFragment()).commit();
                break;
            case R.id.nav_borrowedlist :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new BorrowedListFragment()).commit();
                break;
            case R.id.nav_requestlist :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new RequestListFragment()).commit();
                break;
            case R.id.nav_addbook :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new AddBookFragment()).commit();
                break;
            case R.id.nav_removebook :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new RemoveBookFragment()).commit();
                break;
            case R.id.nav_returnbook :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new ReturnBookFragment()).commit();
                break;
            case R.id.nav_location :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new LocationFragment()).commit();
                break;
            case R.id.nav_account :
                AccounSettingActivity account = new AccounSettingActivity();
                account.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, account).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
