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
        
        Socket socket = new Socket(addr, PORT); // ソケットの生成
        try {
            /*---------------------------------------------------------------------------------------- */
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); // 送信バッファ設定
            
            System.out.println(in.readLine()); // 1

            /*---------------------------------------------------------------------------------------- */
            // login and signup
            System.out.println("1: login , 2: signup");
            Integer option = Integer.parseInt(sc.nextLine());
            out.println(option); // 2

            /*---------------------------------------------------------------------------------------- */
            System.out.println("ユーザー名を入力してください");
            String username = sc.nextLine();
            System.out.println("パスワードを入力してください");
            String pass = sc.nextLine();

            String hash_pass = make_hash(pass);

            out.println(username); // 3
            out.println(hash_pass); // 4

            /*---------------------------------------------------------------------------------------- */
            if (option == 1) {
                Boolean LOGIN = false;
                LOGIN = Boolean.parseBoolean(in.readLine()); // 5
                if (LOGIN == true) {
                    System.out.println("ログインしました!");
                    System.out.println("");

                    while(true){
                        System.out.println("1.新規作成, 2.既存に参加, 3.始める, 4.退出する");
                        Integer option2 = Integer.parseInt(sc.nextLine());// 6

                        out.println(option2); // 6

                        /*---------------------------------------------------------------------------------------- */
                        if (option2 == 1) {// 1.新規作成
                            System.out.println("チャットルーム名を入力してください");
                            String room_name = sc.nextLine();
                            out.println(room_name);// 7

                            System.out.println(in.readLine());// 8
                            System.out.println("");
                        
                        /*---------------------------------------------------------------------------------------- */
                        } else if (option2 == 2) {// 2.既存に参加
                            System.out.println("参加するチャットルーム名を入力してください");
                            String room_name = sc.nextLine();
                            out.println(room_name);// 9
                            
                            System.out.println(in.readLine());// 10

                        /*---------------------------------------------------------------------------------------- */
                        } else if (option2 == 3) {// 3.始める
                            System.out.println("チャットを開始します");
                            while (true) {
                                String str = in.readLine();
                                if (str.equals("END"))break;
                                System.out.println(str);
                            }
                        
                        /*---------------------------------------------------------------------------------------- */
                        } else if (option2 == 4) {// 4.退出する
                            System.out.println("終了します");
                            break;
                        
                        /*---------------------------------------------------------------------------------------- */
                        }
                    }
                } else {
                    System.out.println("ログインできませんでした");
                }
            
            /*---------------------------------------------------------------------------------------- */
            } else if (option == 2) {
                System.out.println(in.readLine()); // 6
            }

            /*---------------------------------------------------------------------------------------- */
        } finally {
            System.out.println("closing...");
            socket.close();
            sc.close();
        }
    }
}
