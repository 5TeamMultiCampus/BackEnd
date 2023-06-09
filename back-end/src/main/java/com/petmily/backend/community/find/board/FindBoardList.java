package com.petmily.backend.community.find.board;

import java.time.LocalDateTime;

public interface FindBoardList {
	Long getBoardNum();
	String getBoardId();
	String getBoardSubject();
	Integer getBoardCount();
	LocalDateTime getBoardDate();
	String getImgThumbnail();
	String getMemberNickName();
}
