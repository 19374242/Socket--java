package Client;

import Server.ClientHandle;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ChatClient extends JFrame {
    JFrame frame;
    //JTextArea area;
    JTextPane area;
    JTextPane field;
    //JTextField field;
    JButton button,buttonPicture,buttonFile;
    ListenerCenter listen;
    JButton b1,b2,b3,b4;
    JComboBox<String> user1, user2;
    public static int from=1,to=2;
    public Socket s;
    public InputStream is;
    public DataInputStream dis;
    public OutputStream os;
    public DataOutputStream dos;
    public static boolean isPicture=false,isFile=false;
    public static ImageIcon imageIcon;
    public static File file;
    public static String fileName;
    public static HashMap<Integer,String> fromClient=new HashMap<Integer, String>();
    public static HashMap<Integer,String> toClient=new HashMap<Integer, String>();
    public static void main(String[] args) {
        new ChatClient().start();
    }
    public ChatClient(){
        try {
            s=new Socket("127.0.0.1",8888);
            is = s.getInputStream();
            dis=new DataInputStream(is);
            os=s.getOutputStream();
            dos=new DataOutputStream(os);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frame=new JFrame("聊天室");
        frame.setBounds(200,200,600,500);
        frame.getContentPane().setLayout(null);
//        area=new JTextArea();
        area=new JTextPane();
        area.setBounds(20,20,400,300);
        area.setBackground(new Color(240,240,245));

        //field=new JTextField();
        field=new JTextPane();
        field.setBounds(20,350,300,30);
        field.setBackground(new Color(240,240,245));
        frame.add(field);
        frame.add(area);

        //监听器
        listen=new ListenerCenter();
        listen.setArea(area);
        listen.setField(field);

        fromClient.put(1,"张三");
        fromClient.put(2,"李四");
        fromClient.put(3,"王五");
        fromClient.put(4,"群聊");
        toClient.put(1,"张三");
        toClient.put(2,"李四");
        toClient.put(3,"王五");
        toClient.put(4,"群聊");
        toClient.put(5,"服务器");



//        user1 = new JComboBox<>();
//        user1.setBounds(450,20,120,40);
//        user1.addItem("选择你的身份");
//        user1.addItem("张三");
//        user1.addItem("李四");
//        user1.addItem("王五");
//        frame.add(user1);
//        user1.addItemListener(e -> {
//            if(e.getStateChange()==ItemEvent.SELECTED){
//                if(user1.getSelectedItem().equals("张三")){
//                    System.out.println("张三1");
//                    from=1;
//                }
//                else if(user1.getSelectedItem().equals("李四")){
//                    System.out.println("李四1");
//                    from=2;
//                }
//                else if(user1.getSelectedItem().equals("王五")){
//                    System.out.println("王五1");
//                    from=3;
//                }
//                try {
//                    dos.writeUTF("1#"+fromClient.get(from));
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
        try {
            dos.writeUTF("1#"+fromClient.get(from));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        JLabel label=new JLabel("用户:  "+fromClient.get(from));
        label.setFont(new Font("微软雅黑", Font.PLAIN, 19));
        label.setForeground(Color.GRAY);
        label.setBounds(450,20,120,40);
        frame.add(label);

        user2 = new JComboBox<>();
        user2.setBounds(450,80,120,40);
        user2.addItem("好友列表");
        if(!fromClient.get(from).equals("张三")) user2.addItem("张三");
        if(!fromClient.get(from).equals("李四")) user2.addItem("李四");
        if(!fromClient.get(from).equals("王五")) user2.addItem("王五");
        user2.addItem("群聊");
        user2.addItem("服务器");
        frame.add(user2);
        user2.addItemListener(e -> {
            if(e.getStateChange()== ItemEvent.SELECTED){//没有这句话会运行两次,点击一次，下拉后点击又一次
                if(user2.getSelectedItem().equals("张三")){
                    System.out.println("张三2");
                    to=1;
                }
                else if(user2.getSelectedItem().equals("李四")){
                    System.out.println("李四2");
                    to=2;
                }
                else if(user2.getSelectedItem().equals("王五")){
                    System.out.println("王五2");
                    to=3;
                }
                else if(user2.getSelectedItem().equals("群聊")){
                    System.out.println("群聊2");
                    to=4;
                }
                else if(user2.getSelectedItem().equals("服务器")){
                    System.out.println("服务器2");
                    to=5;
                }
                area.setText("");
            }
        });
        button=new JButton("发送");
        button.setBounds(330,350,90,30);
        buttonPicture=new JButton("图片");
        buttonPicture.setBounds(20,320,70,30);
        buttonFile=new JButton("文件");
        buttonFile.setBounds(90,320,70,30);
        frame.add(button);
        frame.add(buttonPicture);
        frame.add(buttonFile);
        buttonFile.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();//桌面文件选择器
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooser.showOpenDialog(frame);//在frame中心
            if (result == JFileChooser.APPROVE_OPTION) {
                isFile=true;
                file=chooser.getSelectedFile();
                try {
                    StyledDocument styledDocument=field.getStyledDocument();
                    styledDocument.insertString(styledDocument.getLength(),file.getAbsolutePath(),null);
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonPicture.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();//桌面文件选择器
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = chooser.showOpenDialog(frame);//在frame中心
            if (result == JFileChooser.APPROVE_OPTION) {
                imageIcon=new ImageIcon(chooser.getSelectedFile().toString());
                imageIcon.setImage(imageIcon.getImage().getScaledInstance(100, 60, Image.SCALE_DEFAULT));//改变大小
                isPicture=true;
                file=chooser.getSelectedFile();
                try {
                    StyledDocument styledDocument=field.getStyledDocument();
                    styledDocument.insertString(styledDocument.getLength(),file.getAbsolutePath(),null);
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button.addActionListener(listen);

        buttonPicture.setFont(new Font("宋体",Font.BOLD,12));
        buttonPicture.setForeground(Color.gray);
        buttonPicture.setOpaque(false);//透明
        buttonPicture.setContentAreaFilled(false);
        buttonFile.setFont(new Font("宋体",Font.BOLD,12));
        buttonFile.setForeground(Color.gray);
        buttonFile.setOpaque(false);//透明
        buttonFile.setContentAreaFilled(false);

//        BackgroundPanel LP1=new BackgroundPanel();
//        frame.add(LP1);
        JLabel background=new JLabel(new ImageIcon("image/background1.jpg"));
        background.setBounds(0,0,600,500);
        frame.add(background);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void start(){

            ClientRead clientRead=new ClientRead(dis,area);
            clientRead.start();
            ClientWrite clientWrite=new ClientWrite(dos,area);
            clientWrite.start();
            listen.setClientWrite(clientWrite);

    }

}
