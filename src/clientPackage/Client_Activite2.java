package clientPackage;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client_Activite2 {
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("entrer un entier");
        int x = sc.nextInt();
        Socket socket = new Socket("localhost",1234);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x);
        System.out.println("nombre envoyé au server");
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        int res = dis.readInt();
        System.out.println("le resultat est: " + res);
        dis.close();
        dos.close();
        dos.close();
        sc.close();
        os.close();
        socket.close();
    }
}
