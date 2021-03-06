package org.hbs.rezoom.bean.model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.util.EBusinessKey;
import org.hbs.rezoom.util.EnumInterface;

public interface IMessages extends IProducersBase, EBusinessKey
{
	public enum EMessageCategory implements EnumInterface
	{
		Alerts, Banner, Discussion, Email, NewsFeed, Notifications, PopUp, SMS, UpcomingEvents, WhatsApp;

		public static Map<String, String> getTypeList()
		{
			Map<String, String> hmTypeList = new LinkedHashMap<String, String>();

			for (EMessageCategory msg : EMessageCategory.values())
			{
				hmTypeList.put(msg.name(), msg.name());
			}

			return hmTypeList;

		}

	}

	public enum EMessageStatus implements EnumInterface
	{
		Abort, Failed, InComplete, Pending, Processing, Ready, Retry, Send;

	}

	String generateVTLMessage() throws IOException;

	String generateVTLSubject() throws IOException;

	IConfiguration getConfiguration();

	Map<String, Object> getDataMap();

	String getDataMapTemplateName();

	EMedia getMedia();

	String getMessage();

	String getMessageId();

	String getMessageName();

	String[] getRecipients();

	String getSubject();

	EnumInterface getTemplateName();

	void setConfiguration(IConfiguration configuration);

	void setDataMap(Map<String, Object> dataMap);

	void setDataMapTemplateName(String dataMapTemplateName);

	void setMedia(EMedia media);

	void setMessage(String message);

	void setMessageId(String messageId);

	void setMessageName(String messageName);

	void setRecipients(String... recipients);

	void setSubject(String subject);

	boolean isTextHTML();

	public void setTextHTML(boolean textHTML);

}