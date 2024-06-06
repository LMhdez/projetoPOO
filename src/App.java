
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class App {

    public static void main(String[] args) {

        InfoSistema infoSistema = loadInfoSistema();
        infoSistema.incrementarExecucoes();

        GerirUser gerirUser = new GerirUser();
        // gerirUser.setLista(dadosFicheiro.getUsers());
        GerirEncomendas gerirEncomendas = new GerirEncomendas();
        GerirMedicamentos gerirMedicamento = new GerirMedicamentos();
        DadosFicheiro dadosFicheiro = loadData();

        if (dadosFicheiro != null) {
            gerirUser = dadosFicheiro.getUsers();
            gerirEncomendas = dadosFicheiro.getEncomendas();
            gerirMedicamento = dadosFicheiro.getMedicamentos();
        }

        if (gerirUser.isEmpty()) {
            while (gerirUser.isEmpty()) {

                if (gerirUser.criarGestor(leDados("Introduza o seu username: "), leDados("Introduza a sua password: "),
                        leDados("Introduza o seu nome: "), leDados("Introduza o seu email: "), true)) {
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

                    User Userlogado = gerirUser.logar(leDados("Introduza o seu username: "),
                            leDados("Introduza a sua password: "));

                    if (Userlogado != null) {
                        int opLogado = 1;
                        System.out.println("Bem-vindo " + Userlogado.getLogin());
                        logAction(Userlogado.login, "Fez login");
                        infoSistema.setUltimoUsuario(Userlogado.getLogin());
                        saveInfoSistema(infoSistema);
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

                    switch (tipo) {
                        case 1:
                            if (gerirUser.criarFarmaceutico(leDados("Introduza o seu username: "),
                                    leDados("Introduza a sua password: "), leDados("Introduza o seu nome: "),
                                    leDados("Introduza o seu email: "), false, leDados("Introduza o seu NIF: "),
                                    leDados("Introduza a sua morada: "),
                                    leDados("Introduza o seu contato: "))) {
                                System.out.println("Farmaceutico criado com sucesso!");
                            } else {
                                System.out.println(
                                        "O login/Username/email/NIF/Contato já estão associados a outra conta!");
                            }
                            break;
                        case 2:
                            if (gerirUser.criarCliente(leDados("Introduza o seu username: "),
                                    leDados("Introduza a sua password: "), leDados("Introduza o seu nome: "),
                                    leDados("Introduza o seu email: "), false, leDados("Introduza o seu NIF: "),
                                    leDados("Introduza a sua morada: "),
                                    leDados("Introduza o seu contato: "))) {
                                System.out.println("Cliente criado com sucesso!");
                            } else {
                                System.out.println(
                                        "O login/Username/email/NIF/Contato já estão associados a outra conta!");
                            }
                            break;
                        case 3:

                            if (gerirUser.criarGestor(leDados("Introduza o seu username: "),
                                    leDados("Introduza a sua password: "), leDados("Introduza o seu nome: "),
                                    leDados("Introduza o seu email: "), false)) {
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

                    saveData(gerirUser, gerirEncomendas, gerirMedicamento);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }

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

    private static void saveData(GerirUser aGerirUser, GerirEncomendas aGerirEncomendas,
            GerirMedicamentos aGerirMedicamentos) {
        try {
            DadosFicheiro dados = new DadosFicheiro(aGerirUser, aGerirEncomendas, aGerirMedicamentos);
            if (dados.getEncomendas() != null || dados.getUsers() != null || !dados.getUsers().isEmpty()) {
                File file = new File("dados_apl.dat");
                if (!file.exists()) {
                    // Se o ficheiro não existe, cria e retorna null
                    try {
                        file.createNewFile();
                        System.out.println("ficheiro dados_apl.dat criado com sucesso.");

                    } catch (IOException e) {
                        System.err.println("Erro ao criar o ficheiro: " + e.getMessage());
                        e.printStackTrace();

                    }
                }

                FileOutputStream ficheiro = new FileOutputStream("dados_apl.dat");
                BufferedOutputStream bos = new BufferedOutputStream(ficheiro);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(dados);
                oos.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DadosFicheiro loadData() {
        File file = new File("dados_apl.dat");
        // Carregar os dados do ficheiro
        try {
            FileInputStream ficheiro = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(ficheiro);
            ObjectInputStream ois = new ObjectInputStream(bis);
            DadosFicheiro dados = (DadosFicheiro) ois.readObject();
            ois.close();
            System.out.println("Dados carregados com sucesso de dados_apl.dat.");
            return dados;
        } catch (Exception e) {

            return null;
        }
    }

    private static void logAction(String aUser, String aAcao) {
        File logFile = new File("log.txt");
        try {
            File tempFile = new File("temp_log.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
            writer.println(aUser + " " + aAcao);

            if (logFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(logFile));
                String linha;
                while ((linha = reader.readLine()) != null) {
                    writer.println(linha);
                }
                reader.close();
            }
            writer.close();

            logFile.delete();
            tempFile.renameTo(logFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static InfoSistema loadInfoSistema() {
        File file = new File("info_sistema.dat");
        if (!file.exists()) {
            try {
                file.createNewFile();
                return new InfoSistema();
            } catch (IOException e) {
                e.printStackTrace();
                return new InfoSistema();
            }
        }

        try {
            FileInputStream fileIn = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fileIn);
            ObjectInputStream ois = new ObjectInputStream(bis);
            InfoSistema info = (InfoSistema) ois.readObject();
            ois.close();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return new InfoSistema();
        }
    }

    private static void saveInfoSistema(InfoSistema info) {
        try {
            FileOutputStream fileOut = new FileOutputStream("info_sistema.dat");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(info);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
