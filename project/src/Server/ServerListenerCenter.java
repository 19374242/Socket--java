package Server;

import Client.ClientWrite;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerListenerCenter implements ActionListener{
    String s;
   // JTextArea area;
  //  JTextField field;
    JTextPane area,field;
    ServerWrite serverWrite;
    public void setArea(JTextPane area){
        this.area=area;
    }
    public void setField(JTextPane field){
        this.field=field;
    }

    public void setServerWrite(ServerWrite serverWrite) {
        this.serverWrite = serverWrite;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        s = field.getText();
        field.setText("");
        StyledDocument styledDocument=area.getStyledDocument();
        Date nowDate=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr=dateFormat.format(nowDate);
        SimpleAttributeSet set1 = new SimpleAttributeSet();//属性集合
        //          StyleConstants.setFontSize(set1, 12);
        StyleConstants.setForeground(set1, Color.black);
        StyleConstants.setAlignment(set1, 0);
        styledDocument.setParagraphAttributes(ChatServer.area.getText().length(), styledDocument.getLength() - ChatServer.area.getText().length(), set1, false);


        if(!ChatServer.serveToPerson.equals("all")){
            ClientHandle c = ClientHandle.clients.get(ChatServer.serveToPerson);
            try {
                c.output.writeUTF("服务器端#"+s);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                styledDocument.insertString(styledDocument.getLength(),"向"+ChatServer.serveToPerson+"发送消息:"+timeStr+"\n",null);
                styledDocument.insertString(styledDocument.getLength(),s+"\n",null);
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
            //area.append("向"+ChatServer.serveToPerson+"发送消息:"+s+"\n");
        }
        else{
            for(String str:ClientHandle.clients.keySet()){
                ClientHandle c = ClientHandle.clients.get(str);
                try {
                    c.output.writeUTF("服务器端#"+s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                styledDocument.insertString(styledDocument.getLength(),"向所有人发送消息:"+timeStr+"\n",null);
                styledDocument.insertString(styledDocument.getLength(),s+"\n",null);
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
            //area.append("向所有人发送消息:"+s+"\n");
        }
    }

}