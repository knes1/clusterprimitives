package com.knesek.clusterprimitives.jgroups;

import com.knesek.clusterprimitives.Cluster;
import com.knesek.clusterprimitives.Member;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author knesek
 * Created on: 21/07/14
 */
public class JGroupsCluster implements Receiver, Cluster {

	@Autowired
	private JChannel channel;

	private LeaderElectionStrategy leaderElectionStrategy;

	final static Logger log = LoggerFactory.getLogger(JGroupsCluster.class);

	public JGroupsCluster() {
		leaderElectionStrategy = new SimpleLeaderElectionStrategy();
	}



	private volatile View view = null;

	public synchronized View getView() {
		if (view == null) {
			view = channel.getView();
			setView(view);
		}
		return view;
	}

	public synchronized void setView(View view) {
		this.view = view;
	}

	public JGroupsMember getLeader() {
		return new JGroupsMember(leaderElectionStrategy.electLeader(getView()));
	}

	/**
	 * Get reference to underlying library's cluster/connection/client object.
	 */
	@Override
	public Object unwrap() {
		return channel;
	}

	public boolean isLeader() {
		Address leader = getLeader().getAddress();
		return leader.equals(channel.getAddress());
	}

	@Override
	public List<Member> getMembers() {
		List<Member> members = new ArrayList<Member>();
		for (Address address : getView().getMembers()) {
			members.add(new JGroupsMember(address));
		}
		return members;
	}

	/**
	 * From JGroups Docs:
	 *
	 * Called when a change in membership has occurred. No long running actions, sending of messages
	 * or anything that could block should be done in this callback. If some long running action
	 * needs to be performed, it should be done in a separate thread.
	 * <p/>
	 * Note that on reception of the first view (a new member just joined), the channel will not yet
	 * be in the connected state. This only happens when {@link org.jgroups.Channel#connect(String)} returns.
	 */
	@Override
	public void viewAccepted(View newView) {
		log.info("viewAccepted() New View: {}", newView);
		setView(newView);
	}

	/**
	 * From JGroups Docs:
	 *
	 * Called whenever a member is suspected of having crashed, but has not yet been excluded.
	 */
	@Override
	public void suspect(Address suspectedMbr) {
	}

	/**
	 * From JGroups Docs:
	 *
	 * Called (usually by the FLUSH protocol), as an indication that the member should stop sending
	 * messages. Any messages sent after returning from this callback might get blocked by the FLUSH
	 * protocol. When the FLUSH protocol is done, and messages can be sent again, the FLUSH protocol
	 * will simply unblock all pending messages. If a callback for unblocking is desired, implement
	 * {@link org.jgroups.MembershipListener#unblock()}. Note that block() is the equivalent
	 * of reception of a BlockEvent in the pull mode.
	 */
	@Override
	public void block() {
	}

	/**
	 * From JGroups Docs:
	 *
	 * Called <em>after</em> the FLUSH protocol has unblocked previously blocked senders, and
	 * messages can be sent again. This callback only needs to be implemented if we require a
	 * notification of that.
	 *
	 * <p>
	 * Note that during new view installation we provide guarantee that unblock invocation strictly
	 * follows view installation at some node A belonging to that view . However, some other message
	 * M may squeeze in between view and unblock callbacks.
	 *
	 * For more details see https://jira.jboss.org/jira/browse/JGRP-986
	 *
	 */
	@Override
	public void unblock() {
	}

	/**
	 * Called when a message is received.
	 *
	 * @param msg
	 */
	@Override
	public void receive(Message msg) {
	}

	/**
	 * From JGroups Docs:
	 *
	 * Allows an application to write a state through a provided OutputStream. After the state has
	 * been written the OutputStream doesn't need to be closed as stream closing is automatically
	 * done when a calling thread returns from this callback.
	 *
	 * @param output
	 *           the OutputStream
	 * @throws Exception
	 *            if the streaming fails, any exceptions should be thrown so that the state requester
	 *            can re-throw them and let the caller know what happened
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void getState(OutputStream output) throws Exception {
	}

	/**
	 * From JGroups Docs:
	 *
	 * Allows an application to read a state through a provided InputStream. After the state has been
	 * read the InputStream doesn't need to be closed as stream closing is automatically done when a
	 * calling thread returns from this callback.
	 *
	 * @param input
	 *           the InputStream
	 * @throws Exception
	 *            if the streaming fails, any exceptions should be thrown so that the state requester
	 *            can catch them and thus know what happened
	 * @see java.io.InputStream#close()
	 */
	@Override
	public void setState(InputStream input) throws Exception {
	}
}
