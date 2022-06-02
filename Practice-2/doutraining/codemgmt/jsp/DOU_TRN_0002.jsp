<%
/*=========================================================
*Copyright(c) 2020 CyberLogitec
*@FileName : DOU_TRN_001.jsp
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2020.03.17
*@LastModifier : 
*@LastVersion : 1.0
* 2020.03.17 
* 1.0 Creation
=========================================================*/
%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.dou.doutraining.codemgmt.event.DouTrn0002Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	DouTrn0002Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;			//서버에서 발생한 에러
	String strErrMsg = "";						//에러메세지
	int rowCount	 = 0;						//DB ResultSet 리스트의 건수

	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";
	String strSubSysCd= "";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	Logger log = Logger.getLogger("com.clt.apps.DouTraining.codemgmt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (DouTrn0002Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		
		// get ETC Data
		//strSubSysCd = eventResponse.getETCData("sub_sys_cd");

	}catch(Exception e) {
		out.println(e.toString());
	}
%>
<html>
<head>
<title>Error Message Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
	}
</script>
</head>

<body  onLoad="setupPage();">
<form name="form1">
<input type="hidden" name="f_cmd">
<input type="hidden" name="pagerows">
<input type="hidden" name="codeid">
<input type="hidden" name="selectcodes">

<!-- 개발자 작업	-->
<div class="page_title_area clear">
	<h2 class="page_title"><button type="button"><span id="title"></span></button></h2>
	<div class="opus_design_btn">
		<button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--  
		--><button type="button" class="btn_normal" name="btn_Save" id="btn_Save">Save</button><!--
		--><button type="button" class="btn_normal" name="btn_DownExcell" id="btn_DownExcell">Down Excell</button>
	</div>
	<div class="location">
	     <span id="navigation"></span>
	</div>
</div>
	
<div class="wrap_search">
	<div class="opus_design_inquiry wFit">
		<table class="search" border="0" style="witdh: 100%">
		        
			<tr>
				<th width="80">Sub System</th>
				<td width="120"><input type="text" style="width:100px;" class="input" value="" name="s_ownr_sub_sys_cd" id="s_ownr_sub_sys_cd"></td>
				<td width="70">Cd ID</td>
				<td><input type="text" style="width:100px;" class="input" value="" name="s_intg_cd_id" id="s_intg_cd_id"></td>
				</tr> 
			
		</table>
	</div>
</div>
		
<div class="wrap_result">
	<div class="opus_design_grid">
		<h3 class="title_design">Master</h3>
		<div class="opus_design_btn">
			<button type="button" class="btn_normal" name="btn_rowadd" id="btn_rowadd">Row Add</button><!--
			--><button type="button" class="btn_normal" name="btn_rowdelete" id="btn_rowdelete">Row Delete</button>
		</div>
	</div>
	
	<!-- Gọi ra sheet1 -->
	<script language="javascript">ComSheetObject('sheet1');</script>
	<div class="opus_design_inquiry"><table class="line_bluedot"><tr><td></td></tr></table></div>
	
	<div class ="opus_design_grid" >
		<h3 class="title_design">Detail</h3>
		<div class="opus_design_btn">
			<button type="button" class="btn_normal" name="btn_rowadd_dtl" id="btn_rowdelete_dtl">Row Add</button><!-- 
			 --><button type="button" class="btn_normal" name="btn_rowdelete_dtl" id="btn_rowdelete_dtl">Row Delete</button>
		</div>
	</div>
		
	<!-- Gọi ra sheet2 -->
	<script language="javascript">ComSheetObject('sheet2');</script>
	
	
</div>


<!-- 개발자 작업  끝 -->
</form>
</body>
</html>