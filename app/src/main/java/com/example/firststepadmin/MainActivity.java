package com.example.firststepadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.firststepadmin.BottomNavigationThings.CustomShoeOrders.CustomOrdersFragment;
import com.example.firststepadmin.BottomNavigationThings.Profile.ProfileFragment;
import com.example.firststepadmin.BottomNavigationThings.UploadNewProducts.UploadNewFragment;
import com.example.firststepadmin.BottomNavigationThings.UserWishList.UserWishListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private static final String FIRST_FRAGMENT_UPLOAD_NEW_PRODUCT ="UploadNewProduct" ;
    private static final String SECOND_FRAGMENT_USER_WISH="UserWish";
    private static final String THIRD_FRAGMENT_CUSTOM_SHOE="Custom Shoe";
    private static final String FOURTH_FRAGMENT_PROFILE="Profile";

    private Fragment UploadNewProductFragmentInstance=new UploadNewFragment();
    private Fragment UserWishFragmentInstance=new UserWishListFragment();
    private Fragment CustomOrdersFragmentInstance=new CustomOrdersFragment();
    private Fragment ProfileFragmentInstance=new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomNavigation();
        replaceFragment(UploadNewProductFragmentInstance,FIRST_FRAGMENT_UPLOAD_NEW_PRODUCT);
    }

    private void setUpBottomNavigation() {
    BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
    bottomNavigationView.inflateMenu(R.menu.bottom_navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Upload_icon:
                    replaceFragment(UploadNewProductFragmentInstance,FIRST_FRAGMENT_UPLOAD_NEW_PRODUCT);
                    return true;
                case R.id.navigation_wishList:
                    replaceFragment(UserWishFragmentInstance,SECOND_FRAGMENT_USER_WISH);
                    return true;
                case R.id.navigation_custom:
                    replaceFragment(CustomOrdersFragmentInstance,THIRD_FRAGMENT_CUSTOM_SHOE);
                    return true;
                case R.id.navigation_profile:
                    replaceFragment(ProfileFragmentInstance,FOURTH_FRAGMENT_PROFILE);
                    return true;
            }
            return false;
        }

    };


    public void replaceFragment(Fragment fragment, String tag)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,tag).commit();
    }
}
