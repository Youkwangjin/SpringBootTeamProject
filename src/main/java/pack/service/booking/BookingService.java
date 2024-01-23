package pack.service.booking;

import org.springframework.stereotype.Service;
import pack.dto.admin.AdminDTO;
import pack.dto.booking.bookingDTO;

import java.util.ArrayList;

@Service
public interface BookingService {
    boolean bookingInsert(bookingDTO bookingdto);
    boolean contStatusUpdate(AdminDTO adminDTO);
    ArrayList<bookingDTO> bookingList(String user_id);
    boolean bookingDelete(bookingDTO bookingDto);
}
