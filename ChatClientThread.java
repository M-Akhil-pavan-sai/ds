import java.net.*;
import java.io.*;
public class ChatClientThread extends Thread {
    private ChatClient client=null;
    private Socket socket=null;
    private DataInputStream In=null;
    public ChatClientThread(ChatClient cli,Socket sock)
    {
        client=cli;
        socket=sock;
        try
        {
            In=new DataInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.out.println("error geting input stream:"+e);
            client.stop();
        }
        start();
    }
    public void close()
    {
        try
        {
            if(In!=null)
            In.close();
        }
        catch (IOException e)
        {
            System.out.println("error closing input stream:"+e);
        }
    }

    public void run()
    {
        while(true)
        {
            try
            {
                client.handle(In.readLine());
            }
            catch(IOException e)
            {
                System.out.println("Listening error"+e.getMessage()); client.stop();
            }
        }
    }
}