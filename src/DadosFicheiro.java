
import java.io.Serializable;


public class DadosFicheiro implements Serializable {
    private GerirUser Users;
    private GerirEncomendas Encomendas;
    private GerirMedicamentos Medicamentos;

    public DadosFicheiro(GerirUser aUsers, GerirEncomendas aEncomendas,GerirMedicamentos aGerirMedicamentos) {
        this.Users = aUsers;
        this.Encomendas = aEncomendas;
        this.Medicamentos = aGerirMedicamentos;

        
    }

    public GerirEncomendas getEncomendas() {
        return Encomendas;
    }
    public GerirMedicamentos getMedicamentos() {
        return Medicamentos;
    }
    public GerirUser getUsers() {
        return Users;
    }


}