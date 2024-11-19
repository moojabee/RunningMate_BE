package com.lswr.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lswr.demo.model.dao.MyPageDao;
import com.lswr.demo.model.dto.Board;
import com.lswr.demo.model.dto.MyPage;
import com.lswr.demo.model.dto.Run;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyPageServiceImpl implements MyPageService{
	
	@Autowired
	private MyPageDao myPageDao;

	@Override
    public MyPage getUserInfo(Long userId) {
        return myPageDao.getUserInfo(userId);
    }

    @Override
    public MyPage getUserBoard(Long userId) {
        List<Board> boards = myPageDao.getUserBoards(userId);
        MyPage myPage = new MyPage();
        myPage.setBoardList(boards);
        return myPage;
    }

    @Override
    public MyPage getUserRun(Long userId) {
        List<Run> runs = myPageDao.getUserRuns(userId);
        MyPage myPage = new MyPage();
        myPage.setRecordList(runs);
        return myPage;
    }
	
}
