package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Opinio;

import java.util.ArrayList;

public class EventViewModel extends ViewModel {

    private MutableLiveData<Event> mEvent;
    private FirebaseConnection fb;
    private MutableLiveData<ArrayList<Opinio>> mOpinions;

    public EventViewModel() {
        fb = new FirebaseConnection();
        mEvent = new MutableLiveData<>();
        mOpinions = new MutableLiveData<>();
    }

    public LiveData<Event> getEvent() {
        return mEvent;
    }

    public LiveData<ArrayList<Opinio>> getOpinions() {
        return mOpinions;
    }

    public void loadOpinions() {
        fb.getEventOpinions(mEvent.getValue().getId(), new FirebaseConnection.FireStoreResults() {
            @Override
            public void onResultGet() {
                mOpinions.setValue(fb.getListOpinions());
            }
        });
    }

    public void setEvent(Event event) {
        mEvent.setValue(event);
        loadOpinions();
    }

}
