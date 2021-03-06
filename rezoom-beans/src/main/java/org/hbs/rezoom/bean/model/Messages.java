package org.hbs.rezoom.bean.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hbs.rezoom.security.resource.IPath.ETemplate;

@Entity
@Table(name = "messages")
public class Messages extends MessagesBase
{

	private static final long serialVersionUID = 684883062828693721L;

	public Messages()
	{
		super();
	}

	public Messages(ETemplate eTemplate)
	{
		this.messageId = eTemplate.name();
	}

}