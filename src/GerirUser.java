import java.util.ArrayList;

public class GerirUser {

    private ArrayList<User> lista;

    public GerirUser() {
        lista = new ArrayList<User>();
    }

    public boolean adicionarUser(User aUser) {
        if (lista != null && !verificaEmail(aUser.getEmail()) && !verificaLogin(aUser.getLogin())) {
            return lista.add(aUser);
        }
        return false;
    }

    public User logar(String aUser, String aPassword) {

        for (User u : lista) {
            if (u.getLogin().equals(aUser) && u.getPassword().equals(aPassword) && u.getativo()) {
                return u;
            }
        }
        return null;

    }

    public boolean criarGestor(String login, String password, String nome, String email, boolean aAtivo) {
        Gestor gestor = new Gestor(login, password, nome, email, aAtivo);
        return adicionarUser(gestor);
    }

    public boolean criarFarmaceutico(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo,
            String aNIF, String aMorada,
            String aContato) {

        Farmaceutico farmaceutico = new Farmaceutico(aLogin, aPassword, aNome, aEmail, aAtivo, aNIF, aMorada, aContato);
        if (!verificaNIF(aNIF) && !verificaContato(aContato)) {
            return adicionarUser(farmaceutico);
        } else
            return false;

    }

    public boolean criarCliente(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo,
            String aNIF, String aMorada,
            String aContato) {
        Cliente cliente = new Cliente(aLogin, aPassword, aNome, aEmail, aAtivo, aNIF, aMorada, aContato);
        if (!verificaNIF(aNIF) && !verificaContato(aContato)) {
            return adicionarUser(cliente);
        } else
            return false;
    }

    public boolean verificaLogin(String aLogin) {
        for (User u : lista) {
            if (u.getLogin().equals(aLogin)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificaNIF(String aNIF) {
        for (User u : lista) {
            if (u instanceof Cliente) {
                Cliente cliente = (Cliente) u; // Cast para Cliente
                if (cliente.getNIF().equals(aNIF)) {
                    return true;
                } else if (u instanceof Farmaceutico) {
                    Farmaceutico farmaceutico = (Farmaceutico) u; // Cast para farmaceutico
                    if (farmaceutico.getNIF().equals(aNIF)) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean verificaContato(String aContato) {
        for (User u : lista) {
            if (u instanceof Cliente) {
                Cliente cliente = (Cliente) u; // Cast para Cliente
                if (cliente.getContato().equals(aContato)) {
                    return true;
                } else if (u instanceof Farmaceutico) {
                    Farmaceutico farmaceutico = (Farmaceutico) u; // Cast para farmaceutico
                    if (farmaceutico.getContato().equals(aContato)) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean verificaEmail(String aEmail) {
        for (User u : lista) {
            if (u.getEmail().equals(aEmail)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<User> Pedidosporaprovar = new ArrayList<User>();

    public ArrayList<String> GetPedidosdeRegisto() {
        ArrayList<String> Pedidos = new ArrayList<String>();
        int i = 0;
        for (User u : lista) {
            if (!u.getativo()) {
                Pedidos.add((char) i + "-" + u.getLogin() + " - " + u.getClass().getName());
                i++;
                Pedidosporaprovar.add(u);
            }
        }
        return Pedidos;

    }

    public User getUserByIndex(int i) {
        return Pedidosporaprovar.get(i);

    }

    public boolean setAtivo(User aUser) {
        return aUser.setAtivo();
    }

}
