package io.github.dumijdev.jackson.flex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dumijdev.jackson.flex.annotations.JsonFlexGetter;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class MyClass {
    @JsonFlexGetter(src = "data.name", dst = "mercator.codename.name")
    @JsonProperty("mercator")
    A mercator;
}
