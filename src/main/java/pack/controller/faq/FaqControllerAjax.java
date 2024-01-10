package pack.controller.faq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaqControllerAjax {
	@Autowired
	private ModelAjax modelAjax;
	
	@GetMapping("detailfaq")
	@ResponseBody
	public ModelAjax getFqa(@RequestParam("detail")String detail) {
		modelAjax.setDetail(detail);
		return modelAjax;
	}
}





