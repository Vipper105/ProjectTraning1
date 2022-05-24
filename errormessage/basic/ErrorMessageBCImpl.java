/*=========================================================
*Copyright(c) 2009 CyberLogitec
*@FileName : ErrorMessageBCImpl.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2009.02.26
*@LastModifier : 김경범
*@LastVersion : 1.0
* 2009.02.26 김경범
* 1.0 Creation
=========================================================*/
package com.clt.syscommon.management.opus.errormessage.basic;

import java.util.List;

import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.syscommon.common.table.ComErrMsgVO;
import com.clt.syscommon.management.opus.errormessage.integration.ErrorMessageDAO;
import com.clt.syscommon.management.opus.errormessage.vo.ErrorMessageResponseContainerVO;

/**
 * NIS2010-ErrorMessageManagement Business Logic Basic Command implementation<br>
 * - NIS2010-ErrorMessageManagement에 대한 비지니스 로직을 처리한다.<br>
 *
 * @author KyungBum Kim
 * @see ErrorMessageManagementEventResponse,ErrorMessageBC 각 DAO 클래스 참조
 * @since J2EE 1.4
 */

public class ErrorMessageBCImpl extends BasicCommandSupport implements ErrorMessageBC {

	// Database Access Object
	private transient ErrorMessageDAO dbDao = null;

	/**
	 * $ErrorMessageBCImpl 객체 생성<br>
	 * ErrorMessageDBDAO를 생성한다.<br>
	 */
	public ErrorMessageBCImpl() {
		dbDao = new ErrorMessageDAO();
	}
	/**
	 * 조회 이벤트 처리<br>
	 *  ErrorMessage화면에 대한 조회 이벤트 처리<br>
	 * 
	 * @param ComErrMsgVO comerrmsgvo
	 * @return ErrorMessageResponseContainerVO
	 * @exception EventException
	 */
	public ErrorMessageResponseContainerVO searchComErrMsgVO(ComErrMsgVO comerrmsgvo) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		DBRowSet rowSet = null;
		ErrorMessageResponseContainerVO containerVO = new ErrorMessageResponseContainerVO();
		try {
			rowSet = dbDao.searchComErrMsgVO(comerrmsgvo);
			if(rowSet!=null){
				List<Object> list = RowSetUtil.rowSetToVOs(rowSet, ComErrMsgVO .class);
				containerVO.setComErrMsgVO(list.toArray(new ComErrMsgVO[list.size()]));
				containerVO.setEtcKey1("SEARCH ETC DATA1");
				containerVO.setEtcKey2("SEARCH ETC DATA2");
				//containerVO.setUsrMessage(new ErrorHandler("TOT10003",new String[]{"Schedule Information"}).getUserMessage());
			}
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage());
		}
		return containerVO;
	}
	/**
	 * 멀티 이벤트 처리<br>
	 * In화면에 대한 멀티 이벤트 처리<br>
	 * 
	 * @param ComErrMsgVO[] comerrmsgvo
	 * @param SignOnUserAccount account
	 * @return ErrorMessageResponseContainerVO
	 * @exception EventException
	 */
	public ErrorMessageResponseContainerVO multiComErrMsgVO(ComErrMsgVO[] comerrmsgvo, SignOnUserAccount account) throws EventException{
		ErrorMessageResponseContainerVO containerVO = new ErrorMessageResponseContainerVO();
		try {
			dbDao.multiComErrMsgVO(comerrmsgvo, account);
		} catch (DAOException de) {
			log.error("err " + de.toString(), de);
			throw new EventException(de.getMessage());
		} catch (Exception de) {
			log.error("err " + de.toString(), de);
			throw new EventException(de.getMessage());
		}
		containerVO.setEtcKey1("SAVE ETC DATA1");
		containerVO.setEtcKey2("SAVE ETC DATA2");
		containerVO.setUsrMessage(new ErrorHandler("TOT10003").getUserMessage());
		
		return containerVO;
	}
}