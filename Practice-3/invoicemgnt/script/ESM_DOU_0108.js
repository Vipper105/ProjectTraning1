/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.js
*@FileTitle : Invoice Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.02
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.02 
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

/**
 * @extends 
 * @class ESM_DOU_0108 : ESM_DOU_0108 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
 */
function ESM_DOU_0108() {
    this.processButtonClick = tprocessButtonClick;
    this.setSheetObject = setSheetObject;
    this.loadPage = loadPage;
    this.initSheet = initSheet;
    this.initControl = initControl;
    this.doActionIBSheet = doActionIBSheet;
    this.setTabObject = setTabObject;
    this.validateForm = validateForm;
}

/* 개발자 작업	*/

var sheetObjects = new Array();
var sheetCnt = 0;

var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";

var tabObjects = new Array();
var tabCnt = 0;
var beforetab = 1;

var isOK = false;

/**
 * body.onload 이벤트에서 호출되는 함수로 페이지 로드완료 후 선처리해야할 기능을 추가한다. <br>
 * HTML컨트롤의 각종 이벤트와 IBSheet, IBTab 등을 초기화 한다. <br>
 **/
function loadPage() {
    // Initialize MultiCombobox
    for (var j = 0; j < comboObjects.length; j++) {
        initCombo(comboObjects[j], j + 1);
    }

    // Initialize default screen
    initializeDefault();
    
   

    for (var i = 0; i < sheetObjects.length; i++) {
        ComConfigSheet(sheetObjects[i]);
        initSheet(sheetObjects[i], i + 1);
        ComEndConfigSheet(sheetObjects[i]);
    }

    
    // Initialize tab
    initializeTab();

    // Initialize periods
    initPeriod();
    

    // Show data when loading page
    for(var j=0; j < sheetObjects.length; j++){
    	doActionIBSheet(sheetObjects[j], document.form, IBSEARCH);   	
    }
 
}

//버튼클릭이벤트를 받아 처리하는 이벤트핸들러 정의 */
document.onclick = processButtonClick;

// 버튼 네임으로 구분하여 프로세스를 분기처리하는 이벤트핸들러 */
function processButtonClick() {

    var sheetObject1 = sheetObjects[0];
    var sheetObject2 = sheetObjects[1];
    var formObj = document.form;


    try {
        var srcName = ComGetEvent("name");
        if (srcName == null) {
            return;
        }

        switch (srcName) {
            case "btn_datefrom_down":
                addMonth(formObj.acct_yrmon_from, -1);
                yearmonth_onchange();
                break;

            case "btn_datefrom_up":
                addMonth(formObj.acct_yrmon_from, 1);
                excuteCheck();
                yearmonth_onchange();
                break;

            case "btn_dateto_down":
                addMonth(formObj.acct_yrmon_to, -1);
                excuteCheck();
                yearmonth_onchange();
                break;

            case "btn_dateto_up":
                addMonth(formObj.acct_yrmon_to, 1);
                yearmonth_onchange();
                break;

            case "btn_Retrieve":

                // check if over three month
                if (isOverThreeMonth()) {
                    if (!isOK) {
                        if (confirm("Year Month over 3 months, do you realy want to get data?")) {
                            return;
                        } else {
                            isOK = true;
                        }
                    }
                }

                doActionIBSheet(sheetObject1, formObj, IBSEARCH);
                doActionIBSheet(sheetObject2, formObj, IBSEARCH);
                break;

            case "btn_New":
                resetForm(formObj);
                break;

            case "btn_DownExcel":
                doActionIBSheet(sheetObject1, formObj, IBDOWNEXCEL);
                break;
            case "btn_Down":
            	doActionIBSheet(sheetObject1, formObj, IBDOWNEXCEL);
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
 * Register the Tab Object created on the page in the tabObjects array.<br>
 * The tabObjects array is defined as a global global variable at the top, and this function is automatically called <br>
 * when the Tab Object is created by the {@link CoObject # ComTabObject} function.
 * 
 * @param tab_obj  Tab Object ** /
 * 
 */
function setTabObject(tab_obj) {
    tabObjects[tabCnt++] = tab_obj;
}


// ↑ ===========================================    Set array for components   ==========================================



// ↓ ===========================================    Initialize components   ==========================================
/**
 *  Initialization tab
 * 
 **/
function initializeTab(){
	// Initialize tab
	for (var k = 0; k < tabObjects.length; k++) {
		initTab(tabObjects[k], k + 1);
		// Select tab which default index value
		let tabIndex = 0;
		tabObjects[k].SetSelectedIndex(tabIndex);
	}
	
}

/**
 *  Initialize default screen
 * 
 **/
function initializeDefault(){
	 s_partner.SetSelectIndex(0);
	 acct_yrmon_from.disabled = true;
	 acct_yrmon_to.disabled = true;
	 s_lane.SetEnable(false);
	 s_trade.SetEnable(false);
}


/**
 * Tab Initialization 
 * 
 * {@link #loadPage} Call this function when load page in order to initialize tab
 * @param  tabObj       tab object
 * @param  tabNo        No. of tab
 **/
function initTab(tabObj, tabNo) {
    switch (tabNo) {
        case 1:
            with (tabObj) {
                var cnt = 0;
                InsertItem("Summary", "");
                InsertItem("Details", "");
            }
            break;
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
            var comboItems = partnersCombo.split("|");
            addComboItem(comboObj, comboItems);

            break;
    }
}

/**
 * 페이지에 있는 IBSheet Object를 초기화 한다. <br>
 * IBSheet가 여러개일 경우 개수만큼 case를 추가하여 IBSheet 초기화 모듈을 구성한다. <br>
 * {@link #loadPage}함수에서 이 함수를 호출하여 IBSheet Object를 초기화 한다. <br>
 * @param {ibsheet} sheetObj    IBSheet Object
 * @param {int}     sheetNo     sheetObjects 배열에서 순번
 **/
function initSheet(sheetObj, sheetNo) {
    var cnt = 0;
    switch (sheetObj.id) {
        case "sheetSummary":
            with (sheetObj) {
                var HeadTitle1 = "|Partner|Lane|Invoice No|Slip No|Approval|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider"
                var HeadTitle2 = "|Partner|Lane|Invoice No|Slip No|Approval|Curr.|Revenue|Expense|Code|Name"
                var prefix = "sheet1_";

                SetConfig({ SearchMode: 1, MergeSheet: 5, Page: 20, FrozenCol: 0, DataRowMerge: 0 });

                var info = { Sort: 1, ColMove: 1, HeaderCheck: 0, ColResize: 1 };
                var headers = [{ Text: HeadTitle1, Align: "Center" },
                { Text: HeadTitle2, Align: "Center" }];
                InitHeaders(headers, info);

                var cols = [
                    { Type: "Status", Hidden: 1, Width: 50, Align: "Center", SaveName: prefix + "ibflag", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 70, Align: "Center", SaveName: prefix + "jo_crr_cd", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 60, Align: "Center", SaveName: prefix + "rlane_cd", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 150, Align: "Left", SaveName: prefix + "inv_no", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 180, Align: "Left", SaveName: prefix + "csr_no", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Combo", Hidden: 0, Width: 100, Align: "Center", SaveName: prefix + "apro_flg", KeyField: 0, UpdateEdit: 0, InsertEdit: 0, ComboCode: "Y|N", ComboText: "Y|N" },
                    { Type: "Text", Hidden: 0, Width: 120, Align: "Center", SaveName: prefix + "locl_curr_cd", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 150, Align: "Right", SaveName: prefix + "inv_rev_act_amt", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 150, Align: "Center", SaveName: prefix + "inv_exp_act_amt", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                    { Type: "Text", Hidden: 0, Width: 150, Align: "Left", SaveName: prefix + "prnr_ref_no", KeyField: 0, UpdateEdit: 0, InsertEdit: 0, ComboCode: "Y|N", ComboText: "Y|N" },
                    { Type: "Text", Hidden: 0, Width: 80, Align: "Center", SaveName: prefix + "cust_vndr_eng_mn", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 }
                ];

                InitColumns(cols);
                // SetEditable(1);
                // SetAutoSumPosition(1);
                resizeSheet();
            }
            break;

        case "sheetDetails":
            with (sheetObj) {

                var HeadTitle1 = "|Partner|Lane|Invoice No|Slip No|Approval|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
                var HeadTitle2 = "|Partner|Lane|Invoice No|Slip No|Approval|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
                var prefix = "sheet2_";

                SetConfig({ SearchMode: 2, MergeSheet: 5, Page: 20, FrozenCol: 0, DataRowMerge: 0 });

                var info = { Sort: 1, ColMove: 1, HeaderCheck: 0, ColResize: 1 };
                var headers = [{ Text: HeadTitle1, Align: "Center" },
                { Text: HeadTitle2, Align: "Center" }];
                InitHeaders(headers, info);

                var cols = [{ Type: "Status", Hidden: 1, Width: 50, Align: "Center", SaveName: prefix + "ibflag", KeyField: 0 },
                { Type: "Text", Hidden: 0, Width: 70, Align: "Center", SaveName: prefix + "jo_crr_cd", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 60, Align: "Center", SaveName: prefix + "rlane_cd", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 150, Align: "Left", SaveName: prefix + "inv_no", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 180, Align: "Left", SaveName: prefix + "csr_no", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Combo", Hidden: 0, Width: 100, Align: "Center", SaveName: prefix + "apro_flg", KeyField: 0, UpdateEdit: 0, InsertEdit: 0, ComboCode: "Y|N", ComboText: "Y|N" },

                { Type: "Text", Hidden: 0, Width: 90, Align: "Center", SaveName: prefix + "rev_exp", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 90, Align: "Center", SaveName: prefix + "item", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },

                { Type: "Text", Hidden: 0, Width: 120, Align: "Center", SaveName: prefix + "locl_curr_cd", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 150, Align: "Right", SaveName: prefix + "inv_rev_act_amt", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 150, Align: "Center", SaveName: prefix + "inv_exp_act_amt", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 },
                { Type: "Text", Hidden: 0, Width: 150, Align: "Left", SaveName: prefix + "prnr_ref_no", KeyField: 0, UpdateEdit: 0, InsertEdit: 0, ComboCode: "Y|N", ComboText: "Y|N" },
                { Type: "Text", Hidden: 0, Width: 50, Align: "Center", SaveName: prefix + "cust_vndr_eng_mn", KeyField: 0, UpdateEdit: 0, InsertEdit: 0 }
                ];

                InitColumns(cols);
                // SetEditable(1);
                //  SetAutoSumPosition(1);
                SetWaitImageVisible(0);
                resizeSheet();
            }
            break;
    }
}

// ↑ ===========================================    Initialize components   ==========================================

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
}

/**
 * adjust dimension of IBsheet following dimension of browser.
 * 
 */
function resizeSheet() {
    ComResizeSheet(sheetObjects[0]);
    ComResizeSheet(sheetObjects[1]);
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
            if (sheetObj.id == "sheetSummary") {
                // ComOpenWait(true);
                formObj.f_cmd.value = SEARCH;

                let arr1 = new Array("sheet1_", "");
                let sParam1 = FormQueryString(formObj) + "&" + ComGetPrefixParam(arr1);
                let sXml1 = sheetObj.GetSearchData("ESM_DOU_0108GS.do", sParam1);
                if (sXml1.length > 0) {
                    sheetObj.LoadSearchData(sXml1, { Sync: 1 });
                }
            }

            else if (sheetObj.id == "sheetDetails") {
                // ComOpenWait(true);
                formObj.f_cmd.value = SEARCH02;
                let arr2 = new Array("sheet2_", "");
                let sParam2 = FormQueryString(formObj) + "&" + ComGetPrefixParam(arr2);

                let sXml2 = sheetObj.GetSearchData("ESM_DOU_0108GS.do", sParam2);
                if (sXml2.length > 0) {
                    sheetObj.LoadSearchData(sXml2, { Sync: 1 });
                }
            }

            break;

        case IBDOWNEXCEL:
            if (sheetObj.RowCount() < 1) {
                ComShowCodeMessage("COM132501");
            } else {
                sheetObjects[0].Down2ExcelBuffer(true);
                // makeHiddenSkipCol cột nào có hidden = 1 thì khi lưu file excel sẽ không có cột đó 
                for (let i = 0; i < sheetObjects.length; i++) {
                    sheetObjects[i].Down2Excel({ FileName: "excel2", SheetName: "sheet" + (i + 1), DownCols: makeHiddenSkipCol(sheetObjects[i]), SheetDesign: 1, Merge: 1 });
                }
                sheetObjects[0].Down2ExcelBuffer(false);
            }

            break;
    }

}

/**
 * Validate periods is over three months
 * 
 */
function isOverThreeMonth() {
    var formObj = document.form;
    var fromDate = formObj.acct_yrmon_from.value.replaceStr("-", "") + "01";
    var toDate = formObj.acct_yrmon_to.value.replaceStr("-", "") + "01";

    if (ComGetDaysBetween(fromDate, toDate) > 88) {
        return true;
    }

    return false;

}

// ================      handle event tab  ==================

/**
 * Event for change tab
 * 
 * @param tabObj
 * @param nItem
 **/
function tab1_OnChange(tabObj, nItem) {
    var objs = document.all.item("tabLayer");
    objs[nItem].style.display = "Inline";
    //--------------- this is important! --------------------------//
    for (var i = 0; i < objs.length; i++) {
        if (i != nItem) {
            objs[i].style.display = "none";
            objs[beforetab].style.zIndex = objs[nItem].style.zIndex - 1;
        }
    }
    //------------------------------------------------------//
    beforetab = nItem;
    resizeSheet();
}

//================     handle event tab  ==================

/**
 * Event for reset function
 * 
 * @param formObj
 **/
function resetForm(formObj) {
    sheetObjects[0].RemoveAll();
    sheetObjects[1].RemoveAll();
    formObj.reset();
    s_partner.SetSelectIndex(0);
    initPeriod();
}

/**
 * Event for reset function
 * 
 **/
function s_lane_OnChange() {
    s_trade.SetEnable(true);
    getTradeComboData();
}

/**
 * When click change month data in sheet will be removed
 * 
 **/
function yearmonth_onchange() {
    sheetObjects[0].RemoveAll();
    sheetObjects[1].RemoveAll();
}

// ↓ ===========================================    Create Date combobox    ==========================================

/**
 * create month
 * 
 * @param obj
 * @param month
 **/
function addMonth(obj, month) {
    if (obj.value != "") {
        obj.value = ComGetDateAdd(obj.value + "-01", "M", month).substr(0, 7);
    }
}

/**
 * Check FROM date is bigger than TO date
 * 
 **/
function checkCondition() {
    var formObj = document.form;
    var fromDate = formObj.acct_yrmon_from.value.replaceStr("-", "") + "01";
    var toDate = formObj.acct_yrmon_to.value.replaceStr("-", "") + "01";
    if (ComGetDaysBetween(fromDate, toDate) <= 0)
        return false;
    return true;
}

/**
 * Excute check condition 
 * 
 **/
function excuteCheck() {
    if (!checkCondition()) {
        initPeriod();
    }
}

/**
 * Initialize date
 * 
 **/
function initPeriod() {
    var formObj = document.form;
    var ymTo = ComGetNowInfo("ym", "-");
    formObj.acct_yrmon_to.value = ymTo;
    var ymFrom = ComGetDateAdd(formObj.acct_yrmon_to.value + "-01", "M", -2);
    formObj.acct_yrmon_from.value = GetDateFormat(ymFrom, "ym");
}

/**
 * Get format date
 * 
 **/
function GetDateFormat(obj, sFormat) {
    var sVal = String(getArgValue(obj));
    sVal = sVal.replace(/\/|\-|\.|\:|\ /g, "");
    if (ComIsEmpty(sVal)) return "";

    var retValue = "";
    switch (sFormat) {
        case "ym":
            retValue = sVal.substring(0, 6);
            break;
    }
    retValue = ComGetMaskedValue(retValue, sFormat);
    return retValue;
}

//↑ ===========================================    Create Date combobox   ==========================================

/**
 * Event when if user double clicks on 1 Invoice, then system automatically change to detail tab 
 * and select to the right row for user checking.
 * @param {ibsheet} sheetObj    IBSheet Object
 * @param {ibsheet} Row			Row of IBSheet Object
 * @param {ibsheet} Col			Col of IBSheet Object
 * 
 **/
function sheetSummary_OnDblClick(sheetObj, Row, Col) {
    var dataRows = sheetSummary.GetSelectionRows(",");;
    var inv_no = sheetObj.GetCellValue(Row, "sheet1_inv_no");
    tab1_OnChange(tabObjects[0], 1);
    
    var x = sheetDetails.FindText(3, inv_no);
    sheetObjects[1].SetSelectRow(x);
    
    // change to tab details
    tabObjects[0].SetSelectedIndex(1);
}


//↓ ===========================================    Generate combobox   ==========================================

/**
 * Generate lane combobox
 * 
 **/
function getLaneComboData() {
//    s_lane.RemoveAll();
//    s_trade.RemoveAll();
    var formObj = document.form;
    formObj.f_cmd.value = SEARCH03;
    // var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do",FormQueryString(document.form));
    let arr1 = new Array("sheet1_", "");
    let sParam1 = FormQueryString(formObj) + "&" + ComGetPrefixParam(arr1);
    
    // sParam1 = replaceStr(sParam1);
    
    let sXml1 = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", sParam1);

    lanes = ComGetEtcData(sXml1, "lanes");
    generateDataCombo(comboObjects[1], lanes);	 
}

/**
 * Generate trade combobox
 * 
 **/
function getTradeComboData() {
    var formObj = document.form;
    formObj.f_cmd.value = SEARCH04;
    let arr1 = new Array("sheet1_", "");
    let sParam1 = FormQueryString(formObj) + "&" + ComGetPrefixParam(arr1);
    let sXml1 = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", sParam1);

    trades = ComGetEtcData(sXml1, "trades");
    generateDataCombo(comboObjects[2], trades);

}

/**
 * Insert item into combobox
 * 
 **/
function generateDataCombo(comboObj, dataStr) {
    if (typeof dataStr !== 'undefined') {
        if (dataStr.indexOf("|") != -1) {
            var data = dataStr.split("|");
            for (var i = 0; i < data.length; i++) {
                comboObj.InsertItem(-1, data[i], data[i]);
            }
        }
        if (dataStr.length > 0 && dataStr.indexOf("|") == -1) {
            comboObj.InsertItem(-1, dataStr, dataStr);
        }
    }
}

/**
 * Event when click on items of combobox Partner
 * 
 */
function s_partner_OnCheckClick(Index, Code, Checked) {   
	let numComboPartner = s_partner.GetItemCount()
    if (Checked == 'ALL') {
        for (let i = 1; i < numComboPartner - 1; i++) {
        	s_partner.SetItemCheck(i, false);
        }
        
        // get all Lane, because when initialize ALL is checked so that it's NOT get all lane 
        // => We need get all Lane when initilize
//        getLaneComboData();
        
        
    } else{
    	let isChecked = s_partner.GetItemCheck(Code);
    	if(isChecked){
    		s_partner.SetItemCheck('ALL', false); 
    		s_partner.SetItemCheck(Code, true); 
    	}
    }

    // Search combobox Partner
    var formObj = document.form;
    formObj.f_cmd.value = SEARCH;

    let arr1 = new Array("sheet1_", "");
    let sParam1 = FormQueryString(formObj) + "&" + ComGetPrefixParam(arr1);
    
   // replace value when click ALL
    // sParam1 = replaceStr(sParam1);
    
    let sXml1 = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", sParam1);
    if (sXml1.length > 0) {
        sheetObjects[0].LoadSearchData(sXml1, { Sync: 1 });
    }

    s_lane.SetEnable(true);
    getLaneComboData();
    
 
}

// ↑ ===========================================    Generate combobox   ==========================================

function replaceStr(sParam1) {
	//let sParamList[] = sParam1.split("&");
	let findStr = 's_partner=ALL';
//	for (let i = 0; i < sParamList.length; i++) {
//		if (sParamList[i] == findStr) {
//			
//		}
//	}
//	let comboItems = partnersCombo.split("|");
//	for(let i= 1 ;i<comboItems.length;i++){
//		comboItems.get(i);
//	}
	
	//let resultStr  = '';
	if(sParam1.indexOf(findStr) > -1){
		let comboItems = partnersCombo.replaceAll("|","%2");
		resultStr = sParam1.replaceAll("ALL", comboItems);
	}
	
	return resultStr;
}


// ↓ ===========================================    Handle event Onsearch End   ==========================================

/**
 * Handling event after searching on sheetSummary
 * 
 **/
function sheetSummary_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
    ComOpenWait(false);
    let prefix = 'sheet1_';
    var totalRow = sheetObj.RowCount();
    for (var i = 1; i <= totalRow + 1; i++) {
        if (sheetObj.GetCellValue(i, prefix + "jo_crr_cd") == '') {
            if (sheetObj.GetCellValue(i, prefix + "inv_no") !== '') {
                sheetObj.SetRowFontColor(i, "#FF9F33");
                sheetObj.SetRangeFontBold(i, 1, i, 10, 1);
                sheetObj.SetCellValue(i, prefix + "inv_no", "");
            }
            else if (sheetObj.GetCellValue(i, prefix + "inv_no") == '') {
                sheetObj.SetRowBackColor(i, "#FF9F33");
                sheetObj.SetRangeFontBold(i, 1, i, 10, 1);
            }
        }
    }
}

/**
 * Handling event after searching on sheetDetails 
 * 
 **/
function sheetDetails_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
    ComOpenWait(false);
    let prefix = 'sheet2_';
    var totalRow = sheetObj.RowCount();
    for (var i = 1; i <= totalRow + 1; i++) {
        if (sheetObj.GetCellValue(i, prefix + "jo_crr_cd") == '') {
            if (sheetObj.GetCellValue(i, prefix + "inv_no") !== '') {
                sheetObj.SetRowFontColor(i, "#ff3300");
                sheetObj.SetRangeFontBold(i, 1, i, 12, 1);
                sheetObj.SetCellValue(i, prefix + "inv_no", "");
            }
            else if (sheetObj.GetCellValue(i, prefix + "inv_no") == '') {
                sheetObj.SetRowBackColor(i, "#ff9933");
                sheetObj.SetRangeFontBold(i, 1, i, 12, 1);
            }

        }
    }
}

// ↑ ===========================================    Handle event Onsearch End   ==========================================

/* 개발자 작업  끝 */
