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
    public BookingResponse updateBookingStatus(int bookingId, String status) {
        BookingDTO booking = bookingDao.getBookingById(bookingId);
        if (booking == null) {
            return new BookingResponse(false, "예약을 찾을 수 없습니다.");
        }
        LocalDate today = LocalDate.now();
        LocalDate bookingStartDate = LocalDate.parse(booking.getBooking_date_start());
        // 예약 취소 조건 검사
        if ("예약취소".equals(status) && !bookingStartDate.isAfter(today)) {
            return new BookingResponse(false, "이미 시작된 예약은 취소할 수 없습니다.");
        }
        // 예약 상태 업데이트
        boolean updateResult = bookingDao.updateBookingStatus(bookingId, status);
        if (updateResult && "예약취소".equals(status)) {
            boolean deleteResult = bookingDao.bookingDelete(bookingId);
            if (!deleteResult) {
                return new BookingResponse(false, "예약 취소 후 삭제에 실패했습니다.");
            }
        }
        return new BookingResponse(updateResult, updateResult ? "예약 상태가 업데이트되었습니다." : "예약 상태 업데이트에 실패했습니다.");
    }

    @Override
    public void deleteAllBookingsForUser(String userId) {
        List<BookingDTO> bookings = bookingDao.findBookingsByUserId(userId);
        for (BookingDTO booking : bookings) {
            bookingDao.bookingDelete(booking.getBooking_id());
        }
    }

    @Override
    public void updateExpiredBookings() {
        List<BookingDTO> expiredBookings = bookingDao.findExpiredBookings();
        for (BookingDTO booking : expiredBookings) {
            updateBookingStatus(booking.getBooking_id(), "예약 가능");
        }
    }

    @Override
    public BookingDTO getBookingById(int bookingId) {
        return bookingDao.getBookingById(bookingId);
    }
}