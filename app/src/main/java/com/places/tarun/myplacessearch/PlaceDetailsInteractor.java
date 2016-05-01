package com.places.tarun.myplacessearch;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Tarun on 4/29/2016.
 */
public interface PlaceDetailsInteractor {
    PlaceDetails getPlaceDetails(GoogleApiClient mGoogleApiClient, String placeId);
}
