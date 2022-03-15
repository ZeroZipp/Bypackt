package com.zerozipp.installer;

public class Logger {
	public static int ERROR = 0;
	public static int INFO = 1;
	public static int WARNING = 2;
	
	public static void print(String log, int type) {
		if(type == ERROR) {
			System.out.println("[ERROR] "+log);
		}else if(type == INFO) {
			System.out.println("[INFO] "+log);
		}else if(type == WARNING) {
			System.out.println("[WARNING] "+log);
		}
	}
}