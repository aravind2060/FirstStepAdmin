package com.example.firststepadmin.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firststepadmin.Common.D_CurrentUser;
import com.example.firststepadmin.Common.SharedPreferenceClass;
import com.example.firststepadmin.MainActivity;
import com.example.firststepadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class A_SignIn extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout UserNameLayout,PassWordLayout;
    TextInputEditText UserName,PassWord;
    Button SignIn;
    boolean isAdmin=false;
    boolean isUser=false;
    FirebaseAuth firebaseAuth;
    String Uid;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
          SharedPreferenceClass.getDataFromSharedPreference(getApplicationContext());
          new AsyncTaskToFetchCurrentUserData().execute();
          startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__sign_in);
        findViewByIds();
         firebaseAuth=FirebaseAuth.getInstance();

    }

    private void findViewByIds()
    {
       UserNameLayout=findViewById(R.id.sign_in_txt_layout_1);
       PassWordLayout=findViewById(R.id.sign_in_txt_layout_2);
       UserName=findViewById(R.id.sign_in_txt_edit_txt_email);
       PassWord=findViewById(R.id.sign_in_txt_edit_txt_password);
       SignIn=findViewById(R.id.log_btn);
       SignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.log_btn)
        {
            setSignIn();
        }
    }

    private void setSignIn() {
        if (checkUserNameOffline() && checkPasswordOffline())
        {
            if (checkUserNamePassWordInFireBaseAuthentication()) {

                Log.e("A_Signin","authentication is ok");

                if (checkWhetherAdminOrNotInFireBaseDataBase())
                {
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                    Log.e("A_Signin","Database is ok");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(this, "Not Admin", Toast.LENGTH_SHORT).show();
                    Log.e("A_Signin","database is not ok");
                }
            }
            else
                Log.e("A_Signin","authentication is not  ok");
        }
    }


    private boolean checkUserNameOffline()
    {
       String User=UserName.getEditableText().toString();
       if (!TextUtils.isEmpty(User))
           return true;
       else
       {
           UserNameLayout.setError("Cannot be Empty!");
           return false;
       }
    }
    private boolean checkPasswordOffline()
    {
        String Pass=PassWord.getEditableText().toString();
        if (!TextUtils.isEmpty(Pass))
            return true;
        else
        {
            PassWordLayout.setError("Cannot be Empty!");
            return false;
        }
    }

    private boolean checkUserNamePassWordInFireBaseAuthentication()
    {
        firebaseAuth.signInWithEmailAndPassword(UserName.getEditableText().toString(),PassWord.getEditableText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                   Uid=firebaseAuth.getCurrentUser().getUid();
                   isUser=true;
                }
                else
                {
                    try {
                        throw task.getException();
                    }
                    catch (Exception e)
                    {
                        if (e instanceof FirebaseAuthInvalidCredentialsException)
                        {
                            Toast.makeText(getApplicationContext(),"Invalid Credentials ",Toast.LENGTH_LONG).show();
                            Log.e("A_SIGNIN","Invalid credentials");
                        }
                        else if (e instanceof FirebaseAuthInvalidUserException)
                        {
                            Toast.makeText(getApplicationContext(),"Account does not exist",Toast.LENGTH_LONG).show();
                        }
                        else if (e instanceof FirebaseNetworkException) {
                            Toast.makeText(getApplicationContext(), "Unable to reach firebase", Toast.LENGTH_LONG).show();
                        }
                        else if (e instanceof FirebaseAuthProvider)
                        {
                            Toast.makeText(getApplicationContext(),"Login Via GoogleSignIN",Toast.LENGTH_LONG).show();
                        }
                    }
                    isUser=false;
                }
            }
        });
        return isUser;
    }

    private boolean checkWhetherAdminOrNotInFireBaseDataBase() {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference.child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                  isAdmin=true;
                else
                    isAdmin=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("A_Signin","Error : "+databaseError.getMessage());
            }
        });

        return isAdmin;
    }


    class AsyncTaskToFetchCurrentUserData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Admin").child(firebaseAuth.getCurrentUser().getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                       String email=dataSnapshot.child("Email").getValue(String.class);
                       String name=dataSnapshot.child("Name").getValue(String.class);
                       String profileImage=dataSnapshot.child("ProfileImage").getValue(String.class);
                       setCurrentData(email,name,profileImage);
                    }
                    else
                        Log.e("A_SignIn","Unable to get user Data");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

    private void setCurrentData(String email,String name,String profileImage)
    {
        D_CurrentUser.setEmail(email);
        D_CurrentUser.setName(name);
        D_CurrentUser.setProfileImage(profileImage);
        SharedPreferenceClass.setDataIntoSharedPreference(getApplicationContext());
    }

}
