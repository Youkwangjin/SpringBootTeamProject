package com.acorn.api.controller.container;

import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.service.container.ContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContainerPageController {

    private final ContainerService containerService;

    @GetMapping("/container/list")
    public String containerPage(ContainerListDTO listData, Model model) {
        List<ContainerListDTO> containerListData = containerService.getContainerListData(listData);
        model.addAttribute("containerData", containerListData);
        model.addAttribute("request", listData);
        return "container/container-list";
    }

    @GetMapping("/container/register")
    public String registerPage(Model model) {
        return "container/container-register";
    }
}
