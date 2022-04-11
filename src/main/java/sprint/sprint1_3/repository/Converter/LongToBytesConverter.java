package sprint.sprint1_3.repository.Converter;

import java.nio.ByteBuffer;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class LongToBytesConverter implements Converter<Long, String > {

    @Override
    public String convert(Long source) {
        return String.valueOf(source);
    }
}