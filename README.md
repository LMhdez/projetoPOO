# Projeto POO — Gestão de Farmácia

Aplicação de consola em Java desenvolvida no âmbito de Programação Orientada a Objetos. O código está organizado em classes que representam utilizadores, gestores, clientes, farmacêuticos, medicamentos, componentes, encomendas e operações de gestão.

## Funcionalidades identificadas

- Autenticação e criação de contas.
- Criação do gestor inicial quando ainda não existem utilizadores.
- Gestão de utilizadores e pedidos de registo.
- Gestão de medicamentos e encomendas.
- Persistência de dados em ficheiros e registo de atividade.
- Informação de utilização do sistema.

## Estrutura

- `src/` — código-fonte Java.
- `bin/` — ficheiros compilados.
- `.vscode/` — configuração do ambiente Visual Studio Code.
- Ficheiros `.dat` e `.txt` — dados e registos locais da aplicação.

## Compilar e executar

A aplicação usa Java. A partir da raiz, podes compilar as classes-fonte para uma pasta de saída:

```bash
mkdir -p out
javac -d out src/*.java
java -cp out App
```

Confirma a versão de Java e os caminhos de dados antes de executar. A aplicação pode criar ou alterar ficheiros locais de dados e logs.

## Aviso de segurança — revisão necessária

Foi identificado na raiz do repositório um ficheiro denominado `credenciais_acesso.txt`. O conteúdo não foi aberto nem reproduzido nesta auditoria. **Se contiver credenciais reais, considera-as expostas**: revoga/roda imediatamente essas credenciais, remove o ficheiro do branch atual e avalia a remoção do histórico Git. Apagar apenas o ficheiro num commit novo não elimina cópias anteriores do histórico.

Não guardar palavras-passe, dados pessoais ou credenciais reais no repositório. Os ficheiros de dados e logs também devem ser revistos antes de partilhar o projeto publicamente.