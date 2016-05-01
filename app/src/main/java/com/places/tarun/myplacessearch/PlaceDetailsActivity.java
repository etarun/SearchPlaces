package com.places.tarun.myplacessearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Tarun on 4/29/2016.
 */
public class PlaceDetailsActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    private PlacesSearchPresenter placesSearchPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Initializing Presenter
        placesSearchPresenter = new PlacesSearchPresenterImpl(this);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        progressBar = (ProgressBar) findViewById(R.id.loadingPanel);
        //Get place ID from MainActivity
        Bundle data = getIntent().getExtras();
        String placeId = data.getString("placeId");
        //Start background task to get Place details
        new CallPlaceDetails().execute(placeId);
        progressBar.setVisibility(View.VISIBLE);
        text1.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        text3.setVisibility(View.INVISIBLE);
        text4.setVisibility(View.INVISIBLE);

    }
    class CallPlaceDetails extends AsyncTask<String, String, PlaceDetails>{

        @Override
        protected PlaceDetails doInBackground(String... params) {

            PlaceDetails placeDetails = placesSearchPresenter.getPlaceDetails(params[0]);
            return placeDetails;
        }
        @Override
        protected void onPostExecute(PlaceDetails placeDetails) {
            progressBar.setVisibility(View.GONE);
            if(placeDetails.getName() != null){
                text1.setVisibility(View.VISIBLE);
                text1.setText(placeDetails.getName());
            }
            if(placeDetails.getAddress() != null){
                text2.setVisibility(View.VISIBLE);
                text2.setText(placeDetails.getAddress());
            }
            if(placeDetails.getPhoneNumber() != null){
                text3.setVisibility(View.VISIBLE);
                text3.setText(placeDetails.getPhoneNumber());
            }
            if(placeDetails.getWebsiteUri() != null){
                text4.setVisibility(View.VISIBLE);
                text4.setText(
                        Html.fromHtml(
                                "<a href="+placeDetails.getWebsiteUri().toString()+">"+placeDetails.getWebsiteUri().toString()+"</a>"));
                text4.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    public void onStart(){
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        placesSearchPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        placesSearchPresenter.onPause();
    }
}
