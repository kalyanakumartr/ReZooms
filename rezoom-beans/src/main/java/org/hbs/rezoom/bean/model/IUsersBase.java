package org.hbs.rezoom.bean.model;

import java.sql.Timestamp;
import java.util.Set;

import org.hbs.rezoom.util.EBusinessKey;
import org.hbs.rezoom.util.EnumInterface;
import org.hbs.rezoom.util.ICRUDBean;

public interface IUsersBase extends EBusinessKey, ICRUDBean, ICommonDateAndStatusFields, ICountryBase, IProducersBase
{
	public enum EResource implements EnumInterface
	{
		Default, Profile;
	}

	public enum EUserStatus implements EnumInterface
	{
		Activated, Blocked, DeActivated, Default, FirstTime, Fradulent, KYC_Incomplete, Pending, RecentPassword, ResetPassword, RecentToken, Suspended, Validate;
	}

	public enum EUserType implements EnumInterface
	{
		User, Employee, Consumer, Producer;
	}

	public enum EGender implements EnumInterface
	{
		NoToSpecify, Male, Female, BiGender;
	}

	public Object getActiveProfile();

	public IUsersAddress getCommunicationAddress();

	public String getDateOfJoin();

	public String getDob();

	public String getEmployeeId();

	public EUserStatus getUserStatus();

	public String getFatherName();

	public String getFolderToken();

	public String getLastLoginTime();

	public String getLastName();

	public IProducers getParentProducer();

	public IProducers getProducer();

	public EGender getSex();

	public String getToken();

	public Timestamp getTokenExpiryDate();

	public String getUserId();

	public String getUserImage();

	public Set<IUserLog> getUserLogs();

	public String getUserName();

	public String getUserPwd();

	public Timestamp getUserPwdModDate();

	public Boolean getUserPwdModFlag();

	public EUserType getUserType();

	public void setActiveProfile(Object activeProfile);

	public void setDateOfJoin(String usDateOfJoin);

	public void setDob(String usDob);

	public void setEmployeeId(String usEmployeeId);

	public void setFatherName(String usFatherName);

	public void setFolderToken(String usFolderToken);

	public void setLastName(String usLastName);

	public void setParentProducer(IProducers subProducer);

	public void setProducer(IProducers producer);

	public void setSex(EGender usSex);

	public void setToken(String usToken);

	public void setTokenExpiryDate(Timestamp usTokenExpiryDate);

	public void setUserId(String usUserId);

	public void setUserImage(String usUserImage);

	public void setUserLogs(Set<IUserLog> userLogs);

	public void setUserName(String usUserName);

	public void setUserPwd(String usUserPwd);

	public void setUserPwdModDate(Timestamp usUserPwdModDate);

	public void setUserPwdModFlag(Boolean usUserPwdModFlag);

	public void setUserStatus(EUserStatus usUserStatus);

	public void setUserType(EUserType usUsersType);

}