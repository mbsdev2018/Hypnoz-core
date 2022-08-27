package net.hypnoz.core.services.builder;

import net.hypnoz.core.dto.ApplicationsDto;

import java.util.Collections;
import java.util.List;

public class ApplicationsBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static ApplicationsDto getDto() {
        return  ApplicationsDto.builder().build();
    }
}