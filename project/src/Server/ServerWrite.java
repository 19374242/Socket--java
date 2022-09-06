package Server;


import javax.swing.*;
import java.io.*;

public class ServerWrite extends Thread{
    DataOutputStream dos;
    JTextArea area;
    String info;
    public ServerWrite(DataOutputStream dos,JTextArea area){
        this.dos=dos;
        this.area=area;
    }
    public synchronized void setInfo(String s){
        info=s;
        this.notify();
    }
    public synchronized void run(){
        while(true){
            try {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                dos.writeUTF(info);
                area.append("我说:"+info+"\n");
                if(info.equals("bye")){
                    System.out.println("自己下线");
                    System.exit(0);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
    }
}