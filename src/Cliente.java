public class Cliente extends User {
    private String NIF;
    private String Morada;
    private String contato;

    Cliente(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo, String aNIF, String aMorada,
            String aContato, boolean aUtilizadoresCriados) {
        super(aLogin, aPassword, aNome, aEmail, aAtivo,aUtilizadoresCriados );
        this.NIF = aNIF;
        this.Morada = aMorada;
        this.contato = aContato;

    }

}
