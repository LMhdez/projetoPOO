public class Cliente extends User {
    private String NIF;
    private String Morada;
    private String contato;

    Cliente(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo, String aNIF, String aMorada,
            String aContato) {
        super(aLogin, aPassword, aNome, aEmail, aAtivo);
        this.NIF = aNIF;
        this.Morada = aMorada;
        this.contato = aContato;

    }

}
