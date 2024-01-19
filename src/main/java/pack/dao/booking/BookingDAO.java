
package pack.dao.booking;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.admin.AdminDTO;
import pack.dto.booking.bookingDTO;
import pack.repository.booking.BookingMapper;


@Repository
@AllArgsConstructor
public class BookingDAO {

	private final BookingMapper bookingMapper;

	@Transactional
	public boolean bookingInsert(bookingDTO bookingdto) {
		boolean b = false;
		try {
			int re = bookingMapper.bookingInsert(bookingdto);
			if (re > 0) {
				b = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	

	public ArrayList<bookingDTO> bookingListAll(String user_id){
		ArrayList<bookingDTO> bList = (ArrayList<bookingDTO>) bookingMapper.bookingList(user_id);
		System.out.println("user_id : " + user_id);
		return bList;
	}

	public boolean bookingDelete(bookingDTO bookingdto) {
		boolean b = false;
		int re = bookingMapper.bookingDelete(bookingdto);
		if(re >= 0 ) b = true;
		return b;
	}
}
