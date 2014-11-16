package com.knesek.clusterprimitives;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author knesek
 * Created on: 28/10/14
 */
@Component
public class LeaderOnlyAdvisor extends AbstractPointcutAdvisor {

	private final StaticMethodMatcherPointcut pointcut = new
			StaticMethodMatcherPointcut() {
				@Override
				public boolean matches(Method method, Class<?> targetClass) {
					return method.isAnnotationPresent(LeaderOnly.class);
				}
			};

	@Autowired
	private LeaderOnlyMethodInterceptor interceptor;

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	@Override
	public Advice getAdvice() {
		return interceptor;
	}
}
