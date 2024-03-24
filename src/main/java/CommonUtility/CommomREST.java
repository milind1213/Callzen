package CommonUtility;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.io.output.WriterOutputStream;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Utility.TestListeners.*;
import static io.restassured.RestAssured.given;

public class CommomREST extends CommonConstants {
    protected RequestSpecification req;
    protected Response res;
    StringWriter sw;
    PrintStream ps;

    private String jsonFilePath = "resource/TestData/sentences.json", smnObject = "KeyWords";

    private static RequestSpecification getRequestSpecification(String baseURL, String endPoint, Map<String, String> cookiess, Map<String, String> headers) {
        return RestAssured.given().log().all()
                .baseUri(baseURL)
                .basePath(endPoint)
                .headers(headers)
                .cookies(cookiess);
    }

    public static Response Post(String baseURL, String endPoint, String requestPayload, Map<String, String> cookiess, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        printRequestLogInReport(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.post();
        response.getBody().asString();
        printResponseLogInReport(response);
        return response;
    }
    public static Response post(String baseURL, String endPoint, String requestPayload, Map<String, String> cookiess, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        printRequestLogInReport(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.post();
        response.getBody().prettyPrint();
        return response;
    }

    public static Response post1(String baseURL, String endPoint, String requestPayload, Map<String, String> cookiess, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        printRequestLogInReport(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.post();
        response.getBody().toString();
        return response;
    }

    public static Response Delete(String baseURL, String endPoint, String requestPayload, Map<String, String> cookiess, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        printRequestLogInReportGet(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.delete();
        response.getBody().asString();
        printResponseLogInReport(response);
        return response;
    }

    public static Response Get(String baseURL, String endPoint, Map<String, String> cookiess, Map<String, String> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        printRequestLogInReportGet(requestSpecification);
        Response response = requestSpecification.get();
        printResponseLogInReport(response);
        return response;
    }

    public static Response Get(String baseURL, String endPoint, Map<String, String> cookiess, Map<String, String> headers, Map<String, String> queryParameters) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        requestSpecification.queryParams(queryParameters);
        Response response = requestSpecification.get();
        printResponseLogInReport(response);
        return response;
    }

    public static Response GetNew(String baseURL, String endPoint, Map<String, String> cookiess, Map<String, String> headers, Map<String, String> queryParameters) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, cookiess, headers);
        requestSpecification.queryParams(queryParameters);
        Response response = requestSpecification.get();
        response.getBody().prettyPrint();
        return response;
    }

    public Response post(RequestSpecification request, String resourceURL) {
        res = request.post(resourceURL);
        return res;
    }

    protected Response get(RequestSpecification spec, String resourceURL) {
        sw = new StringWriter();
        ps = new PrintStream(new WriterOutputStream(sw), true);
        res = given().spec(spec).filter(new RequestLoggingFilter(ps)).log().all().when().get(resourceURL);
        sw.toString();
        res.prettyPrint();
        return res;
    }
    protected Response postNew(RequestSpecification spec, String resourceURL) {
        sw = new StringWriter();
        ps = new PrintStream(new WriterOutputStream(sw), true);
        res = given().spec(spec).filter(new RequestLoggingFilter(ps)).log().all().when().post(resourceURL);
        sw.toString();
        res.prettyPrint();
        return res;
    }

    protected Response put(RequestSpecification spec, String resourceURL) {
        sw = new StringWriter();
        ps = new PrintStream(new WriterOutputStream(sw), true);
        res = given().spec(spec).filter(new RequestLoggingFilter(ps)).log().all().when().put(resourceURL);
        return res;
    }
    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }
    public Response getCurrentResponse() {
        return res;
    }
    public void waitFor(int i) {
        try {
            Thread.sleep(1000 * i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object parseJson(Response response, String path) {
        return response.jsonPath().get(path);
    }
    public String getJsonPathValue(String res, String key) {
        JsonPath json = new JsonPath(res.toString());
        Object value = json.get(key);
        return String.valueOf(value);
    }

    public String getRedirectedURL(String link) throws IOException {
        HttpURLConnection con = (HttpURLConnection) (new URL(link).openConnection());
        con.setInstanceFollowRedirects(false);
        con.connect();
        int responseCode = con.getResponseCode();
        System.out.println(responseCode);
        String location = con.getHeaderField("Location");
        System.out.println(location);
        return location;
    }


    public String decrypt(int xdLength, String splitterString, String encryptedText) {
        String decoded = null;
        try {
            decoded = new String(org.apache.commons.codec.binary.Base64.decodeBase64(splitterString));
            System.out.println(decoded);
            String one = encryptedText.split(decoded)[0];
            String two = encryptedText.replace(one, "").replace(decoded, "");

            String encryptionString = new String(
                    org.apache.commons.codec.binary.Base64.decodeBase64(two.substring(0, xdLength)),
                    StandardCharsets.UTF_8).replace("new Function('return \"", "").replace("\"')", "");
            String third = two.substring(xdLength, two.length());
            return getDecryptedString(one + third, new String(
                    org.apache.commons.codec.binary.Base64.decodeBase64(encryptionString), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDecryptedString(String inputText, String secret) {
        SecretKey key = new SecretKeySpec(secret.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            Thread.sleep(1000);
            return new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(inputText)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void printRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        logInfoDetails("Base URL is  : " + queryableRequestSpecification.getBaseUri());
        logInfoDetails("Base Path is  : " + queryableRequestSpecification.getBasePath());
        logInfoDetails("Headers are  : ");
        logHeaders(queryableRequestSpecification.getHeaders().asList());
        logInfoDetails("Request Body is :  ");
        logJson(queryableRequestSpecification.getBody());
    }

    private static void printResponseLogInReport(Response response) {
        logInfoDetails("Response status is  : " + response.getStatusCode());
        logInfoDetails("Response Headers are :  ");
        logHeaders(response.getHeaders().asList());
        logInfoDetails("Response Body is :  ");
        logJson(response.getBody().prettyPrint());
    }

    private static void printRequestLogInReportGet(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        logInfoDetails("Base URL is  : " + queryableRequestSpecification.getBaseUri());
        logInfoDetails("Base Path is  : " + queryableRequestSpecification.getBasePath());
        logInfoDetails("Headers are  : ");
        logHeaders(queryableRequestSpecification.getHeaders().asList());
    }

    protected static void logInfoDetails(String log) {
        extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.PINK));
    }

    protected static void addLogs(String label, Map<String,Object> log) {
        extentTest.get().info(MarkupHelper.createLabel(label, ExtentColor.PURPLE));
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : log.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        extentTest.get().info(MarkupHelper.createLabel(stringBuilder.toString(), ExtentColor.TRANSPARENT));
    }


    static void logJson(String json) {
        extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }
    static void logHeaders(List<Header> headersList) {
       String[][] arrayHeaders = headersList.stream().map(header -> new String[]{header.getName(), header.getValue()})
                .toArray(String[][]::new);
        extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
    }

    public static String randomSentences(String filePath,String object) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonObject jsonObject = new Gson().fromJson(jsonContent, JsonObject.class);
        JsonArray momentTextsArray = jsonObject.getAsJsonArray(object);
        Random random = new Random();
        int index = random.nextInt(momentTextsArray.size());
        String momentText = momentTextsArray.get(index).getAsString();
        return momentText;
    }

    public static List<String> getRandomKeywords(String filePath, String object, int count) throws IOException {
        List<String> keywords = new ArrayList<>();
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JsonObject jsonObject = new Gson().fromJson(jsonContent, JsonObject.class);
        JsonArray keywordArray = jsonObject.getAsJsonArray(object);
        Random random = new Random();
        int arraySize = keywordArray.size();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(arraySize);
            String keyword = keywordArray.get(index).getAsString();
            keywords.add(keyword);
        }
        return keywords;
    }

}