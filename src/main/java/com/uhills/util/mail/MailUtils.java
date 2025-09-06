package com.uhills.util.mail;

import java.util.*;
import java.io.UnsupportedEncodingException;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

public class MailUtils
{
    private EMailConnectionProperties       m_mailParms;
    private Session                         m_mailSession;
    private Transport                       m_mailTransport;

    public MailUtils(EMailConnectionProperties mailParms)
    {
        m_mailParms = mailParms;
    }

    public void open() throws NoSuchProviderException, MessagingException
    {
        m_mailSession = Session.getDefaultInstance(new Properties(), null);

        m_mailTransport = m_mailSession.getTransport("smtp");
        m_mailTransport.connect(m_mailParms.SMTPServer,
                                m_mailParms.userName,
                                m_mailParms.password);
    }

    /**
     * Sends one E-mail message to one recipient.  This is used
     * by the business bean subclasses for sending single confirmation
     * E-mails, security info, etc. to Members or prospects.
     *
     * @param ex - the Java exception thrown
     */
    public void sendSingleMessage(InternetAddress senderAddress,
                                  InternetAddress recipientAddress,
                                  String strSubject,
                                  String strBody) throws NoSuchProviderException, MessagingException, UnsupportedEncodingException
    {
        Session                 mailSession;
        Transport               mailTransport;

        mailSession = Session.getDefaultInstance(new Properties(), null);

        mailTransport = mailSession.getTransport("smtp");
        mailTransport.connect(m_mailParms.SMTPServer,
                              m_mailParms.userName,
                              m_mailParms.password);

        // format and send mail
        MimeMessage         mimeMessage = new MimeMessage(mailSession);
        InternetAddress[]   recipientArray = { recipientAddress };

        mimeMessage.setFrom(senderAddress);
        mimeMessage.setSubject(strSubject);
        mimeMessage.setText(strBody);
        mimeMessage.setRecipient(Message.RecipientType.TO, recipientAddress);

        mailTransport.sendMessage(mimeMessage, recipientArray);
        mailTransport.close();
    }

    /**
     * Sends one E-mail message to one recipient.  This is used
     * by the business bean subclasses for sending single confirmation
     * E-mails, security info, etc. to Members or prospects.
     *
     * @param ex - the Java exception thrown
     */
    public void sendMessage(InternetAddress senderAddress,
                            InternetAddress recipientAddress,
                            String strSubject,
                            String strBody) throws NoSuchProviderException, MessagingException, UnsupportedEncodingException
    {
        // format and send mail
        MimeMessage         mimeMessage = new MimeMessage(m_mailSession);
        Address[]           replyTo = { senderAddress };
        InternetAddress[]   recipientArray = { recipientAddress };

        mimeMessage.setFrom(senderAddress);
        mimeMessage.setReplyTo(replyTo);
        mimeMessage.setSubject(strSubject);
        mimeMessage.setText(strBody);
        mimeMessage.setRecipient(Message.RecipientType.TO, recipientAddress);

        m_mailTransport.sendMessage(mimeMessage, recipientArray);
    }

    public void close() throws MessagingException
    {
        if (m_mailTransport != null)
            m_mailTransport.close();
    }
}