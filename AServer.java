import java.io.*;
//import java.math.BigInteger;
import java.net.*;
//import java.security.*;
import java.util.ArrayList;
import java.util.List;


public class AServer extends func {

    // public static final int PORT = 8080; // ポート番号を設定する．

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
                        // while (true) {
                        // Integer option2 = Integer.parseInt(in.readLine()); // 5
                        // File chat_log;
                        // if (option2 == 1) {
                        // String room_name = in.readLine();
                        // List<String> list_data2 = new ArrayList<String>();
                        // list_data2 = check_chat_room(db2, room_name);
                        // if (list_data2.size() == 1) {
                        // if (Integer.parseInt(list_data2.get(0)) == 1) {
                        // out.println(
                        // "'" + room_name + "'というチャットルームが存在します");
                        // } else {
                        // create_chat_room(db2, room_name);
                        // chat_log = new File(room_name + "_chat_log.txt");
                        // chat_log.createNewFile();
                        // out.println("チャットルームを作成しました");
                        // }
                        // }
                        // } else if (option2 == 2) {
                        // String room_name = in.readLine();
                        // // config room and join chat room
                        // List<String> list_data2 = new ArrayList<String>();
                        // list_data2 = check_chat_room(db2, room_name);
                        // out.println(room_name + "に参加できました");
                        // } else if (option2 == 3) {
                        // // ユーザーが参加しているチャットルームを表示
                        // List<String> list_data2 = new ArrayList<String>();
                        // list_data2 = show_chat_room(db2, username);
                        // out.println(list_data2);
                        // String room_name = in.readLine();
                        // chat_log = new File(room_name + "_chat_log.txt");
                        // // chat log
                        // BufferedReader chat_log_reader = new BufferedReader(
                        // new FileReader(chat_log));
                        // String chat_log_line;
                        // while ((chat_log_line = chat_log_reader.readLine()) != null) {
                        // out.println(chat_log_line);
                        // }
                        // chat_log_reader.close();
                        // // chat log
                        // PrintWriter chat_log_writer = new PrintWriter(
                        // new BufferedWriter(new FileWriter(chat_log, true)));
                        // String chat_log_data = in.readLine();
                        // chat_log_writer.println(chat_log_data);
                        // chat_log_writer.close();
                        // } else if (option2 == 4) {
                        // break;
                        // }
                        // }
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
