import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.math.BigInteger;
import java.security.*;

import java.awt.Rectangle;
import java.awt.event.*;

public class Client {

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

            // Create and set up the window.
            JFrame frame = new JFrame("MyApplication");
            // frame.setTitle("MyApplication!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Rectangle table = new Rectangle(1000, 500);
            frame.setBounds(table);
            frame.setLocationRelativeTo(null);

            // Add contents to the window.
            JPanel p = new JPanel();
            final Integer[] option = { null };
            // login and signup button with action listener
            JButton login_btn = new JButton("login");
            login_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    option[0] = 1;
                }
            });
            JButton signup_btn = new JButton("signup");
            signup_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    option[0] = 2;
                }
            });
            p.add(login_btn);
            p.add(signup_btn);
            frame.getContentPane().add(p);
            frame.setVisible(true);

            // optionの値が決まるまで以下のコードを実行しない
            while (true) {
                if (option[0] == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            out.println(option[0]); // 2
            frame.getContentPane().removeAll();

            JPanel p2 = new JPanel();
            JLabel label_user = new JLabel("ユーザー名を入力してください");
            JLabel label_pass = new JLabel("パスワードを入力してください");
            JTextField tf_user = new JTextField();
            JPasswordField tf_pass = new JPasswordField();
            final Boolean[] ok = { null };
            JButton ok_btn = new JButton("ok");
            ok_btn.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    ok[0] = true;
                }
            });
            tf_user.setColumns(10);
            tf_pass.setColumns(10);
            p2.add(tf_user);
            p2.add(tf_pass);
            p2.add(label_user);
            p2.add(label_pass);
            p2.add(ok_btn);
            frame.add(p2);
            frame.setVisible(true);
            frame.validate();
            frame.repaint();

            String username = tf_user.getText();
            char[] pass = tf_pass.getPassword();
            String pass_str = new String(pass);
            String hash_pass = make_hash(pass_str);

            while (true) {
                if (ok[0] == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            // usernameとpass_strが入力されるまで以下のコードを実行しない
            while (true) {
                if (username == null || pass_str == null || username == "" || pass_str == "") {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            out.println(username); // 3
            out.println(hash_pass); // 4

            if (option[0] == 1) {
                Boolean LOGIN = false;
                LOGIN = Boolean.parseBoolean(in.readLine()); // 5
                if (LOGIN == true) {
                    System.out.println("ログインしました!");
                    // create chat room
                    // while (true) {
                    // System.out.println(
                    // "1.create chat, 2.join chat, 3.start chat, 4.exit");
                    // Integer option2 = Integer.parseInt(sc.nextLine());

                    // out.println(option2); // 5
                    // if (option2 == 1) {
                    // System.out.println("チャットルーム名を入力してください");
                    // String room_name = sc.nextLine();
                    // out.println(room_name);
                    // System.out.println("チャットルームを作成しました");
                    // // System.out.println("チャットルームを作成しませんでした");
                    // } else if (option2 == 2) {
                    // System.out.println(
                    // "参加するチャットルーム名を入力してください");
                    // String room_name = sc.nextLine();
                    // out.println(room_name);
                    // System.out.println("チャットルームに参加しました");
                    // // System.out.println("チャットルームに参加しませんでした");
                    // } else if (option2 == 3) {
                    // System.out.println("チャットを開始します");
                    // while (true) {
                    // String str = in.readLine();
                    // if (str.equals("END"))
                    // break;
                    // System.out.println(str);
                    // }
                    // } else if (option2 == 4) {
                    // System.out.println("終了します");
                    // break;
                    // }
                    // }
                } else {
                    System.out.println("ログインできませんでした");
                }
            } else if (option[0] == 2) {
                System.out.println(in.readLine()); // 6
            }
        } finally {
            System.out.println("closing...");
            socket.close();
            sc.close();
        }
    }

    // sha-256ハッシュ値を返す
    public static String make_hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            return String.format("%064x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
