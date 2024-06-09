import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GerirUser implements Serializable {

    private ArrayList<User> lista;

    public void setLista(ArrayList<User> lista) {
        this.lista = lista;
    }

    public GerirUser() {
        lista = new ArrayList<User>();
    }

    public boolean adicionarUser(User aUser) {
        if (lista != null && !verificaEmail(aUser.getEmail()) && !verificaLogin(aUser.getLogin())) {
            boolean sucesso = lista.add(aUser);
            if (sucesso) {
                // Escrever credenciais em credenciais_acesso.txt
                try {
                    FileWriter writer = new FileWriter("credenciais_acesso.txt", true);
                    writer.write(aUser.getLogin() + " " + aUser.getPassword() + "\n");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sucesso;
        }
        return false;
    }

   

   
        


    public ArrayList<User> getUsers() {
        return lista;
    }

    public ArrayList<User> getUsersByClassname(String classname) {
        ArrayList<User> users = new ArrayList<User>();
        for (User u : lista) {
            if (u.getClass().getName().equals(classname)) {
                users.add(u);
            }
        }
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

    public ArrayList<User> GetPedidosdeRegisto() {

        ArrayList<User>PedidosPorAprovar= new ArrayList<User>();
        for (User u : lista) {
            if (!u.getAtivo()) {
               

                PedidosPorAprovar.add(u); // Guarda index e seu user no hashmap
            }
        }
        return PedidosPorAprovar;
    }

 

    public ArrayList<User> getUserByUsername(String aUsername) {
        ArrayList<User> resultados = new ArrayList<User>();
        for (User u : lista) {
            if (u.getNome().contains(aUsername)) {
                resultados.add(u);
            }
        }
        return resultados;
    }
    

    public ArrayList<User> getUserByNome(String aNome) {
        ArrayList<User> resultados = new ArrayList<User>();
        for (User u : lista) {
            if (u.getNome().contains(aNome)) {
                resultados.add(u);
            }
        }
        return resultados;
    }
    
    public ArrayList<User>getOrderedUsers(ArrayList<User> aUsers){
        ArrayList<User> users = new ArrayList<User>();
        for (User u : aUsers) {
            users.add(u);
        }
        users.sort(null);
        return users;

    }

    public ArrayList<User> getUserByNome(String aNome, String aClassname) {

        ArrayList<User> Resultados = new ArrayList<User>();
        for (User u : lista) {
            if (u.getNome().contains(aNome) && u.getClass().getName().equals(aClassname)) {
                Resultados.add(u);
            }
        }
        return Resultados;
    }

}
