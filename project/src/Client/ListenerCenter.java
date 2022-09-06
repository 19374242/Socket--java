package Client;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListenerCenter implements ActionListener {
    String s;
//    JTextArea area;
    JTextPane area,field;
  //  JTextField field;
    ClientWrite clientWrite;
    public void setArea(JTextPane area){
        this.area=area;
    }
    public void setField(JTextPane field){
        this.field=field;
    }

    public void setClientWrite(ClientWrite clientWrite) {
        this.clientWrite = clientWrite;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("发送")){
            if(!ChatClient.isPicture&&!ChatClient.isFile){
                s=field.getText();
                clientWrite.setInfo(s);
                field.setText("");
            }
            else if(!ChatClient.isFile){
                clientWrite.setPicture();
                field.setText("");
            }
            else{
                clientWrite.setFile();
                field.setText("");
            }
            ChatClient.isPicture=false;
            ChatClient.isFile=false;
        }

    }
}
