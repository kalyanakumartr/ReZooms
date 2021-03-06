package org.hbs.rezoom.bean.model.application;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hbs.rezoom.bean.model.CommonAddress;

@Entity
@Table(name = "resume_address")
public class _Address extends CommonAddress
{
	private static final long	serialVersionUID	= -4415169545653808849L;

	protected ResumeData		resumeData;

	public _Address()
	{
		super();
	}

	public _Address(ResumeData resumeData)
	{
		super();
		this.resumeData = resumeData;
	}

	@ManyToOne(targetEntity = ResumeData.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "professionalId")
	public ResumeData getResumeData()
	{
		return resumeData;
	}

	public void setResumeData(ResumeData resumeData)
	{
		this.resumeData = resumeData;
	}

}
