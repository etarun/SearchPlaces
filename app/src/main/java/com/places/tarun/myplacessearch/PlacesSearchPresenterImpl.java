package com.places.tarun.myplacessearch;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.places.tarun.myplacessearch.utility.Constants;
import com.places.tarun.myplacessearch.adapters.PlacesAutoCompleteAdapter;

/**
 * Created by Tarun on 4/28/2016.
 */
public class PlacesSearchPresenterImpl implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,PlacesSearchPresenter {
    protected GoogleApiClient mGoogleApiClient;
    private MainActivity mainActivity;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private PlaceDetailsInteractor placeDetailsInteractor;
    private static final LatLngBounds MAPS_BOUND = new LatLngBounds(
            new LatLng(-0, 0), new LatLng(0, 0));

    public PlacesSearchPresenterImpl(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mGoogleApiClient = new GoogleApiClient.Builder(mainActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mAutoCompleteAdapter =  new PlacesAutoCompleteAdapter(mainActivity, R.layout.search_adapter,
                mGoogleApiClient, MAPS_BOUND, null);
    }
    public PlacesSearchPresenterImpl(PlaceDetailsActivity placeDetailsActivity){
        mGoogleApiClient = new GoogleApiClient.Builder(placeDetailsActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        placeDetailsInteractor = new PlaceDetailsInteractorImpl();
    }

    /*
    * callPlacesAPI call from Main activity and invokes adapter to get auto complete results
     */
    @Override
    public void callPlacesAPI(CharSequence s) {
        if(mGoogleApiClient.isConnected()) {
            //get auto complete results from adapter
            mAutoCompleteAdapter.getFilter().filter(s.toString());
            mainActivity.setAutoCompletePlaces(mAutoCompleteAdapter);
        }else {
            mainActivity.errorResponse();
            Log.e(Constants.PlacesTag,Constants.API_NOT_CONNECTED);
        }
    }

    /*
    * Method returns placeID of the selected auto complete result.
     */
    @Override
    public String callPlaceDetails(int position) {
        final PlacesAutoComplete item = mAutoCompleteAdapter.getItem(position);
        return String.valueOf(item.placeId);
    }

    /*
    * Method calls the interactor and gets place Details
     */
    @Override
    public PlaceDetails getPlaceDetails(String placeId) {
        placeDetailsInteractor = new PlaceDetailsInteractorImpl();
        return placeDetailsInteractor.getPlaceDetails(mGoogleApiClient, placeId);
    }
    @Override
    public void onResume() {
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()){
            Log.v("Google API", "Connecting");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        if(mGoogleApiClient.isConnected()){
            Log.v("Google API","Dis-Connecting");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("Google API Callback", "Connection Done");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("Google API Callback","Connection Failed");
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
        Toast.makeText(mainActivity, Constants.API_NOT_CONNECTED, Toast.LENGTH_SHORT).show();
    }
}
