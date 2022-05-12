import java.io.*;
import java.net.Socket;

public class HTTPConnection implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final OutputStream out;

    public HTTPConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = socket.getOutputStream();
    }

    public void run() {
        try {
            readInputHeaders();
            writeResponse("<html><body><h1>HELLO!!!</h1></body></html>");
        } catch (IOException exception) {
            System.out.println("HTTP Connection exception: " + exception);
        } finally {
            try {
                socket.close();
            } catch (Exception exception) {
                System.out.println("Trouble with socket close " + exception);
            }
        }
        System.out.println("Client processing finished");
    }

    private void readInputHeaders() throws IOException {
        while (true) {
            String message = in.readLine();
            if (message.equals("")) {
                return;
            }
        }
    }

    private void writeResponse(String message) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + message.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + message;
        out.write(result.getBytes());
        out.flush();
    }
}
