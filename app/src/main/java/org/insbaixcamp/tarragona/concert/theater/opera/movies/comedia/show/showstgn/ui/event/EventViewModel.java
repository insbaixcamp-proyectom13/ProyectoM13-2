package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.event;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Opinio;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Reserva;

import java.util.ArrayList;

public class EventViewModel extends ViewModel {

    private MutableLiveData<Event> mEvent;
    private FirebaseConnection fb;
    private MutableLiveData<ArrayList<Opinio>> mOpinions;

    public EventViewModel() {
        Gson gson = new Gson();
        fb = new FirebaseConnection();
        mEvent = new MutableLiveData<>();
        mOpinions = new MutableLiveData<>();

        fb.getDatabaseReference().child("opinions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Opinio opinio = snapshot.getValue(Opinio.class);
                Log.i("opinio", opinio.toString());

                if (mOpinions.getValue() != null) {
                    ArrayList<Opinio> opinions = mOpinions.getValue();
                    opinions.add(opinio);
                    mOpinions.setValue(opinions);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
            public void onResultGet(ArrayList list) {
                mOpinions.setValue(fb.getListOpinions());
            }
        });
    }

    public void setEvent(Event event) {
        mEvent.setValue(event);
        loadOpinions();
    }

}
