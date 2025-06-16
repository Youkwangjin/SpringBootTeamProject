package com.acorn.api.service.notice;

import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.notice.request.NoticeDeleteReqDTO;
import com.acorn.api.dto.notice.request.NoticeSaveReqDTO;
import com.acorn.api.dto.notice.request.NoticeUpdateReqDTO;
import com.acorn.api.dto.notice.response.NoticeDetailResDTO;
import com.acorn.api.dto.notice.response.NoticeListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {

    List<NoticeListResDTO> getNoticeListData(CommonListReqDTO listData);

    NoticeDetailResDTO getNoticeDetailData(Integer noticeId);

    void noticeDataSave(NoticeSaveReqDTO saveData);

    void noticeDataUpdate(NoticeUpdateReqDTO updateData);

    void noticeDataDelete(NoticeDeleteReqDTO deleteData);
}