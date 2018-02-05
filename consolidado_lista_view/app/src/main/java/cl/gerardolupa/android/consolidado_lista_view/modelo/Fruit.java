package cl.gerardolupa.android.consolidado_lista_view.modelo;

/**
 * Created by gerardolupahuanca on 04-02-18.
 */

public class Fruit {

    private String NombreDeLaFruta;
    private int Icono;
    private String Origen;

    //una clase constructor
    public Fruit(){}

    //otra clase constructor con parametros
    public Fruit(String NombreDeLaFruta, int Icono, String Origen){
        this.NombreDeLaFruta = NombreDeLaFruta;
        this.Icono = Icono;
        this.Origen = Origen;
    }

    //los getter y los setter
    public String getNombreDeLaFruta(){ return NombreDeLaFruta; }

    public void setNombreDeLaFruta(String NombreDeLaFruta){ this.NombreDeLaFruta = NombreDeLaFruta; }

    public int getIcono(){ return  Icono; }

    public void setIcono(int Icono){ this.Icono = Icono; }

    public String getOrigen(){ return Origen; }

    public void setOrigen(String Origen){ this.Origen = Origen; }
}
