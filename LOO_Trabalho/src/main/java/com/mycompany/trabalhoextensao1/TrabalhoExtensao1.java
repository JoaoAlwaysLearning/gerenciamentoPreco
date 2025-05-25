/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.trabalhoextensao1;

import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author JOAO VITOR
 */

/*
            try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
            var pstatment = conn.prepareStatement(sql)){
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        //Verifica se esta conectado
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

            //BACKUP

            
        //Insira elementos
        var sqlInsert = "INSERT INTO produtos(codigoBarra,nomeProduto,precoProduto) VALUES(?,?,?)";
        
        //Select
        var sqlSelect = "SELECT codigoBarra, nomeProduto, precoProduto FROM produtos";
        
        //Server inicia
        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql); 
             var pstmt = conn.prepareStatement(sqlInsert);
             var pstmtSelect = conn.createStatement()) {
            
            //Verifica se esta conectado
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            
            //Executa comando, nesse caso, cria uma tabela
            stmt.executeUpdate();
            
            //Insere itens na tabela
            pstmt.setInt(1,inserirCodigo);
            pstmt.setString(2,inserirNome);
            pstmt.setDouble(3,inserirPreco);
            
            //Executa Inserir
            pstmt.executeUpdate();
            System.out.println("Atualizado com sucesso");
            
            //Select itens
            var select = pstmtSelect.executeQuery(sqlSelect);
            
            System.out.println(select.getInt("codigoBarra"));
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
          
        System.out.println("Hello World!");


*/

public class TrabalhoExtensao1 {
    
    //CRIAR TABELA
    public void criaTabela(){
            
             //SQL nova tabela, somente se ela ja não existe
            var sql = "CREATE TABLE IF NOT EXISTS produtos ("
                + "	codigoBarra INTEGER PRIMARY KEY,"
                + "	nomeProduto TEXT NOT NULL,"
                + "	precoTotal REAL NOT NULL,"
                + "     quantidade INTEGER NOT NULL,"
                + "     precoVenda REAL NOT NULL"
                + ");";
            
            //Servidor cria
            try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
                var pstatment = conn.prepareStatement(sql)){
                
                //Cria tabela
                pstatment.executeUpdate();
                
            }catch (SQLException e) {
                System.err.println(e.getMessage());
        }
        }
    
    //INSERIR PRODUTO
    public void insereProduto(int cod, String nome, double precoT, int qnt, double precoV){
        
        //Comando a ser inserido no SQL
        var sqlInsert = "INSERT INTO produtos(codigoBarra, nomeProduto, precoTotal, quantidade, precoVenda) VALUES(?,?,?,?,?)";
        
        //Server inicia para executar o comando
        try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
            var pstatmentIn = conn.prepareStatement(sqlInsert)){
            
            //Insere itens/prepara para inseri-los
            pstatmentIn.setInt(1, cod);
            pstatmentIn.setString(2, nome);
            pstatmentIn.setDouble(3, precoT);
            pstatmentIn.setInt(4, qnt);
            pstatmentIn.setDouble(5, precoV);
            
            //Executa o comando
            pstatmentIn.executeUpdate();
            System.out.println("Itens inseridos com sucesso");
            
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    //CHECAR SE O PRODUTO JA FOI CADASTRADO
    public static boolean cadastroExiste(int chavePrimaria){
        
        //Valor que será retornado
        boolean cadExiste = false;
        
        //Comando SQL
        String query = "SELECT 1 FROM produtos WHERE codigoBarra = ?"; // '?' ao invés de '+ chavePrimaria+' para evitar SQL injection
        
        //Executar comando no servidor
        try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
            var pstatmentQ = conn.prepareStatement(query)){
            
            //Coloca a chave primaria no lugar do '?'
            pstatmentQ.setInt(1, chavePrimaria);
            
            //Executa e salva o resultado
            
            //PROBLEMA AQUI, RETORNA 1 ou 0 o que fazer?
            var result = pstatmentQ.executeQuery();
            cadExiste = result.next();
            System.out.println(cadExiste);
            
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cadExiste;
    }
    
    //SELECIONADOR PRECO TOTAL
    public static double selecionador(int chavePrimaria, String nomeColuna){
        
        //Valor a ser retornado
        double valor = 0;
        
        //System.out.println(nomeColuna);
        
        //Comandos SQL
        String queryS = "";
        
        switch (nomeColuna){
            case "precoTotal" -> {
                queryS = "SELECT precoTotal FROM produtos WHERE codigoBarra = ?";
                //System.out.println("1");
            }
            case "precoVenda" -> {
                queryS = "SELECT precoVenda FROM produtos WHERE codigoBarra = ?";
                //System.out.println("2");
            }
            case "quantidade" -> {
                queryS = "SELECT quantidade FROM produtos WHERE codigoBarra = ?";
                //System.out.println("3");
            }
        }   
        
        //Executar comando no servidor
        try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
            var pstatmentQS = conn.prepareStatement(queryS)){
            
            //Define a coluna e o ID
            //pstatmentQS.setString(1, nomeColuna);
            pstatmentQS.setInt(1, chavePrimaria);
            
            //Executa e salva o resultado
            var resultado = pstatmentQS.executeQuery();
            resultado.next();
            valor = resultado.getDouble(nomeColuna);
            
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }    
    return valor;
    }
    
    //ATUALIZA OS DADOS DA LINHA
    public void atualizarDados(int chavePrimaria, double precoTo, double precoVen){
        
        //Comando a ser executado
        String sqlAtt = "UPDATE produtos SET precoTotal = ?, precoVenda = ? WHERE codigoBarra = ?";
        
        //Executar comando no SQL
        try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
            var pstatmentAtt = conn.prepareStatement(sqlAtt)){
            
            //Prepara as atualizações a ocorrerem
            pstatmentAtt.setDouble(1, precoTo);
            pstatmentAtt.setDouble(2, precoVen);
            pstatmentAtt.setInt(3, chavePrimaria);
            
            //Executa o comando
            pstatmentAtt.executeUpdate();
            
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /* DESATIVADO
    //SELECIONADOR QUANTIDADE
    public static int selecionadorQnt(String nomeTabela, int chavePrimaria, String nomeColuna){
        
        //Valor a ser retornado
        int valorQnt = 0;
        
        //Comando SQL
        String querySI = "SELECT ? FROM" + nomeTabela + "WHERE id = ?";
        
        //Executar comando no servidor
        try(var conn = DriverManager.getConnection("jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db");
            var pstatmentQI = conn.prepareStatement(querySI)){
            
            //Define a coluna e o ID
            pstatmentQI.setString(1, nomeColuna);
            pstatmentQI.setInt(2, chavePrimaria);
            
            //Executa e salva o resultado
            var resultado = pstatmentQI.executeQuery();
            resultado.next();
            valorQnt = resultado.getDouble(nomeColuna);
            
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }    
    return valorQnt;
    }
    */
    
    public static void main(String[] args) {
        
        //Caminho da base de dados
        var url = "jdbc:sqlite:\\C:\\Users\\JOAO VITOR\\sqlite\\db\\chinook.db";
        
        //Inputs incio
        Scanner myObj = new Scanner(System.in);
        
        //Criar tabela
        TrabalhoExtensao1 sqlite = new TrabalhoExtensao1();
        sqlite.criaTabela();
        
        //Inserir produtos
        TrabalhoExtensao1 sqlite1 = new TrabalhoExtensao1();  
        
        //Atualizar produtos
        TrabalhoExtensao1 sqlite2 = new TrabalhoExtensao1();
        
        //Variaveis
        double precoInformado = 0;
        double inserirPrecoT = 0;
        
        //Execução do programa
        while(true){
            
            //Código do produto
            System.out.println("Insira o codigo do Produto");
            int inserirCodigo = myObj.nextInt();
            
            //Verifica se o codigo ja foi cadastrado
            var verificar = cadastroExiste(inserirCodigo);
            
            //Compara
            if(verificar == false){
                
                //Cadastro
                
                //Nome do produto
                System.out.println("Insira o nome do Produto");
                String tank = myObj.nextLine();
                String inserirNome = myObj.nextLine();
        
                //Preço total
                System.out.println("Insira o preco total (Preco do fardo ou caixa)");
                inserirPrecoT = myObj.nextDouble();
                
                //Quantidade
                System.out.println("Insira a quantidade do Produto (que vem no fardo ou caixa)");
                int inserirQuantidade = myObj.nextInt();
                
                //Preço de Venda
                double precoCusta = inserirPrecoT/inserirQuantidade;
                double recomendacao = precoCusta/0.8;
                System.out.println("Insira o preco de venda do produto, dica o preco recomendado é: " + recomendacao);
                double inserirPrecoV = myObj.nextDouble();
                
                //Chama função
                sqlite1.insereProduto(inserirCodigo, inserirNome, inserirPrecoT, inserirQuantidade, inserirPrecoV);
                
                //Preço total atual informado
                precoInformado = inserirPrecoT;
            }
            else{
                //Preço total
                System.out.println("Insira o preco total (Preco do fardo ou caixa)");
                inserirPrecoT = myObj.nextDouble();
                precoInformado = inserirPrecoT;
            
            }    
            
            //Comparar preço para ver se mudou
            double precoComparar = selecionador(inserirCodigo,"precoTotal");
            //System.out.println(precoComparar);
            
            //Preço para usar na atualização, nesse caso é o antigo, para caso ele se mantenha
            double inserirPrecoVendaAtt = selecionador(inserirCodigo,"precoVenda");
            
            //SE MUDOU O PREÇO
            if (precoInformado != precoComparar && verificar == true){
                double quantidadeAtt = selecionador(inserirCodigo,"quantidade");
                //System.out.println(quantidadeAtt);
                double precoCustoAtt = inserirPrecoT/quantidadeAtt;
                //System.out.println(precoCustoAtt);
               
                double recomendacaoAtt = precoCustoAtt/0.8;
                System.out.println("Insira o novo preco de venda do produto, dica o preco recomendado é: " + recomendacaoAtt);
                inserirPrecoVendaAtt = myObj.nextDouble();
            }
            
            sqlite2.atualizarDados(inserirCodigo, precoInformado, inserirPrecoVendaAtt);
            
            //Encerra o loop
            System.out.println("Pressione 'x' para finalizar o programa, caso contrario aperte 'Enter'");
            String tank1 = myObj.nextLine();
            String finalizarPrograma = myObj.nextLine();
            if ("x".equals(finalizarPrograma)){
                break;
            }
        }
        //Fim do programa
        System.out.println("Obrigado por utilizar o programa!");

    }
}
