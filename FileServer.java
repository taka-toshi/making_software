import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    private static Socket socket;

    public FileServer(Socket socket) {
        this.socket = socket;
    }

    public static void sync() throws IOException {
        while (true) {
            // クライアントからのリクエストを受け付け
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fileName = in.readLine();
            System.out.println("Requested file: " + fileName);
            if (fileName == null) {
                System.out.println("File name is null.");
                return;
            }
            // ファイルが存在しない場合は終了
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File does not exist: " + fileName);
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            byteArrayOutputStream.close();
            byte[] fileContent = byteArrayOutputStream.toByteArray();
            // 確認のためファイルの内容を保存
            FileOutputStream fileOutputStream = new FileOutputStream("tmp_" + fileName);
            fileOutputStream.write(fileContent);
            fileOutputStream.close();
            System.out.println("File saved to tmp_" + fileName);

            // ファイルの内容をクライアントに送信
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(fileContent);
            System.out.println("check point 7");
        }
    }
}
