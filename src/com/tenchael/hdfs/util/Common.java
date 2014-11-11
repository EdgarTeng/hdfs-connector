package com.tenchael.hdfs.util;

import java.util.ArrayList;
import java.util.List;

import com.tenchael.test.CFile;

public class Common {
	public static List<CFile> getParents(final String path) {
		StringBuffer buf = new StringBuffer(path);
		if (!path.trim().endsWith("/")) {// ˵��path��һ���ļ�·��
			buf.append("/");
		}
		List<CFile> list = new ArrayList<CFile>();
		list.add(new CFile("/", "/"));// ��Ӹ��ڵ�

		String[] parts = buf.toString().split("/");
		StringBuffer temp = new StringBuffer("/");
		for (int i = 1; i < parts.length - 1; i++) {
			temp = temp.append(parts[i] + "/");
			list.add(new CFile(temp.toString(), parts[i]));
		}

		return list;
	}

}
