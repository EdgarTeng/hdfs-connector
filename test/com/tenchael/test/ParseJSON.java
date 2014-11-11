package com.tenchael.test;

import static com.tenchael.test.Constants.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tenchael.hdfs.HdfsConnector;
import com.tenchael.hdfs.impl.WebHdfsConnector;
import com.tenchael.hdfs.util.Common;

public class ParseJSON {

	public static void main(String[] args) {

		String path = "/";
		List<CFile> list = Common.getParents(path);
		for(CFile f:list){
			System.out.println(f.getId()+"\t"+f.getName());
		}

		HdfsConnector connector = new WebHdfsConnector("master", 50070, "root");
		String remoteDirectory = "/user/upload";
		String msg = connector.list(remoteDirectory);
		System.out.println(msg);
		JSONObject json = JSONObject.fromObject(msg);
		JSONObject body = json.getJSONObject(ATTR_BODY);
		JSONObject fileStatuses = body.getJSONObject(ATTR_FILESTATUSES);
		JSONArray files = fileStatuses.getJSONArray(ATTR_FILESTATUS);
		Set<CFile> set = new HashSet<CFile>();
		for (int i = 0; i < files.size(); i++) {
			JSONObject fInfo = files.getJSONObject(i);
			CFile cf = new CFile();
			cf.setName(fInfo.getString(FILENAME));
			cf.setSize(fInfo.getLong(FILESIZE));
			cf.setCreateTime(longToDate(fInfo.getLong(CREATETIME)));
			cf.setType(fInfo.getString(FILETYPE));
			set.add(cf);
		}
		for (CFile f : set) {
			System.out.println(f.getName() + "\t" + f.getCreateTime() + "\t"
					+ f.getSize() + "\t" + f.getType());
		}

	}

	private static String longToDate(long d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(d);
		return sdf.format(dt); // 得到精确到秒的表示：08/31/2006 21:08:00
	}

}
