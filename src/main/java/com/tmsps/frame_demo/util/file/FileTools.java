package com.tmsps.frame_demo.util.file;

import com.tmsps.frame_demo.util.ChkTools;

public class FileTools {

	public static String getSuffix(String filename) {
		if (ChkTools.isNull(filename)) {
			return "";
		}
		if (!filename.contains(".")) {
			return "";
		}
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	public static void main(String[] args) {
		String s = "x.ds";
		System.err.println(FileTools.getSuffix(s));

	}
}
