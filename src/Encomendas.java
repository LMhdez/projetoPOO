import java.util.ArrayList;

public class Encomendas {
    private int id;
    private Farmaceutico farmaceutico;
    private ArrayList<Medicamentos> medicamentos;
    private float total;
    private String data;
    private float horasGastas;
    private String descricao;
    private boolean urgente;
    private static int controloencomendas=0;
 
    private int status;// vai variar de 1 a 5

    public String toString() {
        return "ID: " + id + " Farmaceutico: " + farmaceutico + " Medicamentos: " + medicamentos + " Total: " + total
                + " Data: " + data
                + " Horas Gastas: " + horasGastas + " Descricao: " + descricao + " Urgencia: " + urgente + " Status: "
                + status;
    }

    Encomendas(ArrayList<Medicamentos> aMedicamentos, boolean aUrgente) {
        this.id=controloencomendas++;
        this.medicamentos = aMedicamentos;
        this.urgente = aUrgente;
        this.status = 1;
    }

    public boolean setFarmaceutico(Farmaceutico aFarmaceutico) {

        if (status>1 && aFarmaceutico != null) {
            this.farmaceutico = aFarmaceutico;
            return true;

        } else
            return false;

    }

    public Farmaceutico getFarmaceutico() {
        return farmaceutico;
    }



    public ArrayList<Medicamentos> getMedicamentos() {
        return medicamentos;
    }
    public boolean setEstatus(int aEstatus){
        if (aEstatus>0&& aEstatus<5){
            this.status = aEstatus;
            return true;
        }
        else return false;

    }
    public int getEstatus(){
        return this.status;
    }
    public int getId() {
        return id;
    }
    public float getHorasGastas() {
        return horasGastas;
    }

}