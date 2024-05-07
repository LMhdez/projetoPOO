
public class User {
  protected String login;
  protected String password;
  protected String nome;
  protected String email;
  protected boolean ativo;

  User(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo) {

    this.login = aLogin;
    this.password = aPassword;
    this.nome = aNome;
    this.email = aEmail;
    this.ativo = aAtivo;

  }

  protected String getLogin() {
    return login;
  }

  protected String getEmail() {
    return email;
  }

  protected void setLogin(String aLogin) {
    this.login = aLogin;
  }
  
  protected String getPassword(){

    return password;
  }

  protected void setPassword(String aPassword) {
    this.password = aPassword;
  }

  protected void setNome(String aNome) {
    this.nome = aNome;
  }

  protected void setEmail(String aEmail) {
    this.email = aEmail;
  }

  protected String getNome() {
    return nome;
  }

}
