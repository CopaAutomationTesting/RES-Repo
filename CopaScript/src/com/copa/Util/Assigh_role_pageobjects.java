package com.copa.Util;

public class Assigh_role_pageobjects {
	

	public static String UserProvIcon="//figure//div[contains(text(),'User Provisioning Tool')]";
	public static String UserIdField="//input[@aria-label='userId']";
	public static String SearchButton="//button[contains(text(),'Search')]";
	public static String AddCityButton="//button[@ng-click='linkUser.addCity()' and contains(text(),'Add')]";
	public static String linkUserButton="//i[@ng-click='linkUser.addRoles();linkUser.reomoveRoles()']";
	public static String AddedCityListXpath="//div/md-content[@class='md-content-display _md']/md-radio-group/div/div/md-radio-button/div[2]/span";
	public static String EnterCityFieldXpath="//input[@name='location']";
	public static String SelectCityFromDropDownXpath="//ul[@ng-class='::menuClass']/li[1]";
	public static String PrivateFareCodeXpath="//md-input-container/label[contains(text(),'Private Fare Code')]";
	public static String AgentSineField="//md-input-container/label[contains(text(),'Agent Sine')]";
	public static String OkButtonXpath="//button[text()='OK']";
	public static String SaveButtonXpath="//button[@ng-click='linkUser.saveUser()' and contains(text(),'Save')]";

}
