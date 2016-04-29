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
import com.places.tarun.myplacessearch.adapters.PlacesAutoCompleteAdapter;
import com.places.tarun.myplacessearch.utility.Constants;

/**
 * Created by Tarun on 4/28/2016.
 */
public class PlacesSearchPresenterImpl implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,PlacesSearchPresenter {
    protected GoogleApiClient mGoogleApiClient;
    private MainActivity mainActivity;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
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
    @Override
    public void callPlacesAPI(CharSequence s) {
        if(mGoogleApiClient.isConnected()) {
            mAutoCompleteAdapter.getFilter().filter(s.toString());
            mainActivity.setAutoCompletePlaces(mAutoCompleteAdapter);
        }else if(!mGoogleApiClient.isConnected()){
            mainActivity.errorResponse();
            Log.e(Constants.PlacesTag,Constants.API_NOT_CONNECTED);
        }
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
