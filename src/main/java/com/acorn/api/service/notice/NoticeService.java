package com.acorn.api.service.notice;

import com.acorn.api.dto.notice.NoticeListDTO;
import com.acorn.api.dto.notice.NoticeSaveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {

    List<NoticeListDTO> getNoticeListData(NoticeListDTO listData);

    void noticeDataSave(NoticeSaveDTO saveData);
}