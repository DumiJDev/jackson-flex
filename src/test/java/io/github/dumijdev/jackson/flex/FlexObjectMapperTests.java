package io.github.dumijdev.jackson.flex;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlexObjectMapperTests {

    final String data = "{\"data\":{\"name\": \"Paulo\", \"others\": [12, 12, 12, 12, 12, 12]}}";
    final String data1 = "{\"students\": [{\"name\": \"Paulo\"}, {\"name\": \"Paulo0\"}, {\"name\": \"Paulo1\"}]}";
    final ObjectMapper mapper = new FlexObjectMapper();

    @Test
    @SneakyThrows
    void shouldParseWithSuccessful() {
        var result = mapper.readValue(data, MyClass.class);
        var text = mapper.writeValueAsString(result);

        System.out.println(text);

        Assertions.assertEquals(result.getMercator().getCodename().name, "Paulo");
        Assertions.assertNotEquals("", text);
    }

    @Test
    @SneakyThrows
    void shouldMercatorFieldNull() {
        var result = mapper.readValue(data1, MyClass.class);

        Assertions.assertNull(result.getMercator());
    }

    @Test
    @SneakyThrows
    void shouldParseListWithSuccessful() {
        var result = mapper.readValue(data1, MyClass1.class);
        var text = mapper.writeValueAsString(result);

        System.out.println(result);
        System.out.println(text);

        Assertions.assertTrue(!result.getNums().isEmpty());
        Assertions.assertNotEquals("", text);
    }
}

