package com.acorn.api.controller.notice;

import com.acorn.api.dto.notice.NoticeDetailDTO;
import com.acorn.api.dto.notice.NoticeListDTO;
import com.acorn.api.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticePageController {

    private final NoticeService noticeService;

    @GetMapping("/notice/list")
    public String noticeListPage(NoticeListDTO listData, Model model) {
        List<NoticeListDTO> noticeListData = noticeService.getNoticeListData(listData);
        model.addAttribute("noticeListData", noticeListData);
        model.addAttribute("request", listData);
        return "notice/notice-list";
    }

    @GetMapping("/admin/notice/write")
    public String noticeWritePage() {
        return "notice/notice-write";
    }

    @GetMapping("/notice/detail/{noticeId}")
    public String noticeDetailPage(@PathVariable("noticeId") Integer noticeId, Model model) {
        NoticeDetailDTO detailData = noticeService.getNoticeDetailData(noticeId);
        model.addAttribute("noticeDetailData", detailData);
        return "notice/notice-detail";
    }
}