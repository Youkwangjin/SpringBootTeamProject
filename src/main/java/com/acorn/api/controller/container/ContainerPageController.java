package com.acorn.api.controller.container;

import com.acorn.api.dto.container.ContainerDetailDTO;
import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.dto.owner.OwnerResponseDTO;
import com.acorn.api.service.container.ContainerService;
import com.acorn.api.service.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContainerPageController {

    private final ContainerService containerService;
    private final OwnerService ownerService;

    @GetMapping("/container/list")
    public String containerPage(ContainerListDTO listData, Model model) {
        List<ContainerListDTO> containerListData = containerService.getContainerListData(listData);
        model.addAttribute("containerData", containerListData);
        model.addAttribute("request", listData);
        return "container/container-list";
    }

    @GetMapping("/container/register")
    public String registerPage(Model model) {
        OwnerResponseDTO ownerData = ownerService.getOwnerData();
        model.addAttribute("ownerId", ownerData.getOwnerId());
        model.addAttribute("ownerName", ownerData.getOwnerNm());
        return "container/container-register";
    }

    @GetMapping("/container/detail/{containerId}")
    public String detailPage(@PathVariable("containerId") Integer containerId, Model model) {
        ContainerDetailDTO containerDetailData = containerService.getContainerData(containerId);
        model.addAttribute("containerDetailData", containerDetailData);
        return "container/container-detail";
    }

    @GetMapping("/container/update/{containerId}")
    public String updatePage(@PathVariable("containerId") Integer containerId, Model model) {
        ContainerDetailDTO containerDetailData = containerService.getContainerData(containerId);
        model.addAttribute("containerDetailData", containerDetailData);
        return "container/container-update";
    }
}