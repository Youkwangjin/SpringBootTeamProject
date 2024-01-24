package pack.service.booking;

import org.springframework.stereotype.Service;
import pack.dto.admin.AdminDTO;
import pack.dto.booking.BookingDTO;
import pack.response.BookingResponse;

import java.util.ArrayList;
import java.util.List;


@Service
public interface BookingService {
    boolean bookingInsert(BookingDTO bookingdto);
    boolean contStatusUpdate(AdminDTO adminDTO);
    ArrayList<BookingDTO> bookingList(String user_id);
    BookingResponse bookingDelete(int bookingId);
    List<BookingDTO> getActiveBookingsForUser(String userId);
    List<BookingDTO> findBookingsByUserId(String userId);
    void deleteAllBookingsForUser(String userId);
}