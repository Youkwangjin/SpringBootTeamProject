package pack.controller.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.dao.container.ContainDAO;
import pack.dto.container.ContainMapDTO;


@Controller
@AllArgsConstructor
public class ContainController {

    private final ContainDAO containDao;

    @GetMapping("/container")
    @ResponseBody
    public Map<String, Object> sangpumProcess() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> data = null;

        for (ContainMapDTO c : containDao.getContainAll()) {
            data = new HashMap<String, String>();
            data.put("con_no", c.getCont_no());
            data.put("con_name", c.getCont_name());
            data.put("con_addr", c.getCont_addr());
            data.put("we", c.getCont_we());
            data.put("kyung", c.getCont_kyung());
            data.put("con_area", c.getCont_size());
            list.add(data);
        }
        System.out.println(list);

        Map<String, Object> sangList = new HashMap<>();
        sangList.put("datas", list);
        return sangList;
    }
}
