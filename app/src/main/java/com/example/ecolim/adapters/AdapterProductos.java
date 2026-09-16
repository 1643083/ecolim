package com.example.ecolim.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecolim.R;
import com.example.ecolim.model.Producto;

import java.util.ArrayList;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolderProducto> {

    ArrayList<Producto> listProductos;

    public AdapterProductos(ArrayList<Producto> listaEntrada) {
        this.listProductos = listaEntrada;
    }

    @NonNull
    @Override
    public ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolderProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProducto holder, int position) {
        holder.asignarDatos(listProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return listProductos.size();
    }

    public class ViewHolderProducto extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCategoria, txtPrecioStock;

        public ViewHolderProducto(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtPrecioStock = itemView.findViewById(R.id.txtPrecioStock);
        }

        public void asignarDatos(Producto producto) {
            txtNombre.setText(producto.getNombre());
            txtCategoria.setText(producto.getCategoria());
            txtPrecioStock.setText("S/ " + producto.getPrecio() + "  |  Stock: " + producto.getStock());
        }
    }
}