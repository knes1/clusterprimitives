package com.knesek.clusterprimitives;

import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author knesek
 * Created on: 21/07/14
 */
public class Main {

	final static Logger log = LoggerFactory.getLogger(Main.class);
	public static volatile boolean shouldStop = false;

	@Scheduled
	public static void main(String[] args) throws Exception{
		final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:app-config.xml");
		ctx.registerShutdownHook();
		final String who = "" +  (int)(Integer.MAX_VALUE * Math.random());
		final Cluster cluster = (Cluster) ctx.getBean("cluster");
		final TestClusterLeaderOnly testBean = ctx.getBean(TestClusterLeaderOnly.class);
		Thread masterOutput = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!shouldStop) {
					log.info("Is leader? {}", cluster.isLeader());
					testBean.test(who);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						log.debug("Exception occured " + e.getMessage(), e);
					}
				}
			}
		});
		masterOutput.start();
		System.in.read();
		shouldStop = true;
		JChannel channel = (JChannel) ctx.getBean("channel");
		channel.close();
	}

}
