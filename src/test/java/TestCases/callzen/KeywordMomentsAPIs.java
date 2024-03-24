package TestCases.callzen;

import CommonUtility.CommomREST;
import Pages.callzen.ConvozenAPIs;
import Utility.TestListeners;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Listeners(TestListeners.class)
public class KeywordMomentsAPIs extends ConvozenAPIs {
    private String jsonFilePath = "resource/TestData/sentences.json", keywords= "KeyWords";
    public static String baseURL, email, password, userId, csrftoken, sessionId, momentId;
    private Map<String, String> headers, cookiess, parentMoments, teamDataMap;
    private Map<String, List<String>> attributes,limitedAttributes;

    // private List<String> keywordList = Arrays.asList("hello", "good morning", "good evening");
    private String momentName = "KeyMoments Test Moment" + RandomStringUtils.randomAlphabetic(2).toLowerCase();
    private List<String> speakers = Arrays.asList("agent", "customer"), conversationType = Arrays.asList("call"), filterScope = Arrays.asList("team");
    private String org = "beta_new", globalMoment = "true", emotion = "positive", activate = "true";
    public String bulkTagStartDate = "2024-03-9 13:28:00", bulkTagEndDate = "2024-03-12 13:28:00";


    @BeforeClass
    public void loadConfiguration() {
        baseURL = config.callzenProp("REST").getProperty("baseURL");
        email = config.callzenProp("REST").getProperty("email");
        password = config.callzenProp("REST").getProperty("password");
    }

    @Test(priority = 1)
    public void Login_With_Valid_Credentials() {
        Response res = Post(baseURL, login, loginPayload(email, password), new HashedMap<>(), new HashedMap<>());
        Assert.assertEquals(res.getStatusCode(), 200);
        String loginMessage = res.jsonPath().getString("message");
        userId = res.jsonPath().getString("data.user_id");
        csrftoken = res.getCookie("csrftoken");
        sessionId = res.getCookie("sessionid");
        headers = new HashedMap<>();
        headers.put("X-CSRFToken", csrftoken);

        cookiess = new HashedMap<>();
        cookiess.put("csrftoken", csrftoken);
        cookiess.put("sessionid", sessionId);

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(loginMessage, "Login successful");

    }

    @Test(priority = 2)
    public void Get_Existing_MomentData() {
        // Initialize the attributes map
        attributes = new HashMap<>();
        limitedAttributes = new HashMap<>();
        for (int page = 1; page <= 1; page++) {
            Response res = Post(baseURL, momentDetails, Moments_Data(String.valueOf(page)), cookiess, headers);
            for (int i = 0; i <25; i++) {
                // Extract moment details
                String momentName = res.jsonPath().getString("data.moments.edges[" + i + "].node.momentName");
                String momentId = res.jsonPath().getString("data.moments.edges[" + i + "].node.momentId");
                Map<String, List<String>> attributesMap = res.jsonPath().getMap("data.moments.edges[" + i + "].node.attributes");
                // Iterate through the attributes map and add them to the attributes map
                for (Map.Entry<String, List<String>> entry : attributesMap.entrySet()) {
                    String attributeName = entry.getKey();
                    List<String> attributeValues = entry.getValue();
                    List<String> attributeList = attributes.getOrDefault(attributeName, new ArrayList<>());
                    attributeList.addAll(attributeValues);
                    attributes.put(attributeName, attributeList);
                    limitedAttributes.put(attributeName, attributeList);
                }
                // Create parentMoments map
                parentMoments = new LinkedHashMap<>();
                parentMoments.put("label", momentName);
                parentMoments.put("value", momentId);
            }
            Assert.assertEquals(res.getStatusCode(), 200);
        }
    }

    @Test(priority = 3)
    public void MomentName_Available_Verification() {
        Response momentValidResponse = Post(baseURL, momentValidation, momentValidationPayload(momentName), cookiess, headers);
        String msg = momentValidResponse.jsonPath().getString("message");
        Assert.assertEquals(momentValidResponse.getStatusCode(), 200);
        Assert.assertEquals(msg, "success");
    }

   @Test(priority = 4)
    public void KeyMoments_SamplePhrases_with_containsAnyOneOf_Options() throws IOException {
        String payload = getKeywordSentence_containsAnyOneOf(getRandomKeywords(jsonFilePath,keywords,3),"call");
        Response res = Post(baseURL, KeywordSentence,payload, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        List<Map<String, String>> samplePhraseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String chunkId = res.jsonPath().getString("message[" + i + "].chunkId");
            String transcript = res.jsonPath().getString("message[" + i + "].transcript");
            Map<String, String> map = new LinkedHashMap<>();
            map.put("chunkId", chunkId);
            map.put("transcript", transcript);
            samplePhraseList.add(map);
        }
        boolean containsAnyKeyword = false;
        for (Map<String, String> phraseMap : samplePhraseList) {
            String transcript = phraseMap.get("transcript");
            for (String keyword : getRandomKeywords(jsonFilePath,keywords,3)) {
                if (transcript.contains(keyword)) {
                    containsAnyKeyword = true;
                    break;
                }
            }
            if (containsAnyKeyword) {
                break;
            }
        }
        Assert.assertTrue(containsAnyKeyword, "At least contains any keyword");
    }

   @Test(priority = 5)
    public void KeyMoments_SamplePhrases_with_MustContains_Options() throws IOException {
        String payload = getKeywordSentence_mustContain(getRandomKeywords(jsonFilePath,keywords,3),"call");
        Response res = Post(baseURL, KeywordSentence, payload, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        List<Map<String, String>> samplePhraseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String chunkId = res.jsonPath().getString("message[" + i + "].chunkId");
            String transcript = res.jsonPath().getString("message[" + i + "].transcript");
            Map<String, String> map = new LinkedHashMap<>();
            map.put("chunkId", chunkId);
            map.put("transcript", transcript);
            samplePhraseList.add(map);
        }
        for (Map<String, String> phraseMap : samplePhraseList) {
            String transcript = phraseMap.get("transcript");
            if (transcript == null) {
                System.out.println("Transcript is null for phrase: " + phraseMap);
                continue; // Skip to the next phrase
            }
            boolean containsAllKeywords = true;
            for (String keyword : getRandomKeywords(jsonFilePath,keywords,3)) {
                if (!transcript.contains(keyword)) {
                    containsAllKeywords = false;
                    break;
                }
            }
            Assert.assertTrue(containsAllKeywords, "Each sample phrase must contain all keywords");
        }
        Assert.assertEquals(res.getStatusCode(), 200);
    }

  @Test(priority = 6)
    public void Create_Keyword_Moments() throws IOException {
        List<String> keywordList =  getRandomKeywords(jsonFilePath,keywords,3);
        Response res = Post(baseURL, createMoment, create_KeywordMoments(momentName, userId, emotion, keywordList, globalMoment, parentMoments, speakers), cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        momentId = res.jsonPath().getString("message");
        String activationStatus = res.jsonPath().getString("activationStatus");
        Assert.assertEquals(activationStatus, "SUCCESS");
    }


  @Test(priority = 7)
    public void Activate_KeyWordMoment() throws InterruptedException {
        Thread.sleep(2000);
        String Payload = Activate_Moments(momentId, userId, momentName, activate, "fuzzy_match", globalMoment);
        Response response = Post(baseURL, activateMoment, Payload, cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        String activationStatus = response.jsonPath().getString("message");
        Assert.assertEquals(activationStatus, "Moment got activated - true");
    }

  @Test(priority = 8)
    public void BulkTagging_KeywordMoment() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startDateTime = dateFormat.parse(bulkTagStartDate).getTime();
        long endDateTime = dateFormat.parse(bulkTagEndDate).getTime();
        long changedDateTime = startDateTime;
        long oneDayInMillis = 24 * 60 * 60 * 1000; // One day in milliseconds
        while (true) {
            Response response = Post(baseURL, bulkTagStats, totalCalls_Limit(momentId, changedDateTime, endDateTime), cookiess, headers);
            String totalCallsStr = response.jsonPath().getString("message.total_calls");
            String bulkLimitStr = response.jsonPath().getString("message.bulk_limit");

            int totalCalls = Integer.parseInt(totalCallsStr);
            int bulkLimit = Integer.parseInt(bulkLimitStr);

            if (totalCalls == 0) {
                changedDateTime -= 86400000; // Decrement by 1 day (in milliseconds)
                System.out.println("Total calls zero, moving start date to: " + dateFormat.format(new Date(changedDateTime)));
            } else if (totalCalls > bulkLimit) {
                if (endDateTime - changedDateTime <= oneDayInMillis) {
                    changedDateTime += 3600000; // Increment by 1 hour (in milliseconds)
                    System.out.println("Less than one day left. Switching to hourly increment.");
                } else {
                    changedDateTime += 86400000; // Increment by 1 day (in milliseconds)
                }
            } else {
                break;
            }
        }
        Response res = Post(baseURL, bulkMomentTagger, bulkTag_Moments(org, activate, momentId, userId, "keyword", changedDateTime, endDateTime), cookiess, headers);
        String activationMessage = res.jsonPath().getString("message");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(activationMessage, "[Transcripts Tagging Started]");
    }

 @Test(priority = 9)
    public void UnTagging_KeyWordMoment() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startDateTime = dateFormat.parse(bulkTagStartDate).getTime();
        long endDateTime = dateFormat.parse(bulkTagEndDate).getTime();
        Response res = Post(baseURL, untag, untag_Moments(momentId, startDateTime, endDateTime, false), cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        String untagMessage = res.jsonPath().getString("message");
        Assert.assertEquals(untagMessage, "untagging in progress");
    }

    @Test(priority = 10)
    public void Get_Teams_details() {
        Response res = CommomREST.Get(baseURL, getTeam, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        JsonElement jsonElement = JsonParser.parseString(res.getBody().asString());
        JsonArray resultsArray = jsonElement.getAsJsonObject().getAsJsonArray("results");
        teamDataMap = new LinkedHashMap<>();
        int count = 0;
        for (JsonElement element : resultsArray) {
            if (count >= 2) break;
            JsonObject teamObject = element.getAsJsonObject();
            String id = teamObject.get("id").getAsString();
            String name = teamObject.get("name").getAsString();
            teamDataMap.put(id, name);
            count++;
        }
    }

    @Test(priority = 11)
    public void Edit_Moments_verification() throws InterruptedException {
        String conversationType = "call", filterScope = "team";
        String editedMomentPayload = edit_Moments(momentName, userId, momentId, conversationType, filterScope, "customer", "agent", teamDataMap);
        Response res = CommomREST.Post(baseURL, editMoment, editedMomentPayload, cookiess, headers);
        String editMomentId = res.jsonPath().getString("message");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(editMomentId, momentId);
    }

    @Test(priority = 14)
    public void Delete_KeywordMoment() {
        String Payload = delete_Moments(momentName, userId, momentId);
        Response res = CommomREST.Delete(baseURL, deleteMoment, Payload, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        String deleteStatus = res.jsonPath().getString("message");
        Assert.assertEquals(deleteStatus, momentId + " deleted successfully");
    }

    private Map<String, List<String>> limitAttributes(Map<String, List<String>> attributes, int limit) {
        Map<String, List<String>> limitedAttributes = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
            String attributeName = entry.getKey();
            List<String> attributeValues = entry.getValue();
            List<String> limitedValues = new ArrayList<>();
            for (int i = 0; i < Math.min(limit, attributeValues.size()); i++) {
                String value = attributeValues.get(i);
                if (value != null) { // Check if the value is not null
                    limitedValues.add(value);
                }
            }
            if (!limitedValues.isEmpty()) { // Check if the limitedValues list is not empty
                limitedAttributes.put(attributeName, limitedValues);
            }
        }
        return limitedAttributes;
    }


}
