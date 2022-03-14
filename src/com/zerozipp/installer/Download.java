package com.zerozipp.installer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Download {
	public static void run(String path, String name, String url) {
		try {
			File file = new File(path);
			file.mkdir();
		} catch(NullPointerException e) {
			System.out.println("Cant create path:");
			System.out.println(e);
			return;
		}
		
		try {
			BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
			try (FileOutputStream fileOutputStream = new FileOutputStream(path+"/"+name, true)) {
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				    fileOutputStream.write(dataBuffer, 0, bytesRead);
				}
			    System.out.println("Done installing");
			}
		} catch(IOException e) {
			System.out.println("Failed to download:");
			System.out.println(e);
			return;
		}
	}
}