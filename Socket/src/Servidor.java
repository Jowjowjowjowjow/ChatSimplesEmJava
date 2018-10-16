import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class Servidor {

    public static void main(String[] args) throws IOException {
        // inicia o servidor
        new Servidor().executa();
    }

    private int porta;
   //private List<PrintStream> clientes;
    private List<Usuario> usuarios;

    public Servidor () {
    //public Servidor (int porta) {
        //this.porta = porta;
        //this.clientes = new ArrayList<PrintStream>();
    	this.usuarios = new ArrayList<Usuario>();
    }

    public void executa () throws IOException {
    	this.porta = Integer.parseInt(JOptionPane.showInputDialog("Insira a porta em que deseja abrir a conexão"));
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta " + this.porta +" aberta!");

        while (true) {
            // aceita um cliente
        	Usuario usuario = new Usuario();
        	usuario.setSocket(servidor.accept());
            //Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " +     
                //cliente.getInetAddress().getHostAddress()
            		usuario.getSocket().getInetAddress().getHostName()
            );
            
            usuario.setNome(usuario.getSocket().getInetAddress().getHostName());

            // adiciona saida do cliente à lista
            usuario.setPrint(new PrintStream(usuario.getSocket().getOutputStream()));
            //PrintStream ps = new PrintStream(cliente.getOutputStream());
            //this.clientes.add(ps);
            
            this.usuarios.add(usuario);
            // cria tratador de cliente numa nova thread
            TrataCliente tc = 
                    //new TrataCliente(cliente.getInputStream(), this);
            		new TrataCliente(usuario, this);
            new Thread(tc).start();
        }

    }

    public void distribuiMensagem(String msg, Usuario quemEnviou) {
        // envia msg para todo mundo
        //for (PrintStream cliente : this.clientes) {
    	
    	String[] teste = msg.split(":");
    	for(Usuario usuario : this.usuarios) {
    		if(teste[0].equalsIgnoreCase("nome")) {
    			quemEnviou.setNome(teste[1]);
    		}else {
    			if(!usuario.equals(quemEnviou)) {
    				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    				Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
    				String dataFormatada = sdf.format(hora);
        			usuario.getPrint().println("("+dataFormatada+") " + 
    				quemEnviou.getNome() +" disse: " + msg);
        		}
    		}
            //cliente.println(msg);
        }
    }
}