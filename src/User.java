public class User{
  protected String login;
  protected String password;
  protected String nome;
  protected String email;
  protected boolean ativo;

  User(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo){
    this.login = aLogin;
    this.password = aPassword;
    this.nome = aNome;
    this.email = aEmail;
    this.ativo = aAtivo;
  }
}








