package com.example.firststepadmin.BottomNavigationThings.CustomShoeOrders;

public class D_CustomShoesData {

    public String productTitle,productDescription,productPrice,imageLocation,Quantity,Size,OrderId,DateOfPurchase_DeliveryStatus,CustomerName,CustomerMobileNumber,CustomerEmail,CustomerAddress;

    public D_CustomShoesData(String productTitle, String productDescription, String productPrice, String imageLocation, String quantity, String size, String orderId, String dateOfPurchase_DeliveryStatus, String customerName, String customerMobileNumber, String customerEmail, String customerAddress) {
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.imageLocation = imageLocation;
        Quantity = quantity;
        Size = size;
        OrderId = orderId;
        DateOfPurchase_DeliveryStatus = dateOfPurchase_DeliveryStatus;
        CustomerName = customerName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerEmail = customerEmail;
        CustomerAddress = customerAddress;
    }

    public D_CustomShoesData(){}
}
