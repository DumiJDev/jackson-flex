package io.github.dumijdev.jackson.flex;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dumijdev.jackson.flex.annotations.JsonFlexGetter;

import java.io.*;
import java.net.URL;
import java.util.Objects;

public class FlexObjectMapper extends ObjectMapper {
    @Override
    public <T> T readValue(File src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        var tree = super.readTree(src);
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }

    @Override
    public <T> T readValue(URL src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        var tree = super.readTree(src);
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }

    @Override
    public <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        var tree = super.readTree(content);
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }

    @Override
    public <T> T readValue(Reader src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        var tree = super.readTree(src);
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }

    @Override
    public <T> T readValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        var tree = super.readTree(src);
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }

    @Override
    public <T> T readValue(byte[] src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        var tree = super.readTree(src);
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }

    @Override
    public <T> T readValue(DataInput src, Class<T> valueType) throws IOException {
        var tree = super.readTree(src.readUTF());
        var newNode = internalFlexReadTree(tree, valueType);
        return super.treeToValue(newNode, valueType);
    }


    private JsonNode internalFlexReadTree(JsonNode node, Class<?> clazz) {
        var object = super.createObjectNode();

        for (var field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonFlexGetter.class)) {
                var annotation = field.getAnnotation(JsonFlexGetter.class);
                String[] srcPath = annotation.src().split("\\.");
                String[] dstPath = annotation.dst().split("\\.");

                var currentNode = node;
                for (String step : srcPath) {
                    currentNode = currentNode.path(step);
                }
                if (!isNullNode(currentNode))
                    addToNode(object, dstPath, currentNode);
            } else if (field.isAnnotationPresent(JsonProperty.class)) {
                var jsonProperty = field.getAnnotation(JsonProperty.class);
                var path = jsonProperty.value();

                path = "".equals(path) ? field.getName() : path;

                if (!"".equals(path)) {
                    var currentNode = node.path(path);

                    if (!isNullNode(currentNode))
                        addToNode(object, path, currentNode);
                }
            } else if (field.isAnnotationPresent(JsonGetter.class)) {
                var jsonGetter = field.getAnnotation(JsonGetter.class);
                var path = jsonGetter.value();

                path = "".equals(path) ? field.getName() : path;

                if (!"".equals(path)) {
                    var currentNode = node.path(path);

                    if (!isNullNode(currentNode))
                        addToNode(object, path, currentNode);
                }
            } else {
                var path = field.getName();
                var currentNode = node.path(path);

                if (!isNullNode(currentNode))
                    addToNode(object, path, currentNode);

            }
        }

        return object;
    }

    private boolean isNullNode(JsonNode node) {
        return node == null || node.isNull() || node.isMissingNode();
    }

    private void addToNode(ObjectNode rootNode, String[] path, JsonNode value) {
        ObjectNode currentNode = rootNode;
        for (int i = 0; i < path.length - 1; i++) {
            String key = path[i];
            var nextNode = currentNode.get(key);
            if (nextNode == null || !nextNode.isObject()) {
                nextNode = JsonNodeFactory.instance.objectNode();
                currentNode.set(key, nextNode);
            }
            currentNode = (ObjectNode) nextNode;
        }

        currentNode.set(path[path.length - 1], !value.isMissingNode() ? value : JsonNodeFactory.instance.nullNode());
    }

    private void addToNode(ObjectNode rootNode, String key, JsonNode value) {
        rootNode.set(key, !value.isMissingNode() ? value : JsonNodeFactory.instance.nullNode());
    }

}
