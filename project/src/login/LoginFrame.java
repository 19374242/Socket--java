package login;
import Client.ChatClient;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginFrame extends JFrame implements ActionListener,MouseListener {
    JTextField usernameText;
    JPasswordField passwordText;
    JButton loginButton;
    JButton registerButton;
    JLabel usernameLabel;
    JLabel passwordLabel;
    RegisterFrame registerFrame;
    JFrame loginWindow;
    private BufferedImage title1;

    public static void main(String[] args) {
        new LoginFrame();
    }
    public LoginFrame() {
        User.readDatebase();
        loginWindow=new JFrame("登入");
        //loginWindow.setSize(600, 400);
        loginWindow.setSize(600,500);
        loginWindow.setLocation(200,200);
        loginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        usernameLabel = new JLabel("用户名");
        usernameLabel.setBounds(120, 140, 90, 35);
        usernameLabel.setForeground(Color.GRAY);
        usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        loginWindow.add(usernameLabel);

        usernameText = new JTextField();
        usernameText.setBounds(230, 140, 250, 35);
        usernameText.setFont(new Font("微软雅黑",Font.PLAIN,16));
        usernameText.setOpaque(false);
        usernameText.addMouseListener(this);
        loginWindow.add(usernameText);

        passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(120, 200, 90, 35);
        passwordLabel.setForeground(Color.GRAY);
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 21));
        loginWindow.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(230, 200, 250, 35);
        passwordText.setOpaque(false);
        //passwordText.setBorder(null);
        passwordText.addMouseListener(this);
        loginWindow.add(passwordText);

        loginButton = new JButton("登入");
        loginButton.setFont(new Font("微软雅黑",Font.PLAIN,21));
        loginButton.setBounds(170, 280, 100, 35);
        loginButton.setForeground(Color.gray);
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.addActionListener(this);
        loginWindow.add(loginButton);

        registerButton = new JButton("注册");
        registerButton.setFont(new Font("微软雅黑",Font.PLAIN,21));
        registerButton.setBounds(320, 280, 100, 35);
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setForeground(Color.gray);
        loginWindow.add(registerButton);
        registerButton.addActionListener(this);


        LoginPanel LP1=new LoginPanel();
        loginWindow.add(LP1);
        loginWindow.setVisible(true);
//        Graphics g1=LP1.getGraphics();
//        title1=GameUtil.loadBufferedImage(Constant.TITLE_IMG_PATH);
//        g1.drawImage(title1,200,70,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name=usernameText.getText();
        String pas=passwordText.getText();
        if (e.getSource() == registerButton) {
            registerFrame=new RegisterFrame();
            loginWindow.setVisible(false);
            loginWindow.dispose();
        }
        if (e.getSource() == loginButton) {
            if(name.equals("")) {
                JOptionPane.showMessageDialog(loginWindow, "请输入用户名");
                return;
            }
            if(pas.equals("")) {
                JOptionPane.showMessageDialog(loginWindow,"请输入密码");
                return;
            }
            if(!User.UserNameToPassword.containsKey(name)) {
                JOptionPane.showMessageDialog(loginWindow,"用户名不存在");
                return;
            }
            if(!User.UserNameToPassword.get(name).equals(pas)) {
                JOptionPane.showMessageDialog(loginWindow,"密码错误");
                return;
            }
            User user=User.UserNameToUser.get(name);
            ChatClient.from=Integer.parseInt(user.phone);
            new ChatClient().start();
            loginWindow.setVisible(false);
            loginWindow.dispose();
            return;
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==usernameText){
            usernameText.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        }
        else if(e.getSource()==passwordText){
            passwordText.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==usernameText){
            usernameText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        else if(e.getSource()==passwordText){
            passwordText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
