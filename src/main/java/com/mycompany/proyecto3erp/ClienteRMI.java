/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3erp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author crisb
 */
public class ClienteRMI extends JFrame implements ActionListener  {
    private JFrame frame = new JFrame();
    public int x;
    public boolean aux1=true;
    public Registry registry;
    public TrapezoidalRuleRMI remoteCalculator;
    public Holder holder;
   // public InterfazCliente cliente;
    
    public JLabel LimSupLabel = new JLabel("Numero Cliente: ");
    public JLabel LimSupTextField = new JLabel("");

    
    public JTextArea textArea = new JTextArea(5, 5);

    private JLabel resultado = new JLabel("Resultado: ");
    private JLabel resultadoLabel = new JLabel(" ");

    private JPanel panel = new JPanel(new BorderLayout());
    private JButton refresh = new JButton("COMENZAR");

    Font font = new Font("Arial", Font.PLAIN, 14);
    //Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);

    
    public ClienteRMI() {
       super("Integración numérica");
       registrar();
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // chambear();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        try {
            x = remoteCalculator.valor();
            String str1 = String.valueOf(x);
            LimSupTextField.setText(str1);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }



        JPanel panelNorte = new JPanel(new GridLayout(1, 1));
        panelNorte.add(LimSupLabel);
        panelNorte.add(LimSupTextField);


        JPanel panelCentro = new JPanel(new GridLayout(2, 1));
        panelCentro.add(resultado);
        panelCentro.add(resultadoLabel);
        panelCentro.add(refresh);

        JPanel panelSur = new JPanel(new GridLayout(1, 1));

        panelSur.add(textArea);

        panel.add(panelNorte, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        refresh.addActionListener(this);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    
     public void registrar(){
    
        try {

            registry = LocateRegistry.getRegistry("192.168.100.22", 1099);
            remoteCalculator = (TrapezoidalRuleRMI) registry.lookup("TrapezoidalRuleRMI");
            remoteCalculator.agregar(remoteCalculator);
            System.out.println("Conexion exitosa");
            textArea.setText("ESPERANDO AL MASTER");

        } catch (Exception e) {
            System.err.println("Error send the message: " + e.toString());
        }
    
    }
    
     public static void main(String[] args) {
    	
    	
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClienteRMI();
            }
        });
        
       
    }
     
    public void chambear(){
    textArea.setText("CHAMBEANDO");
        try {
            
            holder = remoteCalculator.aux();
            System.out.println(holder);
            double n = holder.Inter;
            double a = holder.LimSup;
            double b = holder.LimInf;
            int numThreads = holder.NUMT;
            String tipo = holder.Type;
            
            double res = remoteCalculator.calculate(a, b, n, tipo, numThreads);
            System.out.println(res);
            System.out.println("Recibido"); 
            remoteCalculator.recibir(res);
            resultadoLabel.setText("Terminado");
            holder=null;
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actionPerformed(ActionEvent e){
        
        //aux1=true;
        while(true){
          try {
            
            if(!remoteCalculator.flag()){ 
                chambear();
                break;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
        textArea.setText("TERMINADO");
        /*
        try {
            int c = remoteCalculator.valor();
            System.out.println(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    
    
    }

}

