package org.hbs.rezoom.bean;

import org.hbs.rezoom.security.resource.IPath.EFormAction;

public class PasswordFormBean extends APIStatus
{
	private static final long	serialVersionUID	= 8292008040523676703L;

	public String				userId;

	public String				oldPassword;

	public String				newPassword;

	public EFormAction			eFormAction			= EFormAction.ChangePassword;

	public String				tokenUrl;

	public OTPFormBean			otp;

}
