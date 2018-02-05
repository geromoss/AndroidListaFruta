package cl.gerardolupa.android.consolidado_lista_view.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.gerardolupa.android.consolidado_lista_view.R;
import cl.gerardolupa.android.consolidado_lista_view.modelo.Fruit;

/**
 * Created by gerardolupahuanca on 04-02-18.
 */

public class FrutaAdaptador extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Fruit> list;

    public FrutaAdaptador(Context context, int layout, List<Fruit> list){
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int id) { return id; }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null){
            //solo si esta vacia renderizamos el item list para reciclar los recursos
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_list,null);
            //hacemos una vista mantenedor donde se hace referencia a los datos nombre, origen e imagen para
            //que le vaya pasando los datos cada vez que recargar la lista
            holder = new ViewHolder();
            holder.nombre = (TextView) convertView.findViewById(R.id.textViewName);
            holder.origen = (TextView) convertView.findViewById(R.id.textViewOrigin);
            holder.icono = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            convertView.setTag(holder);
        }else{
            //obtenemos las referencias que posteriormente colocamos en el convert view
            //y asi reciclamos su uso sin necesidad de buscar uno nuevo, referencias con findviewId
            holder = (ViewHolder) convertView.getTag();
        }
        //creamos un objeto Fruta en la ultima posicion para ser mostrado
        final Fruit actualFruta = (Fruit) getItem(position);
        holder.nombre.setText(actualFruta.getNombreDeLaFruta());
        holder.origen.setText(actualFruta.getOrigen());
        holder.icono.setImageResource(actualFruta.getIcono());
        return convertView;

    }

    //la clase que mantiene encapsulado los 3 elementos de la vista para que sea mas limpio pasar informacion
    static class ViewHolder{
        private TextView nombre;
        private TextView origen;
        private ImageView icono;

    }
}
