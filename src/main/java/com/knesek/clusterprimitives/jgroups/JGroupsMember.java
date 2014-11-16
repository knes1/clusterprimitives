package com.knesek.clusterprimitives.jgroups;

import com.knesek.clusterprimitives.Member;
import org.jgroups.Address;

/**
 * @author knesek
 * Created on: 16/11/14
 */
public class JGroupsMember implements Member {

	private final Address address;

	public JGroupsMember(Address address) {
		this.address = address;
		if (address == null) {
			throw new IllegalArgumentException("Address may not be null");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JGroupsMember that = (JGroupsMember) o;

		if (!address.equals(that.address)) return false;

		return true;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		return address.hashCode();
	}

	@Override
	public String getName() {
		return address.toString();
	}
}
