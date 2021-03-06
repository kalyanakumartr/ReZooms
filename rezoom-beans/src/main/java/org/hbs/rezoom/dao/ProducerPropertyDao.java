package org.hbs.rezoom.dao;

import java.util.List;

import org.hbs.rezoom.bean.model.ProducersProperty;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.security.resource.IPath.EMediaMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerPropertyDao extends JpaRepository<ProducersProperty, String>
{

	@Query("Select PP.producer.producerId, PP.value From ProducersProperty PP where PP.status = true and PP.media = :eMedia and PP.mediaMode = :eMediaMode")
	public List<Object[]> getProperty(@Param("eMedia") EMedia eMedia, @Param("eMediaMode") EMediaMode eMediaMode);
}
