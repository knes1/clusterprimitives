package com.knesek.clusterprimitives;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author knesek
 * Created on: 28/10/14
 */
@Component
public class LeaderOnlyMethodInterceptor implements MethodInterceptor {

	final static Logger log = LoggerFactory.getLogger(LeaderOnlyMethodInterceptor.class);

	@Autowired
	Cluster cluster;

	/**
	 * Implement this method to perform extra treatments before and
	 * after the invocation. Polite implementations would certainly
	 * like to invoke {@link org.aopalliance.intercept.Joinpoint#proceed()}.
	 *
	 * @param invocation the method invocation joinpoint
	 * @return the result of the call to {@link
	 * org.aopalliance.intercept.Joinpoint#proceed()}, might be intercepted by the
	 * interceptor.
	 *
	 * @throws Throwable if the interceptors or the
	 * target-object throws an exception.  */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (cluster.isLeader()) {
			return invocation.proceed();
		}
		return null;
	}
}
