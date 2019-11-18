package com.example.firststepadmin.Common;

public class D_CurrentUser {

   static public String Email,Name,ProfileImage,PassWord;
    public D_CurrentUser(String email,String name,String profileImage,String password)
    {
        this.Email=email;
        this.Name=name;
        this.ProfileImage=profileImage;
        this.PassWord=password;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getProfileImage() {
        return ProfileImage;
    }

    public static void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }
}
