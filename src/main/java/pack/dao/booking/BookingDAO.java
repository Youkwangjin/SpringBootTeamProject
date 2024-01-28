
package pack.dao.booking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.admin.AdminDTO;
import pack.dto.booking.BookingDTO;
import pack.mapper.booking.BookingMapper;


@Repository
@AllArgsConstructor
public class BookingDAO {

    private final BookingMapper bookingMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookingDAO.class);

    @Transactional
    public boolean bookingInsert(BookingDTO bookingdto) {
        boolean bookingInsertData = false;
        try {
            int bookingInsertRe = bookingMapper.bookingInsert(bookingdto);
            if (bookingInsertRe > 0) {
                bookingInsertData = true;
            }
        } catch (Exception e) {
            logger.error("Error bookingInsert", e);
        }
        return bookingInsertData;
    }


    @Transactional
    public boolean contStatusUpdate(AdminDTO adminDTO) {
        boolean contStatusData = false;
        try {
            int contUpdateRe = bookingMapper.contStatusUpdate(adminDTO);
            if (contUpdateRe > 0)
                contStatusData = true;
        } catch (Exception e) {
            logger.error("예외 발생 ", e);
        }
        return contStatusData;
    }

    public ArrayList<BookingDTO> bookingList(String user_id) {
        ArrayList<BookingDTO> bList = null;
        try {
            bList = (ArrayList<BookingDTO>) bookingMapper.bookingList(user_id);
            if (bList != null) {
                logger.info("예약한 사용자 user_id: {}, list size: {}", user_id, bList.size());
                for (BookingDTO booking : bList) {
                    logger.info("Booking: {}", booking.toString());
                }
            } else {
                logger.info("사용자 찾을 수 없습니다. user_id: {}", user_id);
            }
        } catch (Exception e) {
            logger.error("예외 발생 user_id: {}", user_id, e);
        }
        return bList;
    }

    public boolean bookingDelete(int bookingId) {
        boolean bookingDeleteData = false;
        try {
            int bookingDeleteRe = bookingMapper.bookingDelete(bookingId);
            if (bookingDeleteRe > 0) bookingDeleteData = true;
        } catch (Exception e) {
            logger.error("Error bookingDelete", e);
        }
        return bookingDeleteData;
    }


    public BookingDTO getBookingById(int bookingId) {
        try {
            return bookingMapper.getBookingById(bookingId);
        } catch (Exception e) {
            logger.error("Error 발생 booking ID", e);
            return null;
        }
    }

    public List<BookingDTO> findActiveBookingsByUserId(String userId) {
        try {
            return bookingMapper.findActiveBookingsByUserId(userId);
        } catch (Exception e) {
            logger.error("창고를 예약한 사용자는 탈퇴 불가! user: {}", userId, e);
            return Collections.emptyList();
        }
    }

    public List<BookingDTO> findBookingsByUserId(String userId) {
        try {
            return bookingMapper.findBookingsByUserId(userId);
        } catch (Exception e) {
            logger.error("사용자 ID로 예약을 찾는 중 오류 발생", e);
            return Collections.emptyList();
        }
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        int result = bookingMapper.updateBookingStatus(bookingId, status);
        return result > 0;
    }

    public List<BookingDTO> findExpiredBookings() {
        return bookingMapper.findExpiredBookings();
    }
}