public class Farmaceutico extends User {
    private String NIF;
    private String morada;
    private String contato;

    Farmaceutico(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo, String aNIF,
            String aMorada,
            String aContato) {
        super(aLogin, aPassword, aNome, aEmail, aAtivo);
        this.NIF = aNIF;
        this.morada = aMorada;
        this.contato = aContato;

    }

    public String getNIF() {
        return NIF;
    }

    public String getContato() {
        return contato;
    }

    // Os farmacêuticos introduzem medicamentos, categorias e excipientes e gerem os
    // serviços.
    public Medicamentos CriarMedicamento(String aMarca, String aLote, String aComponenteAct, String aDosagem, int aStock, float aPreco, int aAnoFabrico, boolean aMedicoNecessario, boolean aGenerico) {
    Medicamentos medicamento= new Medicamentos( aMarca, aLote, aComponenteAct, aDosagem, aStock, aPreco, aAnoFabrico, aMedicoNecessario, aGenerico);
    return medicamento;
    }
    /*public Categorias CriarCategoria() {
    
    return ;
    }
    public Excipientes CriarExcipiente() {
    
    return ;
    }*/
    


}
