package cl.gerardolupa.android.consolidado_lista_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.gerardolupa.android.consolidado_lista_view.adaptador.FrutaAdaptador;
import cl.gerardolupa.android.consolidado_lista_view.modelo.Fruit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //variables Listview, GridView y Adapters
    private ListView MiLista;
    private GridView MiGrilla;
    private FrutaAdaptador adaptadorListView;
    private FrutaAdaptador adaptadorGridView;


    //lista de nuestro modelo de la fruta
    private List<Fruit> frutas;

    //items en el menu options menu
    private MenuItem itemListView;
    private MenuItem itemGridView;


    //contador para la nueva fruta que se agregue
    private int counter = 0;
    //el valor no importa mucho es solo para fines esteticos
    private final int SWITCH_TO_LIST_VIEW = 0;
    private final int SWITCH_TO_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. mostrar el icono en la barra superior
        this.EnforceIconBar();

        //2. llamamos al metodo para obtener toda las frutas
        this.frutas = getAllFruits();

        //rescatamos el listview y gridview del layout asociar el layout con el codigo
        this.MiLista = findViewById(R.id.miListaView);
        this.MiGrilla = findViewById(R.id.miGridView);

        //adjuntando el mismo metodo para ambos
        this.MiLista.setOnItemClickListener(this);
        this.MiGrilla.setOnItemClickListener(this);

        //usamos los adaptadores para distintos tipos de vista uno en lista y otro en grilla
        this.adaptadorListView = new FrutaAdaptador(this, R.layout.list_view_item_fruta, frutas);
        this.adaptadorGridView = new FrutaAdaptador(this, R.layout.grid_view_item_fruta, frutas);

        //asignamos los adaptadores a su correspondiente vista
        this.MiLista.setAdapter(this.adaptadorListView);
        this.MiGrilla.setAdapter(this.adaptadorGridView);

        //registrar el contexto para ambos
        registerForContextMenu(this.MiLista);
        registerForContextMenu(this.MiGrilla);
    }

    private void EnforceIconBar(){
        //muestra el icono en la barra superior
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.clickFruta(frutas.get(position));
    }

    private void clickFruta(Fruit fruta){
        //diferenciamos entre las frutas conocidas y desconocidas
        if (fruta.getOrigen().equals("Desconocido"))
            Toast.makeText(this, "Lo sentimos no conocemos esa fruta", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "La fruta se llama "+ fruta.getNombreDeLaFruta() + "y viene de "+ fruta.getOrigen(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //con esto forzamos a mostros iconos en el menu superior
        getMenuInflater().inflate(R.menu.option_menu, menu);
       //despues de inflar recogemos las referencias  los botones que nos interesan
        this.itemListView = menu.findItem(R.id.lista_view);
        this.itemGridView = menu.findItem(R.id.grilla_view);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //eventos para los clicks en los botones del menu
        switch (item.getItemId()){
            //si presiona el boton agregar fruta
            case R.id.add_fruta:
                this.addFruta(new Fruit("Agregado nº " + (++counter), R.mipmap.ic_nueva_fruta,  "Desconocido"));
                return true;
            //si presiona el icono i mostrara en forma de lista
            case R.id.lista_view:
                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                return true;
            //si presiona el icono grilla, mostrara en forma de grilla
            case R.id.grilla_view:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //inflamos el context menu con nuestro layout
        MenuInflater inflater = getMenuInflater();
        //antes de inflar le añadimos el header dependiendo del objeto que le pasemos
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.frutas.get(info.position).getNombreDeLaFruta());
        //inflamos
        inflater.inflate(R.menu.context_menu_frutas, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //obtener info en el context menu del objeto que se pinche
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete_fruta:
                this.deleteFruta(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void switchListGridView(int option){
    //metodo para cambiar entre Grid/List view
        if (option == SWITCH_TO_LIST_VIEW){
            //si queremos cambiar a list view, pero justo list view esta en modo invisible
            if (this.MiLista.getVisibility() == View.INVISIBLE){
                //aqui se esconde el grid view y mostramos su boton en el menu de opciones
                this.MiGrilla.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                //ahora enseñamos el list view  y escondemos su correspondiente boton en el menu de opciones
                this.MiLista.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        }else if (option == SWITCH_TO_GRID_VIEW){
            //si queremos cambiar a grid view y el grid view esta en modo invisible
            if (this.MiGrilla.getVisibility() == View.INVISIBLE){
                //aqui se esconde el List view y enseñamos su boton en el meno de opciones
                this.MiLista.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);
                //ahora enseñamos el list view y escondemos su correspondiente boton en el menu e opciones
                this.MiGrilla.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }

    //operaciones CRUD - get , add, delete

    private List<Fruit> getAllFruits(){

        List<Fruit> lista = new ArrayList<Fruit>(){{
            add(new Fruit("Banana", R.mipmap.ic_banana, "La Serena"));
            add(new Fruit("Guinda", R.mipmap.ic_guinda,"Iquique"));
            add(new Fruit("Manzana", R.mipmap.ic_manzana, "Paine"));
            add(new Fruit("Pera", R.mipmap.ic_pera, "Rancagua"));
            add(new Fruit("Frutilla", R.mipmap.ic_frutilla, "Valdivia"));
        }};
        return  lista;
    }

    private void addFruta(Fruit fruta){
        //agregamos en la Lista llamada frutas , el objeto fruta
        this.frutas.add(fruta);
        //avisamos de estos cambios al adapter
        this.adaptadorListView.notifyDataSetChanged();
        this.adaptadorGridView.notifyDataSetChanged();

    }

    private void deleteFruta(int position){
        //eliminamos la fruta de la lista
        this.frutas.remove(position);
        //avisamos del cambios a ambos adaptadores
        this.adaptadorListView.notifyDataSetChanged();
        this.adaptadorGridView.notifyDataSetChanged();
    }


}


