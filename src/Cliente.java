

public class Cliente extends User {
    private String NIF;
    private String morada;
    private String contato;

    public String toString() {
        return "login: " + login + " password: " + password + " nome: " + nome + " email: " + email + " ativo: " + ativo
                + " NIF: " + NIF + " morada: " + morada + " contato: " + contato;
    }

    Cliente(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo, String aNIF, String aMorada,
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

}
