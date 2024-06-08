import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GerirMedicamentos implements Serializable {
    private ArrayList<Medicamentos> listaMedicamentos = new ArrayList<Medicamentos>();
    private ArrayList<Excipiente> listaExcipientes = new ArrayList<Excipiente>();
    private ArrayList<ComponenteAtivo> listaComponenteAtivos = new ArrayList<ComponenteAtivo>();

    private HashMap<Integer, Categoria> listaCategorias;

    public GerirMedicamentos() {
        listaMedicamentos = new ArrayList<Medicamentos>();
        listaExcipientes = new ArrayList<Excipiente>();
        listaComponenteAtivos = new ArrayList<ComponenteAtivo>();
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
            int aStock, float aPreco, int aAnoFabrico, boolean aMedicoNecessario, ArrayList<Excipiente> aExcipientes,
            ArrayList<Categoria> aCategorias, boolean aGenerico) {
        Medicamentos medicamento = new Medicamentos(aMarca, aLote, aComponenteAct, aDosagem, aStock, aPreco,
                aAnoFabrico, aMedicoNecessario, aExcipientes, aCategorias, aGenerico);

        return listaMedicamentos.add(medicamento);

    }

    public boolean criarExcipiente(String aDesignacao, String aClassificacao, int aQuantidade) {
        Excipiente excipiente = new Excipiente(aDesignacao, aClassificacao, aQuantidade);
        
        return listaExcipientes.add(excipiente);
    }

    public boolean criarComponenteAtivo(String aDesignacao, String aClassificacao, int aQuantidade) {
        ComponenteAtivo componenteAtivo = new ComponenteAtivo(aDesignacao, aClassificacao, aQuantidade);
        
        return listaComponenteAtivos.add(componenteAtivo);
    }

    public ArrayList<Medicamentos> getMedicamentos() {
        return listaMedicamentos;
    }

    public ArrayList<ComponenteAtivo> getListaComponenteAtivos() {
        return listaComponenteAtivos;
    }

    public ArrayList<Excipiente> getListaExcipientes() {
        return listaExcipientes;
    }

    public HashMap<Integer, Categoria> getListaCategorias() {
        return listaCategorias;
    }
    public Categoria getCategoriaById(int id) {
        return listaCategorias.get(id);
    }
    public Excipiente getExcipienteById(int id) {
        return listaExcipientes.get(id);
    }
    public ComponenteAtivo getComponenteAtivoById(int id) {
        return listaComponenteAtivos.get(id);
    }

    public ArrayList<Medicamentos> getOrderedMedicamentos() {
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

    public Medicamentos getMedicamentoByNome(String aNome) {
        for (Medicamentos m : listaMedicamentos) {
            if (m.getNome().equals(aNome)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Medicamentos> getMedicamentosByCategoria(Categoria aCategoria) {
        ArrayList<Medicamentos> Resultados = new ArrayList<Medicamentos>();
        for (Medicamentos m : listaMedicamentos) {
            if (m.getCategorias().contains(aCategoria)) {
                Resultados.add(m);
            }
        }
        return Resultados;

    }

    public ArrayList<Medicamentos> getMedicamentosByComponenteAtiva(ComponenteAtivo aAtivo) {
        {
            ArrayList<Medicamentos> Resultados = new ArrayList<Medicamentos>();
            for (Medicamentos m : listaMedicamentos) {
                if (m.getComponenteAct().equals(aAtivo)) {
                    Resultados.add(m);
                }
            }
            return Resultados;

        }

    }

    public ArrayList<Medicamentos> getMedicamentosGenericos(boolean aGenerico) {
        ArrayList<Medicamentos> Resultados = new ArrayList<Medicamentos>();
        for (Medicamentos m : this.listaMedicamentos) {
            if (m.getGenerico() == aGenerico) {
                Resultados.add(m);
            }
        }
        return Resultados;
    }

    public ArrayList<Medicamentos> getMedicamentosGenericos(boolean aGenerico,
            ArrayList<Medicamentos> listadepesquisa) {
        ArrayList<Medicamentos> Resultados = new ArrayList<Medicamentos>();
        for (Medicamentos m : listadepesquisa) {
            if (m.getGenerico() == aGenerico) {
                Resultados.add(m);
            }
        }
        return Resultados;
    }

    public ArrayList<Medicamentos> getMedicamentosByStock(boolean aGenerico) {
        ArrayList<Medicamentos> Resultados = new ArrayList<Medicamentos>();
        for (Medicamentos m : this.listaMedicamentos) {
            if (m.getGenerico() == aGenerico) {
                Resultados.add(m);
            }
        }
        return Resultados;
    }

    public ArrayList<Medicamentos> getMedicamentosByStock(boolean aGenerico, ArrayList<Medicamentos> listadepesquisa) {
        ArrayList<Medicamentos> Resultados = new ArrayList<Medicamentos>();
        for (Medicamentos m : listadepesquisa) {
            if (m.getGenerico() == aGenerico) {
                Resultados.add(m);
            }
        }
        return Resultados;
    }
}
