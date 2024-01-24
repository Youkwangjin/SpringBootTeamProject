package pack.controller.container;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import pack.dto.container.ContainerDTO;
import pack.dto.upload.UploadFileDTO;
import pack.service.container.ContainerService;

@Controller
@RequestMapping(value = "/owner")
@AllArgsConstructor
public class ContainerController {

    private final ContainerService containerService;

    @GetMapping("/paid")
    public String cont_pay() {
        return "container/container-paid";
    }

    @GetMapping("/register")
    public String cont_regs() {
        return "container/container-register";
    }

    @GetMapping("/container")
    @ResponseBody
    public Map<String, Object> containerProcess() {
        return containerService.containerProcess();
    }

    @GetMapping("/list")
    public String containerMgr(Model model, HttpSession session) {
        String business_num = (String) session.getAttribute("business_num");
        model.addAttribute("datas", containerService.getContainerAllData(business_num));
        return "container/container-list";
    }

    @GetMapping("/reserve")
    public String containerReserve(Model model, HttpSession session) {
        String business_num = (String) session.getAttribute("business_num");
        model.addAttribute("datas", containerService.getDataReserve(business_num));
        return "container/container-reserve";
    }

    @GetMapping("/detail")
    public String containerDetail(@RequestParam("cont_no") String cont_no, HttpSession session, Model model) {
        String business_num = (String) session.getAttribute("business_num");
        if (business_num == null) {
            return "redirect:/";
        }
        ContainerDTO conDto = containerService.containerDetail(cont_no);
        model.addAttribute("conDto", conDto);
        return "container/container-detail";
    }

    @GetMapping("/goUpdate")
    public String containerUpdate(@RequestParam("cont_no") String cont_no, Model model) {
        ContainerDTO conDto = containerService.containerDetail(cont_no);
        model.addAttribute("conDto", conDto);
        return "container/container-update";
    }

    @GetMapping("/delete")
    public String containerDelete(@RequestParam("cont_no") String cont_no) {
        boolean isSuccess = containerService.deleteContainer(cont_no);
        if (isSuccess) {
            return "redirect:/owner/list";
        } else {
            return "container/container-error";
        }
    }

    @PostMapping("/insert")
    public String insertSubmit(@ModelAttribute ContainerDTO containerDTO, @ModelAttribute UploadFileDTO uploadFileDTO,
                               BindingResult result, HttpSession session) {
        String business_num = (String) session.getAttribute("business_num");

        if (business_num == null) {
            return "redirect:/";
        }
        containerDTO.setOwner_num(business_num);
        String uploadDirectory = "src/main/resources/static/upload/";
        return containerService.insertContainerWithFile(containerDTO, uploadFileDTO, result, uploadDirectory);
    }

    @PostMapping("/update")
    public String containerUpdate(ContainerDTO containerDTO) {
        boolean isSuccess = containerService.updateContainer(containerDTO);
        if (isSuccess) {
            return "redirect:/owner/list";
        } else {
            return "container/container-error";
        }
    }

    @PostMapping("/reserveContainer")
    public ResponseEntity<?> reserveContainer(@RequestParam("cont_no") String cont_no) {
        return containerService.processReservation(cont_no);
    }
}