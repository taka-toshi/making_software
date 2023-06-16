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
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true); // 送信バッファ設定

            // frameを実装する
            JFrame frame = new JFrame("MyApplication");
            // frame.setTitle("MyApplication!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Rectangle table = new Rectangle(1000, 500);
            frame.setBounds(table);// frameのサイズを指定→table
            frame.setLocationRelativeTo(null);// 画面の真ん中にframeを表示

            System.out.println(in.readLine()); // 1

            gui(frame, in, out, sc);

        } finally {
            System.out.println("closing...");
            socket.close();
            sc.close();
        }
    }

    public static void gui(JFrame frame, BufferedReader in, PrintWriter out, Scanner sc) throws IOException {
        frame.getContentPane().removeAll();// パネルがあれば取り除く

        // パネルp1をframeに追加する
        JPanel p1 = new JPanel();
        p1.setLayout(null);

        // final Integer[] option = { null };
        Integer[] option = { null };

        // ログインボタンの実装
        JButton login_btn = new JButton("ログイン");
        login_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option[0] = 1;
                out.println(option[0]); // 2
            }
        });

        // サインアップボタンの実装
        JButton signup_btn = new JButton("サインイン");
        signup_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option[0] = 2;
                out.println(option[0]);// 2
            }
        });

        login_btn.setBounds(375, 225, 100, 50);
        signup_btn.setBounds(525, 225, 100, 50);
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
        frame.getContentPane().removeAll();// パネルp1を取り除く

        // パネルp2の実装
        JPanel p2 = new JPanel();
        p2.setLayout(null);

        JLabel label_user = new JLabel("ユーザー名：");
        JLabel label_pass = new JLabel("パスワード：");
        JTextField tf_user = new JTextField();
        JPasswordField tf_pass = new JPasswordField();

        // final Boolean[] ok = { null };
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

                // もしusernameまたはpassのどちらか一方が空白だった場合はエラーを表示させる
                if (username.equals(null) || username.equals("")) {
                    JOptionPane.showMessageDialog(null, "ユーザー名を入力してください");
                } else if (pass_str.equals(null) || pass_str.equals("")) {
                    JOptionPane.showMessageDialog(null, "パスワードを入力してください");
                } else {
                    out.println(ok[0]);// 3
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
        frame.repaint();// 画面を書き直す

        // エラーなし（usernameもpasswordも正常に入力）になるまで以下のコードを実行しない
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
                    frame.getContentPane().removeAll();// パネルp2を取り除く

                    // パネルp4の実装
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
                    frame.repaint();// 画面を書き直す

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
                        frame.getContentPane().removeAll();// パネルp4を取り除く

                        // パネルp5の実装
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
                        frame.repaint();// 画面を書き直す

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

                        String chat_f = in.readLine();// 8

                        frame.getContentPane().removeAll();// パネルp5を取り除く

                        // パネルp6の実装
                        JPanel p6 = new JPanel();
                        p6.setLayout(null);

                        JLabel label_chatresult = new JLabel(chat_f);

                        Boolean[] ok4 = { null };
                        JButton ok4_btn = new JButton("OK");
                        ok4_btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ok4[0] = true;
                            }
                        });

                        label_chatresult.setBounds(420, 236, 500, 25);
                        ok4_btn.setBounds(570, 286, 50, 50);
                        p6.add(label_chatresult);
                        p6.add(ok4_btn);
                        frame.add(p6);
                        frame.setVisible(true);
                        frame.validate();
                        frame.repaint();// 画面を書き直す

                        // okボタンが押されるまで以下のコードを実行しない
                        while (true) {
                            if (ok4[0] == null) {
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
                    } else if (option2[0] == 2) {// 2. 既存に参加
                        frame.getContentPane().removeAll();// パネルp6を取り除く

                        // パネルp7の実装
                        JPanel p7 = new JPanel();
                        p7.setLayout(null);

                        JLabel label_chatname = new JLabel("既存のチャットルーム名：");
                        JTextField tf_chatname = new JTextField();

                        Boolean[] ok5 = { null };
                        JButton ok5_btn = new JButton("OK");
                        ok5_btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ok5[0] = true;
                                String room_name = tf_chatname.getText();
                                out.println(room_name);// 9
                            }
                        });

                        label_chatname.setBounds(300, 236, 200, 25);
                        tf_chatname.setBounds(525, 236, 200, 25);
                        ok5_btn.setBounds(675, 261, 50, 50);
                        p7.add(label_chatname);
                        p7.add(tf_chatname);
                        p7.add(ok5_btn);
                        frame.add(p7);
                        frame.setVisible(true);
                        frame.validate();
                        frame.repaint();// 画面を書き直す

                        // okボタンが押されるまで以下のコードを実行しない
                        while (true) {
                            if (ok5[0] == null) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                break;
                            }
                        }

                        // System.out.println(in.readLine());// 10
                        String join_message = in.readLine();// 10
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

                            // public static void chatting(JFrame frame){
                            while (true) {
                                frame.getContentPane().removeAll();// パネルp7を取り除く

                                // パネルp8の実装
                                JPanel p8 = new JPanel();
                                p8.setLayout(null);

                                JLabel label_chat_success = new JLabel(join_message);
                                // chat_log.txtを表示
                                JLabel label_messagelabel = new JLabel("メッセージ：");
                                JTextField tf_message = new JTextField();
                                JButton send_btn = new JButton("SEND");
                                JButton quit_btn = new JButton("QUIT");

                                send_btn.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        String message = tf_message.getText();
                                        out.println(message);
                                    }
                                });

                                quit_btn.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                    }
                                });

                                tf_message.setColumns(50);
                                label_chat_success.setBounds(100, 50, 800, 25);
                                label_messagelabel.setBounds(100, 350, 200, 25);
                                tf_message.setBounds(300, 350, 200, 25);
                                send_btn.setBounds(870, 350, 30, 25);
                                quit_btn.setBounds(390, 425, 35, 25);
                                p8.add(label_chat_success);
                                p8.add(tf_message);
                                p8.add(send_btn);
                                p8.add(quit_btn);
                                frame.add(p8);
                                frame.setVisible(true);
                                frame.validate();
                                frame.repaint();// 画面を書き直す
                            }
                            // }

                        } else {
                            frame.getContentPane().removeAll();// パネルp7を取り除く

                            // パネルp9の実装
                            JPanel p9 = new JPanel();
                            p9.setLayout(null);

                            JLabel label_chat_failure = new JLabel(join_message);
                            Boolean[] ok6 = { null };
                            JButton ok6_btn = new JButton("OK");
                            ok6_btn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    ok6[0] = true;
                                }
                            });

                            label_chat_failure.setBounds(420, 236, 500, 25);
                            ok6_btn.setBounds(570, 286, 50, 50);
                            p9.add(label_chat_failure);
                            p9.add(ok6_btn);
                            frame.add(p9);
                            frame.setVisible(true);
                            frame.validate();
                            frame.repaint();// 画面を書き直す

                            // okボタンが押されるまで以下のコードを実行しない
                            while (true) {
                                if (ok6[0] == null) {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    break;
                                }
                            }
                        }

                        /*---------------------------------------------------------------------------------------- */
                    } else if (option2[0] == 3) {// 3.退出する
                        frame.getContentPane().removeAll();// パネルを取り除く

                        // パネルp10の実装
                        JPanel p10 = new JPanel();
                        p10.setLayout(null);

                        JLabel label_logout = new JLabel("退出できました！");
                        Boolean[] ok7 = { null };
                        JButton ok7_btn = new JButton("OK");
                        ok7_btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ok7[0] = true;
                            }
                        });

                        label_logout.setBounds(420, 236, 500, 25);
                        ok7_btn.setBounds(570, 286, 50, 50);
                        p10.add(label_logout);
                        p10.add(ok7_btn);
                        frame.add(p10);
                        frame.setVisible(true);
                        frame.validate();
                        frame.repaint();// 画面を書き直す

                        // okボタンが押されるまで以下のコードを実行しない
                        while (true) {
                            if (ok7[0] == null) {
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

                        /*---------------------------------------------------------------------------------------- */
                    }
                }
            } else {// ログイン失敗のとき
                frame.getContentPane().removeAll();// パネルp2を取り除く

                // パネルp3の実装
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
                frame.repaint();// 画面を書き直す

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
            Boolean SIGNUP = false;
            SIGNUP = Boolean.parseBoolean(in.readLine()); // 6.0

            if (SIGNUP == true) {// サインイン成功のとき

                frame.getContentPane().removeAll();// パネルを取り除く

                // パネルp11の実装
                JPanel p11 = new JPanel();
                p11.setLayout(null);

                JLabel label_signup = new JLabel("サインインが完了しました！");
                Boolean[] ok8 = { null };
                JButton ok8_btn = new JButton("OK");
                ok8_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ok8[0] = true;
                    }
                });

                label_signup.setBounds(420, 236, 500, 25);
                ok8_btn.setBounds(570, 286, 50, 50);
                p11.add(label_signup);
                p11.add(ok8_btn);
                frame.add(p11);
                frame.setVisible(true);
                frame.validate();
                frame.repaint();// 画面を書き直す

                // okボタンが押されるまで以下のコードを実行しない
                while (true) {
                    if (ok8[0] == null) {
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
            } else {// サインアップできなかった場合
                frame.getContentPane().removeAll();// パネルを取り除く

                // パネル12の実装
                JPanel p12 = new JPanel();
                p12.setLayout(null);

                JLabel label_login_failure = new JLabel(in.readLine()); // 6.1

                Boolean[] ok9 = { null };
                JButton ok9_btn = new JButton("OK");
                ok9_btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ok9[0] = true;
                    }
                });

                label_login_failure.setBounds(420, 236, 200, 25);
                ok9_btn.setBounds(570, 286, 50, 50);
                p12.add(label_login_failure);
                p12.add(ok9_btn);
                frame.add(p12);
                frame.setVisible(true);
                frame.validate();
                frame.repaint();// 画面を書き直す

                // okボタンが押されるまで以下のコードを実行しない
                while (true) {
                    if (ok9[0] == null) {
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
        }
    }

    /*
     * public static void chatting(JFrame frame, String join_message, BufferedReader
     * in, PrintWriter out, Scanner sc) throws IOException {
     * frame.getContentPane().removeAll();//パネルp7を取り除く
     * 
     * //パネルp8の実装
     * JPanel p8 = new JPanel();
     * p8.setLayout(null);
     * 
     * JLabel label_chat_success = new JLabel(join_message);
     * //chat_log.txtを表示
     * JLabel label_messagelabel = new JLabel("メッセージ：");
     * JTextField tf_message = new JTextField();
     * JButton send_btn = new JButton("SEND");
     * JButton quit_btn = new JButton("QUIT");
     * 
     * send_btn.addActionListener(new ActionListener() {
     * public void actionPerformed(ActionEvent e) {
     * String message = tf_message.getText();
     * out.println(message);
     * 
     * gui(frame, in, out, sc);
     * }
     * });
     * 
     * quit_btn.addActionListener(new ActionListener() {
     * public void actionPerformed(ActionEvent e) {
     * 
     * }
     * });
     * 
     * tf_message.setColumns(50);
     * label_chat_success.setBounds(100, 50, 800, 25);
     * label_messagelabel.setBounds(100, 350, 200,25);
     * tf_message.setBounds(300, 350, 200,25);
     * send_btn.setBounds(870, 350, 30,25);
     * quit_btn.setBounds(390, 425, 35,25);
     * p8.add(label_chat_success);
     * p8.add(tf_message);
     * p8.add(send_btn);
     * p8.add(quit_btn);
     * frame.add(p8);
     * frame.setVisible(true);
     * frame.validate();
     * frame.repaint();//画面を書き直す
     * }
     */
}
