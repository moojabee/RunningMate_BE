package com.lswr.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunResultDto {
	private Long runId;
	private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double distance;
    private String runImg;
}
