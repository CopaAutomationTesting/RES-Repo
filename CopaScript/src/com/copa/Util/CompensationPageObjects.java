package com.copa.Util;



public class CompensationPageObjects {
	public static String ReservationXpath ="//md-menu[@ng-model='layout.pssguiModules.module']/button";
	public static String CompensationXpath = "//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Travel Compensation')]";
	public static String SearchXpath = "//div[@translate='cm.travel.search']";
	public static String FlightNoXpath = "//input[@name='FlightNo']";
	public static String FromLocXpath = "//input[@name='location' and @aria-label='From']";
	public static String SearchPaxListIcon = "//md-select[@aria-label='Select Passenger List']";
	public static String AllPaxListXpath = "//div[contains(text(),'All Passenger List')]";
	public static String CompensationList = "//div[contains(text(),'Compensation List')]";
	public static String SearchDate = "//input[@class='md-datepicker-input']";
	public static String DateXpath = "//div[3]/div[1]/pssgui-date-time/div/md-datepicker/div/input[@class='md-datepicker-input']";
	public static String OrderIdXpath = "//input[@name='Order']";
	public static String TicketNoXpath = "//input[@name='Etkt']";
	public static String FFPgmListIcon = "//md-select[@name='FrequentFlyer']";
	public static String FFNumberXpath = "//input[@name='Fflyer']";
	public static String SearchBtnXpath = "//button[@ng-model='compensationSearch.model.search']";
	public static String AddPaxDataXpath = "//button[@ng-model='compensationSearch.model.addorder']";
	public static String ReportXpath = "//div[@translate='cm.report.search']";
	public static String ToLocXpath = "//input[@name='location' and @aria-label='To']";
	public static String GivenName = "//input[@ng-model='addOrder.model.PaxFirstNm']";
	public static String Surname = "//input[@ng-model='addOrder.model.PaxLastNm']";
	public static String PaxTier = "//input[@name='Tier']";
	public static String PaxCabin = "//input[@name='Class']";
	public static String PaxCheckbox = "//md-checkbox[@ng-model='reports.selected']";
	public static String RowItem = "//div[contains(@ng-repeat,'reports in ')]";
	public static String CancelBtn = "//button[@translate='cm.cancel']";//button[@aria-label='Cancel']
	public static String ContinueBtn = "//button[@translate='cm.continue']";
	public static String SaveButton = "//button[@translate='cm.save']";
	public static String OkButton = "//button[@translate='upt.add.ok']";
	public static String CompReason = "//md-select[@ng-model='compPassengers.model.compensationReason']";
	public static String IssueCompButton = "//button[@translate='cm.issue.compensation']";
	public static String BCompPrinted = "//div[@translate='cm.compensation.printed']";
	public static String BCompNotPrinted = "//div[@translate='cm.compensation.not.printed']";
	public static String EmailLink = "//span[@ng-click='issueCompensation.addEmail(reports)']";
	public static String EmailText = "//input[@name='email']";
	public static String EmailButton = "//button[@aria-label='cm.email']";
	public static String AmountXpath = "//input[@name='Amount']";
	public static String OverrideReason = "//input[@ng-model='otherDetails.model.tempBreComp.reasons']";
	public static String CompIssuedTab = "//div[contains(text(),'Compensation Issued')]";
	public static String EMDPrintedPage = "//div[contains(text(),'EMD Printed')]";
	public static String EMDToPrintPage = "//div[contains(text(),'EMD Available for Print')]";
	public static String LoadingXpath = "//div[contains(@class,'loading-wrapper')]";
	public static String HomeXpath = "//div[@translate='pssgui.home']";
	public static String PrintXpath = "//button[@aria-label='cm.print']";
	public static String ReportSearchList = "//md-select[@aria-label='Search By']";
	public static String ReportViewBtn = "//button[@translate='cm.view']";
	public static String ReportPage = "(//div/div[@model='compensationReports.model'])[1]";
	public static String ReportDate = "//input[@class='md-datepicker-input']";
	public static String ReportArrowIcon = "//i[@class='toggle-arrow icon-forward']";//.size
	public static String ReportTktNoXpath = "//input[@ng-model='compensationSearch.model.ticketTemp']";
	public static String ReportRowData = "//div[@ng-repeat='segment in compensationReports.model.flightPaxReports']";//.getdata//updated
	public static String PrintReport = "//button[contains(text(),'Print')]";
	public static String EndorsementReport ="//div[contains(text(),'For use on routes operated by Copa Airlines')]";
	public static String EndorsementReport1 ="//div[contains(text(),'Valid 1 year after issuance, residual value remains 1 year')]";
	public static String EndorsementReport2 ="//div[contains(text(),'Non refundable')]";
	public static String EndorsementReport3 ="//div[contains(text(),'Not valid for taxes')]";
	public static String EndorsementReport4 ="//div[contains(text(),'Transferable to first degree relatives')]";
	public static String EndorsementReport5 ="//div[contains(text(),'Redeemable in local currency at the current exchange rate')]";
	public static String AgentReportXpath = "//div[contains(@class,'_md md-open-menu-container') and contains(@class,'md-active')]//button[contains(text(),'Agent Sales Report')]";
	public static String ReportAmtXpath = "//div[@class='pssgui-bold ng-binding flex pull-right'])";
	public static String InboundTab= "//div[@translate='pssgui.inbound']";
	public static String InboundList= "//div[@class='table-inbound-hover layout-column']";
	public static String ResEMDXpath= "//div[@class='pssgui-link padding-left ng-binding']";
	public static String OrderRowXpath= "//div[contains(@class,'input ng-binding flex')]";
	public static String AllOversoldListXpath = "//div[contains(text(),'Oversold List')]";
	public static String PnrFromCompenesation="//md-content/div[1]//ng-form/div[contains(@class,'passenger-list')]/div[6]";
	public static String OrderDetailsPage= "//div[@ng-repeat='reports in compensationItinerary.model.displayPaxModel']";
	public static String OrderListView= "(//div[contains(@ng-repeat,'compOrderList.model')])";
	public static String SelectAllChk= "//md-checkbox[contains(@ng-model,'model.selectAll')]";
	public static String PAXListView= "(//div[contains(@class,'passenger-list')])";
	
}

