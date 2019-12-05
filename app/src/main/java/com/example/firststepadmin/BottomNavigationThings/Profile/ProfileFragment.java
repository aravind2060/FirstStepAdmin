package com.example.firststepadmin.BottomNavigationThings.Profile;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firststepadmin.Common.D_CurrentUser;
import com.example.firststepadmin.Common.SharedPreferenceClass;
import com.example.firststepadmin.Login.A_SignIn;
import com.example.firststepadmin.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    public ProfileFragment() {
    }

    CircleImageView circleImageView;
    TextView Email,Name;
    View SignOut,ContactDeveloper,rootlinearlayout,helpCustomersWithOrderId,pastorders;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_profile, container, false);
       findViewByIds(view);
       setData();
       return view;
    }

    private void findViewByIds(View view)
    {
        rootlinearlayout=view.findViewById(R.id.root_ProfileFragment);
      circleImageView=view.findViewById(R.id.profile_image);
      Email=view.findViewById(R.id.email);
      Name=view.findViewById(R.id.fullname);
      SignOut=view.findViewById(R.id.signout);
      ContactDeveloper=view.findViewById(R.id.contactDeveloper);
      ContactDeveloper.setOnClickListener(this);
      SignOut.setOnClickListener(this);
      helpCustomersWithOrderId=view.findViewById(R.id.Help_Customers_With_OrderId);
      helpCustomersWithOrderId.setOnClickListener(this);
      pastorders=view.findViewById(R.id.View_Past_Orders);
      pastorders.setOnClickListener(this);
    }
    private void setData()
    {
        Glide.with(getContext()).load(D_CurrentUser.getProfileImage()).placeholder(R.color.DodgerBlue).fallback(R.drawable.men_icon).into(circleImageView);
        Email.setText(D_CurrentUser.getEmail());
        Name.setText(D_CurrentUser.getName());
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.signout)
        {
            setSignOut();
        }
        else if (v.getId()==R.id.contactDeveloper)
        {
           setContactDeveloper();
        }
        else if (v.getId()==R.id.Help_Customers_With_OrderId)
        {
            startActivity(new Intent(getContext(),A_HelpCustomersWithOrderId.class));
        }
        else if (v.getId()==R.id.View_Past_Orders)
            startActivity(new Intent(getContext(),A_PastUploadedProducts.class));
    }

    private void setContactDeveloper() {
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
        }
        else
        {
            String number="+919866772522";
            Intent intent4=new Intent(Intent.ACTION_CALL);
            intent4.setData(Uri.parse("tel:"+number));
            startActivity(intent4);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      if (requestCode==1)
      {
          if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
          {
             setContactDeveloper();
          }
          else
          {
              Snackbar.make(rootlinearlayout,"You need to allow permission to make a call",Snackbar.LENGTH_LONG).setAction("Allow", new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      setContactDeveloper();
                  }
              }).show();
          }
      }
    }

    private void setSignOut() {
        D_CurrentUser.setName("");
        D_CurrentUser.setEmail("");
        D_CurrentUser.setProfileImage("");
        SharedPreferenceClass.setDataIntoSharedPreference(getContext());
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getContext(), A_SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

  private void UploadToFirebase


}
