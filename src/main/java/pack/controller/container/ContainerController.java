package pack.controller.container;

import java.util.*;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ContainerController {

    private final ContainerService containerService;

    @GetMapping("/container/paid")
    public String cont_pay() {
        return "container/container-paid";
    }

    @GetMapping("/container/register")
    public String cont_regs() {
        return "container/container-register";
    }

    @GetMapping("/container/error")
    public String containerError() {
        return "container/container-error";
    }

    @GetMapping("/container")
    @ResponseBody
    public Map<String, Object> containerProcess() {
        return containerService.containerProcess();
    }

    @GetMapping("/container/list")
    public String containerMgr(Model model, HttpSession session) {
        String business_num = (String) session.getAttribute("business_num");
        model.addAttribute("datas", containerService.getContainerAllData(business_num));
        return "container/container-list";
    }

    @GetMapping("/container/reserve")
    public String containerReserve(Model model, HttpSession session) {
        String business_num = (String) session.getAttribute("business_num");
        model.addAttribute("datas", containerService.getDataReserve(business_num));
        return "container/container-reserve";
    }

    @GetMapping("/container/detail")
    public String containerDetail(@RequestParam("cont_no") String cont_no, HttpSession session, Model model) {
        String business_num = (String) session.getAttribute("business_num");
        if (business_num == null) {
            return "redirect:/";
        }
        ContainerDTO conDto = containerService.containerDetail(cont_no);
        model.addAttribute("conDto", conDto);
        return "container/container-detail";
    }

    @GetMapping("/container/update")
    public String containerUpdate(@RequestParam("cont_no") String cont_no, Model model) {
        ContainerDTO conDto = containerService.containerDetail(cont_no);
        model.addAttribute("conDto", conDto);
        return "container/container-update";
    }

    @GetMapping("/container/delete")
    public String containerDelete(@RequestParam("cont_no") String cont_no) {
        boolean isSuccess = containerService.deleteContainer(cont_no);
        if (isSuccess) {
            return "redirect:/container/list";
        } else {
            return "container/container-delete-error";
        }
    }

    @PostMapping("/container/insert")
    public String insertSubmit(@ModelAttribute ContainerDTO containerDTO,
                               @ModelAttribute UploadFileDTO uploadFileDTO,
                               BindingResult result,
                               HttpSession session) {
        String business_num = (String) session.getAttribute("business_num");

        if (business_num == null) {
            return "redirect:/";
        }
        containerDTO.setOwner_num(business_num);
        String uploadDirectory = "src/main/resources/static/upload/";

        String redirectUrl;
        try {
            redirectUrl = containerService.insertContainerWithFile(containerDTO, uploadFileDTO, result, uploadDirectory);
            if ("redirect:/container/list".equals(redirectUrl)) {
                return redirectUrl;
            } else {
                return "redirect:/container/list";
            }
        } catch (Exception e) {
            return "redirect:/container/error";
        }
    }

    @PostMapping("/container/update")
    public String containerUpdate(ContainerDTO containerDTO) {
        boolean isSuccess = containerService.updateContainer(containerDTO);
        if (isSuccess) {
            return "redirect:/container/list";
        } else {
            return "container/container-update-error";
        }
    }

    @PostMapping("/container/reserve")
    public ResponseEntity<?> reserveContainer(@RequestParam("cont_no") String cont_no) {
        return containerService.processReservation(cont_no);
    }
}