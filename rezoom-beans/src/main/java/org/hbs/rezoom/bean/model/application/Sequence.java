package org.hbs.rezoom.bean.model.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hbs.rezoom.bean.model.IProducers;
import org.hbs.rezoom.bean.model.Producers;
import org.hbs.rezoom.util.ICRUDBean;

@Entity
@Table(name = "sequence")
public class Sequence implements ICRUDBean
{
	private static final long	serialVersionUID	= 1696926036927427862L;

	protected IProducers		producer;
	private long				sequenceId;
	private String				sequenceKey;

	public Sequence()
	{
		super();
	}

	public Sequence(long sequenceId, String sequenceKey)
	{
		super();
		this.sequenceId = sequenceId;
		this.sequenceKey = sequenceKey;
	}

	@ManyToOne(targetEntity = Producers.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "producerId")
	public IProducers getProducer()
	{
		return producer;
	}

	@Id
	@Column(name = "sequenceId")
	public long getSequenceId()
	{
		return sequenceId;
	}

	@Column(name = "sequenceKey")
	public String getSequenceKey()
	{
		return sequenceKey;
	}

	public void setProducer(IProducers producer)
	{
		this.producer = producer;
	}

	public void setSequenceId(long sequenceId)
	{
		this.sequenceId = sequenceId;
	}

	public void setSequenceKey(String sequenceKey)
	{
		this.sequenceKey = sequenceKey;
	}

}
