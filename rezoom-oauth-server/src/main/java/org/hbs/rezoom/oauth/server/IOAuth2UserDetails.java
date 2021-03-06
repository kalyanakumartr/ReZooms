package org.hbs.rezoom.oauth.server;

import org.springframework.security.core.userdetails.UserDetails;

public interface IOAuth2UserDetails extends UserDetails
{
	public String getFullName();

	public String getProducerId();
}
