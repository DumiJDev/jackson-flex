package io.github.dumijdev.jackson.flex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dumijdev.jackson.flex.annotations.JsonFlexGetter;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FlexObjectMapperTests {

    final String data = "{\"data\":{\"name\": \"Paulo\", \"others\": [12, 12, 12, 12, 12, 12]}}";
    final String data1 = "{\"students\": [{\"name\": \"Paulo\"}, {\"name\": \"Paulo0\"}, {\"name\": \"Paulo1\"}]}";
    final ObjectMapper mapper = new FlexObjectMapper();

    @Test
    @SneakyThrows
    void shouldParseWithSuccessful() {
        var result = mapper.readValue(data, MyClass.class);

        Assertions.assertEquals(result.getMercator().getCodename().name, "Paulo");
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

        System.out.println(result);

        Assertions.assertTrue(!result.getNums().isEmpty());
    }
}

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class MyClass {
    @JsonFlexGetter(src = "data.name", dst = "mercator.codename.name")
    @JsonProperty("mercator")
    A mercator;
}

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class MyClass1 {
    @JsonFlexGetter(src = "students", dst = "nums")
    @JsonProperty("nums")
    List<B> nums;
}

@ToString
@Getter
class A {
    B codename;
}

@ToString
@Getter
class B {
    String name;
}
