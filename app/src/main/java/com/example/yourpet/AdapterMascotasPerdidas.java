package com.example.yourpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMascotasPerdidas extends RecyclerView.Adapter<AdapterMascotasPerdidas.PerdidasViewHolder> {

    private Context context;
    private List<DetallesMascota> mDatosPet;

    public AdapterMascotasPerdidas(Context c, List<DetallesMascota> mDatosPet) {
        this.context = c;
        this.mDatosPet = mDatosPet;
    }

    @Override
    public PerdidasViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.reportes_item, parent, false);
        return new PerdidasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PerdidasViewHolder holder, int position) {

        DetallesMascota uploadPet = mDatosPet.get(position);
        DatosPerdida datos = uploadPet.getDatosPerdida();


        holder.titleNombre.setText("Nombre");
        holder.titleId.setText("ID");
        holder.titleFecha.setText("Fecha de desaparición");
        holder.titleDistrito.setText("Distrito");
        holder.titleCelular.setText("Celular");
        holder.titleDescripcion.setText("Descripción");

        holder.textViewNombre.setText(uploadPet.getNombre());
        holder.textViewId.setText(uploadPet.getId());
        holder.textViewFecha.setText(datos.getFechaLost());
        holder.textViewDistrito.setText(uploadPet.getDistrito());
        holder.textViewCelular.setText(datos.getNumeroContacto());
        holder.textViewDescripcion.setText(datos.getDescripcion());

        Picasso.with(context).load(uploadPet.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerInside().into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return mDatosPet.size();
    }

    public class PerdidasViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombre, textViewId, textViewFecha, textViewDistrito, textViewCelular,textViewDescripcion;
        public TextView titleNombre, titleId, titleFecha,titleDistrito, titleCelular,titleDescripcion;
        public ImageView foto;

        public PerdidasViewHolder(View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textView4);
            textViewId = itemView.findViewById(R.id.textView11);
            textViewFecha = itemView.findViewById(R.id.textView15);
            textViewDistrito = itemView.findViewById(R.id.textView20);
            textViewCelular = itemView.findViewById(R.id.textView17);
            textViewDescripcion = itemView.findViewById(R.id.textView13);


            titleNombre = itemView.findViewById(R.id.textView);
            titleId = itemView.findViewById(R.id.textView9);
            titleFecha = itemView.findViewById(R.id.textView14);
            titleDistrito = itemView.findViewById(R.id.textView19);
            titleCelular = itemView.findViewById(R.id.textView16);
            titleDescripcion = itemView.findViewById(R.id.textView12);

            foto = itemView.findViewById(R.id.imageView);
        }

    }
}
