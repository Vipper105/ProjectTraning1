/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrorMsgManagermentBCImpl.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.17
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.17 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.integration.ErrorMsgManagermentDBDAO;
import com.clt.apps.opus.esm.clv.errormanagementtraining.errormsgmanagerment.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-ErrorManagementTraining Business Logic Command Interface<br>
 * - ALPS-ErrorManagementTraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author dinhhuy
 * @since J2EE 1.6
 */
public class ErrorMsgManagermentBCImpl extends BasicCommandSupport implements ErrorMsgManagermentBC {

	// Database Access Object
	private transient ErrorMsgManagermentDBDAO dbDao = null;

	/**
	 * ErrorMsgManagermentBCImpl 객체 생성<br>
	 * ErrorMsgManagermentDBDAO를 생성한다.<br>
	 */
	public ErrorMsgManagermentBCImpl() {
		dbDao = new ErrorMsgManagermentDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchErrMsg(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageErrMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			
			// huy
			List<ErrMsgVO> listAllMsg= dbDao.searchErrMsg(null);
			Set<String> listDuplicateMsgErrList = duplicateMsgErrList(listAllMsg,errMsgVO);
			// 
			
			for ( int i=0; i<errMsgVO .length; i++ ) {
				if ( errMsgVO[i].getIbflag().equals("I")){
				
					
					
					// lấy data insert vào so sánh trong DB xem có trùng hay không
//					boolean isDuplicateMsgErr = checkDuplicateMsgErr(listAllMsg,errMsgVO[i]);
					boolean isDuplicateMsgErr = listDuplicateMsgErrList.size() > 0;
					
					StringBuilder msgError = null;
					if(isDuplicateMsgErr){
						/*
						msgError = new StringBuilder();
						msgError.append("Messege Code: ");
						Iterator value = listDuplicateMsgErrList.iterator();
						 while (value.hasNext()) {
							msgError.append(" "+ value.next() +" , ");
						}
						msgError.append(" is existed");
						throw new EventException(msgError.toString());
						*/
						throw new EventException(new ErrorHandler("ERR00001").getMessage());
					}
					
				
					
//					List<ErrMsgVO> listMsgCd = checkDuplicateMsgErr2(listAllMsg,errMsgVO[i]);
//					if(listMsgCd.size() > 0){
//						throw new EventException("Messege Code: "+errMsgVO[i].getErrMsgCd() + " is existed");
//					}
					
					errMsgVO[i].setCreUsrId(account.getUsr_id());
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(errMsgVO[i]);
					
				} else if ( errMsgVO[i].getIbflag().equals("U")){
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("D")){
					deleteVoList.add(errMsgVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageErrMsgS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageErrMsgS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageErrMsgS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 *  Return list contains the duplicated error messages
	 * 
	 * @param List<ErrMsgVO> listMsgErr
	 * @param ErrMsgVO[] errMsgVO
	 */
	public Set<String> duplicateMsgErrList(List<ErrMsgVO> listMsgErr,
			ErrMsgVO[] errMsgVO) {
		
		Set<String> duplicatedMsgCds = new HashSet<String>();
		for (int i = 0; i < errMsgVO.length; i++) {

			for (int j = 0; j < listMsgErr.size(); j++) {
				if (listMsgErr.get(j).getErrMsgCd().equals(errMsgVO[i].getErrMsgCd())) {	
					duplicatedMsgCds.add(errMsgVO[i].getErrMsgCd());
					break;
				}
			}
		}

		return duplicatedMsgCds;	
		
	}
	
}