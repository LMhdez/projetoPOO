public class Gestor extends User {
    protected String login;
    protected String password;
    protected String nome;
    protected String email;
    protected boolean ativo;

    Gestor(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo) {
        super(aLogin, aPassword, aNome, aEmail, aAtivo);
        this.login = aLogin;
        this.password = aPassword;
        this.nome = aNome;
        this.email = aEmail;
        this.ativo = aAtivo;
    }
}
