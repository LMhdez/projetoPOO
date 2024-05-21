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
    private boolean aprovado;
    private boolean encerrado;
    private int status;// vai variar de 1 a 5
    
    Encomendas( ArrayList<Medicamentos> aMedicamentos, boolean aUrgente){
        this.id++;
        this.medicamentos = aMedicamentos;
        this.urgente = aUrgente;
        this.status = 1;
    }
    public boolean setFarmaceutico(Farmaceutico aFarmaceutico){
        
        if (aprovado && aFarmaceutico != null){ 
         this.farmaceutico = aFarmaceutico;
         return true;
            
        }
        else
        return false;


    }

    public boolean setAprovado(){
        
        return this.aprovado = true;
    }
   

    public boolean setEncerrado(){
        
        return this.encerrado = true;
    }
   public ArrayList<Medicamentos> getMedicamentos() {
       return medicamentos;
   }


}