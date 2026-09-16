package com.example.ecolim;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.ecolim.fragments.ListarFragment;
import com.example.ecolim.fragments.BuscarFragment;
import com.example.ecolim.fragments.RegistrarFragment;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.bottomNav);

        // Fragment inicial al abrir la app
        cambiarFragment(new ListarFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_listar) {
                cambiarFragment(new ListarFragment());
                return true;
            } else if (id == R.id.nav_buscar) {
                cambiarFragment(new BuscarFragment());
                return true;
            } else if (id == R.id.nav_registrar) {
                cambiarFragment(new RegistrarFragment());
                return true;
            }
            return false;
        });
    }

    private void cambiarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}