
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
                System.out.println(
                        "Devido à inexistencia de utilizadores é necessario criar um gestor para iniciar a aplicacao");

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
                                        "1- Gerir Registos \n2- Gerir Encomendas\n3-Ver contas\n4-Alterar dados da conta\n5-Ver medicamentos\n6-Ver encomendas\n0-Encerrar sessao");
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
                                        opLogado = leDadosInt(
                                                "1-Ver todas as contas\n2-Listar farmaceuticos\n3-Listar clientes\n4-Listar gestores\n5-Procurar por nome\n6-Procurar por login(username)");
                                        boolean ordenar = leDadosInt("Deseja ordenar por nome? 1-Sim\n2-Nao") == 1;
                                        ArrayList<User> users = new ArrayList<User>();

                                        switch (opLogado) {

                                            case 1:

                                                users = gerirUser.getUsers();

                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users == null) {

                                                    System.out.println("Nao existem contas");

                                                }

                                                else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + user);

                                                    }
                                                }

                                                break;

                                            case 2:

                                                users = gerirUser.getUsersByClassname("Farmaceutico");
                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users == null) {

                                                    System.out.println("Nao existem farmaceuticos");
                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + user);

                                                    }
                                                }
                                                break;
                                            case 3:

                                                users = gerirUser.getUsersByClassname("Cliente");
                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users == null) {

                                                    System.out.println("Nao existem clientes");

                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + user);

                                                    }
                                                }
                                                break;
                                            case 4:

                                                users = gerirUser.getUsersByClassname("Gestor");
                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users == null) {

                                                    System.out.println("Nao existem gestores");
                                                } {

                                                for (User user : users) {
                                                    System.out.println(users.indexOf(user) + "-" + user);

                                                }
                                            }
                                            case 5:

                                                users = gerirUser.getUserByNome(leDados("Indique o nome: "));
                                                if (ordenar) {
                                                    users = gerirUser.getOrderedUsers(users);

                                                }
                                                if (users == null) {

                                                    System.out.println("Nao exitem utilizadores com esse nome");
                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + user);
                                                    }
                                                }

                                                break;

                                            case 6:

                                                users = gerirUser.getUserByUsername("Indique o username: ");
                                                if (ordenar) {
                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users == null) {
                                                    System.out.println("Nao existem utilizadores com esse username");
                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + user);
                                                    }
                                                }
                                            default:

                                                break;

                                        }
                                        break;

                                    case 4:
                                        if (AlterarDadosUser(Userlogado)) {
                                            System.out.println("Dados alterados com sucesso");
                                        } else {
                                            System.out.println("Falha na alteração dos dados");

                                        }
                                        break;
                                    case 5:
                                        switch (leDadosInt(
                                                "1-Listar todos os medicamentos\n2-Procurar medicamentos por nome\n3-Procurar medicamentos por categoria\n4-Procurar medicamentos por componente ativa\n5-Procurar por genericidade\n6-Procurar medicamentos com uma quantidade de stock abaixo de um determinado limite")) {
                                            case 1:
                                                // 1-Ver medicamentos
                                                ArrayList<Medicamentos> medicamentos = gerirMedicamento
                                                        .getMedicamentos();
                                                for (Medicamentos medicamento : medicamentos) {
                                                    System.out.println(
                                                            medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                }
                                                break;
                                            case 2:
                                                // 2-Procurar medicamentos por nome
                                                medicamentos = gerirMedicamento
                                                        .getMedicamentosByNomeParcial(leDados("Indique o nome: "));
                                                for (Medicamentos medicamento : medicamentos) {
                                                    System.out.println(
                                                            medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                }
                                                break;
                                            case 3:
                                                // 3-Procurar medicamentos por categoria
                                                medicamentos = gerirMedicamento
                                                        .getMedicamentosByCategoria(leDados("Indique a categoria: "));
                                                for (Medicamentos medicamento : medicamentos) {
                                                    System.out.println(
                                                            medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                }
                                                break;
                                            case 4:
                                                // 4-Procurar medicamentos por componente ativa
                                                medicamentos = gerirMedicamento.getMedicamentosByComponenteAtiva(
                                                        leDados("Indique o componente ativo: "));
                                                for (Medicamentos medicamento : medicamentos) {
                                                    System.out.println(
                                                            medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                }
                                                break;
                                            case 5:
                                                // 5-Procurar por genericidade
                                                medicamentos = gerirMedicamento.getMedicamentosByGenerico(
                                                        leDadosInt("1-Generico\n2-Nao Generico\n") == 1);
                                                for (Medicamentos medicamento : medicamentos) {
                                                    System.out.println(
                                                            medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                }
                                                break;
                                            case 6:
                                                // 6-Procurar medicamentos com uma quantidade de stock abaixo de um
                                                // determinado limite
                                                medicamentos = gerirMedicamento
                                                        .getMedicamentosByStock(leDadosInt("Indique o limite: "));
                                                for (Medicamentos medicamento : medicamentos) {
                                                    System.out.println(
                                                            medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                }
                                                break;
                                            default:

                                                break;

                                        }
                                        break;

                                    case 6:
                                        // 6-Ver encomendas
                                        opLogado = leDadosInt(
                                                "1-Ver todas as encomendas\n2-Listar encomendas de um cliente\n3-Listar encomendas a decorrer\n4-Listar encomendas encerradas\n5-Procurar encomenda por ID\n6-Procurar encomenda com tempo despendido superior a um determinado limite");
                                        HashMap<Cliente, ArrayList<Encomendas>> encomendas = new HashMap<Cliente, ArrayList<Encomendas>>();
                                        switch (opLogado) {
                                            case 1:

                                                encomendas = gerirEncomendas.getEncomendas();

                                                for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendas
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    ArrayList<Encomendas> encomendasCliente = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    for (Encomendas encomenda : encomendasCliente) {
                                                        System.out.println("Encomenda: " + encomenda);
                                                    }

                                                }
                                                break;
                                            case 2:
                                                switch (leDadosInt(
                                                        "Deseja listar todos os clientes ou pesquisar por nome?\n1-Listar todos\n2-Pesquisar por nome ")) {
                                                    case 1:

                                                        ArrayList<User> clientes = gerirUser
                                                                .getUsersByClassname("Cliente");
                                                        for (User cliente : clientes) {
                                                            cliente = (Cliente) cliente;

                                                            System.out.println(
                                                                    clientes.indexOf(cliente) + cliente.toString());
                                                        }
                                                        int index;
                                                        do {
                                                            index = leDadosInt(
                                                                    "Qual é o numero do cliente que deseja?\nIntroduza um numero negativo se deseja votar atras");
                                                            ArrayList<Encomendas> encomendasList = new ArrayList<Encomendas>();
                                                            if (index >= 0
                                                                    && index < clientes.size()) {
                                                                encomendasList = gerirEncomendas
                                                                        .getEncomendasByCliente(
                                                                                (Cliente) clientes.get(index));
                                                                System.out.println("Encomendas do cliente: "
                                                                        + clientes.get(index).getNome());
                                                                for (Encomendas encomenda : encomendasList) {
                                                                    System.out.println(encomenda);
                                                                }

                                                            } else if (index >= encomendasList.size()) {
                                                                System.out.println(
                                                                        "NUmero invalido encontrado");
                                                            }
                                                        } while (index >= clientes.size());

                                                        break;

                                                    case 2:

                                                        ArrayList<User> resultadoClientes = gerirUser.getUserByNome(
                                                                leDados("Introduza o nome do cliente que procura: "),
                                                                "Cliente");
                                                        if (resultadoClientes.size() == 0) {

                                                            System.out.println("Cliente não encontrado");
                                                        } else {
                                                            for (User cliente : resultadoClientes) {
                                                                cliente = (Cliente) cliente;

                                                                System.out.println(
                                                                        resultadoClientes.indexOf(cliente)
                                                                                + cliente.toString());
                                                            }
                                                        }

                                                        do {
                                                            index = leDadosInt(
                                                                    "Qual é o numero do cliente que deseja?\nIntroduza um numero negativo se deseja votar atras");
                                                            if (index >= 0
                                                                    && index < resultadoClientes.size()) {

                                                                ArrayList<Encomendas> encomendasList = gerirEncomendas
                                                                        .getEncomendasByCliente(
                                                                                (Cliente) resultadoClientes.get(index));
                                                                System.out.println("Encomendas do cliente: "
                                                                        + resultadoClientes.get(index).getNome());
                                                                for (Encomendas encomenda : encomendasList) {
                                                                    System.out.println(encomenda);
                                                                }

                                                            }

                                                            else if (index > resultadoClientes.size()) {

                                                                System.out.println("numero invalido");

                                                            }

                                                        } while (index >= resultadoClientes.size());

                                                        break;
                                                    default:
                                                        break;

                                                }
                                            case 3:
                                                HashMap<Cliente, ArrayList<Encomendas>> resultado = gerirEncomendas
                                                        .getEncomendasByStatus(3);

                                                for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : resultado
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    ArrayList<Encomendas> encomendasCliente = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    for (Encomendas encomenda : encomendasCliente) {
                                                        System.out.println("Encomenda: " + encomenda);
                                                    }
                                                }

                                                break;

                                            case 4:
                                                resultado = gerirEncomendas
                                                        .getEncomendasByStatus(5);

                                                for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : resultado
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    ArrayList<Encomendas> encomendasCliente = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    for (Encomendas encomenda : encomendasCliente) {
                                                        System.out.println("Encomenda: " + encomenda);
                                                    }
                                                }

                                                break;

                                            case 5:
                                                // 5-Procurar encomenda por ID

                                                Encomendas encomenda = gerirEncomendas
                                                        .getEncomendaById(leDadosInt("Introduza o ID da encomenda: "));

                                                if (encomenda == null) {

                                                    System.out.println("Encomenda não encontrada");
                                                } else {
                                                    System.err.println("Cliente: "
                                                            + gerirEncomendas.getClienteByEncomenda(encomenda));
                                                    System.out.println(encomenda);
                                                }

                                                break;

                                            case 6:
                                                // 6-Procurar encomenda com tempo despendido superior a um determinado
                                                // limite

                                                HashMap<Cliente, Encomendas> encomendasHasMap = gerirEncomendas
                                                        .getEncomendasByhorasgastas(leDadosFloat(
                                                                "Introduza o limite de horas gastas: "));
                                                if (encomendasHasMap.size() == 0) {

                                                    System.out.println("Nao existem encomendas com esse limite");
                                                } {

                                                for (HashMap.Entry<Cliente, Encomendas> entry : encomendasHasMap
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    encomenda = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    System.out.println("Encomenda: " + encomenda);
                                                }
                                            }
                                                break;

                                            default:
                                                break;

                                        }
                                        break;

                                }

                                if (Userlogado instanceof Cliente) {
                                    System.out.println("cliente");
                                    while (opLogado > 0) {
                                        opLogado = leDadosInt(
                                                "1- Solicitar encomenda\n2-Alterar dados da Conta\n0-Encerrar sessao");
                                        switch (opLogado) {
                                            case 0:
                                                System.out.println("Adeus " + Userlogado.getNome());
                                                break;
                                            case 1:
                                                // cliente solicita encomenda
                                                ArrayList<Object> listaUser = new ArrayList<Object>();
                                                boolean continuar = true;
                                                while (continuar) {

                                                    switch (leDadosInt(
                                                            "Deseja procurar o medicamento por componente ativo ou por nome?\n1-Nome\n2-Componente Ativo")) {

                                                        case 1:
                                                            switch (leDadosInt(
                                                                    "Deseja procurar manualmete ou listar todos os medicamentos?\n1-Manual\n2-Listar todos")) {
                                                                case 1:
                                                                    ArrayList<Medicamentos> medicamentos = gerirMedicamento
                                                                            .getMedicamentosByNomeParcial(leDados(
                                                                                    "Introduza o nome do medicamento: "));
                                                                    // print arraylist
                                                                    if (medicamentos != null) {
                                                                        for (Medicamentos medicamento : medicamentos) {
                                                                            System.out.println(medicamento);
                                                                        }
                                                                        int index;
                                                                        do {
                                                                            index = leDadosInt(
                                                                                    "Qual é o numero do medicamento que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                                            if (index >= 0
                                                                                    && index < medicamentos.size()) {
                                                                                listaUser.add(medicamentos.get(index));
                                                                            } else {
                                                                                System.out.println(
                                                                                        "Medicamento não encontrado");
                                                                            }
                                                                        } while (index >= medicamentos.size());

                                                                    }
                                                                    continuar = leDadosInt(
                                                                            "Deseja adicionar outro medicamento?\n1-Sim\n2-Nao") == 1;
                                                                    break;

                                                                case 2:
                                                                    medicamentos = gerirMedicamento.getMedicamentos();
                                                                    for (Medicamentos medicamento : medicamentos) {
                                                                        System.out.println(
                                                                                medicamentos.indexOf(medicamento)
                                                                                        + medicamento.toString());
                                                                    }
                                                                    int index;
                                                                    do {
                                                                        index = leDadosInt(
                                                                                "Qual é o numero do medicamento que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                                        if (index >= 0 && index < medicamentos.size()) {
                                                                            listaUser.add(medicamentos.get(index));
                                                                        } else {
                                                                            System.out.println(
                                                                                    "Medicamento não encontrado");
                                                                        }
                                                                    } while (index >= medicamentos.size());

                                                                    continuar = leDadosInt(
                                                                            "Deseja adicionar outro medicamento?\n1-Sim\n2-Nao") == 1;

                                                                    break;

                                                                default:
                                                                    break;
                                                            }

                                                        case 2:
                                                            switch (leDadosInt(
                                                                    "Deseja procurar manualmete ou listar todos os componentes?\n1-Manual\n2-Listar todos")) {
                                                                case 1:
                                                                    ArrayList<ComponenteAtivo> componentesAtivos = gerirMedicamento
                                                                            .getComponentesAtivosByNomeParcial(leDados(
                                                                                    "Introduza o nome do medicamento: "));
                                                                    if (componentesAtivos != null) {
                                                                        for (ComponenteAtivo componente : componentesAtivos) {
                                                                            System.out.println(componente);
                                                                        }
                                                                        int index;
                                                                        do {
                                                                            index = leDadosInt(
                                                                                    "Qual é o numero do componente que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                                            if (index >= 0
                                                                                    && index < componentesAtivos
                                                                                            .size()) {
                                                                                listaUser.add(
                                                                                        componentesAtivos.get(index));
                                                                            } else {
                                                                                System.out.println(
                                                                                        "Componente não encontrado");
                                                                            }
                                                                        } while (index >= componentesAtivos.size());

                                                                    }
                                                                    continuar = leDadosInt(
                                                                            "Deseja adicionar outro elemento ao pedido?\n1-Sim\n2-Nao") == 1;
                                                                case 2:
                                                                    componentesAtivos = gerirMedicamento
                                                                            .getListaComponenteAtivos();
                                                                    for (ComponenteAtivo componente : componentesAtivos) {
                                                                        System.out.println(
                                                                                componentesAtivos.indexOf(componente)
                                                                                        + componente.toString());
                                                                    }
                                                                    int index;
                                                                    do {
                                                                        index = leDadosInt(
                                                                                "Qual é o numero do componente que deseja ?\nIntroduza um numero negativo se deseja votar atras");
                                                                        if (index >= 0
                                                                                && index < componentesAtivos.size()) {
                                                                            listaUser.add(componentesAtivos.get(index));
                                                                        } else {
                                                                            System.out.println(
                                                                                    "Componente não encontrado");

                                                                        }

                                                                    } while (index >= componentesAtivos.size());
                                                                    continuar = leDadosInt(
                                                                            "Deseja adicionar outro elemento ao pedido?\n1-Sim\n2-Nao") == 1;
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                    }

                                                }
                                                String descricao = leDados(
                                                        "Introduza uma descricao para a sua encomenda");
                                                boolean urgente = leDadosInt(
                                                        "A encomenda é urgente?\n1-Sim\n2-Nao") == 1;

                                                Encomendas encomendaUser = new Encomendas(listaUser, descricao,
                                                        urgente);

                                                gerirEncomendas.adicionarEncomenda((Cliente) Userlogado, encomendaUser);

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
                                                "1-Gerir encomendas\n2-Alterar dados da Conta\n3-Introduzir medicamento\n4-Introduzir categoria\n5-Introduzir excipiente\n6-Introduzir componente ativa\n7-Modificar stock de medicamento existente\n0-Encerrar sessao");

                                        switch (opLogado) {
                                            case 1:
                                                switch (leDadosInt(
                                                        "1-Iniciar encomenda \n2-Conluir encomenda\n3-Consultar todas as encomendas associadas a si\n4-Adicionar subtarefa a uma encomenda iniciada")) {
                                                    case 1:
                                                        HashMap<Cliente, ArrayList<Encomendas>> encomendas = gerirEncomendas
                                                                .getEncomendasByStatus(2,
                                                                        gerirEncomendas.getEncomendasByFarmaceutico(
                                                                                (Farmaceutico) Userlogado));
                                                        if (encomendas == null) {
                                                            System.out.println("Não exitem encomendas para iniciar");
                                                        } else {
                                                            // print hashmap encomendas
                                                            for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendas
                                                                    .entrySet()) {
                                                                Cliente cliente = entry.getKey();
                                                                ArrayList<Encomendas> encomendasCliente = entry
                                                                        .getValue();
                                                                System.out.println("Cliente: " + cliente.getNome());
                                                                for (Encomendas encomenda : encomendasCliente) {
                                                                    System.out.println("Encomenda: " + encomenda);
                                                                }
                                                            }

                                                            gerirEncomendas
                                                                    .processarEncomenda(
                                                                            gerirEncomendas.getEncomendaById(
                                                                                    leDadosInt(
                                                                                            "Introduza o id da encomenda a iniciar")));
                                                        }
                                                    case 2:
                                                        HashMap<Cliente, ArrayList<Encomendas>> encomendasPorConcluir = gerirEncomendas
                                                                .getEncomendasByStatus(3,
                                                                        gerirEncomendas.getEncomendasByFarmaceutico(
                                                                                (Farmaceutico) Userlogado));
                                                        if (encomendasPorConcluir == null) {
                                                            System.out.println("Não exitem encomendas por concluir");
                                                        } else {
                                                            // print hashmap encomendas
                                                            for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendasPorConcluir
                                                                    .entrySet()) {
                                                                Cliente cliente = entry.getKey();
                                                                ArrayList<Encomendas> encomendasCliente = entry
                                                                        .getValue();
                                                                System.out.println("Cliente: " + cliente.getNome());
                                                                for (Encomendas encomenda : encomendasCliente) {
                                                                    System.out.println("Encomenda: \n" + encomenda);
                                                                }
                                                            }

                                                            Encomendas encomenda = gerirEncomendas.getEncomendaById(
                                                                    leDadosInt(
                                                                            "Introduza o id da encomenda Concluida"));
                                                            gerirEncomendas.atribuirTotal(encomenda,
                                                                    leDadosInt("Introduza o valor total do pedido"));

                                                            gerirEncomendas.atribuirHoras(encomenda,
                                                                    leDadosInt("Introduza as horas gastas: "));

                                                            // Substituir componentes ativas por medicamentos escolhidos
                                                            // pelo farmacêutico
                                                            ArrayList<Object> novosItens = new ArrayList<>();
                                                            for (Object item : encomenda.getLista()) {
                                                                if (item instanceof ComponenteAtivo) {
                                                                    ComponenteAtivo componenteAtivo = (ComponenteAtivo) item;
                                                                    ArrayList<Medicamentos> medicamentos = gerirMedicamento
                                                                            .getMedicamentosByComponenteAtiva(
                                                                                    componenteAtivo.getDesignacao());
                                                                    System.out.println(
                                                                            "Escolha um medicamento para o componente ativo: "
                                                                                    + componenteAtivo.getDesignacao());
                                                                    for (Medicamentos medicamento : medicamentos) {
                                                                        System.out
                                                                                .println((medicamentos
                                                                                        .indexOf(medicamento))
                                                                                        + ". " + medicamento.getNome());
                                                                    }
                                                                    int escolha = leDadosInt(
                                                                            "Digite o número do medicamento escolhido: ");
                                                                    if (escolha >= 0
                                                                            && escolha <= medicamentos.size()) {
                                                                        novosItens.add(medicamentos.get(escolha - 1));
                                                                    }
                                                                } else {
                                                                    novosItens.add(item);
                                                                }
                                                            }
                                                            encomenda.setLista(novosItens);
                                                            gerirEncomendas
                                                                    .processarEncomenda(encomenda);
                                                        }

                                                        break;
                                                    case 3:
                                                        HashMap<Cliente, ArrayList<Encomendas>> encomendasFarmaceutico = gerirEncomendas
                                                                .getEncomendasByFarmaceutico((Farmaceutico) Userlogado);
                                                        if (encomendasFarmaceutico == null) {
                                                            System.out.println("Não exitem encomendas associadas a si");
                                                        } else {
                                                            // print hashmap encomendas
                                                            for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendasFarmaceutico
                                                                    .entrySet()) {
                                                                Cliente cliente = entry.getKey();
                                                                ArrayList<Encomendas> encomendasCliente = entry
                                                                        .getValue();
                                                                System.out.println("Cliente: " + cliente.getNome());
                                                                for (Encomendas encomenda : encomendasCliente) {
                                                                    System.out.println("Encomenda: \n" + encomenda);
                                                                }
                                                            }
                                                        }
                                                    case 4:
                                                        HashMap<Cliente, ArrayList<Encomendas>> encomendasIniciadas = gerirEncomendas
                                                                .getEncomendasByStatus(3,
                                                                        gerirEncomendas.getEncomendasByFarmaceutico(
                                                                                (Farmaceutico) Userlogado));
                                                        if (encomendasIniciadas == null) {
                                                            System.out.println("Não exitem encomendas a decorrer");
                                                        } else {
                                                            // print hashmap encomendas
                                                            for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendasIniciadas
                                                                    .entrySet()) {
                                                                Cliente cliente = entry.getKey();
                                                                ArrayList<Encomendas> encomendasCliente = entry
                                                                        .getValue();
                                                                System.out.println("Cliente: " + cliente.getNome());
                                                                for (Encomendas encomenda : encomendasCliente) {
                                                                    System.out.println("Encomenda: \n" + encomenda);
                                                                }
                                                            }

                                                            Encomendas encomenda = gerirEncomendas.getEncomendaById(
                                                                    leDadosInt(
                                                                            "Introduza o id da encomenda a que deseja adicionar a subtarefa: "));
                                                            ArrayList<User> listaFarmaceuticos = gerirUser
                                                                    .getUsersByClassname("Farmaceutico");

                                                            for (User farmaceutico : listaFarmaceuticos) {
                                                                System.out.println(
                                                                        listaFarmaceuticos.indexOf(farmaceutico)
                                                                                + farmaceutico.getNome());
                                                            }

                                                            Farmaceutico farmaceutico = (Farmaceutico) listaFarmaceuticos
                                                                    .get(leDadosInt(
                                                                            "Introduza o id do farmaceutico: "));

                                                            encomenda.criarSubTarefa(farmaceutico,
                                                                    leDados("Introduza o nome da subtarefa: "));

                                                        }

                                                    default:
                                                        break;
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
                                                int anoFabrico = leDadosInt(
                                                        "Introduza o ano de fabrico do medicamento: ");
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

                                                ArrayList<Excipiente> excipientes = gerirMedicamento
                                                        .getListaExcipientes();
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

                                                if (gerirMedicamento.CriarMedicamento(marca, lote, componenteAtivo,
                                                        dosagem,
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
                                                ArrayList<Medicamentos> medicamentos = gerirMedicamento
                                                        .getMedicamentosByNomeParcial(
                                                                leDados("Introduza o nome do medicamento"));
                                                if (medicamentos == null) {
                                                    System.out.println("Medicamento não encontrado");

                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {

                                                        System.out.println(medicamentos.indexOf(medicamento)
                                                                + medicamento.toString());

                                                    }

                                                }

                                            default:
                                                break;
                                        }
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
