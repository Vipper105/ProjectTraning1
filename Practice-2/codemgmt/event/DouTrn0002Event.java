package com.clt.apps.opus.dou.doutraining.codemgmt.event;

import com.clt.apps.opus.dou.doutraining.codemgmt.vo.CodeMgmtVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailByCodeIDVO;
import com.clt.framework.support.layer.event.EventSupport;

public class DouTrn0002Event extends EventSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Table Value Object query conditions and single case processing  */
	CodeMgmtVO codeMgmtVO = null;
	DetailByCodeIDVO detailByCodeIDVO = null;
	
	/** Table Value Object Multi Data Processing */
	CodeMgmtVO[] codeMgmtVOs = null;
	DetailByCodeIDVO[] detailByCodeIDVOs = null;
	
	/**
	 * set CodeVo .<br>
	 * 
	 * @param code a instance to save information into CodeVO
	 */
	public void setCodeVO(CodeMgmtVO code) {
		this.codeMgmtVO = code;	
	}

	/**
	 * set CodeVOs .<br>
	 * 
	 * @param codeMgmtVOs is a array which stores instances to save information into CodeVO
	 */
	public void setCodeVOs(CodeMgmtVO[] codeMgmtVOs) {
		this.codeMgmtVOs = codeMgmtVOs;	
	}
	
	/**
	 * get CodeMgmtVO .<br>
	 * 
	 */
	public CodeMgmtVO getCodeVO() {
		return codeMgmtVO;
	}
	
	/**
	 * get a array stores CodeMgmtVOs .<br>
	 * 
	 */
	public CodeMgmtVO[] getCoMgmtVOs(){
		return codeMgmtVOs;
	}

	/**
	 * get a array stores DetailByCodeIDVO .<br>
	 * 
	 */
	public DetailByCodeIDVO getDetailByCodeIDVO() {
		return detailByCodeIDVO;
	}

	/**
	 * get a array stores DetailByCodeIDVOs .<br>
	 * 
	 */
	public DetailByCodeIDVO[] getDetailByCodeIDVOs() {
		return detailByCodeIDVOs;
	}
	
	/**
	 * get a array stores DetailByCodeIDVO .<br>
	 * @param detailByCodeIDVO a instance to save information into DetailByCodeIDVO
	 */
	public void setDetailByCodeIDVO(DetailByCodeIDVO detailByCodeIDVO) {
		this.detailByCodeIDVO = detailByCodeIDVO;
	}

	/**
	 * get a array stores DetailByCodeIDVO .<br>
	 * @param detailByCodeIDVOs is array which is save instances of DetailByCodeIDVO
	 */
	public void setDetailByCodeIDVOs(DetailByCodeIDVO[] detailByCodeIDVOs) {
		this.detailByCodeIDVOs = detailByCodeIDVOs;
	}
	
	

}
