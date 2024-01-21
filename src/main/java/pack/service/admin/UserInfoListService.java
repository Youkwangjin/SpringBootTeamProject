package pack.service.admin;


import org.springframework.stereotype.Service;
import pack.dto.form.FormDTO;
import pack.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;
@Service
public interface UserInfoListService {
    List<UserDTO> getUserList();
    List<UserDTO> getUserSearch(FormDTO formDTO);
    int totalUser();
    boolean userDelete(String user_id);
    int userCount();
    ArrayList<UserDTO> getUserListData(ArrayList<UserDTO> list, int page);
    int getUserPageSu();
}
