package hu.fitforfun.services.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import hu.fitforfun.enums.MailTemplate;
import hu.fitforfun.exception.FreeMarkerConsoleEx;
import hu.fitforfun.model.user.User;
import hu.fitforfun.services.EmailService;
import hu.fitforfun.util.EmailConstants;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String RECIPIENT = "recipient";



    public boolean sendPasswordResetRequest(String firstName, String email, String token) {
        boolean returnValue = false;

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().
                withRegion(Regions.EU_CENTRAL_1).build();

        String htmlBodyWithToken = EmailConstants.getEmailPasswordResetHtmlBody().replace("%tokenValue", token);
        htmlBodyWithToken = htmlBodyWithToken.replace("$firstName", firstName);

        String textBodyWithToken = EmailConstants.getEmailPasswordResetTextBody().replace("%tokenValue", token);
        textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);

        SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(email))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content().withCharset("UTF-8").withData(EmailConstants.getEmailPasswordResetSubject())))
                .withSource(EmailConstants.getEmailSender());

        SendEmailResult result = client.sendEmail(request);
        if (result != null && (result.getMessageId() != null && !result.getMessageId().isEmpty())) {
            returnValue = true;
        }
        return returnValue;

    }

    @Override
    public void sendRegistrationMail(User user) throws Exception {
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.put(RECIPIENT, user.getContactData().getEmail());
        templateData.put("userFirstName", user.getFirstName());
        templateData.put("userLastName", user.getLastName());
        templateData.put("date", new Date());

        sendMailFromTemplate(MailTemplate.REGISTRATION, templateData);
    }

    @Override
    public void sendContactUsEmail(String email, String message) throws Exception{
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.put(RECIPIENT, "heymisi99@gmail.com");
        templateData.put("email", email);
        templateData.put("message", message);
        templateData.put("date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));

        sendMailFromTemplate(MailTemplate.CONTACT_US, templateData);
    }

    private String getProcessedMailSubject(MailTemplate mailTemplate, Map<String, Object> templateData) throws Exception {
        try {
            Configuration cfg = new Configuration(new Version("2.3.30"));
            cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/templates/");
            cfg.setDefaultEncoding("UTF-8");

            Template mailBodyTemplate = cfg.getTemplate(mailTemplate.getSubject());
            StringWriter mailBody = new StringWriter();
            mailBodyTemplate.process(templateData, mailBody);
            return mailBody.toString();
        } catch (Exception e) {
            throw e;
        }

    }
    private String getProcessedMailTemplate(MailTemplate mailTemplate, Map<String, Object> templateData) throws Exception {
        try {
            Configuration cfg = new Configuration(new Version("2.3.30"));
            cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/templates/") ;
            cfg.setDefaultEncoding("UTF-8");

            Template mailBodyTemplate = cfg.getTemplate(mailTemplate.getTemplateName());
            StringWriter mailBody = new StringWriter();
            mailBodyTemplate.process(templateData, mailBody);
            return mailBody.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public void sendMailFromTemplate(MailTemplate mailTemplate, Map<String, Object> templateData) throws Exception{
        String mailSubject = getProcessedMailSubject(mailTemplate,templateData);
        String mailBody = getProcessedMailTemplate(mailTemplate,templateData);

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().
                withRegion(Regions.EU_CENTRAL_1).build();

        SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses((String) templateData.get(RECIPIENT)))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(mailBody))
                                .withText(new Content().withCharset("UTF-8").withData(mailBody)))
                        .withSubject(new Content().withCharset("UTF-8").withData(mailSubject)))
                .withSource(EmailConstants.getEmailSender());
        client.sendEmail(request);
    }

}
