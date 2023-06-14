import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
//import java.math.BigInteger;
//import java.security.*;

import java.awt.Rectangle;
import java.awt.event.*;

public class Client extends func{

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        final int PORT = 8080;
        InetAddress addr = InetAddress.getByName("localhost"); // IP アドレスへの変換

        Socket socket = new Socket(addr, PORT); // ソケットの生成

        try {
            /*---------------------------------------------------------------------------------------- */
            // System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // データ受信用バッファの設定
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); // 送信バッファ設定
            
            System.out.println(in.readLine()); // 1

            // frameを実装する
            JFrame frame = new JFrame("MyApplication");
            // frame.setTitle("MyApplication!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Rectangle table = new Rectangle(1000, 500);
            frame.setBounds(table);//frameのサイズを指定→table
            frame.setLocationRelativeTo(null);//画面の真ん中にframeを表示

            /*---------------------------------------------------------------------------------------- */
            //パネルp1をframeに追加する
            JPanel p1 = new JPanel();
            p1.setLayout(null);

            //final Integer[] option = { null };
            Integer[] option = { null };

            //ログインボタンの実装
            JButton login_btn = new JButton("login");
            login_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    option[0] = 1;
                    out.println(option[0]); 
                }
            });

            //サインアップボタンの実装
            JButton signup_btn = new JButton("signup");
            signup_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    option[0] = 2;
                    out.println(option[0]); 
                }
            });

            login_btn.setBounds(375,225, 100,50);
            signup_btn.setBounds(525,225, 100,50);
            p1.add(login_btn);
            p1.add(signup_btn);
            frame.getContentPane().add(p1);
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
            //out.println(option[0]); // 2

            /*---------------------------------------------------------------------------------------- */
            frame.getContentPane().removeAll();//パネルp1を取り除く

            //パネルp2の実装
            JPanel p2 = new JPanel();
            p2.setLayout(null);

            JLabel label_user = new JLabel("ユーザー名：");
            JLabel label_pass = new JLabel("パスワード：");
            JTextField tf_user = new JTextField();
            JPasswordField tf_pass = new JPasswordField();
            String username = tf_user.getText();
            char[] pass = tf_pass.getPassword();
            String pass_str = new String(pass);
            String hash_pass = make_hash(pass_str);

            //final Boolean[] ok = { null };
            Boolean[] ok = { null };
            JButton ok_btn = new JButton("ok");
            ok_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ok[0] = true;
                    out.println(ok[0]);//3
                    out.println(username);// 4
                    out.println(hash_pass);// 5
                }
            });

            tf_pass.setColumns(10);
            tf_user.setColumns(10);
            tf_pass.setColumns(10);
            label_user.setBounds(390, 200, 150, 25);
            label_pass.setBounds(390, 275, 150, 25);
            tf_user.setBounds(545, 200, 150, 25);
            tf_pass.setBounds(545, 275, 150, 25);
            ok_btn.setBounds(800, 300, 50, 50);
            p2.add(tf_user);
            p2.add(tf_pass);
            p2.add(label_user);
            p2.add(label_pass);
            p2.add(ok_btn);
            frame.add(p2);
            frame.setVisible(true);
            frame.validate();
            frame.repaint();//画面を書き直す

            /* 
            //?
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
            */
            //out.println(username); // 3
            //out.println(hash_pass); // 4


            /*---------------------------------------------------------------------------------------- */
            if (option[0] == 1) {
                Boolean LOGIN = false;
                LOGIN = Boolean.parseBoolean(in.readLine()); // 5
                if (LOGIN == true) {
                    System.out.println("ログインしました!");
                    // create chat room
                    while (true) {
                        System.out.println("1.新規作成, 2.既存に参加, 3.始める, 4.退出する");
                        Integer option2 = Integer.parseInt(sc.nextLine());

                        out.println(option2); // 6
                        
                        if (option2 == 1) {
                            System.out.println("チャットルーム名を入力してください");
                            String room_name = sc.nextLine();
                            out.println(room_name);

                            //System.out.println("チャットルームを作成しました");
                            //System.out.println("チャットルームを作成しませんでした");
                            System.out.println(in.readLine());
                        } else if (option2 == 2) {
                            System.out.println("参加するチャットルーム名を入力してください");
                            String room_name = sc.nextLine();
                            out.println(room_name);
                            System.out.println("チャットルームに参加しました");
                            System.out.println("チャットルームに参加しませんでした");
                        } else if (option2 == 3) {
                            System.out.println("チャットを開始します");
                            while (true) {
                                String str = in.readLine();
                                if (str.equals("END"))break;
                                System.out.println(str);
                            }
                        } else if (option2 == 4) {
                            System.out.println("終了します");
                            break;
                        }
                    }
                } else {
                    System.out.println("ログインできませんでした");
                }
            } else if (option[0] == 2) {
                System.out.println(in.readLine()); // 7
            }
        } finally {
            System.out.println("closing...");
            socket.close();
            sc.close();
        }
    }
}
