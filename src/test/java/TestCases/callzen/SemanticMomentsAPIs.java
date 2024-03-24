package TestCases.callzen;

import CommonUtility.CommomREST;
import Pages.callzen.ConvozenAPIs;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class SemanticMomentsAPIs extends ConvozenAPIs {
    String jsonFilePath = "resource/TestData/sentences.json", smnObject = "SemanticText", insObject = "InstructionalText", org = "beta_new";
    String baseURL, email, password, userId, csrftoken, sessionId, momentId;
    Map<String, String> headers, cookiess,teamDataMap;
    String momentName = "Semantic Test Moment" + RandomStringUtils.randomAlphabetic(2).toLowerCase();
    List<String> transcriptList, chunkidList, feedBackChunkIdList, feedBacktranscriptList, feedBackhashIdList;
    String rad = Stream.of("Milind", "Ghonage", "Pune", "Mumbai").findAny().get();
    String bulkTagStartDate = "2024-03-9 13:28:00", bulkTagEndDate = "2024-03-12 13:28:00", activate = "true";


    @BeforeClass
    public void loadConfiguration() {
        baseURL = config.callzenProp("REST").getProperty("baseURL");
        email = config.callzenProp("REST").getProperty("email");
        password = config.callzenProp("REST").getProperty("password");
    }

    @Test(priority = 1)
    public void Login_With_Valid_credentials() {
        Response res = CommomREST.Post(baseURL, login, loginPayload(email, password), new HashedMap<>(), new HashedMap<>());
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
        List<Map<String, String>> momentList = new ArrayList<>();
        for (int page = 1; page <= 3; page++) {
            Response res = CommomREST.Post(baseURL, momentDetails, Moments_Data(String.valueOf(page)), cookiess, headers);
            for (int i = 0; i < 25; i++) {
                String momentId = res.jsonPath().getString("data.moments.edges[" + i + "].node.momentId");
                String momentName = res.jsonPath().getString("data.moments.edges[" + i + "].node.momentName");
                String searchType = res.jsonPath().getString("data.moments.edges[" + i + "].node.searchType");
                String status = res.jsonPath().getString("data.moments.edges[" + i + "].node.status");
                String activationStatus = res.jsonPath().getString("data.moments.edges[" + i + "].node.activationStatus");
                String feedbackStatus = res.jsonPath().getString("data.moments.edges[" + i + "].node.feedbackStatus");
                // Create a map to store moment data
                Map<String, String> momentMap = new LinkedHashMap<>();
                momentMap.put("momentName", momentName);
                momentMap.put("momentId", momentId);
                momentMap.put("searchType", searchType);
                momentMap.put("status", status);
                momentMap.put("activationStatus", activationStatus);
                momentMap.put("feedbackStatus", feedbackStatus);
                momentList.add(momentMap);
            }
            Assert.assertEquals(res.getStatusCode(), 200);
        }
        for (Map<String, String> moment : momentList) {
            if (moment.get("status").equals("active") && (moment.get("searchType").equals("semantic"))) {
                System.out.println("Active Moments : " + moment);
            }



        }
    }

    public void Verifying_Existing_MomentData2() {
        List<Map<String, String>> momentList = new ArrayList<>();
        for (int page = 1; page <= 2; page++) {
            Response res = CommomREST.Post(baseURL, momentDetails, Moments_Data(String.valueOf(page)), cookiess, headers);
            List<Map<String, String>> pageMoments = res.jsonPath().getList("data.moments.edges.node");
            momentList.addAll(pageMoments); // Add all page moments directly
        }
        for (Map<String, String> moment : momentList) {
            if ("active".equals(moment.get("status")) && "semantic".equals(moment.get("searchType"))) {
                System.out.println("Active Moments : " + moment);
            }
        }
    }


    @Test(priority = 3)
    public void MomentName_Availability_Verification() {
        Response momentValidResponse = CommomREST.Post(baseURL, momentValidation, momentValidationPayload(momentName), cookiess, headers);
        String msg = momentValidResponse.jsonPath().getString("message");
        Assert.assertEquals(momentValidResponse.getStatusCode(), 200);
        Assert.assertEquals(msg, "success");
    }

    @Test(priority = 4)
    public void Phrase_Suggestions_Verification() throws IOException {
        String payload = phrageSuggestion(randomSentences(jsonFilePath, smnObject));
        Response response = CommomREST.Post(baseURL, phraseSuggestions, payload, cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        String responseBody = response.getBody().asString();
        JsonElement jsonElement = JsonParser.parseString(responseBody);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray messages = jsonObject.getAsJsonArray("message");
        List<Map<String, Object>> transcriptChunks = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            JsonObject messageObject = messages.get(i).getAsJsonObject();
            String transcript = messageObject.get("transcript").getAsString();
            String chunkId = messageObject.get("chunkId").getAsString();
            double score = messageObject.get("score").getAsDouble();

            Map<String, Object> chunkMap = new HashMap<>();
            chunkMap.put("transcript", transcript);
            chunkMap.put("chunkId", chunkId);
            chunkMap.put("score", score);

            transcriptChunks.add(chunkMap);
        }
        Collections.sort(transcriptChunks, Comparator.comparingDouble(tc -> (double) tc.get("score")));
        Collections.reverse(transcriptChunks); // Sort transcriptChunks based on score in descending order
        transcriptList = new ArrayList<>();
        chunkidList = new ArrayList<>();

        for (Map<String, Object> tc : transcriptChunks) {
            transcriptList.add((String) tc.get("transcript"));
            chunkidList.add((String) tc.get("chunkId"));
        }
        for(String tr : transcriptList){
            System.out.println(tr);
        }
    }

    @Test(priority = 5)
    public void Create_SymenticMoment() throws InterruptedException {
        for (String chunk : transcriptList) {
            System.out.println("Creating function :: " + chunk);
        }
        Response response = CommomREST.Post(baseURL, createMoment, createSymenticMoments(momentName,
                transcriptList.subList(0, 5).toArray(new String[0]), chunkidList.subList(0, 5).toArray(new String[0])), cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        momentId = response.jsonPath().getString("message");
        System.out.println("Moment ID : " + momentId);
        Thread.sleep(2000);
    }

    @Test(priority = 6)
    public void Update_SemanticMoment() {
        String[][] data = new String[transcriptList.size()][3];
        for (int i = 0; i < transcriptList.size(); i++) {
            data[i][0] = transcriptList.get(i); // Transcript
            data[i][1] = chunkidList.get(i);    // Chunk ID
            data[i][2] = "0";                   // Assuming third value is always "0"
        }
        Response response = CommomREST.Post(baseURL, updateMoments, updateSymenticMoments(momentName, "positive", "semantic", "true",
                new String[]{"call"}, new String[]{"team"}, userId, momentId, data, new String[]{}), cookiess, headers);
        String activationStatus = response.jsonPath().getString("activationStatus");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(activationStatus, "FEEDBACK_PENDING");
    }

    @Test(priority = 7)
    public void Get_MomentFeedbacks() throws InterruptedException {
        Thread.sleep(2000);
        Map<String, String> queryParams = new HashedMap<>();
        queryParams.put("momentId", momentId);
        Response getFeedbackResponse = CommomREST.Get(baseURL, momentFeedback, cookiess, headers, queryParams);
        Assert.assertEquals(getFeedbackResponse.getStatusCode(), 200);

        String responseBody = getFeedbackResponse.getBody().asString();
        JsonElement jsonElement = JsonParser.parseString(responseBody);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject message = jsonObject.getAsJsonObject("message");
        JsonArray suggestions = message.getAsJsonArray("suggestions");

        feedBackChunkIdList = new ArrayList<>();
        feedBacktranscriptList = new ArrayList<>();
        feedBackhashIdList = new ArrayList<>();

        for (JsonElement suggestionElement : suggestions) {
            JsonObject suggestion = suggestionElement.getAsJsonObject();
            JsonArray suggestionList = suggestion.getAsJsonArray("suggestions");
            for (JsonElement suggestionItem : suggestionList) {
                JsonObject suggestionObject = suggestionItem.getAsJsonObject();
                String chunkId = suggestionObject.get("chunkId").getAsString();
                String transcript = suggestionObject.get("transcript").getAsString();
                String hashId = suggestionObject.get("hashId").getAsString();
                feedBackChunkIdList.add(chunkId);
                feedBacktranscriptList.add(transcript);
                feedBackhashIdList.add(hashId);
            }
        }
        Thread.sleep(2000);
    }

    @Test(priority = 8)
    public void Submit_MomentFeedback() {
        List<String> feedBackChunkIdList = new ArrayList<>();
        List<String> feedBacktranscriptList = new ArrayList<>();
        List<String> feedBackhashIdList = new ArrayList<>();
        String payload = feedbackMoment_round1(momentId, feedBackChunkIdList, feedBacktranscriptList, feedBackhashIdList);
        Response res = CommomREST.Post(baseURL, momentFeedback, payload, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 8)
    public void BulkTagging_SemanticMoment_Previous_Calls() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startDateTime = dateFormat.parse(bulkTagStartDate).getTime();
        long endDateTime = dateFormat.parse(bulkTagEndDate).getTime();
        long changedDateTime = startDateTime;
        long oneDayInMillis = 24 * 60 * 60 * 1000; // One day in milliseconds

        while (true) {
            Response response = CommomREST.Post(baseURL, bulkTagStats, totalCalls_Limit(momentId, changedDateTime, endDateTime), cookiess, headers);
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
        Response res = CommomREST.Post(baseURL, bulkMomentTagger, bulkTag_Moments(org, activate, momentId, userId, "semantic", changedDateTime, endDateTime), cookiess, headers);
        String activationMessage = res.jsonPath().getString("message");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(activationMessage, "[Transcripts Tagging Started]");
    }

    @Test(priority = 9)
    public void UnTag_SymenticMoment() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startDateTime = dateFormat.parse(bulkTagStartDate).getTime();
        long endDateTime = dateFormat.parse(bulkTagEndDate).getTime();
        Response res = CommomREST.Post(baseURL, untag, untag_Moments(momentId, startDateTime, endDateTime, false), cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        String untagMessage = res.jsonPath().getString("message");
        Assert.assertEquals(untagMessage, "untagging in progress");
    }

    @Test(priority = 10)
    public void Activate_SemanticMoment() throws InterruptedException {
        Thread.sleep(2000);
        String Payload = Activate_Moments(momentId, userId, momentName, activate, "semantic", "true");
        Response response = CommomREST.Post(baseURL, activateMoment, Payload, cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        String activationStatus = response.jsonPath().getString("message");
        Assert.assertEquals(activationStatus, "Moment got activated - true");
    }

    @Test(priority = 11)
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

    @Test(priority = 12)
    public void Edit_Moments_verification() throws InterruptedException {
        String conversationType = "call", filterScope = "team";
        String editedMomentPayload = edit_Moments(momentName, userId, momentId, conversationType, filterScope, "customer", "agent", teamDataMap);
        Response res = CommomREST.Post(baseURL, editMoment, editedMomentPayload, cookiess, headers);
        String editMomentId = res.jsonPath().getString("message");
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(editMomentId, momentId);
    }

    @Test(priority = 13)
    public void verifying_Deactivate_Moments_Functionality() {
        String Payload = Activate_Moments(momentId, userId, momentName, "false", "semantic", "true");
        Response response = CommomREST.Post(baseURL, activateMoment, Payload, cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        String activationStatus = response.jsonPath().getString("message");
        Assert.assertEquals(activationStatus, "Moment got activated - false");
    }

    @Test(priority = 14)
    public void Delete_SymenticMoments() {
        String Payload = delete_Moments(momentName, userId, momentId);
        Response res = CommomREST.Delete(baseURL, deleteMoment, Payload, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        String deleteStatus = res.jsonPath().getString("message");
        Assert.assertEquals(deleteStatus, momentId + " deleted successfully");
    }

    public void verify_PhraseSearch_Playground_Functionality() {
        Response res = CommomREST.Post(baseURL, momentOnPhrase, PlaygroundPhraseSimilarity("semantic", momentId, "discounted price"), cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        res.jsonPath().getString("message");
    }
    @Test(priority = 15)
    public void verifying_Logout_Functionality() {
        Response res = CommomREST.Get(baseURL, logout, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        String logoutStatus = res.jsonPath().getString("msg");
        Assert.assertEquals(logoutStatus, "User Successfully Logged Out.");
    }


}
