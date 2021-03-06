package org.hbs.rezoom.dao;

import java.util.List;

import org.hbs.rezoom.bean.model.channel.EmailAttachments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao extends CrudRepository<EmailAttachments, String>
{
	@Query("From EmailAttachments where message.messageId = ?0")
	List<EmailAttachments> getByMessageId(final String messageId);

	@Query("select count(*) from EmailAttachments where message.messageId = ?0")
	long countByMessageId(final String messageId);

}
