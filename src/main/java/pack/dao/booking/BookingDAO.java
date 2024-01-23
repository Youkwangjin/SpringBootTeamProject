
package pack.dao.booking;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.admin.AdminDTO;
import pack.dto.booking.bookingDTO;
import pack.mapper.booking.BookingMapper;


@Repository
@AllArgsConstructor
public class BookingDAO {

	private final BookingMapper bookingMapper;
	private static final Logger logger = LoggerFactory.getLogger(BookingDAO.class);

	@Transactional
	public boolean bookingInsert(bookingDTO bookingdto) {
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

	public ArrayList<bookingDTO> bookingList(String user_id) {
		ArrayList<bookingDTO> bList = null;
		try {
			bList = (ArrayList<bookingDTO>) bookingMapper.bookingList(user_id);
			if(bList != null) {
				logger.info("예약한 사용자 user_id: {}, list size: {}", user_id, bList.size());
				for(bookingDTO booking : bList) {
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

	public boolean bookingDelete(bookingDTO bookingdto) {
		boolean bookingDeleteData = false;
		try {
			int bookingDeleteRe = bookingMapper.bookingDelete(bookingdto.getBooking_id());
			if(bookingDeleteRe > 0 ) bookingDeleteData = true;
		} catch (Exception e) {
			logger.error("Error bookingDelete", e);
		}
		return bookingDeleteData;
	}
}
