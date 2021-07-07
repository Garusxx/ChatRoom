package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

    private static DatagramSocket socket;
    private static boolean running;
    private static int ClientId;
    private static ArrayList<ClientInfo> clientInfos = new ArrayList<ClientInfo>();

    public static void start(int port){

        try{
            socket = new DatagramSocket(port);
            System.out.println("Server start at port, " + port);
            running = true;
            listend();

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    private static void listend(){
        Thread listenTread = new Thread("Chatprogram listener..."){
            public void run(){
                try{
                    while (running){
                        byte[] data = new byte[1042];
                        DatagramPacket packet = new DatagramPacket(data,data.length);
                        socket.receive(packet);
                        String massage = new String(data);
                        massage = massage.substring(0, massage.indexOf("\\e"));
                        if(!isConnected(massage,packet)){
                            broudcast(massage);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }; listenTread.start();
    }

    private static void broudcast(String massage){
        for(ClientInfo info : clientInfos){
            seend(massage, info.getAddress(), info.getPort());
        }

    }

    private static void seend(String massage, InetAddress address, int port){
        try{
            massage += "\\e";
            byte[] data = massage.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length,address,port);
            socket.send(packet);
            System.out.println("Send Massage: " + address.getHostAddress() + ":" + port);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isConnected(String massage, DatagramPacket packet){
        if(massage.startsWith("\\con:")){
            String name = massage.substring(massage.indexOf(":") + 1);

            clientInfos.add(new ClientInfo(packet.getAddress(), packet.getPort(),name, ClientId++));
            broudcast("User: " + name + ", Connected");
            return true;
        }
        return false;
    }

    public static void stop(){
        running = false;
    }


}
