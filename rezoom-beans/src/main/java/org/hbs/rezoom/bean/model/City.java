package org.hbs.rezoom.bean.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hbs.rezoom.util.ICRUDBean;

@Entity
@Table(name = "city")
public class City implements ICRUDBean, Comparable<City>
{

	private static final long	serialVersionUID	= 3432894599006955455L;

	private String				autoId;
	private String				city;
	private State				state;
	private String				zipCode;
	private boolean				status;

	public City()
	{
		super();
	}

	@Id
	@Column(name = "autoId")
	public String getAutoId()
	{
		return autoId;
	}

	@Column(name = "city")
	public String getCity()
	{
		return city;
	}

	@ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "state")
	public State getState()
	{
		return state;
	}

	@Column(name = "status")
	public boolean isStatus()
	{
		return status;
	}

	@Column(name = "zipcode")
	public String getZipCode()
	{
		return zipCode;
	}

	public void setAutoId(String autoId)
	{
		this.autoId = autoId;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	@Override
	public int compareTo(City city)
	{
		return this.city.compareTo(city.getCity());
	}

}
