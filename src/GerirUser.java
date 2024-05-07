import java.util.ArrayList;

public class GerirUser {

  private ArrayList<User> lista;

  public GerirUser() {
    lista = new ArrayList<User>();
  }

  public boolean adicionarUser(User aUser) {
    if (lista != null)
      return lista.add(aUser);
    return false;
  }

  public boolean criarGestor(String login, String password, String nome, String email, boolean aAtivo) {
    Gestor gestor = new Gestor(login, password, nome, email, aAtivo);
    return adicionarUser(gestor);
  }

  public boolean criarFarmaceutico(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo,
      String aNIF, String aMorada,
      String aContato) {

    Farmaceutico farmaceutico = new Farmaceutico(aLogin, aPassword, aNome, aEmail, aAtivo, aNIF, aMorada, aContato);
    return adicionarUser(farmaceutico);
  }

  public boolean criarCliente(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo,
      String aNIF, String aMorada,
      String aContato) {
    Cliente cliente = new Cliente(aLogin, aPassword, aNome, aEmail, aAtivo, aNIF, aMorada, aContato);
    return adicionarUser(cliente);
  }

}
