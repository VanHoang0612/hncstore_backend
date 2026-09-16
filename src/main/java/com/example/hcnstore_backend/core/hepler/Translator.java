package com.example.hcnstore_backend.core.hepler;

import com.example.hcnstore_backend.core.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class Translator {
    private final MessageSource messageSource;

    public String getMessage(ResponseCode responseCode, String labelKey, Object... args) {
        return getMessage(responseCode.getMessageKey(), labelKey, args);
    }

    public String getMessage(String messageKey, String labelKey, Object... args) {
        Object[] safeArgs = (args != null) ? args : new Object[0];
        try {
            Locale locale = LocaleContextHolder.getLocale();
            if (labelKey != null) {

                String label = messageSource.getMessage(labelKey, null, locale);
                Object[] newArgs = new Object[safeArgs.length + 1];
                newArgs[0] = label;
                System.arraycopy(safeArgs, 0, newArgs, 1, safeArgs.length);
                safeArgs = newArgs;
            }
            log.info("messageKey: {}, safeArgs: {}", messageKey, safeArgs);
            return messageSource.getMessage(messageKey, safeArgs, locale);
        } catch (NoSuchMessageException e) {
            log.error(String.valueOf(e));
            return messageKey;
        }

    }

}
