package org.hbs.rezoom.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.security.resource.IPath.EMediaMode;
import org.springframework.stereotype.Repository;

public class ProducerPropertyDaoImpl
{
	private static final long	serialVersionUID	= 8378932574374701771L;

	@PersistenceContext
	private EntityManager		entityManager;

	@SuppressWarnings("unchecked")
	public List<Object[]> getProperty(EMedia eMedia, EMediaMode eMediaMode)
	{
		StringBuffer sbSelectQuery = new StringBuffer();
		sbSelectQuery.append("Select PP.producer.producerId, PP.value From ProducersProperty PP ");
		sbSelectQuery.append("Where PP.status = true And PP.media = :eMedia And PP.mediaMode = :eMediaMode");

		Query query = entityManager.createQuery(sbSelectQuery.toString());
		query.setParameter("eMedia", eMedia);
		query.setParameter("eMediaMode", eMediaMode);

		return query.getResultList();
	}

}
