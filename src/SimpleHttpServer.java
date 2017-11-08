import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SimpleHttpServer {

    private int port=8080;
    private ServerSocketChannel serverSocketChannel = null;
    private ExecutorService executorService;
    private static final int POOL_MULTIPLE = 4;

    public SimpleHttpServer() throws IOException {
        executorService= Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE);
        serverSocketChannel= ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.socket().bind(new InetSocketAddress("172.20.0.34",8080));
        System.out.println("Server Starting...");
    }

    public void service() {
        while (true) {
            SocketChannel socketChannel=null;
            try {
                socketChannel = serverSocketChannel.accept();
                executorService.execute(new Handler(socketChannel));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[])throws IOException {
        new SimpleHttpServer().service();
    }

    class Handler implements Runnable{
        private SocketChannel socketChannel;
        public Handler(SocketChannel socketChannel){
            this.socketChannel=socketChannel;
        }
        public void run(){
            handle(socketChannel);
        }

        public void handle(SocketChannel socketChannel){
            try {
                Socket socket=socketChannel.socket();
                System.out.println("ddd" +
                        socket.getInetAddress() + ":" +socket.getPort());

                ByteBuffer buffer=ByteBuffer.allocate(4000);
                socketChannel.read(buffer);
                buffer.reset();//  缓冲区position还原，设定limit
                String request=decode(buffer);
                System.out.print(request);

                StringBuffer sb=new StringBuffer("HTTP/1.1 200 OK\r\n");
                sb.append("Content-Type:text/html\r\n\r\n");
                socketChannel.write(encode(sb.toString()));

                FileInputStream in;

                String firstLineOfRequest=request.substring(0,request.indexOf("\r\n"));
                if(firstLineOfRequest.indexOf("login.htm")!=-1)
                    in=new FileInputStream("login.html");
                else
                    in=new FileInputStream("post.html");

                FileChannel fileChannel=in.getChannel();
                fileChannel.transferTo(0,fileChannel.size(),socketChannel);
                fileChannel.close();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try{
                    if(socketChannel!=null)socketChannel.close();
                }catch (IOException e) {e.printStackTrace();}
            }
        }
        private Charset charset=Charset.forName("GBK");
        public String decode(ByteBuffer buffer){
            CharBuffer charBuffer= charset.decode(buffer);
            return charBuffer.toString();
        }
        public ByteBuffer encode(String str){
            return charset.encode(str);
        }
    }
}
