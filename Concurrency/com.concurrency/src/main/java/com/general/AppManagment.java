package com.general;

public class AppManagment {
	public static String getInputFile() {
		return System.getenv("input.file");
	}

	public static String getOutputFile() {
		return System.getenv("output.file");
	}
	
	public static int getThreadCount(){
		return Integer.valueOf(System.getenv("thread.count"));
	}
}
