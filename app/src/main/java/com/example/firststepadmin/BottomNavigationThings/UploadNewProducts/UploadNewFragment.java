package com.example.firststepadmin.BottomNavigationThings.UploadNewProducts;


import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firststepadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadNewFragment extends Fragment implements View.OnClickListener {


    private static final int FILE_CHOOSER_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_FOR_CAMERA = 2;

    public UploadNewFragment() {
        // Required empty public constructor
    }

    View UploadImage;
    Button Upload;
    RadioGroup radioGroup_productCategoryByMaterial,getRadioGroup_productCategoryByGender;
    ImageView UploadedImage;
    TextInputLayout DescriptionLayout;
    TextInputEditText Description;
    TextView ProductPrice,ProductName;
    Uri Imageuri;
    Bitmap bitmap;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    boolean isImageUploaded=false;
    String D_ProductCategoryByGender,D_ProductCategory,D_ProductPrice,D_ProductTitle,D_ProductDescription,D_Image,key;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upload_new, container, false);
      findViewByIds(view);
      return view;
    }
  private void findViewByIds(View view)
  {
      radioGroup_productCategoryByMaterial=view.findViewById(R.id.radio_group_ProductCategoryByMaterial);
      getRadioGroup_productCategoryByGender=view.findViewById(R.id.radio_group_ProductCategoryByGender);
    UploadImage=view.findViewById(R.id.Upload_materialCard);
    UploadedImage=view.findViewById(R.id.Upload_newImage);
    DescriptionLayout=view.findViewById(R.id.ProductDescription_TxtLayout);
    Description=view.findViewById(R.id.ProductDescription);
    ProductPrice=view.findViewById(R.id.ProductPrice);
    ProductName=view.findViewById(R.id.ProductName);
    UploadImage.setOnClickListener(this);
    ProductName.setOnClickListener(this);
    ProductPrice.setOnClickListener(this);
    Upload=view.findViewById(R.id.Upload_Btn);
    Upload.setOnClickListener(this);
    radioGroup_productCategoryByMaterial.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton radioButton=group.findViewById(checkedId);
            if (radioButton!=null)
                D_ProductCategory=radioButton.getText().toString();
        }
    });
    getRadioGroup_productCategoryByGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton radioButton=group.findViewById(checkedId);
            if (radioButton!=null)
                D_ProductCategoryByGender=radioButton.getText().toString();
        }
    });
  }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.Upload_Btn)
        {
         if (checkProductName() && checkPrice() && checkProductDescription() && checkProductGender() && checkProductMaterial() && checkImageSelected())
         {
            UploadProductDataToDataBase();
         }
        }
        else if (v.getId()==R.id.Upload_materialCard)
        {
          if (checkProductGender() && checkProductMaterial())
          {
              setImage();
          }
        }
        else if (v.getId()==R.id.ProductName)
        {
            setProductName();
        }
        else if (v.getId()==R.id.ProductPrice)
        {
            setProductPrice();
        }
    }
    private boolean checkImageSelected()
    {
      if (!isImageUploaded)
      {
          Toast.makeText(getContext(), "Please Upload Image", Toast.LENGTH_SHORT).show();
        return false;
      }
      else
      {
         return true;
      }
    }

  private boolean checkProductDescription()
  {
      String des=Description.getEditableText().toString();
       if (!TextUtils.isEmpty(des)) {
           D_ProductDescription = des;
           return true;
       }
       else
       {
         DescriptionLayout.setError("Cannot be Empty");
           return false;
       }
  }

    private boolean checkProductName() {
        String na=ProductName.getText().toString();
        if (na.contentEquals("ProductName") || na.trim().isEmpty())
        {
            Toast.makeText(getContext(), "Please Change product name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            D_ProductTitle=na;
            return true;
        }
    }

    private boolean checkPrice() {
        String na=ProductPrice.getText().toString();
        if (na.contentEquals("Price") || na.trim().isEmpty())
        {
            Toast.makeText(getContext(), "Please Fix amount of product", Toast.LENGTH_SHORT).show();
           return false;
        }
        else
        {
            D_ProductPrice=na;
            return true;
        }
    }

    private boolean checkProductMaterial() {
        if (!TextUtils.isEmpty(D_ProductCategory))
        {
            return true;
        }
        else
        {
            Toast.makeText(getContext(), "Please Select Type Of Material", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkProductGender() {
        if (!TextUtils.isEmpty(D_ProductCategoryByGender))
        {
            return true;
        }
        else
        {
            Toast.makeText(getContext(), "Please Select Type Of FootWear", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void getImageFromCamera()
  {
      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
      {
         requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CODE_FOR_CAMERA);
      }
      else
      {
          Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
      }
  }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==2) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImageFromCamera();
            } else {
                //todo snackbar
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_FOR_CAMERA);
            }
        }
        else if (requestCode==1)
        {
           if (permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
           {
               getImageFromGallery();
           }
           else
           {
               //todo snackbar
               requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},FILE_CHOOSER_REQUEST_CODE);
           }
        }
    }

    private void setImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose from one among this..");
        builder.setItems(new CharSequence[]{"Camera","Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        getImageFromCamera();
                        break;
                    case 1:
                        getImageFromGallery();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void getImageFromGallery() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},FILE_CHOOSER_REQUEST_CODE);
        }
        else
        {
           Intent intent=new Intent();
           intent.setType("image/*");
           intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(intent,FILE_CHOOSER_REQUEST_CODE);
        }
    }

    private void setUploadDataFromGallery()
    {
        String imageName=getUniqueKeyFromDatabase()+"."+getFileExtension();
        storageReference=FirebaseStorage.getInstance().getReference(D_ProductCategoryByGender);
        storageReference.child(D_ProductCategory).child(imageName).putFile(Imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                       result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               D_Image=""+uri;
                               Log.e("UploadNewFragment","D_Image "+D_Image);
                               isImageUploaded=true;
                           }
                       });
                    }
                });

    }
    private void setUploadFromCamera()
    {
        String imageName=getUniqueKeyFromDatabase()+".JPEG";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap!=null)
        {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            Log.e("UploadNewFragment","Bitmap compressed");
        }
        else
        {
            Log.e("UploadNewFragment","Bitmap is null unable to compress");
        }
        byte[] data = baos.toByteArray();
        storageReference=FirebaseStorage.getInstance().getReference(D_ProductCategoryByGender).child(D_ProductCategory).child(imageName);
        UploadTask uploadTask=storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        D_Image=""+uri;
                        Log.e("UploadNewFragment","D_Image "+D_Image);
                        isImageUploaded=true;
                    }
                });
            }
        });
    }
   private String getFileExtension()
   {
       ContentResolver contentResolver=getContext().getContentResolver();
       MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
       return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(Imageuri));
   }

   private String getUniqueKeyFromDatabase()
   {
    if (key==null) {
        databaseReference=FirebaseDatabase.getInstance().getReference(D_ProductCategoryByGender).child(D_ProductCategory);
        key = databaseReference.push().getKey();
    }
     return key;
   }

   private void UploadProductDataToDataBase()
   {
       D_ShoesDataFromInternet d_shoesDataFromInternet=new D_ShoesDataFromInternet(D_ProductTitle,D_ProductPrice,D_ProductDescription,D_Image);
       databaseReference=FirebaseDatabase.getInstance().getReference(D_ProductCategoryByGender).child(D_ProductCategory);
       databaseReference.child(getUniqueKeyFromDatabase()).setValue(d_shoesDataFromInternet)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()) {
                           //todo snackbar
                           Toast.makeText(getContext(), "Product Added", Toast.LENGTH_SHORT).show();
                       }   else {
                       }       //todo snackbar
                   }
               });
   }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==FILE_CHOOSER_REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
             Imageuri=data.getData();
            Glide.with(getContext()).load(Imageuri).into(UploadedImage);
            setUploadDataFromGallery();
        }
        else if (requestCode==REQUEST_CODE_FOR_CAMERA ) {
            if (resultCode == RESULT_OK) {
                Log.e("UploadNewFragment","got image from camera");
                bitmap = (Bitmap) data.getExtras().get("Data");
                if (bitmap != null)
                    Log.e("UploadNewFragment", "bitmap true");
                else
                    Log.e("UploadNewFragment", "bitmap false");
                Glide.with(getContext()).load(bitmap).into(UploadedImage);
                setUploadFromCamera();
            }
            else
                Log.e("UploadNewFragment","Unable to get image");
        }
    }


   private void setProductName()
   {
        final TextInputLayout textInputLayoutForName=new TextInputLayout(getContext());
       textInputLayoutForName.setHint("ProductTitle");
       textInputLayoutForName.setErrorEnabled(true);
       final TextInputEditText textInputEditTextForName=new TextInputEditText(getContext());
       textInputEditTextForName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
       textInputLayoutForName.addView(textInputEditTextForName);
       LinearLayout linearLayout=new LinearLayout(getContext());
       linearLayout.setOrientation(LinearLayout.VERTICAL);
       linearLayout.addView(textInputLayoutForName);
     AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
     builder.setView(linearLayout).setPositiveButton("Change", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             String name=textInputEditTextForName.getEditableText().toString();
             if (name.contentEquals("ProductTitle") || name.trim().isEmpty())
             {
                 textInputLayoutForName.setError("Please Enter Valid Name");
             }
             else
             {
                 D_ProductTitle=name;
                 ProductName.setText(D_ProductTitle);
             }
         }
     }).setTitle("Change Details").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
       dialog.dismiss();
         }
     });

     AlertDialog alertDialog=builder.create();
     alertDialog.show();
   }

   private void setProductPrice()
   {
       final TextInputLayout textInputLayoutForName=new TextInputLayout(getContext());
       textInputLayoutForName.setHint("Product Price");
       textInputLayoutForName.setErrorEnabled(true);
       final TextInputEditText textInputEditTextForName=new TextInputEditText(getContext());
       textInputEditTextForName.setInputType(InputType.TYPE_CLASS_NUMBER);
       textInputLayoutForName.addView(textInputEditTextForName);
       LinearLayout linearLayout=new LinearLayout(getContext());
       linearLayout.setOrientation(LinearLayout.VERTICAL);
       linearLayout.addView(textInputLayoutForName);
       AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
       builder.setView(linearLayout).setPositiveButton("Change", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               String name=textInputEditTextForName.getEditableText().toString();
               if (name.contentEquals("Price") || name.trim().isEmpty())
               {
                   textInputLayoutForName.setError("Please Enter Valid Price");
               }
               else
               {
                   D_ProductPrice=name;
                   ProductPrice.setText(D_ProductPrice);
               }
           }
       }).setTitle("Change Details").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });

       AlertDialog alertDialog=builder.create();
       alertDialog.show();
   }

}
