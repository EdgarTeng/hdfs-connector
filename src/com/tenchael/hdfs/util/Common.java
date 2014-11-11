package com.tenchael.hdfs.util;

import java.util.ArrayList;
import java.util.List;

import com.tenchael.test.CFile;

public class Common {
	public static List<CFile> getParents(final String path) {
		StringBuffer buf = new StringBuffer(path);
		if (!path.trim().endsWith("/")) {// 说明path是一个文件路径
			buf.append("/");
		}
		List<CFile> list = new ArrayList<CFile>();
		list.add(new CFile("/", "/"));// 添加根节点

		String[] parts = buf.toString().split("/");
		StringBuffer temp = new StringBuffer("/");
		for (int i = 1; i < parts.length - 1; i++) {
			temp = temp.append(parts[i] + "/");
			list.add(new CFile(temp.toString(), parts[i]));
		}

		return list;
	}

}
