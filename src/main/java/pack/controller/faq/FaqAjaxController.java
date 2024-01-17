package pack.controller.faq;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pack.dto.faq.FaqAjaxDTO;

@Controller
@AllArgsConstructor
public class FaqAjaxController {

    private final FaqAjaxDTO faqAjaxDTO;

    @GetMapping("detailFaq")
    @ResponseBody
    public FaqAjaxDTO getFqa(@RequestParam("detail") String detail) {
        faqAjaxDTO.setDetail(detail);
        return faqAjaxDTO;
    }
}





