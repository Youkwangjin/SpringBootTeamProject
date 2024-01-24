
package pack.mapper.booking;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pack.dto.admin.AdminDTO;
import pack.dto.booking.BookingDTO;


@Mapper
public interface BookingMapper {
	
	@Insert("INSERT INTO booking_board (user_id, user_name, booking_date_start, booking_date_end, cont_no, cont_size, booking_price) VALUES (#{user_id}, #{user_name}, #{booking_date_start}, #{booking_date_end}, #{cont_no}, #{cont_size}, #{booking_price})")
	int bookingInsert(BookingDTO bookingdto);

	@Select("select * from booking_board inner join user on user.user_id = booking_board.user_id where user.user_id = #{user_id}")
	List<BookingDTO> bookingList(@Param("user_id") String user_id);

	@Delete("delete from booking_board where booking_id=#{booking_id}")
	int bookingDelete(@Param("booking_id") int bookingId);
	
	@Update("update container set cont_status='2' where cont_no=#{cont_no}")
	int contStatusUpdate(AdminDTO adminDTO);

	@Select("SELECT * FROM booking_board WHERE booking_id = #{booking_id}")
	BookingDTO getBookingById(@Param("booking_id") int bookingId);

	@Select("SELECT * FROM booking_board WHERE user_id = #{user_id} AND booking_date_start > NOW()")
	List<BookingDTO> findActiveBookingsByUserId(@Param("user_id") String userId);
}
