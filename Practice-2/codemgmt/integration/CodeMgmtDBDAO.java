package com.clt.apps.opus.dou.doutraining.codemgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailByCodeIDVO;
import com.clt.apps.opus.dou.doutraining.errmsgmgmt.integration.ErrMsgMgmtDBDAOErrMsgVOCSQL;
import com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.integration.ErrorMsgManagermentDBDAOErrMsgVOCSQL;
import com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.integration.ErrorMsgManagermentDBDAOErrMsgVODSQL;
import com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.integration.ErrorMsgManagermentDBDAOErrMsgVOUSQL;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.syscommon.management.opus.codemanagement.event.CodeManagementEvent;
import com.clt.syscommon.management.opus.codemanagement.integration.CodeManagementDBDAOComIntgCdDtlRSQL;

public class CodeMgmtDBDAO extends  DBDAOSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	public List<CodeMgmtVO> searchCodeVO(CodeMgmtVO codeMgmtVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<CodeMgmtVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(codeMgmtVO != null){
				Map<String, String> mapVO = codeMgmtVO .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAOCodeMgmtVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CodeMgmtVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	
//	public DBRowSet searchAPPCodeDetailList(Event e) throws DAOException {
//		DBRowSet dbRowset = null;
//		CodeManagementEvent event = (CodeManagementEvent) e;
//		// form 조회조건
//		String codeid = event.getCodeMgmtCondVO().getCodeid().toUpperCase(); 
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("codeid", codeid);
//		try {
//			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeManagementDBDAOComIntgCdDtlRSQL(), param, null);
//		}catch(SQLException se){
//			log.error(se.getMessage(),se);
//			throw new DAOException(new ErrorHandler(se).getMessage());
//		}catch(Exception ex){
//			log.error(ex.getMessage(),ex);
//			throw new DAOException(new ErrorHandler(ex).getMessage());
//		}
//		return dbRowset;
//	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<DetailByCodeIDVO> searchCodeMgmDetails(DetailByCodeIDVO detailByCodeIDVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<DetailByCodeIDVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

//		String codeID = codeMgmtVO.getIntgCdId();
		
		try{
 			if(detailByCodeIDVO != null){
				Map<String, String> mapVO = detailByCodeIDVO .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDBDAODetailByCodeIDRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailByCodeIDVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	
	public int[] addCodeSheet1(List<CodeMgmtVO> codeMgmtVO) throws DAOException, Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (codeMgmtVO.size() > 0) {
				insCnt = sqlExe.executeBatch(
								(ISQLTemplate) new CodeMgmtDBDAOCodeMgmtVOCSQL(),
								codeMgmtVO, null);
				for (int i = 0; i < insCnt.length; i++) {
					if (insCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}

	public int[] modifyCodeSheet1(List<CodeMgmtVO> codeMgmtVO) throws DAOException,Exception  {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeMgmtVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeMgmtVOUSQL(), codeMgmtVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}

	public int[] removeCodeSheet1(List<CodeMgmtVO> codeMgmtVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeMgmtVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeMgmtVODSQL(), codeMgmtVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to remove No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	
	public int[] addCodeSheet2(List<DetailByCodeIDVO> detailByCodeIDVO) throws DAOException, Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if (detailByCodeIDVO.size() > 0) {
				insCnt = sqlExe.executeBatch(
								(ISQLTemplate) new CodeMgmtDBDAOCodeMgmtDetailsVOCSQL(),
								detailByCodeIDVO, null);
				for (int i = 0; i < insCnt.length; i++) {
					if (insCnt[i] == Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No" + i + " SQL");
				}
			}
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}

	public int[] modifyCodeSheet2(List<DetailByCodeIDVO> detailByCodeIDVO) throws DAOException,Exception  {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailByCodeIDVO.size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeMgmtDetailsVOUSQL(), detailByCodeIDVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}

	public int[] removeCodeSheet2(List<DetailByCodeIDVO> detailByCodeIDVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(detailByCodeIDVO.size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDBDAOCodeMgmtDetailsVODSQL(), detailByCodeIDVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to remove No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}

}
