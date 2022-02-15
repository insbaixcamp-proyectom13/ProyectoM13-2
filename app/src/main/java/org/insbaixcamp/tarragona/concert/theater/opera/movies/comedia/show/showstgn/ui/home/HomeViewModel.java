package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Event>> mText;
    private FirebaseConnection fbc;

    public HomeViewModel() {

        fbc = new FirebaseConnection();
        mText = new MutableLiveData<>();
        fbc.getEvents(new FirebaseConnection.FireStoreResults() {
            @Override
            public void onResultGet() {
                mText.setValue(fbc.getListEvents());
            }
        });
    }

    public LiveData<ArrayList<Event>> getEventList() {
        return mText;
    }
}