import java.io.PrintStream;
import java.net.Socket;

public class Usuario {

	private String nome;
	private PrintStream print;
	private Socket socket;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public PrintStream getPrint() {
		return print;
	}
	public void setPrint(PrintStream print) {
		this.print = print;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
	
}
