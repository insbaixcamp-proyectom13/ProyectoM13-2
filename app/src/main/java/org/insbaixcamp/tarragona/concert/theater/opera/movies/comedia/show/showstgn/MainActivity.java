package org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.data.FirebaseConnection;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.databinding.ActivityMainBinding;
import org.insbaixcamp.tarragona.concert.theater.opera.movies.comedia.show.showstgn.utilities.PicassoTrustAll;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PicassoTrustAll.getInstance(getApplicationContext());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_iniciar_sessio, R.id.nav_event, R.id.nav_carrito, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FirebaseConnection fbc = new FirebaseConnection();
//        fbc.getOpinions(new FirebaseConnection.FireStoreResults() {
//            @Override
//            public void onResultGet() {
//                Log.i("opiniions", fbc.getListOpinions().toString());
//            }
//        });

//        fbc.getEventOpinions(3, new FirebaseConnection.FireStoreResults() {
//            @Override
//            public void onResultGet() {
//                Log.i("opiniions", fbc.getListOpinions().toString());
//            }
//        });
//
//        fbc.getUserReserves("VjRto7OUIZfyJwuId9KHBhAfOkQ2", new FirebaseConnection.FireStoreResults() {
//            @Override
//            public void onResultGet() {
//                Log.i("reserves", fbc.getListReserves().toString());
//            }
//        });
//
//        fbc.getUserData("JdeDyVWJ2uQEKAHBWxCKTMVXwJp2", new FirebaseConnection.FireStoreResults() {
//            @Override
//            public void onResultGet() {
//                Log.i("reserves", fbc.getDadesUsuari().toString());
//            }
//        });

//        fbc.getReserves(new FirebaseConnection.FireStoreResults() {
//            @Override
//            public void onResultGet() {
//                Log.i("reserves", fbc.getListReserves().toString());
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}