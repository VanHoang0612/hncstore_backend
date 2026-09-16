package com.hoang.hncstore_backend.core.hepler;

import com.github.slugify.Slugify;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class SlugHelper {

    private final Slugify slugify;

    public SlugHelper() {
        this.slugify = Slugify.builder()
                .lowerCase(true)
                .locale(Locale.forLanguageTag("vi"))
                .build();
    }

    public String toSlug(String input) {
        return slugify.slugify(input);
    }

    public String generateUniqueSlug(String input) {

        return toSlug(input) + "-" + UUID.randomUUID()
                .toString()
                .substring(0, 4);

    }


}
