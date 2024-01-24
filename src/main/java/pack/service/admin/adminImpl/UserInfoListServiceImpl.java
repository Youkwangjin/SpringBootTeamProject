package pack.service.admin.adminImpl;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.dao.admin.UserInfoListDAO;
import pack.dto.booking.BookingDTO;
import pack.dto.form.FormDTO;
import pack.dto.user.UserDTO;
import pack.response.BookingResponse;
import pack.service.admin.UserInfoListService;
import pack.service.booking.BookingService;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class UserInfoListServiceImpl implements UserInfoListService {
    private final Logger logger = LoggerFactory.getLogger(UserInfoListServiceImpl.class);
    private final UserInfoListDAO userInfoListDao;
    private final BookingService bookingService;
    private final int plist = 10;

    @Override
    public List<UserDTO> getUserList() {
        return userInfoListDao.getUserList();
    }

    @Override
    public List<UserDTO> getUserSearch(FormDTO formDTO) {
        return userInfoListDao.getUserSearch(formDTO);
    }

    @Override
    public int totalUser() {
        return userInfoListDao.totalUser();
    }

    @Override
    public boolean userDelete(String user_id) {
        try {
            bookingService.deleteAllBookingsForUser(user_id);
            return userInfoListDao.userDelete(user_id);
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public int userCount() {
        return userInfoListDao.userCount();
    }

    @Override
    public ArrayList<UserDTO> getUserListData(ArrayList<UserDTO> list, int page) {
        ArrayList<UserDTO> result = new ArrayList<>();
        int start = (page - 1) * plist;
        int end = Math.min(start + plist, list.size());
        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    @Override
    public int getUserPageSu() {
        int tot = totalUser();
        int pageSu = tot / plist;
        if (tot % plist > 0) pageSu += 1;
        return pageSu;
    }
}
