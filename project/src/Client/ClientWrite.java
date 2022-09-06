package Client;


import javax.swing.*;
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

public class ClientWrite extends Thread{
    DataOutputStream dos;
    //JTextArea area;
    JTextPane area;
    String info,s;
    public boolean fileState=false,fileFileState=false;
    public ClientWrite(DataOutputStream dos,JTextPane area){
        this.dos=dos;
        this.area=area;
    }

    public synchronized void setInfo(String s){
        this.s=s;
        if(ChatClient.to==4)
            info="oneToAll#"+ChatClient.fromClient.get(ChatClient.from)+"#"+ChatClient.toClient.get(ChatClient.to)+"#"+s;
        else
            info="oneToOne#"+ChatClient.fromClient.get(ChatClient.from)+"#"+ChatClient.toClient.get(ChatClient.to)+"#"+s;
        this.notify();
    }
    public synchronized void setPicture(){
        fileState=true;
        info="FILE#"+ChatClient.fromClient.get(ChatClient.from)+"#"+ChatClient.toClient.get(ChatClient.to);
        this.notify();
    }
    public synchronized void setFile(){
        fileFileState=true;
        fileState=true;
        info="FILEFILE#"+ChatClient.fromClient.get(ChatClient.from)+"#"+ChatClient.toClient.get(ChatClient.to);
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

                Date nowDate=new Date();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeStr=dateFormat.format(nowDate);
                StyledDocument styledDocument=area.getStyledDocument();
                try{
                    SimpleAttributeSet set1 = new SimpleAttributeSet();//属性集合
          //          StyleConstants.setFontSize(set1, 12);
                    StyleConstants.setForeground(set1,Color.black);//颜色黑色
                    StyleConstants.setAlignment(set1, 2);//右对齐
                    styledDocument.setParagraphAttributes(area.getText().length(), styledDocument.getLength() - area.getText().length(), set1, false);

                    styledDocument.insertString(styledDocument.getLength(),"我"+"     "+timeStr+"\n",null);
  //                  styledDocument.insertString(styledDocument.getLength(),"---------------------------------------\n",null);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }


                if(fileState){
                    dos.writeUTF(info);
                    byte[] buff = new byte[1024];
                    FileInputStream fileInputStream = new FileInputStream(ChatClient.file);
                    int len = -1;
                    while ((len = fileInputStream.read(buff)) != -1) {
                        dos.write(buff, 0, len);
                    }
                    fileState=false;
                    if(fileFileState){
                        try {
                            SimpleAttributeSet set2 = new SimpleAttributeSet();//属性集合
                            StyleConstants.setForeground(set2, Color.BLUE);//字体蓝色
                            StyleConstants.setAlignment(set2, 2);//右对齐
                            styledDocument.setParagraphAttributes(area.getText().length(), styledDocument.getLength() - area.getText().length(), set2, false);
                            styledDocument.insertString(styledDocument.getLength(),"已向"+ChatClient.toClient.get(ChatClient.to)+"发送文件"+"\n",null);
                        } catch (BadLocationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        area.setCaretPosition(styledDocument.getLength());//位置
                        area.insertIcon(ChatClient.imageIcon);
                        try {
                            styledDocument.insertString(styledDocument.getLength(),"\n",null);
                        } catch (BadLocationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    fileFileState=false;
                    continue;
                }
                dos.writeUTF(info);


                try {

                    SimpleAttributeSet set2 = new SimpleAttributeSet();//属性集合
         //           StyleConstants.setFontSize(set2, 18);
                    StyleConstants.setForeground(set2, Color.BLUE);
                    StyleConstants.setAlignment(set2, 2);//右对齐
                    styledDocument.setParagraphAttributes(area.getText().length(), styledDocument.getLength() - area.getText().length(), set2, false);


                    styledDocument.insertString(styledDocument.getLength(),s+"\n",null);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }

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
