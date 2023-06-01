//package com.context;
//
//package RideSharingPlatform.Context;
//
//import javax.management.relation.Role;
//
//import com.rideManagement.model.Booking;
//import com.rideManagement.model.Distance;
//
//import RideSharingPlatform.Authentication.Models.*;
//import RideSharingPlatform.Microservices.IncidentManagement.Models.*;
//import RideSharingPlatform.Microservices.RideManagement.Models.*;
//import RideSharingPlatform.Microservices.UserVerification.Models.*;
//import RideSharingPlatform.Microservices.VehicleManagement.Models.*;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//@Entity
//@Table
//public class RideDbContext extends DbContext {
//    public RideDbContext(DbContextOptions<RideDbContext> options) {
//        super(options);
//    }
//
//    // microservice-user verification
////    public DbSet<Company> Companies;
////    public DbSet<UserApplication> UserApplications;
////    public DbSet<DrivingLicense> DrivingLicenses;
////    public DbSet<Role> Roles;
////    public DbSet<AuthUser> AuthUser;
//
//    // microservice-vehicle management
////    public DbSet<Vehicle> Vehicles;
////    public DbSet<VehicleDetail> VehicleDetails;
////    public DbSet<VehicleType> VehicleTypes;
//
//    // microservice-ride management
//    public DbSet<Booking> Bookings;
//    public DbSet<Distance> Distances;
//    public DbSet<RideSchedules> RideSchedules;
//
//    // microservices-incident management
////    public DbSet<Incident> Incidents;
////    public DbSet<IncidentTypes> IncidentTypes;
////    public DbSet<InvestigationDetails> InvestigationDetails;
//
//    protected void OnModelCreating(ModelBuilder modelBuilder) {
//        modelBuilder.Entity<Role>().HasData(
//            new Role(1, "Motorist"),
//            new Role(2, "Rider"),
//            new Role(3, "SecurityHead")
//        );
//
//        modelBuilder.Entity<Company>().HasData(
//            new Company(1, "Cognizant", "Unitech", "Amit Sau", "92123456789")
//        );
//
//        modelBuilder.Entity<UserApplication>().HasData(
//            new UserApplication(1, "Niladri", "niladri@cts.com", "9123456789", "Intern", 1, "345678", "786556784567",
//                    "New", 1, "Niladri@123")
//        );
//
//        modelBuilder.Entity<DrivingLicense>().HasData(
//            new DrivingLicense(1, "FER6578HYU", LocalDate.parse("2023-07-19"), "Beltala", "Bike", 1)
//        );
//
//        modelBuilder.Entity<AuthUser>().HasData(
//            new AuthUser(1, "Amit Sau", "amit@cts.com", "92123456789", "Amit@123", 3)
//        );
//
//        // microservice-vehicle management
//
//        modelBuilder.Entity<VehicleType>().HasData(
//            new VehicleType(1, "SUV", 7, 13),
//            new VehicleType(2, "Sedan", 4, 12),
//            new VehicleType(3, "2Wheeler", 1, 9)
//        );
//
//        // microservice-ride management
//
//        modelBuilder.Entity<Distance>().HasData(
//            new Distance(1, "Unitech", "Dunlop", 10),
//            new Distance(2, "Unitech", "SlatLake", 5)
//        );
//
//        // microservice-incident management
//
//        modelBuilder.Entity<IncidentTypes>().HasData(
//            new IncidentTypes(1, "Accident", 1),
//            new IncidentTypes(2, "Theft", 2),
//            new IncidentTypes(3, "Driver Misbehave", 1)
//        );
//    }
//}
//

//-------------------------------------------------------------------------------------------
package com.context;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.modelmapper.ModelMapper;

import com.rideManagement.model.Booking;
import com.rideManagement.model.Distance;
import com.rideManagement.model.RideSchedules;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class RideDbContext {
    private SessionFactory sessionFactory;

    // microservice-user verification
//    public List<Company> Companies;
//    public List<UserApplication> UserApplications;
//    public List<DrivingLicense> DrivingLicenses;
//    public List<Role> Roles;
//    public List<AuthUser> AuthUser;

    // microservice-vehicle management
//    public List<Vehicle> Vehicles;
//    public List<VehicleDetail> VehicleDetails;
//    public List<VehicleType> VehicleTypes;

    // microservice-ride management
    public List<Booking> Bookings;
    public List<Distance> Distances;
    public List<RideSchedules> RideSchedules;

    // microservices-incident management
//    public List<Incident> Incidents;
//    public List<IncidentTypes> IncidentTypes;
//    public List<InvestigationDetails> InvestigationDetails;

    // Constructor
    public RideDbContext() {
        configureHibernate();
        initializeRepositories();
    }

    // Configure Hibernate session factory
    private void configureHibernate() {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create Hibernate session factory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Initialize your repositories or data access objects
    private void initializeRepositories() {
        // Initialize your repositories or data access objects here using the session factory
        // Example: Companies = new CompanyRepository(sessionFactory);
        // Replace 'CompanyRepository' with your actual repository class name
    }

    // Define your Hibernate configuration and mappings here

    // OnModelCreating method
    protected void OnModelCreating(ModelMapper modelBuilder) {
        // Configure your Hibernate mappings and data seeding here
    }
}

