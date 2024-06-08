
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
                                        "1- Gerir Registos \n2- Gerir Pedidos de Servicos\n3- Historico de Servicos\n4-Alterar dados da conta\n0-Encerrar sessao");
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
                                        opLogado = leDadosInt("1-Aprovar encomendas\n2-Encerrar encomendas");
                                        HashMap<Cliente, ArrayList<Encomendas>> Encomendas;
                                        int opEncomenda;
                                        switch (opLogado) {
                                            case 1:
                                                Encomendas = gerirEncomendas
                                                        .getEncomendasByStatus(1);

                                                // Print HashMap
                                                for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : Encomendas
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    ArrayList<Encomendas> encomendas = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    for (Encomendas encomenda : encomendas) {
                                                        System.out.println("Encomenda: " + encomenda);
                                                    }
                                                }
                                                opEncomenda = leDadosInt(
                                                        "Indique o ID da encomenda que deseja aprovar: ");
                                                if (opEncomenda > Encomendas.size() || opEncomenda < 1) {
                                                    System.out.println("Encomenda inexistente");
                                                } else {
                                                    gerirEncomendas
                                                            .aprovarEncomenda(
                                                                    gerirEncomendas.getEncomendaById(opEncomenda));
                                                }
                                                break;

                                            case 2:

                                                Encomendas = gerirEncomendas
                                                        .getEncomendasByStatus(4);

                                                for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : Encomendas
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    ArrayList<Encomendas> encomendas = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    for (Encomendas encomenda : encomendas) {
                                                        System.out.println("Encomenda: " + encomenda);
                                                    }
                                                }

                                                opEncomenda = leDadosInt(
                                                        "Indique o ID da encomenda que deseja encerrar: ");
                                                if (opEncomenda > Encomendas.size() || opEncomenda < 1) {
                                                    System.out.println("Encomenda inexistente");
                                                } else {
                                                    gerirEncomendas
                                                            .aprovarEncomenda(
                                                                    gerirEncomendas.getEncomendaById(opEncomenda));
                                                }
                                                break;

                                            default:
                                                break;
                                        }

                                    case 3:

                                        break;

                                    case 4:
                                        if (AlterarDadosUser(Userlogado)) {
                                            System.out.println("Dados alterados com sucesso");
                                        } else {
                                            System.out.println("Falha na alteração dos dados");

                                        }

                                }

                            }

                            if (Userlogado instanceof Cliente) {
                                System.out.println("cliente");
                                while (opLogado > 0) {
                                    opLogado = leDadosInt(
                                            "1- Solicitar pedido de Servico\n2-Alterar dados da Conta\n0-Encerrar sessao");
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
                                            boolean urgente = leDadosInt(
                                                    "A sua encomenda é urgente?\n1-Sim\n2-Não") == 1;
                                            Encomendas Encomenda = null;
                                            Encomenda = new Encomendas(null, urgente);
                                            gerirEncomendas.adicionarEncomenda((Cliente) Userlogado, Encomenda);
                                            break;
                                        case 2:
                                            if (AlterarDadosUser(Userlogado)) {
                                                System.out.println("Dados alterados com sucesso");
                                            } else {
                                                System.out.println("Falha na alteração dos dados");

                                            }
                                            break;
                                        default:
                                            System.out.println("Opção inválida");
                                            break;
                                    }
                                }
                            }
                            if (Userlogado instanceof Farmaceutico) {
                                while (opLogado > 0) {
                                    opLogado = leDadosInt(
                                            "1-Consultar encomendas\n2-Alterar dados da Conta\n3-Introduzir medicamento\n4-Introduzir categoria\n5-Introduzir excipiente\n6-Introduzir componente ativa\n7-Modificar stock de medicamento existente\n\n0-Encerrar sessao");

                                    switch (opLogado) {
                                        case 1:
                                            ArrayList<Encomendas> listaEncomendas = gerirEncomendas
                                                    .getEncomendasByFarmaceutico((Farmaceutico) Userlogado);

                                            for (Encomendas encomendas : listaEncomendas) {
                                                System.out.println("Encomenda" + ": " + encomendas.getMedicamentos());
                                            }

                                            break;
                                        case 2:
                                            if (AlterarDadosUser(Userlogado)) {
                                                System.out.println("Dados alterados com sucesso");
                                            } else {
                                                System.out.println("Falha na alteração dos dados");

                                            }

                                            break;
                                        case 3:
                                            String marca = leDados("Introduza a marca do medicamento: ");
                                            String lote = leDados("Introduza o lote do medicamento: ");
                                            String dosagem = leDados("Introduza a dosagem do medicamento: ");
                                            int stock = leDadosInt("Introduza o stock do medicamento: ");
                                            float preco = leDadosFloat("Introduza o preco do medicamento: ");
                                            int anoFabrico = leDadosInt("Introduza o ano de fabrico do medicamento: ");
                                            boolean medicoNecessario = leDadosInt(
                                                    "Introduza se o medicamento necessita de medico?\n1-Sim\n2-Não") == 1;

                                            boolean generico = leDadosInt("generico? 1-Sim\n2-Não") == 1;

                                            HashMap<Integer, Categoria> categorias = gerirMedicamento
                                                    .getListaCategorias();
                                            // print hashmap
                                            int n;
                                            do {
                                                n = leDadosInt(
                                                        "Introduza o numero de categorias do medicamento(max:3)");
                                                if (n > 2 || n < 1) {
                                                    System.out.println("Entrada invalida");

                                                }

                                            } while (n > 2 || n < 1);
                                            for (HashMap.Entry<Integer, Categoria> entry : categorias.entrySet()) {
                                                Integer key = entry.getKey();
                                                Categoria categoria = entry.getValue();
                                                System.err.println(key);
                                                System.out.println(categoria);
                                            }

                                            ArrayList<Categoria> categoriasMedicamento = new ArrayList<Categoria>();

                                            for (int i = 0; i < n; i++) {
                                                categoriasMedicamento.add(gerirMedicamento.getCategoriaById(
                                                        leDadosInt("Introduza o codigo da categoria: ")));
                                            }

                                            do {
                                                n = leDadosInt(
                                                        "Introduza o numero de excipientes do medicamento(max:5)");
                                                if (n > 5 || n < 1) {
                                                    System.out.println("Entrada invalida");

                                                }

                                            } while (n > 5 || n < 1);

                                            ArrayList<Excipiente> excipientes = gerirMedicamento.getListaExcipientes();
                                            for (Excipiente excipiente : excipientes) {
                                                System.out.println(excipientes.indexOf(excipiente));
                                                System.out.println(excipiente);
                                            }

                                            ArrayList<Excipiente> excipienteMedicamento = new ArrayList<Excipiente>();

                                            for (int i = 0; i < n; i++) {
                                                excipienteMedicamento.add(gerirMedicamento.getExcipienteById(
                                                        leDadosInt("Introduza o index do excipiente: ")));
                                            }

                                            ArrayList<ComponenteAtivo> componentesAtivos = gerirMedicamento
                                                    .getListaComponenteAtivos();
                                            for (ComponenteAtivo componenteAtivo : componentesAtivos) {
                                                System.out.println(componentesAtivos.indexOf(componenteAtivo));
                                                System.out.println(componenteAtivo);
                                            }

                                            do {
                                                n = leDadosInt(
                                                        "Introduza o numero de componente ativo");
                                                if (n > componentesAtivos.size() || n < 0) {
                                                    System.out.println("Entrada invalida");

                                                }

                                            } while (n > componentesAtivos.size() || n < 0);

                                            ComponenteAtivo componenteAtivo = gerirMedicamento
                                                    .getComponenteAtivoById(n);

                                            if (gerirMedicamento.CriarMedicamento(marca, lote, componenteAtivo, dosagem,
                                                    stock, preco, anoFabrico, medicoNecessario, excipientes,
                                                    categoriasMedicamento, generico)) {

                                                System.out.println("Medicamento criado com sucesso");
                                            }

                                            else {
                                                System.out.println("Erro na criação do medicamento");
                                            }

                                            break;
                                        case 4:

                                            if (gerirMedicamento.criarCategoria(
                                                    leDados("Introduza a designacao da nova categoria: "),
                                                    leDados("Introduza a classificacao: "),
                                                    leDadosInt("introduza o codigo: "),
                                                    leDados("introduza o fornecedor: "))) {
                                                System.out.println("Categoria criada com sucesso ");
                                            } else {
                                                System.out.println(
                                                        "Erro na criação da categoria, verifique se o codigo já está a ser utilizado");
                                            }

                                            break;

                                        case 5:
                                            // String aDesignacao, String aClassificacao, int aQuantidade
                                            if (gerirMedicamento.criarExcipiente(
                                                    leDados("Introduza o nome do excipiente: "),
                                                    leDados("Introduza a classificacao: "),
                                                    leDadosInt("Introduza a quantidade padrao: "))) {
                                                System.out.println("Excipiente criado com sucesso ");
                                            } else {
                                                System.out.println(
                                                        "Erro na criação do excipiente");
                                            }

                                            break;

                                        case 6:
                                            if (gerirMedicamento.criarExcipiente(
                                                    leDados("Introduza o nome da componente ativa: "),
                                                    leDados("Introduza a classificacao: "),
                                                    leDadosInt("Introduza a quantidade padrao: "))) {
                                                System.out.println("Excipiente criado com sucesso ");
                                            } else {
                                                System.out.println(
                                                        "Erro na criação do excipiente");
                                            }

                                            break;

                                        case 7:

                                            if (gerirMedicamento.getMedicamentoByNome(
                                                    leDados("Introduza o nome do medicamento: ")) == null) {
                                                System.out.println("Nao foi possível encontrar o medicamento");

                                            }

                                        default:
                                            break;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Login ou password errados!");
                        }
                    }

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

    private static float leDadosFloat(String aMensagem) {
        while (true) {
            try {
                System.out.println(aMensagem);
                Scanner teclado = new Scanner(System.in);
                return teclado.nextFloat();

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
                    // Se o ficheiro não existe, cria
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

    private static boolean AlterarDadosUser(User aUserLogado) {
        int opLogado = leDadosInt(
                "Escolha o que deseja editar\n1-Username\n2-Password\n3-Nome\n4-Email\n");
        switch (opLogado) {
            case 1:
                aUserLogado.setLogin(leDados("Introduza o seu username: "));
                break;

            case 2:
                aUserLogado.setPassword(leDados("Introduza a sua password: "));
                break;

            case 3:
                aUserLogado.setNome(leDados("Introduza o seu nome: "));
                break;

            case 4:
                aUserLogado.setEmail(leDados("Introduza o seu email: "));
                break;

            default:
                System.out.println("Opção inválida");
                return false; // Adicionado para indicar que a operação falhou
        }
        return true; // Adicionado para indicar que a operação foi bem-sucedida
    }

}
