import java.io.*;
import java.net.Socket;

public class FileClient {

    private static Socket socket;

    public FileClient(Socket socket) {
        this.socket = socket;
    }

    public static void request(String fileName,String username) throws IOException {
        // サーバーにファイルの内容をリクエストして受信
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        out.println(fileName);

        InputStream inputStream = socket.getInputStream();
        // 1秒待機
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        InputStream inputStream2 = inputStream;
        inputStream.close();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        System.out.println("check point 3");

        // inputStream.read()の値を変数に代入する
        Integer inputStreamRead = inputStream2.read();
        // 読んだ値を保存する変数を定義
        Integer inputStreamReadSave = 0;
        while (true) {
            System.out.println(inputStreamRead);
            System.out.println(inputStreamReadSave);
            if (inputStreamReadSave == inputStreamRead) {
                break;
            }
            try {
                bytesRead = inputStream2.read(buffer);
            } catch (IOException e) {
                System.out.println(e);
                break;
            }
            if (bytesRead == -1) {
                break;
            }
            byteArrayOutputStream.write(buffer, 0, bytesRead);
            inputStreamReadSave += bytesRead;
            System.out.println("check point 4.1");
        }
        System.out.println("check point 4.9");
        inputStream2.close();
        System.out.println("check point 5");
        byte[] fileContent = byteArrayOutputStream.toByteArray();
        System.out.println("check point 6");

        // 受信したファイルの内容をファイルに保存
        fileName = username + "_" + fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(fileContent);
        fileOutputStream.close();
        System.out.println("File saved to " + fileName);
    }
}
