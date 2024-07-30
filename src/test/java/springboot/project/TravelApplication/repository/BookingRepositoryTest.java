package springboot.project.TravelApplication.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springboot.project.TravelApplication.entity.Booking;
import springboot.project.TravelApplication.repository.BookingRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testSaveBooking() {
        Booking booking = new Booking();
        booking.setPickupTime(LocalTime.of(10, 0));
        booking.setPickupLocation("Location A");
        booking.setDropoffLocation("Location B");
        booking.setDropoffTime(LocalTime.of(10, 30));
        booking.setDistance(10.0);
        booking.setTime(30);
        booking.setCabType("Sedan");
        booking.setFare(15.0);

        Booking savedBooking = bookingRepository.save(booking);

        assertThat(savedBooking).isNotNull();
        assertThat(savedBooking.getId()).isNotNull();
    }

    @Test
    public void testGetBookingById() {
        Booking booking = new Booking();
        booking.setPickupTime(LocalTime.of(10, 0));
        booking.setPickupLocation("Location A");
        booking.setDropoffLocation("Location B");
        booking.setDropoffTime(LocalTime.of(10, 30));
        booking.setDistance(10.0);
        booking.setTime(30);
        booking.setCabType("Sedan");
        booking.setFare(15.0);

        Booking savedBooking = bookingRepository.save(booking);
        Optional<Booking> retrievedBooking = bookingRepository.findById(savedBooking.getId());

        assertThat(retrievedBooking).isPresent();
        assertThat(retrievedBooking.get().getPickupLocation()).isEqualTo("Location A");
    }

    @Test
    public void testFindAllBookings() {
        Booking booking1 = new Booking();
        booking1.setPickupTime(LocalTime.of(10, 0));
        booking1.setPickupLocation("Location A");
        booking1.setDropoffLocation("Location B");
        booking1.setDropoffTime(LocalTime.of(10, 30));
        booking1.setDistance(10.0);
        booking1.setTime(30);
        booking1.setCabType("Sedan");
        booking1.setFare(15.0);

        Booking booking2 = new Booking();
        booking2.setPickupTime(LocalTime.of(11, 0));
        booking2.setPickupLocation("Location C");
        booking2.setDropoffLocation("Location D");
        booking2.setDropoffTime(LocalTime.of(11, 30));
        booking2.setDistance(12.0);
        booking2.setTime(30);
        booking2.setCabType("SUV");
        booking2.setFare(20.0);

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        List<Booking> bookings = bookingRepository.findAll();
        
        assertThat(bookings).hasSize(2);
    }
}
