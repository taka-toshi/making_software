import java.io.*;
import java.net.*;
import java.util.*;
//import java.math.BigInteger;
//import java.security.*;

public class Client extends func {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        final int PORT = 8080;
        InetAddress addr = InetAddress.getByName("localhost"); // IP アドレスへの変換
        // System.out.println("addr = " + addr);
        // Socket socket = new Socket(addr, JabberServer.PORT); // ソケットの生成
        Socket socket = new Socket(addr, PORT); // ソケットの生成
        try {
            // System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true); // 送信バッファ設定
            System.out.println(in.readLine()); // 1

            // login and signup
            System.out.println("1: login , 2: signup");
            Integer option = Integer.parseInt(sc.nextLine());

            // optionの値が決まるまで以下のコードを実行しないようにする必要性があるかもしれない
            out.println(option); // 2

            System.out.println("ユーザー名を入力してください");
            String username = sc.nextLine();
            System.out.println("パスワードを入力してください");
            String pass = sc.nextLine();

            String hash_pass = make_hash(pass);

            // username,passが入力されるまで以下のコードを実行しないようにする必要性があるかもしれない
            out.println(username); // 3
            out.println(hash_pass); // 4

            if (option == 1) {
                Boolean LOGIN = false;
                LOGIN = Boolean.parseBoolean(in.readLine()); // 5
                if (LOGIN == true) {
                    System.out.println("ログインしました!");
                } else {
                    System.out.println("ログインできませんでした");
                }
            } else if (option == 2) {
                System.out.println(in.readLine()); // 6
            }
        } finally {
            System.out.println("closing...");
            socket.close();
            sc.close();
        }
    }
}
