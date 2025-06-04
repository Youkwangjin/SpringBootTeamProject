package com.acorn.api.service.notice.impl;

import com.acorn.api.dto.notice.NoticeListDTO;
import com.acorn.api.entity.notice.Notice;
import com.acorn.api.repository.notice.NoticeRepository;
import com.acorn.api.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeListDTO> getNoticeListData(NoticeListDTO listData) {
        listData.setTotalCount(noticeRepository.selectListCountByRequest(listData));
        List<Notice> noticeListData = noticeRepository.selectNoticeListData(listData);

        return noticeListData.stream()
                .map(noticeList -> {
                    final Integer rowNum = noticeList.getRowNum();
                    final Integer noticeId = noticeList.getNoticeId();
                    final String noticeTitle = noticeList.getNoticeTitle();
                    final String noticeWriter = noticeList.getAdmin().getAdminNm();
                    final Integer noticeHits = noticeList.getNoticeHits();
                    final LocalDateTime noticeCreated = noticeList.getNoticeCreated();

                    return NoticeListDTO.builder()
                            .rowNum(rowNum)
                            .noticeId(noticeId)
                            .noticeTitle(noticeTitle)
                            .noticeWriter(noticeWriter)
                            .noticeHits(noticeHits)
                            .noticeCreated(noticeCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }
}