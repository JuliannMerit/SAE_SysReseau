import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 4444);
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String message = bufferedReader.readLine();
            System.out.println(message);
            // envoyer un message au serveur écrit dans la console
            socket.getOutputStream().write(1);
            socket.getOutputStream().flush();


        }catch (Exception e){
            System.out.println(e);
        }
    }
}
