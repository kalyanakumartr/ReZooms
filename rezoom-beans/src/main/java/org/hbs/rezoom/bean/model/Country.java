package org.hbs.rezoom.bean.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hbs.rezoom.util.ICRUDBean;

@Entity
@Table(name = "country")
public class Country implements ICRUDBean, Comparable<Country>
{

	private static final long	serialVersionUID	= 8372130046238222330L;
	protected String			country;
	protected String			countryName;
	protected int				displayOrder;
	protected boolean			status;

	public Country()
	{
		super();
	}

	public Country(String country, String countryName, boolean status)
	{
		super();
		this.country = country;
		this.countryName = countryName;
		this.status = status;
	}

	@Id
	@Column(name = "country")
	public String getCountry()
	{
		return country;
	}

	@Column(name = "countryName")
	public String getCountryName()
	{
		return countryName;
	}

	@Column(name = "displayOrder")
	public int getDisplayOrder()
	{
		return displayOrder;
	}

	@Column(name = "status")
	public boolean isStatus()
	{
		return status;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public void setDisplayOrder(int displayOrder)
	{
		this.displayOrder = displayOrder;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	@Override
	public int compareTo(Country country)
	{
		return countryName.compareTo(country.getCountryName());
	}
}
