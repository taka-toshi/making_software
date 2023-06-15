import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class AServer extends func {
    public static void main(String[] args) throws IOException {
        // データベースの設定
        File db = new File("database.db");
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        // usernameとpasswordを保存するテーブルを制作
        create_table(db);
        // チャットルームの名前を保存するテーブルを制作
        create_table_chatname(db);

        final int PORT = 8080; // ポート番号をプログラムの引数で与える

        ServerSocket s = new ServerSocket(PORT); // ソケットを作成する

        try {
            Socket socket = s.accept();// コネクション設定要求を待つ
            try {
                /*---------------------------------------------------------------------------------------- */
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        true); // 送信バッファ設定

                out.println("Serverと接続しました。"); // 1

                /*---------------------------------------------------------------------------------------- */
                Integer option = Integer.parseInt(in.readLine()); // 2

                /*---------------------------------------------------------------------------------------- */
                String username = in.readLine(); // 3
                String pass = in.readLine(); // 4

                String hash_pass = make_hash(pass);

                /*---------------------------------------------------------------------------------------- */
                if (option == 1) {
                    List<String> list_data = new ArrayList<String>();
                    list_data = login_user(db, username, hash_pass);
                    Boolean LOGIN = false;
                    if (list_data.size() == 1) {
                        LOGIN = true;
                        out.println(LOGIN); // 5

                        while (true) {
                            Integer option2 = Integer.parseInt(in.readLine()); // 6
                            File chat_log;

                            /*---------------------------------------------------------------------------------------- */
                            if (option2 == 1) {// 1.新規作成
                                // チャットルームの名前を受信
                                String room_name = in.readLine();// 7
                                // room_nameの制限 \ / : * ? " < > |
                                if (room_name.contains("\\") || room_name.contains("/") || room_name.contains(":")
                                        || room_name.contains("*") || room_name.contains("?")
                                        || room_name.contains("\"") || room_name.contains("<")
                                        || room_name.contains(">") || room_name.contains("|")) {
                                    out.println("チャットルーム名に \\ / : * ? \" < > | は使えません。");// 8
                                } else {

                                    // チャットルームがあるかdbから探す
                                    List<String> list_data2 = new ArrayList<String>();
                                    list_data2 = check_chat_room(db, room_name);

                                    if (list_data2.size() == 1) {
                                        if (Integer.parseInt(list_data2.get(0)) == 1) {
                                            out.println("'" + room_name + "'というチャットルームが存在します。別のチャットルーム名を入力してください。");// 8
                                        } else {
                                            // チャットルームの名前をテーブルに追加
                                            add_chatname(db, room_name);

                                            chat_log = new File(room_name + "_chat_log.txt");
                                            chat_log.createNewFile();

                                            out.println("チャットルームを作成しました!");// 8
                                        }
                                    } else {
                                        out.println("チャットルームを作成できませんでした。");// 8
                                    }
                                }

                                /*---------------------------------------------------------------------------------------- */
                            } else if (option2 == 2) {// 2.既存に参加
                                String room_name = in.readLine();// 9

                                // ルームをdbから探してチャットルームに参加する
                                List<String> list_data2 = new ArrayList<String>();
                                list_data2 = check_chat_room(db, room_name);

                                if (Integer.parseInt(list_data2.get(0)) == 1) {
                                    out.println(room_name + "に参加できました!");// 10
                                    out.println(true);// 10.5
                                    out.println(room_name);// 11

                                    chat_log = new File(room_name + "_chat_log.txt");

                                    while (true) {
                                        BufferedReader chat_log_reader = new BufferedReader(new FileReader(chat_log));
                                        PrintWriter chat_log_writer = new PrintWriter(
                                                new BufferedWriter(new FileWriter(chat_log, true)));
                                        /*
                                         * String chat_log_line;
                                         * while ((chat_log_line = chat_log_reader.readLine()) != null) {
                                         * out.println(chat_log_line);// 13
                                         * }
                                         */
                                        chat_log_reader.close();

                                        String chat_log_data = in.readLine();// 12
                                        chat_log_writer.println(chat_log_data);
                                        chat_log_writer.close();

                                        if (chat_log_data.equals("END")) {
                                            break;
                                        }
                                    }

                                } else {
                                    out.println(room_name + "に参加できませんでした。");// 10
                                    out.println(false);// 10.5
                                }

                                /*---------------------------------------------------------------------------------------- */
                            } else if (option2 == 3) {// 3.退出する
                                break;

                                /*---------------------------------------------------------------------------------------- */
                            }
                        }
                    } else {
                        out.println(LOGIN); // 5
                    }

                    /*---------------------------------------------------------------------------------------- */
                } else if (option == 2) {
                    if (username.contains("\\") || username.contains("/") || username.contains(":")
                            || username.contains("*") || username.contains("?")
                            || username.contains("\"") || username.contains("<")
                            || username.contains(">") || username.contains("|")) {
                        out.println("ユーザー名に \\ / : * ? \" < > | は使えません。");// 6
                    } else {
                        List<String> list_data2 = new ArrayList<String>();
                        list_data2 = check_user(db, username);
                        if (list_data2.size() == 1) {
                            if (Integer.parseInt(list_data2.get(0)) == 1) {
                                out.println("'" + username + "'というユーザーが存在します"); // 6
                            } else {
                                add_user(db, username, hash_pass);
                                out.println("ユーザーを追加しました"); // 6
                            }
                        } else {
                            out.println("ユーザーを追加できませんでした"); // 6
                        }
                    }
                } else {
                    Boolean LOGIN = false;
                    out.println(LOGIN); // 5
                }
                /*---------------------------------------------------------------------------------------- */
            } finally {
                System.out.println("closing...");
                socket.close();
            }
        } finally {
            s.close();
        }
    }
}
