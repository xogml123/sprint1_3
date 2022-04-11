package sprint.sprint1_3.repository.Converter;

import java.nio.ByteBuffer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class BytesToLongConverter implements Converter<String, Long > {

    @Override
    public Long convert(String source) {
        return Long.valueOf(source);
    }
}