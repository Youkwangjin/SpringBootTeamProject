package com.acorn.api.service.notice.impl;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.notice.NoticeListDTO;
import com.acorn.api.dto.notice.NoticeSaveDTO;
import com.acorn.api.entity.notice.Notice;
import com.acorn.api.entity.notice.NoticeFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.notice.NoticeFileRepository;
import com.acorn.api.repository.notice.NoticeRepository;
import com.acorn.api.service.notice.NoticeService;
import com.acorn.api.utils.AdminSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final FileComponent fileComponent;

    @Value("${file.upload.path.notice}")
    private String uploadDir;

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

    @Override
    @Transactional
    public void noticeDataSave(NoticeSaveDTO saveData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer noticeId = noticeRepository.selectNoticeIdKey();
        final String noticeTitle = saveData.getNoticeTitle();
        final String noticeContent = saveData.getNoticeContents();
        final String noticeContentText = Jsoup.parse(noticeContent).text();
        final List<MultipartFile> noticeFiles = saveData.getNoticeFiles();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Notice saveNoticeData = Notice.builder()
                .noticeId(noticeId)
                .noticeTitle(noticeTitle)
                .noticeContents(noticeContent)
                .noticeContentText(noticeContentText)
                .noticeAdminId(currentAdminId)
                .build();

        noticeRepository.saveNoticeData(saveNoticeData);

        if (noticeFiles != null && !noticeFiles.isEmpty()) {
            for (MultipartFile multipartFile : noticeFiles) {
                final Integer noticeFileId = noticeFileRepository.selectNoticeFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", noticeId, noticeFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                NoticeFile saveNoticeFileData = NoticeFile.builder()
                        .noticeFileId(noticeFileId)
                        .noticeOriginalFileName(originalFileName)
                        .noticeStoredFileName(storedFileName)
                        .noticeFilePath(filePath)
                        .noticeFileExtNm(fileExtNm)
                        .noticeFileSize(fileSize)
                        .noticeId(noticeId)
                        .build();

                noticeFileRepository.saveNoticeFileData(saveNoticeFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }
}