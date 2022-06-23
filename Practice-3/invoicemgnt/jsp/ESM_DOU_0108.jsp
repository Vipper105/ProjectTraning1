<%
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.jsp
*@FileTitle : Invoice Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
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
<%@ page import="com.clt.apps.opus.esm.clv.invoicedoutraining.invoicemgnt.event.EsmDou0108Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	EsmDou0108Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;			//서버에서 발생한 에러
	String strErrMsg = "";						//에러메세지
	int rowCount	 = 0;						//DB ResultSet 리스트의 건수

	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	String partners   		= "";
	// String lanes			= "";
	
	Logger log = Logger.getLogger("com.clt.apps.InvoiceDouTraining.InvoiceMgnt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (EsmDou0108Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		// get data for combobox partners
		partners = eventResponse.getETCData("partners");
		// lanes = eventResponse.getETCData("lanes");
	}catch(Exception e) {
		out.println(e.toString());
	}
%>

<head>
<title>Invoice Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<script language="javascript">
var partnersCombo = "ALL|<%=partners%>";
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
			<button class="btn_accent" name="btn_Retrieve" id="btn_Retrieve" type="button">Retrieve</button><!--
			--><button class="btn_accent" name="btn_New" id="btn_New" type="button">New</button><!--
			--><button class="btn_accent" name="btn_DownExcel" id="btn_DownExcel" type="button">Down Excell</button><!--
			--><button class="btn_accent" name="btn_Down" id="btn_Down" type="button">Down</button>			
		</div>
		
		<div class="location">	
			<span id="navigation"></span>
		</div>
</div>



<div class="wrap_search_tab">
		
		<div class="opus_design_inquiry wFit">
		    <table class="search">
		     	<colgroup>
		     	
					<col width="100" />				
					<col width="0" />						
					<col width="100" />	
					<col width="0" />
					<col width="50" />
					<col width="0" />
					<col width="50" />
					<col width="*" />
				
									
					
							
			   </colgroup> 
		        <tbody>
				<tr>
					<th>Year month</th>
				 	<td style="width: 350px; ">
					 	<input class="input" style="width: 80px;text-align:center;background-color:#C7E4FA;" type="text" id="acct_yrmon_from" name="acct_yrmon_from" value=""><!--
					 	--><button class="btn_left" name="btn_datefrom_down" id="btn_datefrom_down" type="button"></button><!--
					 	--><button class="btn_right" name="btn_datefrom_up" id="btn_datefrom_up" type="button"></button><!--
					 	--><input class="input" style="width: 80px;text-align:center;background-color:#C7E4FA;" type="text" id="acct_yrmon_to" name="acct_yrmon_to" value=""><!--
					 	--><button class="btn_left" name="btn_dateto_down" id="btn_dateto_down" type="button"></button><!--
					 	--><button class="btn_right" name="btn_dateto_up" id="btn_dateto_up" type="button"></button>
						
					</td>
					<th>Partner</th>
					<td><script type="text/javascript">ComComboObject('s_partner', 1, 80, 1, 0, 0);</script></td>
				   	<th>Lane</th>
					<td><script type="text/javascript">ComComboObject('s_lane', 1, 80, 1, 0, 0);</script></td>
					<th>Trade</th>
					<td><script type="text/javascript">ComComboObject('s_trade', 1, 80, 1, 0, 0);</script></td>
				
				</tr>
				</tbody>
			</table>
		
		</div>
		
		
</div>

<div class="wrap_result">
	<div class="opus_design_tab">
			<script type="text/javascript">ComTabObject('tab1')</script>
		</div>
		<div class="opus_design_grid wFit" name="tabLayer" id="tabLayer">
			<h3 class="title_design">Summary</h3>
			<script language="javascript">ComSheetObject('sheetSummary');</script>
		</div>
		
		<div class="opus_design_grid wFit" name="tabLayer" id="tabLayer">
			<h3 class="title_design">Details</h3>
			<script language="javascript">ComSheetObject('sheetDetails');</script>
		</div>
		
	</div>


<!-- 개발자 작업  끝 -->
</form>
