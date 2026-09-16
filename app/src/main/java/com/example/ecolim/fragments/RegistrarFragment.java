package com.example.ecolim.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecolim.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarFragment extends Fragment {

    EditText edtNombre, edtCategoria, edtDescripcion, edtGarantia, edtPrecio, edtStock;
    Button btnGuardar;
    RequestQueue requestQueue;

    private final String URL = "http://localhost:3000/productos";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar, container, false);

        edtNombre = view.findViewById(R.id.edtNombre);
        edtCategoria = view.findViewById(R.id.edtCategoria);
        edtDescripcion = view.findViewById(R.id.edtDescripcion);
        edtGarantia = view.findViewById(R.id.edtGarantia);
        edtPrecio = view.findViewById(R.id.edtPrecio);
        edtStock = view.findViewById(R.id.edtStock);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                registrarProducto();
            }
        });

        return view;
    }

    private boolean validarCampos() {
        if (edtNombre.getText().toString().trim().isEmpty()) {
            edtNombre.setError("El nombre es obligatorio");
            return false;
        }
        if (edtCategoria.getText().toString().trim().isEmpty()) {
            edtCategoria.setError("La categoría es obligatoria");
            return false;
        }
        if (edtPrecio.getText().toString().trim().isEmpty()) {
            edtPrecio.setError("El precio es obligatorio");
            return false;
        }
        return true;
    }

    private void registrarProducto() {
        requestQueue = Volley.newRequestQueue(requireContext());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", edtNombre.getText().toString());
            jsonObject.put("categoria", edtCategoria.getText().toString());
            jsonObject.put("descripcion", edtDescripcion.getText().toString());
            jsonObject.put("garantia", edtGarantia.getText().toString());
            jsonObject.put("precio", Double.parseDouble(edtPrecio.getText().toString()));
            jsonObject.put("stock", edtStock.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtStock.getText().toString()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                response -> {
                    try {
                        Toast.makeText(requireContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(requireContext(), "No se pudo grabar", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }
}