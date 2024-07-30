package springboot.project.TravelApplication.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.project.TravelApplication.entity.Booking;
import springboot.project.TravelApplication.repository.BookingRepository;
import springboot.project.TravelApplication.service.BookingService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBookings() {
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

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> bookings = bookingService.getAllBookings();

        assertThat(bookings).hasSize(2);
        verify(bookingRepository, times(1)).findAll();
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

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Booking retrievedBooking = bookingService.getBookingById(1L);

        assertThat(retrievedBooking).isNotNull();
        assertThat(retrievedBooking.getPickupLocation()).isEqualTo("Location A");
        verify(bookingRepository, times(1)).findById(1L);
    }

    
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

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.save(booking);

        assertThat(savedBooking).isNotNull();
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    public void testCalculateFare() {
        double distance = 10.0;
        int time = 30;
        String cabType = "standard";

        double fare = bookingService.calculateFare(distance, time, cabType);

        double expectedFare = 50.0 + (distance * 10.0) + (time * 0.5) + 50.0; // baseFare + distanceFare + timeFare + cabTypeFare

        assertThat(fare).isEqualTo(expectedFare);
    }
}
