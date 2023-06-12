/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3erp;

/**
 *
 * @author crisb
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;


public class TrapezoidalRuleRMIImpl extends UnicastRemoteObject implements TrapezoidalRuleRMI {
    public List<TrapezoidalRuleRMI> clientes;
    public List<String> aux;
    int contador=0;
    public double resultado=0;
    public double aux1 = 0;
    public double A=0;
    public double B=0;
    public double N=0;
    public int NumThreads=0;
    public String Tipo="";
    public boolean Flag = true;
    public boolean Flag2 = true;
    public Holder holder;
    
    public TrapezoidalRuleRMIImpl() throws RemoteException {
        clientes = new ArrayList<>();
    }

    @Override
    public void agregar(TrapezoidalRuleRMI cliente)throws RemoteException{
        System.out.println(cliente);
        clientes.add(cliente);
        System.out.println(clientes.size());
        
    }
    
    @Override 
    public int valor(){
        return clientes.size();
    }
    
    @Override 
    public void enviar(double a, double b, double n, String tipo, int numThreads){
       A=a;
       B=b;
       N=n;
       Tipo=tipo;
       NumThreads=numThreads;
       holder = new Holder(A, B, N, Tipo, NumThreads);
       Flag=false;
    }
    
    @Override
    public Holder aux(){
       if(Flag == false) return holder;
       else return null;
    }
    
    @Override 
    public void recibir(double res){
        contador++;
        for(int i=0; i<clientes.size(); i++){
               aux1+=res;
            
        }
        
        resultado = aux1/clientes.size();
        System.out.println(resultado);
        Flag2 = false;
        aux1=0;


    }


    @Override
    public double calculate(double a, double b, double n, String tipo, int numThreads) {
            double h = (b - a) / n; // Calcular el ancho del subintervalo
                 Function<Double, Double> function;
                        switch (tipo) {

                    case "X":
                        function = x -> x;
                        break;

                    case "X2":
                        function = x -> x*x;
                        break;

                    case "X3":
                        function = x -> x*x*x;
                        break;

                    case "1/X":
                        function = x -> 1/x;
                        if(a==0 || b==0) {
                                System.out.println("Funcion discontinua. Limites no pueden ser 0");
                        }
                        break;

                    case "SEN":
                        function = x -> Math.sin(x);
                        break;

                    case "COS":
                        function = x -> Math.cos(x);
                        break;

                    case "TAN":
                        function = x -> Math.tan(x);
                        break;

                    case "EXP":
                        function = x -> Math.exp(x);
                        break;

                    case "Ln":
                        function = x -> Math.log(x);
                        if(a==0 || b==0) {
                                System.out.println("Funcion discontinua. Limites no puede ser 0");
                        }
                        break;

                    default:
                        System.out.println("Seleccion invalida");
                        function = x -> x * x;
                        break;
                }



                        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // Crear el ExecutorService con el número de hilos especificado


                        Future<Double>[] futures = new Future[numThreads]; // Crear una lista de futuros para almacenar los resultados de cada hilo


                        for (int i = 0; i < numThreads; i++) {    // Dividir el trabajo entre los hilos
                            int threadNumber = i;
                            System.out.println("Thread " + i + " Inicia");
                            futures[i] = executor.submit(() -> {
                                double sum = 0;
                                int startIndex = (int) (threadNumber * n / numThreads);
                                int endIndex = (int) ((threadNumber + 1) * n / numThreads);


                                if (threadNumber == numThreads - 1) {
                                    endIndex = (int) n;
                                }

                                for (int j = startIndex + 1; j < endIndex; j++) {
                                    double x = a + j * h;
                                    sum += function.apply(x);
                                }
                                return sum;
                            }

                          );

                       }

                        double sum = 0.5 * (function.apply((double) a) + function.apply((double) b));
                        try {
                            for (int i = 0; i < numThreads; i++) {
                                sum += futures[i].get();
                                    System.out.println("Thread " + i + " termnina");
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }  // Esperar a que se completen todos los futuros y sumar los resultados


                        double integral = h * sum; // Multiplicar por el ancho del subintervalo para obtener la integral
                        executor.shutdown();

            return integral;
        }

     @Override
     public double resultado(){
         Flag = true;
         Flag2 = Flag;
         return resultado;
     }
    
    @Override
    public boolean flag(){
        return Flag;
        
    }
    
    @Override
    public boolean flag2(){
        return Flag2;
        
    }

}

