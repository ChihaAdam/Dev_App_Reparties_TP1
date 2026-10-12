package serverPackage;
import java.io.*;
import java.net.*;

public class Server_activite3 {
    private static int result(int x, char op){
        if (op=='+') return x+5;
        if (op=='-') return x-5;
        if (op=='*') return x*5;
        return x/5;
    }
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("je suis un serveur en attente la connexion d'un client");
        Socket socket = serverSocket.accept();
        System.out.println("le client est connecté");
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        char op = dis.readChar();
        int nb = dis.readInt();

        int x = result(nb, op);
        System.out.println("calcul de serveur: " + nb + " " + op + " 5 = " + x);

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x);
        dos.flush();
        dos.close();
        os.close();
        dis.close();
        is.close();
        socket.close();
        serverSocket.close();
    }
}