import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
//import java.math.BigInteger;
//import java.security.*;

import java.awt.Rectangle;
import java.awt.event.*;

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

            // frameを実装する
            JFrame frame = new JFrame("MyApplication");
            // frame.setTitle("MyApplication!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Rectangle table = new Rectangle(1000, 500);
            frame.setBounds(table);//frameのサイズを指定→table
            frame.setLocationRelativeTo(null);//画面の真ん中にframeを表示

            System.out.println(in.readLine()); // 1

            gui(frame, in, out, sc);
         
        }finally{
            System.out.println("closing...");
            socket.close();
            sc.close();
        }
    }
    


    public static void gui(JFrame frame, BufferedReader in, PrintWriter out, Scanner sc) throws IOException{
        frame.getContentPane().removeAll();//パネルがあれば取り除く
        
        //パネルp1をframeに追加する
        JPanel p1 = new JPanel();
        p1.setLayout(null);

        //final Integer[] option = { null };
        Integer[] option = { null };

        //ログインボタンの実装
        JButton login_btn = new JButton("ログイン");
        login_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option[0] = 1;
                out.println(option[0]); // 2
            }
        });

        //サインアップボタンの実装
        JButton signup_btn = new JButton("サインイン");
        signup_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option[0] = 2;
                out.println(option[0]);// 2 
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

        /*---------------------------------------------------------------------------------------- */
        frame.getContentPane().removeAll();//パネルp1を取り除く
                
        //パネルp2の実装
        JPanel p2 = new JPanel();
        p2.setLayout(null);

        JLabel label_user = new JLabel("ユーザー名：");
        JLabel label_pass = new JLabel("パスワード：");
        JTextField tf_user = new JTextField();
        JPasswordField tf_pass = new JPasswordField();

        //final Boolean[] ok = { null };
        Boolean[] ok = { null };
        Boolean[] noerror = { null };
        JButton ok_btn = new JButton("OK");
        ok_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ok[0] = true;
                String username = tf_user.getText();
                char[] pass = tf_pass.getPassword();
                String pass_str = new String(pass);
                String hash_pass = make_hash(pass_str);

                //もしusernameまたはpassのどちらか一方が空白だった場合はエラーを表示させる
                if (username.equals(null) || username.equals("")){
                    JOptionPane.showMessageDialog(null, "ユーザー名を入力してください");
                }else if(pass_str.equals(null) || pass_str .equals("")){
                    JOptionPane.showMessageDialog(null, "パスワードを入力してください");
                } else {
                    out.println(ok[0]);//3
                    out.println(username);// 4
                    out.println(hash_pass);// 5
                    noerror[0] = true;
                }
            }
        });

        tf_pass.setColumns(10);
        tf_user.setColumns(10);
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

        //エラーなし（usernameもpasswordも正常に入力）になるまで以下のコードを実行しない
        while (true) {
            if (noerror[0] == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }


        /*---------------------------------------------------------------------------------------- */
        if (option[0] == 1) {
            Boolean LOGIN = false;
            LOGIN = Boolean.parseBoolean(in.readLine()); // 5

            if (LOGIN == true) {// ログイン成功のとき
                            
                while (true) {
                    frame.getContentPane().removeAll();//パネルp2を取り除く

                    //パネルp4の実装
                    JPanel p4 = new JPanel();
                    p4.setLayout(null);

                    JLabel label_choice = new JLabel("ログインに成功しました！");

                    Integer[] option2 = { null };

                    JButton create_chat = new JButton("新規作成");
                    create_chat.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            option2[0] = 1;// 6
                            out.println(option2[0]);
                        }
                    });

                    JButton join_chat = new JButton("既存に参加");
                    join_chat.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            option2[0] = 2;// 6
                            out.println(option2[0]);
                        }
                    });

                    JButton logout = new JButton("退出する");
                    logout.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            option2[0] = 3;// 6
                            out.println(option2[0]);
                        }
                    });

                    label_choice.setBounds(425, 136, 200, 25);
                    create_chat.setBounds(300, 236, 100, 25);
                    join_chat.setBounds(450, 236, 100, 25);
                    logout.setBounds(600, 236, 100, 25);
                    p4.add(label_choice);
                    p4.add(create_chat);
                    p4.add(join_chat);
                    p4.add(logout);
                    frame.add(p4);
                    frame.setVisible(true);
                    frame.validate();
                    frame.repaint();//画面を書き直す

                    // ３つのボタンのうちどれかが押されるまで以下のコードを実行しない
                    while (true) {
                        if (option2[0] == null) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            break;
                        }
                    }
                                
                    /*---------------------------------------------------------------------------------------- */
                    if (option2[0] == 1) {// 1.新規作成
                        //System.out.println("チャットルーム名を入力してください");
                        //String room_name = sc.nextLine();
                        //out.println(room_name);// 7



                        frame.getContentPane().removeAll();//パネルp4を取り除く

                        //パネルp5の実装
                        JPanel p5 = new JPanel();
                        p5.setLayout(null);

                        JLabel label_newchatname = new JLabel("新規のチャットルーム名：");
                        JTextField tf_newchatname = new JTextField();

                        Boolean[] ok3 = { null };
                        JButton ok3_btn = new JButton("OK");
                        ok3_btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ok3[0] = true;
                                String room_name = tf_newchatname.getText();
                                out.println(room_name);// 7
                            }
                        });

                        label_newchatname.setBounds(300, 236, 200, 25);
                        tf_newchatname.setBounds(525, 236, 200, 25);
                        ok3_btn.setBounds(675, 261, 50, 50);
                        p5.add(label_newchatname);
                        p5.add(tf_newchatname);
                        p5.add(ok3_btn);
                        frame.add(p5);
                        frame.setVisible(true);
                        frame.validate();
                        frame.repaint();//画面を書き直す

                        // okボタンが押されるまで以下のコードを実行しない
                        while (true) {
                            if (ok3[0] == null) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                break;
                            }
                        }

                        System.out.println(in.readLine());//8

                        //System.out.println("");
                    /*---------------------------------------------------------------------------------------- */
                    } else if (option2[0] == 2) {
                        System.out.println("参加するチャットルーム名を入力してください");
                        String room_name = sc.nextLine();
                        out.println(room_name);// 9

                        System.out.println(in.readLine());// 10
                        Boolean JOIN = false;
                        JOIN = Boolean.parseBoolean(in.readLine());// 10.5
                        if (JOIN == true) {
                            System.out.println("");

                            System.out.println("チャットを開始します。");
                            String room_mem = in.readLine();// 11
                            System.out.println(room_mem);
                            System.out.println("あなたは '" + room_mem + "' に参加しています。");
                            System.out.println("終了する際は'END'で終了してください。");
                            System.out.println("");

                            while (true) {
                                System.out.println("メッセージ：");
                                String message = sc.nextLine();
                                out.println(message);

                                /*
                                * String echo = in.readLine();// 13
                                * System.out.println("echo:"+ echo);
                                */

                                if (message.equals("END")) {
                                    System.out.println("");
                                    break;
                                }
                            }
                        } else {
                            System.out.println("");
                        }
                                
                    /*---------------------------------------------------------------------------------------- */
                    } else if (option2[0] == 3) {// 3.退出する
                        //System.out.println("終了します。");
                        //System.out.println("");
                        break;

                    /*---------------------------------------------------------------------------------------- */
                    }
                }
            } else {// ログイン失敗のとき
                frame.getContentPane().removeAll();//パネルp2を取り除く

                //パネルp3の実装
                JPanel p3 = new JPanel();
                p3.setLayout(null);

                JLabel label_login_failure = new JLabel("ログインに失敗しました。");

                Boolean[] ok2 = { null };
                JButton ok2_btn = new JButton("OK");
                ok2_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ok2[0] = true;
                    }
                });

                label_login_failure.setBounds(420, 236, 200, 25);
                ok2_btn.setBounds(570, 286, 50, 50);
                p3.add(label_login_failure);
                p3.add(ok2_btn);
                frame.add(p3);
                frame.setVisible(true);
                frame.validate();
                frame.repaint();//画面を書き直す

                // okボタンが押されるまで以下のコードを実行しない
                while (true) {
                    if (ok2[0] == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        gui(frame, in, out, sc);
                        break;
                    }
                }

            }

        /*---------------------------------------------------------------------------------------- */
        } else if (option[0] == 2) {// サインインしたとき
            System.out.println(in.readLine()); // 6
        }
    }

    public static void success(){
        
    }
}


