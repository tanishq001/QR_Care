package com.example.qrcare;

public class ExampleItem {

    private String name;
    private String number;
    private String address;
    public ExampleItem( String text1, String text2,String text3) {

        name = text1;
        number = text2;
        address = text3;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public String getAddress() {
        return address;
    }
}
