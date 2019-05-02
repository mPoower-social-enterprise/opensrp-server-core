package org.opensrp.domain.postgres;

public class Plan {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.plan.id
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.plan.json
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    private Object json;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.plan.date_deleted
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    private Long dateDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.plan.server_version
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    private Long serverVersion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.plan.id
     *
     * @return the value of core.plan.id
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.plan.id
     *
     * @param id the value for core.plan.id
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.plan.json
     *
     * @return the value of core.plan.json
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public Object getJson() {
        return json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.plan.json
     *
     * @param json the value for core.plan.json
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public void setJson(Object json) {
        this.json = json;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.plan.date_deleted
     *
     * @return the value of core.plan.date_deleted
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public Long getDateDeleted() {
        return dateDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.plan.date_deleted
     *
     * @param dateDeleted the value for core.plan.date_deleted
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public void setDateDeleted(Long dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.plan.server_version
     *
     * @return the value of core.plan.server_version
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public Long getServerVersion() {
        return serverVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.plan.server_version
     *
     * @param serverVersion the value for core.plan.server_version
     *
     * @mbg.generated Thu May 02 10:52:55 EAT 2019
     */
    public void setServerVersion(Long serverVersion) {
        this.serverVersion = serverVersion;
    }
}