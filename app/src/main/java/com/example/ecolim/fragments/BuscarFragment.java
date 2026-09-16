package com.example.ecolim.fragments;

import android.app.AlertDialog;
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

public class BuscarFragment extends Fragment {

    EditText edtBuscarId, edtNombre, edtCategoria, edtDescripcion, edtGarantia, edtPrecio, edtStock;
    Button btnBuscar, btnActualizar, btnEliminar;
    RequestQueue requestQueue;

    private final String URL = "http://localhost:3000/productos";
    private int idActual = -1; // guarda el id del producto que se está mostrando

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        edtBuscarId = view.findViewById(R.id.edtBuscarId);
        edtNombre = view.findViewById(R.id.edtNombre);
        edtCategoria = view.findViewById(R.id.edtCategoria);
        edtDescripcion = view.findViewById(R.id.edtDescripcion);
        edtGarantia = view.findViewById(R.id.edtGarantia);
        edtPrecio = view.findViewById(R.id.edtPrecio);
        edtStock = view.findViewById(R.id.edtStock);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnActualizar = view.findViewById(R.id.btnActualizar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        requestQueue = Volley.newRequestQueue(requireContext());

        btnBuscar.setOnClickListener(v -> buscarProducto());
        btnActualizar.setOnClickListener(v -> confirmarAccion("¿Actualizar este producto?", this::actualizarProducto));
        btnEliminar.setOnClickListener(v -> confirmarAccion("¿Eliminar este producto?", this::eliminarProducto));

        return view;
    }

    private void buscarProducto() {
        String id = edtBuscarId.getText().toString().trim();
        if (id.isEmpty()) {
            edtBuscarId.setError("Ingresa un ID");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + "/" + id,
                null,
                response -> {
                    try {
                        edtNombre.setText(response.getString("nombre"));
                        edtCategoria.setText(response.getString("categoria"));
                        edtDescripcion.setText(response.getString("descripcion"));
                        edtGarantia.setText(response.getString("garantia"));
                        edtPrecio.setText(String.valueOf(response.getDouble("precio")));
                        edtStock.setText(String.valueOf(response.getInt("stock")));
                        idActual = Integer.parseInt(id);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(requireContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    private void actualizarProducto() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", edtNombre.getText().toString());
            jsonObject.put("categoria", edtCategoria.getText().toString());
            jsonObject.put("descripcion", edtDescripcion.getText().toString());
            jsonObject.put("garantia", edtGarantia.getText().toString());
            jsonObject.put("precio", Double.parseDouble(edtPrecio.getText().toString()));
            jsonObject.put("stock", Integer.parseInt(edtStock.getText().toString()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                URL + "/" + idActual,
                jsonObject,
                response -> Toast.makeText(requireContext(), "Producto actualizado", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(requireContext(), "No se pudo actualizar", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    private void eliminarProducto() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                URL + "/" + idActual,
                null,
                response -> {
                    Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                },
                error -> Toast.makeText(requireContext(), "No se pudo eliminar", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    private void limpiarCampos() {
        edtBuscarId.setText("");
        edtNombre.setText("");
        edtCategoria.setText("");
        edtDescripcion.setText("");
        edtGarantia.setText("");
        edtPrecio.setText("");
        edtStock.setText("");
        idActual = -1;
    }

    private void confirmarAccion(String mensaje, Runnable accion) {
        if (idActual == -1) {
            Toast.makeText(requireContext(), "Primero busca un producto", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(requireContext())
                .setMessage(mensaje)
                .setPositiveButton("Sí", (dialog, which) -> accion.run())
                .setNegativeButton("Cancelar", null)
                .show();
    }
}