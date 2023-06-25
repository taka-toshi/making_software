import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    private static ServerSocket serverSocket;
    private Socket socket;

    public FileServer(){
        try {
            final int PORT = 8070;
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        FileServer fileServer = new FileServer();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                fileServer.socket = socket;
                fileServer.sync();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void sync() throws IOException {
        // クライアントからのリクエストを受け付け
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String fileName = in.readLine();
        //System.out.println("Requested file: " + fileName);
        if (fileName == null) {
            //System.out.println("File name is null.");
            return;
        }
        // ファイルが存在しない場合は終了
        File file = new File(fileName);
        if (!file.exists()) {
            //System.out.println("File does not exist: " + fileName);
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

        // ファイルの内容をクライアントに送信
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(fileContent);
        outputStream.flush();
        outputStream.close();
    }
}
