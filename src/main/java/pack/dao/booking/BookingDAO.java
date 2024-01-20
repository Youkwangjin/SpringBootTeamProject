
package pack.dao.booking;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.admin.AdminDTO;
import pack.dto.booking.bookingDTO;
import pack.repository.booking.BookingMapper;


@Repository
@AllArgsConstructor
public class BookingDAO {

	private final BookingMapper bookingMapper;
	private static final Logger logger = LoggerFactory.getLogger(BookingDAO.class);

	@Transactional
	public boolean bookingInsert(bookingDTO bookingdto) {
		boolean b = false;
		try {
			int re = bookingMapper.bookingInsert(bookingdto);
			if (re > 0) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("Error during bookingInsert", e);
		}
		return b;
	}

	
	@Transactional
	public boolean contStatusUpdate(AdminDTO adminDTO) {
		boolean a = false;
		System.out.println("상태 업데이트 메서드 시작");
		try {
			int re = bookingMapper.contStatusUpdate(adminDTO);
			System.out.println("SQL 실행 결과: " + re);
			if (re > 0)
				a = true;
		} catch (Exception e) {
			// 예외 발생 시 처리
			System.out.println("예외 발생: " + e.getMessage());
        }
		return a;
	}


	public ArrayList<bookingDTO> bookingList(String user_id) {
		ArrayList<bookingDTO> bList = null;
		try {
			bList = (ArrayList<bookingDTO>) bookingMapper.bookingList(user_id);
			if(bList != null) {
				logger.info("bookingList - user_id: {}, list size: {}", user_id, bList.size());
				for(bookingDTO booking : bList) {
					logger.info("Booking: {}", booking.toString());
				}
			} else {
				logger.info("No bookings found for user_id: {}", user_id);
			}
		} catch (Exception e) {
			logger.error("Error during fetching booking list for user_id: {}", user_id, e);
		}
		return bList;
	}

	public boolean bookingDelete(bookingDTO bookingdto) {
		boolean b = false;
		try {
			int re = bookingMapper.bookingDelete(bookingdto.getBooking_id());
			if(re > 0 ) b = true;
		} catch (Exception e) {
			logger.error("Error bookingDelete", e);
		}
		return b;
	}
}
