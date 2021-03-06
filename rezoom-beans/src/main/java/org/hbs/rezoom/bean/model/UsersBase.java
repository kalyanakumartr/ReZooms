package org.hbs.rezoom.bean.model;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hbs.rezoom.util.CommonValidator;
import org.hbs.rezoom.util.ServerUtilFactory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@MappedSuperclass
public abstract class UsersBase extends CommonDateAndStatusFields implements IUsersBase
{
	private static final long	serialVersionUID	= -3501049714698273199L;
	protected Country			country;
	protected IProducers		producer;
	protected IProducers		parentProducer;
	protected String			dateOfJoin;
	protected String			dob;
	protected String			employeeId;
	protected String			fatherName;
	protected String			folderToken;
	protected String			lastName;
	protected EGender			sex					= EGender.NoToSpecify;
	protected String			otp;
	protected String			token;
	protected Timestamp			tokenExpiryDate;
	protected String			userId;
	protected String			userImage;
	protected String			userName;
	protected String			userPwd;
	protected Timestamp			userPwdModDate;
	protected Boolean			userPwdModFlag;
	protected EUserStatus		userStatus			= EUserStatus.Pending;
	protected EUserType			userType			= EUserType.User;
	protected Set<IUserLog>		userLogs			= new LinkedHashSet<IUserLog>(0);

	protected Object			activeProfile;

	public UsersBase()
	{
		super();
	}

	@Transient
	public Object getActiveProfile()
	{
		return activeProfile;
	}

	@Override
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country", nullable = false)
	public Country getCountry()
	{
		return country;
	}

	@Column(name = "dateOfJoin")
	public String getDateOfJoin()
	{
		return dateOfJoin;
	}

	@Column(name = "dob")
	public String getDob()
	{
		return dob;
	}

	@Id
	@Column(name = "employeeId")
	public String getEmployeeId()
	{
		return employeeId;
	}

	@Column(name = "fatherName")
	public String getFatherName()
	{
		return fatherName;
	}

	@Column(name = "folderToken")
	public String getFolderToken()
	{
		return folderToken;
	}

	@Transient
	public IUserLog getLastLoginInformation()
	{
		if (CommonValidator.isSetFirstNotEmpty(userLogs))
		{
			return userLogs.iterator().next();
		}
		return null;
	}

	@Transient
	public String getLastLoginTime()
	{
		IUserLog userLog = getLastLoginInformation();
		if (CommonValidator.isNotNullNotEmpty(userLog, this.country, this.country.country))
		{
			return EDate.DD_MMM_YYYY_HH_MM_SS_AM_PM.byTimeZone(ServerUtilFactory.getInstance().getTimeZone(), this.country.country, userLog.getUserLoginTime(), EDate.YYYY_MM_DD_HH_MM_SS_24);
		}
		return "";
	}

	@Column(name = "lastName")
	public String getLastName()
	{
		return lastName;
	}

	@Column(name = "otp")
	public String getOtp()
	{
		return otp;
	}

	@ManyToOne(targetEntity = Producers.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentProducerId")
	public IProducers getParentProducer()
	{
		return parentProducer;
	}

	@ManyToOne(targetEntity = Producers.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "producerId")
	public IProducers getProducer()
	{
		return producer;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	public EGender getSex()
	{
		return sex;
	}

	@Column(name = "token")
	public String getToken()
	{
		return token;
	}

	@Column(name = "tokenExpiryDate")
	public Timestamp getTokenExpiryDate()
	{
		return tokenExpiryDate;
	}

	@Column(name = "userId")
	public String getUserId()
	{
		return userId;
	}

	@Transient
	public String getUserImage()
	{
		return userImage;
	}

	@OneToMany(targetEntity = UserLog.class, fetch = FetchType.LAZY, mappedBy = "users")
	@Fetch(FetchMode.JOIN)
	@Where(clause = "fetchBlock = true")
	@OrderBy("userLoginTime DESC")
	public Set<IUserLog> getUserLogs()
	{
		return userLogs;
	}

	@Column(name = "userName")
	public String getUserName()
	{
		return userName;
	}

	@Column(name = "userPwd")
	public String getUserPwd()
	{
		return userPwd;
	}

	@Column(name = "userPwdModDate")
	public Timestamp getUserPwdModDate()
	{
		return userPwdModDate;
	}

	@Column(name = "userPwdModFlag")
	public Boolean getUserPwdModFlag()
	{
		return userPwdModFlag;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "userStatus")
	public EUserStatus getUserStatus()
	{
		return userStatus;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "userType")
	public EUserType getUserType()
	{
		return userType;
	}

	public void setActiveProfile(Object activeProfile)
	{
		this.activeProfile = activeProfile;
	}

	@Override
	public void setCountry(Country country)
	{
		this.country = country;
	}

	public void setDateOfJoin(String dateOfJoin)
	{
		this.dateOfJoin = dateOfJoin;
	}

	public void setDob(String dob)
	{
		this.dob = dob;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	public void setFatherName(String fatherName)
	{
		this.fatherName = fatherName;
	}

	public void setFolderToken(String folderToken)
	{
		this.folderToken = folderToken;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setOtp(String otp)
	{
		this.otp = otp;
	}

	public void setParentProducer(IProducers parentProducer)
	{
		this.parentProducer = parentProducer;
	}

	@Override
	public void setProducer(IProducers producer)
	{
		this.producer = producer;
	}

	public void setSex(EGender sex)
	{
		this.sex = sex;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public void setTokenExpiryDate(Timestamp tokenExpiryDate)
	{
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public void setUserImage(String userImage)
	{
		this.userImage = userImage;
	}

	public void setUserLogs(Set<IUserLog> userLogs)
	{
		this.userLogs = userLogs;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public void setUserPwd(String userPwd)
	{
		this.userPwd = userPwd;
	}

	public void setUserPwdModDate(Timestamp userPwdModDate)
	{
		this.userPwdModDate = userPwdModDate;
	}

	public void setUserPwdModFlag(Boolean userPwdModFlag)
	{
		this.userPwdModFlag = userPwdModFlag;
	}

	public void setUserStatus(EUserStatus userStatus)
	{
		this.userStatus = userStatus;
	}

	public void setUserType(EUserType userType)
	{
		this.userType = userType;
	}
}