package serverPackage;
import java.io.*;
import java.net.*;

public class Server_Activite2 {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("je suis un serveur en attente la connexion d'un client");
        Socket socket = serverSocket.accept();
        System.out.println("le client est connecté");

        InputStream is = socket.getInputStream();
        DataInputStream dis= new DataInputStream(is);
        int nb = dis.readInt();
        int x = nb*5;
        System.out.println("calcul de serveur: " + nb + " * 5 = "+ x);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x);

        dis.close();
        dos.close();
        is.close();
        os.close();
        socket.close();
        serverSocket.close();
    }
}
