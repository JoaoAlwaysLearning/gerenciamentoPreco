/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loo_trabalho;

import java.util.Scanner;

/**
 *
 * @author JOAO VITOR
 */

class usuario{
        
    //Atributos
    private String nome;
    private String sobrenome;
    private int CPF;
    private String senha;
    private double saldo;
        
    //Get
    public String getNome(){
        return nome;}
    public String getSobrenome(){
        return sobrenome;}
    public int getCPF(){
        return CPF;}
    public String getSenha(){
        return senha;}
    public double getSaldo(){
        return saldo;}
        
    //Set
    public void setNome(String n){
        nome = n;}
    public void setSobrenome(String s){
        sobrenome = s;}
    public void setCPF(int cpf){
        CPF = cpf;}
    public void setSenha(String p){
        senha = p;}
    public void setSaldo(double sal){
        saldo = sal;}
}

//main
public class LOO_Trabalho {
    
    public static void main(String[] args) {
        
        usuario Joao = new usuario();
        Joao.setNome("Jao");
        Joao.setSobrenome("Vitor");
        Joao.setCPF(000-111-222-33);
        Joao.setSenha("12345");
        Joao.setSaldo(0);
        
        //Controla quando parar ou não o while
        boolean loop = true; 
        
        //Inicio da execução do programa
        while (loop){
            Scanner myObj = new Scanner(System.in);
            
            //Decisão
            System.out.println("O que deseja? Consultar o saldo (1), depositar (2), retirar dinheiro (3), finalizar o programa (4)");
           
            String resposta = myObj.nextLine();
            
            switch(resposta) {
                case "1":
                    
                    //Consulta
                    
                    System.out.println("Saldo: " + Joao.getSaldo());
                    break;
                    
                case "2":
                    
                    //Deposito
                    
                    System.out.println("Insira o valor que quer depositar: ");
                    double deposito = myObj.nextDouble();
                    Joao.setSaldo(Joao.getSaldo() + deposito);
                  
                    System.out.println("Deposito realizado com sucesso!!!");
                    break;
                    
                case "3":
                    
                    //Saque
                    double saque = myObj.nextDouble();
                    System.out.println("Insira o valor que quer sacar: ");
                    
                    //Verificar se o valor que o usuario quer sacar é menor ou igual ao que ele possui de saldo
                    if (saque <= Joao.getSaldo()){
                        Joao.setSaldo(Joao.getSaldo() - saque);
                    }
                    else{
                        System.out.println("Erro, você não possui saldo suficiente");
                    }
                    break;
                case "4":
                    //Finaliza o programa
                    loop = false;
            }
        }
 
        System.out.println("Muito obrigado por utilizar o programa :)");
    }
}
