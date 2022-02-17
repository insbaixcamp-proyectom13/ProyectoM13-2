package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.event;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.adapters.OpinioAdapter;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.databinding.FragmentDetallsEventBinding;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Opinio;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.utilities.PicassoTrustAll;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    private EventViewModel eventViewModel;
    private FragmentDetallsEventBinding binding;
    private View root;
    private OpinioAdapter adapter;
    private LinearLayoutManager layoutManager;
    private FirebaseConnection fb;
    private Event selectedEvent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        binding = FragmentDetallsEventBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.incOpinions.recyclerView2.setLayoutManager(layoutManager);
        fb = new FirebaseConnection();

        TextView tvTitle = binding.textView2;
        TextView tvDescripcio = binding.tvDescripcio;
        TextView tvDireccio = binding.tvDireccio;
        TextView tvData = binding.tvData;
        TextView tvAforament = binding.tvAforament;
        TextView tvHora = binding.tvHora;
        ImageView ivEvent = binding.ivDetailEvent;

        if (getArguments() != null) {
            eventViewModel.setEvent((Event) getArguments().getSerializable("event"));
            selectedEvent = (Event) getArguments().getSerializable("event");
            tvTitle.setText(selectedEvent.getNom());
            tvAforament.setText(String.valueOf(selectedEvent.getAforament()));
            tvDescripcio.setText(selectedEvent.getDescripcio());
            tvData.setText(selectedEvent.getData());
            tvHora.setText(selectedEvent.getHora());
            tvDireccio.setText(selectedEvent.getAdreça());
            PicassoTrustAll.getInstance().load(selectedEvent.getImatge()).into(ivEvent);

            eventViewModel.getOpinions().observe(getViewLifecycleOwner(), new Observer<ArrayList<Opinio>>() {
                @Override
                public void onChanged(ArrayList<Opinio> opinios) {
                    if (!opinios.isEmpty()) {
                        binding.incOpinions.cvEmpty.setVisibility(View.GONE);
                        binding.incOpinions.recyclerView2.setVisibility(View.VISIBLE);
                        adapter = new OpinioAdapter(opinios);
                        binding.incOpinions.recyclerView2.setAdapter(adapter);
                    }
                }
            });

            ImageView ivOpina = binding.incOpinions.ivOpina;
            EditText etOpinio = binding.incOpinions.etOpinio;

            ivOpina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String opinio = etOpinio.getText().toString().trim();
                    int puntuacion = 5;
                    String usuari = "JdeDyVWJ2uQEKAHBWxCKTMVXwJp2";
                    String nomUsuari = "JuanJose";


                    if (!opinio.isEmpty() && opinio != "") {
                        etOpinio.setText(null);
                        fb.writeOpinion(opinio, selectedEvent.getId(), usuari, nomUsuari, puntuacion, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                eventViewModel.loadOpinions();
                                adapter.notifyDataSetChanged();
                                binding.incOpinions.cvEmpty.setVisibility(View.GONE);
                                binding.incOpinions.recyclerView2.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }
            });
        }

        return root;
    }
}
