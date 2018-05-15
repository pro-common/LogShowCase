package org.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.status.StatusLogger;
import org.junit.Test;

public class Log4JLevel {

//	public static void main(String[] args) {
//		Logger logger = LogManager.getLogger("");
//		ThreadContext.put("loginId", "User2");
//		ThreadContext.put("CODE", "User2");
//
//		logger.debug(">>>>debug info>>>>>>>>>");
//		logger.info(">>>> common  info>>>>>>>>>>");
//		logger.error(">>>>error  info>>>>>>>>>>");
//
//	}

	// user1 DEBUG所有日志,生产场景下可以将USER1的日志输出到单独的LOG文件，来进行问题定位
	@Test
	public void mdcLogUser1() {
		Logger statuslogger = StatusLogger.getLogger();
		statuslogger.debug("mdcLogUser1 start >>>>>>>>>>>>>>>>>>>>>");
		ThreadContext.put("loginId", "User1");
		ThreadContext.put("CODE", "11111");
		logMDCInfo();
	}
	
	// user2只能输出ERROR日志
	@Test
	public void mdcLogUser2() {
		 
		Logger statuslogger = StatusLogger.getLogger();
		statuslogger.debug("mdcLogUser2 start >>>>>>>>>>>>>>>>>>>>>");
		ThreadContext.put("loginId", "User2");
		ThreadContext.put("CODE", "User2");
		logMDCInfo();
	}

	
	@Test////启动节点一
	public void startServerNode1() throws InterruptedException{
			System.out.println("start SERVER1 " );
			startServerAndCreateLogFile("SERVER1");
			Thread.sleep(60*1000);
			//启动节点一后再启动节点2  startServerNode2
	}
	
	
	@Test////启动节点2
	public void startServerNode2() throws InterruptedException{
			System.out.println("start SERVER2 " );
			startServerAndCreateLogFile("SERVER2");
			Thread.sleep(60*1000);
	}
	
	
	@Test////重新加载配置文件
	public void reloadLogConfig() throws InterruptedException{
		ThreadContext.put("loginId", "User2");
		while(true){
			logMDCInfo();
			// 将 User2 替换成User1 ，看日志变化
			System.out.println("线程休眠，赶快去修改LOG4J2.XML文件，看是否会重新加载配置");
			Thread.sleep(10*1000);
		}
		 
	}
	
	private void startServerAndCreateLogFile(String serverName) {
		// -DlogPath=target -DserverName=NODE1
		Logger statuslogger = StatusLogger.getLogger();
		statuslogger.debug("configLogFilePath start >>>>>>>>>>>>>>>>>>>>>");
		System.setProperty("logPath", "target");
		System.setProperty("serverName", serverName);
		Logger logger = LogManager.getLogger("");
		logger.debug("server start>>>>>>>>>>>>>> " + serverName);
	}

	private void logMDCInfo() {
		startServerAndCreateLogFile("");
		Logger logger = LogManager.getLogger("");
		logger.debug(">>>>debug info>>>>>>>>>");
		logger.info(">>>> common  info>>>>>>>>>>");
		logger.error(">>>>error  info>>>>>>>>>>");
	}

	
}
