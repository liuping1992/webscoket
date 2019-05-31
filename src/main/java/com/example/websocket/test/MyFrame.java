package com.example.websocket.test;

import javax.swing.*;
import java.awt.*;

public class MyFrame {

    public static void main(String[] args) {
        JFrame jf = new JFrame("测试swing");
        jf.setVisible(true);
        jf.setSize(500, 300);
        jf.setBounds(400, 200,1000, 600);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton jb = new JButton("点击弹出框");
        jb.setBounds(30, 30, 100, 25);
        jb.addActionListener(e -> {
            JDialog jd = new JDialog(jf,"这是一个子窗体", true);
            jd.setBounds(540,250,200,100);
            jd.setVisible(true);
        });

        Container con = jf.getContentPane();
        con.setLayout(null);
        con.add(jb);
    }

}
