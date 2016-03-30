package com.sampleAuth.mybatis.bean;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column User.id
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column User.userName
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private String username;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column User.email
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private String email;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column User.phone
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private String phone;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column User.created
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private String created;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column User.updated
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private String updated;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table User
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column User.id
	 * @return  the value of User.id
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column User.id
	 * @param id  the value for User.id
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column User.userName
	 * @return  the value of User.userName
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column User.userName
	 * @param username  the value for User.userName
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column User.email
	 * @return  the value of User.email
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column User.email
	 * @param email  the value for User.email
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column User.phone
	 * @return  the value of User.phone
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column User.phone
	 * @param phone  the value for User.phone
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column User.created
	 * @return  the value of User.created
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column User.created
	 * @param created  the value for User.created
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column User.updated
	 * @return  the value of User.updated
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column User.updated
	 * @param updated  the value for User.updated
	 * @mbggenerated  Tue Mar 22 14:33:36 IST 2016
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}
}