package org.hbs.rezoom.bean;

import org.hbs.rezoom.bean.model.Users;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.springframework.security.core.Authentication;

public class UserFormBean extends APIStatus
{
	private static final long	serialVersionUID	= 1452795795821759203L;

	public Users				repoUser;
	public String				emailId;
	public String				tokenURL;
	public Users				user;
	public String				searchParam;
	public String				country;
	public String				state;
	public String				media				= EMedia.Email.name();
	public String				authToken;
	public String				roleName;

	public UserFormBean()
	{
		super();
	}

	public UserFormBean(String userId)
	{
		super();
		this.user = new Users(userId);

	}

	public void updateRepoUser(Authentication auth)
	{
	}

}
