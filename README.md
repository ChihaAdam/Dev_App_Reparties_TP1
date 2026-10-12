# Communication entre un client et un serveur

Ce projet présente deux façons d’échanger des données entre des programmes Java grâce aux sockets. Dans chaque activité, le serveur attend une connexion sur le port `1234`. Le client se connecte, envoie des valeurs, puis attend le résultat calculé par le serveur.

## Activité 2 : envoyer un entier et recevoir son résultat

### Côté client

Le client commence par demander un entier à l’utilisateur avec `Scanner`. Il ouvre ensuite une connexion avec le serveur et récupère les flux associés à la socket. Le flux de sortie permet d’envoyer le nombre. Le flux d’entrée sert à recevoir la réponse :

```java
System.out.println("entrer un entier");
int x = sc.nextInt();

Socket socket = new Socket("localhost", 1234);
DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
dos.writeInt(x);
```

`DataOutputStream` et `DataInputStream` permettent d’écrire et de lire directement des valeurs Java comme des entiers. Le client envoie donc un entier avec `writeInt`. Il attend ensuite que le serveur renvoie le résultat, qu’il lit lui aussi comme un entier :

```java
DataInputStream dis = new DataInputStream(socket.getInputStream());
int res = dis.readInt();
System.out.println("le resultat est: " + res);
```

### Côté serveur

Le serveur crée une `ServerSocket` sur le port `1234`. L’appel à `accept()` le met en attente : il continue lorsque le client se connecte et fournit une socket pour cet échange. Le serveur lit le nombre envoyé, le multiplie par 5, puis écrit le résultat dans le flux de sortie :

```java
Socket socket = serverSocket.accept();
DataInputStream dis = new DataInputStream(socket.getInputStream());
int nb = dis.readInt();

int x = nb * 5;
DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
dos.writeInt(x);
```

L’ordre des opérations est important : le client écrit un entier et le serveur lit un entier ; ensuite, le serveur écrit le résultat et le client le lit. Les deux programmes ferment leurs flux et leurs sockets après l’échange.

## Activité 3 : envoyer une opération et un entier

L’Activité 3 suit le même échange, mais le client transmet deux valeurs : un opérateur et un entier. Le serveur utilise l’opérateur pour choisir le calcul à faire avec `5`.

### Côté client

Le client vérifie d’abord la saisie de l’opérateur. Il redemande une valeur jusqu’à recevoir `+`, `-`, `*` ou `/`. Il demande ensuite un entier. Une fois connecté au serveur, il envoie l’opérateur en premier et le nombre en second :

```java
dos.writeChar(op);
dos.writeInt(x);
dos.flush();
```

Le serveur doit lire ces valeurs dans le même ordre. `flush()` pousse les données écrites dans le flux afin qu’elles soient transmises sans attendre la fermeture de celui-ci. Après l’envoi, le client lit l’entier retourné par le serveur et l’affiche.

### Côté serveur

Après `accept()`, le serveur lit le caractère de l’opérateur, puis l’entier. La méthode `result` choisit le calcul correspondant :

```java
char op = dis.readChar();
int nb = dis.readInt();

private static int result(int x, char op) {
    if (op == '+') return x + 5;
    if (op == '-') return x - 5;
    if (op == '*') return x * 5;
    return x / 5;
}
```

Le résultat est envoyé au client avec `writeInt`, qui le récupère avec `readInt`. Comme le serveur renvoie un entier, la division est une division entière Java : par exemple, `7 / 5` donne `1`.

### Connexion au serveur sur le réseau local

Dans le code du client de l’Activité 3, l’adresse du serveur est définie dans `IP_SERVEUR`, puis utilisée avec le port `1234` :

```java
static final String IP_SERVEUR = "192.168.100.67";
static final int PORT = 1234;

InetSocketAddress adresseServeur = new InetSocketAddress(IP_SERVEUR, PORT);
socket.connect(adresseServeur, 5000);
```

Pour une connexion entre deux machines, `IP_SERVEUR` doit contenir l’adresse IP locale de la machine qui exécute le serveur, par exemple `192.168.1.20`, et non `127.0.0.1`. Cette adresse doit être accessible depuis l’autre machine sur le même réseau. Le délai `5000` correspond à cinq secondes maximum pour établir la connexion.

## Lancer les programmes

Il faut d’abord démarrer le serveur de l’activité souhaitée. Celui-ci reste en attente sur le port `1234`. On lance ensuite le client correspondant : il se connecte, envoie ses données et affiche la réponse. Les deux activités utilisent le même port, donc une seule instance de serveur peut être lancée à la fois sur cette machine.
