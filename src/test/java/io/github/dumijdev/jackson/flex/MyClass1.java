package io.github.dumijdev.jackson.flex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dumijdev.jackson.flex.annotations.JsonFlexGetter;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class MyClass1 {
    @JsonFlexGetter(src = "students", dst = "nums")
    @JsonProperty("nums")
    List<B> nums;
}
