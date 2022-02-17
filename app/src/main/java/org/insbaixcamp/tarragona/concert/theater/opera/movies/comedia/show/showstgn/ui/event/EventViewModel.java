package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;

public class EventViewModel extends ViewModel {

    private MutableLiveData<Event> mEvent;
    private FirebaseConnection fb;

    public EventViewModel() {
        fb = new FirebaseConnection();
        mEvent = new MutableLiveData<>();
    }

    public LiveData<Event> getEvent() {
        return mEvent;
    }

}
