import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

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
            out.println(username); // 3
            out.println(pass_str); // 4
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

            if (option[0] == 1) {
                Boolean LOGIN = false;
                LOGIN = Boolean.parseBoolean(in.readLine()); // 5
                if (LOGIN == true) {
                    System.out.println("ログインしました!");
                } else {
                    System.out.println("ログインできませんできた");
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
}
