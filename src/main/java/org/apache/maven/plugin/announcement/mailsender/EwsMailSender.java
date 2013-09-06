package org.apache.maven.plugin.announcement.mailsender;

import microsoft.exchange.webservices.data.*;
import org.codehaus.plexus.mailsender.AbstractMailSender;
import org.codehaus.plexus.mailsender.MailMessage;
import org.codehaus.plexus.mailsender.MailSenderException;

import java.net.URI;
import java.util.List;

public class EwsMailSender extends AbstractMailSender implements ProjectMailSender {
    private String ewsUrl;

    public void send(MailMessage mailMessage) throws MailSenderException {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);

        ExchangeCredentials credentials = new WebCredentials(getUsername(),
                getPassword());
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI(ewsUrl));
            service.setTraceEnabled(true);

            EmailMessage message = new EmailMessage(service);
            message.setSubject(mailMessage.getSubject());
            message.setBody(MessageBody.getMessageBodyFromText(mailMessage.getContent()));

            EmailAddressCollection toRecipients = message.getToRecipients();
            List toAddresses = mailMessage.getToAddresses();
            for (Object toAddress : toAddresses) {
                String rfc2822Address = ((MailMessage.Address) toAddress).getMailbox();
                toRecipients.add(new EmailAddress(rfc2822Address));
            }
            message.save();
            message.sendAndSaveCopy();

        } catch (Exception e) {
            throw new MailSenderException("Sending mail threw exception:", e);
        }
    }

    public void initialize() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEwsUrl(String ewsUrl) {
        this.ewsUrl = ewsUrl;
    }
}
