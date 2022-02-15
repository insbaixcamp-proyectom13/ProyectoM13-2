package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Opinio;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Reserva;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Usuari;

import java.util.ArrayList;

public class FirebaseConnection {

    FirebaseDatabase db;
    DatabaseReference myRef;
    Gson gson;
    String jsonValues;
    ArrayList listEvents;
    ArrayList listOpinions;
    ArrayList listReserves;
    Usuari dadesUsuari;
    FireStoreResults fireStoreResults;

    public ArrayList getListEvents() {
        return listEvents;
    }

    public ArrayList getListReserves() {
        return listReserves;
    }

    public Usuari getDadesUsuari() {
        return dadesUsuari;
    }

    public ArrayList getListOpinions() {
        return listOpinions;
    }

    public FirebaseConnection () {
        db = FirebaseDatabase.getInstance("https://showstarragona-default-rtdb.europe-west1.firebasedatabase.app/");
        gson = new Gson();
        myRef = db.getReference();
        listEvents = new ArrayList<>();
        listOpinions = new ArrayList<>();
        listReserves = new ArrayList<>();
    }

    public void getEventOpinions(int idEvent, final FireStoreResults results) {

        myRef.child("opinions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                String string = gson.toJson(task.getResult().getValue());
                ArrayList<Opinio> opinions = gson.fromJson(string, new TypeToken<ArrayList<Opinio>>(){}.getType());
                ArrayList<Opinio> filteredOpinions = new ArrayList<>();

                for (Opinio opinion : opinions) {
                    if (opinion.getEvent() == idEvent) {
                        filteredOpinions.add(opinion);
                    }
                }

                listOpinions = new ArrayList(filteredOpinions);

                results.onResultGet();
            }
        });


    }

    public void getUserData(String UID, final FireStoreResults resul) {

        myRef.child("usuaris").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String string = gson.toJson(task.getResult().getValue());
                Usuari user;

                user = gson.fromJson(string, Usuari.class);
                dadesUsuari = user;
                resul.onResultGet();
            }
        });

    }

    public void getOpinions(final FireStoreResults resul) {

        myRef.child("opinions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {

                String string = gson.toJson(task.getResult().getValue());
                ArrayList<Opinio> opinions = gson.fromJson(string, new TypeToken<ArrayList<Opinio>>(){}.getType());
                listOpinions = opinions;

                resul.onResultGet();
            }
        });

    }

    public void getUserReserves (String UID, final FireStoreResults resul) {

        myRef.child("reserves").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                String string = gson.toJson(task.getResult().getValue());
                ArrayList<Reserva> reserves = gson.fromJson(string, new TypeToken<ArrayList<Reserva>>(){}.getType());
                ArrayList<Reserva> filteresReserves = new ArrayList<>();

                for (Reserva reserva : reserves) {
                    if (reserva.getClient().equals(UID)) {
                        filteresReserves.add(reserva);
                    }
                }

                listReserves = new ArrayList(filteresReserves);

                resul.onResultGet();
            }
        });

    }

    public void getReserves(final FireStoreResults resul) {

        myRef.child("reserves").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {

                String string = gson.toJson(task.getResult().getValue());
                ArrayList<Reserva> reserves = gson.fromJson(string, new TypeToken<ArrayList<Reserva>>(){}.getType());
                listReserves = reserves;

                resul.onResultGet();
            }
        });

    }


    public void getEvents (final FireStoreResults resul) {

        myRef.child("events").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {

                String string = gson.toJson(task.getResult().getValue());
                ArrayList<Event> events = gson.fromJson(string, new TypeToken<ArrayList<Event>>(){}.getType());
                listEvents = events;

                resul.onResultGet();
            }
        });

    }

    public interface FireStoreResults {
        public void onResultGet();
    }

}
