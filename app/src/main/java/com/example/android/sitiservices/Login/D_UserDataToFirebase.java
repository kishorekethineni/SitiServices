package com.example.android.sitiservices.Login;


public class D_UserDataToFirebase {

    public String Name,Email,Phone,Gender,Password;

    public D_UserDataToFirebase(String name, String email, String phone, String gender,String password) {
        Name = name;
        Email = email;
        Phone = phone;
        Gender = gender;
        Password=password;
    }

    public D_UserDataToFirebase() {
    }


}
