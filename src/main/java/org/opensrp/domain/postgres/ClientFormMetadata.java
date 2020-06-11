package org.opensrp.domain.postgres;

import java.util.Date;

public class ClientFormMetadata {

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.id
	 *
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	private Long id;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.identifier
	 *
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	private String identifier;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.jurisdiction
	 *
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	private String jurisdiction;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.version
	 *
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	private String version;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.label
	 *
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	private String label;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.module
	 *
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	private String module;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.created_at
	 *
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	private Date createdAt;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.is_draft
	 *
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	private Boolean isDraft;

	/**
	 * This field was generated by MyBatis Generator.
	 * This field corresponds to the database column core.client_form_metadata.is_json_validator
	 *
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	private Boolean isJsonValidator;

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.id
	 *
	 * @return the value of core.client_form_metadata.id
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.id
	 *
	 * @param id the value for core.client_form_metadata.id
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.identifier
	 *
	 * @return the value of core.client_form_metadata.identifier
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.identifier
	 *
	 * @param identifier the value for core.client_form_metadata.identifier
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.jurisdiction
	 *
	 * @return the value of core.client_form_metadata.jurisdiction
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public String getJurisdiction() {
		return jurisdiction;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.jurisdiction
	 *
	 * @param jurisdiction the value for core.client_form_metadata.jurisdiction
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.version
	 *
	 * @return the value of core.client_form_metadata.version
	 * @mbg.generated Thu Jun 11 16:41:30 EAT 2020
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.version
	 *
	 * @param version the value for core.client_form_metadata.version
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.label
	 *
	 * @return the value of core.client_form_metadata.label
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.label
	 *
	 * @param label the value for core.client_form_metadata.label
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.module
	 *
	 * @return the value of core.client_form_metadata.module
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public String getModule() {
		return module;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.module
	 *
	 * @param module the value for core.client_form_metadata.module
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.created_at
	 *
	 * @return the value of core.client_form_metadata.created_at
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.created_at
	 *
	 * @param createdAt the value for core.client_form_metadata.created_at
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.is_draft
	 *
	 * @return the value of core.client_form_metadata.is_draft
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public Boolean getIsDraft() {
		return isDraft;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.is_draft
	 *
	 * @param isDraft the value for core.client_form_metadata.is_draft
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method returns the value of the database column core.client_form_metadata.is_json_validator
	 *
	 * @return the value of core.client_form_metadata.is_json_validator
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public Boolean getIsJsonValidator() {
		return isJsonValidator;
	}

	/**
	 * This method was generated by MyBatis Generator.
	 * This method sets the value of the database column core.client_form_metadata.is_json_validator
	 *
	 * @param isJsonValidator the value for core.client_form_metadata.is_json_validator
	 * @mbg.generated Thu Jun 11 16:41:31 EAT 2020
	 */
	public void setIsJsonValidator(Boolean isJsonValidator) {
		this.isJsonValidator = isJsonValidator;
	}
}
