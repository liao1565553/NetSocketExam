﻿1、打开工程，打开com.hand.MyServerSocket.java  文件启动该文件中的main函数

	maven命令：mvn exec:java -Dexec.mainClass="com.hand.MyServerSocket" -Dexec.args="arg0 arg1 arg2"

2、另外打开一个cmd程序，运行cmd 输入   “telnet 127.0.0.1 12345”  
3、在工程的主目录下即可看到刚刚下载好的target.pdf 文件

