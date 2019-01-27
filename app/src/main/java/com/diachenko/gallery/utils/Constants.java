package com.diachenko.gallery.utils;

public class Constants {

    public static final String SASE_URL = "https://api.imgur.com";

    public static final String DATA_BASE_NAME = "GalleryDatabase";

    public static final String MY_IMGUR_CLIENT_ID = "4084401bf6f16b9";

    //      Client Auth
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }

}
