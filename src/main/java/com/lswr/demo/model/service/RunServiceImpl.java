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
	public RunResultDto addRunRecord(RunResultDto resultDto) {
        int insertResult = runDao.addRunRecord(resultDto);
        System.out.println(insertResult);
        if (insertResult > 0) {
            // 방금 삽입한 데이터의 ID를 가져와 데이터를 조회
            return runDao.getRunRecord(resultDto.getRunId());
        }
        return null;
    }

}
