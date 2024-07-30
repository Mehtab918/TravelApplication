package springboot.project.TravelApplication.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import springboot.project.TravelApplication.entity.Booking;
import springboot.project.TravelApplication.service.BookingService;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
     
    @GetMapping("/home")
    public String homePage() {
        return "home"; 
    }
   
    @GetMapping("/bookings/new")  //Booking service pe click krne se ye url aa rha hai
    public String showBookingPage(Model model) {
        model.addAttribute("booking", new Booking());   // Booking service click krne se ye method chal rha hai ... or ye booking.html open kr rha hai 
        return "booking";  }

    @GetMapping("/bookingLists")
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookingList"; // Assume you have a bookingList.html to display the bookings
    }
    
    @GetMapping("/api/bookings")
    @ResponseBody
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            // Validate if cab_type is not null
            if (booking.getCabType() == null) {
                return ResponseEntity.badRequest().body("Cab type cannot be null");
            }
            
            // Proceed with creating the booking
            Booking savedBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create booking: " + e.getMessage());
        }
    }

    @PostMapping("/calculateFare")
    public String calculateFare(@ModelAttribute Booking booking, Model model) {
        double fare = bookingService.calculateFare(booking.getDistance(), booking.getTime(), booking.getCabType());
        booking.setFare(fare);
        model.addAttribute("booking", booking);
        return "booking";
    }
    
    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute Booking booking, Model model) {
        // Save the booking
        bookingService.save(booking); // dtabase record

        model.addAttribute("booking", booking);
        return "confirmation"; // your confirmation page
    }
}
