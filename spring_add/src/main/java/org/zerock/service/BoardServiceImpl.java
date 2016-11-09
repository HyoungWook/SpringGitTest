package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO dao;

	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);

	}

	@Transactional
	@Override
	public BoardVO read(Integer bno) throws Exception {
		BoardVO board = dao.read(bno);
		dao.updateViewCnt(bno);
		return board;
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		dao.update(board);

	}

	@Override
	public void remove(Integer bno) throws Exception {
		dao.delete(bno);

	}

	@Override
	public List<BoardVO> listCri(SearchCriteria cri) throws Exception {
		List<BoardVO> list = dao.listCri(cri);
		return list;
	}

	@Override
	public int listCountCri(SearchCriteria cri) throws Exception {
		return dao.countPaging(cri);
	}

}
