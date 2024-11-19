package com.lswr.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Run {
	private Long recordId;
	private Long userId;
    private String runImg;
    private String dist;
    private String pace;
    private LocalDateTime startTime;
}
