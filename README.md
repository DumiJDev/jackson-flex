# Jackson-Flex

A flexible implementation over Jackson (core and databind), now you can refer to deep fields/objects by annotation.

## Motivation

Jackson is a very useful library for converting JSON to POJO and vice-versa. However, it becomes challenging when we need to refer to deep fields in JSON.

### Example

#### JSON
```json
{
  "data": {
    "name": "Dumildes Paulo"
  }
}
```

#### Java
```java
import com.fasterxml.jackson.annotation.JsonProperty;

class StudentDTO {
    @JsonProperty("data") // is necessary
    StudentData data;
}

class StudentData {
    @JsonProperty("name")
    String name;
}
```

It's challenging when your JSON has deep fields with level 4+. `jackson-flex` offers a very flexible solution:

#### JSON
```json
{
  "data": {
    "name": "Dumildes Paulo"
  }
}
```

#### Java
```java
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dumijdev.jackson.flex.annotations.JsonFlexGetter;

class StudentDTOSimple {
    @JsonFlexGetter(src = "data.name", dst = "name")
    @JsonProperty("name")
    String name;
}
```

## How to Use It?

We use the provided JSON and `StudentDTOSimple` in the example:

### Usage Example

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dumijdev.jackson.flex.FlexObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        String json = "{\"data\":{\"name\":\"Dumildes Paulo\"}}";
        
        ObjectMapper mapper = new FlexObjectMapper();
        StudentDTOSimple studentDTOSimple = mapper.readValue(json, StudentDTOSimple.class);
        
        System.out.println(studentDTOSimple.name); // Output: Dumildes Paulo
    }
}
```

## Installation

Include the dependency in your `pom.xml` for Maven:

```xml
<dependency>
    <groupId>io.github.dumijdev</groupId>
    <artifactId>jackson-flex</artifactId>
    <version>0.0.1</version>
</dependency>
```

For Gradle:

```gradle
implementation 'io.github.dumijdev:jackson-flex:0.0.1'
```

## Annotations

### `@JsonFlexGetter`

`@JsonFlexGetter` is used to map deep fields from JSON to a flat Java object field.

#### Attributes:
- `src`: The JSON path to the source field.
- `dst`: The destination field in the Java object.

### Example

```java
class StudentDTOSimple {
    @JsonFlexGetter(src = "data.name", dst = "name")
    String name;
}
```

## Advanced Usage

### Nested Transformations

You can define nested transformations to map deeply nested JSON structures to your Java objects.

#### JSON
```json
{
  "school": {
    "student": {
      "details": {
        "name": "Dumildes Paulo",
        "age": 22
      }
    }
  }
}
```

#### Java
```java
import io.github.dumijdev.jackson.flex.annotations.JsonFlexGetter;

class Student {
    @JsonFlexGetter(src = "school.student.details.name", dst = "name")
    String name;
    
    @JsonFlexGetter(src = "school.student.details.age", dst = "age")
    int age;
}
```

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

Special thanks to the [Jackson](https://github.com/FasterXML/jackson) project for providing the foundation for JSON processing.
