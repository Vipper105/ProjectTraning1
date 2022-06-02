/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : CodeMgmtBC.java
 *@FileTitle : Practice 02
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.25
 *@LastModifier : 
 *@LastVersion : 1.0 
 * 1.0 Creation
=========================================================*/

package com.clt.apps.opus.dou.doutraining.codemgmt.basic;

import java.util.List;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailByCodeIDVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;




/**
 * Booking Business Logic Command Interface<br>
 * Booking에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author Baek Seungil
 * @see
 * @since J2EE 1.6
 */
public interface CodeMgmtBC {

	/**
	 * search a list of CodeMgmtVO base on input conditions.<br>
	 * 
	 * @param codeMgmtVO a instance to save information of UI input conditions.
	 * @return a list of CodeMgmtVO
	 * @exception EventException
	 */
	public List<CodeMgmtVO> searchCode(CodeMgmtVO codeMgmtVO) throws EventException;
	
	/**
	 * search a list of DetailByCodeIDVO base on input conditions.<br>
	 * 
	 * @param detailByCodeIDVO a instance to save information of UI input conditions.
	 * @return a list of DetailByCodeIDVO
	 * @exception EventException
	 */
	public List<DetailByCodeIDVO> searchCodeMgmDetails(DetailByCodeIDVO detailByCodeIDVO) throws EventException;
	
	/**
	 * manage create/delete/update for code .<br>
	 * 
	 * @param codeMgmtVOs a instance to save information of UI input conditions.
	 * @param account a instance to show information user who singing
	 * @exception EventException
	 */
	public void manageCode(CodeMgmtVO[] codeMgmtVOs, SignOnUserAccount account) throws EventException;
	
	/**
	 * manage create/delete/update for details code .<br>
	 * 
	 * @param detailByCodeIDVO a instance to save information of UI input conditions.
	 * @param account a instance to show information user who singing
	 * @exception EventException
	 */
	public void manageCodeDetails(DetailByCodeIDVO[] detailByCodeIDVO, SignOnUserAccount account) throws EventException;
	
}
