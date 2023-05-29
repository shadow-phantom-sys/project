package com.rideManagement.model;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RideManagement {
	@Id
	private int id;
	private String RideFrom;
	private String RideTo;
	private Date RideStartsOn;
	private Time RideTime;
	private int RideFare;
	private String VehicleRegistrationNo;
	private int MotoristUserId;
	private int NoOfSeatsAvailable;
	private String From;
	private String To;
	private int DistanceKMS;
	private int BookingId;
	private int BookedOn;
	private int RideUserId;
	private int NoOfSeats;
	private int TotalAmount;
	private String PaymentMode;
	private int RideScheduleId;
}
