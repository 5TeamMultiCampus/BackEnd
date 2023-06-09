package com.petmily.backend.community.missing.board;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="missingboard")
public class MissingBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNum;

    @Column
    private Long memberNum;

    @Column
    private String boardId;

    @Column
    private String boardSubject;

    @Lob
	@Column(name = "boardContent", columnDefinition = "LONGTEXT")
    private String boardContent;

    @Column
    private Integer boardCount;

    @Column
    private LocalDateTime boardDate;
    
    @Column
    private String boardName;
    
    @Column
    private String boardSpecies;
    
    @Column
    private String boardLocation;
    
    @Column
    private int boardAge;
    
    @Column
    private String boardGender;
    
    @Column
    private Boolean boardStatus;

    @Column
    private String imgThumbnail;
}