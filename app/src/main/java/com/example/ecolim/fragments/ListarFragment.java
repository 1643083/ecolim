package com.example.ecolim.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolim.R;
import com.example.ecolim.adapters.AdapterProductos;
import com.example.ecolim.model.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListarFragment extends Fragment {

    RecyclerView RVProductos;
    ArrayList<Producto> listProductos = new ArrayList<>();
    AdapterProductos adapterProductos;
    RequestQueue requestQueue;

    private final String URL = "http://localhost:3000/productos";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar, container, false);

        RVProductos = view.findViewById(R.id.RVProductos);
        RVProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapterProductos = new AdapterProductos(listProductos);
        RVProductos.setAdapter(adapterProductos);

        obtenerDatosWS();

        return view;
    }

    private void obtenerDatosWS() {
        requestQueue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    listProductos.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            listProductos.add(new Producto(
                                    obj.getString("nombre"),
                                    obj.getString("categoria"),
                                    obj.getString("descripcion"),
                                    obj.getString("garantia"),
                                    obj.getDouble("precio"),
                                    obj.getInt("stock")
                            ));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    adapterProductos.notifyDataSetChanged();
                },
                error -> {}
        );

        requestQueue.add(request);
    }
}