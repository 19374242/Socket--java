package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
public class User {
    public static ArrayList<User> UserList=new ArrayList<>();
    public static HashMap<String,String> UserNameToPassword=new HashMap<>();
    public static HashMap<String,User> UserNameToUser=new HashMap<>();
    public String name,password,phone,time;
    public int which=0;

    /**
     * 读取数据库中信息，为每个用户创建一个用户类
     */
    public static void readDatebase() {
        String filePath="datebase/record.csv";
        File record = new File(filePath);
        record.setReadable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(record), "UTF-8");
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line="";
        int x=0;
        try {
            while ((line = br.readLine()) != null) {
                if(x==0){
                    x=1;
                    continue;
                }
                String a[]=line.split(",");
                User newUser=new User(a[0],a[1],a[2],a[3],true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建用户类
     * @param name 用户名
     * @param password 密码
     * @param phone 电话号码
     * @param time 最长坚持时间
     * @param isReadDatebase 是否是最开始读数据库时调用
     */
    public User(String name,String password,String phone,String time,boolean isReadDatebase) {
        this.name=name;
        this.password=password;
        this.phone=phone;
        this.time=time;
        User.UserList.add(this);
        User.UserNameToPassword.put(name, password);
        User.UserNameToUser.put(name,this);
        if(isReadDatebase==false) updateDatebase();
    }

    /**
     * 更新数据库信息
     */
    public void updateDatebase() {
        String filePath="datebase/record.csv";
        File record = new File(filePath);
        record.setReadable(true);
        record.setWritable(true);
        BufferedWriter out;
        try {
            //第二个参数表示从末尾开始读
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(record, true)));
            out.write(name+","+password+","+phone+","+time);
            out.newLine();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将信息写回数据库
     */
    public static void putBack(){
        String filePath="datebase/record.csv";
        File record = new File(filePath);
        record.setReadable(true);
        record.setWritable(true);
        BufferedWriter out= null;
        for(int i=0;i<UserList.size();i++){
            try {
                if(i==0){
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(record, false)));
                    out.write("账号"+","+"密码"+","+"手机号"+","+"时间");
                    out.newLine();
                }
                else out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(record, true)));
                out.write(UserList.get(i).name+","+UserList.get(i).password+","+UserList.get(i).phone+","+UserList.get(i).time);
                out.newLine();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
