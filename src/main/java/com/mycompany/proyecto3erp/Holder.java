/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3erp;

import java.io.Serializable;

/**
 *
 * @author crisb
 */
public class Holder implements Serializable {
    public double LimSup;
    public double LimInf;
    public double Inter;
    public String Type;
    public int NUMT;

    
    
    public Holder(double a, double b, double n, String tipo, int numThreads){
        LimSup = a;
        LimInf = b;
        Inter = n;
        Type = tipo;
        NUMT = numThreads;
        
        
    }
    
}
