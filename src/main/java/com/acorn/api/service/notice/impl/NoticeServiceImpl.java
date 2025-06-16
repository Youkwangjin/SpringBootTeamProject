package com.acorn.api.service.notice.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.common.CommonListReqDTO;
import com.acorn.api.dto.notice.request.NoticeDeleteReqDTO;
import com.acorn.api.dto.notice.request.NoticeSaveReqDTO;
import com.acorn.api.dto.notice.request.NoticeUpdateReqDTO;
import com.acorn.api.dto.notice.response.NoticeDetailResDTO;
import com.acorn.api.dto.notice.response.NoticeFileResDTO;
import com.acorn.api.dto.notice.response.NoticeListResDTO;
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
import java.util.ArrayList;
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
    public List<NoticeListResDTO> getNoticeListData(CommonListReqDTO listData) {
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

                    return NoticeListResDTO.builder()
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
    public NoticeDetailResDTO getNoticeDetailData(Integer noticeId) {
        Notice detailData = noticeRepository.selectNoticeDetailData(noticeId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.NOTICE_NOT_FOUND);
        }

        final String noticeTitle = detailData.getNoticeTitle();
        final String noticeWriter = detailData.getAdmin().getAdminNm();
        final String noticeContents = detailData.getNoticeContents();
        final String noticeContentsText = detailData.getNoticeContentsText();
        final Integer noticeHits = detailData.getNoticeHits();
        final LocalDateTime noticeCreated = detailData.getNoticeCreated();

        noticeRepository.updateNoticeHits(noticeId);

        List<NoticeFile> noticeFileEntities = detailData.getNoticeFilesList();
        final List<NoticeFileResDTO> noticeFileData = noticeFileEntities.stream()
                .map(noticeFile -> {
                    final Integer noticeFileId = noticeFile.getNoticeFileId();
                    final String noticeOriginalFileName = noticeFile.getNoticeOriginalFileName();
                    final String noticeStoredFileName = noticeFile.getNoticeStoredFileName();
                    final String noticeFilePath = noticeFile.getNoticeFilePath();
                    final String noticeFileExtNm = noticeFile.getNoticeFileExtNm();
                    final String noticeFileSize = noticeFile.getNoticeFileSize();

                    return NoticeFileResDTO.builder()
                            .noticeFileId(noticeFileId)
                            .noticeOriginalFileName(noticeOriginalFileName)
                            .noticeStoredFileName(noticeStoredFileName)
                            .noticeFilePath(noticeFilePath)
                            .noticeFileExtNm(noticeFileExtNm)
                            .noticeFileSize(noticeFileSize)
                            .build();
                })
                .collect(Collectors.toList());

        return NoticeDetailResDTO.builder()
                .noticeId(noticeId)
                .noticeTitle(noticeTitle)
                .noticeWriter(noticeWriter)
                .noticeContents(noticeContents)
                .noticeContentsText(noticeContentsText)
                .noticeHits(noticeHits + 1)
                .noticeCreated(noticeCreated)
                .noticeFiles(noticeFileData)
                .build();
    }

    @Override
    @Transactional
    public void noticeDataSave(NoticeSaveReqDTO saveData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer noticeId = noticeRepository.selectNoticeIdKey();
        final String noticeTitle = saveData.getNoticeTitle();
        final String noticeContents = saveData.getNoticeContents();
        final String noticeContentsText = Jsoup.parse(noticeContents).text();
        final List<MultipartFile> noticeFiles = saveData.getNoticeFiles();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Notice saveNoticeData = Notice.builder()
                .noticeId(noticeId)
                .noticeTitle(noticeTitle)
                .noticeContents(noticeContents)
                .noticeContentsText(noticeContentsText)
                .noticeAdminId(currentAdminId)
                .build();

        noticeRepository.saveNotice(saveNoticeData);

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

                noticeFileRepository.saveNoticeFile(saveNoticeFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    @Transactional
    public void noticeDataUpdate(NoticeUpdateReqDTO updateData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer noticeId = updateData.getNoticeId();
        final String noticeTitle = updateData.getNoticeTitle();
        final String noticeContents = updateData.getNoticeContents();
        final String noticeContentsText = Jsoup.parse(noticeContents).text();
        final List<MultipartFile> noticeFiles = updateData.getNoticeFiles();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Notice detailData = noticeRepository.selectNoticeDetailData(noticeId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.NOTICE_NOT_FOUND);
        }

        Notice updateNoticeData = Notice.builder()
                .noticeId(noticeId)
                .noticeTitle(noticeTitle)
                .noticeContents(noticeContents)
                .noticeContentsText(noticeContentsText)
                .build();

        noticeRepository.updateNotice(updateNoticeData);

        List<Integer> noticeFileIds = updateData.getNoticeFileIds();
        if (noticeFileIds == null) {
            noticeFileIds = new ArrayList<>();
        }

        List<NoticeFile> existingFiles = noticeFileRepository.selectFilesByNoticeId(noticeId);
        List<Integer> deleteFileIds = new ArrayList<>();
        for (NoticeFile noticeFile : existingFiles) {
            if (!noticeFileIds.contains(noticeFile.getNoticeFileId())) {
                deleteFileIds.add(noticeFile.getNoticeFileId());
            }
        }

        for (Integer noticeFileId : deleteFileIds) {
            NoticeFile noticeFile = noticeFileRepository.selectFilesByNoticeFileId(noticeFileId);
            if (noticeFile == null) {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }
            final String filePath = noticeFile.getNoticeFilePath();
            final String storedFileName = noticeFile.getNoticeStoredFileName();

            fileComponent.delete(filePath, storedFileName);
            noticeFileRepository.deleteNoticeFile(noticeFileId);
        }

        if (noticeFiles != null && !noticeFiles.isEmpty()) {
            for (MultipartFile multipartFile : noticeFiles) {
                final Integer noticeFileId = noticeFileRepository.selectNoticeFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", noticeId, noticeFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                NoticeFile newFileData = NoticeFile.builder()
                        .noticeFileId(noticeFileId)
                        .noticeOriginalFileName(originalFileName)
                        .noticeStoredFileName(storedFileName)
                        .noticeFilePath(filePath)
                        .noticeFileExtNm(fileExtNm)
                        .noticeFileSize(fileSize)
                        .noticeId(noticeId)
                        .build();

                noticeFileRepository.saveNoticeFile(newFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    @Transactional
    public void noticeDataDelete(NoticeDeleteReqDTO deleteData) {
        final Integer currentAdminId = AdminSecurityUtil.getCurrentAdminId();
        final Integer noticeId = deleteData.getNoticeId();

        if (currentAdminId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Notice detailData = noticeRepository.selectNoticeDetailData(noticeId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.NOTICE_NOT_FOUND);
        }

        List<NoticeFile> existingFiles = noticeFileRepository.selectFilesByNoticeId(noticeId);
        if (existingFiles != null && !existingFiles.isEmpty()) {
            for (NoticeFile noticeFile : existingFiles) {
                final Integer noticeFileId = noticeFile.getNoticeFileId();
                final String filePath = noticeFile.getNoticeFilePath();
                final String storedFileName = noticeFile.getNoticeStoredFileName();

                fileComponent.delete(filePath, storedFileName);
                noticeFileRepository.deleteNoticeFile(noticeFileId);
            }
        }

        Notice deleteNoticeData = Notice.builder()
                .noticeId(noticeId)
                .build();

        noticeRepository.deleteNotice(deleteNoticeData);
    }
}