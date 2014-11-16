package com.knesek.clusterprimitives.jgroups;

import org.jgroups.Address;
import org.jgroups.View;

/**
 * @author knesek
 * Created on: 16/11/14
 */
public class SimpleLeaderElectionStrategy implements LeaderElectionStrategy {

	@Override
	public Address electLeader(View view) {
		Address[] addresses = view.getMembersRaw();
		if (addresses != null && addresses.length > 0) {
			return addresses[0];
		}
		throw new IllegalStateException("Cluster view contains no members (addresses).");
	}
}
