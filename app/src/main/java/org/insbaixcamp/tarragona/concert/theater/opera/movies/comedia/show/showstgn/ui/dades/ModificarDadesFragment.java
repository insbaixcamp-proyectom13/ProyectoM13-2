package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.dades;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.R;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.databinding.FragmentDetallsEventBinding;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.event.EventViewModel;

public class ModificarDadesFragment extends Fragment {

    private ModificarDadesFragment binding;
    private View root;
    private FirebaseConnection fb;
    private LinearLayoutManager layoutManager;
    private Button bGuardarCanvis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        binding = FragmentDetallsEventBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        fb = new FirebaseConnection();

        bGuardarCanvis = binding.bGuardarCanvis;


        return root;
    }

}