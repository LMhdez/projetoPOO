import java.util.ArrayList;
import java.util.HashMap;



public class GerirMedicamentos {
    private ArrayList<Medicamentos> listaMedicamentos = new ArrayList<Medicamentos>();
    private ArrayList<Excipiente>listaExcipientes = new ArrayList<Excipiente>();

    private HashMap<Integer, Categoria> listaCategorias;

    public GerirMedicamentos() {
        listaMedicamentos = new ArrayList<Medicamentos>();
        listaExcipientes = new ArrayList<Excipiente>();
        listaCategorias = new HashMap<Integer, Categoria>();
    }

    public boolean criarCategoria(String aDesignacao, String aClassificacao, int aCodigo, String aFornecedor) {
        if (verificaCodCategoria(aCodigo)) {
            return false;
        } else {
            Categoria categoria = new Categoria(aDesignacao, aClassificacao, aCodigo, aFornecedor);
            listaCategorias.put(aCodigo, categoria);
            return true;
        }
    }

    public boolean CriarMedicamento(String aMarca, String aLote, ComponenteAtivo aComponenteAct, String aDosagem,
            int aStock, float aPreco, int aAnoFabrico, boolean aMedicoNecessario,ArrayList<Excipiente> aExcipientes, ArrayList<Categoria> aCategorias, boolean aGenerico) {
        Medicamentos medicamento = new Medicamentos(aMarca, aLote, aComponenteAct, aDosagem, aStock, aPreco,
                aAnoFabrico, aMedicoNecessario,aExcipientes,aCategorias, aGenerico);

        return listaMedicamentos.add(medicamento);

    }
    public boolean criarExcipiente(String aDesignacao, String aClassificacao, int aQuantidade) {
        Excipiente excipiente = new Excipiente(aDesignacao, aClassificacao, aQuantidade);
        listaExcipientes.add(excipiente);
        return true;
    }

    public ArrayList<Medicamentos> getMedicamentos() {
        return listaMedicamentos;
    }
public ArrayList<Medicamentos> getOrderedMedicamentos(){
    ArrayList<Medicamentos> medicamentos = new ArrayList<Medicamentos>();
        for (Medicamentos m : listaMedicamentos) {
            medicamentos.add(m);
        }
        medicamentos.sort(null);
        return medicamentos;


}
    public boolean setStock(Medicamentos aMedicamento, int aStock) {
        return aMedicamento.setStock(aStock);
    }

    public boolean vender(Medicamentos aMedicamento, int aQuantity) {
        return aMedicamento.setStock(aMedicamento.getStock() - aQuantity);
    }

    public boolean verificaCodCategoria(int aCodigo) {
        return listaCategorias.containsKey(aCodigo);
    }

}
