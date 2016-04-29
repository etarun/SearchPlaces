package com.places.tarun.myplacessearch;


/**
 * Created by Tarun on 4/28/2016.
 */
public interface PlacesSearchPresenter {

    void onResume();

    void callPlacesAPI(CharSequence s);
    void onPause();
}
