/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3erp;

/**
 *
 * @author crisb
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TrapezoidalRuleRMI extends Remote {
    double calculate(double a, double b, double n, String tipo, int numThreads) throws RemoteException;
    void agregar(TrapezoidalRuleRMI cliente) throws RemoteException;
    int valor() throws RemoteException;
    void enviar(double a, double b, double n, String tipo, int numThreads) throws RemoteException;
    void recibir(double res) throws RemoteException;
    Holder aux() throws RemoteException;
    boolean flag() throws RemoteException;
    boolean flag2() throws RemoteException;
    double resultado() throws RemoteException;
}

