package com.knesek.clusterprimitives.jgroups;

import com.knesek.clusterprimitives.LeaderOnlyAdvisor;
import com.knesek.clusterprimitives.LeaderOnlyMethodInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * Factory class that creates JGroupsCluster and JChannel singletons, and registers
 * AOP advisors to make cluster primitives annotations work.
 *
 * @author knesek
 * Created on: 16/11/14
 */
public class JGroupsClusterFactory implements BeanDefinitionRegistryPostProcessor {

	private String clusterName;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	/**
	 * Modify the application context's internal bean definition registry after its
	 * standard initialization. All regular bean definitions will have been loaded,
	 * but no beans will have been instantiated yet. This allows for adding further
	 * bean definitions before the next post-processing phase kicks in.
	 * @param registry the bean definition registry used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		registry.registerBeanDefinition("cluster",
			BeanDefinitionBuilder.rootBeanDefinition(JGroupsCluster.class)
				.getBeanDefinition()
		);
		registry.registerBeanDefinition("channel",
				BeanDefinitionBuilder.rootBeanDefinition(ChannelFactory.class)
						.addPropertyValue("clusterName", clusterName)
						.addPropertyReference("receiver", "cluster")
						.getBeanDefinition()
		);

		registry.registerBeanDefinition("leaderOnlyMethodInterceptor",
			BeanDefinitionBuilder.rootBeanDefinition(LeaderOnlyMethodInterceptor.class)
				.getBeanDefinition()
		);

		registry.registerBeanDefinition("leaderOnlyAdvisor",
			BeanDefinitionBuilder.rootBeanDefinition(LeaderOnlyAdvisor.class)
				.getBeanDefinition()
		);
	}

	/**
	 * Modify the application context's internal bean factory after its standard
	 * initialization. All bean definitions will have been loaded, but no beans
	 * will have been instantiated yet. This allows for overriding or adding
	 * properties even to eager-initializing beans.
	 * @param beanFactory the bean factory used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}
}
