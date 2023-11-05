import java.io.InputStream;
import java.util.HashMap;


public class HeadParse {
    private InputStream inputStream=null;
    private String URL,Method;
    private String[] args;
    private HashMap<String,String> hashMap=new HashMap<>();

    public HeadParse(InputStream input)
    {
        this.inputStream=input;
    }

    public void Parse()
    {
        StringBuffer Request=new StringBuffer(9000);
        int num;
        byte[] buffer=new byte[9000];
        try{
            num=inputStream.read(buffer);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            num=-1;
        }
        for(int j=0;j<num;j++)
        {
            Request.append((char)buffer[j]);
        }
        String requestString=Request.toString();
        parseURL(requestString);

    }

    public void parseURL(String requestString)
    {
        int pos1=-1,pos2=-1;
        System.out.println("requestString");
        System.out.println(requestString);
        this.Method=requestString.substring(0,requestString.indexOf(" "));
        if(Method.equals("GET"))
        {
            pos1=requestString.indexOf(" ");
            if(pos1!=-1)
            {
                    pos2 = requestString.indexOf(" ", pos1 + 1);
                    if(pos2>pos1)
                    {
                        URL=requestString.substring(pos1+1,pos2);
                        if(URL.equals("/"))
                            URL="/index.html";
                    }
                System.out.println("URL:"+URL);
            }

        }
        //其他方法
        else
        {
            pos1=requestString.indexOf(" ");
            if(pos1!=-1)
            {
                pos2 = requestString.indexOf(" ", pos1 + 1);
                if(pos2>pos1)
                {
                    URL=requestString.substring(pos1+1,pos2);
                }
            }
            String[] temp=requestString.split("\n");
            args=temp[temp.length - 1].split("&");
            for (int i = 0; i < args.length; i++)
            {
                String[] entry = args[i].split("=");
                hashMap.put(entry[0], entry[1]);
            }

        }

    }


    public String getMethod()
    {
        return Method;
    }

    public String getURL()
    {
        return URL;
    }

    public HashMap<String,String> getHash()
    {
        return hashMap;
    }

}
