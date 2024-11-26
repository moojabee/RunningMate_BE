package com.lswr.demo.model.dao;

import com.lswr.demo.model.dto.RunResultDto;

public interface RunDao {
	int addRunRecord(RunResultDto runResultDto);
	
	RunResultDto getRunRecord(Long runId);
}
