package com.copa.Util;

public class FlightSearchPageObjects {
	
	public static String OriginfirstXpath="//div[@ng-form='availabilityForm']/div[1]//div[contains(@class,'origin')]//input";
	public static String loadXpath="//div[contains(@class,'loading-message')]";
	public static String DestinationfirstXpath="//div[@ng-form='availabilityForm']/div[1]//div[contains(@class,'destn')]//input";
	public static String DateXpath="//div[@ng-form='availabilityForm']/div[1]//div[contains(@class,'datepicker')]//input";
	public static String TimeXpath="//div[@ng-form='availabilityForm']/div[1]//md-select[@ng-model='segment.departureTime']";
	public static String RoundtripXpath="//span[@translate='pssgui.return.flight']";     //----------"//md-checkbox[@type='checkbox']/div[2]";
	public static String DestinationSecondXpath="//div[@ng-form='availabilityForm']/div[2]//div[contains(@class,'datepicker')]//input";
	public static String AduldnameXpath="//label[contains(text(),'Adult')]/following-sibling::input";
	public static String ChildXpath="//label[contains(text(),'Child')]/following-sibling::input";
	public static String sInfwithoutseatXpath="//label[contains(text(),'Infant without Seat')]/following-sibling::input";
	public static String sInfwithseatXpath="//label[contains(text(),'Infant with Seat')]/following-sibling::input";
	public static String searchbuttonXpath="//button[contains(text(),'Search')]";
	public static String viewXpath="//span[contains(text(),'View')]";
	public static String numberoftripsXpath="//pssgui-tabs[@action='airport']//div[@ng-repeat='tab in tabsCtrl.model.tabs']";
	public static String listofallflightsXpath="//span[contains(text(),'View')]//preceding::div[@class='ng-binding'][1]";
	public static String lselectedflightnoXpath="//div[div[div[div[span[span[i[@class='icon-arrow-up']]]]]]]//div[contains(@class,'flight-no')]";
	public static String lavbclassXpath="//div[div[div[div[div[span[span[i[@class='icon-arrow-up']]]]]]]]/div[1]//span[contains(@class,'active-state')]";
	public static String QuoteButtonXpath="//button[@aria-label='quote']";
	public static String QuoteOptionXpath="//div[text()='Quote Options']";
	public static String SlectSegmentXpath="//md-checkbox[@ng-model='flightResult.isAllChecked'][div[div[@class='md-icon']]]";
	public static String PriceOptionXpath="//md-select[contains(@ng-model,'pricingOptionType')]";
	public static String BestBuyXpath="//div[contains(text(),'Price as best buy')]";
	public static String UpsellXpath="//md-option/div[contains(text(),'Upsell')]";  
	
	public static String PaxReductionXpath="//span[text()='Passenger Reduction Type']/preceding::i[1]";
	public static String PaxReductiontypeXpath="//input[@name='paxType']";
	
	public static String FareBaseXpath="//span[text()='Fare Basis']/preceding::i[1]";
	public static String FareBaseCodeXpath="//input[@aria-label='code']";
	public static String FareBaseSegXpath="//div[@action='fare-basis']//md-checkbox[@aria-label='coupon-check']";
	
	public static String Penaltywaiverp="//span[contains(text(),'Penalty Waiver')]/preceding::i[1]";
	public static String Reqticdatep="//span[contains(text(),'Requested Ticketing Date')]/preceding::i[1]";
	public static String Reqticdate_datep="//div[@class='md-datepicker-input-container']//input[@class='md-datepicker-input']";
	
	public static String PaxReductionSeniorCitizenXpath="//div[contains(@class,'md-select-menu-container md-active')]//md-option[div[span[contains(text(),'Senior Citizen') ]]]//parent::md-option[contains(@value,'SRC')]";
    public static String addsegmentXpath="//span[contains(text(),'Add Segment')]";
  //  public static String paxreductionXpath="//div[@action='passenger-reduction-type']/div[1]//md-select";
    public static String paxreductionageXpath="//input[@ng-model='paxType.Age']";
   // public static String paxreductionstudentXpath="//div[contains(@class,'md-select-menu-container md-active')]//md-option[div[span[contains(text(),'Student')]]]";
  //  public static String paxreductionsemanXpath="//div[contains(@class,'md-select-menu-container md-active')]//md-option[div[span[contains(text(),'Seaman')]]]";
  //  public static String paxreductionAirline_staff_standbyXpath="//div[contains(@class,'md-select-menu-container md-active')]//md-option[div[span[contains(text(),'Airline staff standby')]]]";
    public static String cancelbuttonXpath="//button[contains(text(),'Cancel')]";
    public static String nextbuttonXpath="//button[contains(text(),'Next')]";
    public static String TicketXpath="//div[text()='Tickets']/parent::div";
    
    public static String QuotePagexpath="//div[contains(text(),'Price Quote')]";
    public static String BookbuttonXpath="//button[@aria-label='book']";
    public static String farepriceXpath="//pssgui-tabs[contains(@on-tab-change,'farePrice')]//div[@role='button']/div";
    
    public static String traveldetailsXpath="//div[contains(text(),'Enter Passenger Details')]";
    public static String surnameXpath="//input[@name='surname']";
    public static String firstnmXpath="//input[@name='firstName']";
    public static String ffnumberXpath="//input[@aria-label='ffNumber']";
    public static String validateffpXpath="//button[contains(text(),'Validate FF')]";
    public static String addffpXpath="//button[contains(text(),'Add')]";
    public static String datepickerXpath="//input[@class='md-datepicker-input']";
    public static String traverlwithXpath="//md-select[contains(@ng-model,'travelsWith')]";
  //  public static String selectfirsttraverxpath="//div[contains(@id,'select_container') and contains(@class,'md-active')]//md-option[@aria-hidden='false'][1] /div[1]";
    
  //  public static String selectfirsttraverxpath="//div[contains(@id,'select_container') and contains(@class,'md-active')]//md-option[@aria-checked='false']";
    public static String selectfirsttraverxpath="//div[contains(@id,'select_container') and contains(@class,'md-active')]//md-option[@aria-hidden='false'][1]";
    
    public static String gengerXpath="//md-select[md-select-value[span [contains(text(),'Gender')] or span[div[div[contains(text(),'Male') or contains(text(),'Female')]]]]]";
    public static String emailaddresXpath="//input[@aria-label='Email Address']";
    public static String nationalityXpath="//input[@aria-label='Nationality']";
    public static String phonenumberselecterXpath="//div[contains(@ng-repeat,'PhoneNumbers')]//md-select";
    public static String selectfirstphonetyp1Xpath="//div[@aria-hidden='false']//md-option[div[text()='Agency Phone']]";
    public static String countrycodeXpath="//div[contains(@ng-repeat,'PhoneNumbers')]//input[@name='countryCode']";
    public static String selectcountryXpath="//md-virtual-repeat-container[@aria-hidden='false']//span[text()='US']";
    public static String phonenumberXpath="//div[contains(@ng-repeat,'PhoneNumbers')]//input[contains(@aria-label,'Phone Number')]";
    public static String emergencynameXpath="//div[@action='emergency-contact']//input[@name='EmergencyName']";   
    public static String emergencyphoneXpath="//div[@action='emergency-contact']//input[@name='EmergencyPhone']";
    
    public static String phonetypeXpath_EmercontDet="//div[@action='emergency-contact']/following-sibling::div//md-select[@aria-label='Phone Type']";
    public static String selectfirstphonetypeXpath_EmercontDet="//div[@aria-hidden='false']//md-option[div[text()='Agency Phone']]";
    public static String countrycodeselectXpath_EmercontDet="//div[@action='emergency-contact']/following-sibling::div//input[@name='countryCode']";
    public static String phonenumberxpath_EmercontDet="//div[@action='emergency-contact']/following-sibling::div//input[@name='Phone Number']";
    
    public static String phonetypeXpath="//div[@action='emergency-contact']//md-select[@aria-label='Phone Type']";
    public static String selectfirstphonetypeXpath="//div[@aria-hidden='false']//md-option[div[text()='Agency Phone']]";
    public static String countrycodeselectXpath="//div[@action='emergency-contact']//input[@name='countryCode']";
    public static String phonenumberxpath="//div[@action='emergency-contact']//input[@name='Phone Number']";
    
    public static String EditBookingFeeXpath="//i[@ng-click='quoteDetail.editService(fee)']";
    public static String PaxCountXpath="//label[text()='Passengers']/following-sibling::span";
    public static String INFPaxCountXpath="//label[text()='INF']/following-sibling::span";
    
    public static String passangerTabXpath="//div[contains(@class,'tab') and div[contains(text(),'Passengers')]]";
    
    public static String Creditcard_ATO_CTO_Xpath="//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Credit Card')]]";
    public static String Debitcard_ATO_CTO_Xpath="//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Debit Card')]]";
    public static String Credit_Debitcard_CC_Xpath="//div[@aria-hidden='false' and contains(@class,'md-active')]//md-option[div[contains(text(),'Credit')]]";
    public static String CardNumber_Xpath="//div[contains(@class,'amount-container') and @aria-hidden='false']//input[contains(@aria-label,'Card Number')]";
    public static String KTN_Xpath="//input[@aria-label='KnownTraveler']";
    
    public static String remarks_Xpath="//div[contains(text(), 'Remarks')]";
    
    public static String PriceDate="//span[text()='Pricing by date']/preceding::i[1]";
   	public static String PriceDate_Date="//div[@class='md-datepicker-input-container']//input[@class='md-datepicker-input']";
   	public static String areacodeXpath="//div[@action='emergency-contact']/..//input[@name='areacode']";
   	
   	public static String clickUSpopuXpath="//md-virtual-repeat-container[@aria-hidden='false']//li[1]";
    
    
	

}
