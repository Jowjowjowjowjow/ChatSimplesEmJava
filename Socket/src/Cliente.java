import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Cliente {
    public static void main(String[] args) 
                throws UnknownHostException, IOException {
        // dispara cliente
        new Cliente().executa();
    }

    private String host;
    private int porta;

  /*  public Cliente (String host, int porta) {
        this.host = host;
        this.porta = porta;
    }*/
    public Cliente() {
    	
    }

    public void executa() throws UnknownHostException, IOException {
    	this.host = JOptionPane.showInputDialog("Insira o host desejado");
    	this.porta = Integer.parseInt(JOptionPane.showInputDialog("Insira a porta a qual deseja se conectar"));
        
    	Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor!");

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        // lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }

        saida.close();
        teclado.close();
        cliente.close();        
    }
}