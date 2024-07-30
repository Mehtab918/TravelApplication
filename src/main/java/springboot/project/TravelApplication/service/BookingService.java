package springboot.project.TravelApplication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.project.TravelApplication.entity.Booking;
import springboot.project.TravelApplication.repository.BookingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    
    public Booking save(Booking booking)
    {
    	return bookingRepository.save(booking);
    }
    
    public double calculateFare(double distance, int time, String cabType) {
        // Your fare calculation logic goes here
        double baseFare = 50.0;
        double distanceFare = distance * 10.0; // Assuming $10 per kilometer
        double timeFare = time * 0.5; // Assuming $0.5 per minute

        // Additional fare based on cab type
        double cabTypeFare = 0.0;
        if (cabType.equalsIgnoreCase("luxury")) {
            cabTypeFare = 100.0;
        } else if (cabType.equalsIgnoreCase("standard")) {
            cabTypeFare = 50.0;
        }

        // Total fare calculation
        double totalFare = baseFare + distanceFare + timeFare + cabTypeFare;  
        return totalFare;
    }
}