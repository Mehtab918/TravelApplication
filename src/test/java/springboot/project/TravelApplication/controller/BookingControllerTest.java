package springboot.project.TravelApplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import springboot.project.TravelApplication.controller.BookingController;
import springboot.project.TravelApplication.entity.Booking;
import springboot.project.TravelApplication.service.BookingService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setPickupTime(LocalTime.of(10, 0));
        booking.setPickupLocation("Location A");
        booking.setDropoffLocation("Location B");
        booking.setDropoffTime(LocalTime.of(10, 30));
        booking.setDistance(10.0);
        booking.setTime(30);
        booking.setCabType("standard");
        booking.setFare(15.0);
    }

    @Test
    public void testGetAllBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(Arrays.asList(booking));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].pickupLocation").value("Location A"));
    }

    @Test
    public void testGetBookingById() throws Exception {
        when(bookingService.getBookingById(anyLong())).thenReturn(booking);

        mockMvc.perform(get("/bookings/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupLocation").value("Location A"));
    }

    
    @Test
    public void testSaveBooking() throws Exception {
        when(bookingService.save(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/saveBooking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupLocation").value("Location A"));
    }

   
    

    
}
