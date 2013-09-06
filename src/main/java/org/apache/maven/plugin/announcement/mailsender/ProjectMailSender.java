package org.apache.maven.plugin.announcement.mailsender;

import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.mailsender.MailSender;

public interface ProjectMailSender extends MailSender {
    void enableLogging(Logger logger);

    void initialize();

    void setEwsUrl(String ewsUrl);
}
