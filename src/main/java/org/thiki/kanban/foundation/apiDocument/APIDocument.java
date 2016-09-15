package org.thiki.kanban.foundation.apiDocument;

import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.FileUtil;

import java.net.URI;
import java.util.*;

/**
 * Created by xubt on 9/14/16.
 */
public class APIDocument {
    public static final String API_DOCUMENT_FILE_PATH = "src/test/resources/APIDocument.md";

    public static Map<String, List> themes = new HashMap<>();

    public static URI url;
    public static Object request;
    public static Object response;
    public static String scenario;
    public static String testCaseName;
    public static String theme;

    public static void newRequest() {
        url = null;
        request = null;
        response = null;
    }

    public static void endRequest() {

        System.out.println(scenario);
        System.out.println(testCaseName);
        System.out.println(url);
        System.out.println(JSONObject.toJSONString(request, true));
        System.out.println(JSONObject.toJSONString(response, true));

        Map<String, Object> testCase = new HashMap<>();
        testCase.put("testCaseName", testCaseName);
        testCase.put("scenario", scenario);
        testCase.put("url", url);
        testCase.put("request", JSONObject.toJSONString(request, true));
        testCase.put("response", JSONObject.toJSONString(response, true));

        List<Map<String, Object>> testCases = themes.get(theme);
        if (testCases == null) {
            testCases = new ArrayList();
        }
        testCases.add(testCase);
        themes.put(theme, testCases);

        FileUtil.saveFile(API_DOCUMENT_FILE_PATH, buildMDContent(themes));
    }

    private static String buildMDContent(Map<String, List> themes) {

        themes = new TreeMap<>(themes);
        String content = "";

        for (Map.Entry<String, List> entry : themes.entrySet()) {
            content += "\n# " + NumberFormatter.toCN(entry.getKey()) + " #\n";
            List<Map> testCases = entry.getValue();
            for (Map testCase : testCases) {
                content += "\n## " + testCase.get("scenario") + " ##\n";
                content += "\n**用例名称**\n" + testCase.get("testCaseName") + "\n";
                content += "\n**URL**\n" + testCase.get("url") + "\n";
                content += "\n**请求体**\n" + "```\n" + testCase.get("request") + "\n```\n" + "\n";
                content += "\n**响应体**\n" + "```\n" + testCase.get("response") + "\n```\n" + "\n";
                content += "\n-------";
            }
        }

        return content;
    }
}
