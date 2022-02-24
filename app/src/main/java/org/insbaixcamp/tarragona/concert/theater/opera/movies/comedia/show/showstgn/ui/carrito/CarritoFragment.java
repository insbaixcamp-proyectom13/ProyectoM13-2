package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.carrito;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.R;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.adapters.ReservesAdapter;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.databinding.FragmentCarritoBinding;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Reserva;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CarritoFragment extends Fragment {

    private CarritoViewModel mViewModel;
    private FragmentCarritoBinding binding;
    private FirebaseConnection fb;
    private Gson gson;
    private ArrayList<Event> expiredEvents;
    private ArrayList<Event> reservedEvents;
    private ArrayList<Event> events;
    private ArrayList<Reserva> filteredReserves;
    private ArrayList<Reserva> expiredReserves;
    private ReservesAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ReservesAdapter adapterExpired;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCarritoBinding.inflate(inflater, container, false);
        gson = new Gson();
        fb = new FirebaseConnection();
        linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvReserves.setLayoutManager(linearLayoutManager);

        reservedEvents = new ArrayList<>();
        filteredReserves = new ArrayList<>();
        expiredReserves = new ArrayList<>();

        binding.tvPendents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvPendents.setBackgroundResource(R.drawable.reservas_background_button_left_selected);
                binding.tvExpirats.setBackgroundResource(R.drawable.reservas_background_button_right);
                changeAdapter(adapter);
            }
        });

        binding.tvExpirats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvPendents.setBackgroundResource(R.drawable.reservas_background_button_left);
                binding.tvExpirats.setBackgroundResource(R.drawable.reservas_background_button_right_selected);
                changeAdapter(adapterExpired);
            }
        });

        //Primero se cargan todas las reservas y se filtran los eventos coinicidentes
        fb.getEvents(new FirebaseConnection.FireStoreResults() {
            @Override
            public void onResultGet(ArrayList list) {
                events = list;

                fb.getDatabaseReference().child("reserves").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        String string = gson.toJson(task.getResult().getValue());
                        ArrayList<Reserva> reservas = gson.fromJson(string, new TypeToken<ArrayList<Reserva>>(){}.getType());

                        for (Reserva reserva : reservas) {
                            if (reserva.getClient().equals("JdeDyVWJ2uQEKAHBWxCKTMVXwJp2") && !eventHaExpirat(reserva.getEvent())) {
                                filteredReserves.add(reserva);
                            }
                        }

//                Log.i("events", events.toString());
//                Log.i("reservas", filteredReserves.toString());

                        filtrarEvents(filteredReserves, events);

//                Log.i("reservedEvents", reservedEvents.toString());



                        for (Reserva reserva : reservas) {
                            if (reserva.getClient().equals("JdeDyVWJ2uQEKAHBWxCKTMVXwJp2") && eventHaExpirat(reserva.getEvent())) {
                                expiredReserves.add(reserva);
                            }
                        }
                        Log.i("reservas", expiredReserves.toString());
                        filtrarEventsExpirats(expiredReserves, events);

                        adapter = new ReservesAdapter(filteredReserves, events);
                        adapterExpired = new ReservesAdapter(expiredReserves, events);

                        binding.rvReserves.setAdapter(adapter);

                    }
                });
            }
        });





        fb.getDatabaseReference().child("reserves").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Reserva reserva = snapshot.getValue(Reserva.class);

                if (reserva.getClient().equals("JdeDyVWJ2uQEKAHBWxCKTMVXwJp2")) {
                    ArrayList<Reserva> newReserves = new ArrayList<>();
                    for (int i = 0; i < filteredReserves.size(); i++) {

                        if (reserva.getId() != filteredReserves.get(i).getId()) {
                            newReserves.add(filteredReserves.get(i));
                        }
                    }
                    filteredReserves = newReserves;
                }

                Log.i("cambios", filteredReserves.toString());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }

    private void filtrarEventsExpirats(ArrayList<Reserva> expiredReserves, ArrayList<Event> events) {
        for (Event event : events) {
            for (Reserva reserva : filteredReserves) {

                //Faltaria comprobar que la fecha del evento no sea major que la del sistema
                if (reserva.getEvent() == event.getId()) {
                    expiredEvents.add(event);
                }
            }
        }
    }

    private boolean eventHaExpirat(int idEvent) {

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        for (Event event : reservedEvents) {
            if ((event.getId() == idEvent) && event.getData().equals(formatter.format(date))) {
                return false;
            }
        }

        return true;
    }

    private void changeAdapter(ReservesAdapter adapter) {
        binding.rvReserves.setAdapter(adapter);

    }

    private void filtrarEvents(ArrayList<Reserva> filteredReserves, ArrayList<Event> events) {
        for (Event event : events) {
            for (Reserva reserva : filteredReserves) {

                //Faltaria comprobar que la fecha del evento no sea major que la del sistema
                if (reserva.getEvent() == event.getId()) {
                    reservedEvents.add(event);
                }
            }
        }
    }

}