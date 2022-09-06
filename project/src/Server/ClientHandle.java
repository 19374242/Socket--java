package Server;

import Client.ChatClient;
import Client.ClientWrite;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ClientHandle implements Runnable {
    private Socket s;
    private String userName = "";
    private DataInputStream input = null;
    public DataOutputStream output = null;
    private boolean connected = false;
    private BufferedOutputStream fout = null;
    private String saveFilePath = "";
    public static HashMap<String, ClientHandle> clients = new HashMap<>();
    public ClientHandle(Socket s) {
        this.s = s;
        try {
            input = new DataInputStream(s.getInputStream());
            output = new DataOutputStream(s.getOutputStream());
            connected = true;
//            userName=ChatClient.fromClient.get(ChatClient.from);
//            clients.put(userName,this);
//            System.out.println("1");
//            System.out.println(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (connected) {
                String msg[] = input.readUTF().split("#");//msg={"  ","登录用户","目标用户","要发送的信息"}
                switch (msg[0]) {
                    case "FILE":
                    case "FILEFILE":
                        byte[] buff = new byte[1024];
                        String s="C:/Users/高远/Desktop/serve/serve.jpg";
                        if(msg[0].equals("FILEFILE"))
                            s="C:/Users/高远/Desktop/serve/serve.txt";
                        FileOutputStream os= new FileOutputStream(s);
                        int len = 1;
                        while ((len = input.read(buff)) != -1) {
                            os.write(buff, 0, len);
                            if(len<1024) break;
                        }
                        ClientHandle cFile = clients.get(msg[2]);//获取目标用户的连接
                        if (cFile != null) {
                            if(msg[0].equals("FILE"))
                                cFile.output.writeUTF(userName+"#FILE");
                            else if(msg[0].equals("FILEFILE"))
                                cFile.output.writeUTF(userName+"#FILEFILE");
                            byte[] buff1 = new byte[1024];
                            FileInputStream fileInputStream = new FileInputStream(s);
                            len = -1;
                            while ((len = fileInputStream.read(buff1)) != -1) {
                                cFile.output.write(buff1, 0, len);
                            }
                        }
                        break;
                    case "1":
                        userName=msg[1];
                        clients.put(userName,this);
                        StyledDocument styledDocument=ChatServer.area.getStyledDocument();
                        Date nowDate=new Date();
                        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String timeStr=dateFormat.format(nowDate);
                        try {
                            SimpleAttributeSet set1 = new SimpleAttributeSet();//属性集合
                            //          StyleConstants.setFontSize(set1, 12);
                            StyleConstants.setForeground(set1, Color.black);
                            StyleConstants.setAlignment(set1, 0);
                            styledDocument.setParagraphAttributes(ChatServer.area.getText().length(), styledDocument.getLength() - ChatServer.area.getText().length(), set1, false);
                            styledDocument.insertString(styledDocument.getLength(),userName+"已登录 "+timeStr+"\n",null);
                        } catch (BadLocationException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "oneToOne":
                        if(msg[2].equals("服务器")){
                            StyledDocument styledDocument1=ChatServer.area.getStyledDocument();
                            Date nowDate1=new Date();
                            DateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStr1=dateFormat1.format(nowDate1);
                            try {
                                SimpleAttributeSet set1 = new SimpleAttributeSet();//属性集合
                                //          StyleConstants.setFontSize(set1, 12);
                                StyleConstants.setForeground(set1, Color.red);
                                StyleConstants.setAlignment(set1, 2);//右对齐
                                styledDocument1.setParagraphAttributes(ChatServer.area.getText().length(), styledDocument1.getLength() - ChatServer.area.getText().length(), set1, false);

                                styledDocument1.insertString(styledDocument1.getLength(),msg[1]+" "+timeStr1+"\n",null);
                                styledDocument1.insertString(styledDocument1.getLength(),msg[3]+"\n",null);
                            } catch (BadLocationException e) {
                                throw new RuntimeException(e);
                            }
                            continue;
                        }
                        ClientHandle c = clients.get(msg[2]);//获取目标用户的连接
                        String msgToOne="";
                        if (c != null) {
                            msgToOne=userName+"#"+msg[3];
                            c.output.writeUTF(msgToOne);
                        }
                        else{
                            msgToOne=msg[2]+"#"+"不在线";
                            this.output.writeUTF(msgToOne);
                        }
                        break;
                    case "oneToAll":
                        for(String str:clients.keySet()){
                            if(!str.equals(msg[1])){
                                ClientHandle cc = ClientHandle.clients.get(str);
                                try {
                                    cc.output.writeUTF(userName+"#"+msg[3]);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                        }
                        break;

                }

            }
        } catch (IOException e) {
            System.out.println("退出");
            connected = false;
        } finally {
            System.out.println("1111");

            try {
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                if (fout != null)
                    fout.close();
                if (s != null)
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}

