package org.opensrp.domain.postgres;

import java.util.Date;

public class Stock {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column core.stock.id
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column core.stock.json
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	private Object json;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column core.stock.date_deleted
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	private Date dateDeleted;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column core.stock.id
	 * @return  the value of core.stock.id
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column core.stock.id
	 * @param id  the value for core.stock.id
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column core.stock.json
	 * @return  the value of core.stock.json
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	public Object getJson() {
		return json;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column core.stock.json
	 * @param json  the value for core.stock.json
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	public void setJson(Object json) {
		this.json = json;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column core.stock.date_deleted
	 * @return  the value of core.stock.date_deleted
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	public Date getDateDeleted() {
		return dateDeleted;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column core.stock.date_deleted
	 * @param dateDeleted  the value for core.stock.date_deleted
	 * @mbg.generated  Fri Dec 11 16:02:45 EAT 2020
	 */
	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}
}
