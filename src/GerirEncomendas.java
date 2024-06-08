import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GerirEncomendas implements Serializable {
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

    public HashMap<Cliente, ArrayList<Encomendas>> getEncomendasByStatus(int aStatus) {
        HashMap<Cliente, ArrayList<Encomendas>> encomendasComStatus = new HashMap<>();

        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            Cliente cliente = entry.getKey();
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            ArrayList<Encomendas> encomendasFiltradas = new ArrayList<>();

            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getEstatus() == aStatus) {
                    encomendasFiltradas.add(encomenda);
                }
            }

            if (!encomendasFiltradas.isEmpty()) {
                encomendasComStatus.put(cliente, encomendasFiltradas);
            }
        }

        return encomendasComStatus;

    }

    public HashMap<Cliente, ArrayList<Encomendas>> getEncomendasByStatus(int aStatus, HashMap<Cliente, ArrayList<Encomendas>> aHashMap) {
        HashMap<Cliente, ArrayList<Encomendas>> resultado = new HashMap<>();

        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : aHashMap.entrySet()) {
            Cliente cliente = entry.getKey();
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            ArrayList<Encomendas> encomendasFiltradas = new ArrayList<>();

            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getEstatus() == aStatus) {
                    encomendasFiltradas.add(encomenda);
                }
            }

            if (!encomendasFiltradas.isEmpty()) {
                resultado.put(cliente, encomendasFiltradas);
            }
        }

        return resultado;

    }

    public HashMap<Cliente, ArrayList<Encomendas>> getEncomendasByFarmaceutico(Farmaceutico aFarmaceutico) {
        if (aFarmaceutico == null) {
            return null;
        }
        HashMap<Cliente, ArrayList<Encomendas>> resultado = new HashMap<>();
        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            Cliente cliente = entry.getKey();
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            ArrayList<Encomendas> encomendasFiltradas = new ArrayList<>();
            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getFarmaceutico().equals(aFarmaceutico)) {
                    encomendasFiltradas.add(encomenda);
                }
            }
            if (!encomendasFiltradas.isEmpty()) {
                resultado.put(cliente, encomendasFiltradas);
            }
        }
        return resultado;
    }

    public ArrayList<Encomendas> getEncomendasByCliente(Cliente aCliente) {
        if (aCliente == null) {
            return null;
        }
        return lista.get(aCliente);
    }

    public Encomendas getEncomendaById(int aCodigo) {
        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getId() < aCodigo) {
                    return encomenda;
                }
            }
        }
        return null;
    }

    public Cliente getClienteByEncomenda(Encomendas aEncomenda) {
        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.equals(aEncomenda)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
    // Deve ser possível pesquisar serviços com tempo despendido superior a um
    // determinado limite (introduzido pelo utilizador no momento de pesquisa).
    public HashMap<Cliente, Encomendas> getEncomendasByhorasgastas(float aHoras) {
        HashMap<Cliente, Encomendas> Resultados = new HashMap<Cliente, Encomendas>();
        for (Map.Entry<Cliente, ArrayList<Encomendas>> entry : lista.entrySet()) {
            Cliente cliente = entry.getKey();
            ArrayList<Encomendas> todasEncomendas = entry.getValue();
            for (Encomendas encomenda : todasEncomendas) {
                if (encomenda.getHorasGastas() > aHoras) {
                    Resultados.put(cliente, encomenda);
                }
            }
        }
        return Resultados;

    }
    public boolean atribuirTotal(Encomendas aEncomenda, float aValor){
        if (aEncomenda!=null&& aValor>=0){
            aEncomenda.setTotal(aValor);
            return true;
        }
        else return false;
    }
    
    public boolean atribuirHoras(Encomendas aEncomenda, float aValor){
        if (aEncomenda!=null&& aValor>=0){
            aEncomenda.setHorasGastas(aValor);
            return true;
        }
        else return false;
    }

    public boolean aprovarEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(2);
    }

    public boolean processarEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(3);
        
    }

    public boolean concluirEncomenda(Encomendas aEncomenda) {
        if (aEncomenda.setEstatus(4)) {
            for (Object item : aEncomenda.getLista()) {
                if (item instanceof Medicamentos) {
                    Medicamentos medicamento = (Medicamentos) item;
                    medicamento.setStock(medicamento.getStock()-1); // Supondo que 1 unidade é vendida por cada item na encomenda
                }
            }
            return true;
        }
        return false;
    }

    public boolean encerrarEncomenda(Encomendas aEncomenda) {
        return aEncomenda.setEstatus(5);
    }

    public boolean AssociarFarmaceutico(Encomendas aEncomenda, Farmaceutico aFarmaceutico) {
        return aEncomenda.setFarmaceutico(aFarmaceutico);
    }

}
