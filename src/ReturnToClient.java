import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ReturnToClient
{
    HeadParse request;
    OutputStream output;

    public ReturnToClient(OutputStream output,HeadParse request)
    {
        this.output = output;
        this.request = request;
    }


    public void SentBack() throws IOException
    {
        byte[] bytes = new byte[9000];
        FileInputStream fileInputStream = null;
        try
        {
            String method = request.getMethod();
            //如果是GET方法
            if (method.equals("GET"))
            {
                File file = new File("D:\\STUDY\\java\\IdeaProjects\\WebServer\\sources"+ request.getURL());
                if (file.exists())
                {
                    FileInputStream temp_file = new FileInputStream(file);
                    byte[] byte_stream = temp_file.readAllBytes();
                    //html文件
                    if (request.getURL().contains("html"))
                    {
                        String code = new String(byte_stream);
                        String result = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type:text/html\r\n" +
                                "Content-Length:" + byte_stream.length + "\r\n" +
                                "\r\n" + code;
                        output.write(result.getBytes());
                    }
                    //图片文件
                    else if (request.getURL().contains("jpg"))
                    {
                        String header = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type:image/jpg\r\n" +
                                "Content-Length:" + byte_stream.length + "\r\n" +
                                "\r\n";
                        output.write(header.getBytes(StandardCharsets.UTF_8));
                        output.write(byte_stream);
                    }
                    //其他文件
                    else
                    {
                        String header = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type:text/plain\r\n" +
                                "Content-Length:" + byte_stream.length + "\r\n" +
                                "\r\n";
                        output.write(header.getBytes(StandardCharsets.UTF_8));
                        output.write(byte_stream);
                    }
                }
                //file not found
                else
                {
                    String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                            "Content-Type:text/html\r\n" +
                            "Content-Length:23\r\n" +
                            "\r\n" +
                            "<h1>404 File Not Found </h1>";
                    output.write(errorMessage.getBytes());
                }
            }
            //其他方法
            else
            {
                System.out.println("其他方法"+method);
                System.out.println(request.getURL());
                if (request.getURL().contains("dopost"))
                {
                    HashMap<String, String> information = request.getHash();

                    System.out.println("login"+information.get("login"));
                    System.out.println("pass"+information.get("pass"));
                    if (information.get("login").equals("abcd") && information.get("pass").equals("abcd"))
                    {
                        File file = new File("D:\\STUDY\\java\\IdeaProjects\\WebServer\\sources\\login.jpg");
                        FileInputStream temp_file = new FileInputStream(file);
                        byte[] byte_stream = temp_file.readAllBytes();
                         String header = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type:image/jpg\r\n" +
                                "Content-Length:" + byte_stream.length + "\r\n" +
                                "\r\n";
                        output.write(header.getBytes(StandardCharsets.UTF_8));
                        output.write(byte_stream);
                    }
                    else
                    {
                        String errorMessage = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type:text/html\r\n" +
                                "Content-Length:22\r\n" +
                                "\r\n" +
                                "<h1>Login Failed!</h1>";
                        output.write(errorMessage.getBytes());
                    }
                }

            }
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            if (fileInputStream != null)
            {
                fileInputStream.close();
            }
        }
    }
}
