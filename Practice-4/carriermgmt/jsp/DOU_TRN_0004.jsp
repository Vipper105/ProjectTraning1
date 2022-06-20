<%/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004.jsp
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.esm.clv.carrierdoutraining.carriermgmt.event.DouTrn0004Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	DouTrn0004Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;			//서버에서 발생한 에러
	String strErrMsg = "";						//에러메세지
	int rowCount	 = 0;						//DB ResultSet 리스트의 건수

	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	String carriers   		= "";
	String lanes 			= "";
	Logger log = Logger.getLogger("com.clt.apps.CarrierDouTraining.CarrierMgmt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (DouTrn0004Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
		strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		
		// get carriers combobox
		carriers = eventResponse.getETCData("carriers");
		lanes = eventResponse.getETCData("lanes");

	}catch(Exception e) {
		out.println(e.toString());
	}
%>

<head>
<title>Carrier Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
var carriersCombo = "ALL|<%=carriers%>";
var laneCombo = "<%=lanes%>";
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
	}
</script>
</head>

<form name="form">
<input type="hidden" name="f_cmd">
<input type="hidden" name="pagerows">

<!-- 개발자 작업	-->
<div class="page_title_area clear">
	<div class="opus_design_btn">
		<button class="btn_accent" name="btn_retrieve" id="btn_retrieve" type="button">Retrieve</button><!--
		--><button class="btn_accent" name="btn_new" id="btn_new" type="button">New</button><!--
		--><button class="btn_accent" name="btn_save" id="btn_save" type="button">Save</button><!--
		--><button class="btn_accent" name="btn_downExcel" id="btn_downExcel" type="button">Down Excel</button>			
	</div>
</div>

<div class="wrap_search">
		
		<div class="opus_design_inquiry wFit">
		    <table>
		     	<colgroup>
					<col width="100" />				
					<col width="180" />						
					<col width="20" />	
					<col width="*" />	
							
			   </colgroup> 
		        <tbody>
				<tr>
					<th>Carrier</th>
					<td>
					 	<script type="text/javascript">ComComboObject('s_carrier', 1, 120, 1, 0, 0);</script>
					</td>
					<th>Vendor</th>
					<td>
					 	<input class="input" style="width: 150px;" type="text" maxlength="6" id="s_vndr_seq" name="s_vndr_seq" value="">
					</td>
					<th>Create Date</th>
				 	<td>
					 	<input class="input" style="width: 150px;" type="text" id="s_cre_dt_fm" name="s_cre_dt_fm" value="" placeholder="YYYY-MM-DD"><!-- 
					 	--><button type="button" class="calendar ir" name="btn_calendar_dt_fr" id="btn_calendar_dt_fr"></button>~
					 	<input class="input" style="width: 150px;" type="text" id="s_cre_dt_to" name="s_cre_dt_to" value="" placeholder="YYYY-MM-DD"><!-- 
					 	--><button type="button" class="calendar ir" name="btn_calendar_dt_to" id="btn_calendar_dt_to"></button>
					 	
						
					</td>
					
				</tr>
				</tbody>
			</table>
		
		</div>
		
		
</div>

<div class="wrap_result">
		
		<div class ="opus_design_btn">
			<button type="button" class="btn_normal" name="btn_Delete" id="btn_Delete">Row Delete</button><!-- 
				--><button type="button" class="btn_normal" name="btn_Add" id="btn_Add">Row Add</button>
		</div>
		<div class="opus_design_grid clear" name="tabLayer" id="tabLayer">
			<script language="javascript">ComSheetObject('sheetCarrier');</script>
		</div>
		
	</div>

<!-- 개발자 작업  끝 -->
</form>