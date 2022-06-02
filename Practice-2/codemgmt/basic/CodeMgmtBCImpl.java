package com.clt.apps.opus.dou.doutraining.codemgmt.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.clt.apps.opus.dou.doutraining.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailByCodeIDVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;


public class CodeMgmtBCImpl extends BasicCommandSupport  implements CodeMgmtBC{

	private transient CodeMgmtDBDAO dao = null;
	
	public CodeMgmtBCImpl(){
		dao = new CodeMgmtDBDAO();
	}

	/**
	 * search a list of CodeMgmtVO base on input conditions.<br>
	 * 
	 * @param codeMgmtVO a instance to save information of UI input conditions.
	 * @return a list of CodeMgmtVO
	 * @exception EventException
	 */
	@Override
	public List<CodeMgmtVO> searchCode(CodeMgmtVO codeMgmtVO) throws EventException {
		try {
			return dao.searchCodeVO(codeMgmtVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}

	}
	
	/**
	 * search a list of DetailByCodeIDVO base on input conditions.<br>
	 * 
	 * @param detailByCodeIDVO a instance to save information of UI input conditions.
	 * @return a list of DetailByCodeIDVO
	 * @exception EventException
	 */
	@Override
	public List<DetailByCodeIDVO> searchCodeMgmDetails(DetailByCodeIDVO detailByCodeIDVO) throws EventException {
		try {
			return dao.searchCodeMgmDetails(detailByCodeIDVO);
		} catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}

	}
	
	/**
	 * manage create/delete/update for code .<br>
	 * 
	 * @param codeMgmtVOs a instance to save information of UI input conditions.
	 * @param account a instance to show information user who singing
	 * @exception EventException
	 */
	public void manageCode(CodeMgmtVO[] codeMgmtVOs, SignOnUserAccount account) throws EventException{
		try {
			
			List<CodeMgmtVO> codeList = searchCode(null);
			Set<String> listDuplicateCodeMgmt = duplicateCodeList(codeList,codeMgmtVOs);
					
			List<CodeMgmtVO> insertVoList = new ArrayList<CodeMgmtVO>();
			List<CodeMgmtVO> updateVoList = new ArrayList<CodeMgmtVO>();
			List<CodeMgmtVO> deleteVoList = new ArrayList<CodeMgmtVO>();
			for ( int i=0; i<codeMgmtVOs .length; i++ ) {
				if ( codeMgmtVOs[i].getIbflag().equals("I")){
					
					
					boolean isDuplicateCode = listDuplicateCodeMgmt.size() > 0;
					
					if(isDuplicateCode){
						throw new EventException(new ErrorHandler("ERR00001").getMessage());
					}			
					
					codeMgmtVOs[i].setCreUsrId(account.getUsr_id());
					codeMgmtVOs[i].setUpdUsrId(account.getUsr_id());
					insertVoList.add(codeMgmtVOs[i]);
				} else if ( codeMgmtVOs[i].getIbflag().equals("U")){
					codeMgmtVOs[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(codeMgmtVOs[i]);
				} else if ( codeMgmtVOs[i].getIbflag().equals("D")){
					deleteVoList.add(codeMgmtVOs[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dao.addCodeSheet1(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dao.modifyCodeSheet1(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dao.removeCodeSheet1(deleteVoList);
			}
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	/**
	 * manage create/delete/update for details code .<br>
	 * 
	 * @param detailByCodeIDVO a instance to save information of UI input conditions.
	 * @param account a instance to show information user who singing
	 * @exception EventException
	 */
	@Override
	public void manageCodeDetails(DetailByCodeIDVO[] dtlByCodeIDVOs,
			SignOnUserAccount account) throws EventException {
		try {
			
			List<DetailByCodeIDVO> codeDetailsList = searchCodeMgmDetails(null);
			Set<String> listDuplicateCodeDetails = duplicateCodeDetailsList(codeDetailsList,dtlByCodeIDVOs);
			
			List<DetailByCodeIDVO> insertVoList = new ArrayList<DetailByCodeIDVO>();
			List<DetailByCodeIDVO> updateVoList = new ArrayList<DetailByCodeIDVO>();
			List<DetailByCodeIDVO> deleteVoList = new ArrayList<DetailByCodeIDVO>();
			for ( int i=0; i<dtlByCodeIDVOs .length; i++ ) {
				if ( dtlByCodeIDVOs[i].getIbflag().equals("I")){
					
					// ↓ ADDED by HuyDP - practice2 - 01-06-2022
					boolean isDuplicateCodeDetails = listDuplicateCodeDetails.size() > 0;
					
					if(isDuplicateCodeDetails){
						throw new EventException(new ErrorHandler("ERR00001").getMessage());
					}
					
					dtlByCodeIDVOs[i].setCreUsrId(account.getUsr_id());
					dtlByCodeIDVOs[i].setUpdUsrId(account.getUsr_id());
					// ↑ ADDED by HuyDP - practice2 - 01-06-2022
					insertVoList.add(dtlByCodeIDVOs[i]);
				} else if ( dtlByCodeIDVOs[i].getIbflag().equals("U")){
					dtlByCodeIDVOs[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(dtlByCodeIDVOs[i]);
				} else if ( dtlByCodeIDVOs[i].getIbflag().equals("D")){
					deleteVoList.add(dtlByCodeIDVOs[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dao.addCodeSheet2(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dao.modifyCodeSheet2(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dao.removeCodeSheet2(deleteVoList);
			}
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * check whether if duplicate Code or NOT  .<br>
	 * 
	 * @param detailByCodeIDVO a instance to save information of UI input conditions.
	 * @param account a instance to show information user who singing
	 * @return Set<String> stores code that is being duplicate 
	 */
	public Set<String> duplicateCodeList(List<CodeMgmtVO> listCodeMgmtVOs,
			CodeMgmtVO[] codeMgmtVO) {
		
		Set<String> duplicatedCode = new HashSet<String>();
		for (int i = 0; i < codeMgmtVO.length; i++) {

			for (int j = 0; j < listCodeMgmtVOs.size(); j++) {
				if (listCodeMgmtVOs.get(j).getIntgCdId().equals(codeMgmtVO[i].getIntgCdId())) {	
					duplicatedCode.add(codeMgmtVO[i].getIntgCdId());
					break;
				}
			}
		}

		return duplicatedCode;	

	}
	
	/**
	 * check whether if duplicate Code or NOT  .<br>
	 * 
	 * @param detailByCodeIDVO a instance to save information of UI input conditions.
	 * @param account a instance to show information user who singing
	 * @return Set<String> stores details code that is being duplicate 
	 */
	public Set<String> duplicateCodeDetailsList(List<DetailByCodeIDVO> detailByCodeIDVOs,
			DetailByCodeIDVO[] detailByCodeIDVO) {
		
		Set<String> duplicatedCodeDetails = new HashSet<String>();
		for (int i = 0; i < detailByCodeIDVO.length; i++) {

			for (int j = 0; j < detailByCodeIDVOs.size(); j++) {
				if (detailByCodeIDVOs.get(j).getIntgCdId().equals(detailByCodeIDVO[i].getIntgCdId())) {	
					duplicatedCodeDetails.add(detailByCodeIDVO[i].getIntgCdId());
					break;
				}
			}
		}

		return duplicatedCodeDetails;	

	}
}
