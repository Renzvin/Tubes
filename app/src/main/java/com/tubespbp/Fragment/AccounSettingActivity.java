package com.tubespbp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tubespbp.R;


public class AccounSettingActivity extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.accountsetting_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadFragment(new ChangeAvatarFragment());
        BottomNavigationView bottomNavigationView =(BottomNavigationView) getView().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Bundle temp = this.getArguments();
        final String user = temp.getString("user");
        final String jabatan = temp.getString("jabatan");
        Bundle bundle = new Bundle();
        bundle.putString("user", user );
        bundle.putString("jabatan",jabatan);
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.nav_avatar:
                fragment = new ChangeAvatarFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_username:
                fragment = new ChangeUsernameFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_email:
                fragment = new EmailFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_password:
                fragment = new PasswordFragment();
                fragment.setArguments(bundle);
                break;
        }
        return loadFragment(fragment);

    }
}
