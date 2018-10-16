import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class TrataCliente implements Runnable {

    private InputStream cliente;
    private Servidor servidor;
    private Usuario usuario;

    /*public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }*/
    
    public TrataCliente(Usuario usuario, Servidor servidor) {
    	this.usuario = usuario;
    	this.servidor = servidor;
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos
        //Scanner s = new Scanner(this.cliente);
    	Scanner s = null;
		try {
			s = new Scanner(this.usuario.getSocket().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
        while (s.hasNextLine()) {
            servidor.distribuiMensagem(s.nextLine(), this.usuario);
        }
        s.close();
    }
}