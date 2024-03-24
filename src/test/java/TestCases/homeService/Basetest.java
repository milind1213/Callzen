package TestCases.homeService;

import CommonUtility.CommonConstants;
import Pages.callzen.POJO.MomentData;
import Pages.callzen.POJO.Payloads;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.stream.Stream;

import static CommonUtility.CommonConstants.MOMENTS_VALUES.MOMENTS;

public class Basetest {
    private static String[] names = {"Milind", "Ghonage", "Pune", "Mumbai"};
    private static Random random = new Random();
    private static String rad = names[random.nextInt(names.length)];
    private static String name = Stream.of("Milind", "Ghonage", "Pune", "Mumbai").findAny().get();

    public static void main(String[] args) throws JsonProcessingException {
        MomentData payload =  Payloads.pojoPayload();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

        String jsonData = writer.writeValueAsString(payload);
        System.out.println(jsonData);
    }
    @Test
    public void payloads() {
        System.out.println(CommonConstants.getRandomInstructional().getText());
        System.out.println(MOMENTS.VALUE());

    }
}
