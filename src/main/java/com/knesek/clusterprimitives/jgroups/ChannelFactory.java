package com.knesek.clusterprimitives.jgroups;

import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author knesek
 * Created on: 21/07/14
 */
public class ChannelFactory implements FactoryBean<JChannel>, DisposableBean, InitializingBean {

	private JChannel channel;
	private String clusterName = "Default Cluster";
	private Receiver receiver;

	public ChannelFactory() throws Exception {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		channel = new JChannel();
		channel.connect(clusterName);
		if (receiver != null) {
			channel.setReceiver(receiver);
		}
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public JChannel getObject() throws Exception {
		return channel;
	}

	@Override
	public Class<?> getObjectType() {
		return JChannel.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		channel.close();
	}
}
