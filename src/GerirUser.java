import java.util.ArrayList;

public class GerirUser {

  private ArrayList<User> lista;

  public GerirUser() {
    lista = new ArrayList<User>();
  }

  public boolean adicionaUser(User aUser) {
    if (lista != null)
      return lista.add(aUser);
    return false;
  }

  public boolean criaGestor(String login, String password, String nome, String email, boolean aAtivo) {
    Gestor gestor = new Gestor(login, password, nome, email, aAtivo);
    return adicionaUser(gestor);
  }

  public boolean criaFarmaceutico(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo,
      String aNIF, String aMorada,
      String aContato) {

    Farmaceutico farmaceutico = new Farmaceutico(aLogin, aPassword, aNome, aEmail, aAtivo, aNIF, aMorada, aContato);
    return adicionaUser(farmaceutico);
  }

}
