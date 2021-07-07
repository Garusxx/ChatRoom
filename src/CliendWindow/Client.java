package CliendWindow;

import Server.ClientInfo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Client {


    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private static boolean running;

    public Client(String name, String address, int port) {
        try{
            this.address = InetAddress.getByName(address);
            this.port = port;
            socket = new DatagramSocket();
            running = true;
            listend();
            send("\\con:" + name);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(String massage){
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

    private void listend(){
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
                            ClientWindow.prindToConsol(massage);
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }; listenTread.start();
    }

    private static boolean isConnected(String massage, DatagramPacket packet){
        if(massage.startsWith("\\com:")){
            return true;
        }
        return false;
    }




}
