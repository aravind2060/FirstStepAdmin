package com.example.firststepadmin.BottomNavigationThings.Inventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firststepadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PurchasedListFragment extends Fragment {
    interface OnCardViewItemClickListener
    {
        void onItemClickListenerOfCardView(int position);
    }

    MyAdapterPurchasedList myAdapterPurchasedList;
    ArrayList<Object> dPurchasedListItems=new ArrayList<>();
    ListView listView;
    ArrayList<String> Dates=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_purchasedlist,container,false);
     Dates.clear();
     setDates();
     setListView(view);
     getDataFromInternet();
     return view;
    }
  private void setDates()
  {
      Calendar now=Calendar.getInstance();
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
      now.add(Calendar.DATE,-1);
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
      now.add(Calendar.DATE,-1);
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
      now.add(Calendar.DATE,-1);
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
      now.add(Calendar.DATE,-1);
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
      now.add(Calendar.DATE,-1);
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
      now.add(Calendar.DATE,-1);
      Dates.add(""+now.get(Calendar.DATE)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR));
  }

    private void getDataFromInternet()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child("UsersNormalShoeOrders");
        dPurchasedListItems.clear();
        for (int i=0;i<Dates.size();i++)
        {
            dPurchasedListItems.add(Dates.get(i));
            databaseReference.child("DateOfPurchaseDeliveryStatus").equalTo("02-12-2019_Pending").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists())
                 {
                     String CustomerName=dataSnapshot.child("CustomerName").getValue(String.class);
                     String CustomerMobileNumber=dataSnapshot.child("CustomerMobileNumber").getValue(String.class);
                     String CustomerEmail=dataSnapshot.child("CustomerEmail").getValue(String.class);
                     String ProductName =dataSnapshot.child("ProductName").getValue(String.class);
                     String ProductDescription=dataSnapshot.child("ProductDescription").getValue(String.class);
                     String ProductImage=dataSnapshot.child("ProductImage").getValue(String.class);
                     String NoOfOrdersPurchased=dataSnapshot.child("NoOfOrdersPurchased").getValue(String.class);
                     String ProductSize=dataSnapshot.child("ProductSize").getValue(String.class);
                     String ProductCategoryByGender=dataSnapshot.child("ProductCategoryByGender").getValue(String.class);
                     String ProductCategoryByMaterial=dataSnapshot.child("ProductCategoryByMaterial").getValue(String.class);
                     String AmountPaid=dataSnapshot.child("AmountPaid").getValue(String.class);
                     String CustomerAddress=dataSnapshot.child("CustomerAddress").getValue(String.class);
                     String OrderId=dataSnapshot.child("OrderId").getValue(String.class);
                     String DateOfPurchase_DeliveryStatus=dataSnapshot.child("DateOfPurchaseDeliveryStatus").getValue(String.class);
                     dPurchasedListItems.add(new D_PurchasedListItems(CustomerName,CustomerMobileNumber,CustomerEmail,ProductName,ProductDescription,ProductImage,NoOfOrdersPurchased,ProductSize,ProductCategoryByGender,ProductCategoryByMaterial,AmountPaid,CustomerAddress,OrderId,DateOfPurchase_DeliveryStatus));
                 }
                 else
                 {
                     Log.e("PurchasedListFragment","DataSnapshot does not exist");
                     dPurchasedListItems.add(null);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.e("PurchasedListFragment","Something Error in getting users past orders");
             }
         });
        }
        myAdapterPurchasedList.notifyDataSetChanged();

    }


    private void setListView(View view) {
     listView=view.findViewById(R.id.ListView_PurchasedOrders);
     myAdapterPurchasedList=new MyAdapterPurchasedList(dPurchasedListItems);
     listView.setAdapter(myAdapterPurchasedList);

//     myAdapterPurchasedList.setOnCardViewItemClickListener(new OnCardViewItemClickListener() {
//         @Override
//         public void onItemClickListenerOfCardView(int position) {
//          //todo send data
//
//         }
//     });
    }


    class MyAdapterPurchasedList extends BaseAdapter
    {
       ArrayList<Object>arrayList;
       private static final int HEADER=1;
       private static final int PURCHASED_ITEM_DATA=0;
      OnCardViewItemClickListener onCardViewItemClickListener;
      int pos;
      public MyAdapterPurchasedList(ArrayList<Object>arrayList1)
      {
         this.arrayList=arrayList1;
      }

      public void setOnCardViewItemClickListener(OnCardViewItemClickListener onCardViewItemClickListener1)
      {
          this.onCardViewItemClickListener=onCardViewItemClickListener1;
      }
      @Override
      public int getItemViewType(int position) {
          super.getItemViewType(position);
          if (arrayList.get(position) instanceof D_PurchasedListItems)
              return PURCHASED_ITEM_DATA;
          else
              return HEADER;
      }

      @Override
      public int getViewTypeCount() {
          return 2;
      }

      @Override
      public int getCount() {
          return arrayList.size();
      }

      @Override
      public Object getItem(int position)
      {
          return arrayList.get(position);
      }

      @Override
      public long getItemId(int position) {
          return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent)
      {
         if (convertView==null)
         {
           switch (getItemViewType(position))
           {
               case HEADER:
                   convertView=LayoutInflater.from(getContext()).inflate(R.layout.listview_header_date_purchased_orders,parent,false);
                   break;
               case PURCHASED_ITEM_DATA:
                   convertView=LayoutInflater.from(getContext()).inflate(R.layout.single_view_purchased_orders,parent,false);
                   break;
           }
         }

         switch (getItemViewType(position))
         {
             case HEADER:
                 TextView date=convertView.findViewById(R.id.Date_Purchased_Orders);
                 date.setText((String)arrayList.get(position));
                 break;
             case PURCHASED_ITEM_DATA:

                 CircleImageView circleImageView=convertView.findViewById(R.id.Image_Purchased_Orders);
                 Glide.with(getContext()).load(((D_PurchasedListItems)arrayList.get(position)).getProductImage()).into(circleImageView);

                 TextView ProductName=convertView.findViewById(R.id.ProductName_Purchased_Orders);
                 ProductName.setText(((D_PurchasedListItems)arrayList.get(position)).getProductName()+"-"+((D_PurchasedListItems)arrayList.get(position)).NoOfOrdersPurchased);

                 TextView ProductPrice=convertView.findViewById(R.id.AmountPaid_Purchased_Orders);
                 ProductPrice.setText(((D_PurchasedListItems)arrayList.get(position)).getAmountPaid());

                 TextView CustomerName=convertView.findViewById(R.id.CustomerName_Purchased_Orders);
                 CustomerName.setText(((D_PurchasedListItems)arrayList.get(position)).getCustomerName());

                 pos=position;
                 convertView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if (onCardViewItemClickListener!=null)
                         {
                                 onCardViewItemClickListener.onItemClickListenerOfCardView(pos);
                         }
                     }
                 });
                 break;
         }

          return convertView;
      }
  }


}
