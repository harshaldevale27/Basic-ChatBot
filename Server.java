import java.net.*;//for using the networking concept
import java.io.*;
class Server
{
    ServerSocket server;//serversocket is se hum srever bnate hai java me AND ***server** is the name  of server
    Socket socket;//socket is the name of the client
    BufferedReader br;// for the read br for reading
    PrintWriter out;//for writing and out for writing
    //constructor
    public Server()
    {
        try {
            server= new ServerSocket(7777);//7777 is the port number it throw the exception we write in try catch
            System.out.println("server is ready to acceept connection");
            System.out.println("waiting...");
            socket=server.accept();// server accetp the connection of client and it store in socket and it pass to the br and out for read and write
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));//jo input stream hmne socket se nikali hai vo br me jayegi aur vo read krega
            out = new PrintWriter(socket.getOutputStream());//it is digect output stream aur vo write krega
            startReading();//function call
            startWriting();
        }catch(Exception e){
            e.printStackTrace();//it show the exception
        }


    }
    //here we use the multithreading
    public void startReading()
    {
        // thread read krke deta rahega
       //here we created the therad
        Runnable r1=()->//creating the runnable using lambda exprassion
        {
            System.out.println("reader started...");
            try{
                    while(true)//bar bar read kre ga infinite times
                    {
                        String msg=br.readLine();// br read krega line by line and store in msg
                        if( msg.equals("exit"))// user ne exit type kiya  to chat terminated ho jayega
                        {
                            System.out.println("client terminated the chat");
                            socket.close();
                            break;

                        }
                        System.out.println("Client : "+msg);
                    }
            }catch(Exception e)
            {
                //e.printStackTrace();
                System.out.println("Connection is Closed");
            }

        };
        new Thread(r1).start();//here start the thread with the hetp of thread class and pass the value of thread and .start
    }

    public void startWriting()
    {
        //thread data user se lega and send krega client tak
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
        System.out.println("this is server.. going to start server ");
        new Server();
    }

}