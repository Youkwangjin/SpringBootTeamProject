package com.acorn.api.entity.contact;

import com.acorn.api.entity.admin.Admin;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    private Integer rowNum;

    private Integer contactId;

    private Integer contactUserId;

    private Integer contactOwnerId;

    private Integer contactAdminId;

    private String contactTitle;

    private String contactContents;

    private String contactContentsText;

    private Integer contactStatus;

    private Integer contactWriterType;

    private String contactAdminContents;

    private String contactAnswerYn;

    private LocalDateTime contactCreated;

    private LocalDateTime contactUpdated;

    private User user;

    private Owner owner;

    private Admin admin;

    private List<ContactFile> contactFilesList;
}