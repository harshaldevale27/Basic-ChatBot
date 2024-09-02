import java.net.Socket;//for using the networking concept
import java.io.*;
public class Client {
    Socket socket;
    BufferedReader br;// for the read br for reading
    PrintWriter out;//for writing and out for writing
    //constructor
    public Client()
    {
        try{
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection is Done...");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));//jo input stream hmne socket se nikali hai vo br me jayegi aur vo read krega
            out = new PrintWriter(socket.getOutputStream());//it is digect output stream aur vo write krega
            startReading();//function call
            startWriting();//jub tak gui ready nahhi hota 

        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    //here we use the multithreading
    //start reading[method]
    public void startReading()//thread data user se lega and send krega server ko 
    {
        // thread read krke deta rahega
       //here we created the therad
        Runnable r1=()->//creating the runnable using lambda exprassion
        {
            System.out.println("reader started...");
            try{//while ke andar exception aaye hi to loop break ho jayeha aur thread band ho jayrga
                    while(true)//bar bar read kre ga infinite times
                    {
                    String msg=br.readLine();// br read krega line by line and store in msg
                    if( msg.equals("exit"))// user ne exit type kiya  to chat terminated ho jayega
                        {
                            System.out.println("Server terminated the chat");
                            socket.close();
                            break;
                        }
                            System.out.println("Server : "+msg);
                    }
                }catch(Exception e)
                {
                    //e.printStackTrace();
                    System.out.println("Connection is Closed");
                }   
        };
        new Thread(r1).start();//here start the thread with the hetp of thread class and pass the value of thread and .start
    }
    //start writing [ method]
    public void startWriting()
    {
        //thread data user se lega and send krega server ko 
        Runnable r2=()->
        {
            System.out.println("writer started...");
            try{
                    while(true&& !socket.isClosed())
                    {
                        
                            BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));//br1 ki help se data input se le na hai iteh the help of keybord
                            String content = br1.readLine();
                            out.println(content);//out ki help se data client tak send krega
                            out.flush();// forcefully data send krta hai
                            if(content.equals("exit"))
                            {
                                socket.close();
                                break;
                            }
                    }
                    System.out.println("Connection is Closed");
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        };
        new Thread(r2).start();//here start the thread with the hetp of thread class and pass the value of thread and .start

    }


    public static void main(String args[])
    {
        System.out.println("this is client..");
        new Client();
    }
}
