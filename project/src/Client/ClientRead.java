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

public class ClientRead extends Thread{
    DataInputStream dis;
    //JTextArea area;
    JTextPane area;
    String info;
    public ClientRead(DataInputStream dis, JTextPane area){
        this.dis=dis;
        this.area=area;
    }
    public void run(){
        while(true){
            try {
                info=dis.readUTF();
                String msg[] = info.split("#");//msg={"登录用户","要发送的信息"}

                StyledDocument styledDocument=area.getStyledDocument();
                Date nowDate=new Date();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeStr=dateFormat.format(nowDate);
                if(msg[1].equals("不在线")){
//
                    try {
                        styledDocument.insertString(styledDocument.getLength(),timeStr+msg[0]+" 不在线，消息发送失败"+"\n",null);
                    } catch (BadLocationException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                try {
                    SimpleAttributeSet set1 = new SimpleAttributeSet();//属性集合
         //           StyleConstants.setFontSize(set1, 12);//字体
                    StyleConstants.setForeground(set1,Color.black);//颜色
                    StyleConstants.setAlignment(set1, 0);//左对齐
                    styledDocument.setParagraphAttributes(area.getText().length(), styledDocument.getLength() - area.getText().length(), set1, false);
                    styledDocument.insertString(styledDocument.getLength(),msg[0]+"     "+timeStr+"\n",null);
     //               styledDocument.insertString(styledDocument.getLength(),"---------------------------------------\n",null);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }


                if(msg[1].equals("FILE")||msg[1].equals("FILEFILE")){
                    System.out.println("1");
                    byte[] buff = new byte[1024];
                    String s="C:/Users/高远/Desktop/client/client.jpg";
                    if(msg[1].equals("FILEFILE")){
                        s="C:/Users/高远/Desktop/client/client.txt";
                    }
                    OutputStream os = new FileOutputStream(s);
                    int len = -1;
                    while ((len = dis.read(buff)) != -1) {
                        os.write(buff, 0, len);
                        if(len<1024) break;
                    }
                    if(msg[1].equals("FILE")){
                        area.setCaretPosition(styledDocument.getLength());//位置
                        ImageIcon imageIcon=new ImageIcon(s);
                        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 60, Image.SCALE_DEFAULT));//改变大小
                        area.insertIcon(imageIcon);
                    }
                    else if(msg[1].equals("FILEFILE")){
                        try {
                            SimpleAttributeSet set2 = new SimpleAttributeSet();//属性集合
                            StyleConstants.setForeground(set2,Color.red);
                            StyleConstants.setAlignment(set2, 0);//左对齐
                            styledDocument.setParagraphAttributes(area.getText().length(), styledDocument.getLength() - area.getText().length(), set2, false);
                            styledDocument.insertString(styledDocument.getLength(),"收到文件在C:/Users/高远/Desktop/client/client.txt",null);
                        } catch (BadLocationException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    try {
                        styledDocument.insertString(styledDocument.getLength(),"\n",null);
                    } catch (BadLocationException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                try {

                    SimpleAttributeSet set2 = new SimpleAttributeSet();//属性集合
       //             StyleConstants.setFontSize(set2, 18);
                    StyleConstants.setForeground(set2,Color.red);
                    StyleConstants.setAlignment(set2, 0);//左对齐
                    styledDocument.setParagraphAttributes(area.getText().length(), styledDocument.getLength() - area.getText().length(), set2, false);

                    styledDocument.insertString(styledDocument.getLength(),msg[1]+"\n",null);

                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }

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
