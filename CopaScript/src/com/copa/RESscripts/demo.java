package com.copa.RESscripts;

 
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
  *
  * Java program to compare two XML files using XMLUnit example

  * @author Javin Paul
  */
public class demo {
	
	
	
	

	public static void main() throws UnknownHostException
	{
	final String username = "chebolu.kishore@dxc.com";

	   final String password = "Mphasis!567";

	   String hostname = "Unknown";
	   InetAddress ip;
	   InetAddress addr;
	    addr = InetAddress.getLocalHost();
	    hostname = addr.getHostName();
	    ip = InetAddress.getLocalHost();
	   
	   Properties props = new Properties();
	   props.put("mail.smtp.auth", true);
	   props.put("mail.smtp.starttls.enable", true);
	  // props.put("mail.smtp.host", "smtp.gmail.com");
	 //  props.put("mail.smtp.host", "outlook.office365.com");
	   props.put("mail.smtp.host", "smtp-mail.outlook.com");
	   props.put("mail.smtp.port", "587");
	 //  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	   
	   Session session = Session.getInstance(props,
	           new javax.mail.Authenticator() {
	               protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(username, password);
	               }
	           });

	   try {

	       Message message = new MimeMessage(session);
	       message.setFrom(new InternetAddress("chebolu.kishore@dxc.com"));
	       message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse("chebolu.kishore@dxc.com"));
	       message.setSubject("Automation report: "+ ip);
	       message.setText("PFA");

	       MimeBodyPart messageBodyPart = new MimeBodyPart();

	       Multipart multipart = new MimeMultipart();

	       messageBodyPart = new MimeBodyPart();
	       ///https://myaccount.google.com/lesssecureapps
	       String file = "C:\\MSmart\\Report\\MSmart_202004241953\\SummaryTestReport_202004241953.html";
	       String fileName = "SummaryTestReport_202004241953.html";
	       DataSource source = new FileDataSource(file);
	       messageBodyPart.setDataHandler(new DataHandler(source));
	       messageBodyPart.setFileName(fileName);
	       multipart.addBodyPart(messageBodyPart);

	       message.setContent(multipart);

	       System.out.println("Sending");

	       Transport.send(message);

	       System.out.println("Done");

	   } catch (MessagingException e) {
	       e.printStackTrace();
	   }
	 }

	
	 @Test
	public void dd()throws ParseException{
		
		//May 3, 2020
		
String date1="May-4-2020";
		
		String date2="May-04-2020";
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM-dd-yyyy");
		
		Date datea = sdf.parse(date2);
        Date datec = sdf.parse(date1);
		System.out.println(datea);
		System.out.println(datec);
		if(datea.compareTo(datec) == 0) {
			System.out.println("Equal");
		}
		
		String sss=date2.replace(" ", "-");
		System.out.println(sss);
		
	}
}
		 
		 
		 
	