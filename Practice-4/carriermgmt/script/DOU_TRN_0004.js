/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004.js
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */
    
   	/* 개발자 작업	*/
    var sheetObjects = new Array();
    var sheetCnt = 0;

    var comboObjects = new Array();
    var comboCnt = 0;

	/* 개발자 작업  끝 */
    
    function loadPage() {
    	
        // Initialize MultiCombobox
        for (var j = 0; j < comboObjects.length; j++) {
            initCombo(comboObjects[j], j + 1);
        }


        for (var i = 0; i < sheetObjects.length; i++) {
            ComConfigSheet(sheetObjects[i]);
            initSheet(sheetObjects[i], i + 1);
            ComEndConfigSheet(sheetObjects[i]);
        }

        // Show data on sheet when loading page
        doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);   	
        
        initDefault();
        
        // Handle events
        initControl();
    }
    
    function initDefault(){
    	s_cre_dt_fm.disabled = true;
    	s_cre_dt_to.disabled = true;
    }  
    
  //버튼클릭이벤트를 받아 처리하는 이벤트핸들러 정의 */
    document.onclick = processButtonClick;
    document.onkeydown=logKey;

    // 버튼 네임으로 구분하여 프로세스를 분기처리하는 이벤트핸들러 */
    function processButtonClick() {

        var sheetObject1 = sheetObjects[0];
        var formObj = document.form;


        try {
            var srcName = ComGetEvent("name");
            if (srcName == null) {
                return;
            }

            switch (srcName) {
                
                case "btn_retrieve":
                    doActionIBSheet(sheetObject1, formObj, IBSEARCH);
                    break;

                case "btn_new":
                    resetForm(formObj);
                    break;

                case "btn_save":
                    doActionIBSheet(sheetObject1, formObj, IBSAVE);
                    break;
                    
                case "btn_downExcel":
                	doActionIBSheet(sheetObject1, formObj, IBDOWNEXCEL);
                    break;
         
                case "btn_Add":
                	doActionIBSheet(sheetObject1, formObj, IBINSERT);
                	break;
                	
                case "btn_Delete":
                	doActionIBSheet(sheetObject1, formObj, IBDELETE);
                	break;	
                	
                case "btn_calendar_dt_fr":
                	var calendar = new ComCalendar();
                	calendar.select(formObj.s_cre_dt_fm,"yyyy-MM-dd");
                	break;
                	
                case "btn_calendar_dt_to":
                	var calendar = new ComCalendar();
                	calendar.select(formObj.s_cre_dt_to,"yyyy-MM-dd");
                	break;
            }

        } catch (e) {
            if (e == "[object Error]") {
                ComShowCodeMessage('JOO00001');
            } else {
                ComShowMessage(e.message);
            }
        }
    }
    
    
    /**
     * function to set button enter
     */
    function logKey(key){
    	var sheetObject1 = sheetObjects[0];
    	var formObj = document.form;
    	
    	if(key.code == 'Enter'){
    		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
    	}
    }
    
    /**
     * 페이지에 있는 IBSheet Object를 초기화 한다. <br>
     * IBSheet가 여러개일 경우 개수만큼 case를 추가하여 IBSheet 초기화 모듈을 구성한다. <br>
     * {@link #loadPage}함수에서 이 함수를 호출하여 IBSheet Object를 초기화 한다. <br>
     * @param {ibsheet} sheetObj    IBSheet Object
     * @param {int}     sheetNo     sheetObjects 배열에서 순번
     **/
    function initSheet(sheetObj,sheetNo) {
    	var cnt = 0;
    	switch (sheetNo) {
    		case 1:
    			with(sheetObj){  
    				var HeadTitle="STS|Chk|Hidden Carrier|Hidden Rev. Lane|Carrier|Rev. Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";
    				
    				SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
    				
    				var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
    				var headers = [ { Text:HeadTitle, Align:"Center"} ];
    				InitHeaders(headers, info);
    				
    				var cols = [ 
    		            {Type:"Status",    Hidden:1, Width:50,  Align:"Center",  SaveName:"ibflag"}, 
    		            {Type:"DelCheck",  Hidden:0, Width:50,  Align:"Center",  SaveName:"del_chk"},
    		            {Type:"Text",      Hidden:1, Width:100, Align:"Center",  SaveName:"jo_crr_cd_hid"},
    			        {Type:"Text",      Hidden:1, Width:100, Align:"Center",  SaveName:"rlane_cd_hid"},
    			        {Type:"PopupEdit",     Hidden:0, Width:100, Align:"Center",  SaveName:"jo_crr_cd",     KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:3},
    			        {Type:"ComboEdit",     Hidden:0, Width:100, Align:"Center",  SaveName:"rlane_cd",      KeyField:1, UpdateEdit:1, InsertEdit:1, ComboCode:laneCombo, ComboText: laneCombo},
    			        {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"vndr_seq",      KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N", EditLen:6},
    			        {Type:"Popup",     Hidden:0, Width:50,  Align:"Center",  SaveName:"cust_cnt_cd",   KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:2}, 
    				    {Type:"Popup",     Hidden:0, Width:100, Align:"Center",  SaveName:"cust_seq",      KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N", EditLen: 6}, 
    				    {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"trd_cd",        KeyField:0, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:3},
    				    {Type:"Combo",     Hidden:0, Width:70,  Align:"Center",  SaveName:"delt_flg",      KeyField:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y",  ComboText:"N|Y"}, 
    				    {Type:"Text",      Hidden:0, Width:200, Align:"Center",  SaveName:"cre_dt",        KeyField:0, UpdateEdit:0, InsertEdit:0}, 
    				    {Type:"Text",      Hidden:0, Width:200, Align:"Left",    SaveName:"cre_usr_id",    KeyField:0, UpdateEdit:0, InsertEdit:0}, 
    				    {Type:"Text",      Hidden:0, Width:200, Align:"Center",  SaveName:"upd_dt",        KeyField:0, UpdateEdit:0, InsertEdit:0}, 
    				    {Type:"Text",      Hidden:0, Width:200, Align:"Left",    SaveName:"upd_usr_id",    KeyField:0, UpdateEdit:0, InsertEdit:0}
    			    ];
    		        InitColumns(cols);
    		        SetEditable(1);
    		        SetWaitImageVisible(0);
    		        resizeSheet();
    			}
    			break;
    	}
    }
    
 // ↓ ===========================================    Set array for components   ==========================================

    /**
     * 페이지에 생성된 IBSheet Object를 sheetObjects배열에 등록한다. <br>
     * sheetObjects 배열은 공통전역변수로 상단에 정의하고, 이 함수는 {@link CoObject#ComSheetObject} 함수에 의해서 IBSheet Object가 생성되면서 자동 호출된다. <br>
     * 
     * @param {ibsheet} sheet_obj    IBSheet Object
     **/
    function setSheetObject(sheet_obj) {
        sheetObjects[sheetCnt++] = sheet_obj;
    }

    /**
     * Register the Combo Object created on the page in the ComboObjects array.<br>
     * The comboObjects array is defined as a global global variable at the top, and this function is automatically called <br>
     * when the Combo Object is created by the {@link CoObject # ComComboObject} function.
     * 
     * @param combo_obj  Combo Object ** /
     * 
     */
    function setComboObject(combo_obj) {
        comboObjects[comboCnt++] = combo_obj;
    }
    
    /**
     * adjust dimension of IBsheet following dimension of browser.
     * 
     */
    
 
    
    // ↑ ===========================================    Set array for components   ==========================================
    
    function resizeSheet() {
    	ComResizeSheet(sheetObjects[0]);
    }

    /**
     * IBSheet 관련 각종 기능(조회,저장 등)을 처리한다. <br>
     * {@link #processButtonClick}함수에서 이 함수를 호출하여 버튼에서 IBSheet의 기능을 호추할 때 사용한다. <br>
     * @param {ibsheet} sheetObj    IBSheet Object
     * @param {form}    formObj     Form Object
     * @param {int}     sAction     처리할 Action 코드(예:IBSEARCH,IBSAVE,IBDELETE,IBDOWNEXCEL 등이며 CoObject.js에 정의됨)
     **/
    function doActionIBSheet(sheetObj, formObj, sAction) {
        sheetObj.ShowDebugMsg(false);

        switch (sAction) {
            case IBSEARCH:
            	ComOpenWait(true);
                formObj.f_cmd.value = SEARCH;
                

                let sXml1 = sheetObj.GetSearchData("DOU_TRN_0004GS.do", FormQueryString(formObj));
                if (sXml1.length > 0) {
                    sheetObj.LoadSearchData(sXml1, { Sync: 1 });
                }
                
                s_carrier.SetSelectIndex(0);
                break;

            case IBDOWNEXCEL:
                if (sheetObj.RowCount() < 1) {
                    ComShowCodeMessage("COM132501");
                } else {
                    sheetObjects[0].Down2ExcelBuffer(true);
                    sheetObjects[0].Down2Excel({ FileName: "excel2", SheetName: "sheetCarrier", DownCols: makeHiddenSkipCol(sheetObjects[0]), SheetDesign: 1, Merge: 1 });      
                    sheetObjects[0].Down2ExcelBuffer(false);
                }

                break;
                
            case IBSAVE:
        		formObj.f_cmd.value = MULTI;
        		sheetObj.DoSave("DOU_TRN_0004GS.do", FormQueryString(formObj),-1,false);
         	
            	break;
            	
            case IBINSERT:
    			sheetObj.DataInsert(-1);
    			break;
    					
            case IBDELETE:
            	
            	for(let i= sheetObj.LastRow(); i >= sheetObj.HeaderRows(); i--){
            		if(sheetObj.GetCellValue(i,"del_chk")==1){
            			sheetObj.SetRowHidden(i,1);
            			sheetObj.SetRowStatus(i,"D");          			
            		}
            	}
            	 	
            	break;
        }

    }
  //↓ ===========================================    Generate combobox   ==========================================
    
    /**
     * Insert items into combobox
     * @param comboObj
     * @param comboItems
     * 
     **/
    function addComboItem(comboObj, comboItems) {
        for (var i = 0; i < comboItems.length; i++) {
            var comboItem = comboItems[i].split(",");
            //NYK Modify 2014.10.21
            if (comboItem.length == 1) {
                comboObj.InsertItem(i, comboItem[0], comboItem[0]);
            } else {
                comboObj.InsertItem(i, comboItem[0] + "|" + comboItem[1], comboItem[1]);
            }
        }
    }
    
    /**
     * Combo Initialization 
     * 
     * {@link #loadPage} Call this function when load page in order to initialize combobox
     * @param comboObj       tab object
     * @param comboNo        No. of tab
     **/
    function initCombo(comboObj, comboNo) {
        var formObj = document.form
        switch (comboNo) {
            case 1:
                with (comboObj) {
                    SetMultiSelect(1);
                    SetDropHeight(200);
                    ValidChar(2, 1);   
                   
                
                }
                var comboItems = carriersCombo.split("|");
                addComboItem(comboObj, comboItems);

                break;
        }
    }

    /**
     * Insert items into combobox
     * @param comboObj
     * @param comboItems
     * 
     **/
    function addComboItem(comboObj, comboItems) {
        for (var i = 0; i < comboItems.length; i++) {
            var comboItem = comboItems[i].split(",");
            //comboObj.InsertItem(i, comboItem[0] + "|" + comboItem[1], comboItem[1]);
            //NYK Modify 2014.10.21
            if (comboItem.length == 1) {
                comboObj.InsertItem(i, comboItem[0], comboItem[0]);
            } else {
                comboObj.InsertItem(i, comboItem[0] + "|" + comboItem[1], comboItem[1]);
            }
        }
        comboObj.SetItemCheck(0, true);
    }
    
    // ↑ ===========================================    Generate combobox   ==========================================
    
    /**
     * Event for reset function
     * 
     * @param formObj
     **/
    function resetForm(formObj) {
        sheetObjects[0].RemoveAll();
        formObj.reset();
    }    
    
    /**
     * Handling event when sheet change
     * 
     * @param sheetObj
     * @param Row
     * @param Col
     * @param Value
     * @param OldValue
     * @param RaiseFlag
     */
    function sheetCarrier_OnChange(sheetObj, Row, Col, Value, OldValue, RaiseFlag){
    	var formObj=document.form ;
    	var colName=sheetObj.ColSaveName(Col);
    	
    	//check exist lane code
    	if(colName == "rlane_cd"){
    		formObj.f_cmd.value     = COMMAND05;
    		var sParam              = FormQueryString(formObj) + "&rlane_cd=" + Value;
    		var sXml                = sheetObj.GetSearchData("DOU_TRN_0004GS.do", sParam, {sync:1});
    		var flag                = ComGetEtcData(sXml, "ISEXIST");
    		if(flag == 'N'){
    			ComShowCodeMessage("COM130402", ["Lane"]);
    			sheetObj.SetCellValue(Row, Col, OldValue, 0);
    			sheetObj.SelectCell(Row, Col);
    			return;
    		}
    	}

    	//check dup;icate joo carreer and lane code
    	if(colName == "jo_crr_cd" || colName == "rlane_cd"){
    		if(sheetObj.GetCellValue(Row,"jo_crr_cd") != "" && sheetObj.GetCellValue(Row,"rlane_cd") != ""){
    			if(sheetObj.ColValueDup("jo_crr_cd|rlane_cd") > -1){
    				ComShowCodeMessage("COM12115", "The Carrier and Rev.Lane");
    				sheetObj.SetCellValue(Row, Col,OldValue,0);
    				sheetObj.SelectCell(Row, Col);
    				return;
    			}		
    			//check on Service side
    			formObj.f_cmd.value	= COMMAND01;
    			var sParam			= FormQueryString(formObj) + "&jo_crr_cd=" + sheetObj.GetCellValue(Row,"jo_crr_cd") + "&rlane_cd=" + sheetObj.GetCellValue(Row,"rlane_cd");
    			var sXml 			= sheetObj.GetSearchData("DOU_TRN_0004GS.do", sParam, {sync:1});	
    			var flag			= ComGetEtcData(sXml, "ISEXIST");
    			if(flag == 'Y'){
    				ComShowCodeMessage("COM12115","The Carrier and Rev.Lane");
    				sheetObj.SetCellValue(Row, Col,OldValue,0);
    				sheetObj.SelectCell(Row, Col);
    			}
    		}
    	}
    	
    	//check exist vendor code
    	if(colName == "vndr_seq"){
    		formObj.f_cmd.value		= COMMAND02;
    		var sParam				= FormQueryString(formObj) + "&vndr_seq=" + Value;
    		var sXml 				= sheetObj.GetSearchData("DOU_TRN_0004GS.do", sParam, {sync:1});	
    		var flag				= ComGetEtcData(sXml, "ISEXIST");
    		if(flag == 'N'){
    			ComShowCodeMessage("COM130402",["Vendor"]);
    			sheetObj.SetCellValue(Row, Col,OldValue,0);
    			sheetObj.SelectCell(Row, Col);
    		}
    	}
    	
    	//check exist trade code
    	if(colName == "trd_cd"){
    		formObj.f_cmd.value     = COMMAND04;
    		var sParam              = FormQueryString(formObj) + "&trd_cd=" + Value;
    		var sXml                = sheetObj.GetSearchData("DOU_TRN_0004GS.do", sParam, {sync:1});
    		var flag                = ComGetEtcData(sXml, "ISEXIST");
    		if(flag == 'N'){
    			ComShowCodeMessage("COM130402", ["Trade"]);
    			sheetObj.SetCellValue(Row, Col, OldValue, 0);
    			sheetObj.SelectCell(Row, Col);
    		}
    	}
    	
    }
    
    /**
     * Handle event
     * 
     */
    function initControl(){	
    	// Handle event for [Vendor] textfield
    	document.getElementById('s_vndr_seq').addEventListener('keypress',
    		function(){
    			ComKeyOnlyNumber(this);
    		}	
    	)  	
    }
    
    /**
     * 
     * @param sheetObj
     * @param Row
     * @param Col
     */
    function sheetCarrier_OnPopupClick(sheetObj, Row, Col){
    	let colName = sheetObj.ColSaveName(Col);
    	
    	switch(colName){
    	case "cust_cnt_cd":
    	case "cust_seq":
    		ComOpenPopup('/opuscntr/CUSTOMER_POPUP.do', 900, 550, 'setCustCd', '1,0,1,1,1,1', true);
    		// ComOpenPopup('/opuscntr/COM_ENS_041.do', 800, 500, 'setCustCd', '1,0,1,1,1,1', true);
    		break;       
    	case "jo_crr_cd":
    		/**
    		 * This function open the pop-up.
    		 * 
    		 * @param sUrl: {string} - Required, pop-up address to be called.
    		 * @param iWidth: {int} - Required, the width of the pop-up window
    		 * @param iHeight: {int} - Required, the height of the pop-up window
    		 * @param sFunc: {string} - Required, function return data to parent window.
    		 * @param sDisplay: {string} - Required, column of the grid in the pop-up window is hidden, value: 1 visible|0 hidden.
    		 * @param bModal: {bool} - Selection, is the pop-up modal?
    		 * */
    		ComOpenPopup('/opuscntr/COM_ENS_0N1.do', 900, 550, 'setCrrCd', '1,0,0,1,1,1,1,1', true);
    		break;
    	case "vndr_seq":
    		ComOpenPopup('/opuscntr/COM_COM_0007.do', 900, 550, 'setVndrCd', '1,0,1', true, false); 		
    		break;
    		
    	case "trd_cd":
    		ComOpenPopup('/opuscntr/COM_COM_0012.do', 900, 550, 'setTrdCd', '1,0,0,1,1,1,1,1', true);	
    		break;
    		
    	default:
    		break;
   	
    	}	
    }
    
    // ↓ ===========================================    Set value for sheet from pop-up   ==========================================
    
    /**
     * Set value to customer code from pop-up
     * 
     * @param aryPopupData
     */
    function setCustCd(aryPopupData){
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_cnt_cd", aryPopupData[0][2]);
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_seq",    aryPopupData[0][3]);
    }
    
    /**
     * Set value to vendor code from pop-up. 
     * 
     * @param aryPopupData
     */
    function setVndrCd(aryPopupData){
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "vndr_seq", aryPopupData[0][2]);
    }
    
    /**
     * Set value to trade from pop-up
     * @param aryPopupData
     */
    function setTrdCd(aryPopupData){
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "trd_cd", aryPopupData[0][3]);
    } 

    /**
     * Set value to partner from pop-up
     * 
     * @param aryPopupData
     */
    function setCrrCd(aryPopupData){
    	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "jo_crr_cd", aryPopupData[0][3]);
    }
    
    // ↑ ===========================================    Set value for sheet from pop-up   ==========================================

    /**
     * 
     * @param Index
     * @param Code
     * @param Checked
     */
    function s_carrier_OnCheckClick(Index, Code, Checked) {
        
        if (Checked == 'ALL') {
            let numComboCarrier = s_carrier.GetItemCount()
            for (let i = 1; i < numComboCarrier - 1; i++) {
            	s_carrier.SetItemCheck(i, false);
            }
        } else{
        	let isChecked = s_carrier.GetItemCheck(Code);
        	if(isChecked){
        		s_carrier.SetItemCheck(0, false);
        		s_carrier.SetItemCheck(Code,true);
        	}
        }
    }
    
    
    /**
     * Handling event after save.
     * 
     * @param Code
     * @param Msg
     */
    function sheetCarrier_OnSaveEnd(sheetObj, Code, Msg){
    	ComOpenWait(false);
    	if (Code >= 0){
    		doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
    	}
    	else{
    		ComShowCodeMessage('COM130103', sheetObjects[0].id);
    		var dupData = sheetObj.FindStatusRow("I");
    		var aRows = dupData.split(';');
    		for (var i = 0; i < aRows.length; i++){
    			if (Msg.includes(sheetObj.GetCellValue(aRows[i],"jo_crr_cd")) &&
    					Msg.includes(sheetObj.GetCellValue(aRows[i],"rlane_cd"))){
    				sheetObj.SetRowBackColor(aRows[i],"#FF6666");
    			}
    		}
    	}
    }
    
    /**
     * Handling event after search.
     * 
     * @param sheetObj
     * @param Code
     * @param Msg
     * @param StCode
     * @param StMsg
     */
    function sheetCarrier_OnSearchEnd() { 
    	ComOpenWait(false);
    }
