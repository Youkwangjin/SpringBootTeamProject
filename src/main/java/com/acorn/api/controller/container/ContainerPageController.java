package com.acorn.api.controller.container;

import com.acorn.api.dto.container.response.ContainerDetailResDTO;
import com.acorn.api.dto.container.request.ContainerListReqDTO;
import com.acorn.api.dto.container.response.ContainerListResDTO;
import com.acorn.api.dto.owner.response.OwnerResDTO;
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
    public String containerPage(ContainerListReqDTO listData, Model model) {
        List<ContainerListResDTO> containerListData = containerService.getContainerListData(listData);
        model.addAttribute("containerData", containerListData);
        model.addAttribute("request", listData);
        return "container/container-list";
    }

    @GetMapping("/container/detail/{containerId}")
    public String containerDetailPage(@PathVariable("containerId") Integer containerId, Model model) {
        ContainerDetailResDTO containerDetailData = containerService.getContainerData(containerId);
        model.addAttribute("containerDetailData", containerDetailData);
        return "container/container-detail";
    }

    @GetMapping("/container/register")
    public String containerRegisterPage(Model model) {
        OwnerResDTO ownerData = ownerService.getOwnerData();
        model.addAttribute("ownerId", ownerData.getOwnerId());
        model.addAttribute("ownerName", ownerData.getOwnerNm());
        return "container/container-register";
    }

    @GetMapping("/container/update/{containerId}")
    public String containerUpdatePage(@PathVariable("containerId") Integer containerId, Model model) {
        ContainerDetailResDTO containerDetailData = containerService.getContainerData(containerId);
        model.addAttribute("containerDetailData", containerDetailData);
        return "container/container-update";
    }
}