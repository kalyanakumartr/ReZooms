package org.hbs.rezoom.admin.bo;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.hbs.rezoom.bean.PasswordFormBean;
import org.hbs.rezoom.bean.UserFormBean;
import org.hbs.rezoom.bean.model.Users;
import org.hbs.rezoom.bean.path.IErrorAdmin;
import org.hbs.rezoom.bean.path.IPathAdmin;
import org.hbs.rezoom.dao.UserDao;
import org.hbs.rezoom.event.service.GenericKafkaProducer;
import org.hbs.rezoom.security.resource.IPath.ETopic;
import org.hbs.rezoom.util.CommonValidator;
import org.hbs.rezoom.util.EnumInterface;
import org.hbs.rezoom.util.ServerUtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
public class PasswordBoImpl implements PasswordBo, IErrorAdmin, IPathAdmin
{
	private static final long	serialVersionUID	= 1949352771664273090L;

	@Autowired
	GenericKafkaProducer		gKafkaProducer;

	@Autowired
	protected UserDao			userDao;

	@Autowired
	OTPBo						otpBo;

	@SuppressWarnings("unused")
	@Autowired
	private ServerUtilFactory	serverUtil;

	@Override
	public EnumInterface changePassword(@RequestBody PasswordFormBean pfBean)
	{
		Users users = userDao.findByEmailOrMobileOrUserId(pfBean.userId);
		pfBean.otp.user = users;
		try
		{
			switch ( pfBean.eFormAction )
			{
				case ChangePassword :
				case ForgotPassword :
				case Verify :
				{
					if (EReturn.Success == validatePassword(pfBean, users))
					{
						users.setUserPwd(new BCryptPasswordEncoder().encode(pfBean.newPassword));
						users.setUserPwdModFlag(true);
						users.setStatus(true);
						users.setUserPwdModDate(new Timestamp(System.currentTimeMillis()));
						users.setOtp(null);
						users.setToken(null);
						users.setTokenExpiryDate(null);
						userDao.save(users);
						pfBean.messageCode = EReturn.Success.name();
						return EReturn.Success;
					}
				}
				default :
					break;
			}
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}

		return EReturn.Failure;
	}

	private EReturn validatePassword(PasswordFormBean pfBean, Users users) throws ExecutionException
	{
		if (!Pattern.compile(PASSWORD_VALIDATION_REGEX).matcher(pfBean.newPassword).matches())
		{
			pfBean.messageCode = PASSWORD_STRENGTH_FAILURE;
			return EReturn.Failure;
		}
		else if (CommonValidator.isEqual(new BCryptPasswordEncoder().encode(pfBean.newPassword), users.getUserPwd()))
		{
			pfBean.messageCode = PASSWORD_SAME_AS_OLD_PASSWORD;
			return EReturn.Failure;
		}
		else if (CommonValidator.isNotEqual(otpBo.validateOTP(pfBean.otp), EReturn.Success))
		{
			pfBean.messageCode = PASSWORD_INVALID_OTP;
			return EReturn.Failure;
		}
		return EReturn.Success;
	}

	@Override
	public EnumInterface forgotPassword(@RequestBody UserFormBean ufBean)
	{
		Users users = userDao.findByEmailOrMobileOrUserId(ufBean.emailId);
		if (users != null)
		{
			String token = ServerUtilFactory._DomainUrl + ESecurity.Token.generate(users, EFormAction.ForgotPassword);
			ufBean.tokenURL = token;
			ufBean.user = users;
			userDao.save(users);

			gKafkaProducer.sendMessage(ETopic.Internal, EMedia.Email, ETemplate.User_Reset_Password, ufBean.user);
			ufBean.messageCode = FORGOT_PASSWORD_MAIL_SUCCESS;
			return EReturn.Success;
		}

		return EReturn.Failure;
	}

}
