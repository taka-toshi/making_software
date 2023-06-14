import java.io.*;
//import java.net.*;
import java.util.*;
import java.math.BigInteger;
import java.security.*;

public class func {
     // execute sql
     public static void execute_sql(File db, String sql) {
        String[] cmd = { "sqlite3", db.getAbsolutePath(), sql };
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    

    // execute_sql_return_data
    public static List<String> execute_sql_return_data(File db, String sql) {
        List<String> data = new ArrayList<String>();
        String[] cmd = { "sqlite3", db.getAbsolutePath(), sql };
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        return data;
    }

    // create table
    public static void create_table(File db) {
        String sql = "CREATE TABLE IF NOT EXISTS userstable (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT,password TEXT)";
        execute_sql(db, sql);
    }

    // create table　for chatroom's name
    public static void create_table_chatname(File db) {
        String sql = "CREATE TABLE IF NOT EXISTS chatnametable (id INTEGER PRIMARY KEY AUTOINCREMENT, chatname TEXT)";
        execute_sql(db, sql);
    }

    // add user to database
    public static void add_user(File db, String username, String password) {
        String sql = "INSERT INTO userstable(username,password) VALUES ('" +
                username +
                "','" +
                password +
                "')";
        execute_sql(db, sql);
    }

    // add chatroom's name to database
    public static void add_chatname(File db, String room_name) {
        String sql = "INSERT INTO chatnametable(room_name) VALUES ('" + 
                room_name + 
                "')";
        execute_sql(db, sql);
    }

    // login user
    public static List<String> login_user(
            File db,
            String username,
            String password) {
        String sql = "SELECT * FROM userstable WHERE username = '" +
                username +
                "' AND password = '" +
                password +
                "'";
        List<String> data = new ArrayList<String>();
        data = execute_sql_return_data(db, sql);
        return data;
    }

    // check user
    public static List<String> check_user(File db, String username) {
        String sql = "SELECT  EXISTS(SELECT * FROM userstable WHERE username = '" +
                username +
                "')AS customer_check;";
        List<String> data = new ArrayList<String>();
        data = execute_sql_return_data(db, sql);
        return data;
    }

    // check room
    public static List<String> check_chat_room(File db, String room_name){
        String sql = "SELECT  EXISTS(SELECT * FROM chatnametable WHERE room_name = '" + 
                room_name + 
                "')AS customer_check;";
        List<String> data2 = new ArrayList<String>();
        data2 = execute_sql_return_data(db, sql);
        return data2;
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
