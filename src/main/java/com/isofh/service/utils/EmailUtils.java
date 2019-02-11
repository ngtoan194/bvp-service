/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.utils;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.EmailConst;
import com.isofh.service.model.EmailEntity;
import org.springframework.http.HttpHeaders;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {


    static Session getMailSession;
    static MimeMessage mimeMessage;


    public static synchronized void sendEmail(EmailEntity emailEntity) throws Exception {
        if (emailEntity.getSent() == 1) return;
        String[] emails = emailEntity.getEmails().split(",");


        String subject = emailEntity.getTitle();
        String content = emailEntity.getContent();
        String mailServerPORT = EmailConst.MAIL_SERVER_PORT;
        String mailServer = EmailConst.MAIL_SERVER;
        String mailServerPassword = EmailConst.MAIL_SERVER_PASSWORD;
        String mailServerSTMP = EmailConst.MAIL_SERVER_STMP;
        String personalInfo = EmailConst.PERSONAL;

        Properties mailServerProperties = System.getProperties();

        mailServerProperties.put("mail.smtp.port", mailServerPORT);
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        mimeMessage = new MimeMessage(getMailSession);
        mimeMessage.setFrom(new InternetAddress(mailServer, personalInfo));
        mimeMessage.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");
        for (int i = 0; i < emails.length; i++) {
            LogUtils.getInstance().info("start send EMAIL " + emails[i]);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emails[i]));
        }

        if(!StrUtils.isNullOrWhiteSpace(emailEntity.getCcEmails())) {
            String[] ccEmails = emailEntity.getCcEmails().split(",");
            for (int i = 0; i < ccEmails.length; i++) {
                LogUtils.getInstance().info("cc send EMAIL " + ccEmails[i]);
                mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmails[i]));
            }
        }
        mimeMessage.setSubject(subject, "utf-8");
        mimeMessage.setText(content, "utf-8", "html");
        // Step3
        Transport transport = getMailSession.getTransport("smtp");


        transport.connect(mailServerSTMP, mailServer, mailServerPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        LogUtils.getInstance().info("end send EMAIL  " + emailEntity.getEmails());
    }

    /**
     * Create content of EMAIL forgot password
     *
     * @param name
     * @param link
     * @return
     */
    public static String getBodyResetPassword(String name, String link, String app, String contactMail) {
        String appName = app;
        String contactEmail = contactMail;
        String emailContent = FileUtils.getFileContent("email_reset_password.html");

        String keyUserName = "$USER_NAME$";
        String keyAppName = "$APP_NAME$";
        String keyHref = "$HREF$";
        String keyContactEmail = "$CONTACT_EMAIL$";
        if (name == null) name = "";
        emailContent = emailContent.replace(keyUserName, name);
        emailContent = emailContent.replace(keyAppName, appName);
        emailContent = emailContent.replace(keyHref, link);
        emailContent = emailContent.replace(keyContactEmail, contactEmail);
        emailContent = emailContent.replace("$SERVICE_URL$", AppConst.SERVICE);
        return emailContent;
    }

    /**
     * Email body when user want to change EMAIL
     *
     * @param name
     * @param link
     * @return
     */
    public static String getBodyVerifyEmail(String name, String link,String appName, String contactEmail) {
        String emailContent = FileUtils.getFileContent("email_verify_email.html");

        String keyUserName = "$USER_NAME$";
        String keyAppName = "$APP_NAME$";
        String keyHref = "$HREF$";
        String keyContactEmail = "$CONTACT_EMAIL$";
        emailContent = emailContent.replace(keyUserName, name);
        emailContent = emailContent.replace(keyAppName, appName);
        emailContent = emailContent.replace(keyHref, link);
        emailContent = emailContent.replace(keyContactEmail, contactEmail);
        return emailContent;
    }

    public static String getBodySendAccountInfo(String name, String username, String password, String link, String app) {
        String emailContent = FileUtils.getFileContent("email_send_account_info.html");
        String file = AppConst.SERVICE + AppConst.FILE_HDSD;
//        String website = AppConst.WEB_ADDRESS;

        String keyName = "$NAME$";
        String keyLink = "$LINK$";
        String keyFile = "$FILE$";
        String keyPassword = "$PASSWORD$";
        String keyUsername = "$USER_NAME$";
        if (name == null) name = "";
        emailContent = emailContent.replace(keyName, name);
        emailContent = emailContent.replace(keyFile, file);
        emailContent = emailContent.replace(keyLink, link);
        emailContent = emailContent.replace(keyUsername, username);
        emailContent = emailContent.replace(keyPassword, password);
        emailContent = emailContent.replace("$SERVICE_URL$", AppConst.SERVICE);
        return emailContent;
    }

    public static String getBodySendAccountInfoHoiLao(String name, String link, String email, String password) {
        //Properties properties = ResourceUtils.getInstance().getProperties(PropertiesNameConst.APP);
        String appName = "Hội Lao và Bệnh Phổi Việt Nam";
        String contactEmail = AppConst.CONTACT_BVP_EMAIL;
        String emailContent = FileUtils.getFileContent("email_send_account_info_hoilao.html");

        String keyUserName = "$USER_NAME$";
        String keyAppName = "$APP_NAME$";
        String keyHref = "$HREF$";
        String keyContactEmail = "$CONTACT_EMAIL$";
        String keyPassword = "$PASSWORD$";
        String keyEmail = "$EMAIL$";
        if (name == null) name = "";
        emailContent = emailContent.replace(keyUserName, name);
        emailContent = emailContent.replace(keyAppName, appName);
        emailContent = emailContent.replace(keyHref, link);
        emailContent = emailContent.replace(keyContactEmail, contactEmail);
        emailContent = emailContent.replace(keyEmail, email);
        emailContent = emailContent.replace(keyPassword, password);
        return emailContent;
    }

    /**
     * Email body when user want to change email
     *
     * @param name
     * @param link
     * @return
     */
    public static String getBodyVerifyLaoMember(String name,String courseName, String link) {
        String appName = AppConst.APP_BVP_NAME;
        String contactEmail = AppConst.CONTACT_BVP_EMAIL;
        String emailContent = FileUtils.getFileContent("email_admin_approval_user_course.html");

        String keyUserName = "$USER_NAME$";
        String keyCourseName = "$COURSE_NAME$";
        String keyAppName = "$APP_NAME$";
        String keyHref = "$HREF$";
        String keyContactEmail = "$CONTACT_EMAIL$";
        emailContent = emailContent.replace(keyUserName, name);
        emailContent = emailContent.replace(keyCourseName, courseName);
        emailContent = emailContent.replace(keyAppName, appName);
        emailContent = emailContent.replace(keyHref, link);
        emailContent = emailContent.replace(keyContactEmail, contactEmail);
        return emailContent;
    }

    /**
     * Email body when user want to change email
     *
     * @param name
     * @param link
     * @return
     */
    public static String getBodySendCertificate(String name, String link) {
        if (StrUtils.isNullOrWhiteSpace(name)) {
            name = " Quý vị";
        }
        String appName = AppConst.APP_HL_NAME;
        String contactEmail = AppConst.CONTACT_HL_EMAIL;
        String emailContent = FileUtils.getFileContent("email_hoilao_certificate.html");

        String keyUserName = "$USER_NAME$";
        String keyAppName = "$APP_NAME$";
        String keyHref = "$HREF$";
        String keyContactEmail = "$CONTACT_EMAIL$";
        if (name == null) name = "";
        emailContent = emailContent.replace(keyUserName, name);
        emailContent = emailContent.replace(keyAppName, appName);
        emailContent = emailContent.replace(keyHref, link);
        emailContent = emailContent.replace(keyContactEmail, contactEmail);
        return emailContent;
    }
}
