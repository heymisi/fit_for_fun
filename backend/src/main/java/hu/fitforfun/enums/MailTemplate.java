package hu.fitforfun.enums;

public enum MailTemplate {
    CONTACT_US("contact_us_body.ftl","contact_us_subject.ftl"),
    REGISTRATION("registration_body.ftl","registration_subject.ftl"),
    PASSWORD_RESET("contact_us_body.ftl","contact_us_subject.ftl")
    ;

    private String templateName;
    private String subject;

    MailTemplate(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
