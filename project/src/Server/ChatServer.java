package Server;

import Client.ClientRead;
import Client.ClientWrite;
import Client.ListenerCenter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ChatServer {
    JFrame frame;
    //JTextArea area;
    //JTextField field;
    static JTextPane area,field;
    JButton button;
    JComboBox<String> user1;
    public static String serveToPerson;
    ServerListenerCenter listen;
    public static void main(String[] args) {
        new ChatServer().start();
    }
    public ChatServer(){
        frame=new JFrame("服务器端");
        frame.setBounds(200,200,600,500);
        frame.getContentPane().setLayout(null);
       // area=new JTextArea();
        area=new JTextPane();
        area.setBounds(20,20,400,300);
        field=new JTextPane();
       // field=new JTextField();
        field.setBounds(20,350,300,30);
        frame.add(field);
        frame.add(area);

        //监听器
        listen=new ServerListenerCenter();
        listen.setArea(area);
        listen.setField(field);

        user1 = new JComboBox<>();
        user1.setBounds(450,20,120,40);
        user1.addItem("发布消息对象");
        user1.addItem("向张三发布消息");
        user1.addItem("向李四发布消息");
        user1.addItem("向王五发布消息");
        user1.addItem("向所有人发布消息");
        frame.add(user1);
        user1.addItemListener(e -> {
            if(e.getStateChange()== ItemEvent.SELECTED){
                if(user1.getSelectedItem().equals("向张三发布消息")){
                    serveToPerson="张三";
                }
                else if(user1.getSelectedItem().equals("向李四发布消息")){
                    serveToPerson="李四";
                }
                else if(user1.getSelectedItem().equals("向王五发布消息")){
                    serveToPerson="王五";
                }
                else if(user1.getSelectedItem().equals("向所有人发布消息")){
                    serveToPerson="all";
                }
            }
        });

        button=new JButton("发送");
        button.setBounds(330,350,90,30);
        frame.add(button);
        button.addActionListener(listen);


        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void start(){
        try {
            ServerSocket x=new ServerSocket(8888);

            while(true){
                Socket socket = x.accept();//会阻塞
                ClientHandle client = new ClientHandle(socket);
                new Thread(client).start();
            }

//            Socket s=x.accept();
//            InputStream is = s.getInputStream();
//            DataInputStream dis=new DataInputStream(is);
//            OutputStream os=s.getOutputStream();
//            DataOutputStream dos=new DataOutputStream(os);
//
//            ServerRead serverRead=new ServerRead(dis,area);
//            serverRead.start();
//            ServerWrite serverWrite=new ServerWrite(dos,area);
//            serverWrite.start();
//            listen.setServerWrite(serverWrite);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
