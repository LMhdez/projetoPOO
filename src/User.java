import java.io.Serializable;
public class User implements Comparable<User>, Serializable{
  protected String login;
  protected String password;
  protected String nome;
  protected String email;
  protected boolean ativo;

  public int compareTo(User u) {
    return this.nome.toLowerCase().compareTo(u.getNome().toLowerCase());
  }

  public String toString() {
    return "login: " + login + " password: " + password + " nome: " + nome + " email: " + email + " ativo: " + ativo;
  }

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

  protected String getPassword() {

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

  protected boolean getAtivo() {
    return ativo;
  }

  protected boolean setAtivo() {
    return ativo = true;
  }

}
