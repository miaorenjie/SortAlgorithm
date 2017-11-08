import java.io.*;
import java.net.*;

class Client{
    public static void main(String args[])throws IOException{

        URL url=new URL("http://localhost:8080/post.html");
        URLConnection connection=url.openConnection();//实现连接

        System.out.println("Content Type："+connection.getContentType());
        System.out.println("Content Length："+
                connection.getContentLength());
        InputStream in=connection.getInputStream();//Input Response
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();

        byte[] buff=new byte[1024];
        int len=-1;
        while((len=in.read(buff))!=-1){
            buffer.write(buff,0,len);
        }

        System.out.println(new String(buffer.toByteArray()));
    }
}
