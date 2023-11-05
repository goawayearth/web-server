import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{
    public void work()
    {
        ServerSocket serverSocket = null;
        int port = 80;
        try
        {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        while (true)
        {
            Socket socket = null;
            InputStream input = null;//输入的字符串
            OutputStream output = null;//输出的字符串
            try
            {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                HeadParse headParse=new HeadParse(input);
                //得到了url和method
                headParse.Parse();

                ReturnToClient returnToClient=new ReturnToClient(output,headParse);
                returnToClient.SentBack();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        HttpServer server = new HttpServer();
        server.work();
    }
}
