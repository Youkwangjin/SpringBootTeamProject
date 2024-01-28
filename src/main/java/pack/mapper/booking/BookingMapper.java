
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

    @Insert("""
            INSERT INTO booking_board\s
            (user_id, user_name, booking_date_start, booking_date_end, cont_no, cont_size, booking_price, booking_status)\s
            VALUES (#{user_id}, #{user_name}, #{booking_date_start}, #{booking_date_end}, #{cont_no}, #{cont_size}, #{booking_price}, '예약중')
            """)
    int bookingInsert(BookingDTO bookingdto);

    @Select("""
            SELECT * FROM booking_board\s
            INNER JOIN user ON user.user_id = booking_board.user_id\s
            WHERE user.user_id = #{user_id}
            """)
    List<BookingDTO> bookingList(@Param("user_id") String user_id);


    @Select("SELECT * FROM booking_board WHERE booking_date_end < CURDATE() AND booking_status != '예약취소'")
    List<BookingDTO> findExpiredBookings();

    @Select("SELECT * FROM booking_board WHERE booking_id = #{booking_id}")
    BookingDTO getBookingById(@Param("booking_id") int bookingId);

    @Select("""
            SELECT * FROM booking_board\s
            WHERE user_id = #{user_id} AND booking_date_start > NOW()
            """)
    List<BookingDTO> findActiveBookingsByUserId(@Param("user_id") String userId);

    @Select("SELECT * FROM booking_board WHERE user_id = #{user_id}")
    List<BookingDTO> findBookingsByUserId(@Param("user_id") String userId);

    @Update("UPDATE booking_board SET booking_status=#{status} WHERE booking_id=#{bookingId}")
    int updateBookingStatus(@Param("bookingId") int bookingId, @Param("status") String status);

    @Update("UPDATE container SET cont_status='2' WHERE cont_no=#{cont_no}")
    int contStatusUpdate(AdminDTO adminDTO);

    @Delete("DELETE FROM booking_board WHERE booking_id=#{booking_id}")
    int bookingDelete(@Param("booking_id") int bookingId);
}