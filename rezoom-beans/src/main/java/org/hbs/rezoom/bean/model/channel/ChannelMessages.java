package org.hbs.rezoom.bean.model.channel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hbs.rezoom.bean.model.CreatedModifiedUsers;
import org.hbs.rezoom.bean.model.IConfiguration;
import org.hbs.rezoom.bean.model.MessagesBase;
import org.hbs.rezoom.security.resource.IPath.EAuth;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.util.CommonValidator;
import org.hbs.rezoom.util.EnumInterface;
import org.springframework.security.core.Authentication;

import com.google.gson.Gson;

@Entity
@Table(name = "channel_messages")
public class ChannelMessages extends MessagesBase implements IChannelMessages, Cloneable
{
	private static final long		serialVersionUID	= 134212154064737177L;

	protected CreatedModifiedUsers	byUser				= new CreatedModifiedUsers();

	protected String				expiryDate;

	protected Timestamp				nextDeliveryDate;

	protected String				scheduledDate;

	public ChannelMessages()
	{
		super();
		this.messageId = getBusinessKey();
	}

	public ChannelMessages(IConfiguration configuration)
	{
		super();
		this.configuration = configuration;
	}

	public ChannelMessages(EnumInterface messageId)
	{
		super();
		this.messageId = messageId.name();
	}

	public ChannelMessages(String messageId, CreatedModifiedUsers byUser, String expiryDate, String message, String messageName, EMedia media, String scheduledDate)
	{
		super();
		this.messageId = messageId;
		this.byUser = byUser;
		this.expiryDate = expiryDate;
		this.message = message;
		this.messageName = messageName;
		this.media = media;
		this.scheduledDate = scheduledDate;
	}

	public ChannelMessages(String messageId, String message, EMedia media)
	{
		super();
		this.messageId = messageId;
		this.message = message;
		this.media = media;
	}

	public ChannelMessages(Authentication auth, EnumInterface eMessage)
	{
		super();
		this.messageId = eMessage.name();
		this.createdUserProducerInfo(auth);
	}

	@Embedded
	public CreatedModifiedUsers getByUser()
	{
		return byUser;
	}

	@Override
	@Column(name = "expiryDate")
	public String getExpiryDate()
	{
		return expiryDate;
	}

	@Id
	@Column(name = "messageId")
	public String getMessageId()
	{
		return messageId;
	}

	@Column(name = "nextDeliveryDate")
	public Timestamp getNextDeliveryDate()
	{
		return nextDeliveryDate;
	}

	@Override
	@Column(name = "scheduledDate")
	public String getScheduledDate()
	{
		return scheduledDate;
	}

	@Transient
	public boolean isInformationExpired() throws ParseException
	{
		if (CommonValidator.isNotNullNotEmpty(expiryDate, producer.getUsers(), producer.getUsers().getCountry()))
		{
			Date expDate = EDate.DD_MMM_YYYY_HH_MM_AM_PM.formatted(expiryDate);

			Date curDate = EDate.DD_MMM_YYYY_HH_MM_AM_PM.byTimeZone(new Date(), producer.getUsers().getCountry().getCountry());

			return expDate.getTime() >= curDate.getTime();

		}
		return false;
	}

	@Transient
	public boolean isScheduledDateReached() throws ParseException
	{
		if (CommonValidator.isNotNullNotEmpty(scheduledDate, producer.getUsers(), producer.getUsers().getCountry()))
		{
			Date schDate = EDate.DD_MMM_YYYY_HH_MM_AM_PM.formatted(scheduledDate);

			Date curDate = EDate.DD_MMM_YYYY_HH_MM_AM_PM.byTimeZone(new Date(), producer.getUsers().getCountry().getCountry());

			return schDate.getTime() >= curDate.getTime();
		}

		return false;
	}

	public void setByUser(CreatedModifiedUsers byUser)
	{
		this.byUser = byUser;
	}

	@Override
	public void setExpiryDate(String expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public void setNextDeliveryDate(Timestamp nextDeliveryDate)
	{
		this.nextDeliveryDate = nextDeliveryDate;
	}

	@Override
	public void setScheduledDate(String scheduledDate)
	{
		this.scheduledDate = scheduledDate;
	}

	public void appendDiscussion(Discussion... discussions)
	{
		Forum forum = null;
		if (CommonValidator.isNotNullNotEmpty(this.message))
		{
			forum = new Gson().fromJson(this.message, Forum.class);
		}
		else
		{
			forum = new Forum();
		}
		forum.setDiscussions(discussions);
		this.message = new Gson().toJson(forum);
	}

	public void createdUserProducerInfo(Authentication auth)
	{
		this.byUser.setCreatedUser(EAuth.User.getUser(auth));
		this.createdDate = new Timestamp(System.currentTimeMillis());
		this.byUser.setModifiedUser(EAuth.User.getUser(auth));
		this.modifiedDate = new Timestamp(System.currentTimeMillis());
		this.producer = EAuth.User.getProducer(auth);
	}

	public void modifiedUserInfo(Authentication auth)
	{
		this.byUser.setModifiedUser(EAuth.User.getUser(auth));
		this.modifiedDate = new Timestamp(System.currentTimeMillis());
	}
}
