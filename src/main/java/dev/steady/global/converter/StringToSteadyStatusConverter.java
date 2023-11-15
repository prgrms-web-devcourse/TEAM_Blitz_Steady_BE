package dev.steady.global.converter;

import dev.steady.steady.domain.SteadyStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSteadyStatusConverter implements Converter<String, SteadyStatus> {

    @Override
    public SteadyStatus convert(String status) {
        return SteadyStatus.from(status);
    }

}
