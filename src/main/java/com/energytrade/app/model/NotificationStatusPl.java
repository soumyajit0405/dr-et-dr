package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the notification_status_pl database table.
 * 
 */
@Entity
@Table(name="notification_status_pl")
@NamedQuery(name="NotificationStatusPl.findAll", query="SELECT n FROM NotificationStatusPl n")
public class NotificationStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="notif_status_id")
	private int notifStatusId;

	private String description;

	@Column(name="type_name")
	private String typeName;

	//bi-directional many-to-one association to AllDrNotification
	@OneToMany(mappedBy="notificationStatusPl")
	private List<AllDrNotification> allDrNotifications;

	public NotificationStatusPl() {
	}

	public int getNotifStatusId() {
		return this.notifStatusId;
	}

	public void setNotifStatusId(int notifStatusId) {
		this.notifStatusId = notifStatusId;
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
		allDrNotification.setNotificationStatusPl(this);

		return allDrNotification;
	}

	public AllDrNotification removeAllDrNotification(AllDrNotification allDrNotification) {
		getAllDrNotifications().remove(allDrNotification);
		allDrNotification.setNotificationStatusPl(null);

		return allDrNotification;
	}

}