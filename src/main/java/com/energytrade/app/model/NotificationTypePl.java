package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the notification_type_pl database table.
 * 
 */
@Entity
@Table(name="notification_type_pl")
@NamedQuery(name="NotificationTypePl.findAll", query="SELECT n FROM NotificationTypePl n")
public class NotificationTypePl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="notification_type_id")
	private int notificationTypeId;

	private String description;

	@Column(name="type_name")
	private String typeName;

	//bi-directional many-to-one association to AllDrNotification
	@OneToMany(mappedBy="notificationTypePl")
	private List<AllDrNotification> allDrNotifications;

	public NotificationTypePl() {
	}

	public int getNotificationTypeId() {
		return this.notificationTypeId;
	}

	public void setNotificationTypeId(int notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<AllDrNotification> getAllDrNotifications() {
		return this.allDrNotifications;
	}

	public void setAllDrNotifications(List<AllDrNotification> allDrNotifications) {
		this.allDrNotifications = allDrNotifications;
	}

	public AllDrNotification addAllDrNotification(AllDrNotification allDrNotification) {
		getAllDrNotifications().add(allDrNotification);
		allDrNotification.setNotificationTypePl(this);

		return allDrNotification;
	}

	public AllDrNotification removeAllDrNotification(AllDrNotification allDrNotification) {
		getAllDrNotifications().remove(allDrNotification);
		allDrNotification.setNotificationTypePl(null);

		return allDrNotification;
	}

}