import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        String login;
        String password;
        String nome;
        String email;
        boolean ativo;
        String NIF = null;
        String morada = null;
        String contato = null;

        GerirUser gerirUser = new GerirUser();
        GerirEncomendas gerirEncomendas = new GerirEncomendas();
        try {
            FileInputStream fileInputStream = new FileInputStream("dados_apl.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<User> userList = (ArrayList<User>) objectInputStream.readObject();
            gerirUser.setLista(userList); // Set the loaded list to your GerirUser object
            objectInputStream.close();
            System.out.println("Arquivo dados_apl.dat lido com sucesso.");
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, create a new empty file
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("dados_apl.dat"));
                outputStream.close();
                System.out.println("Arquivo dados_apl.dat criado com sucesso.");
            } catch (IOException ex) {
                System.out.println("Erro ao criar o arquivo dados_apl.dat: " + ex.getMessage());
            }
        } catch (IOException e) {
            // Handle IO exception
            System.out.println("Erro ao ler o arquivo dados_apl.dat: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Handle class not found exception
            System.out.println("Classe não encontrada ao ler o arquivo dados_apl.dat: " + e.getMessage());
        }
        
        

        // gerirUser.criarGestor("sim", "123", "manito", "fghj", true);
        // User Userlogado = gerirUser.logar("sim", "123");

        if (gerirUser.isEmpty()) {
            while (gerirUser.isEmpty()) {
                login = leDados("Introduza o seu username: ");
                password = leDados("Introduza a sua password: ");
                nome = leDados("Introduza o seu nome: ");
                email = leDados("Introduza o seu email: ");
                ativo = true;

                if (gerirUser.criarGestor(login, password, nome, email, ativo)) {
                    System.out.println("Gestor criado com sucesso!");

                } else {
                    System.out.println("Gestor nao criado!");
                }
            }
        }

        String menu = "1-Login\n2-Criar Conta\n0-Sair";
        int op = 1;
        while (op > 0) {
            op = leDadosInt(menu);
            switch (op) {
                case 1:
                    login = leDados("Introduza o seu username: ");
                    password = leDados("Introduza a sua password: ");

                    User Userlogado = gerirUser.logar(login, password);

                    if (Userlogado != null) {
                        int opLogado = 1;
                        System.out.println("Bem-vindo " + login);
                        if (Userlogado instanceof Gestor) {
                            System.out.println("gestor");
                            while (opLogado != 0) {
                                opLogado = leDadosInt(
                                        "1- Gerir Registos \n2- Gerir Pedidos de Servicos\n3- Historico de Servicos\n0-Encerrar sessao");
                                switch (opLogado) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());
                                        break;
                                    case 1:

                                        HashMap<Integer, User> pedidos = gerirUser.GetPedidosdeRegisto();

                                        // Print HashMap
                                        for (HashMap.Entry<Integer, User> entry : pedidos.entrySet()) {
                                            int i = entry.getKey();
                                            User user = entry.getValue();
                                            System.out.println(i + "- " + "Nome: " + user.getNome()
                                                    + ", Tipo de conta: " + user.getClass().getName());
                                        }
                                        int pedido = leDadosInt("Indique o Pedido que deseja aprovar: ");
                                        if (pedido > gerirUser.GetPedidosdeRegisto().size() || pedido < 1) {
                                            System.out.println("Pedido inexistente");
                                        } else {
                                            gerirUser.ativarUser(pedido);
                                        }
                                        break;
                                    case 2:

                                        // Handle "Gerir encomendas"
                                        HashMap<Cliente, ArrayList<Encomendas>> encomendas = gerirEncomendas
                                                .getEncomendas();

                                        // Print HashMap
                                        for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendas
                                                .entrySet()) {
                                            Cliente cliente = entry.getKey();
                                            ArrayList<Encomendas> listaEncomendas = entry.getValue();
                                            System.out.println(cliente.getNome() + ": " + listaEncomendas);
                                        }

                                        break;
                                    case 3:
                                        // Handle "Historico de Servicos"
                                        break;
                                    default:
                                        System.out.println("Opção inválida");
                                        break;
                                }
                            }
                        }

                        if (Userlogado instanceof Cliente) {
                            System.out.println("cliente");
                            while (opLogado > 0) {
                                opLogado = leDadosInt("1- Solicitar pedido de Servico \n0-Encerrar sessao");
                                switch (opLogado) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());
                                        break;
                                    case 1:
                                        boolean continuar = true;
                                        HashMap<String, Integer> medicamentos = new HashMap<String, Integer>();
                                        // isto tem que ser mudados depois quando o projeto tiver mais avançado

                                        while (continuar) {
                                            String nomeMedicamento = leDados("Indique o nome do medicamento");
                                            int quantidade = leDadosInt("Indique a quantidade do medicamento");
                                            continuar = leDadosInt(
                                                    "Deseja adicionar outro medicamento?\n1-Sim\n2-Nao") == 1;
                                            medicamentos.put(nomeMedicamento, quantidade);
                                        }
                                        boolean urgente = leDadosInt("A sua encomenda é urgente?\n1-Sim\n2-Não") == 1;
                                        Encomendas Encomenda = null;
                                        Encomenda = new Encomendas(null, urgente);
                                        gerirEncomendas.adicionarEncomenda((Cliente) Userlogado, Encomenda);
                                        break;
                                    default:
                                        System.out.println("Opção inválida");
                                        break;
                                }
                            }
                        }
                        if (Userlogado instanceof Farmaceutico) {
                            while (opLogado > 0) {
                                opLogado = leDadosInt("1-Consultar os Pedidos\n0-Encerrar sessao");

                                switch (opLogado) {
                                    case 1:
                                        ArrayList<Encomendas> listaEncomendas = gerirEncomendas
                                                .getEncomendasByFarmaceutico((Farmaceutico) Userlogado);

                                        for (Encomendas encomendas : listaEncomendas) {
                                            System.out.println("Encomenda" + ": " + encomendas.getMedicamentos());
                                        }

                                        break;
                                    case 2:

                                        break;
                                    case 3:

                                        break;

                                    default:
                                        break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Login ou password errados!");
                    }
                    break;
                case 2:
                    int tipo = leDadosInt(
                            "Qual é o tipo de conta que gostaria de criar?\n1-Farmaceutico\n2-Cliente\n3-Gestor");
                    login = leDados("Introduza o seu username: ");
                    password = leDados("Introduza a sua password: ");
                    nome = leDados("Introduza o seu nome: ");
                    email = leDados("Introduza o seu email: ");
                    ativo = false;
                    if (tipo == 1 || tipo == 2) {
                        NIF = leDados("Introduza o seu NIF: ");
                        morada = leDados("Introduza a sua morada: ");
                        contato = leDados("Introduza o seu contato: ");
                    }
                    switch (tipo) {
                        case 1:
                            if (gerirUser.criarFarmaceutico(login, password, nome, email, ativo, NIF, morada,
                                    contato)) {
                                System.out.println("Farmaceutico criado com sucesso!");
                            } else {
                                System.out.println(
                                        "O login/Username/email/NIF/Contato já estão associados a outra conta!");
                            }
                            break;
                        case 2:
                            if (gerirUser.criarCliente(login, password, nome, email, ativo, NIF, morada, contato)) {
                                System.out.println("Cliente criado com sucesso!");
                            } else {
                                System.out.println(
                                        "O login/Username/email/NIF/Contato já estão associados a outra conta!");
                            }
                            break;
                        case 3:
                            if (gerirUser.criarGestor(login, password, nome, email, ativo)) {
                                System.out.println("Gestor criado com sucesso!");
                            } else {
                                System.out.println("O login/Username/email já estão associados a outra conta!");
                            }
                            break;
                        default:
                            System.out.println("Tipo de Conta inválida!");
                            break;
                    }
                    break;
                case 0:
                    System.out.println("Adeus");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Salvar dados em dados_apl.dat
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("dados_apl.dat"));
                outputStream.writeObject(gerirUser.getUsers()); // Lista de usuários
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private static String leDados(String aMensagem) {
        while (true) {
            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                return teclado.nextLine();
            } catch (Exception e) {
                System.out.println("Entrada invalida!");
            }
        }

    }

    private static int leDadosInt(String aMensagem) {
        while (true) {
            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                return teclado.nextInt();

            } catch (Exception e) {
                System.out.println("Entrada invalida!");

            }
        }

    }
}
