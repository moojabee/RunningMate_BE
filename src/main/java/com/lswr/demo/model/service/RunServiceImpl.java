package com.lswr.demo.model.service;

import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.RunDao;
import com.lswr.demo.model.dto.RunResultDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RunServiceImpl implements RunService {
	
	final private RunDao runDao;
	
	@Override
	public boolean addRunRecord(RunResultDto resultDto) {
		int res = runDao.addRunRecord(resultDto);
		return res > 0;
	}

}
