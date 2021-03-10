package hu.fitforfun.services.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import hu.fitforfun.model.user.User;
import hu.fitforfun.util.EmailConstants;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    public void verifyEmail(User user) {

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().
                withRegion(Regions.EU_CENTRAL_1).build();

        String htmlBodyWithToken = EmailConstants.getEmailVerifyEmailHtmlBody().replace("%tokenValue", user.getEmailVerificationToken());
        String textBodyWithToken = EmailConstants.getEmailVerifyEmailTextBody().replace("%tokenValue", user.getEmailVerificationToken());

        SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(user.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content().withCharset("UTF-8").withData(EmailConstants.getEmailVerifyEmailSubject())))
                .withSource(EmailConstants.getEmailSender());
        client.sendEmail(request);

    }

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
        if(result != null && (result.getMessageId()!= null && !result.getMessageId().isEmpty())){
            returnValue = true;
        }
        return returnValue;
    }
}
