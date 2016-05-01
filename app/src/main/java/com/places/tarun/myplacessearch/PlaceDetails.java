package com.places.tarun.myplacessearch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarun on 4/29/2016.
 */
public class PlaceDetails {
    String id;
    CharSequence name;
    CharSequence address;
    List<Integer> placeTypes;
    CharSequence phoneNumber;
    Uri websiteUri;

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

    public CharSequence getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(CharSequence phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public CharSequence getAddress() {
        return address;
    }

    public void setAddress(CharSequence address) {
        this.address = address;
    }

    public List<Integer> getPlaceTypes() {
        return placeTypes;
    }

    public void setPlaceTypes(List<Integer> placeTypes) {
        this.placeTypes = placeTypes;
    }


}