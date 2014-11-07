#Access hdfs with rest
Abstract the interface to access hdfs, and implement a restful way.

##1. Requirements
the hdfs I used is hadoop2.2.0, it support to access the file system with restful way, at first web-hdfs service should be open, so config hadoop and make sure the following content is include in `${HADOOP_HOME}/etc/hadoop/hdfs-site.xml`
```xml
<property>
  <name>dfs.webhdfs.enabled</name>
  <value>true</value>
</property>
```

##2. Problems might be meet
The junit test have used "master" as hdfs namenode hostname, which can be replaced with ip address. But be careful when you use it upload file, it may be failed. The reason is that web-hdfs create a file with two steps, first step request namenode for making file directory in namenode, and return you a datanode address that the file is store in there acturaly. You use this returned address to upload your file.



