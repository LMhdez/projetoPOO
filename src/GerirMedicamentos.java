import java.util.ArrayList;
public class GerirMedicamentos {
    private ArrayList<Medicamentos> lista = new ArrayList<Medicamentos>();
    

    public GerirMedicamentos(){
        lista = new ArrayList<Medicamentos>();
    }
    public boolean adicionarMedicamento(Medicamentos aMedicamento){
        if(aMedicamento == null){
            return false;
        }
        return lista.add(aMedicamento);
    }
    public Medicamentos CriarMedicamento(String aMarca, String aLote, ComponenteAtivo aComponenteAct, String aDosagem, int aStock, float aPreco, int aAnoFabrico, boolean aMedicoNecessario, boolean aGenerico) {
        Medicamentos medicamento= new Medicamentos( aMarca, aLote, aComponenteAct, aDosagem, aStock, aPreco, aAnoFabrico, aMedicoNecessario, aGenerico);
        lista.add(medicamento);
        
        return medicamento;
        }
    public ArrayList<Medicamentos> getMedicamentos(){
        return lista;
    }
    public boolean setStock(Medicamentos aMedicamento, int aStock){
        return aMedicamento.setStock(aStock);
    }
    public boolean vender(Medicamentos aMedicamento, int aQuantity){
        return aMedicamento.setStock(aMedicamento.getStock()-aQuantity);
    }




    
}
