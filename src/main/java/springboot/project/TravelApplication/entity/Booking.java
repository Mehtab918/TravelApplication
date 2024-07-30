package springboot.project.TravelApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
public class Booking 
{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    @DateTimeFormat(pattern="HH:mm")
	    private LocalTime pickupTime;

	    @Column(nullable = false)
	    private String pickupLocation;

	    @Column(nullable = false)
	    private String dropoffLocation;

	    @Column(nullable = false)
	    @DateTimeFormat(pattern="HH:mm")
	    private LocalTime dropoffTime;
	     
	    @Column(nullable = false)
	    private double distance;
	    
	    @Column(nullable = false)
	    private int time;
	    
	    @Column(nullable = false)
	    private String cabType;

	    @Column(nullable = false)
	    private Double fare;
	
	    
}

