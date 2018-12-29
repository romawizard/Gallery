package com.diachenko.gallery.utils;

public class Constants {

    public static final String MY_IMGUR_CLIENT_ID = "4084401bf6f16b9";
    public static final String MY_IMGUR_CLIENT_SECRET = "";

    /*
      Redirect URL for android.
     */
    public static final String MY_IMGUR_REDIRECT_URL = "http://android";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }

}
