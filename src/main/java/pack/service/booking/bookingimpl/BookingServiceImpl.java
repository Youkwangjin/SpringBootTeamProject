package pack.service.booking.bookingimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.booking.BookingDAO;
import pack.dto.admin.AdminDTO;
import pack.dto.booking.bookingDTO;
import pack.service.booking.BookingService;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDao;

    @Override
    public boolean bookingInsert(bookingDTO bookingdto) {
        return bookingDao.bookingInsert(bookingdto);
    }

    @Override
    public boolean contStatusUpdate(AdminDTO adminDTO) {
        return bookingDao.contStatusUpdate(adminDTO);
    }

    @Override
    public ArrayList<bookingDTO> bookingList(String user_id) {
        return bookingDao.bookingList(user_id);
    }

    @Override
    public boolean bookingDelete(bookingDTO bookingDto) {
        return bookingDao.bookingDelete(bookingDto);
    }
}

