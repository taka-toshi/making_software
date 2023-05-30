import java.io.*;
//import java.math.BigInteger;
import java.net.*;
//import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class AServer extends func {
    private File db;
    private final int PORT;
    private ServerSocket s;

    public AServer(String dbPath, int port) throws IOException {
        db = new File(dbPath);
        PORT = port;
        s = new ServerSocket(PORT);
    }

    public void start() throws IOException {
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        create_table(db);

        try {
            Socket socket = s.accept(); // コネクション設定要求を待つ
            handleConnection(socket);
        } finally {
            s.close();
        }
    }

    private void handleConnection(Socket socket) throws IOException {
        try {
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
    }

    public static void main(String[] args) throws IOException {
        AServer server = new AServer("database.db", 8080);
        server.start();
    }
}
