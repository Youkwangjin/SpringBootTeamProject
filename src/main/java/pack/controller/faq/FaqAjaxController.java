package pack.controller.faq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pack.dto.faq.FaqAjaxDTO;

@Controller
@RequiredArgsConstructor
public class FaqAjaxController {

    private final FaqAjaxDTO faqAjaxDTO;

    @GetMapping("/detail/faq")
    @ResponseBody
    public FaqAjaxDTO getDataFqa(@RequestParam("detail") String detail) {
        faqAjaxDTO.setDetail(detail);
        return faqAjaxDTO;
    }
}





