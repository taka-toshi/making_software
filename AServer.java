import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class AServer extends func {
    private File db;
    //private File db2;
    private final int PORT;
    private ServerSocket s;

    public AServer(String dbPath, int port) throws IOException { // コンストラクタ
        db = new File(dbPath);
        PORT = port; // ポート番号をプログラムの引数で与える
        s = new ServerSocket(PORT); // ソケットを作成する
    }

    /* 
    public AServer2 (String dbPath, int port) throws IOException { // コンストラクタ
        db2 = new File(dbPath);
        PORT = port; // ポート番号をプログラムの引数で与える
        s = new ServerSocket(PORT); // ソケットを作成する
    }
    */

    public void start() throws IOException {
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        //usernameとpasswordを保存するテーブルを制作
        create_table(db);
        //チャットルームの名前を保存するテーブルを制作
        create_table_chatname(db);

        try {
            Socket socket = s.accept(); // コネクション設定要求を待つ
            handleConnection(socket);
        } finally {
            s.close();
        }
    }

    private void handleConnection(Socket socket) throws IOException {
        try {
            /*---------------------------------------------------------------------------------------- */
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); // 送信バッファ設定

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

                    while(true){
                        Integer option2 = Integer.parseInt(in.readLine()); // 6
                        File chat_log;

                        /*---------------------------------------------------------------------------------------- */
                        if (option2 == 1) {// 1.新規作成
                            //チャットルームの名前を受信
                            String room_name = in.readLine();

                            //チャットルームがあるかdbから探す
                            List<String> list_data2 = new ArrayList<String>();
                            list_data2 = check_chat_room(db, room_name);

                            if (list_data2.size() == 1) {
                                if (Integer.parseInt(list_data2.get(0)) == 1) {
                                    out.println("'" + room_name + "'というチャットルームが存在します");
                                } else {
                                    //create_chat_room(db2, room_name);
                                    //チャットルームの名前をテーブルに追加
                                    add_chatname(db, room_name);

                                    chat_log = new File(room_name + "_chat_log.txt");
                                    chat_log.createNewFile();
                                    out.println("チャットルームを作成しました");
                                }
                            }
                        
                        /*---------------------------------------------------------------------------------------- */
                        } else if (option2 == 2) {// 2.既存に参加
                            String room_name = in.readLine();
                            //config room and join chat room
                            List<String> list_data2 = new ArrayList<String>();
                            //list_data2 = check_chat_room(db2, room_name);
                            list_data2 = check_chat_room(db, room_name);
                            out.println(room_name + "に参加できました");
                        
                        /*---------------------------------------------------------------------------------------- */
                        } else if (option2 == 3) {// 3.始める
                            //ユーザーが参加しているチャットルームを表示
                            List<String> list_data2 = new ArrayList<String>();
                            //list_data2 = show_chat_room(db2, username);
                            list_data2 = show_chat_room(db, username);
                            out.println(list_data2);
                            String room_name = in.readLine();
                            chat_log = new File(room_name + "_chat_log.txt");

                            // chat log
                            BufferedReader chat_log_reader = new BufferedReader(new FileReader(chat_log));
                            String chat_log_line;
                            while ((chat_log_line = chat_log_reader.readLine()) != null) {
                                out.println(chat_log_line);
                            }
                            chat_log_reader.close();

                            // chat log
                            PrintWriter chat_log_writer = new PrintWriter(new BufferedWriter(new FileWriter(chat_log, true)));
                            String chat_log_data = in.readLine();
                            chat_log_writer.println(chat_log_data);
                            chat_log_writer.close();
                        
                        /*---------------------------------------------------------------------------------------- */
                        } else if (option2 == 4) {// 4.退出する
                            break;
                        
                        /*---------------------------------------------------------------------------------------- */
                        }
                    }
                } else {
                    out.println(LOGIN); // 5
                }

            /*---------------------------------------------------------------------------------------- */
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

            /*---------------------------------------------------------------------------------------- */
        } finally {
            System.out.println("closing...");
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        AServer server = new AServer("database.db", 8080); // インスタンスを作成する
        server.start();
    }
}
