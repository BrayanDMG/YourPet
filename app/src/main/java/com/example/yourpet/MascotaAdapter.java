package com.example.yourpet;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private Context context;
    private List<DetallesMascota> mDatosPet;
    private OnItemClickListener mListener;

    public MascotaAdapter(Context c, List<DetallesMascota> mDatosPet) {
        this.context = c;
        this.mDatosPet = mDatosPet;
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MascotaViewHolder holder, int position) {

        DetallesMascota uploadPet = mDatosPet.get(position);

        holder.titleNombre.setText("Nombre");
        holder.titleMascota.setText("Mascota");
        holder.titleId.setText("ID");
        holder.titleRaza.setText("Raza");
        holder.titleColor.setText("Color");
        holder.titleAños.setText("Años");
        holder.titleDistrito.setText("Distrito");

        holder.textViewNombre.setText(uploadPet.getNombre());
        holder.textViewMascota.setText(uploadPet.getTipo());
        holder.textViewId.setText(uploadPet.getId());
        holder.textViewRaza.setText(uploadPet.getRaza());
        holder.textViewColor.setText(uploadPet.getColor());
        holder.textViewAños.setText(uploadPet.getAños());
        holder.textViewDistrito.setText(uploadPet.getDistrito());

        Picasso.with(context).load(uploadPet.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerInside().into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return mDatosPet.size();
    }

    public class MascotaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewNombre, textViewMascota, textViewId, textViewRaza,
                textViewColor, textViewAños, textViewDistrito;
        public TextView titleNombre, titleMascota, titleId, titleRaza, titleColor,
                titleAños, titleDistrito;
        public ImageView foto;

        public MascotaViewHolder(View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textViewPPnombre);
            textViewMascota = itemView.findViewById(R.id.textViewPPmascota);
            textViewId = itemView.findViewById(R.id.textViewPPid);
            textViewRaza = itemView.findViewById(R.id.textViewPPraza);
            textViewColor = itemView.findViewById(R.id.textViewPPcolor);
            textViewAños = itemView.findViewById(R.id.textViewPPaños);
            textViewDistrito = itemView.findViewById(R.id.textViewPPdistrito);

            titleNombre = itemView.findViewById(R.id.textViewTnombre);
            titleMascota = itemView.findViewById(R.id.textViewTmascota);
            titleId = itemView.findViewById(R.id.textViewTid);
            titleRaza = itemView.findViewById(R.id.textViewTraza);
            titleColor = itemView.findViewById(R.id.textViewTcolor);
            titleAños = itemView.findViewById(R.id.textViewTaños);
            titleDistrito = itemView.findViewById(R.id.textViewTdistrito);

            foto = itemView.findViewById(R.id.imageViewPP);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Seleccione la Acción");
            MenuItem doWhatever = menu.add(Menu.NONE,1,1,"Reportar Pérdida");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Eliminar Mascota");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                  switch (item.getItemId()){
                      case 1:
                          mListener.onReportarClick(position);
                          return  true;
                      case 2 :
                          mListener.onDeleteClick(position);
                          return true;
                  }
                }
            }

            return false;
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onReportarClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
