package com.acorn.api.service.notice;

import com.acorn.api.dto.notice.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {

    List<NoticeListDTO> getNoticeListData(NoticeListDTO listData);

    NoticeDetailDTO getNoticeDetailData(Integer noticeId);

    void noticeDataSave(NoticeSaveDTO saveData);

    void noticeDataUpdate(NoticeUpdateDTO updateData);

    void noticeDataDelete(NoticeDeleteDTO deleteData);
}