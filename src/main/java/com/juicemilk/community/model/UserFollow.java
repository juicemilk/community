package com.juicemilk.community.model;

public class UserFollow {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_FOLLOW.ID
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_FOLLOW.IDOL
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    private Long idol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_FOLLOW.FAN
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    private Long fan;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USER_FOLLOW.GMT_CREATE
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    private Long gmtCreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_FOLLOW.ID
     *
     * @return the value of USER_FOLLOW.ID
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_FOLLOW.ID
     *
     * @param id the value for USER_FOLLOW.ID
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_FOLLOW.IDOL
     *
     * @return the value of USER_FOLLOW.IDOL
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public Long getIdol() {
        return idol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_FOLLOW.IDOL
     *
     * @param idol the value for USER_FOLLOW.IDOL
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public void setIdol(Long idol) {
        this.idol = idol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_FOLLOW.FAN
     *
     * @return the value of USER_FOLLOW.FAN
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public Long getFan() {
        return fan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_FOLLOW.FAN
     *
     * @param fan the value for USER_FOLLOW.FAN
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public void setFan(Long fan) {
        this.fan = fan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USER_FOLLOW.GMT_CREATE
     *
     * @return the value of USER_FOLLOW.GMT_CREATE
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USER_FOLLOW.GMT_CREATE
     *
     * @param gmtCreate the value for USER_FOLLOW.GMT_CREATE
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}