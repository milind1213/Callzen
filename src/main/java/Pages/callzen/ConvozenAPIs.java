package Pages.callzen;
import CommonUtility.CommomREST;
import Utility.PropertyFileReaders;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.*;

public class ConvozenAPIs extends CommomREST {
    public PropertyFileReaders config = new PropertyFileReaders();
    RequestSpecification spec;
    RequestSpecBuilder builder;
    public String login = "auth/login", logout = "auth/logout";
    public static String momentValidation = "moments/check-create-validation/", phraseSuggestions = "suggestions/phrase_suggestions/";
    public  static String createMoment = "moments/create_moment/", updateMoments = "moments/update_moment/";
    public static String momentFeedback = "moments/moment-feedback/", activateMoment = "moments/activate_moment/";
    public static String editMoment = "moments/edit_moment_group/", deleteMoment = "moments/drop_moment/";
    public static String getTeam = "users/team/", KeywordSentence = "suggestions/keyword-sentence-example/";
    public static String momentDetails = "graphql-api/", bulkTagStats = "moments/bulk_tagger_call_stats/";
    public static String bulkMomentTagger = "moments/bulk-moment-tagger/", untag = "moments/untag-moment/";
    public static String momentOnPhrase = "moments/moment-on-phrase/", generateInstructionalFeedback = "moments/generate_instructional_feedback/";
    public static String validateInstructionMoment = "moments/validate_instruction_moment/";


    public static String getChecklistListQuery() {
        String payload = "query {" +
                "  checklistList(" +
                "    callMeta_cz_creationAttributes_cz_campaignName: [\"Legal\"]," +
                "    callMeta_cz_creationAttributes_cz_modeOfCalling: [\"Inbound\", \"Auto\", \"Manual\"]" +
                "  ) {" +
                "    totalCount" +
                "    edges {" +
                "      node {" +
                "        data" +
                "      }" +
                "    }" +
                "  }" +
                "}";
        return payload;
    }


    public static String getChecklistData() {
        String graphqlQuery ="{\n" +
                "    \"query\": \"\\n        query {\\n          checklistList {\\n            totalCount\\n            edges {\\n              node {\\n                data\\n              }\\n            }\\n          }\\n        } \\n\\t\\t\\t\"\n" +
                "}";
        return graphqlQuery;
    }

    public static String generate_Instructional_Feedback(String momentId, String momentName, String sentece, List<String> examples, String userid) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("momentId", momentId);
        payload.put("momentName", momentName);
        payload.put("instructions", sentece);
        payload.put("examples", examples);
        payload.put("userId", userid);
        return new Gson().toJson(payload);
    }

    public static String create_instruction_moment(String momentName, String createdBy, String momentEmotion, boolean globalMoment, String instructions, List<String> examples) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("chunkPhrases", new ArrayList<>());
        payload.put("momentName", momentName);
        payload.put("momentDescription", "");
        payload.put("source", "moment_screen");
        payload.put("createdBy", createdBy);
        payload.put("momentEmotion", momentEmotion);
        payload.put("searchType", "INSTRUCTIONAL_MOMENT");
        payload.put("mustContain", new ArrayList<>());
        payload.put("mustNotContain", new ArrayList<>());
        payload.put("containsAnyOneOf", new ArrayList<>());
        payload.put("globalMoment", globalMoment);
        payload.put("conversationType", Arrays.asList("call"));
        payload.put("momentFilterScope", Arrays.asList(""));
        payload.put("teamName", null);
        payload.put("isFollowUp", false);
        Map<String, String> parentMoment = new LinkedHashMap<>();
        parentMoment.put("label", "");
        parentMoment.put("value", "");
        payload.put("parentMoment", parentMoment);
        Map<String, List<String>> attributes = new LinkedHashMap<>();
        attributes.put("speaker", Arrays.asList("agent"));
        payload.put("attributes", attributes);
        payload.put("enrichedData", null);
        payload.put("instructions", instructions);
        payload.put("examples", examples);
        payload.put("isNex", false);
        return new Gson().toJson(payload);
    }

    public static String validate_Instruction_Moment(String cdrId, List<String> examples, String instruction) {
        Map<String, Object> audioMap = new LinkedHashMap<>();
        audioMap.put("audioUrl", cdrId);
        audioMap.put("examples", examples);
        audioMap.put("instruction", instruction);
        return new Gson().toJson(audioMap);
    }

    public static String create_KeywordMoments(String momentName, String createdBy, String emotion, List<String> keyWords, String globalMoment, Map<String, String> parentMoment, List<String> speakers) {
        String parentMomentJson = new Gson().toJson(parentMoment);
        String payload = "{\n" +
                "    \"chunkPhrases\": [],\n" +
                "    \"momentName\": \"" + momentName + "\",\n" +
                "    \"momentDescription\": \"\",\n" +
                "    \"source\": \"moment_screen\",\n" +
                "    \"createdBy\": \"" + createdBy + "\",\n" +
                "    \"momentEmotion\": \"" + emotion + "\",\n" +
                "    \"searchType\": \"fuzzy_match\",\n" +
                "    \"mustContain\": [],\n" +
                "    \"mustNotContain\": [],\n" +
                "    \"containsAnyOneOf\": " + listToJsonArray(keyWords) + ",\n" +
                "    \"globalMoment\": " + globalMoment + ",\n" +
                "    \"conversationType\": [\n" +
                "        \"call\"\n" +
                "    ],\n" +
                "    \"momentFilterScope\": [],\n" +
                "    \"teamName\": null,\n" +
                "    \"isFollowUp\": true,\n" +
                "    \"parentMoment\": " + parentMomentJson + ",\n" +
                "    \"attributes\": {\n" +
                "        \"speaker\": " + listToJsonArray(speakers) + "\n" +
                "    },\n" +
                "    \"enrichedData\": null\n" +
                "}";
        return payload;
    }

    private static String listToJsonArray(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]"; // Empty array
        }
        StringBuilder array = new StringBuilder("[");
        for (int i = 0; i < list.size() - 1; i++) {
            array.append("\"").append(list.get(i)).append("\",");
        }
        array.append("\"").append(list.get(list.size() - 1)).append("\"]"); // No trailing comma
        return array.toString();
    }

    public static String getKeywordSentence_mustContain(List<String> sentences, String conversationType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("mustContain", sentences);
        payload.put("mustNotContain", List.of()); // Initialize with an empty list
        payload.put("containsAnyOneOf", List.of()); // Initialize with an empty list
        payload.put("conversationType", conversationType);
        return new Gson().toJson(payload);
    }

    public static String getKeywordSentence_containsAnyOneOf(List<String> sentences, String conversationType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("mustContain", List.of());
        payload.put("mustNotContain", List.of()); // Initialize with an empty list
        payload.put("containsAnyOneOf", sentences); // Initialize with an empty list
        payload.put("conversationType", conversationType);
        return new Gson().toJson(payload);
    }

    public String PlaygroundPhraseSimilarity(String momentType, String momentId, String text) {
        Map<String, Object> map = new HashMap<>();
        map.put("searchType", momentType);
        map.put("momentId", momentId);
        map.put("text", text);
        return new Gson().toJson(map);
    }

    public String untag_Moments(String momentId, long startTime, long endTime, Boolean active) {
        Map<String, Object> map = new HashMap<>();
        map.put("momentId", momentId);
        map.put("startStamp", startTime);
        map.put("endStamp", endTime);
        map.put("checklistRetag", active);
        return new Gson().toJson(map);
    }

    public String bulkTag_Moments(String ornName, String activate, String momentIds, String activatedBy, String searchType, long startTime, long endtime) {
        List<String> momentIdList = new ArrayList<String>();
        momentIdList.add(momentIds);
        Map<String, Object> map = new HashMap<>();
        map.put("organisationName", ornName);
        map.put("activate", activate);
        map.put("momentIds", momentIdList);
        map.put("activatedBy", activatedBy);
        map.put("globalMoment", true);
        map.put("searchType", searchType);
        map.put("startStamp", startTime);
        map.put("endStamp", endtime);
        return new Gson().toJson(map);
    }

    public static String loginPayload(String email, String password) {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        return new Gson().toJson(payload);
    }


    public static String momentValidationPayload(String momentName) {
        String requestPayload = "{\"momentName\":\"" + momentName + "\"}";
        return requestPayload;
    }

    public static String phrageSuggestion(String sentence) {
        List<String> sentenceList = new ArrayList<>();
        sentenceList.add(sentence);
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("conversationType", "call");
        payload.put("sentences", sentenceList);
        payload.put("deepSearch", false);
        payload.put("searchType", "semantic");
        return new Gson().toJson(payload);
    }

    public static String totalCalls_Limit(String momentId, long startTime, long endTime) {
        List<String> momentIdsList = new ArrayList<>();
        momentIdsList.add(momentId);
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("momentIds", momentIdsList);
        payload.put("startStamp", startTime);
        payload.put("endStamp", endTime);
        return new Gson().toJson(payload);
    }


    public static String createSymenticMoments(String momentName, String[] phrases, String[] chunkIds) {
        List<Map<String, Object>> chunkPhrasesList = new ArrayList<>();
        for (int i = 0; i < phrases.length; i++) {
            Map<String, Object> chunkPhrase = new HashMap<>();
            chunkPhrase.put("phrase", phrases[i]);
            chunkPhrase.put("chunkId", chunkIds[i]);
            chunkPhrase.put("start", 0);
            chunkPhrase.put("end", -1);
            chunkPhrasesList.add(chunkPhrase);
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("chunkPhrases", chunkPhrasesList);
        payload.put("momentName", momentName);
        payload.put("momentDescription", "");
        payload.put("source", "moment_screen");
        payload.put("createdBy", "064ea5d6-f049-41f0-ba9d-dfc3651dc92b");
        payload.put("momentEmotion", "positive");
        payload.put("searchType", "semantic");
        payload.put("mustContain", new ArrayList<>());
        payload.put("mustNotContain", new ArrayList<>());
        payload.put("containsAnyOneOf", new ArrayList<>());
        payload.put("globalMoment", true);
        List<String> conversationTypeList = new ArrayList<>();
        conversationTypeList.add("call");
        payload.put("conversationType", conversationTypeList);
        List<String> momentFilterScopeList = new ArrayList<>();
        momentFilterScopeList.add("");
        payload.put("momentFilterScope", momentFilterScopeList);
        payload.put("teamName", null);
        payload.put("enrichedData", null);

        return new Gson().toJson(payload);
    }

    public String updateSymenticMoments(String momentName, String momentEmotion, String searchType, String globalMoment, String[] conversationType, String[] momentFilterScope, String updatedBy, String momentId, String[][] addPhrases, String[] deletePhrases) {
        List<Map<String, Object>> addList = new ArrayList<>();
        for (String[] addPhrase : addPhrases) {
            Map<String, Object> phraseMap = new HashMap<>();
            phraseMap.put("phrase", addPhrase[0]);
            phraseMap.put("chunkId", addPhrase[1]);
            phraseMap.put("start", 0);
            phraseMap.put("end", -1);
            phraseMap.put("groupId", addPhrase[2]);
            phraseMap.put("forceAdd", true);
            addList.add(phraseMap);
        }
        List<String> deleteList = List.of(deletePhrases);
        Map<String, Object> chunkPhrasesMap = new HashMap<>();
        chunkPhrasesMap.put("add", addList);
        chunkPhrasesMap.put("delete", deleteList);
        List<String> conversationTypeList = List.of(conversationType);
        List<String> momentFilterScopeList = List.of(momentFilterScope);
        Map<String, Object> payload = new HashMap<>();
        payload.put("chunkPhrases", chunkPhrasesMap);
        payload.put("momentName", momentName);
        payload.put("momentDescription", "");
        payload.put("source", "moment_screen");
        payload.put("createdBy", "");
        payload.put("momentEmotion", momentEmotion);
        payload.put("searchType", searchType);
        payload.put("mustContain", new ArrayList<>());
        payload.put("mustNotContain", new ArrayList<>());
        payload.put("containsAnyOneOf", new ArrayList<>());
        payload.put("globalMoment", Boolean.valueOf(globalMoment));
        payload.put("conversationType", conversationTypeList);
        payload.put("momentFilterScope", momentFilterScopeList);
        payload.put("teamName", null);
        payload.put("enrichedData", null);
        payload.put("updatedBy", updatedBy);
        payload.put("momentId", momentId);

        return new Gson().toJson(payload);
    }

    public static String feedbackMoment_round1(String momentId, List<String> chunkIds, List<String> transcripts, List<String> hashIds) {
        List<Map<String, Object>> suggestionsList = new ArrayList<>();
        for (int i = 0; i < chunkIds.size(); i++) {
            Map<String, Object> suggestionMap = new HashMap<>();
            suggestionMap.put("chunkId", chunkIds.get(i));
            suggestionMap.put("transcript", transcripts.get(i));
            suggestionMap.put("hashId", hashIds.get(i));
            suggestionMap.put("version", 2);
            suggestionsList.add(suggestionMap);
        }
        Map<String, Object> clusterMap = new HashMap<>();
        clusterMap.put("clusterId", "0");
        clusterMap.put("scoreRange", List.of(0.9, 0.95));
        clusterMap.put("nsuggestions", chunkIds.size());
        clusterMap.put("suggestions", suggestionsList);
        clusterMap.put("version", 2);

        Map<String, Object> payload = new HashMap<>();
        payload.put("momentId", momentId);
        payload.put("suggestions", List.of(clusterMap));

        return new Gson().toJson(payload);
    }


    public static String Activate_Moments(String momentId, String userID, String momentName, String activate, String type, String global) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("activate", activate);
        map.put("conversationType", "call");
        map.put("momentId", momentId);
        map.put("activatedBy", userID);
        map.put("momentName", momentName);
        map.put("globalMoment", global);
        map.put("searchType", type);
        return new Gson().toJson(map);
    }

    public static String edit_Moments(String momentName, String updatedBy, String momentId, String conversationType, String filterScope, String customer, String agent, Map<String, String> teamData) {
        JsonObject payload = new JsonObject();
        payload.addProperty("momentName", momentName);
        payload.addProperty("updatedBy", updatedBy);
        payload.addProperty("momentId", momentId);
        payload.addProperty("momentDescription", "");
        payload.addProperty("globalMoment", false);
        payload.addProperty("momentEmotion", "positive");
        JsonArray conversationArray = new JsonArray();
        conversationArray.add(conversationType);
        payload.add("conversationType", conversationArray);
        JsonArray filterScopeArray = new JsonArray();
        filterScopeArray.add(filterScope);
        payload.add("momentFilterScope", filterScopeArray);
        JsonObject attributes = new JsonObject();
        JsonArray campaignNameArray = new JsonArray();
        campaignNameArray.add("OwnerFRMcampaign");
        attributes.add("campaignName", campaignNameArray);
        JsonArray modeOfCallingArray = new JsonArray();
        modeOfCallingArray.add("Pstn-To-Pstn");
        modeOfCallingArray.add("inboundCallForward");
        attributes.add("modeOfCalling", modeOfCallingArray);
        JsonArray speakerArray = new JsonArray();
        speakerArray.add(customer);
        speakerArray.add(agent);
        attributes.add("speaker", speakerArray);
        payload.add("attributes", attributes);
        JsonArray teamNameArray = new JsonArray();
        for (Map.Entry<String, String> entry : teamData.entrySet()) {
            JsonObject teamObject = new JsonObject();
            teamObject.addProperty("label", entry.getValue());
            teamObject.addProperty("value", entry.getKey());
            teamNameArray.add(teamObject);
        }
        payload.add("teamName", teamNameArray);
        payload.add("enrichedData", null);
        return new Gson().toJson(payload);
    }

    public static String delete_Moments(String momentName, String userId, String momentId) {
        Map<String, Object> attributesMap = new HashMap<>();
        attributesMap.put("campaignName", new String[]{"OwnerFRMcampaign"});
        attributesMap.put("modeOfCalling", new String[]{"Pstn-To-Pstn", "inboundCallForward"});
        attributesMap.put("processName", new String[]{null});
        attributesMap.put("speaker", new String[]{"customer", "agent"});
        Map<String, Object> payload = new HashMap<>();
        payload.put("momentId", momentId);
        payload.put("momentName", momentName);
        payload.put("source", "moment_screen");
        payload.put("deletedBy", userId);
        payload.put("attributes", attributesMap);
        payload.put("conversationType", "call");

        return new Gson().toJson(payload);
    }

    public String getCallMetaData(){
        String payload ="{\"query\":\"\\n        query {\\t\\n          callsUniqueDataPhoneNumberWise(page:{number:1,perPage:25},sort:{callTime:\\\"desc\\\"},transcribed:false,showProcessedCalls:false,duration:{start:0})\\n\\t\\t\\t\\t\\t{\\n            totalCount\\n            momentObj {\\n              node {\\n                data\\n              }\\n            }\\n            checklistObj {\\n              node {\\n                data\\n              }\\n            }\\n            actionableResults {\\n              node {\\n                data\\n              }\\n            }\\n            edges {\\n              node {\\n                audioDuration\\n                mayaTranscriptionStatus\\n                taggedMoments {\\n                  momentName\\n                  momentEmotion\\n                  momentId\\n                }\\n                checklist {\\n                  checklistId\\n                  checklistName\\n                  coverageScore\\n                  priorityOrder\\n                  moments {\\n                    momentId\\n                    momentName\\n                    weight\\n                    order\\n                    type\\n                  }\\n                }\\n                channel1Speaker\\n                channel2Speaker\\n                callScore\\n                language\\n                languageDict {\\n                  english\\n                  hindi\\n                  kannada\\n                  tamil\\n                  telugu\\n                }\\n                callMeta {\\n                  creationAttributes\\n{campaignName\\nmodeOfCalling\\nprocessName\\n}\\ncontactInfo\\n{customer\\n{phoneMasked\\nphoneEncrypted\\nname\\nid\\nemail\\n}agent\\n{name\\nid\\n}}\\ndispositionData\\n{disposeName\\nfirstDisposeName\\nsecondDisposeName\\nthirdDisposeName\\n}\\nothers\\n{callCount\\nagentTalkTimeSec\\ndisconnectedBy\\n}\\nzenConfig\\n{abrupttedBy\\ncallTranscriptionQuality\\nlongSilence\\nhighSilence\\nchannel1Emotion\\nchannel2Emotion\\n}\\nstartStamp\\n\\nmayaCdrid\\n\\n                  \\n                }\\n                callProgress {\\n                    InstructionalMomentsTagged\\n                    checklistTagged\\n                    fuzzyMomentsTagged\\n                    momentsTagged\\n                    splitMomentsTagged\\n                    split\\n                  }\\n              }\\n            }\\n          }\\n        }\\n      \"}";
        return payload;
    }

    public static String Moments_Data(String pageNumber) {
        String payload = "{\"query\":\"\\n        query {\\n\\t\\t\\t\\t\\tmoments(\\n\\t\\t\\t\\t\\t\\tsmartMoment:false,page:{number:" + pageNumber + ",perPage:25},conversationType:\\\"call\\\"\\n\\t\\t\\t\\t\\t) {\\n              totalCount\\n              edges {\\n                node {\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tisActionable\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tscript\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tactivatedBy\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tactivationDate\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tactivationStatus\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tbulkJobStatus\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tclusterId\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tcreatedBy\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tcreatedDate\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tglobalMoment\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tisSystemGenerated\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentDescription\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentEmotion\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentId\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentName\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentThreshold\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tsearchType\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tsource\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tstatus\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tupdatedBy\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tupdatedDate\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tconversationType\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentFilterScope\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tmomentFeedbackScore\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tfeedbackStatus\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tbulkJobEndStamp\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tbulkJobCompletionDate\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tversion\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tinstructionalFeedbackScore\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tuntagStatus\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tisNex\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tisFollowUp\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tparentMoment{\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t\\tlabel\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t\\tvalue\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t}\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tenrichedData\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tteamName{\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t\\tlabel\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t\\tvalue\\n\\t\\t\\t\\t\\t\\t\\t\\t\\t}\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tattributes\\n{campaignName\\nmodeOfCalling\\nprocessName\\nspeaker\\n}\\n\\t\\t\\t\\t\\t\\t\\t\\t\\tadditionalAttributes {callMeta_dispositionData_disposeName\\ncallMeta_dispositionData_firstDisposeName\\ncallMeta_others_callCount\\n}\\n                }\\n              }\\n            }\\n          }\\n          \"}";
        return payload;
    }






    public Response getCitiesList(String baseURL, String basePath, String authId) {
        builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL)
                .setBasePath(basePath)
                .addHeader("Accept", "application/json")
                .addQueryParam("serviceType", "RENTAL_AGREEMENT")
                .addHeader("homeservices-auth-id", authId);
        spec = builder.build();
        return get(spec, "");
    }

    public Response addLead(String baseURL, String basePath, String leadName, String leadPhone, String leadEmail, String authId) {
        String payload = "{\n" +
                "    \"userName\": \" " + leadName + " \",\n" +
                "    \"userPhone\": \" " + leadPhone + "\",\n" +
                "    \"serviceTypes\": [\n" +
                "    \"RENTAL_AGREEMENT\"\n" +
                "    ],\n" +
                "    \"city\": \"BANGALORE\",\n" +
                "    \"userEmail\": \" " + leadEmail + " \",\n" +
                "    \"phoneCountryCode\": \"91\",\n" +
                "    \"userType\": \"OWNER\",\n" +
                "    \"source\": \"DETAILS_PAGE_FEEDBACK\"\n" +
                "}";
        builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL)
                .setBasePath(basePath)
                .setContentType("application/json;charset=UTF-8")
                .addHeader("homeservices-auth-id", authId)
                .setBody(payload);
        spec = builder.build();
        return postNew(spec, " ");
    }


}
