package com.places.tarun.myplacessearch;

/**
 * Created by Tarun on 4/29/2016.
 */
public class PlacesAutoComplete {

    public CharSequence placeId;
    public CharSequence description;

    public PlacesAutoComplete(CharSequence placeId, CharSequence description) {
        this.placeId = placeId;
        this.description = description;
    }

    @Override
    public String toString() {
        return description.toString();
    }
}
