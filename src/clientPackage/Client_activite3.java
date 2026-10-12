package clientPackage;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client_activite3 {
    static final String IP_SERVEUR = "127.0.0.1";
    static final int PORT = 1234;
    public static void main(String[] args) throws IOException {

        InetSocketAddress adresseServeur = new InetSocketAddress(IP_SERVEUR, PORT);
        System.out.println("Je suis un client pas encore connecté…");
        Socket socket = new Socket();
        socket.connect(adresseServeur, 5000);
        System.out.println("je suis un client connecté à " + IP_SERVEUR + ":" + PORT);

        Scanner sc = new Scanner(System.in);

        char op;
        while(true){
            System.out.println("entrer un operateur (+-*/)");
            op = sc.next().charAt(0);
            if (op=='+' || op == '-' || op=='*' || op=='/') break;
        }

        System.out.println("entrer un entier");
        int x = sc.nextInt();

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeChar(op);
        dos.writeInt(x);
        dos.flush();

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        int res = dis.readInt();
        System.out.println("le resultat est: " + res);

        dis.close();
        is.close();
        dos.close();
        os.close();
        sc.close();
        socket.close();
    }
}