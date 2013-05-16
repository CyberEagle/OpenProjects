package br.com.cybereagle.commonlibrary.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Reader;
import java.lang.reflect.Type;

@Singleton
public class GsonUtils {

    private Gson gson;
    private JsonParser parser;

    @Inject
    public GsonUtils(Gson gson) {
        this.gson = gson;
        this.parser = new JsonParser();
    }

    public <T> T deserializeJsonElementToObject(JsonElement rootElement, String memberName, Type type) {
        T result = null;
        if (isMemberNameSet(memberName)) {
            JsonElement jsonElement = extractMemberElementFromJsonElement(memberName, rootElement);
            result = gson.fromJson(jsonElement, type);
        } else {
            result = gson.fromJson(rootElement, type);
        }
        return result;
    }

    public <T> T deserializeJsonReaderToObject(Reader jsonReader, String memberName, Type type) {
        T result = null;
        if (isMemberNameSet(memberName)) {
            JsonElement jsonElement = extractMemberElementFromJsonReader(memberName, jsonReader);
            result = gson.fromJson(jsonElement, type);
        } else {
            result = gson.fromJson(jsonReader, type);
        }
        return result;
    }

    public <T> T deserializeJsonStringToObject(String jsonString, String memberName, Type type) {
        T result = null;
        if (isMemberNameSet(memberName)) {
            JsonElement jsonElement = extractMemberElementFromJsonString(memberName, jsonString);
            result = gson.fromJson(jsonElement, type);
        } else {
            result = gson.fromJson(jsonString, type);
        }
        return result;
    }

    public JsonElement extractMemberElementFromJsonReader(String memberName, Reader json) {
        JsonElement jsonElement = parser.parse(json);
        return extractMemberElementFromJsonElement(memberName, jsonElement);
    }

    public JsonElement extractMemberElementFromJsonString(String memberName, String json) {
        JsonElement jsonElement = parser.parse(json);
        return extractMemberElementFromJsonElement(memberName, jsonElement);
    }

    public JsonElement extractMemberElementFromJsonElement(String memberName, JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject().get(memberName);
        }
        return null;
    }

    private boolean isMemberNameSet(String memberName) {
        return StringUtils.isNotEmpty(memberName);
    }

    public JsonParser getParser() {
        return parser;
    }
}
