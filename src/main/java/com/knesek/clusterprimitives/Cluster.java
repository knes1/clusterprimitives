package com.knesek.clusterprimitives;

import java.util.List;

/**
 * Interface to cluster primitives.
 *
 * @author knesek
 * Created on: 16/11/14
 */
public interface Cluster {

	/**
	 * Returns true if running node is the current cluster's elected leader.
	 *
	 * @return true if node is cluster's leader
	 */
	boolean isLeader();

	/**
	 * Returns the list of cluster's members.
	 *
	 * @return list of cluster's members
	 */
	List<Member> getMembers();

	/**
	 * Returns cluster's currently elected leader member.
	 *
	 * @return cluster's currently elected leader member.
	 */
	Member getLeader();


	/**
	 * Get reference to underlying library's cluster/connection/client object.
	 */
	Object unwrap();

}
