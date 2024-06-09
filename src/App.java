
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
                System.out.println(
                        "Devido a inexistencia de utilizadores e necessario criar um gestor para iniciar a aplicacao");

                if (gerirUser.criarGestor(leDados("Introduza o seu username: "), leDados("Introduza a sua password: "),
                        leDados("Introduza o seu nome: "), leDados("Introduza o seu email: "), true)) {
                    System.out.println("Gestor criado com sucesso!");
                    logAction("Sistema", "Criacao de gestor inicial");

                } else {
                    System.out.println("Gestor nao criado!, introduza um email valido");
                    logAction("Sistema", "Criacao de gestor inicial falhida");

                }
            }
        }

        String menu = "1-Login\n2-Criar Conta\n0-Sair";
        int op = 1;
        while (op > 0) {

            switch (op = leDadosInt(menu)) {
                case 1:

                    User Userlogado = gerirUser.logar(leDados("Introduza o seu username: "),
                            leDados("Introduza a sua password: "));

                    if (Userlogado != null) {
                        int opLogado = 1;
                        System.out.println("Bem-vindo " + Userlogado.getLogin());
                        logAction(Userlogado.login, "Fez login");
                        infoSistema.setUltimoUser(Userlogado.getLogin());
                        saveInfoSistema(infoSistema);
                        if (Userlogado instanceof Gestor) {
                            System.out.println("gestor");
                            while (opLogado != 0) {

                                switch (opLogado = leDadosInt(
                                        "1- Gerir Registos \n2- Gerir Encomendas\n3-Ver contas\n4-Alterar dados da conta\n5-Ver medicamentos\n6-Ver encomendas\n0-Encerrar sessao")) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());
                                        logAction(Userlogado.getLogin(), "Encerrar sessao");
                                        break;
                                    case 1:

                                        ArrayList<User> pedidos = gerirUser.GetPedidosdeRegisto();
                                        if (!pedidos.isEmpty()){
                                            logAction(Userlogado.getLogin(), "GetPedidosdeRegisto");

                                        // Print HashMap
                                        for (User u : pedidos) {
                                            System.out.println(pedidos.indexOf(u) + "- " + "Nome: " + u.getNome()
                                                    + ", Tipo de conta: " + u.getClass().getName());

                                        }

                                        int pedido = leDadosInt("Indique o Pedido que deseja aprovar: ");
                                        if (pedido > pedidos.size() || pedido < 0) {
                                            System.out.println("Pedido inexistente");
                                            logAction(Userlogado.getLogin(),
                                                    "Erro pedido de registo inexistente" + pedido);
                                        } else {
                                            if (pedidos.get(pedido).setAtivo()) {
                                                System.out.println("Pedido de registo aprovado com sucesso");

                                                logAction(Userlogado.getLogin(), "Aprovou pedido de registo" + pedido);
                                            } else {
                                                System.out.println("Erro ao aprovar pedido de registo");
                                                logAction(Userlogado.getLogin(),
                                                        "Erro ao aprovar pedido de registo" + pedido);
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println("Nao existem pedidos de registo");
                                        logAction(Userlogado.getLogin(), "Nao existem pedidos de registo");
                                    }
                                        break;
                                    case 2:
                                        HashMap<Cliente, ArrayList<Encomendas>> Encomendas;
                                        int opEncomenda;
                                        switch (opLogado = leDadosInt("1-Aprovar encomendas\n2-Encerrar encomendas")) {
                                            case 1:
                                                Encomendas = gerirEncomendas
                                                        .getEncomendasByStatus(1);
                                                if (!Encomendas.isEmpty()) {
                                                    logAction(Userlogado.getLogin(), "getEncomendasByStatus(1)");
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
                                                        logAction(Userlogado.getLogin(),
                                                                "Erro Encomenda Inexistente" + opEncomenda);
                                                    } else {
                                                        gerirEncomendas
                                                                .aprovarEncomenda(
                                                                        gerirEncomendas.getEncomendaById(opEncomenda));
                                                        logAction(Userlogado.getLogin(),
                                                                "Encomenda Aprovada" + opEncomenda);
                                                    }

                                                }else{
                                                    System.out.println("Nao existem encomendas pendentes");
                                                    logAction(Userlogado.getLogin(), "getEncomendasByStatus, Nao existem encomendas pendentes");
                                                }


                                                

                                                break;

                                            case 2:

                                                Encomendas = gerirEncomendas
                                                        .getEncomendasByStatus(4);

                                                if(!Encomendas.isEmpty()){
                                                    logAction(Userlogado.getLogin(), "getEncomendasByStatus(4");
                                                
                                            



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
                                                    logAction(Userlogado.getLogin(),
                                                            "Erro Encomenda Inexistente" + opEncomenda);

                                                } else {
                                                    gerirEncomendas
                                                            .aprovarEncomenda(
                                                                    gerirEncomendas.getEncomendaById(opEncomenda));
                                                    logAction(Userlogado.getLogin(),
                                                            "Encomenda Aprovada" + opEncomenda);

                                                }
                                            }else{
                                                System.out.println("Nao existem encomendas concluidas por encerrar");
                                                logAction(Userlogado.getLogin(), "getEncomendasByStatus(4), Nao existem encomendas concluidas por encerrar");
                                            }
                                                break;
                                                

                                            default:
                                                break;
                                        }
                                        break;

                                    case 3:
                                        opLogado = leDadosInt(
                                                "1-Ver todas as contas\n2-Listar farmaceuticos\n3-Listar clientes\n4-Listar gestores\n5-Procurar por nome\n6-Procurar por login(username)");
                                        boolean ordenar = leDadosInt("Deseja ordenar por nome?\n1-Sim\n2-Nao") == 1;
                                        ArrayList<User> users = new ArrayList<User>();

                                        switch (opLogado) {

                                            case 1:

                                                users = gerirUser.getUsers();

                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users .isEmpty()) {

                                                    System.out.println("Nao existem contas");
                                                    logAction(Userlogado.getLogin(),
                                                            "getusers erro, nao existem contas");

                                                }

                                                else {
                                                    for (User user : users) {

                                                        System.out.println(users.indexOf(user) + "-" + "tipo de conta: "
                                                                + user.getClass().getName() + " " + user);
                                                        logAction(Userlogado.getLogin(), "getusers");

                                                    }
                                                }

                                                break;

                                            case 2:

                                                users = gerirUser.getUsersByClassname("Farmaceutico");
                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users .isEmpty()) {

                                                    System.out.println("Nao existem farmaceuticos");
                                                    logAction(Userlogado.getLogin(),
                                                            "getfarmaceuticos erro, nao existem farmaceuticos");
                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + "tipo de conta: "
                                                                + user.getClass().getName() + " " + user);
                                                        logAction(Userlogado.getLogin(), "getfarmaceuticos");

                                                    }
                                                }
                                                break;
                                            case 3:

                                                users = gerirUser.getUsersByClassname("Cliente");
                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users .isEmpty()) {

                                                    System.out.println("Nao existem clientes");

                                                    logAction(Userlogado.getLogin(),
                                                            "getclientes erro, nao existem clientes");

                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + "tipo de conta: "
                                                                + user.getClass().getName() + " " + user);

                                                        logAction(Userlogado.getLogin(), "getclientes");

                                                    }
                                                }
                                                break;
                                            case 4:

                                                users = gerirUser.getUsersByClassname("Gestor");
                                                if (ordenar) {

                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users .isEmpty()) {

                                                    System.out.println("Nao existem gestores");

                                                    logAction(Userlogado.getLogin(),
                                                            "getgestores erro, nao existem gestores");
                                                } {

                                                for (User user : users) {
                                                    System.out.println(users.indexOf(user) + "-" + "tipo de conta: "
                                                            + user.getClass().getName() + " " + user);

                                                    logAction(Userlogado.getLogin(), "getgestores");

                                                }
                                            }
                                            case 5:

                                                users = gerirUser.getUserByNome(leDados("Indique o nome: "));
                                                if (ordenar) {
                                                    users = gerirUser.getOrderedUsers(users);

                                                }
                                                if (users.isEmpty()) {

                                                    System.out.println("Nao exitem utilizadores com esse nome");

                                                    logAction(Userlogado.getLogin(),
                                                            "getnomeUSer erro, nao existem utilizadores com esse nome");
                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + "tipo de conta: "
                                                                + user.getClass().getName() + " " + user);
                                                    }

                                                    logAction(Userlogado.getLogin(), "getnomeUser");
                                                }

                                                break;

                                            case 6:

                                                users = gerirUser.getUserByUsername("Indique o username: ");
                                                if (ordenar) {
                                                    users = gerirUser.getOrderedUsers(users);
                                                }
                                                if (users .isEmpty()) {
                                                    System.out.println("Nao existem utilizadores com esse username");

                                                    logAction(Userlogado.getLogin(),
                                                            "getusernameUser erro, nao existem utilizadores com esse username");
                                                } else {
                                                    for (User user : users) {
                                                        System.out.println(users.indexOf(user) + "-" + "tipo de conta: "
                                                                + user.getClass().getName() + " " + user);
                                                    }

                                                    logAction(Userlogado.getLogin(), "getusernameUser");
                                                }
                                            default:

                                                break;

                                        }
                                        break;

                                    case 4:
                                        if (AlterarDadosUser(Userlogado)) {
                                            System.out.println("Dados alterados com sucesso");

                                            logAction(Userlogado.getLogin(), "alterar dados");
                                        } else {
                                            System.out.println("Falha na alteracao dos dados");

                                            logAction(Userlogado.getLogin(), "alterar dados erro");

                                        }
                                        break;
                                    case 5:
                                        switch (leDadosInt(
                                                "1-Listar todos os medicamentos\n2-Procurar medicamentos por nome\n3-Procurar medicamentos por categoria\n4-Procurar medicamentos por componente ativa\n5-Procurar por genericidade\n6-Procurar medicamentos com uma quantidade de stock abaixo de um determinado limite")) {
                                            case 1:
                                                // 1-Ver medicamentos
                                                ArrayList<Medicamentos> medicamentos = gerirMedicamento
                                                        .getMedicamentos();
                                                if (medicamentos .isEmpty()) {

                                                    System.out.println("Nao existem medicamentos");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentos erro, nao existem medicamentos");
                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {
                                                        System.out.println(
                                                                medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                    }
                                                }

                                                logAction(Userlogado.getLogin(), "getMedicamentos");
                                                break;
                                            case 2:
                                                // 2-Procurar medicamentos por nome
                                                medicamentos = gerirMedicamento
                                                        .getMedicamentosByNomeParcial(leDados("Indique o nome: "));

                                                if (medicamentos .isEmpty()) {

                                                    System.out.println("Nao existem medicamentos com esse nome");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByNomeParcial erro, nao existem medicamentos com esse nome");
                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {
                                                        System.out.println(
                                                                medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                    }

                                                    logAction(Userlogado.getLogin(), "getMedicamentosByNomeParcial");

                                                }
                                                break;
                                            case 3:
                                                // 3-Procurar medicamentos por categoria
                                                medicamentos = gerirMedicamento
                                                        .getMedicamentosByCategoria(leDados("Indique a categoria: "));

                                                if (medicamentos .isEmpty()) {

                                                    System.out.println("Nao existem medicamentos com essa categoria");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByCategoria erro, nao existem medicamentos com essa categoria");
                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {
                                                        System.out.println(
                                                                medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                    }

                                                    logAction(Userlogado.getLogin(), "getMedicamentosByCategoria");
                                                }
                                                break;
                                            case 4:
                                                // 4-Procurar medicamentos por componente ativa
                                                medicamentos = gerirMedicamento.getMedicamentosByComponenteAtiva(
                                                        leDados("Indique o componente ativo: "));

                                                if (medicamentos .isEmpty()) {

                                                    System.out.println(
                                                            "Nao existem medicamentos com esse componente ativo");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByComponenteAtiva erro, nao existem medicamentos com esse componente ativo");
                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {
                                                        System.out.println(
                                                                medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                    }

                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByComponenteAtiva");
                                                }
                                                break;
                                            case 5:
                                                // 5-Procurar por genericidade
                                                medicamentos = gerirMedicamento.getMedicamentosByGenerico(
                                                        leDadosInt("1-Generico\n2-Nao Generico\n") == 1);

                                                if (medicamentos .isEmpty()) {

                                                    System.out
                                                            .println("Nao existem medicamentos com essa genericidade");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByGenerico erro, nao existem medicamentos com essa genericidade");
                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {
                                                        System.out.println(
                                                                medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                    }

                                                    logAction(Userlogado.getLogin(), "getMedicamentosByGenerico");
                                                }
                                                break;
                                            case 6:
                                                // 6-Procurar medicamentos com uma quantidade de stock abaixo de um
                                                // determinado limite
                                                medicamentos = gerirMedicamento
                                                        .getMedicamentosByStock(leDadosInt("Indique o limite: "));

                                                if (medicamentos .isEmpty()) {

                                                    System.out.println("Nao existem medicamentos com esse stock");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByStock erro, nao existem medicamentos com esse stock");
                                                } else {
                                                    for (Medicamentos medicamento : medicamentos) {
                                                        System.out.println(
                                                                medicamentos.indexOf(medicamento) + "-" + medicamento);
                                                    }

                                                    logAction(Userlogado.getLogin(), "getMedicamentosByStock");

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

                                                if (encomendas .isEmpty()) {

                                                    System.out.println("Nao existem encomendas");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendas erro, nao existem encomendas");
                                                }

                                                else {

                                                    for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : encomendas
                                                            .entrySet()) {
                                                        Cliente cliente = entry.getKey();
                                                        ArrayList<Encomendas> encomendasCliente = entry.getValue();
                                                        System.out.println("Cliente: " + cliente.getNome());
                                                        for (Encomendas encomenda : encomendasCliente) {

                                                            if (encomendasCliente .isEmpty()) {
                                                                System.out.println(
                                                                        "Nao existem encomendas para esse cliente");
                                                            } else {
                                                                System.out.println("Encomenda: " + encomenda);
                                                            }
                                                        }

                                                    }

                                                    logAction(Userlogado.getLogin(), "getEncomendas");
                                                }
                                                break;
                                            case 2:
                                                switch (leDadosInt(
                                                        "Deseja listar todos os clientes ou pesquisar por nome?\n1-Listar todos\n2-Pesquisar por nome ")) {
                                                    case 1:

                                                        ArrayList<User> clientes = gerirUser
                                                                .getUsersByClassname("Cliente");

                                                        if (clientes .isEmpty()) {

                                                            System.out.println("Nao existem clientes");
                                                            logAction(Userlogado.getLogin(),
                                                                    "getUsersByClassname erro, nao existem clientes");
                                                        } else {
                                                            for (User cliente : clientes) {
                                                                cliente = (Cliente) cliente;

                                                                System.out.println(
                                                                        clientes.indexOf(cliente) + cliente.toString());
                                                            }
                                                            int index;
                                                            do {
                                                                index = leDadosInt(
                                                                        "Qual e o numero do cliente que deseja?\nIntroduza um numero negativo se deseja votar atras");
                                                                ArrayList<Encomendas> encomendasList = new ArrayList<Encomendas>();
                                                                if (index >= 0
                                                                        && index < clientes.size()) {
                                                                    encomendasList = gerirEncomendas
                                                                            .getEncomendasByCliente(
                                                                                    (Cliente) clientes.get(index));
                                                                    System.out.println("Encomendas do cliente: "
                                                                            + clientes.get(index).getNome());
                                                                    for (Encomendas encomenda : encomendasList) {
                                                                        if (encomendasList .isEmpty()) {
                                                                            System.out.println(
                                                                                    "Nao existem encomendas para esse cliente");
                                                                        } else {
                                                                            System.out.println(encomenda);
                                                                        }
                                                                    }

                                                                } else if (index >= encomendasList.size()) {
                                                                    System.out.println(
                                                                            "Numero invalido introduzido");

                                                                    logAction(Userlogado.getLogin(),
                                                                            "getEncomendasByCliente erro, Numero invalido introduzido");
                                                                }
                                                            } while (index >= clientes.size());
                                                        }
                                                        break;

                                                    case 2:

                                                        ArrayList<User> resultadoClientes = gerirUser.getUserByNome(
                                                                leDados("Introduza o nome do cliente que procura: "),
                                                                "Cliente");
                                                        if (resultadoClientes.isEmpty()) {

                                                            System.out.println("Cliente nao encontrado");

                                                            logAction(Userlogado.getLogin(),
                                                                    "getUserByNome erro, Cliente nao encontrado");
                                                        } else {
                                                            for (User cliente : resultadoClientes) {
                                                                cliente = (Cliente) cliente;

                                                                System.out.println(
                                                                        resultadoClientes.indexOf(cliente)
                                                                                + cliente.toString());
                                                            }

                                                            logAction(Userlogado.getLogin(), "getUserByNome");
                                                        }
                                                        int index;
                                                        do {
                                                            index = leDadosInt(
                                                                    "Qual e o numero do cliente que deseja?\nIntroduza um numero negativo se deseja votar atras");
                                                            if (index >= 0
                                                                    && index < resultadoClientes.size()) {

                                                                ArrayList<Encomendas> encomendasList = gerirEncomendas
                                                                        .getEncomendasByCliente(
                                                                                (Cliente) resultadoClientes.get(index));
                                                                System.out.println("Encomendas do cliente: "
                                                                        + resultadoClientes.get(index).getNome());
                                                                for (Encomendas encomenda : encomendasList) {

                                                                    if (encomendasList .isEmpty()) {
                                                                        System.out.println(
                                                                                "Nao existem encomendas para esse cliente");
                                                                    } else {
                                                                        System.out.println(encomenda);
                                                                    }
                                                                }

                                                            }

                                                            else if (index > resultadoClientes.size()) {

                                                                System.out.println("numero invalido");
                                                                logAction(Userlogado.getLogin(),
                                                                        "getEncomendasByCliente erro, numero invalido"
                                                                                + index);

                                                            }

                                                        } while (index >= resultadoClientes.size());

                                                        break;
                                                    default:
                                                        break;

                                                }
                                                break;
                                            case 3:
                                                HashMap<Cliente, ArrayList<Encomendas>> resultado = gerirEncomendas
                                                        .getEncomendasByStatus(3);

                                                if (resultado.isEmpty()) {

                                                    System.out.println("Nao existem encomendas com o status 3");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus erro, Nao existem encomendas com o status 3");
                                                }

                                                else {
                                                    for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : resultado
                                                            .entrySet()) {
                                                        Cliente cliente = entry.getKey();
                                                        ArrayList<Encomendas> encomendasCliente = entry.getValue();
                                                        System.out.println("Cliente: " + cliente.getNome());
                                                        for (Encomendas encomenda : encomendasCliente) {

                                                            if (encomendasCliente .isEmpty()) {
                                                                System.out.println(
                                                                        "Nao existem encomendas para esse cliente");
                                                            }

                                                            else {
                                                                System.out.println("Encomenda: " + encomenda);
                                                            }
                                                        }
                                                    }

                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(3)");
                                                }

                                                break;

                                            case 4:
                                                resultado = gerirEncomendas
                                                        .getEncomendasByStatus(5);

                                                if (resultado.isEmpty()) {
                                                    System.out.println(
                                                            "Nao existem encomendas com o status 5");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus erro, Nao existem encomendas com o status 5");
                                                }

                                                else {

                                                    for (HashMap.Entry<Cliente, ArrayList<Encomendas>> entry : resultado
                                                            .entrySet()) {
                                                        Cliente cliente = entry.getKey();
                                                        ArrayList<Encomendas> encomendasCliente = entry.getValue();
                                                        System.out.println("Cliente: " + cliente.getNome());
                                                        for (Encomendas encomenda : encomendasCliente) {

                                                            if (encomendasCliente .isEmpty()) {
                                                                System.out.println(
                                                                        "Nao existem encomendas para esse cliente");
                                                            }

                                                            else {
                                                                System.out.println("Encomenda: " + encomenda);
                                                            }
                                                        }
                                                    }

                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(5)");
                                                }

                                                break;

                                            case 5:
                                                // 5-Procurar encomenda por ID

                                                Encomendas encomenda = gerirEncomendas
                                                        .getEncomendaById(leDadosInt("Introduza o ID da encomenda: "));

                                                if (encomenda ==null) {

                                                    System.out.println("Encomenda nao encontrada");

                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendaById erro, Encomenda nao encontrada");
                                                } else {
                                                    System.out.println("Cliente: "
                                                            + gerirEncomendas.getClienteByEncomenda(encomenda));
                                                    System.out.println(encomenda);

                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendaById");
                                                }

                                                break;

                                            case 6:
                                                // 6-Procurar encomenda com tempo despendido superior a um determinado
                                                // limite

                                                HashMap<Cliente, Encomendas> encomendasHasMap = gerirEncomendas
                                                        .getEncomendasByhorasgastas(leDadosFloat(
                                                                "Introduza o limite de horas gastas: "));
                                                if (encomendasHasMap.isEmpty()) {

                                                    System.out.println("Nao existem encomendas com esse limite");

                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByhorasgastas erro, Nao existem encomendas com esse limite");
                                                } {

                                                for (HashMap.Entry<Cliente, Encomendas> entry : encomendasHasMap
                                                        .entrySet()) {
                                                    Cliente cliente = entry.getKey();
                                                    encomenda = entry.getValue();
                                                    System.out.println("Cliente: " + cliente.getNome());
                                                    System.out.println("Encomenda: " + encomenda);
                                                }

                                                logAction(Userlogado.getLogin(),
                                                        "getEncomendasByhorasgastas");

                                            }
                                                break;

                                            default:
                                                break;

                                        }
                                        break;

                                }
                            }
                        }

                        if (Userlogado instanceof Cliente) {
                            System.out.println("cliente");
                            while (opLogado > 0) {
                                opLogado = leDadosInt(
                                        "1- Solicitar encomenda\n2-Alterar dados da Conta\n3-Consultar as suas encomendas\n0-Encerrar sessao");
                                switch (opLogado) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());

                                        logAction(Userlogado.getLogin(),
                                                "encerrar sessao");
                                        break;
                                    case 1:
                                        logAction(Userlogado.getLogin(), "Criar pedido");
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

                                                                logAction(Userlogado.getLogin(),
                                                                        "getMedicamentosByNomeParcial");
                                                                int index;
                                                                do {
                                                                    index = leDadosInt(
                                                                            "Qual e o numero do medicamento que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                                    if (index >= 0
                                                                            && index < medicamentos.size()) {
                                                                        listaUser.add(medicamentos.get(index));

                                                                        logAction(Userlogado.getLogin(),
                                                                                "medicamento adicionado a pedido");
                                                                    } else {
                                                                        System.out.println(
                                                                                "Medicamento nao encontrado");
                                                                        logAction(Userlogado.getLogin(),
                                                                                "medicamento nao encontrado");
                                                                    }
                                                                } while (index >= medicamentos.size());

                                                            }

                                                            else {

                                                                System.out.println(
                                                                        "Nao existe medicamentos com esse nome");

                                                                logAction(Userlogado.getLogin(),
                                                                        "erro getMedicamentosByNomeParcial, Nao existe medicamentos com esse nome");

                                                            }
                                                            continuar = leDadosInt(
                                                                    "Deseja adicionar outro medicamento?\n1-Sim\n2-Nao") == 1;
                                                            break;

                                                        case 2:
                                                            medicamentos = gerirMedicamento.getMedicamentos();

                                                            if (medicamentos.isEmpty()) {

                                                                System.out.println("Nao existem medicamentos");
                                                                logAction(Userlogado.getLogin(),
                                                                        "erro getMedicamentos, Nao existem medicamentos");
                                                            }
                                                            for (Medicamentos medicamento : medicamentos) {
                                                                System.out.println(
                                                                        medicamentos.indexOf(medicamento)
                                                                                + medicamento.toString());
                                                            }

                                                            logAction(Userlogado.getLogin(), "getmedicamentos");
                                                            int index;
                                                            do {
                                                                index = leDadosInt(
                                                                        "Qual e o numero do medicamento que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                                if (index >= 0 && index < medicamentos.size()) {
                                                                    if (listaUser
                                                                            .add(medicamentos.get(index))) {
                                                                        System.out.println(
                                                                                "Medicamento adicionado ao pedido");
                                                                        logAction(Userlogado.getLogin(),
                                                                                "Medicamento adicionado ao pedido");
                                                                    }
                                                                } else {
                                                                    System.out.println(
                                                                            "Numero invalido");
                                                                    logAction(Userlogado.getLogin(),
                                                                            "erro getMedicamento, numero invalido");

                                                                }
                                                            } while (index >= medicamentos.size());

                                                            continuar = leDadosInt(
                                                                    "Deseja adicionar outro medicamento?\n1-Sim\n2-Nao") == 1;

                                                            break;

                                                        default:
                                                            break;
                                                    }
                                                    break;

                                                case 2:
                                                    switch (leDadosInt(
                                                            "Deseja procurar manualmete ou listar todos os componentes?\n1-Manual\n2-Listar todos")) {
                                                        case 1:
                                                            ArrayList<ComponenteAtivo> componentesAtivos = gerirMedicamento
                                                                    .getComponentesAtivosByNomeParcial(leDados(
                                                                            "Introduza o nome do medicamento: "));
                                                            if (componentesAtivos.size() != 0) {
                                                                logAction(Userlogado.getLogin(),
                                                                        "getComponentesAtivosByNomeParcial");
                                                                for (ComponenteAtivo componente : componentesAtivos) {
                                                                    System.out.println(componentesAtivos
                                                                            .indexOf(componente) + "-"
                                                                            + componente);
                                                                }
                                                                int index;
                                                                do {
                                                                    index = leDadosInt(
                                                                            "Qual e o numero do componente que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                                    if (index >= 0
                                                                            && index < componentesAtivos
                                                                                    .size()) {
                                                                        listaUser.add(
                                                                                componentesAtivos.get(index));
                                                                        System.out.println(
                                                                                "Componente ativo adicionado ao pedido");
                                                                        logAction(Userlogado.getLogin(),
                                                                                "Componente ativo adicionado ao pedido");
                                                                    } else {
                                                                        System.out.println(
                                                                                "Numero invalido");
                                                                        logAction(Userlogado.getLogin(),
                                                                                "erro getComponente, numero invalido");
                                                                    }
                                                                } while (index >= componentesAtivos.size());

                                                            }

                                                            else {
                                                                System.out.println(
                                                                        "Nenhum componente encontrado");
                                                                logAction(Userlogado.getLogin(),
                                                                        "Nenhum componente encontrado");
                                                            }
                                                            continuar = leDadosInt(
                                                                    "Deseja adicionar outro elemento ao pedido?\n1-Sim\n2-Nao") == 1;
                                                            break;
                                                        case 2:
                                                            componentesAtivos = gerirMedicamento
                                                                    .getListaComponenteAtivos();

                                                            if (componentesAtivos.size() != 0) {
                                                                logAction(Userlogado.getLogin(),
                                                                        "getListaComponenteAtivos");
                                                                for (ComponenteAtivo componente : componentesAtivos) {
                                                                    System.out.println(
                                                                            componentesAtivos
                                                                                    .indexOf(componente) + "-"
                                                                                    + componente.toString());
                                                                }
                                                            } else {
                                                                System.out.println(
                                                                        "Nao existem componentes ativos");
                                                                logAction(Userlogado.getLogin(),
                                                                        "erro getListaComponenteAtivos, nao existem componentes ativos");
                                                            }
                                                            int index;
                                                            do {
                                                                index = leDadosInt(
                                                                        "Qual e o numero do componente que deseja ?\nIntroduza um numero negativo se deseja votar atras");
                                                                if (index >= 0
                                                                        && index < componentesAtivos.size()) {
                                                                    if (listaUser.add(
                                                                            componentesAtivos.get(index))) {
                                                                        System.out.println(
                                                                                "Componente ativo adicionado ao pedido");
                                                                        logAction(Userlogado.getLogin(),
                                                                                "componente ativo adicionado ao pedido");
                                                                    }

                                                                } else {
                                                                    System.out.println(
                                                                            "Numero invalido");
                                                                    logAction(Userlogado.getLogin(),
                                                                            "erro, numero invalido");

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
                                                "A encomenda e urgente?\n1-Sim\n2-Nao") == 1;

                                        Encomendas encomendaUser = new Encomendas(listaUser, descricao,
                                                urgente);

                                        if (gerirEncomendas.adicionarEncomenda((Cliente) Userlogado,
                                                encomendaUser)) {
                                            System.out.println("Encomenda adicionada com sucesso");
                                            logAction(Userlogado.getLogin(), "Encomenda adicionada");

                                        } else {
                                            System.out.println("Erro ao adicionar encomenda");
                                            logAction(Userlogado.getLogin(), "Erro ao adicionar encomenda");
                                        }

                                        break;
                                    case 2:
                                        if (AlterarDadosUser(Userlogado)) {
                                            System.out.println("Dados alterados com sucesso");
                                            logAction(Userlogado.getLogin(), "Alterou os dados da conta");
                                        } else {
                                            System.out.println("Falha na alteracao dos dados");
                                            logAction(Userlogado.getLogin(),
                                                    "Falha na alteracao dos dados da conta");

                                        }
                                        break;
                                    case 3:
                                        // consultar os seus servicos
                                        ArrayList<Encomendas> encomendas = gerirEncomendas
                                                .getEncomendasByCliente((Cliente) Userlogado);
                                        if (encomendas.size() > 0 || encomendas .isEmpty()) {
                                            for (Encomendas e : encomendas) {
                                                System.err.println(encomendas.indexOf(e) + e.toString());
                                            }
                                            logAction(Userlogado.getLogin(), "getEncomendasByCliente");
                                        } else {
                                            System.out.println("Nao tem encomendas");
                                            logAction(Userlogado.getLogin(),
                                                    "getEncomendasByCliente, nao tem encomendas");
                                        }

                                    default:
                                        System.out.println("Opcao invalida");
                                        logAction(Userlogado.getLogin(), "Opcao Invalida");

                                        break;
                                }
                            }
                        }
                        if (Userlogado instanceof Farmaceutico) {
                            System.out.println("farmaceutico");
                            while (opLogado > 0) {
                                opLogado = leDadosInt(
                                        "1-Gerir encomendas\n2-Alterar dados da Conta\n3-Introduzir medicamento\n4-Introduzir categoria\n5-Introduzir excipiente\n6-Introduzir componente ativa\n7-Modificar stock de medicamento existente\n0-Encerrar sessao");

                                switch (opLogado) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());
                                        logAction(Userlogado.getLogin(), "Encerrar sessao");
                                        break;

                                    case 1:
                                        switch (leDadosInt(
                                                "1-Iniciar encomenda \n2-Conluir encomenda\n3-Consultar todas as encomendas associadas a si\n4-Adicionar subtarefa a uma encomenda iniciada")) {
                                            case 1:
                                                HashMap<Cliente, ArrayList<Encomendas>> encomendas = gerirEncomendas
                                                        .getEncomendasByStatus(2,
                                                                gerirEncomendas.getEncomendasByFarmaceutico(
                                                                        (Farmaceutico) Userlogado));
                                                if (encomendas .isEmpty()) {
                                                    System.out.println("Nao exitem encomendas para iniciar");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(2), nao exitem encomendas para iniciar");
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
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(2)");

                                                    if (gerirEncomendas
                                                            .processarEncomenda(
                                                                    gerirEncomendas.getEncomendaById(
                                                                            leDadosInt(
                                                                                    "Introduza o id da encomenda a iniciar")))) {
                                                        System.out.println("Encomenda iniciada com sucesso");
                                                        logAction(Userlogado.getLogin(),
                                                                "Encomenda iniciada com sucesso");
                                                    } else {
                                                        System.out.println("Erro ao iniciar encomenda");
                                                        logAction(Userlogado.getLogin(),
                                                                "Erro ao iniciar encomenda");
                                                    }
                                                }
                                                break;
                                            case 2:
                                                HashMap<Cliente, ArrayList<Encomendas>> encomendasPorConcluir = gerirEncomendas
                                                        .getEncomendasByStatus(3,
                                                                gerirEncomendas.getEncomendasByFarmaceutico(
                                                                        (Farmaceutico) Userlogado));
                                                if (encomendasPorConcluir.isEmpty()) {
                                                    System.out.println("Nao exitem encomendas por concluir");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(3), nao exitem encomendas por concluir");
                                                } else {
                                                    // print hashmap encomendas
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(3)");
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
                                                            leDadosFloat("Introduza o valor total do pedido"));

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
                                                                                + "-" + medicamento.getNome());
                                                            }
                                                            int escolha = leDadosInt(
                                                                    "Introduza o numero do medicamento escolhido: ");
                                                            if (escolha >= 0
                                                                    && escolha < medicamentos.size()) {
                                                                novosItens.add(medicamentos.get(escolha));
                                                            }
                                                        } else {
                                                            novosItens.add(item);
                                                        }
                                                    }

                                                    encomenda.setLista(novosItens);
                                                    System.err.println(
                                                            "Todos os componente ativos foram substituidos");
                                                    logAction(Userlogado.getLogin(),
                                                            "Substituir componentes ativas por medicamentos");
                                                    if (gerirEncomendas
                                                            .processarEncomenda(encomenda)) {
                                                        System.out.println("Encomenda processada com sucesso");
                                                        logAction(Userlogado.getLogin(),
                                                                "Encomenda processada com sucesso");
                                                    } else {
                                                        System.out.println("Erro ao processar encomenda");
                                                        logAction(Userlogado.getLogin(),
                                                                "Erro ao processar encomenda");

                                                    }

                                                }

                                                break;
                                            case 3:
                                                HashMap<Cliente, ArrayList<Encomendas>> encomendasFarmaceutico = gerirEncomendas
                                                        .getEncomendasByFarmaceutico((Farmaceutico) Userlogado);
                                                if (encomendasFarmaceutico .isEmpty()) {
                                                    System.out.println("Nao exitem encomendas associadas a si");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByFarmaceutico, nao exitem encomendas associadas a si");
                                                } else {
                                                    // print hashmap encomendas
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByFarmaceutico()");
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
                                                break;
                                            case 4:
                                                HashMap<Cliente, ArrayList<Encomendas>> encomendasIniciadas = gerirEncomendas
                                                        .getEncomendasByStatus(3,
                                                                gerirEncomendas.getEncomendasByFarmaceutico(
                                                                        (Farmaceutico) Userlogado));
                                                if (encomendasIniciadas .isEmpty()) {
                                                    System.out.println(
                                                            "Nao exitem encomendas a decorrer associadas a si");
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(getEncomendasByFarmaceutico), nao exitem encomendas associadas a si");

                                                } else {
                                                    // print hashmap encomendas
                                                    logAction(Userlogado.getLogin(),
                                                            "getEncomendasByStatus(getEncomendasByFarmaceutico)");
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
                                                    if (encomenda != null) {
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
                                                    } else {
                                                        System.out.println("Encomenda nao existe");
                                                        logAction(Userlogado.getLogin(),
                                                                "getEncomendaById, encomenda nao existe");

                                                    }

                                                }
                                                break;

                                            default:
                                                break;
                                        }

                                        break;
                                    case 2:
                                        if (AlterarDadosUser(Userlogado)) {
                                            System.out.println("Dados alterados com sucesso");
                                            logAction(Userlogado.getLogin(),
                                                    "AlterarDadosUser, dados alterados com sucesso");
                                        } else {
                                            System.out.println("Falha na alteracao dos dados");
                                            logAction(Userlogado.getLogin(),
                                                    "AlterarDadosUser, falha na alteracao dos dados");

                                        }

                                        break;
                                    case 3:
                                        logAction(Userlogado.getLogin(), "Criar medicamento");
                                        String nome = leDados("Introduza o nome do medicamento");
                                        String marca = leDados("Introduza a marca do medicamento: ");
                                        String lote = leDados("Introduza o lote do medicamento: ");
                                        String dosagem = leDados("Introduza a dosagem do medicamento: ");
                                        int stock = leDadosInt("Introduza o stock do medicamento: ");
                                        float preco = leDadosFloat("Introduza o preco do medicamento: ");
                                        int anoFabrico = leDadosInt("Introduza o ano de fabrico do medicamento: ");
                                        boolean medicoNecessario = leDadosInt(
                                                "Introduza se o medicamento necessita receita medica?\n1-Sim\n2-Nao") == 1;
                                        boolean generico = leDadosInt("generico? 1-Sim\n2-Nao") == 1;

                                        HashMap<Integer, Categoria> categorias = gerirMedicamento.getListaCategorias();
                                        HashMap<Integer, Categoria> categoriasMedicamento = new HashMap<Integer, Categoria>();

                                        if (categorias.size() > 0) {

                                            // print hashmap
                                            int n;
                                            do {
                                                n = leDadosInt(
                                                        "Introduza o numero de categorias do medicamento(max:3)");
                                                if (n > 3 || n < 1) {
                                                    System.out.println("Entrada invalida");

                                                }

                                            } while (n > 3 || n < 1);
                                            for (Map.Entry<Integer, Categoria> entry : categorias.entrySet()) {
                                                Integer key = entry.getKey();
                                                Categoria categoria = entry.getValue();
                                                System.err.println(key);
                                                System.out.println(categoria);
                                            }

                                            for (int i = 0; i < n; i++) {
                                                int codigoCategoria = leDadosInt("Introduza o codigo da categoria: ");
                                                Categoria categoria = gerirMedicamento
                                                        .getCategoriaById(codigoCategoria);
                                                if (categoria != null) {
                                                    categoriasMedicamento.put(codigoCategoria, categoria);
                                                    System.out
                                                            .println("Categoria adicionada com sucesso ao medicamento");
                                                    logAction(Userlogado.getLogin(), "categoriasMedicamento.put");
                                                } else {
                                                    System.out.println("Erro ao adicionar categoria ao medicamento");
                                                    logAction(Userlogado.getLogin(), "Erro categoriasMedicamento.put ");
                                                    i--;
                                                }
                                            }
                                        } else {
                                            System.out.println("Nao existem categorias");
                                            logAction(Userlogado.getLogin(), "Nao existem categorias");

                                        }
                                        ArrayList<Excipiente> excipientes = gerirMedicamento.getListaExcipientes();
                                        int n;

                                        do {
                                            n = leDadosInt("Introduza o numero de excipientes do medicamento(max:5)");
                                            if (n > 5 || n < 1) {
                                                System.out.println("Entrada invalida");

                                            }

                                        } while (n > 5 || n < 1);

                                        if (!excipientes.isEmpty()) {
                                            for (Excipiente excipiente : excipientes) {
                                                System.out.println(
                                                        excipientes.indexOf(excipiente) + "-" + excipiente.toString());
                                            }

                                            ArrayList<Excipiente> excipienteMedicamento = new ArrayList<Excipiente>();

                                            for (int i = 0; i < n; i++) {
                                                int indexExcipiente = leDadosInt("Introduza o index do excipiente: ");
                                                Excipiente excipiente = gerirMedicamento
                                                        .getExcipienteById(indexExcipiente);
                                                if (excipiente != null) {
                                                    excipienteMedicamento.add(excipiente);
                                                    System.out.println(
                                                            "Excipiente adicionado com sucesso ao medicamento");
                                                    logAction(Userlogado.getLogin(), "exipienteMedicamento.add");
                                                } else {
                                                    System.out.println("Erro ao adicionar excipiente ao medicamento");
                                                    logAction(Userlogado.getLogin(), "Erro exipienteMedicamento.add ");
                                                    i--;
                                                }
                                            }
                                        } else {
                                            System.out.println("Nao existem excipientes para adicionar");
                                            logAction(Userlogado.getLogin(), "Nao existem excipientes para adicionar");

                                        }

                                        ArrayList<ComponenteAtivo> componentesAtivos = gerirMedicamento
                                                .getListaComponenteAtivos();
                                        ComponenteAtivo componenteAtivoMedicamento = null;

                                        if (!componentesAtivos.isEmpty()) {
                                            for (ComponenteAtivo componenteAtivo : componentesAtivos) {
                                                System.out.println(componentesAtivos.indexOf(componenteAtivo) + "-"
                                                        + componenteAtivo.toString());
                                            }

                                            do {
                                                n = leDadosInt("Introduza o numero do componente ativo");
                                                if (n >= 0 && n < componentesAtivos.size()) {
                                                    componenteAtivoMedicamento = componentesAtivos.get(n);
                                                } else {
                                                    System.out.println("Entrada invalida");
                                                }

                                            } while (n < 0 || n >= componentesAtivos.size());
                                        } else {
                                            System.out.println("Nao existem componentes ativos para adicionar");
                                            logAction(Userlogado.getLogin(),
                                                    "getListaComponenteAtivos, Nao existem componentes ativos para adicionar");
                                        }

                                        if (gerirMedicamento.CriarMedicamento(nome, marca, lote,
                                                componenteAtivoMedicamento, dosagem, stock, preco, anoFabrico,
                                                medicoNecessario, excipientes, categoriasMedicamento, generico)) {

                                            System.out.println("Medicamento criado com sucesso");
                                            logAction(Userlogado.getLogin(), "CriarMedicamento");
                                        } else {
                                            System.out.println("Erro na criacao do medicamento");
                                            logAction(Userlogado.getLogin(),"erro CriarMedicamento" );
                                        }
                                        break;
                                    case 4:
                                        while (true) {
                                            if (gerirMedicamento.criarCategoria(
                                                    leDados("Introduza a designacao da nova categoria: "),
                                                    leDados("Introduza a classificacao: "),
                                                    leDadosInt("introduza o codigo: "),
                                                    leDados("introduza o fornecedor: "))) {
                                                System.out.println("Categoria criada com sucesso ");
                                                logAction(Userlogado.getLogin(), "criarCategoria");
                                                break;
                                            } else {
                                                System.out.println(
                                                        "Erro na criacao da categoria, verifique se o codigo ja esta a ser utilizado");
                                                logAction(Userlogado.getLogin(), "erro criarCategoria");

                                            }

                                        }

                                        break;

                                    case 5:
                                        // String aDesignacao, String aClassificacao, int aQuantidade
                                        while (true) {
                                            if (gerirMedicamento.criarExcipiente(
                                                    leDados("Introduza o nome do excipiente: "),
                                                    leDados("Introduza a o codigo: "),
                                                    leDadosInt("Introduza a quantidade padrao: "))) {
                                                System.out.println("Excipiente criado com sucesso ");
                                                logAction(Userlogado.getLogin(), "criarExcipiente");
                                                break;
                                            } else {
                                                System.out.println(
                                                        "Erro na criacao do excipiente");
                                                logAction(Userlogado.getLogin(), "Erro criarExcipiente");

                                            }
                                        }

                                        break;

                                    case 6:
                                        while (true) {
                                            if (gerirMedicamento.criarComponenteAtivo(
                                                    leDados("Introduza o nome da componente ativa: "),
                                                    leDadosInt("Introduza o codigo: "),
                                                    leDadosInt("Introduza a quantidade padrao: "))) {
                                                System.out.println("Excipiente criado com sucesso ");
                                                logAction(Userlogado.getLogin(), "criarComponenteAtiva");
                                                break;
                                            } else {
                                                System.out.println(
                                                        "Erro na criacao do excipiente");
                                                logAction(Userlogado.getLogin(), "Erro criarComponenteAtiva");
                                            }
                                        }

                                        break;

                                    case 7:
                                        ArrayList<Medicamentos> medicamentos = gerirMedicamento
                                                .getMedicamentosByNomeParcial(
                                                        leDados("Introduza o nome do medicamento"));
                                        if (medicamentos.isEmpty()) {
                                            System.out.println("Medicamento inexistente");
                                            logAction(Userlogado.getLogin(),
                                                    "Erro getMedicamentosByNomeParcial, getMedicamentosByNomeParcial");

                                        } else {
                                            for (Medicamentos medicamento : medicamentos) {

                                                System.out.println(medicamentos.indexOf(medicamento) + "-"
                                                        + medicamento.toString());

                                            }
                                            logAction(Userlogado.getLogin(), "getMedicamentosByNomeParcial");
                                            int index;
                                            do {
                                                index = leDadosInt(
                                                        "Qual e o numero do medicamento que deseja ?\nIntroduza um numero negativo se deseja votar atras");

                                                if (index >= 0
                                                        && index < medicamentos.size()) {
                                                    stock= leDadosInt("Introduza o novo stock do medicamento");
                                                   if (medicamentos.get(index).setStock(stock)){
                                                    System.out.println("Stock alterado com sucesso");
                                                    logAction(Userlogado.getLogin(), "setStock");
                                                   }
                                                   else{
                                                    System.out.println("Erro ao alterar stock");
                                                    logAction(Userlogado.getLogin(), "Erro setStock");
                                                   }

                                                } else {
                                                    System.out.println(
                                                            "getMedicamentosByNomeParcial, Medicamento nao encontrado");
                                                    logAction(Userlogado.getLogin(),
                                                            "getMedicamentosByNomeParcial, medicamento nao encontrado");
                                                }
                                            } while (index >= medicamentos.size());




                                        }

                                    default:
                                        break;
                                }
                            }
                        }

                    } else {
                        System.out.println("Login ou password errados!");
                        logAction("Sistema", "Erro login, Login ou password errados");
                    }

                    break;

                case 2:
                    int tipo = leDadosInt(
                            "Qual e o tipo de conta que gostaria de criar?\n1-Farmaceutico\n2-Cliente\n3-Gestor");

                    switch (tipo) {
                        case 1:
                            while (true) {
                                if (gerirUser.criarFarmaceutico(leDados("Introduza o seu username: "),
                                        leDados("Introduza a sua password: "), leDados("Introduza o seu nome: "),
                                        leDados("Introduza o seu email: "), false, leDados("Introduza o seu NIF: "),
                                        leDados("Introduza a sua morada: "),
                                        leDados("Introduza o seu contato: "))) {
                                    System.out.println("Farmaceutico criado com sucesso!");

                                    logAction("Sistema", "Criar Farmaceutico");
                                    break;
                                } else {
                                    System.out.println(
                                            "O login/Username/email/NIF/Contato invalidos!");
                                    logAction("Sistema", "Erro criar Farmaceutico");

                                }

                            }

                            break;
                        case 2:
                            while (true) {

                                if (gerirUser.criarCliente(leDados("Introduza o seu username: "),
                                        leDados("Introduza a sua password: "), leDados("Introduza o seu nome: "),
                                        leDados("Introduza o seu email: "), false, leDados("Introduza o seu NIF: "),
                                        leDados("Introduza a sua morada: "),
                                        leDados("Introduza o seu contato: "))) {
                                    System.out.println("Cliente criado com sucesso!");
                                    logAction("Sistema", "Criar Cliente");
                                    break;
                                } else {
                                    System.out.println(
                                            "O login/Username/email/NIF/Contato invalidos!");
                                    logAction("Sistema", "Erro criar Cliente");
                                }
                            }
                            break;
                        case 3:
                            while (true) {

                                if (gerirUser.criarGestor(leDados("Introduza o seu username: "),
                                        leDados("Introduza a sua password: "), leDados("Introduza o seu nome: "),
                                        leDados("Introduza o seu email: "), false)) {
                                    System.out.println("Gestor criado com sucesso!");
                                    logAction("Sistema", "Criar Gestor");
                                    break;
                                } else {
                                    System.out.println("O login/Username/email invalidos");
                                    logAction("Sistema", "Erro criar Gestor");
                                }
                                break;
                            }
                            break;
                        default:
                            System.out.println("Tipo de Conta invalida!");
                            logAction("Sistema", "Erro criar Conta, numero invalido");
                            break;
                    }
                    break;
                case 0:
                    System.out.println("Adeus");
                    logAction("Sistema", "Sair");

                    saveData(gerirUser, gerirEncomendas, gerirMedicamento);
                    System.exit(0);

                    break;
                default:
                    System.out.println("Opcao invalida");
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
                    // Se o ficheiro nao existe, cria
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
                System.out.println("Opcao invalida");
                return false; // Adicionado para indicar que a operacao falhou
        }
        return true; // Adicionado para indicar que a operacao foi bem-sucedida
    }

}
