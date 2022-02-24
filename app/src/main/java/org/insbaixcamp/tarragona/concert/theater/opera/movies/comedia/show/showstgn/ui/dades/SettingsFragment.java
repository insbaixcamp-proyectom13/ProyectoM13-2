package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.ui.dades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.databinding.FragmentSettingsBinding;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {

    private SettingsFragment settingsFragment;
    private FirebaseConnection fb;
    private LinearLayoutManager layoutManager;
    private View root;
    private DatabaseReference mDatabase;
    public final String url = "https://showstarragona-default-rtdb.firebaseio.com/";
    private FragmentSettingsBinding binding;
    private int telefon = 0;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance(url);
        mDatabase = database.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously();
        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        EditText etCanviaNom = binding.etCanviaNom;
        EditText etCanviaCognoms = binding.etCanviaCognoms;
        EditText etCanviaTelefon = binding.etCanviaTelefon;
        EditText etCanviaCorreu = binding.etCanviaCorreu;
        EditText etCanviaAdreca = binding.etCanviaAdreca;
        Button bDesarCanvis = binding.bDesarCanvis;


        mDatabase.child("usuaris").child(id)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    etCanviaNom.setText(snapshot.child("nom").getValue().toString());
                    etCanviaCognoms.setText(snapshot.child("cognom").getValue().toString());
                    etCanviaTelefon.setText(snapshot.child("telefon").getValue().toString());
                    telefon = Integer.parseInt(snapshot.child("telefon").getValue().toString());
                    etCanviaCorreu.setText(snapshot.child("correu").getValue().toString());
                    etCanviaAdreca.setText(snapshot.child("adreca").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bDesarCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern
                        .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                String nom = etCanviaNom.getText().toString();
                String cognom = etCanviaCognoms.getText().toString();
                String adreca = etCanviaAdreca.getText().toString();
                String correu = etCanviaCorreu.getText().toString();

                if(!etCanviaTelefon.getText().toString().isEmpty()){
                    telefon = Integer.valueOf(etCanviaTelefon.getText().toString());
                }

                if (!correu.isEmpty() && !nom.isEmpty() && !cognom.isEmpty() && !adreca.isEmpty()) {
                    Matcher machercorreu = pattern.matcher(correu);
                    if (machercorreu.matches()){
                        Map<String, Object> usuariMap = new HashMap();
                        usuariMap.put("nom", nom);
                        usuariMap.put("cognom", cognom);
                        usuariMap.put("telefon", telefon);
                        usuariMap.put("correu", correu);
                        usuariMap.put("adreca", adreca);
                        mDatabase.child("usuaris").child(id).updateChildren(usuariMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "S'han desat els canvis correctament", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "El format del correu no és vàlid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Hi ha camps sense emplenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

}
