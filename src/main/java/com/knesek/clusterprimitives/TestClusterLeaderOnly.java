package com.knesek.clusterprimitives;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author knesek
 * Created on: 28/10/14
 */
@Component
public class TestClusterLeaderOnly {

	final static Logger log = LoggerFactory.getLogger(TestClusterLeaderOnly.class);

	@LeaderOnly
	public void test(String who) {
		log.info("Test method called by " +  who);
	}
}
