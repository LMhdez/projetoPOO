import java.util.ArrayList;
import java.util.HashMap;

public class GerirMedicamentos {
    private ArrayList<Medicamentos> listaMedicamentos = new ArrayList<Medicamentos>();
    private HashMap<Integer, Categoria> listaCategorias;

    public GerirMedicamentos() {
        listaMedicamentos = new ArrayList<Medicamentos>();
        listaCategorias = new HashMap<Integer, Categoria>();
    }

    public boolean adicionarMedicamento(Medicamentos aMedicamento) {
        if (aMedicamento == null) {
            return false;
        }
        return listaMedicamentos.add(aMedicamento);
    }

    public Medicamentos CriarMedicamento(String aMarca, String aLote, String aComponenteAct, String aDosagem,
            int aStock, float aPreco, int aAnoFabrico, boolean aMedicoNecessario, boolean aGenerico) {
        Medicamentos medicamento = new Medicamentos(aMarca, aLote, aComponenteAct, aDosagem, aStock, aPreco,
                aAnoFabrico, aMedicoNecessario, aGenerico);
        listaMedicamentos.add(medicamento);

        return medicamento;
    }

    public ArrayList<Medicamentos> getMedicamentos() {
        return listaMedicamentos;
    }

    public boolean setStock(Medicamentos aMedicamento, int aStock) {
        return aMedicamento.setStock(aStock);
    }

    public boolean soldQuantity(Medicamentos aMedicamento, int aQuantity) {
        return aMedicamento.setStock(aMedicamento.getStock() - aQuantity);
    }

}
