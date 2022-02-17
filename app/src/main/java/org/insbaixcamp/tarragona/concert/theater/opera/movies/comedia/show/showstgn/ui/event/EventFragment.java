package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.databinding.FragmentDetallsEventBinding;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.pojo.Event;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.utilities.PicassoTrustAll;

public class EventFragment extends Fragment {

    private EventViewModel eventViewModel;
    private FragmentDetallsEventBinding binding;
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        binding = FragmentDetallsEventBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        TextView tvTitle = binding.textView2;
        TextView tvDescripcio = binding.tvDescripcio;
        TextView tvDireccio = binding.tvDireccio;
        TextView tvData = binding.tvData;
        TextView tvAforament = binding.tvAforament;
        TextView tvHora = binding.tvHora;
        ImageView ivEvent = binding.ivDetailEvent;

        if (getArguments() != null) {
            Event selectedEvent = (Event) getArguments().getSerializable("event");
            tvTitle.setText(selectedEvent.getNom());
            tvAforament.setText(String.valueOf(selectedEvent.getAforament()));
            tvDescripcio.setText(selectedEvent.getDescripcio());
            tvData.setText(selectedEvent.getData());
            tvHora.setText(selectedEvent.getHora());
            tvDireccio.setText(selectedEvent.getAdreça());
            PicassoTrustAll.getInstance().load(selectedEvent.getImatge()).into(ivEvent);
        }

        return root;
    }
}
