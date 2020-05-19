package com.copa.ATOscripts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.copa.RESscripts.FlightSearch;
import com.copa.RESscripts.Login;
import com.copa.Util.FlightSearchPageObjects;
import FrameworkCode.BFrameworkQueryObjects;
import com.copa.Util.ATOflowPageObjects;

public class ISharesflow  extends FlightSearchPageObjects{
	public static String INRSA_PNR = "";
	public static String SharesPNR = "";	
	public static String Flight = "";
	public static String Orig = "";
	public static String Destn = "";
	public static String fDate = "";
	public static String cTime = "";
	public static String fTime = "";
	public static String fGate = "";
	public static String fOtrInput = "";
	public static String DlyTime = "";
	public static String Shares = "";
	public static String iPNRNo = "";
	public static String iTicketing = "";
	public static String MultiPNR = "";
	public static String iMultiClass = "";
	public static String iCheckin = "";
	public static String iPassengers = "";
	public static String iGrpPax = "";
	public static String pResField = "";
	public static String pSendBtn = "";
	public static String iMulPNR = "";
	public static boolean isNewTab = false;
	public static boolean OpenFlight = false;
	
	public static void ishare(WebDriver driver, BFrameworkQueryObjects queryObjects) throws Exception
	{	
		Shares = FlightSearch.getTrimTdata(queryObjects.getTestData("Shares"));
		String iPNRType = FlightSearch.getTrimTdata(queryObjects.getTestData("PNRType"));
		String iClass = FlightSearch.getTrimTdata(queryObjects.getTestData("Class"));
		String iFromTo = FlightSearch.getTrimTdata(queryObjects.getTestData("From_To"));
		String iDays = FlightSearch.getTrimTdata(queryObjects.getTestData("Days"));
		String iPaxName= FlightSearch.getTrimTdata(queryObjects.getTestData("PaxName"));
		String iFlightnum = FlightSearch.getTrimTdata(queryObjects.getTestData("Flightnum"));
		String iPaxCount = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxCnt"));
		String iSSR = FlightSearch.getTrimTdata(queryObjects.getTestData("SSR"));
		String iSSR_Freetext = FlightSearch.getTrimTdata(queryObjects.getTestData("SSR_Freetext"));		
		SharesPNR =FlightSearch.getTrimTdata(queryObjects.getTestData("PNRis"));
		String Validation = FlightSearch.getTrimTdata(queryObjects.getTestData("Validation"));
		fOtrInput = FlightSearch.getTrimTdata(queryObjects.getTestData("OtherInput"));
		DlyTime = FlightSearch.getTrimTdata(queryObjects.getTestData("DLYHrs"));
		String iSegCnt = FlightSearch.getTrimTdata(queryObjects.getTestData("SegmentCnt"));
		String iAvailType = FlightSearch.getTrimTdata(queryObjects.getTestData("AvailabilityType"));
		String iNxtSegDays = FlightSearch.getTrimTdata(queryObjects.getTestData("NxtSegDays"));
		String iPAXNRCode = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxNRCode"));
		String iSSRSegment = FlightSearch.getTrimTdata(queryObjects.getTestData("SSRSegments"));
		String iSSRPax = FlightSearch.getTrimTdata(queryObjects.getTestData("SSRPax"));
		iTicketing = FlightSearch.getTrimTdata(queryObjects.getTestData("Ticketing"));
		String iPaxType = FlightSearch.getTrimTdata(queryObjects.getTestData("PaxType"));
		String iChildAge = FlightSearch.getTrimTdata(queryObjects.getTestData("ChildAge"));
		iMultiClass = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiClass"));
		iCheckin = FlightSearch.getTrimTdata(queryObjects.getTestData("Checkin"));
		String iCountry = FlightSearch.getTrimTdata(queryObjects.getTestData("Country"));
		iMulPNR = FlightSearch.getTrimTdata(queryObjects.getTestData("MultiPNR"));
		String FQTVNum = FlightSearch.getTrimTdata(queryObjects.getTestData("FQTVUpdate"));
		pResField = "//pre[@id='content-wrap']";
		pSendBtn = "//input[@value='Send']";
		
		try {
			Login.MultiplePNR = "";
			Login.FLIGHTNUM = "";
			Login.PNRNUM =  "";
			if (!Validation.contains("Flifo")) {
				SharesPNR = CreatePNR(driver, queryObjects, iPNRType, iClass, iFromTo, iDays, iPaxName, iFlightnum, iPaxCount, iSSR, 
						iSSR_Freetext, iSegCnt, iAvailType, iNxtSegDays, iPAXNRCode, iSSRSegment, iSSRPax, iPaxType, iChildAge, iCountry, FQTVNum);				
			} else if (Validation.contains("Flifo")) {
				 FLIFO(driver, queryObjects, Validation, iFlightnum, iFromTo,"","","");
			}
		}catch(Exception e) {}		
				
	}
	
	public static boolean VerifyResponse(WebDriver driver, BFrameworkQueryObjects queryObjects, String eRequest, String gResponse, String Verification, String PassMsg, String FailMsg) throws IOException {
		boolean isSuccess = false;
		if (gResponse.contains("GENERAL  INFORMATION FOR:")) {
			((JavascriptExecutor)driver).executeScript("arguments[1].value = arguments[0]; ", eRequest, driver.findElement(By.name("q")));
			driver.findElement(By.xpath(pSendBtn)).click();
			if (driver.findElement(By.xpath(pResField)).getText().contains(gResponse)) {
				isSuccess = true;
			}
		} else {
			if (RequestResponse(driver, queryObjects, eRequest).contains(gResponse)) {
				isSuccess = true;
			}
		}
		if (isSuccess) {
			queryObjects.logStatus(driver, Status.PASS, Verification, PassMsg , null);
		} else {
			queryObjects.logStatus(driver, Status.FAIL, Verification, FailMsg , null);
		}
		
		return isSuccess;
	}
	
	public static String CreatePNR(WebDriver driver, BFrameworkQueryObjects queryObjects, String PNRType, String Class, String FromTo, String Days, String PaxName, String Flightnum, String PaxCount,
			String SSR, String SSR_Freetext,String SegCnt, String AvailType, String NxtSegDays, String PAXNRCode, String SSRSegment, String SSRPax, String PaxType, String ChildAge, String Country, String FQTV) throws Exception {
		
		String Req = "";String Res = ""; String ReqStr = ""; String rEnt = "";
		String SetDate = ""; String SetType = ""; String Surname = "";
		String Firstname = ""; String InfAge = "";  String SetFrmTo = "";
		
		String SptSName[] = null; String SptFName[] = null;
		String SptPNR[] = null; String SegmentCnt = ""; String SptSSR[] = null;
		String FlightSplt[] = null; String SptFlight2[] = null; String SetFlight = null;
		String FrmToSplt[] = null; String OrigDest[] = null;
		String gDays[] = null; String SptPaxType[] = null;
		
		boolean isProceed = false; int ClsCnt = 0;
		pResField = "//pre[@id='content-wrap']";
		try {
			if (PaxType.isEmpty()) {
				PaxType = "ADT";
			}
			if (ChildAge.isEmpty() && PaxType.contains("CHD")) {
				ChildAge = "08";
			}
			if (SegCnt.isEmpty()) {
				SegCnt = "1";
			}
			//Date
			int getDays=0;
			if (Days.isEmpty()) {
				Days="0";
			}
			getDays=Integer.parseInt(Days);
			//Class
			if (Class.isEmpty()) {
				Class = "Y";
			}
			if(driver.findElements(By.xpath(pResField)).size()==0) {
				IshareLogin(driver, queryObjects, Shares);
			}
			
			if (PNRType.equalsIgnoreCase("WEB")) {
				Req = "BBCMWEB";
				Res="B-IN-CM-WEB";
			} else {
				String variable=FromTo.substring(0,3);
				Req = "BBCM"+variable+""; 
				Res="B-IN-CM-"+variable+"";
			}
			if (VerifyResponse(driver, queryObjects, Req, Res, "Check the application is pointed to "+PNRType, "Location is updated", "Location is not updated")) {
				ClsCnt = Integer.parseInt(PaxCount);
				if(PNRType.equalsIgnoreCase("NRSA")) {
					SetType = "MM";
				} else if(PNRType.equalsIgnoreCase("NRPS")){
					SetType = "PNN";
				} else if(PNRType.equalsIgnoreCase("PD")){
					SetType = "PD"; ClsCnt = 0;
				}  else if(PNRType.equalsIgnoreCase("HL")){
					SetType = "LL"; ClsCnt = 0;
				} else if(PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")){
					SetType = "GNN";
				} else {
					SetType = "NN";
				}
				
				if (PaxCount.isEmpty()) {
					PaxCount = "1";
				}
				//Flight Availability
				for (int sc = 1; sc <= Integer.parseInt(SegCnt); sc++) {
					if (Flightnum.equalsIgnoreCase("SameFlight")) {
						Flightnum = Flight;
						FromTo = Orig+Destn;
					}
					if (!Flightnum.contains("-")) {
						Flightnum = Flightnum+"-";
					}
					if (!FromTo.contains("-")) {
						FromTo = FromTo+"-";
					}
					FlightSplt = Flightnum.split("-");
					FrmToSplt = FromTo.split("-");
					if (sc > 1) {
						if (!NxtSegDays.contains("-")) {
							NxtSegDays = NxtSegDays+"-";
						}
						gDays = NxtSegDays.split("-");
						SetDate = Atoflow.AddDateStr(Integer.parseInt(gDays[sc-2]), "ddMMM", "day", null);
					} else {
						SetDate = Atoflow.AddDateStr(getDays, "ddMMM", "day", null);
						cTime = SetDate;
					}
					if (FlightSplt[sc-1].contains(";")) {
						SptFlight2 = FlightSplt[sc-1].split(";");							
						for (int ff = 0; ff < SptFlight2.length; ff++) {
							if (FrmToSplt[sc-1].contains(";")) {
								OrigDest = FrmToSplt[sc-1].split(";");
								SetFrmTo = OrigDest[ff];
							} else {
								SetFrmTo = FrmToSplt[sc-1];
							}
							SetFlight = SptFlight2[ff];
							if (Availability_Sell(driver, queryObjects, Class, "A"+SetFlight+"/"+SetDate+SetFrmTo, ClsCnt)) {
								isProceed = true;
								if ((sc-1) ==0) {
									Login.FLIGHTNUM = Flight = SetFlight;
									Orig = SetFrmTo.substring(0,3);
									Destn = SetFrmTo.substring(3,6);
									break;
								}
							}
						}						
					} else {
						SetFlight = FlightSplt[sc-1];
						SetFrmTo = FrmToSplt[sc-1];
						if (Availability_Sell(driver, queryObjects, Class, "A"+SetFlight+"/"+SetDate+SetFrmTo, ClsCnt)) {
							isProceed = true;
						}
					}
					if (AvailType.contains("LongCell")) {
						rEnt = "0CM"+SetFlight+Class+""+SetDate+""+SetFrmTo+SetType+PaxCount;
					} else {
						RequestResponse(driver, queryObjects, "A "+SetFrmTo+" "+SetDate);
						if(PNRType.equalsIgnoreCase("NRSA") || PNRType.equalsIgnoreCase("NRPS") || PNRType.equalsIgnoreCase("PD") || PNRType.equalsIgnoreCase("HL") || PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
							rEnt = "N"+PaxCount+Class+"1"+SetType;
						} else {
							rEnt = "N"+PaxCount+Class+"1";
						}
					}
					Res = RequestResponse(driver, queryObjects, rEnt);
					if (Res.contains("FLT NOOP") || Res.contains("WTL")) {
						break;
					}
					
				}
				if (!Res.contains("FLT NOOP") && (Res.contains("TERMINAL-")|| Res.contains("SECURE FLIGHT")|| Res.contains("OPERATED BY") || Res.contains("ETKT ELIGIBLE")) && isProceed) {
					queryObjects.logStatus(driver, Status.PASS, "Check the Flight Availability ", "Flight is available for the given route and class" , null);
					
					//Give Group or Corporate Name
					if (PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
						if (PNRType.equalsIgnoreCase("Group")) {
							if (VerifyResponse(driver, queryObjects, "-G/"+PaxCount+"GROUP/"+RandomStringUtils.random(8, true, false), "*", "Group Name Entry", "Group name entry is successful", "Group name is not created")) {
								isProceed = true;
							}
						} else if (PNRType.equalsIgnoreCase("Corporate")){
							if (VerifyResponse(driver, queryObjects, "-C/"+PaxCount+"CORP/"+RandomStringUtils.random(8, true, false), "*", "Corporate Name Entry", "Corporate name entry is successful", "Corporate name is not created")) {
								isProceed = true;
							}						
						}
						if (isProceed) {
							isProceed = false;
							if (VerifyResponse(driver, queryObjects, "6PSGR|7T/|9LAX/N000|ER", "RCVD-", "End Transaction and Redisplay after giving "+PNRType+" name", PNRType+" name creation is successful", PNRType+" name creation failed")) {
								isProceed = true;
							}
						}
					} else {
						isProceed = true;
					}
					if (isProceed) {
						
						//Passenger Names
						Req = ""; rEnt = "";
						isProceed = false;

						if (PaxType.contains("INF") || PaxType.contains("INS")) {
							InfAge = Atoflow.AddDateStr(-1, "ddMMMyy", "year", null);
						}
						if (!PaxType.contains("-")) {
							PaxType = PaxType+"-";
						}
						SptPaxType = PaxType.split("-");
						
						if (PaxName.isEmpty()) {
							for (int pn = 1; pn <= Integer.parseInt(PaxCount); pn++) {
								String TempName = "";
								if (SptPaxType[pn-1].contains("INF")) {
									TempName = "*-INF"+RandomStringUtils.random(5, true, false)+"*"+RandomStringUtils.random(5, true, false)+"/"+InfAge;
								} else if (SptPaxType[pn-1].contains("CHD")) {
									TempName = "*-1CHD"+ChildAge;
								} else if (SptPaxType[pn-1].contains("INS")) {
									TempName = "*-INS"+"/"+InfAge;
								} else {
									TempName = "";
								}
								if (Firstname.isEmpty()) {									
									Firstname = RandomStringUtils.random(6, true, false)+TempName;
								} else {
									Firstname = Firstname+","+RandomStringUtils.random(6, true, false)+TempName;
								}
								if (Surname.isEmpty()) {
									if((PNRType.equalsIgnoreCase("NRSA")||PNRType.equalsIgnoreCase("NRPS")) && !PAXNRCode.isEmpty()) {
										Surname = PAXNRCode+"/"+RandomStringUtils.random(6, true, false);
									} else {
										Surname = RandomStringUtils.random(6, true, false);
									}
									
								} else {
									if((PNRType.equalsIgnoreCase("NRSA")||PNRType.equalsIgnoreCase("NRPS")) && !PAXNRCode.isEmpty()) {
										Surname = Surname+","+PAXNRCode+"/"+RandomStringUtils.random(6, true, false);
									} else {
										Surname = Surname+","+RandomStringUtils.random(7, true, false);
									}
								}
							}
							if (Integer.parseInt(PaxCount) > 1) {
								SptFName = Firstname.split(",");
								SptSName = Surname.split(",");
								for (int pa = 0; pa < SptFName.length; pa++) {
									if(PNRType.equalsIgnoreCase("MultiInitial")) {
										if (rEnt.isEmpty()) {
											rEnt = "-"+PaxCount+SptSName[pa]+"/"+SptFName[pa];
										} else {
											rEnt = rEnt+"/"+SptFName[pa];
										}
									} else {
										if (rEnt.isEmpty()) {
											rEnt = "-"+SptSName[pa]+"/"+SptFName[pa];
										} else {
											rEnt = rEnt+"|"+"-"+SptSName[pa]+"/"+SptFName[pa];
										}
									}
									if (pa==4) {
										iGrpPax = rEnt;
										Res = RequestResponse(driver, queryObjects, rEnt);
										Req = rEnt;
										rEnt ="";
									}
									if (pa>4 && pa==SptFName.length-1) {
										PaxName = Req+"|"+rEnt;
									}
									iPassengers = PaxName = rEnt;
								}								
							} else {
								iPassengers = PaxName = rEnt = "-"+Surname+"/"+Firstname;
							}
						} else {
							
							if (PaxName.contains("sINFAGE")) {
								PaxName = PaxName.replace("sINFAGE", InfAge);
							}
							rEnt = PaxName;
							iPassengers = PaxName;
						}
						
						if (VerifyResponse(driver, queryObjects, rEnt, "*", "Enter the Passenger Name", "Passengers are added", "Unable to add Passengers")) {
							if (!FQTV.isEmpty()) {
								RequestResponse(driver, queryObjects, FQTV);
							}
							if (PNRType.equalsIgnoreCase("Group") || PNRType.equalsIgnoreCase("Corporate")) {
								rEnt = "6PSGR|ER";
							} else {
								rEnt = "6PSGR|7T/|9LAX/N000|ER";
							}
							//End Transaction and ReDisplay
							if (VerifyResponse(driver, queryObjects, rEnt, "RCVD-", "End Transaction and Redisplay PNR", "PNR creation is successful", "PNR creation failed")) {
								
								//Ignore Transaction and ReDisplay
								Res = RequestResponse(driver, queryObjects, "IR");
								SptPNR = Res.split(" ");
								iPNRNo = SptPNR[0];
								SptPNR = null;
								SptPNR = Res.split("\\n");
								for (int rs = 0; rs < SptPNR.length; rs++) {
									if (SptPNR[rs].contains("FONE-")) {
										SegmentCnt = SptPNR[rs-1].trim();
										break;
									}
								}
								SegmentCnt = SegmentCnt.trim().substring(0, 2).trim();
								Res = iPNRNo;
								
								//Add SSR
								if (!SSR_Freetext.isEmpty()) {
									isProceed = false;
									if (SSRSegment.isEmpty()) {
										SSRSegment = "All";
									}
									if (SSRPax.isEmpty()) {
										SSRPax = "All";
									}
									for (int sg = 1; sg <= Integer.parseInt(SegmentCnt); sg++) {
										if (SSRSegment.contains("All")) {
											Req = SSR_Freetext+"S"+sg;
										} else if(Integer.parseInt(SSRSegment)==sg) {
											Req = SSR_Freetext+"S"+SSRSegment;
										}
										for (int px = 1; px <= Integer.parseInt(PaxCount); px++) {
											Res = "";
											if (SSRPax.contains("All")) {
												Req = Req+"N"+px;
												if (VerifyResponse(driver, queryObjects, Req, "*", "Add SSR", "SSR is added", "Unable to add SSR")) {
													isProceed = true;
												}
											} else if(Integer.parseInt(SSRPax)==sg) {
												Req = Req+"N"+SSRPax;
												if (VerifyResponse(driver, queryObjects, Req, "*", "Add SSR", "SSR is added", "Unable to add SSR")) {
													isProceed = true;
												}
											}									
										}								
									}
								} else {
									isProceed = true;
								}
								if (isProceed) {
									//Single SSR
									if (!SSR.isEmpty()) {
										isProceed = false;
										if (SSR.contains(";;")) {
											SptSSR = SSR.split(";;");
											for (int sr = 0; sr < SptSSR.length; sr++) {
												if (VerifyResponse(driver, queryObjects, SptSSR[sr], "*", "Add SSRs", "SSR is added", "Unable to add SSR")) {
													isProceed = true;
												}
											}
										} else {
											if (VerifyResponse(driver, queryObjects, SSR, "*", "Add SSRs", "SSR is added", "Unable to add SSR")) {
												isProceed = true;
											}
										}					
									}
									if (isProceed) {
										//Ticketing
										if (iTicketing.equalsIgnoreCase("yes")) {
											Res = RequestResponse(driver, queryObjects, "T-$|ET");
											if (Res.contains("NO VALID FARE") || Res.contains("UNABLE TO PRICE")|| Res.contains("NO FARE FOR BOOKING CODE") || Res.contains("USE DISCOUNT PRICING") ) {
												isProceed = false;
												//Manual Ticketing
												Res = "";
												if (PNRType.equalsIgnoreCase("NRSA")) {
													//Res = RequestResponse(driver, queryObjects, "$-$-ZO80P");//*
													if (VerifyResponse(driver, queryObjects, "$-$-ZO80P", "80 PERCENT MANUAL DISC APPLIED", "Give Pricing Entry", "Pricing is successful", "Pricing failed")) {
														isProceed = true;
													}
												} 
												else if (PNRType.equalsIgnoreCase("NRPS")) {
													//Res = RequestResponse(driver, queryObjects, "$-$-ZO100P");//*
													if (VerifyResponse(driver, queryObjects, "$-$-ZO100P", "100 PERCENT MANUAL DISC APPLIED", "Give Pricing Entry", "Pricing is successful", "Pricing failed")) {
														isProceed = true;
													}
												} else {
													if (VerifyResponse(driver, queryObjects, "FC|CCALC FARE END |Y1234.12/X12.12|-Y1|E1NON-REFUNDABLE", "*", "Manual Ticketing", "PNR is manually ticketed", "Unable to ticket the PNR manually")) {
														isProceed = true;
														//FC#CCALC FARE END#Y1234.12/X12.12#-Y1#E1PENALTY
														/*CHK DECIMALS IN AMOUNT FIELDS
														R	/FLWG DATA NOT ENTERED/PROCESSED:
														R	#Y2000.00/X20.00#-Y1#E1PENALTY*///Error Message

													}
												}
												if (VerifyResponse(driver, queryObjects, "FF", "FARE QUOTE  1 FILED", "File Fare Quote", "Fare Qoute is filed successful", "Unable to file fare quote")) {
													if(PNRType.equalsIgnoreCase("NRSA") || PNRType.equalsIgnoreCase("NRPS")) {
														if (VerifyResponse(driver, queryObjects, "ER", "FARE QUOTE-AUTO PRICED", "End Manual Pricing", "Manual Pricing is successful", "Manual Pricing failed")) {
															Res = RequestResponse(driver, queryObjects, "T-$|ET");
														}
													}
													else {
													if (VerifyResponse(driver, queryObjects, "ER", "FARE QUOTE-MANUAL PRICED", "End Manual Pricing", "Manual Pricing is successful", "Manual Pricing failed")) {
														Res = RequestResponse(driver, queryObjects, "T-$|ET");
													}}
												}
											}
											if (Res.contains("TKT ISSUED")) {
												queryObjects.logStatus(driver, Status.PASS, "Issue Ticket", "PNR is ticketed" , null);
											} else {
												queryObjects.logStatus(driver, Status.FAIL, "Issue Ticket", "Unable to issue ticket" , null);
											}
										}
										if (iCheckin.equalsIgnoreCase("yes")) {
											isProceed = false;
											Res = RequestResponse(driver, queryObjects, "I");//TRANS IGN
											String Pax = "";
											//With Bags
											//UAFormat-6-948/13FEBSFO-Milla/nithilam#N//nB2-10653498998-9 && //CMFormat-6-948/13FEBSFO-Milla/nithilam#N//nB1/36-812772
											//Without Bags
											//Format-6-948/13FEBSFO-Milla/nithilam#N//nB0
											String SptChkin[] = null;
											if (iPassengers.contains("|")) {
												Pax = iPassengers.replace("|", ",");
												SptChkin = Pax.split(",");
											}
											
											//Set Form Size 2
											Select sel=new Select(driver.findElement(By.xpath("//select[@id='Type']")));
											sel.selectByVisibleText("2");
											if (SptChkin!=null) {
												for (int ck = 0; ck < SptChkin.length; ck++) {
													if (SptChkin[ck].contains("*-1CHD")) {
														SptChkin[ck] = SptChkin[ck].substring(0, SptChkin[ck].indexOf("*-1CHD"));
													} else if (SptChkin[ck].contains("*-IN")) {
														SptChkin[ck] = SptChkin[ck].substring(0, SptChkin[ck].indexOf("*-IN"));
													}
													if (SetType.contains("PD") || SetType.contains("LL")) {
														rEnt = "6-W"+Flight+"/"+cTime+Orig+SptChkin[ck].toUpperCase()+"\n"+"B0";
														Res = "CABIN STBY LIST";
													} else {
														rEnt = "6-"+Flight+"/"+cTime+Orig+SptChkin[ck].toUpperCase()+"|N"+"\n"+"B0";
														Res = "SEATS ASSIGNED";
													}
													
													VerifyResponse(driver, queryObjects, rEnt, "GENERAL  INFORMATION FOR:", "Passenger Check in - APIS display", "Passenger information page(APIS) is displayed", "Passenger information page(APIS) is not displayed");
													FillForm(driver, queryObjects, SptChkin[ck].toUpperCase(), SptPaxType[ck], Country);
													if (driver.findElement(By.xpath(pResField)).getText().contains("COUNTRY OF RESIDENCE")) {
															FillForm(driver, queryObjects, "COUNTRY OF RESIDENCE", "", Country);
													}
													if (driver.findElement(By.xpath(pResField)).getText().contains("STREET ")) {
														FillForm(driver, queryObjects, "STREET ", "", Country);
													}
													if (driver.findElement(By.xpath(pResField)).getText().contains(Res)|| driver.findElement(By.xpath(pResField)).getText().contains("END OF API COLLECT MODE")) {
														isProceed = true;
														queryObjects.logStatus(driver, Status.PASS, "Passenger Checkin", SptChkin[ck].toUpperCase()+" Passenger Checked in successfully" , null);
													} else {
														queryObjects.logStatus(driver, Status.FAIL, "Passenger Checkin", SptChkin[ck].toUpperCase()+" Passenger Check in failed" , null);
													}
													Res = RequestResponse(driver, queryObjects, "I");//TRANS IGN
												}
											} else {
												if (iPassengers.contains("*-1CHD")) {
													iPassengers = iPassengers.substring(0, iPassengers.indexOf("*-1CHD"));
												} else if (iPassengers.contains("*-IN")) {
													iPassengers = iPassengers.substring(0, iPassengers.indexOf("*-IN"));
												}
												if (SetType.contains("PD") || SetType.contains("LL")) {
													rEnt = "6-W"+Flight+"/"+cTime+Orig+iPassengers.toUpperCase()+"\n"+"B0";
													Res = "CABIN STBY LIST";
												} else {
													rEnt = "6-"+Flight+"/"+cTime+Orig+iPassengers.toUpperCase()+"|N"+"\n"+"B0";
													Res = "SEATS ASSIGNED";
												}
												VerifyResponse(driver, queryObjects, rEnt, "GENERAL  INFORMATION FOR:", "Passenger Check in - APIS display", "Passenger information page(APIS) is displayed", "Passenger information page(APIS) is not displayed");
												FillForm(driver, queryObjects, iPassengers, SptPaxType[0], Country);
												if (driver.findElement(By.xpath(pResField)).getText().contains("COUNTRY OF RESIDENCE")) {
													FillForm(driver, queryObjects, "COUNTRY OF RESIDENCE", "", Country);
												}
												if (driver.findElement(By.xpath(pResField)).getText().contains("STREET ")) {
													FillForm(driver, queryObjects, "STREET ", "", Country);
												}
												if (driver.findElement(By.xpath(pResField)).getText().contains(Res)) {
													isProceed = true;
													queryObjects.logStatus(driver, Status.PASS, "Passenger Checkin",iPassengers.toUpperCase()+" Passenger Checked in successfully" , null);
												} else {
													queryObjects.logStatus(driver, Status.FAIL, "Passenger Checkin", iPassengers.toUpperCase()+" Passenger Check in failed" , null);
												}
											}
										}
										if (isProceed) {
											INRSA_PNR = iPNRNo;
											Login.PNRNUM = iPNRNo;
										}
										if (iMulPNR.equalsIgnoreCase("yes")) {
											if (MultiPNR.isEmpty()) {
												MultiPNR = iPNRNo;
											} else {
												MultiPNR = MultiPNR+";"+iPNRNo;
											}
											Login.MultiplePNR = MultiPNR;
											System.out.println(MultiPNR);
										}
									}
								}
							}
						}				
					}					
				} else {
					queryObjects.logStatus(driver, Status.FAIL, "Check the Flight Availability ", "Flight is not available for the given date" , null);
				}
				
				Res = RequestResponse(driver, queryObjects, "I");//TRANS IGN
				
				//CheckinCommand
			}
		}catch(Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Create PNR ", "PNR creation failed" , e);
		}
		if (isNewTab) {
			Atoflow.Close_SwitchTab(driver);
		}
		return iPNRNo;
		
	}
	
	public static String FLIFO(WebDriver driver, BFrameworkQueryObjects queryObjects, String Act, String Flights, String OrgDestn, String DteTime, String Cbn, String Cnt) throws Exception {
		String RetVal = ""; String RetVal1 = ""; String Response = ""; String SptRes[] = null; String ResSpt[] = null;
		String cDate = ""; String FormatTime = ""; String Entry = ""; String UpdateTime = "";
		pResField = "//pre[@id='content-wrap']";
		pSendBtn = "//input[@value='Send']";
		if(driver.findElements(By.xpath(pResField)).size()==0) {
			IshareLogin(driver, queryObjects, Shares);
		}
		String SptFgts[] = Flights.split(";");
		String SptOrgDestn[] = OrgDestn.split(";");
		for (int gd = 0; gd < 2; gd++) {
			if (DteTime.isEmpty()) {
				cDate = Atoflow.AddDateStr(gd, "ddMMM", "day", null).toUpperCase();
			} else {
				cDate = DteTime.toUpperCase();
			}			
			for (int ff = 0; ff < SptFgts.length; ff++) {
				Response = RequestResponse(driver, queryObjects, "2"+SptFgts[ff]+"/"+cDate+" "+SptOrgDestn[ff].substring(0, 3));
				if (!Response.contains("FLT NOOP")) {
					SptRes = Response.split("\\n");
					for (int srs = 0; srs < SptRes.length; srs++) {
						if (SptRes[srs].contains("ORIG")) {
							ResSpt = SptRes[srs].split("    ");
							if (ResSpt[1].isEmpty()) {
								ResSpt = SptRes[srs].split("   ");
							}
							break;
						}
					}
					if (ResSpt[1].length()==4) {
						ResSpt[1] = "0"+ResSpt[1];
					}
					//To Open the flight
					if (!OpenFlight) {
						OpenFlight Open = new OpenFlight(SptFgts[ff], SptOrgDestn[ff].substring(0, 3), cDate, driver, queryObjects);
						Open.run();
						OpenFlight = true;
					}					
					if (!DlyTime.isEmpty()) {
						FormatTime = Atoflow.AddDateStr(Integer.parseInt(DlyTime), "hhmma", "minute", new SimpleDateFormat("ddMMMhhmmaa").parse(cDate+ResSpt[1]+"M"));
					}
					if (FormatTime.length()>5) {
						UpdateTime = FormatTime.substring(0, FormatTime.length()-1);
					} else {
						UpdateTime = FormatTime;
					}
					if (Act.contains("WgtBalRestrict")) {
						Entry = "6:CW"+SptFgts[ff]+"/"+cDate+SptOrgDestn[ff].substring(0, 3)+"."+Cbn+"|"+Cnt;
						RetVal ="WEIGHT AND BALANCE RESTRICTION APPLIED"; RetVal1 = "RELEASE";
					}
					if (Act.contains("Delay")) {
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" OUT "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime+" FLIFOCheck";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					if (Act.contains("OnTime")) {
						FormatTime = ResSpt[1];
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" OUT "+SptOrgDestn[ff].substring(0, 3)+" "+ResSpt[1]+" FLIFOCheck";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					if (Act.contains("Flifo_Delay")) {
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" ETD "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime+" FLIFOCheck";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					if (Act.contains("Reinstate")) {
						FormatTime = ResSpt[1];
						Entry = "2X"+SptFgts[ff]+"/"+cDate+"|"+SptOrgDestn[ff].substring(0, 3)+"/ FX REINSTATEFLIGHT";
						RetVal ="CNLD FLWG MSG";
					}
					if (Act.contains("FXCancel")) {//"RPLCD FLWG MSG"
						Entry = "2N"+SptFgts[ff]+"/"+cDate+" | "+SptOrgDestn[ff].substring(0, 3)+"/ FX CANCELEDFLIGHT";
						RetVal ="RPLCD FLWG MSG"; FormatTime = "";
					}
					if (Act.contains("LXCancel")) {//"RPLCD FLWG MSG"
						Entry = "2N"+SptFgts[ff]+"/"+cDate+" | "+SptOrgDestn[ff].substring(0, 3)+"/ LX CANCELEDFLIGHT";
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG"; FormatTime = "";
					}
					if (Act.contains("Flifo_Forecast")) {
						Entry = "2F"+SptFgts[ff]+"/"+cDate+" | "+SptOrgDestn[ff].substring(0, 3)+" / WX FLIGHT DELAYED BY ETD "+UpdateTime;
						RetVal ="*"; RetVal1 = "RPLCD FLWG MSG";
					}
					//If any update after Delay
					if (Act.contains(";")) {
						RequestResponse(driver, queryObjects, Entry);
					}
					if (Act.contains("GateUpdate")) {
						Entry = "6:FM3@"+SptFgts[ff]+"GTD"+fOtrInput;
						RetVal ="GTD - "+fOtrInput;
					}
					if (Act.contains("GateReturn")) {
						Entry = "2P"+SptFgts[ff]+"/"+cDate+" RR "+SptOrgDestn[ff].substring(0, 3)+" "+UpdateTime;
						RetVal ="*"; RetVal1 ="RPLCD FLWG MSG";
					}
					String sResponse = RequestResponse(driver, queryObjects, Entry); 
					if (sResponse.contains(RetVal) || sResponse.contains(RetVal1)) {
						if (FormatTime.contains("M")) {
							FormatTime = FormatTime.replace("M", "");
						}
						Flight = SptFgts[ff];
						Orig = SptOrgDestn[ff].substring(0,3);
						Destn = SptOrgDestn[ff].substring(3,6);
						fDate = cDate;
						cTime = ResSpt[1];
						fTime = FormatTime;
						fGate = fOtrInput;
						RetVal = SptFgts[ff]+"-"+SptOrgDestn[ff]+"-"+cDate+"-"+ResSpt[1]+"-"+FormatTime;
						break;
					}
				}
			}
			if (!RetVal.isEmpty()) {
				break;
			}
		}
		if (isNewTab) {
			Atoflow.Close_SwitchTab(driver);
		}
		return RetVal;
	}
	
	public static String RequestResponse(WebDriver driver, BFrameworkQueryObjects queryObjects, String SharesEntry) throws IOException {
		String Res = "";
		driver.findElement(By.name("q")).sendKeys(SharesEntry);
		driver.findElement(By.xpath(pSendBtn)).click();
		Res = driver.findElement(By.xpath(pResField)).getText();
		return Res;
	}
	
	public static void FillForm(WebDriver driver, BFrameworkQueryObjects queryObjects, String PaxName, String PaxType, String Country) throws Exception {
		String gForm = ""; String SplitFm[] = null;
		int randomInt = 0;
		if (PaxName.contains("*-IN")) {
			PaxName = PaxName.substring(0, PaxName.indexOf("*-IN"));
		}
		if (PaxType.contains("ADT")) {
			randomInt = ThreadLocalRandom.current().nextInt(30, 50);
		} else if (PaxType.contains("CHD")) {
			randomInt = ThreadLocalRandom.current().nextInt(7, 10);
		} else if (PaxType.contains("INS")) {
			randomInt = ThreadLocalRandom.current().nextInt(1, 2);
		}
		gForm = driver.findElement(By.xpath(pResField)).getText();
		SplitFm = gForm.split("\\n");		
		for (int fm = 2; fm < SplitFm.length; fm++) {
			if (PaxName.contains("COUNTRY OF RESIDENCE")) {
				if (SplitFm[fm].contains("COUNTRY OF RESIDENCE")) {
					SetValue(driver, Country, fm, SplitFm[fm]);
				}
			} else if (SplitFm[fm].contains("STREET ")) {
				if (SplitFm[fm].contains("STREET ")) {
					SetValue(driver,"STREET11", fm, SplitFm[fm]);
				}				
			} else {
				if (SplitFm[fm].contains("GIVEN NAMES")) {
					SetValue(driver, PaxName.substring(PaxName.indexOf("/")+1, PaxName.length()), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("LAST NAME")) {
					SetValue(driver, PaxName.substring(0, PaxName.indexOf("/")), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DATE OF BIRTH")) {
					SetValue(driver, Atoflow.AddDateStr(-randomInt, "yyMMdd", "year", null), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("GENDER")) {
					SetValue(driver, "MALE", fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("NATIONALITY")) {
					SetValue(driver, Country, fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DOCUMENT TYPE")) {
					SetValue(driver, "P", fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("DOCUMENT NUMBER")) {
					SetValue(driver, RandomStringUtils.randomNumeric(8), fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("COUNTRY OF ISSUANCE")) {
					SetValue(driver, Country, fm, SplitFm[fm]);
				} else if (SplitFm[fm].contains("EXPIRATION DATE")) {
					SetValue(driver, Atoflow.AddDateStr(ThreadLocalRandom.current().nextInt(1, 5), "yyMMdd", "year", null), fm, SplitFm[fm]);
				}
			}
		}
		driver.findElement(By.name("q")).sendKeys(Keys.PAGE_DOWN);
		driver.findElement(By.xpath(pSendBtn)).click();
	}
	
	public static void SetValue(WebDriver driver, String Val, int LineNum, String LineText) throws IOException {
		WebElement TextBox = driver.findElement(By.name("q"));
		TextBox.sendKeys(Keys.PAGE_UP);
		for (int ln = 0; ln < LineNum-1; ln++) {
			TextBox.sendKeys(Keys.DOWN);
		}
		TextBox.sendKeys(Keys.HOME);
		if (Val.contains("-")) {
			Val = Val.replace("-", "");
		}
		for (int del = 0; del < Val.length(); del++) {
			TextBox.sendKeys(Keys.DELETE);
		}		
		TextBox.sendKeys(Val);
	}
	
	
	public static boolean Availability_Sell(WebDriver driver, BFrameworkQueryObjects queryObjects, String pClass, String Entry, int Count) throws IOException {
		//A629/23DECLAXMIA
		boolean isClass = false; String Avail = "";
		String SptCls[] = null; String Resp = ""; String Sptline[] = null; String retClass = "";
		String MulCls[] = null; int rLoop = 0;
		if (!iMultiClass.isEmpty()) {
			MulCls = iMultiClass.split("-");
			rLoop = MulCls.length;
		} else {
			rLoop = 1;
		}
		Resp = RequestResponse(driver, queryObjects, Entry);
		if (!Resp.toUpperCase().contains("CANCELLED")) {
			Sptline =Resp.split("\\n");
			for (int mcl = 0; mcl < rLoop; mcl++) {
				for (int sl = 0; sl < Sptline.length; sl++) {
					if (Sptline[sl].contains("ALTERNATE SERVICE")) {
						break;
					} else {
						SptCls = Sptline[sl].split(" ");				
						for (int sc = 2; sc < SptCls.length; sc++) {					
							if (SptCls[sc].matches("^[a-zA-Z]*$")) {
								if (pClass.isEmpty()) {
									pClass = SptCls[sc].trim();
								} else if (!iMultiClass.isEmpty()) {
									pClass = MulCls[mcl];
								}
								if (SptCls[sc].contains(pClass)) {
									if (!SptCls[(sc+1)].isEmpty() && SptCls[(sc+1)].length()>=3) {
										retClass = SptCls[(sc+1)].substring(0, 3);
									} else if (!SptCls[(sc+2)].isEmpty() && SptCls[(sc+2)].length()>=3) {
										retClass = SptCls[(sc+2)].substring(0, 3);
									}
									if (Count==0) {
										if (Integer.parseInt(retClass)== Count) {
											isClass = true;
										} else {
											Avail = "No";
										}
										break;
									} else {
										if (Integer.parseInt(retClass)>= Count) {
											isClass = true;
										} else {
											Avail = "No";
										}
										break;
									}
								}							
							}
						}//Class lines loop
						if (Avail.equalsIgnoreCase("No") || isClass) {
							break;
						}
					}
				}
				if (Avail.equalsIgnoreCase("No")) {
					break;
				}
				if (isClass && (mcl+1) == rLoop) {
					break;
				}
				isClass = false;
			}//Mutiple Class loop
		}		
		return isClass;
	}
	
	public static void EmailValidation(WebDriver driver, BFrameworkQueryObjects queryObjects) throws IOException {
		IshareLogin(driver, queryObjects, Shares);
		String PNRFROMCOMM="*"+Compensation.PnrForEmailValidation;
		driver.findElement(By.name("q")).sendKeys(PNRFROMCOMM);
		driver.findElement(By.xpath(pSendBtn)).click();
	
		String output=driver.findElement(By.xpath(pResField)).getText();
		if(output.contains("TEST@AUTOMATION.COM")) {
			queryObjects.logStatus(driver, Status.PASS, "Checking email update ", "Email update successfully FOR PNR in Share"+Compensation.PnrForEmailValidation , null);	
		} else {
			queryObjects.logStatus(driver, Status.FAIL, "Checking email update ", "Email update successfully FOR PNR in share"+Compensation.PnrForEmailValidation , null);
		}
	}
	
	public static boolean FlightBypass(WebDriver driver, BFrameworkQueryObjects queryObjects, String FlightNbr, String POS, String sDate) throws Exception {
		String Res = ""; boolean fStatus = false;
		IshareLogin(driver, queryObjects, Shares);
		OpenFlight Open = new OpenFlight(FlightNbr,POS, Atoflow.AddDateStr(0, "ddMMM", "day", null).toUpperCase(),driver,queryObjects);
		Open.run();
		RequestResponse(driver, queryObjects, "6-CR"+FlightNbr+"/"+sDate+POS);
		RequestResponse(driver, queryObjects, "6-C*"+FlightNbr+"/"+sDate+POS);
		Res = RequestResponse(driver, queryObjects, "6-CC"+FlightNbr+"/"+sDate+POS+".BYPASS");
		if(Res.contains("POST DEPARTURE BYPASSED")) {
			fStatus = true;
			queryObjects.logStatus(driver, Status.PASS, "Bypass the flight in Shares", "Flight is bypassed" , null);	
		} else {
			queryObjects.logStatus(driver, Status.WARNING, "Bypass the flight in Shares", "Flight is not bypassed, since the current timing is not close to the flight departure time" , null);
		}
		return fStatus;
	}
	
	public static void IshareLogin(WebDriver driver, BFrameworkQueryObjects queryObjects, String SharesA_B) throws IOException {
		try {
			pResField = "//pre[@id='content-wrap']";
			pSendBtn = "//input[@value='Send']";
			try {
				SharesA_B = Shares;
			} catch (Exception e) {
				SharesA_B = "";
			}			
			WebDriverWait wait = new WebDriverWait(driver, 100);
			//Check whether Copa Web page is Opened
			if (driver.getCurrentUrl().contains("airservices.svcs.entsvcs.com")) {
				Atoflow.Open_SwitchTab(driver);
				isNewTab = true;
			}
			if (SharesA_B.contains("SharesA")) {
				driver.navigate().to("https://tpfsa.svcs.entsvcs.net");
			} else {
				driver.navigate().to("https://tpfsb.svcs.entsvcs.net");
				SharesA_B = "SharesB";
			}			
			driver.findElement(By.xpath("//input[@name='ID']")).sendKeys("wzqltv");
			driver.findElement(By.name("Password")).sendKeys("prayer08");
			driver.findElement(By.xpath("//input[@value='Login']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Terminal Emulation')]")));
			driver.findElement(By.xpath("//a[contains(text(),'Terminal Emulation')]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			if (SharesA_B.contains("SharesA")) {
				driver.findElement(By.name("q")).sendKeys("logc uare");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsia");
				driver.findElement(By.xpath(pSendBtn)).click();
				if( driver.findElement(By.xpath(pResField)).getText().contains("A-SINE COMPLETE")||driver.findElement(By.xpath(pResField)).getText().contains("A-IN USE")){	//indrajit
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares A application", "Login is successful", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares A application", "Getting an error while login to ISHARES A applicatio", null);
				}
			} else {
				driver.findElement(By.name("q")).sendKeys("logc cmre");
				driver.findElement(By.xpath(pSendBtn)).click();
				driver.findElement(By.name("q")).sendKeys("bsib");
				driver.findElement(By.xpath(pSendBtn)).click();
				if( driver.findElement(By.xpath(pResField)).getText().contains("B-SINE COMPLETE")||driver.findElement(By.xpath(pResField)).getText().contains("B-IN USE")){	//indrajit
					queryObjects.logStatus(driver, Status.PASS, "Login to Ishares B application", "Login is successful", null);
				}else{
					queryObjects.logStatus(driver, Status.FAIL, "Login to Ishares B application", "Getting an error while login to ISHARES B applicatio", null);
				}
			}		
			
		} catch (Exception e) {
			queryObjects.logStatus(driver, Status.FAIL, "Login to IShares", "Login Failed", null);
		}
		
	}
}
