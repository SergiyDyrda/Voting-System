package ua.webapp.votingsystem.web.resources;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

public class CustomReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public Properties getAllMessages(Locale locale) {
        PropertiesHolder mergedProperties = getMergedProperties(locale);
        return mergedProperties.getProperties();
    }

}
