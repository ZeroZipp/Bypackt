package com.zerozipp.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Main {
	public static void main(String[] args) {
		JFrame frame = frame();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(2);
		frame.setVisible(true);
	}
	
	public static JFrame frame() {
		JFrame frame = new JFrame();
		frame.setSize(330, 180);
		frame.setResizable(false);
		frame.setTitle("Bypackt");
		
		JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Bypackt"));
		
		
		JTextField path = new JTextField();
		path.setText(System.getProperty("user.home")+"/.minecraft/versions/1.12.2-Bypackt");
		path.setBounds(10, frame.getHeight()-60-5, frame.getWidth()-103, 25);
		
		JPanel info = new JPanel();
        
		JLabel text = new JLabel("© 2022 ZeroZipp - All Rights Reserved");
		info.add(text);
		
		JScrollPane scroll = new JScrollPane(info);
		scroll.setBounds(10, 20, frame.getWidth()-23, 90);
		scroll.setHorizontalScrollBar(null);
		
		JButton install = new JButton();
		install.setText("Install");
		install.setBounds(frame.getWidth()-84-5, frame.getHeight()-60-5, 80, 25);
		install.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Run install");
                Download.run(path.getText(), "1.12.2-Bypackt.json", "https://github.com/ZeroZipp/Bypackt/releases/latest/download/1.12.2-Bypackt.json");
            }
        });  
		
		panel.add(scroll);
		panel.add(path);
		panel.add(install);
		frame.add(panel);
		return frame;
	}
}
