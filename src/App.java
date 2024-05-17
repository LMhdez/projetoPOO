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

        boolean utilizadoresCriados = false;
        GerirUser gerirUser = new GerirUser();
        utilizadoresCriados = gerirUser.criarGestor("sim", "123", "manito", "fghj", true);
        User Userlogado = gerirUser.logar("sim", "123");

        if (!utilizadoresCriados) {
            while (!utilizadoresCriados) {
                login = leDados("Introduza o seu username: ");
                password = leDados("Introduza a sua password: ");
                nome = leDados("Introduza o seu nome: ");
                email = leDados("Introduza o seu email: ");
                ativo = true;

                if (gerirUser.criarGestor(login, password, nome, email, ativo)) {
                    System.out.println("Gestor criado com sucesso!");
                    utilizadoresCriados = true;
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

                    Userlogado = gerirUser.logar(login, password);

                    if (Userlogado != null) {
                        System.out.println("Bem-vindo " + login);
                        if (Userlogado instanceof Gestor) {
                            System.out.println("gestor");
                            while (op != 0) {
                                op = leDadosInt(
                                        "1- Gerir Registos \n2- Gerir Pedidos de Servicos\n3- Historico de Servicos\n0-Encerrar sessao");
                                switch (op) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());
                                        break;
                                    case 1:
                                        System.out.println(gerirUser.GetPedidosdeRegisto());
                                        int pedido = leDadosInt("Indique o Pedido que deseja aprovar: ");
                                        if (pedido > gerirUser.GetPedidosdeRegisto().size() || pedido < 1) {
                                            System.out.println("Pedido inexistente");
                                        } else {
                                            gerirUser.ativarUser(pedido);
                                        }
                                        break;
                                    case 2:
                                        // Handle "Gerir Pedidos de Servicos"
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
                            while (op != 0) {
                                op = leDadosInt("1- Solicitar pedido de Servico \n0-Encerrar sessao");
                                switch (op) {
                                    case 0:
                                        System.out.println("Adeus " + Userlogado.getNome());
                                        break;
                                    case 1:
                                        // Handle "Solicitar pedido de Servico"
                                        break;
                                    default:
                                        System.out.println("Opção inválida");
                                        break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Login ou password errados!");
                    }
                    break;
                case 2:
                    int tipo = leDadosInt("Qual é o tipo de conta que gostaria de criar?\n1-Farmaceutico\n2-Cliente\n3-Gestor\n");
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
                            if (gerirUser.criarFarmaceutico(login, password, nome, email, ativo, NIF, morada, contato)) {
                                System.out.println("Farmaceutico criado com sucesso!");
                            } else {
                                System.out.println("O login/Username/email/NIF/Contato já estão associados a outra conta!");
                            }
                            break;
                        case 2:
                            if (gerirUser.criarCliente(login, password, nome, email, ativo, NIF, morada, contato)) {
                                System.out.println("Cliente criado com sucesso!");
                            } else {
                                System.out.println("O login/Username/email/NIF/Contato já estão associados a outra conta!");
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
    }

    private static String leDados(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        return teclado.nextLine();
    }

    private static int leDadosInt(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        return teclado.nextInt();
    }
}
