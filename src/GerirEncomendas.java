import java.util.ArrayList;
import java.util.HashMap;

public class GerirEncomendas {
    private HashMap<Cliente, ArrayList<Encomendas>> lista = new HashMap<Cliente, ArrayList<Encomendas>>();

    public GerirEncomendas() {
        lista = new HashMap<Cliente, ArrayList<Encomendas>>();
    }

    public boolean adicionarEncomenda(Cliente aCliente, Encomendas aEncomenda) {
        if (aCliente == null || aEncomenda == null) {
            return false;
        }
        ArrayList<Encomendas> encomendasList = lista.get(aCliente);

        if (encomendasList == null) {
            encomendasList = new ArrayList<Encomendas>();
            lista.put(aCliente, encomendasList);
        }

        encomendasList.add(aEncomenda);
        return true;

    }
    public HashMap<Cliente, ArrayList<Encomendas>> getEncomendas() {
        return lista;
    }

}
