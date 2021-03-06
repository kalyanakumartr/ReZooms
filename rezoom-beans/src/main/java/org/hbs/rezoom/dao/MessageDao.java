package org.hbs.rezoom.dao;

import java.util.List;

import org.hbs.rezoom.bean.model.IMessages;
import org.hbs.rezoom.bean.model.Messages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends CrudRepository<Messages, String>
{

	@Query("From Messages where producer.producerId = ?0 and messageId = ?1 and status = true")
	List<IMessages> getByMessageId(final String producerId, final String messageId);

	@Query("From Messages where producer.producerId = ?0 and messageType = ?1 and status = true")
	List<IMessages> getByMessageType(final String producerId, final String messageType);

}
