package org.hbs.rezoom.bean.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "producersattachments")
public class ProducersAttachments extends CommonFileUpload implements IProducersAttachments
{
	private static final long	serialVersionUID	= -748718055395205690L;

	protected String			documentDesc;

	protected String			documentStatus;

	protected IProducers		producer;

	public ProducersAttachments()
	{
		super();
	}

	public ProducersAttachments(int autoId, IProducers producer, String documentStatus, String documentDesc)
	{
		super();
		this.autoId = autoId;
		this.producer = producer;
		this.documentStatus = documentStatus;
		this.documentDesc = documentDesc;
	}

	@Column(name = "documentDesc")
	public String getDocumentDesc()
	{
		return documentDesc;
	}

	@Column(name = "documentStatus")
	public String getDocumentStatus()
	{
		return documentStatus;
	}

	@ManyToOne(targetEntity = Producers.class)
	@JoinColumn(name = "producerId", nullable = false)
	public IProducers getProducer()
	{
		return producer;
	}

	public void setDocumentDesc(String documentDesc)
	{
		this.documentDesc = documentDesc;
	}

	public void setDocumentStatus(String documentStatus)
	{
		this.documentStatus = documentStatus;
	}

	public void setProducer(IProducers producer)
	{
		this.producer = producer;
	}

}
