import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Encomendas implements Serializable {
    private int id;
    private Farmaceutico farmaceutico;
    private ArrayList<Object> lista;
    private float total;
    private HashMap<Farmaceutico, String> subtarefas;
    private String data;
    private float horasGastas;
    private String descricao;
    private boolean urgente;
    private int status; // vai variar de 1 a 5
    private int controloencomendas;

    // Adicionado um formatador para a data
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public String toString() {
        String result = "ID: " + id +
                " Farmaceutico: " + farmaceutico +
                " Medicamentos: " + lista +
                " Total: " + total +
                " Data: " + data +
                " Horas Gastas: " + horasGastas +
                " Descricao: " + descricao +
                " Urgencia: " + urgente +
                " Status: " + status;

        if (subtarefas != null && !subtarefas.isEmpty()) {
            result += " Subtarefas:\n ";
            for (Map.Entry<Farmaceutico, String> entry : subtarefas.entrySet()) {
                result += "Farmaceutico: " + entry.getKey() + ", Tarefa: " + entry.getValue() + "\n";
            }
        }

        return result;
    }

    Encomendas(ArrayList<Object> aLista, String aDescricao, boolean aUrgente) {
        this.id = controloencomendas++;
        this.lista = aLista;
        this.urgente = aUrgente;
        this.descricao= aDescricao;
        this.status = 1;
        this.data = LocalDate.now().format(formatter); // Adiciona a data atual formatada
    }

    public boolean setFarmaceutico(Farmaceutico aFarmaceutico) {
        if (status > 1 && aFarmaceutico != null) {
            this.farmaceutico = aFarmaceutico;
            return true;
        } else {
            return false;
        }
    }

    public Farmaceutico getFarmaceutico() {
        return farmaceutico;
    }

    public ArrayList<Object> getLista() {
        return lista;
    }
    public void setLista(ArrayList<Object> lista) {

        this.lista = lista;
        

    }

    public boolean setEstatus(int aEstatus) {
        if (aEstatus > 0 && aEstatus < 5) {
            this.status = aEstatus;
            return true;
        } else {
            return false;
        }
    }

    public int getEstatus() {
        return this.status;
    }

    public int getId() {
        return id;
    }

    public float getHorasGastas() {
        return horasGastas;
    }
    public void setHorasGastas(float horasGastas) {
        this.horasGastas = horasGastas;
    }

    public boolean criarSubTarefa(Farmaceutico aFarmaceutico, String subTarefa) {
        if (status > 1 && aFarmaceutico != null) {
            subtarefas.put(aFarmaceutico, subTarefa);
            return true;
        } else {
            return false;
        }
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
