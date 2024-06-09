import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.*;

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
        // Verificação original do NIF
        for (User u : lista) {
            if (u instanceof Cliente) {
                Cliente cliente = (Cliente) u;
                if (cliente.getNIF().equals(aNIF)) {
                    return true; // Retornar true se o NIF já existir
                }
            } else if (u instanceof Farmaceutico) {
                Farmaceutico farmaceutico = (Farmaceutico) u;
                if (farmaceutico.getNIF().equals(aNIF)) {
                    return true; // Retornar true se o NIF já existir
                }
            }
        }

        // Regex check
        String nifRegex = "^\\d{9}$";
        Pattern pattern = Pattern.compile(nifRegex);
        Matcher matcher = pattern.matcher(aNIF);
        return !matcher.matches(); // Retorna true se o NIF for inválido ou já existir
    }

    public boolean verificaContato(String aContato) {
        // Verificação original do número de telefone
        for (User u : lista) {
            if (u instanceof Cliente) {
                Cliente cliente = (Cliente) u;
                if (cliente.getContato().equals(aContato)) {
                    return true; // Retornar true se o contato já existir
                }
            } else if (u instanceof Farmaceutico) {
                Farmaceutico farmaceutico = (Farmaceutico) u;
                if (farmaceutico.getContato().equals(aContato)) {
                    return true; // Retornar true se o contato já existir
                }
            }
        }

        // Regex check
        // // ^: Indica o início da string.
        // \d{9}: Corresponde a exatamente 9 dígitos numéricos.
        // $: Indica o final da string.
        String phoneRegex = "^\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(aContato);
        return !matcher.matches(); // Retorna true se o número de telefone for inválido ou já existir
    }

    public boolean verificaEmail(String aEmail) {
        // Verificação original do email
        for (User u : lista) {
            if (u.getEmail().equals(aEmail)) {
                return true; // Retornar true se o email já existir
            }
        }

        // Regex check
        // ^: Indica o início da string.
        // [a-zA-Z0-9_+&*-]+: Corresponde a um ou mais caracteres alfanuméricos,
        // incluindo alguns símbolos especiais comuns em endereços de e-mail.
        // (?:\.[a-zA-Z0-9_+&*-]+)*: Um grupo não capturador que corresponde a um ponto
        // seguido por caracteres alfanuméricos, repetido zero ou mais vezes.
        // @: Corresponde ao caractere '@', que separa o nome do usuário do domínio no
        // endereço de e-mail.
        // (?:[a-zA-Z0-9-]+\.)+: Um grupo não capturador que corresponde a um ou mais
        // subdomínios separados por pontos.
        // [a-zA-Z]{2,7}: Corresponde a um domínio, com 2 a 7 caracteres alfabéticos,
        // representando a extensão do domínio, como ".com" ou ".org".
        // $: Indica o final da string.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(aEmail);
        return !matcher.matches(); // Retorna true se o email for inválido ou já existir
    }

    public ArrayList<User> GetPedidosdeRegisto() {

        ArrayList<User> PedidosPorAprovar = new ArrayList<User>();
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

    public ArrayList<User> getOrderedUsers(ArrayList<User> aUsers) {
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
