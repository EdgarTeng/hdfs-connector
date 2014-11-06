package com.tenchael.hdfs.util;

public class NameRules {
	public static final String OP_GETFILESTATUS = "GETFILESTATUS";//get the status of a file or a directory
	public static final String OP_OPEN = "OPEN";//open or read a file
	public static final String OP_MKDIRS = "MKDIRS";//create a directory
	public static final String OP_CREATE = "CREATE";//create a file
	public static final String OP_RENAME = "RENAME";//rename a file or directory
	public static final String OP_DELETE = "DELETE";//delete a file directory
	public static final String OP_LISTSTATUS = "LISTSTATUS";//list the info under a directory
	public static final String OP_GETCONTENTSUMMARY = "GETCONTENTSUMMARY";//get the summary info of a directory
	public static final String KEY_USERNAME = "user.name";
	public static final String KEY_OP = "op";
	public static final String KEY_DESTINATION = "destination";
	public static final String KEY_RECURSIVE = "recursive";
	public static final String ATTR_HEADER = "header";
	public static final String ATTR_BODY = "body";
	public static final String ATTR_CONTENT = "content";
	public static final String ATTR_FILESTATUS = "FileStatus";
	public static final String ATTR_TYPE = "type";
	public static final String VALUE_FILE = "FILE";
	public static final String VALUE_DIRECTORY="DIRECTORY";

}
