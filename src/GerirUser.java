import java.util.ArrayList;
import java.util.HashMap;


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
    public ArrayList<User> getOrderedUsers(){
        ArrayList<User> users = new ArrayList<User>();
        for (User u : lista) {
            users.add(u);
        }
        users.sort(null);
        return users;
    }


    public boolean isEmpty() {
        return lista.isEmpty();
    }

    public User logar(String aUser, String aPassword) {

        for (User u : lista) {
            if (u.getLogin().equals(aUser) && u.getPassword().equals(aPassword) && u.getAtivo()) {
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

    private HashMap<Integer, User> PedidosPorAprovar = new HashMap<Integer, User>(); // Guarda indexes e users

    public HashMap<Integer, User> GetPedidosdeRegisto() {

        int i = 0;
        for (User u : lista) {
            if (!u.getAtivo()) {
                i++;

                PedidosPorAprovar.put(i, u); // Guarda index e seu user no hashmap
            }
        }
        return PedidosPorAprovar;
    }

    public boolean ativarUser(int i) {
        User u = PedidosPorAprovar.get(i); // Procurar user pelo index no hash map
        if (u != null && !u.getAtivo()) {
            u.setAtivo(); // Activar user
            PedidosPorAprovar.remove(i); // Remover o registo do hashmap
            return true; // sucesso
        }
        return false; // nao encontrado ou já estava ativo
    }

}