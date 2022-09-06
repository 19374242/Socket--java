package Server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

public class ServerRead extends Thread{
    DataInputStream dis;
    JTextArea area;
    public ServerRead(DataInputStream dis, JTextArea area){
        this.dis=dis;
        this.area=area;
    }
    public void run(){
        String info;
        while(true){
            try {
                info=dis.readUTF();
                area.append("对方说:"+info+"\n");
//                System.out.println("对方说"+info);
                if(info.equals("bye")){
                    System.out.println("对方下线");
                    System.exit(0);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}