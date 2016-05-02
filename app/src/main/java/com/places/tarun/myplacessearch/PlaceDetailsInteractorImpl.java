package com.places.tarun.myplacessearch;

import android.os.Parcel;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.concurrent.TimeUnit;

/**
 * Created by Tarun on 4/29/2016.
 */
public class PlaceDetailsInteractorImpl implements PlaceDetailsInteractor{
    public PlaceDetails placeDetails;
    public final String TAG = "PlaceDetailsInteractor";
    @Override
    public PlaceDetails getPlaceDetails(GoogleApiClient mGoogleApiClient, String placeId) {
        placeDetails = new PlaceDetails();
        // contain the place details when the query completes.
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                .getPlaceById(mGoogleApiClient, placeId);
        // This method should have been called off the main UI thread. Block and wait for at most 60s
        // for a result from the API.
        PlaceBuffer places = placeResult.await(60, TimeUnit.SECONDS);
        Log.i(TAG, "Fetching details for ID: " +placeId);
        // Confirm that the query completed successfully, otherwise return null
        if(places.getStatus().isSuccess() && places.getCount() == 1){
            setPlaceDetails(places);
            places.release();
        }
        else {
            Log.e(TAG, "Place not found");
            places.release();
        }

        Log.i(TAG, "return to PlacesSearchPresenter");
        return placeDetails;
    }

    /*
    * Method to set the details to PlaceDetails Bean
     */
    private void setPlaceDetails(PlaceBuffer places) {
        Log.i(TAG, "setPlaces to PlaceDetails");
        if(places.get(0).getAddress() != null){
            placeDetails.setAddress(places.get(0).getAddress());
        }
        if(places.get(0).getId() != null){
            placeDetails.setId(places.get(0).getId());
        }
        if(places.get(0).getName() != null){
            placeDetails.setName(places.get(0).getName());
        }
        if(places.get(0).getPhoneNumber() != null){
            placeDetails.setPhoneNumber(places.get(0).getPhoneNumber());
        }
        if(places.get(0).getPlaceTypes() != null){
            placeDetails.setPlaceTypes(places.get(0).getPlaceTypes());
        }
        if(places.get(0).getPlaceTypes() != null){
            placeDetails.setWebsiteUri(places.get(0).getWebsiteUri());
        }
    }

}
