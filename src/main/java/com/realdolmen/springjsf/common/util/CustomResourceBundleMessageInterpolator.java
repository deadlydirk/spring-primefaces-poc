package com.realdolmen.springjsf.common.util;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.resourceloading.ResourceBundleLocator;


public class CustomResourceBundleMessageInterpolator implements
		MessageInterpolator {

    /**
     * The name of the user-provided message bundle as defined in the specification.
     */
    public static final String USER_VALIDATION_MESSAGES = "i18n.validation.ValidationMessages";

    private static final MessageInterpolator DEFAULT_MESSAGE_INTERPOLATOR = Validation.byDefaultProvider().configure()
            .getDefaultMessageInterpolator();

    private final MessageInterpolator resourceBundleInterpolator;

    private final MessageInterpolator defaultInterpolator;

    public CustomResourceBundleMessageInterpolator() {
        this(DEFAULT_MESSAGE_INTERPOLATOR);
    }

    public CustomResourceBundleMessageInterpolator(final MessageInterpolator defaultInterpolator) {
        super();
        final ResourceBundleLocator defaultResourceBundleLocator = new PlatformResourceBundleLocator(USER_VALIDATION_MESSAGES);
        this.defaultInterpolator = defaultInterpolator;
        this.resourceBundleInterpolator = new ResourceBundleMessageInterpolator(defaultResourceBundleLocator);
    }

    @Override
    public String interpolate(final String messageTemplate, final Context context) {
        String result = null;
        Locale locale = Locale.getDefault();
        result = interpolate(messageTemplate, context, locale);
        return result;
    }

    @Override
    public String interpolate(final String messageTemplate, final Context context, final Locale locale) {
        String result = resourceBundleInterpolator.interpolate(messageTemplate, context, locale);
        result = interpolateByDefaultInterpolator(result, context, locale);
        return result;
    }

    private String interpolateByDefaultInterpolator(final String messageTemplate, final Context context, final Locale locale) {
        return this.defaultInterpolator.interpolate(messageTemplate, context, locale);
    }


}