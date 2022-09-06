package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
public class RegisterFrame extends JFrame implements ActionListener,MouseListener {
    JTextField usernameText,phoneNum;
    JPasswordField passwordText1,passwordText2;
    JButton loginButton;
    JButton registerButton,backButton;
    JLabel usernameLabel;
    JLabel passwordLabel1,passwordLabel2;
    JFrame registerWindow;
    JLabel phone;
    LoginFrame loginFrame;
    JFrame loginWindow;
    private BufferedImage title2;

    public RegisterFrame() {
        registerWindow=new JFrame("注册");
        registerWindow.setSize(600,500);
        registerWindow.setLocation(200,200);
        //registerWindow.setSize(600, 500);
        //registerWindow.setLocationRelativeTo(null);//将窗口置于中央（写了这个后面setbound就是相对于该边框）
        registerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        usernameLabel = new JLabel("用户名");
        usernameLabel.setBounds(120, 110, 90, 35);
        usernameLabel.setForeground(Color.GRAY);//前景色
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        registerWindow.add(usernameLabel);

        usernameText = new JTextField();
        usernameText.setBounds(230, 110, 250, 35);
        usernameText.setFont(new Font("微软雅黑",Font.PLAIN,16));
        usernameText.setOpaque(false);//内部透明
        //usernameText.setBorder(null);//边界透明
        usernameText.addMouseListener(this);
        usernameText.addActionListener(this);
        registerWindow.add(usernameText);

        passwordLabel1 = new JLabel("密码");
        passwordLabel1.setBounds(120, 160, 90, 35);
        passwordLabel1.setForeground(Color.GRAY);
        passwordLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        registerWindow.add(passwordLabel1);

        passwordText1 = new JPasswordField();
        passwordText1.setBounds(230, 160, 250, 35);
        passwordText1.setOpaque(false);
        //passwordText.setBorder(null);
        passwordText1.addMouseListener(this);
        passwordText1.addActionListener(this);
        registerWindow.add(passwordText1);

        passwordLabel2 = new JLabel("确认密码");
        passwordLabel2.setBounds(120, 210, 90, 35);
        passwordLabel2.setForeground(Color.GRAY);
        passwordLabel2.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        registerWindow.add(passwordLabel2);

        passwordText2 = new JPasswordField();
        passwordText2.setBounds(230, 210, 250, 35);
        passwordText2.setOpaque(false);
        //passwordText.setBorder(null);
        passwordText2.addMouseListener(this);
        passwordText2.addActionListener(this);
        registerWindow.add(passwordText2);

        phone = new JLabel("手机号");
        phone.setBounds(120, 260, 90, 35);
        phone.setForeground(Color.GRAY);//前景色
        phone.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        registerWindow.add(phone);

        phoneNum = new JTextField();
        phoneNum.setBounds(230, 260, 250, 35);
        phoneNum.setFont(new Font("微软雅黑",Font.PLAIN,16));
        phoneNum.setOpaque(false);//内部透明
        phoneNum.addMouseListener(this);
        phoneNum.addActionListener(this);
        registerWindow.add(phoneNum);

        registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑",Font.PLAIN,21));
        registerButton.setBounds(170, 340, 100, 35);
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setForeground(Color.gray);
        registerWindow.add(registerButton);
        registerButton.addActionListener(this);

        backButton = new JButton("返回");
        backButton.setFont(new Font("微软雅黑",Font.PLAIN,21));
        backButton.setBounds(320, 340, 100, 35);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setForeground(Color.gray);
        registerWindow.add(backButton);
        backButton.addActionListener(this);


        LoginPanel LP2=new LoginPanel();
        registerWindow.add(LP2);
        registerWindow.setVisible(true);
//        Graphics g=LP2.getGraphics();
//        title2=GameUtil.loadBufferedImage(Constant.TITLE_IMG_PATH);
//        g.drawImage(title2,200,70,null);
    }

    /**
     * 按下按钮对应事件
     * @param e 按下按钮
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String name=usernameText.getText();
        String pas1=passwordText1.getText();
        String pas2=passwordText2.getText();
        String phone=phoneNum.getText();
        if(e.getSource()==backButton){
            registerWindow.setVisible(false);
            registerWindow.dispose();
            new LoginFrame();
        }
        else if (e.getSource() == registerButton) {
            if(name.equals("")) {
                JOptionPane.showMessageDialog(registerWindow, "请输入用户名");
                return;
            }
            if(pas1.equals("")) {
                JOptionPane.showMessageDialog(registerWindow,"请输入密码");
                return;
            }
            if(pas2.equals("")) {
                JOptionPane.showMessageDialog(registerWindow,"请确认密码");
                return;
            }
            if(phone.equals("")) {
                JOptionPane.showMessageDialog(registerWindow,"手机号不能为空");
                return;
            }
            if(!pas1.equals(pas2)) {
                JOptionPane.showMessageDialog(registerWindow,"两次密码不相同");
                return;
            }
            if(User.UserNameToPassword.containsKey(name)) {
                JOptionPane.showMessageDialog(registerWindow,"用户名已存在");
                return;
            }
            User user=new User(name,pas1,phone,"0",false);
            JOptionPane.showMessageDialog(registerWindow,"注册成功");
            loginFrame=new LoginFrame();
            registerWindow.setVisible(false);
            registerWindow.dispose();
        }
        return;
    }

    /**
     * 鼠标移入对应事件
     * @param e 鼠标移入
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==usernameText){
            usernameText.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        }
        else if(e.getSource()==passwordText1){
            passwordText1.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        }
        else if(e.getSource()==passwordText2){
            passwordText2.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        }
        else if(e.getSource()==phoneNum){
            phoneNum.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        }
    }

    /**
     * 鼠标移出对应事件
     * @param e 鼠标移出
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==usernameText){
            usernameText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        else if(e.getSource()==passwordText1){
            passwordText1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        else if(e.getSource()==passwordText2){
            passwordText2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        else if(e.getSource()==phoneNum){
            phoneNum.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
