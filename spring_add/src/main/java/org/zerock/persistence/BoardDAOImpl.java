package org.zerock.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace="org.zerock.mappers.BoardMapper";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+".create", vo );
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		BoardVO board = sqlSession.selectOne(namespace+".getboard", bno);
		return board;
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace+".update", vo);

	}

	@Override
	public void delete(Integer bno) throws Exception {
		sqlSession.delete(namespace+".delete", bno);

	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		List<BoardVO> list = sqlSession.selectList(namespace+".list");
		return list;
	}

	@Override
	public List<BoardVO> listCri(SearchCriteria cri) throws Exception {
		List<BoardVO> list = 
				sqlSession.selectList(namespace+ ".list", cri, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
										//보여줄 데이터, 넘겨줄 값, 보여줄 데이터의 개수
		return list;
	}

	@Override
	public int countPaging(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace+".countPaging", cri);
	}

	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bno", bno);
		paramMap.put("amount", amount);
		
		sqlSession.update(namespace+".updateReplyCnt", paramMap);
		
	}

	@Override
	public int getBno(Integer rno) throws Exception {		
		return sqlSession.selectOne(namespace+".getBno", rno);
	}

	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		sqlSession.update(namespace+".updateViewCnt", bno);
	}

	
	
	

}
