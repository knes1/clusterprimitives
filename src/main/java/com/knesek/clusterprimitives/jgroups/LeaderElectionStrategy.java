package com.knesek.clusterprimitives.jgroups;

import org.jgroups.Address;
import org.jgroups.View;

/**
 * @author knesek
 * Created on: 16/11/14
 */
public interface LeaderElectionStrategy {

	public Address electLeader(View view);

}
