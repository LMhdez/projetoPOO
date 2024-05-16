public class Gestor extends User {

    public Gestor(String login, String password, String nome, String email, boolean ativo) {
        super(login, password, nome, email, ativo);
    }

    public boolean aprovarEncomenda(Encomendas aEncomenda){
        return aEncomenda.setAprovado();
    }
    public boolean encerrarEncomenda(Encomendas aEncomenda){ 
        return aEncomenda.setEncerrado();
    }
    public boolean AssociarFarmaceutico(Encomendas aEnconmenda, Farmaceutico aFarmaceutico){ 
        return aEncomenda.setFarmaceutico(aFarmaceutico);
    }
}
