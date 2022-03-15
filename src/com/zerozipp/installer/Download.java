package com.zerozipp.installer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;

public class Download {
	public static void run(String path, String name, String url) {
		try {
			File file = new File(path);
			file.mkdir();
		} catch(NullPointerException e) {
            Logger.print("Cant create path", Logger.WARNING);
			return;
		}
		
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			try(FileOutputStream fileOutputStream = new FileOutputStream(path+"/"+name, true)) {
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				    fileOutputStream.write(dataBuffer, 0, bytesRead);
				}
	            Logger.print("Done installing", Logger.INFO);
	            JFrame frame = Main.info("Done installing", "Info", 210);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(2);
				frame.setVisible(true);
			}
		} catch(IOException e) {
            Logger.print("Failed to download", Logger.ERROR);
            JFrame frame = Main.info("Failed to download", "Error", 260);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(2);
			frame.setVisible(true);
			return;
		}
	}
}