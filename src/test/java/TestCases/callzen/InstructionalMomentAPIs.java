package TestCases.callzen;
import CommonUtility.CommomREST;
import Pages.callzen.ConvozenAPIs;
import Utility.TestListeners;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Listeners(TestListeners.class)
public class InstructionalMomentAPIs extends ConvozenAPIs {
    private static String baseURL, email, password, userId, csrftoken, sessionId, momentId;
    private Map<String, String> headers, cookiess;
    private List<String> examples = Arrays.asList("plan enquiry");
    private String momentName = "Instructional Moment auto" + RandomStringUtils.randomAlphabetic(2).toLowerCase();
    private String Sentence = "did customer discussed with agent related to  mail id change or update?";
    private String cdrId = "02e041a9-8a0f-45d5-b063-c0226bf67665";
    @BeforeClass
    public void loadConfiguration() {
        baseURL = config.callzenProp("REST").getProperty("baseURL");
        email = config.callzenProp("REST").getProperty("email");
        password = config.callzenProp("REST").getProperty("password");
    }

    @Test(priority =1)
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
    public void MomentName_Availability_Validation() {
        Response momentValidResponse = Post(baseURL, momentValidation, momentValidationPayload(momentName), cookiess, headers);
        String msg = momentValidResponse.jsonPath().getString("message");
        Assert.assertEquals(momentValidResponse.getStatusCode(), 200);
        Assert.assertEquals(msg, "success");
    }

    @Test(priority = 3)
    public void Instructional_MomentValidation() {
        Response momentValidResponse = Post(baseURL, validateInstructionMoment, validate_Instruction_Moment(cdrId, examples, Sentence), cookiess, headers);
        String msg = momentValidResponse.jsonPath().getString("message");
        String callLink = momentValidResponse.jsonPath().getString("result.callLink");
        List<String> taggedTranscript = momentValidResponse.jsonPath().getList("result.taggedTranscript");

        Assert.assertEquals(momentValidResponse.getStatusCode(), 200);
        Assert.assertEquals(msg, "transcripts_tagged");
        Assert.assertEquals(callLink, cdrId);
        Assert.assertTrue(taggedTranscript.size() > 0);
        // Checking if the sentence or any of the words/phrases from examples are present in the tagged transcript
        boolean isContentPresent = false;
        for (String transcript : taggedTranscript) {
            if (transcript.toLowerCase().contains(Sentence.toLowerCase())) {
                isContentPresent = true;
                break;
          }
            for (String example : examples) {
                for (String word : example.split("\\s+")) {
                    if (transcript.toLowerCase().contains(word.toLowerCase())) {
                        isContentPresent = true;
                        break;
               }}}
             }
        Assert.assertTrue(isContentPresent, "Sentence or words not found in tagged transcript");
    }
   @Test(priority = 4)
    public void Create_Instructional_PrimeMoment() {
        String payload = create_instruction_moment(momentName,userId,"positive",true,Sentence,examples);
        Response response = CommomREST.Post(baseURL,createMoment,payload, cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
        momentId = response.jsonPath().getString("message");
    }
   @Test(priority =5)
    public void Generate_InstructionalMoment_Feedback(){
        String payload = generate_Instructional_Feedback(momentId,momentName,Sentence,examples,userId);
        Response response = CommomREST.Post(baseURL,generateInstructionalFeedback,payload,cookiess, headers);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    public void Delete_InstructionalMoment_Functionality() {
        String Payload = delete_Moments(momentName, userId, momentId);
        Response res = CommomREST.Delete(baseURL, deleteMoment, Payload, cookiess, headers);
        Assert.assertEquals(res.getStatusCode(), 200);
        String deleteStatus = res.jsonPath().getString("message");
        Assert.assertEquals(deleteStatus, momentId + " deleted successfully");
    }

}
