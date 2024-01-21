package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.container.ContainerDTO;
import pack.dto.form.FormDTO;
import pack.service.admin.ContainerInfoListService;


import java.util.ArrayList;

@Controller
@AllArgsConstructor
public class ContainerInfoListController {

   private final ContainerInfoListService containerInfoListService;

   @GetMapping("/detail")
   public String containerDetail(@RequestParam("cont_no")String cont_no, Model model) {
      ContainerDTO containerDto = containerInfoListService.containerDetail(cont_no);
      model.addAttribute("containerDTO",containerDto);
      return "/admin/admin-cont-approve";
   }

   @GetMapping("/registered")
   public String registeredList(@RequestParam(name = "page", required = false, defaultValue = "1") int page,Model model) {
      int sPage = Math.max(page, 1);

      ArrayList<ContainerDTO> sList3 = (ArrayList<ContainerDTO>) containerInfoListService.getContainerList();
      ArrayList<ContainerDTO> result = containerInfoListService.getRegisteredListData(sList3, sPage);

      model.addAttribute("lists3", result);
      model.addAttribute("pageSu", containerInfoListService.getRegisteredPageSu());
      model.addAttribute("page", sPage);
      return "admin/admin-cont-registered";
   }

   @GetMapping("/deleteContainer")
   public String deleteContainer(@RequestParam("cont_no") String cont_no) {
      return containerInfoListService.deleteContainer(cont_no);
   }

   @PostMapping("/regSearch")
   public String regSearch(@RequestParam(name="page", required = false, defaultValue = "1")int page, FormDTO formDTO, Model model) {
      int sPage = Math.max(page, 1);

      ArrayList<ContainerDTO> sList3 = (ArrayList<ContainerDTO>) containerInfoListService.getRegSearch(formDTO);
      ArrayList<ContainerDTO> result = containerInfoListService.getRegisteredListData(sList3, sPage);

      model.addAttribute("lists3", result);
      model.addAttribute("pageSu", containerInfoListService.getRegisteredPageSu());
      model.addAttribute("page", sPage);

      return "admin/admin-cont-registered";
   }
}
