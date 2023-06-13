import java.io.*;
//import java.math.BigInteger;
import java.net.*;
//import java.security.*;
import java.util.ArrayList;
import java.util.List;


public class AServer extends func{

    public static void main(String[] args) throws IOException {
        File db = new File("database.db");
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        create_table(db);

        final int PORT = 8080; // ポート番号をプログラムの引数で与える
        ServerSocket s = new ServerSocket(PORT); // ソケットを作成する
        // System.out.println("Started: " + s);
        try {
            Socket socket = s.accept(); // コネクション設定要求を待つ
            try {
                // System.out.println("Connection accepted: " + socket);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        true); // 送信バッファ設定

                out.println("Serverと接続しました。"); // 1
                Integer option = Integer.parseInt(in.readLine()); // 2
                String username = in.readLine(); // 3
                String pass = in.readLine(); // 4

                String hash_pass = make_hash(pass);

                if (option == 1) {
                    List<String> list_data = new ArrayList<String>();
                    list_data = login_user(db, username, hash_pass);
                    Boolean LOGIN = false;
                    if (list_data.size() == 1) {
                        LOGIN = true;
                        out.println(LOGIN); // 5
                    } else {
                        out.println(LOGIN); // 5
                    }
                } else if (option == 2) {
                    List<String> list_data2 = new ArrayList<String>();
                    list_data2 = check_user(db, username);
                    if (list_data2.size() == 1) {
                        if (Integer.parseInt(list_data2.get(0)) == 1) {
                            out.println("'" + username + "'というユーザーが存在します"); // 6
                        } else {
                            add_user(db, username, hash_pass);
                            out.println("ユーザーを追加しました"); // 6
                        }
                    }
                }
            } finally {
                System.out.println("closing...");
                socket.close();
            }
        } finally {
            s.close();
        }
    }
}
