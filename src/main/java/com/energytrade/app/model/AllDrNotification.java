package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the all_dr_notifications database table.
 * 
 */
@Entity
@Table(name="all_dr_notifications")
@NamedQuery(name="AllDrNotification.findAll", query="SELECT a FROM AllDrNotification a")
public class AllDrNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="notification_id")
	private int notificationId;

	@Column(name="action_id")
	private int actionId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to NotificationTypePl
	@ManyToOne
	@JoinColumn(name="notification_type")
	private NotificationTypePl notificationTypePl;

	//bi-directional many-to-one association to NotificationStatusPl
	@ManyToOne
	@JoinColumn(name="status")
	private NotificationStatusPl notificationStatusPl;

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private AllUser allUser;

	public AllDrNotification() {
	}

	public int getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public int getActionId() {
		return this.actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

	public NotificationTypePl getNotificationTypePl() {
		return this.notificationTypePl;
	}

	public void setNotificationTypePl(NotificationTypePl notificationTypePl) {
		this.notificationTypePl = notificationTypePl;
	}

	public NotificationStatusPl getNotificationStatusPl() {
		return this.notificationStatusPl;
	}

	public void setNotificationStatusPl(NotificationStatusPl notificationStatusPl) {
		this.notificationStatusPl = notificationStatusPl;
	}

	public AllUser getAllUser() {
		return this.allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

}