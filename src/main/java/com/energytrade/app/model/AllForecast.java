package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the all_forecasts database table.
 * 
 */
@Entity
@Table(name="all_forecasts")
@NamedQuery(name="AllForecast.findAll", query="SELECT a FROM AllForecast a")
public class AllForecast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="forecast_id")
	private int forecastId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="ev_power")
	private BigDecimal evPower;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="forecast_date")
	private Date forecastDate;

	@Column(name="generator_power")
	private BigDecimal generatorPower;

	private byte softdeleteflag;

	@Column(name="solar_power")
	private BigDecimal solarPower;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@ManyToOne
	@JoinColumn(name="timeslot_id")
	private AllTimeslot timeslot;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	@Column(name="user_load")
	private BigDecimal userLoad;

	//bi-directional many-to-one association to AllUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private AllUser allUser;

	public AllForecast() {
	}

	public int getForecastId() {
		return this.forecastId;
	}

	public void setForecastId(int forecastId) {
		this.forecastId = forecastId;
	}

	public byte getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
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

	public BigDecimal getEvPower() {
		return this.evPower;
	}

	public void setEvPower(BigDecimal evPower) {
		this.evPower = evPower;
	}

	public Date getForecastDate() {
		return this.forecastDate;
	}

	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}

	public BigDecimal getGeneratorPower() {
		return this.generatorPower;
	}

	public void setGeneratorPower(BigDecimal generatorPower) {
		this.generatorPower = generatorPower;
	}

	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public BigDecimal getSolarPower() {
		return this.solarPower;
	}

	public void setSolarPower(BigDecimal solarPower) {
		this.solarPower = solarPower;
	}

	public Date getSyncTs() {
		return this.syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public AllTimeslot getTimeslotId() {
		return this.timeslot;
	}

	public void setTimeslotId(AllTimeslot timeslot) {
		this.timeslot = timeslot;
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

	public BigDecimal getUserLoad() {
		return this.userLoad;
	}

	public void setUserLoad(BigDecimal userLoad) {
		this.userLoad = userLoad;
	}

	public AllUser getAllUser() {
		return this.allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

}