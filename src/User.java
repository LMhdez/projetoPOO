import java.util.List;

public class User {
  protected String login;
  protected String password;
  protected String nome;
  protected String email;
  protected boolean ativo;

  private List<String> listaLogins;
  private List<String> listaEmails;

  User(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo, boolean aUtilizadoresCriados) {

    if (!aUtilizadoresCriados) {
      this.login = aLogin;
      this.password = aPassword;
      this.nome = aNome;
      this.email = aEmail;
      this.ativo = aAtivo;

    } else if (aUtilizadoresCriados && !verifyEmail(aEmail) && !verifyLogin(aLogin)) {
      this.login = aLogin;
      this.password = aPassword;
      this.nome = aNome;
      this.email = aEmail;
      this.ativo = aAtivo;
    } else {
      throw new IllegalArgumentException("Email ou login inválido.");
    }
  }

  protected void setLogin(String aLogin) {
    this.login = aLogin;
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

  private boolean verifyEmail(String aEmail) {
    return listaEmails.contains(aEmail);
  }

  private boolean verifyLogin(String aLogin) {
    return listaLogins.contains(aLogin);
  }
}
