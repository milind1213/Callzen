package TestCases.callzen;

import CommonUtility.CommomREST;
import Pages.callzen.ConvozenAPIs;
import Utility.TestListeners;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Listeners(TestListeners.class)
public class CallPage extends ConvozenAPIs {
    String baseURL, email, password, userId, csrftoken, sessionId, TotalCalls;
    Map<String, String> headers, cookiess;
    Map<String, Object> logMap, momentNameIds, checklistNameIds;
    List<Map<String, Object>> taggedMomentsList, taagedChecklistList, taggedMomentsUndercheckList;


    @BeforeClass
    public void loadConfiguration() {
        baseURL = config.callzenProp("REST").getProperty("baseURL");
        email = config.callzenProp("REST").getProperty("email");
        password = config.callzenProp("REST").getProperty("password");
    }

    @Test(priority = 1)
    public void Login_with_valid_credentials() {
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
    public void GetChecklistData() {
        Response res = post1(baseURL, momentDetails, getChecklistData(), cookiess, headers);
        int totalCount = res.jsonPath().getInt("data.checklistList.totalCount");
        Map<String, String> checklistData = new LinkedHashMap<>();
        Map<String, Map<String, String>> dataMap = res.jsonPath().getMap("data.checklistList.edges.node.data");
        for (Map.Entry<String, Map<String, String>> entry : dataMap.entrySet()) {
            String checklistId = entry.getValue().get("checklistId");
            String checklistName = entry.getValue().get("checklistName");
            checklistData.put(checklistId, checklistName);
        }
        int actualTotalCount = checklistData.size();
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(totalCount, actualTotalCount);

        logMap = new LinkedHashMap<>();
        logMap.put("TotalNumber of Checklist ", actualTotalCount);
        addLogs("CheckList Data ", logMap);
    }

    @Test(priority = 3)
    public void getTeamwiseChecklist() {
        Response res = Post(baseURL, momentDetails, getChecklistListQuery(), cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
    }


    public void GetCallData() {
        Response res = post1(baseURL, momentDetails, getCallMetaData(), cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        TotalCalls = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.totalCount");
        //  ((ArrayList)((LinkedHashMap)((LinkedHashMap) response.getData()).get("Mumbai")).get("Mumbai")).get(1);

        int actionableCount = 0;
        momentNameIds = new LinkedHashMap<>();
        Map<String, Map<String, Object>> getMomentMap = res.jsonPath().getMap("data.callsUniqueDataPhoneNumberWise.momentObj.node.data");
        for (Map.Entry<String, Map<String, Object>> mapIds : getMomentMap.entrySet()) {
            String momentId = mapIds.getKey();
            Map<String, Object> innerMap = mapIds.getValue();
            String name = (String) innerMap.get("name");
            boolean isActionable = (boolean) innerMap.get("isActionable");
            momentNameIds.put("momentId", momentId);
            momentNameIds.put("name", name);

            if (isActionable) {
                actionableCount++;
            }
        }
        System.out.println("Number of Active moments: " + actionableCount);
        int checkListCount = 0;
        checklistNameIds = new LinkedHashMap<>();
        Map<String, Map<String, Object>> getchecklistMap = res.jsonPath().getMap("data.callsUniqueDataPhoneNumberWise.checklistObj.node.data");
        for (Map.Entry<String, Map<String, Object>> checklistIds : getchecklistMap.entrySet()) {
            String checklistId = checklistIds.getKey();
            Map<String, Object> innerMap = checklistIds.getValue();
            String name = (String) innerMap.get("name");
            checklistNameIds.put("checklistId", checklistId);
            checklistNameIds.put("name", name);
            checkListCount++;
        }
        System.out.println("Number of CheckList: " + checkListCount);
        int calls = res.jsonPath().getList("data.callsUniqueDataPhoneNumberWise.edges").size();
        System.out.println("Number of Call :  " + calls);
        int transcribedCount = 0;
        for (int i = 0; i < calls; i++) {
            String transcriptionStatus = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.mayaTranscriptionStatus");
            transcribedCount++;
        }
        taggedMomentsList = new ArrayList<>();
        int taggedMomentCount = 0;
        for (int i = 0; i < calls; i++) {
            for (int j = 0; j < transcribedCount; j++) {
                Map<String, Object> taggedMomentMap = new LinkedHashMap<>();
                String taggedMomentName = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.taggedMoments[" + j + "].momentName");
                String taggedMomentId = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.taggedMoments[" + j + "].momentId");
                String momentEmotion = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.taggedMoments[" + j + "].momentEmotion");

                taggedMomentMap.put("taggedMomentName", taggedMomentName);
                taggedMomentMap.put("taggedMomentId", taggedMomentId);
                taggedMomentMap.put("momentEmotion", momentEmotion);
                taggedMomentsList.add(taggedMomentMap);
                taggedMomentCount++;
            }
        }
        System.out.println("Number of taggedMoment: " + taggedMomentCount);
        taagedChecklistList = new ArrayList<>();

        int taggedChecklistCount = 0;
        for (int i = 0; i < calls; i++) {
            for (int j = 0; j < transcribedCount; j++) {
                Map<String, Object> taggedchecklistMap = new LinkedHashMap<>();
                String checklistId = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.checklist[" + j + "].checklistId");
                String checklistName = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.checklist[" + j + "].checklistName");
                String priorityOrder = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.checklist[" + j + "].priorityOrder");

                if (checklistId != null || checklistName != null) {
                    taggedchecklistMap.put("checklistId", checklistId);
                    taggedchecklistMap.put("checklistName", checklistName);
                    taggedchecklistMap.put("priorityOrder", priorityOrder);
                    taagedChecklistList.add(taggedchecklistMap);
                    System.out.println("Checklist ID : " + checklistId);
                    System.out.println("Checklist name : " + checklistName);
                }
                taggedChecklistCount++;
                int momentSize = res.jsonPath().getList("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.checklist[" + j + "].moments").size();
                for (int k = 0; k < momentSize; k++) {
                    String momentId = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.checklist[" + j + "].moments[" + k + "].momentId");
                    String momentName = res.jsonPath().getString("data.callsUniqueDataPhoneNumberWise.edges[" + i + "].node.checklist[" + j + "].moments[" + k + "].momentName");
                    System.out.println("MomentId : " + momentId);
                    System.out.println("MomentName : " + momentName);
                }
            }
        }
        System.out.println("Number of taggedChecklist: " + taggedChecklistCount);
    }


}