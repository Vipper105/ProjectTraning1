/*=========================================================
*Copyright(c) 2009 CyberLogitec
*@FileName : ErrorMessageDAO.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2009.02.26
*@LastModifier : 김경범
*@LastVersion : 1.0
* 2009.02.26 김경범
* 1.0 Creation
=========================================================*/
package com.clt.syscommon.management.opus.errormessage.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.syscommon.common.table.ComErrMsgVO;
import com.clt.syscommon.management.opus.errormessage.basic.ErrorMessageBCImpl;


/**
 * NIS2010 ErrorMessageDAO <br>
 * - NIS2010-ErrorMessageManagement system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author KyungBum Kim
 * @see ErrorMessageBCImpl 참조
 * @since J2EE 1.4
 */
public class ErrorMessageDAO extends DBDAOSupport {

	/**
	 * ErrorMessageDAO의 데이타 모델에 해당되는 값을 불러온다.<br>
	 * 
	 * @param ComErrMsgVO comerrmsgvo
	 * @return DBRowSet
	 * @throws DAOException
	 */
	public DBRowSet searchComErrMsgVO(ComErrMsgVO comerrmsgvo) throws DAOException {
		DBRowSet dbRowset = null;
		
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(comerrmsgvo != null){
				Map<String, String> mapVO = comerrmsgvo .getColumnValues();
			
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("SysComDB").executeQuery((ISQLTemplate)new ErrorMessageDAOComErrMsgVORSQL(), param, velParam);
		}catch(SQLException se){
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return dbRowset;
	}
	/**
	 * ErrorMessageDAO의 여러 데이타 모델을 지정된 ibflag 값에 따라 DB에 반영한다.(추가, 수정, 삭제)<br>
	 * @param ComErrMsgVO[] comerrmsgvo
	 * @param SignOnUserAccount account
	 * @see ErrorMessageDAOEvent
	 * @throws DAOException
	 */
	public void multiComErrMsgVO(ComErrMsgVO[] comerrmsgvo, SignOnUserAccount account) throws DAOException,Exception {
		
		ArrayList<ComErrMsgVO> insModels = new ArrayList<ComErrMsgVO>();
		ArrayList<ComErrMsgVO> updModels = new ArrayList<ComErrMsgVO>();
		ArrayList<ComErrMsgVO> delModels = new ArrayList<ComErrMsgVO>();

		try {
			ComErrMsgVO model = null;
			for (int i = 0; i < comerrmsgvo .length; i++) {
				model = comerrmsgvo[i];
				if (model.getIbflag().length() > 0) {
					if (model.getIbflag().equals("I")) {
						insModels.add(model);
					} else if (model.getIbflag().equals("U")) {
						updModels.add(model);
					} else if (model.getIbflag().equals("D")) {
						delModels.add(model);
					}
					// 세션 정보 설정
					model.setCreUsrId(account.getUsr_id());
					model.setUpdUsrId(account.getUsr_id());
				}
			}
			int[] insCnt = null;
			int[] updCnt = null;
			int[] delCnt = null;

			SQLExecuter sqlExe = new SQLExecuter("SysComDB");
			
			if(updModels.size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new ErrorMessageDAOComErrMsgVOUSQL(), updModels,null);
				for(int i=0;i<updCnt.length;i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to update No"+ i + " SQL");
				}
			}
			if(delModels.size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new ErrorMessageDAOComErrMsgVODSQL(), delModels,null);
				for(int i=0;i<delCnt.length;i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to delete No"+ i + " SQL");
				}
			}
			
			if(insModels.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new ErrorMessageDAOComErrMsgVOCSQL(), insModels,null);
				for(int i=0;i<insCnt.length;i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}

		} catch (SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}
}
