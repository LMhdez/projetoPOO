import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public HashMap<Cliente, ArrayList<Encomendas>> getEncomendasDecorrer() {
        HashMap<Cliente, ArrayList<Encomendas>> encomendasComStatus3 = new HashMap<>();

        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            Cliente cliente = entry.getKey();
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            ArrayList<Encomendas> encomendasFiltradas = new ArrayList<>();

            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getEstatus() == 3) {
                    encomendasFiltradas.add(encomenda);
                }
            }

            if (!encomendasFiltradas.isEmpty()) {
                encomendasComStatus3.put(cliente, encomendasFiltradas);
            }
        }

        return encomendasComStatus3;

    }
    public HashMap<Cliente, ArrayList<Encomendas>> getEncomendasEncerradas() {
        HashMap<Cliente, ArrayList<Encomendas>> encomendasComStatus5 = new HashMap<>();

        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            Cliente cliente = entry.getKey();
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            ArrayList<Encomendas> encomendasFiltradas = new ArrayList<>();

            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getEstatus() == 5) {
                    encomendasFiltradas.add(encomenda);
                }
            }

            if (!encomendasFiltradas.isEmpty()) {
                encomendasComStatus5.put(cliente, encomendasFiltradas);
            }
        }

        return encomendasComStatus5;

    }

    public ArrayList<Encomendas> getEncomendasFarmaceutico(Farmaceutico aFarmaceutico) {
        if (aFarmaceutico == null) {
            return null;
        }
        ArrayList<Encomendas> encomendasFarmaceutico = new ArrayList<Encomendas>();

        for (ArrayList<Encomendas> listaEncomendas : lista.values()) {
            for (Encomendas encomenda : listaEncomendas) {
                if (encomenda.getFarmaceutico().equals(aFarmaceutico)) {
                    encomendasFarmaceutico.add(encomenda);
                }
            }
        }

        return encomendasFarmaceutico;
    }

    public ArrayList<Encomendas> getEncomendasCliente(Cliente aCliente) {
        if (aCliente == null) {
            return null;
        }
        return lista.get(aCliente);
    }

    public boolean aprovarEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(2);
    }

    public boolean processarEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(3);
    }

    public boolean concluirEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(4);
    }

    public boolean encerrarEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(5);
    }

    public boolean AssociarFarmaceutico(Encomendas aEncomenda, Farmaceutico aFarmaceutico) {
        return aEncomenda.setFarmaceutico(aFarmaceutico);
    }

}
