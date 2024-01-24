package pack.service.booking.bookingimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.booking.BookingDAO;
import pack.dto.admin.AdminDTO;
import pack.dto.booking.BookingDTO;
import pack.response.BookingResponse;
import pack.service.booking.BookingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDao;

    @Override
    public boolean bookingInsert(BookingDTO bookingdto) {
        return bookingDao.bookingInsert(bookingdto);
    }

    @Override
    public boolean contStatusUpdate(AdminDTO adminDTO) {
        return bookingDao.contStatusUpdate(adminDTO);
    }

    @Override
    public ArrayList<BookingDTO> bookingList(String user_id) {
        return bookingDao.bookingList(user_id);
    }

    @Override
    public BookingResponse bookingDelete(int bookingId) {
        BookingDTO existingBooking = bookingDao.getBookingById(bookingId);
        if (existingBooking == null) {
            return new BookingResponse(false, "예약을 찾을 수 없습니다.");
        }
        LocalDate today = LocalDate.now();
        LocalDate bookingStartDate = LocalDate.parse(existingBooking.getBooking_date_start());
        if (!bookingStartDate.isAfter(today)) {
            return new BookingResponse(false, "예약 시작 날짜가 지났으므로 취소할 수 없습니다.");
        }
        boolean result = bookingDao.bookingDelete(bookingId);
        return new BookingResponse(result, result ? "예약이 성공적으로 취소되었습니다." : "예약 취소에 실패했습니다.");
    }

    @Override
    public List<BookingDTO> getActiveBookingsForUser(String userId) {
        return bookingDao.findActiveBookingsByUserId(userId);
    }

    @Override
    public List<BookingDTO> findBookingsByUserId(String userId) {
        try {
            return bookingDao.findBookingsByUserId(userId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAllBookingsForUser(String userId) {
        List<BookingDTO> bookings = bookingDao.findBookingsByUserId(userId);
        for (BookingDTO booking : bookings) {
            bookingDao.bookingDelete(booking.getBooking_id());
        }
    }
}