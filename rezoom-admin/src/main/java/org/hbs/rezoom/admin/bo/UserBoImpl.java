package org.hbs.rezoom.admin.bo;

import java.security.InvalidKeyException;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.kafka.common.errors.InvalidRequestException;
import org.hbs.rezoom.bean.UserFormBean;
import org.hbs.rezoom.bean.model.IUsersMedia;
import org.hbs.rezoom.bean.model.Users;
import org.hbs.rezoom.bean.path.IErrorAdmin;
import org.hbs.rezoom.bean.path.IPathAdmin;
import org.hbs.rezoom.dao.SequenceDao;
import org.hbs.rezoom.event.service.GenericKafkaProducer;
import org.hbs.rezoom.security.resource.IPath.ETopic;
import org.hbs.rezoom.util.CommonValidator;
import org.hbs.rezoom.util.EnumInterface;
import org.hbs.rezoom.util.ServerUtilFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserBoImpl extends UserBoComboBoxImpl implements UserBo, IErrorAdmin, IPathAdmin
{

	private static final long	serialVersionUID	= 7255672818512788055L;

	private final Logger		logger				= LoggerFactory.getLogger(UserBoImpl.class);

	@Autowired
	GenericKafkaProducer		gKafkaProducer;

	@Autowired
	private SequenceDao			sequenceDao;

	@Autowired
	private ServerUtilFactory	serverUtil;

	@Override
	public EnumInterface blockUser(Authentication auth, UserFormBean ufBean) throws InvalidRequestException
	{
		if (isRecentlyUpdated(ufBean))
		{
			try
			{
				logger.info("Inside UserBoImpl blockUser ::: ", ufBean.user.getUserId());
				ufBean.repoUser.setStatus(!ufBean.user.getStatus());// Negate Current Status
				ufBean.repoUser.modifiedUserInfo(auth);
				userDao.save(ufBean.repoUser);

				ufBean.messageCode = USER_BLOCKED_SUCCESSFULLY;
				return EReturn.Success;
			}
			finally
			{
				ufBean.tokenURL = null;
				ufBean.user = null;
			}
		}
		throw new InvalidRequestException(USER_DATA_UPDATED_RECENTLY);
	}

	@Override
	public EnumInterface deleteUser(Authentication auth, UserFormBean ufBean) throws InvalidRequestException
	{
		logger.info("Inside UserBoImpl deleteUser ::: ", ufBean.user.getUserId());
		userDao.deleteById(ufBean.user.getUserId());
		return EReturn.Success;
	}

	@Override
	public Users getUser(UserFormBean ufBean) throws InvalidRequestException
	{
		return userDao.getOne(ufBean.user.getEmployeeId());
	}

	@Override
	public List<Users> getUserByProducer(Authentication auth) throws InvalidRequestException
	{
		return userDao.findByProducerId(EAuth.User.getProducerId(auth));
	}

	private boolean isRecentlyUpdated(UserFormBean ufBean)
	{
		logger.info("Inside UserBoImpl isRecentlyUpdated ::: ", ufBean.user.getUserId());
		if (CommonValidator.isNotNullNotEmpty(ufBean.user))
		{
			Users user = userDao.getOne(ufBean.user.getEmployeeId());
			if (CommonValidator.isNotNullNotEmpty(user))
			{
				if (ChronoUnit.NANOS.between(ufBean.user.getModifiedDate().toLocalDateTime(), user.getModifiedDate().toLocalDateTime()) == 0)
				{
					ufBean.repoUser = user;
					return true;
				}
				return false;
			}
		}
		throw new InvalidRequestException(USER_NOT_FOUND);
	}

	@Override
	public EnumInterface saveUser(Authentication auth, UserFormBean ufBean) throws InvalidRequestException, InvalidKeyException
	{

		logger.info("UserBoImpl saveUser starts ::: ");
		IUsersMedia uMedia = ufBean.user.getPrimaryMedia();
		List<String> userNameList = userDao.checkUserNameEmailIdOrMobileNo(ufBean.user.getProducer().getProducerId(), uMedia.getEmailId(), uMedia.getMobileNo());
		if (CommonValidator.isListFirstNotEmpty(userNameList))
		{
			ufBean.user.createdUserProducerInfo(auth);

			ufBean.tokenURL = ServerUtilFactory._DomainUrl + ESecurity.Token.generate(ufBean.user, EFormAction.Verify);

			ufBean.user.setUserId("USR" + sequenceDao.getPrimaryKey(Users.class.getSimpleName()));
			ufBean.user.setStatus(false);
			ufBean.user.setUserPwdModFlag(true);
			ufBean.user = userDao.save(ufBean.user);

			if (CommonValidator.isNotNullNotEmpty(ufBean.user, ufBean.tokenURL))
			{
				try
				{
					HttpHeaders headers = new HttpHeaders();
					headers.add(AUTHORIZATION, ufBean.authToken);

					gKafkaProducer.sendMessage(ETopic.Internal, EMedia.Email, ETemplate.User_Create_Admin, ufBean);
					gKafkaProducer.sendMessage(ETopic.Internal, EMedia.Email, ETemplate.User_Create_Employee, ufBean);
					gKafkaProducer.sendMessage(ETopic.Internal, EMedia.SMS, ETemplate.SMS_Create_Employee, ufBean);

					ufBean.messageCode = USER_CREATED_SUCCESSFULLY;
					return EReturn.Success;
				}
				finally
				{
					ufBean.tokenURL = null;
					ufBean.user = null;
				}
			}
			throw new InvalidKeyException(USER_TOKEN_KEY_GENERATE_ISSUE);
		}
		throw new InvalidRequestException(USER_ALREADY_EXISTS);
	}

	@Override
	public List<Users> searchUser(Authentication auth, UserFormBean ufBean) throws InvalidRequestException
	{
		return userDao.searchUser(ufBean.searchParam);
	}

	@Override
	public EnumInterface updateUser(Authentication auth, UserFormBean ufBean) throws InvalidRequestException
	{
		if (isRecentlyUpdated(ufBean))
		{
			try
			{
				logger.info("UserBoImpl updateUser starts ::: ");
				ufBean.updateRepoUser(auth);
				userDao.save(ufBean.repoUser);

				ufBean.messageCode = USER_UPDATED_SUCCESSFULLY;
				logger.info("UserBoImpl updateUser ends ::: ");
				return EReturn.Success;
			}
			finally
			{
				ufBean.tokenURL = null;
				ufBean.user = null;
			}
		}
		throw new InvalidRequestException(USER_DATA_UPDATED_RECENTLY);
	}

	@Override
	public UserFormBean validateUser(String tokenKey) throws InvalidKeyException
	{
		if (CommonValidator.isNotNullNotEmpty(tokenKey))
		{
			logger.info("Inside UserBoImpl validateUser ::: ");
			UserFormBean ufBean = ESecurity.Token.validate(userDao, tokenKey, TOKEN_EXPIRY_DURATION);

			if (CommonValidator.isNotNullNotEmpty(ufBean))
			{
				return ufBean;
			}
		}
		throw new InvalidKeyException(USER_TOKEN_KEY_NOT_AVAILABLE_IN_REQUEST);
	}

	@Override
	public EnumInterface resendActivationLink(Authentication auth, UserFormBean ufBean)
	{
		ufBean.user = getUser(ufBean);
		ufBean.tokenURL = ServerUtilFactory._DomainUrl + ESecurity.Token.generate(ufBean.user, EFormAction.Verify);
		ufBean.user.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		ufBean.user.setStatus(false);
		userDao.save(ufBean.user);
		if (CommonValidator.isNotNullNotEmpty(ufBean.user, ufBean.tokenURL))
		{
			try
			{
				gKafkaProducer.sendMessage(ETopic.Internal, EMedia.Email, ETemplate.User_Create_Admin, ufBean);
				gKafkaProducer.sendMessage(ETopic.Internal, EMedia.Email, ETemplate.User_Create_Employee, ufBean);
				gKafkaProducer.sendMessage(ETopic.Internal, EMedia.SMS, ETemplate.SMS_Create_Employee, ufBean);

				ufBean.messageCode = ACTIVATION_LINK_SENT_SUCCESSFULLY;
				return EReturn.Success;
			}
			finally
			{
				ufBean.tokenURL = null;
				ufBean.user = null;
			}
		}
		return EReturn.Success;
	}
}
