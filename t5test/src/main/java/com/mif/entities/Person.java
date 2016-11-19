package com.mif.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mif.data.Regions;

/**
 * The Person entity.
 */
// @Entity
@SuppressWarnings("serial")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Version
	@Column(nullable = false)
	private Integer version;

	@Column(length = 10, nullable = false)
	@NotNull
	@Size(max = 10)
	private String firstName;

	@Column(length = 10, nullable = false)
	@NotNull
	@Size(max = 10)
	private String lastName;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Regions region;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date startDate;

	public String toString() {
		final String DIVIDER = ", ";

		StringBuilder buf = new StringBuilder();
		buf.append(this.getClass().getSimpleName() + ": ");
		buf.append("[");
		buf.append("id=" + id + DIVIDER);
		buf.append("version=" + version + DIVIDER);
		buf.append("firstName=" + firstName + DIVIDER);
		buf.append("lastName=" + lastName + DIVIDER);
		buf.append("region=" + region + DIVIDER);
		buf.append("startDate=" + startDate);
		buf.append("]");
		return buf.toString();
	}

	// Default constructor is required by JPA.
	public Person() {
	}

	public Person(String firstName, String lastName, Regions region, Date startDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.region = region;
		this.startDate = startDate;
	}

	public Person(Long id, int version, String firstName, String lastName, Regions region, Date startDate) {
		super();
		this.id = id;
		this.version = version;
		this.firstName = firstName;
		this.lastName = lastName;
		this.region = region;
		this.startDate = startDate;
	}

	// The need for an equals() method is discussed at
	// http://www.hibernate.org/109.html

	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof Person) && id != null && id.equals(((Person) obj).getId());
	}

	// The need for a hashCode() method is discussed at
	// http://www.hibernate.org/109.html

	@Override
	public int hashCode() {
		return id == null ? super.hashCode() : id.hashCode();
	}

	/*
	 * @PrePersist
	 * 
	 * @PreUpdate public void validate() throws ValidationException { }
	 */

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Regions getRegion() {
		return region;
	}

	public void setRegion(Regions region) {
		this.region = region;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}