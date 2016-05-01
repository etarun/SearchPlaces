package com.places.tarun.myplacessearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.places.tarun.myplacessearch.adapters.PlacesAutoCompleteAdapter;
import com.places.tarun.myplacessearch.listeners.RecyclerItemClickListener;
import com.places.tarun.myplacessearch.utility.Constants;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private PlacesSearchPresenter placesSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //Initializing Presenter
        placesSearchPresenter = new PlacesSearchPresenterImpl(this);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String placeId = placesSearchPresenter.callPlaceDetails(position);

                        Intent i = new Intent(MainActivity.this, PlaceDetailsActivity.class);
                        i.putExtra("placeId", placeId);
                        startActivity(i);
                    }
                })
        );
    }

    /*
    * onCreateOptionsMenu for searchView
    */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setIconifiedByDefault(true);
            searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
            //Search quuery listener
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.toString().equals("") && (newText.length()%2 ==1)) {
                        //Presenter Call
                        placesSearchPresenter.callPlacesAPI(newText);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            // onClose listener
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    mRecyclerView.setAdapter(null);
                    searchView.onActionViewCollapsed();
                    return true;
                }
            });
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    public void errorResponse(){
        Toast.makeText(getApplicationContext(), Constants.API_NOT_CONNECTED,Toast.LENGTH_SHORT).show();
    }

    /*
    * set auto complete results to recycler view.
    */
    public void setAutoCompletePlaces(PlacesAutoCompleteAdapter mAutoCompleteAdapter){
        mRecyclerView.setAdapter(mAutoCompleteAdapter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}