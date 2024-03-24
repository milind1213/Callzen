package CommonUtility;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class CommonConstants {
      public static SEMANTIC_SENTENCE RANDOM_SEMANTIC() {
        Random random = new Random();
        return SEMANTIC_SENTENCE.values()[random.nextInt(SEMANTIC_SENTENCE.values().length)];
    }

   public enum MOMENTS_VALUES{
       SEMANTIC_MOMENT("Semantic Moment"),MOMENTS("Moments"),GLOBAL("Global"),POSITIVE("Positive"),AAD_ANYWAY("Add anyway"),SUGGEST_SIMMILER_PHRASES("Suggest similar phrases"),
       DELET_PHRASES("Delete phrases");


       String value;
       MOMENTS_VALUES(String s) {
           value = s;
       }
       public String VALUE() {
           return value;
       }
   }


    public enum PROJECT
    {
        HS, LD, PS, RSB, SSN, common;
    }

    public enum SEMANTIC_SENTENCE {
        DISCOUNT("How much percentage discount will I get?"),
        URGENT_REQUIREMENT("I have an urgent requirement"),
        SERVICES_OFFERED("Which services do you offer better?"),
        SUBSCRIPTION_INTEREST("Are you interested in taking the subscription?"),
        PURCHASE_PLANNING("Planning to purchase or buy"),
        CLEANING_PAINTING_SERVICE("Regarding the painting, cleaning service"),
        RENTAL_AGREEMENT("Rental agreement service"),
        PAYMENT_CHARGES("Payment and service charges"),
        SLOT_SELECTION("Service slot selection"),
        PROPERTY_AVAILABLE("Property is available for rent"),
        BOOKING_CONFIRMATION("Booking or payment confirmation"),
        PAINTING_COST_ESTIMATE("Painting service estimate cost"),
        CLEANING_APPOINTMENT("Schedule a cleaning appointment"),
        BOOKING_CANCELLATION("Booking cancellation"),
        WEEKEND_OFFERS("Any weekend offers"),
        GOOD_MORNING("Good morning! I'm calling to inquire"),
        DISCOUNTED_QUOTATION("discounted quotation"),
        SUBSCRIPTION_OPTIONS("subscription options"),
        CANCEL_BOOKING("Unfortunately, I need to cancel my booking."),
        PROPERTY_AVAILABILITY("Is the property still available for rent?"),
        PROVIDE_PHONE_NUMBER("Could you please provide your phone number?"),
        THANK_YOU("Thank you for your assistance."),
        SUBSCRIPTION_INTERESTED("I'm interested in the subscription, package or plan"),
        HOME_INSPECTION("schedule a home inspection"),
        RENOVATION_QUOTATION("quotation for the renovation services"),
        OFFER_INSURANCE("Do you offer insurance"),
        GET_TRIAL("Can I get a trial"),
        PROCEED("can we proceed with this"),
        WRONG_NUMBER("I'm sorry, this is the wrong number"),
        NO_REQUIREMENT("I have no requirement as of now, will see for the future."),
        THANK_YOU_DEMO("Thank you for the demo."),
        SERVICE_BETTER("Service could be better."),
        REMAINING_PAYMENT("I'm ready to make the remaining payment."),
        CONFIRM_AMOUNT("Can you confirm the amount?"),
        UPGRADE_SUBSCRIPTION("upgrade my subscription or plan"),
        CONSULTATION_SERVICES("Need consultation services");

        private final String sentence;

        SEMANTIC_SENTENCE(String sentence) {
            this.sentence = sentence;
        }

        public String SENTENCE() {
            return sentence;
        }
    }

    public static INSTRUCTIONAL_SENTENCE getRandomInstructional() {
        Random random = new Random();
        return INSTRUCTIONAL_SENTENCE.values()[random.nextInt(INSTRUCTIONAL_SENTENCE.values().length)];
    }

    public enum INSTRUCTIONAL_SENTENCE {
        DISCUSS_MAIL_CHANGE("Did customer discuss with agent related to mail ID change or update?"),
        DISCUSS_SUBSCRIPTION("Did agent discuss with customer about subscription?"),
        INQUIRE_DISCOUNT("Did customer inquire about discount?"),
        DISCUSS_PAYMENT("Did agent discuss with customer about payment?"),
        AGREE_TO_PAYMENT("Customer agreed to make payment"),
        INQUIRE_PROPERTY_AVAILABILITY("Did agent ask if the property is available for rent or sale?"),
        CONFIRM_PAYMENT("Customer confirmed payment?"),
        INQUIRE_PAINTING_SERVICES("Did customer ask related to painting services?"),
        GREET_CUSTOMER("Did agent greet the customer?"),
        REQUEST_CALLBACK("Did customer request a callback?"),
        REQUEST_CANCELLATION("Did customer request for cancellation?"),
        INQUIRY_HOME_INSPECTION("Inquiry about home inspection services."),
        INQUIRE_DISCOUNTED_QUOTATION("Did customer inquire about discounted quotation?"),
        DISCUSS_FREE_ASSISTANCE("Agent discussed the free assistance available."),
        REQUEST_BETTER_DEALS("Customer requested guidance on better deals."),
        INQUIRE_SLOT_AVAILABILITY("Customer asked about slot availability for service."),
        REQUEST_TRIAL_OFFER("Customer requested a trial offer before commitment."),
        OFFER_SERVICE_DEMO("Did agent offer a demo of the service?"),
        INFORM_LIMITED_TIME_OFFER("Agent informed about a limited-time offer."),
        INQUIRE_EXCLUSIVE_DEAL("Customer enquired about exclusive deal?"),
        PROVIDE_PACKAGE_DETAILS("Agent provided details about packages"),
        REQUEST_UPGRADE_SUBSCRIPTION("Customer requested an upgrade plan or subscription"),
        OFFER_CONSULTATION("Agent offered a consultation for better understanding."),
        REQUEST_SERVICE_QUOTE("Customer asked for a quote for the service."),
        PROVIDE_SERVICE_ESTIMATE("Agent provided an estimate for the service."),
        REQUEST_FEATURE_COMPARISON("Customer requested a feature comparison."),
        SCHEDULE_INSPECTION_APPOINTMENT("Agent scheduled an inspection appointment."),
        REQUEST_PAINTING_QUOTATION("Customer requested painting service quotation?"),
        DISCUSS_RENOVATION_SERVICES("Agent discussed renovation services."),
        INQUIRE_MOVING_SERVICES("Customer inquired about moving services."),
        INQUIRE_PACKERS_MOVERS("Customer asked about packers and movers."),
        PROVIDE_SHIPMENT_INFO("Agent provided information about shipment process."),
        INQUIRE_DELIVERY_OPTIONS("Customer asked about delivery options.");

        private final String text;

        INSTRUCTIONAL_SENTENCE(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public enum KEYWORD_TEXT {
        GOOD_MORNING("Good morning"), GOOD_NIGHT("Good night"), PAYMENT("Payment"), SUBSCRIPTION("Subscription"), CANCELLED("Cancelled"),
        PROPERTY("Property"), PHONE_NUMBER("Phone Number"), COMPLETED("Completed"), SHIFTING_HOGAYA("Shifting hogaya"), REMAINING_PAYMENT("Remaining payment"),
        WRONG_NUMBER("Wrong number"), NO_REQUIREMENT("No requirement as of now"), CALL_BACK("Call back"), REFUND("Refund"), CLEANING_SERVICE("Cleaning service"),
        PAINTING_SERVICE("Painting service"), HOME_INSPECTION("Home Inspection"), FREE_INSPECTION("Free of cost"), CUSTOMIZATION("Customization"), TRIAL_OFFER("Trial offer"),
        DISCOUNTED_QUOTATION("Discounted quotation"), FREE_OF_COST("Free of cost"), NON_CHARGEABLE("Non-chargeable"), BETTER_OFFER("Better offer"),
        BETTER_DEAL("Better deal"), FREE_ASSISTANCE("Free assistance"), ASSISTANCE("Assistance"), GUIDANCE("Guidance"), OFFER("Offer"), AGREEMENT("Agreement"),
        CLOSING("Closing"), FOLLOW_UP("Follow-up"), FEEDBACK("Feedback"), SATISFACTION("Satisfaction"), REFERENCE("Reference"), PRODUCT_INQUIRY("Product inquiry"),
        DISCOUNTED_PRICE("Discounted price"), SERVICE_CHARGES("Service charges"), SLOT_AVAILABILITY("Slot availability"), DEMO("Demo"), SPECIAL_OFFER("Special offer"),
        LIMITED_TIME_OFFER("Limited-time offer"), EXCLUSIVE_DEAL("Exclusive deal"), PACKAGE("Package"), UPGRADE("Upgrade"), CONSULTATION("Consultation"),
        QUOTE("Quote"), ESTIMATE("Estimate"), FEATURE_COMPARISON("Feature comparison"), INSPECTION("Inspection"), QUOTATION("Quotation"), MAINTENANCE("Maintenance"),
        RENOVATION("Renovation"), MOVING("Moving"), CHANGE_EMAIL("Change/update email"), CHANGE_ADDRESS("Change/update address"), RELOCATION("Relocation"),
        PACKING("Packing"), SHIPMENT("Shipment"), DELIVERY("Delivery"), MOVING_TRUCK("Moving truck"), LOADING_UNLOADING("Loading and unloading"), FURNITURE("Furniture"),
        TRANSIT_INSURANCE("Transit insurance");

        private final String keyword;

        KEYWORD_TEXT(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }

    public enum MOMENT_KEYS {
        MOMENT_ID("momentId"), MOMENT_NAME("momentName"), INSPECTION("instructions"), EXAMPLE("example"), USER_ID("userId"), CHUNK_PHRASES("chunkPhrases"),
        MOMENT_DESCRIPTION("momentDescription"), SOURCE("source"), CREATED_BY("createdBy"), MOMENT_EMOTION("momentEmotion"), SEARCH_TYPE("searchType"),
        MUST_CONTAIN("mustContain"), MUST_NOT_CONTAIN("mustNotContain"), CONTAINS_ANY_ONE_OF("containsAnyOneOf"), GLOBAL_MOMENT("globalMoment"),
        CONVERSATION_TYPE("conversationType"), MOMENT_FILTER_SCOPE("momentFilterScope"), TEAM_NAME("teamName"), IS_FOLLOW_UP("isFollowUp"), PARENT_MOMENT("parentMoment"),
        ATTRIBUTES("attributes"), ENRICHED_DATA("enrichedData"), AUDIO_URL("audioUrl"), TRANSCRIPT("transcript"), HASH_IDS("hashIds"), CLUSTER_ID("clusterId"),
        SCORE_RANGE("scoreRange"), SUGGESTIONS("suggestions"), VERSION("version"), ACTIVATE("activate"), SERVICE_TYPES("serviceTypes"), CITY("city"),
        PHONE_COUNTRY_CODE("phoneCountryCode"), USER_TYPE("userType"), PHRASE("phrase"), START("start"), END("end"), GROUP_ID("groupId"), FORCE_ADD("forceAdd"),
        UPDATED_BY("updatedBy"), DELETED_BY("deletedBy"), IS_ACTIONABLE("isActionable"), SCRIPT("script"), ACTIVATED_BY("activatedBy"), ACTIVATION_DATE("activationDate"),
        ACTIVATION_STATUS("activationStatus"), BULK_JOB_STATUS("bulkJobStatus"), CREATED_DATE("createdDate"), IS_SYSTEM_GENERATED("isSystemGenerated"), MOMENT_THRESHOLD("momentThreshold"),
        STATUS("status"), UPDATED_DATE("updatedDate"), MOMENT_FEEDBACK_SCORE("momentFeedbackScore"), FEEDBACK_STATUS("feedbackStatus"), BULK_JOB_END_STAMP("bulkJobEndStamp"),
        BULK_JOB_COMPLETION_DATE("bulkJobCompletionDate"), INSTRUCTIONAL_FEEDBACK_SCORE("instructionalFeedbackScore"), UNTAG_STATUS("untagStatus"),
        IS_NEX("isNex"), ADDITIONAL_ATTRIBUTES("additionalAttributes"), CALLMETA_DISPOSITION_DATA_DISPOSE_NAME("callMeta_dispositionData_disposeName"),
        CALLMETA_DISPOSITION_DATA_FIRST_DISPOSE_NAME("callMeta_dispositionData_firstDisposeName"), CALLMETA_OTHERS_CALL_COUNT("callMeta_others_callCount"), SUGGEST_AGAIN("suggestAgain"),
        NSUGGESTIONS("nsuggestions"), ID("id"), CHUNK_ID("chunkId"), START_STAMP("startStamp"), CHANNEL("channel"), LANGUAGE("language"), SPEAKER("speaker"), HASH_ID("hashId"),
        SCORE("score"), ROUND("round"), RANGE("range"), FORCED_ADD("forceAdd");

        private final String key;

        MOMENT_KEYS(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public enum MOMENT_VALUES {
        TRUE("true"), FALSE("false"), POSITIVE("positive"), NEGATIVE("negative"), NEUTRAL("neutral"), NULL("null"),
        AGENT("agent"), CUSTOMER("customer"), SEMANTIC("semantic"), KEYWORDS("keywords"), INSTRUCTIONAL("instructional"), FUZZY_MATCH("fuzzy_match"), FUZZY("fuzzy"),
        CALL("call"), INBOUND("inbound"), OUTBOUND("outbound"), MANUAL("manual"), AUTO("auto"), FEEDBACK_PENDING("FEEDBACK_PENDING");


        private final String key;

        MOMENT_VALUES(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public enum CONFIRMATION {
        OK, Ok, CANCEL, Cancel, YES, Yes, NO, No, EXPLORE, SUBSCRIBE, PREVIOUS, NEXT, Next, SUBMIT, Submit, Continue, CONTINUE, SEARCH,
        Proceed, Verify, Collect, Confirm, Done, Add, Update, Accept, ALLOW, Allow
    }


    public enum PAYMENT_OPTION {
        Total, Pay, Advance, Free, Discount, token, FINAL, full, Partial,
        TOKEN_PAYMENT("TOKEN"), FINAL_PAYMENT("final"), PAY_RENT("Pay Rent with Credit Card"),
        CASH("Cash"), DEBIT_CARD("Debit Card"), CREDIT_CARD("Credit Card"), InternetBanking("Internet Banking"), CORPORATE_CARD("Corporate Card"),
        BHIM_UPI("BHIM UPI"), NET_BANKING("Net Banking"), PAYTM("Paytm"), CHEQUE("Cheque"), NEFT("Neft"), PayZapp, Simpl, Mobikwik;

        String value;

        PAYMENT_OPTION() {
            value = this.name();
        }

        PAYMENT_OPTION(String s) {
            value = s;
        }

        public String toString() {
            return value;
        }
    }

    public enum APARTMENT_TYPE {
        APARTMENT("Apartment"),
        INDEPENDENT_HOUSE_VILLA("Independent House/Villa"),
        GATED_COMMUNITY_VILLA("Gated Community Villa"),
        STANDALONE_BUILDING("Standalone Building");
        String value;

        APARTMENT_TYPE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum PROPERTY_TYPE {
        OFFICE_SPACE("Office Space"),
        CO_WORKING("Co-Working"),
        RESTAURANT_CAFE("Restaurant/Cafe"),
        SHOP("Shop"),
        SHOWROOM("Showroom"),
        INDUSTRIAL_BUILDING("Industrial Building"),
        INDUSTRIAL_SHED("Industrial Shed"),
        GODOWN_WAREHOUSE("Gowdown/Warehouse"),
        OTHER_BUSINESS("Other business");

        String value;

        PROPERTY_TYPE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum AVAILABILITY {
        IMMEDIATE("Immediate"),
        WITHIN_15_DAYS("Within 15 Days"),
        WITHIN_30_DAYS("Within 30 Days"),
        AFTER_30_DAYS("After 30 Days");

        String value;

        AVAILABILITY(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ROOM_TYPE {
        SINGLE_ROOM("Single Room"),
        DOUBLE_SHARING("Double Sharing"),
        TRIPLE_SHARING("Triple Sharing"),
        FOUR_SHARING("Four Sharing");

        String value;

        ROOM_TYPE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ROOM_TYPES {
        SINGLE_ROOM("Single Room"),
        SHARED_ROOM("Shared Room"),
        SINGLE_ROOMS("Single room"),
        SHARED_ROOMS("Shared room");

        String value;

        ROOM_TYPES(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum AGE {
        LESS_THAN_1_YEAR("Less than 1 Year"),
        _1_3_YEARS("1-3 years"),
        _3_5_YEARS("3-5 years"),
        _5_10_YEARS("5-10 years"),
        MORE_THAN_10_YEARS(">10 years"),
        LESS_THAN_A_YEAR("Less than a year"),
        _1_5_YEAR("1 to 5 year"),
        _5_10_YEAR("5 to 10 year"),
        MORE_THAN_10_YEAR("More than 10 year");
        String value;

        AGE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum MENU_BTN {
        POST_YOUR_PROPERTY("Post Your Property"),
        RENTAL_AGREEMENT("Rental Agreement"),
        PAINTING_AND_CLEANING("Painting & Cleaning"),
        REFER_AND_EARN("Refer & Earn"),
        RENT_RECITES("Rent Receipts"),
        TENANT_PLANS("Tenant Plans"),
        OWNER_PLANS("Owner Plans"),
        BUYER_PLANS("Buyer Plans"),
        SELLER_PLANS("Seller Plans");

        String value;

        MENU_BTN(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum PROFESSION {
        DOESNT_MATTER("Doesn't Matter"),
        FAMILY("Family"),
        BACHELORS("Bachelor"),
        COMPANY("Company"),
        ANY("Any"),
        STUDENT("Student"),
        Working_PROFESSIONAL("Working Professional"),
        ANYONE("Anyone");
        String value;

        PROFESSION(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum CONFIRM {
        YES("Yes"), NO("No"), TRUE("True"), FALSE("False"),
        True("true"), False("false");

        String value;

        CONFIRM(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum WHO_WILL_SHOW {
        NEED_HELP("Need help"),
        NEIGHBOURS("Neighbours"),
        FRIENDS("Friends/Relatives"),
        OTHERS("Others"),
        SECURITY("Security"),
        TENANTS("Tenants");

        String value;

        WHO_WILL_SHOW(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum AVAILABLE_DAYS {
        EVERYDAY("Everyday"),
        WEEKDAYS("Weekdays"),
        WEEKEND("Weekends");

        String value;

        AVAILABLE_DAYS(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum LOCALITY_TYPE {
        MARKET_COMPLEX("Market Complex"),
        MALL("Shopping Mall"),
        RESIDENTIAL_AREA("Residential Area"),
        STANDALONE_BUILDING("Standalone Building"),
        INDUSTRIAL_AREA("Industrial Area"),
        TECH_PARK("Tech Park"),
        OFFICE_AREA("Office Area");
        String value;

        LOCALITY_TYPE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum LIFT {
        NONE("None"),
        PERSONAL("Personal"),
        COMMON("Common");
        String value;

        LIFT(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum PROPERTY_AGE {
        UNDER_CONSTRUCTION("Under Construction"),
        LESS_THAN_1_YEAR("Less than a Year"),
        _1_5_YEARS("1 to 5 year"),
        _5_10_YEARS("5 to 10 year"),
        MORE_THAN_10_YEARS("More than 10 year");
        String value;

        PROPERTY_AGE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PLANS {
        FreedomPlan, RelaxPlan, MoneyBackPlan, SuperRelaxPlan, SuperMoneyBackPlan, PowerPlan, ComfortPlan, CommercialFreedomPlan, CommercialRelaxPlan,
        CommercialMoneyBackPlan, CommercialPowerPlan, CommercialComfortPlan,
    }

    public enum STATUS {
        Open("Open"), InProgress("In Progress"), Success("Success"), JobCompleted("Job Completed"), Successful("Successful"), VehicleStarted("Vehicle Started"), NotAnswered("Not Answered"), LastSpoken("Last Spoken"), Started, UpdateStatus("Update Status"), InspectionPaymentReceived("Inspection Payment Received"),
        StartJob("Start Job"), StartedForJob("Started For Job"), Pickupdone("Pickup done"), Reached, SendInvoiceJob("Send Invoice And End Job"), PaymentLinkSent("Payment Link Sent"), InspectionRequired("Inspection Required"), InspectionPaymentLinkSent("Inspection Payment Link Sent"), QuotationSent("Quotation Sent"), JobCancelled("Job Cancelled"), Pending, LeadCreated("Lead Created"),
        Rejected, ReasonNotListed("Reason Not Listed"), UpdateInventory("Update Inventory"), ReachedDestination("Reached Destination"), PaymentRequested("Payment Requested"), PaymentCompleted("Payment Completed"), PickupDone("Pickup Done"), PaymentSuccessful("Payment Successful"), OrderConfirmed("Order Confirmed"),
        JobSentToPartner("Job Sent To Partner"), JobScheduled("Job Scheduled"), PartnerStartedForService("Partner Started For Service"), JobStarted("Job Started"), PaymentSuccessfull("Payment Successfull"), InspectionScheduled("Inspection Scheduled"), InspectionStarted("Inspection Started"), AgreementCompleted("Agreement Completed"), SoftCopyShared("Registered Soft Copy Shared");

        String value;

        STATUS(String s) {
            value = s;
        }

        STATUS() {
            value = this.name();
        }

        public String toString() {
            return value;
        }
    }

    public enum GENDER {
        MALE, FEMALE, Male, Female, male, female
    }

    public enum HS_VALUES {

        HomePainting("Home Painting"), Cleaning("Cleaning"), HomeCleaning("Home Cleaning"), FullHouseDeepCleaning("Full House Deep Cleaning"), FullHouseCleaning("Full House Cleaning"), AddHSLeadBtn("Add Home Service Lead"), AddVia("Add via Name/Phone/Email"),
        HS_SalesDashboard("Home Service Sales Dashboard"), HS_SalesDashboardTitle("Home Services Dashboard (Sales)"), Carpentry, Plumbing, Electrician, PestControl("Pest Control"), pnm("Packers and Movers"),
        HomeSanitization("Home Sanitization"), ApplianceService("Appliance Service & Repair"), PersonTypeSelf("Self"), Leads, StartInspection("Start Inspection"), EmailExt("@endtest-mail.io"), Myself,
        PaintingAddons("Painting Addons"), DoorPainting("Door Painting"), CompleteJob("Complete Job"), SendToCustomer("Send to Customer"), SendQuoteInspection("Send Quote & Complete Inspection"),
        TotalQuotationAmount("Total Quotation Amount"), HS_ServiceDashboardTitle("Home Services Dashboard (Servicing)"), TotalBillAmt("Total Bill Amount"), ACService("AC Service & Repair"),
        BasicFumigation("Basic Fumigation"), OtherCleaning("Other Cleaning"), Waterproofing, CustomPackage("Custom Package"), AddOnPrice("AddOn Price"), FinalPrice("Final Price"), AddonPrice("Addon Price"),
        CleaningSolution, AMT_25("25"), AMT_10("10"), MaterialCost("Material Cost"), RentalPainting("Rental Painting"), BHKType("BHK Type"), chooseBHK("Choose BHK"), HomeInterior("Home Interior"), PrimaryServices("Primary Services"),
        Inspectiondate("Inspection date"), SampleStartDate("2020-12-01 13:30:00"), SampleEndDate("2020-12-15 13:30:00"), SofaCleaning("Sofa Cleaning"), CompleteInspection("Complete Inspection"), Any, Default, MasterBedroom("Master Bedroom"),
        D30("30"), SideWall3("Side Wall 3"), SideWall4("Side Wall 4"), CeilingArea("Ceiling Area"), TotalAmount("Total Amount"), AmountPayable("Amount Payable"), Address, UpdateSuccessMsg("Updated Successfully"),
        UserStates("Under Process,Broker,Genuine,Not a Broker"), FIRST_TIME_RENTING("First Time Renting"),
        CURRENTLY_RENTED("Currently Rented"), PREVIOUSLY_RENTED("Previously Rented"),
        PaintingUpdateMsg("Painting details updated successfully"), ScheduleInspectionMsg("Inspection Assigned"), BookingCompletedTxt("Paid Successfully"), SlotBookedMsg("Your slot has been booked."), AdvanceAmountPaid("Advance Amount Paid"),
        RemainingAmtPayable("Remaining Amount Payable"), BookingConfirmed("Booking Confirmed"), InspectionAmt("49"),
        PECInspectionAmt("199"),
        BlrAddress1("Koramangala, Bengaluru, Karnataka, India"), MobileWebPlatform("MobileWeb"), Web("Web"), BathroomCleaningSubscription("Bathroom Cleaning Subscription");
        String value;

        HS_VALUES(String s) {
            value = s;
        }

        HS_VALUES() {
            value = this.name();
        }

        public String toString() {
            return value;
        }
    }


    public enum CITY_SELECTION {
        MUMBAI("Mumbai"),
        BANGALORE("Bangalore"),
        PUNE("Pune"),
        CHENNAI("Chennai"),
        GURGAON("Gurgaon"),
        HYDERABAD("Hyderabad"),
        DELHI("Delhi"),
        NOIDA("Noida"),
        GREATER_NOIDA("Greater Noida"),
        GHAZIABAD("Ghaziabad"),
        FARIDABAD("Faridabad"),
        THANE("Thane, Maharashtra, India");
        String value;

        CITY_SELECTION(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }


    public enum RA_VALUES {
        ESTAMPEDAGREEMNT("E-Stamped Agreement"), UPLOADYOURDRAFT("Upload your Draft"),

        rentalAgreement("Rental Agreement"), raPlusPoliceIntimation("Rental Agreement + Police Intimation"),
        LegalService("Legal Services"), notary("5f3a9def6ed0974167ab1667"), eSign_Agreement("62c6dddee771577b5113730e"),
        Stamp200("200 Stamp"), Stamp100(" 100 Stamp"), Stamp500("500 Stamp"), Locality("Locality"), STAMP100("₹ 100 Stamp"), STAMP500("₹ 500 Stamp"),
        SalesDashboardMenu("Legal Document Rental Sales Dashboard"), SalesDashboardTitle("Legal Services Dashboard (Sales)"), AddLead("Add  Lead"), FinalizeDraft("Finalize Draft"), ScheduleBiometric("Schedule Biometric"),
        RentalAgreement("Rental Agreement"), PoliceIntimation("Police Intimation"), CheckPrices("Check Prices"), CreateRentalAgreement("Create Rental Agreement"), Myself("No, I will do it Myself"), BiometricJobCompleted("Biometric Job Completed"), PropertyDetail("Property Detail"),
        ContinueasTenant("Continue as Tenant"), SkipContinue("Skip & Continue"), Home, Fixed, SendPaymentLink("Send Payment Link"), SendInspectinPaymentLink("Save & Send Inspection Payment Link"), ViewEditDoc("View / Edit Document"), MaintenancePaidBy("Maintenance Paid By"), FollowUpReason("NC/Busy/Callback"),
        ExecutingThrough("Executing Through"), LandlordDetail("Landlord Detail"), TenantDetail("Tenant Detail"), ContractDetail("Contract Detail"), MinimumLockinPeriod("Minimum Lockin Period (In months)"), EditOrder("Edit Order"), agreement_id("Agreement Id"),
        BiometricAppointments("Biometric Appointments"), AddNewAppointment("Add New Appointment"), UploadDocuments("Upload Documents"), ShareCopy("Share Registered Soft Copy"), BiometricVerification("Biometric Verification"), user_id,

        Owner, Tenant, flat("Apartment/Flat"), IndependentHouse("Independent House"), city, Location,

        Months10("10 Months"), Months11("11 Months"), AMT1K("1000"), AMT8K("8,000"), AMT9K("9,000"), AMT9k("9000"), AMT10K("10,000"), AMT11K("11,000"), AMT12K("12000"),

        TypeofProperty("Type of Property"), BuildingName("Building Name"), FlatHouseNumber("Flat Number / House Number"), FlatNumber("Flat Number"), FloorNumber("Floor Number"), HouseNumber("House Number"), PermanentAddressFull("Permanent Address Full"), TypeofUnit("Type of Unit"),
        PropertyAddressFull("Property Address (Full)"), Pincode2("Pincode"), FlatHouseBuiltUpArea("Flat/House Built-up Area"), PropertyNumberType("Property Number Type"), RoadStreet("Road / Street"), PropertyNumber("Property Number"), BuiltupAreaUnit("Built-up Area Unit"),
        IndependentHouseNumber("Independent House Number"),
        RentAmt("Rent Amount"), MonthlyRentAmt("Monthly Rent Amount"), RefundableDeposit("Refundable Deposit"), RefundableDepositamt("Refundable Deposit Amount"), NonRefundableDeposit("Non Refundable Deposit"), BranchName("Branch Name"), PINCode("PIN Code"), District("District"), VillageCity("Village/City"), Taluka("Taluka"),
        ChequeNumber("Cheque Number"), ChequeAmount("Cheque Amount"), RentDay("Enter Rent Day"), NoticePeriod("Notice Period (In months)"), AgreementDuration("Agreement Duration"), AGREEMENTDURATION("Agreement Duration (In months)"), DateofCheque("Date of Cheque"), CashAmount("Cash Amount"), TransferAmt("Transfer Amount"), DateofTransferAmt("Date of Transfer Amount"),
        DepositPaymentMode("Deposit Payment Mode"), BankName("Bank Name"), StampPaidBy("Stamp Duty & Registration Fee Paid By"), AgreementStartDate("Agreement Start Date"), ChequeNEFT("Cheque/NEFT/RTGS"), TxnRefNumber("Transaction Reference Number"), DemandDraft("Demand Draft"),
        BedroomNumber("Number Of Bedrooms"), BathroomNumber("Number of Bathrooms"), FansNumber("Number of Fans"), TubesNumber("Number of Tubes"), BedsNumber("Number of Beds"),
        FullName("Full Name"), Age, AadharNumber("Aadhar Number (12 Digits)"), PAN("PAN Number (ABCDE1234F)"), EmailAddress("Email Address"), Occupation, Business, Service, Licensor, Licensee, Salutation,
        MiddleName("Middle Name"), LastName("Last Name"), FirstName("First Name"), Surname, Preview, Phone("Phone"),
        BlrAddress1("Koramangala, Bengaluru, Karnataka, India"), BlrAddress2("Sanjaynagar, Bengaluru, Karnataka, India"), BlrAddress3("Malleshwaram, Bengaluru, Karnataka, India"), BankAxis("AXIS BANK LTD"), IDBI,
        MbiAddress1("Juhu, Mumbai, Maharashtra, India"), MbiPin1("400049"), MbiAddress2("Worli, Mumbai, Maharashtra, India"), MbiPin2("40,0018"), MhState("Maharashtra"), PuneAddress1("Pune, Hadapsar, Maharashtra"),

        ccBiometricAvailable("Can all landlords and tenants be available at one location in Mumbai/Pune for biometric verification?"),
        ccBiometricPartyIndia("Do all parties stay within India?"),
        PartyType("Party Type"), Gender("Gender");
        String value;

        RA_VALUES(String s) {
            value = s;
        }

        RA_VALUES() {
            value = this.name();
        }

        public String toString() {
            return value;
        }
    }


    public enum RA_ADDONS {

        NOTARISEDAGREEMENT("Notarised Agreement"),
        ESIGNAGREMENT("E-Sign Agreement"),
        ONEDAYDELIVERY("Get One Day Delivery"),


        MH(
                "Extra visit in same city;" +
                        "Extra visit within Maharashtra;" +
                        "Extra visit within India;" +
                        "Extra visit outside India;" +
                        "I have my own device;" +
                        "I require a device sent to me for use;" +
                        "Personalised Assistance"
        ),

        NONMH(
                "Notarised Agreement;" +
                        "Personalised Assistance"
        );

        String value;

        RA_ADDONS(String s) {
            value = s;
        }

        public String toString() {
            return value;
        }
    }

    public enum CHECKOUT_VALUES {
        AmexCardMsg("Amex cards are not supported currently"),
        PayNow("Pay Now"), SkipPay("Skip & Pay"),
        IntermilesCard("3671233333333333"), PayZappCorpCard("4329091207169785"), PzCorpCardPwd("1111");

        String value;

        CHECKOUT_VALUES(String s) {
            value = s;
        }

        public String toString() {
            return value;
        }
    }

    public enum OTHER_AMENITIES {
        AIR_CONDITIONER("Air-Conditioner"),
        CLUB("club"),
        PLAY_GROUND("Playground"),
        GAS("Gas"),
        RAIN_WATER_HARVESTING("Rain Water Harvesting"),
        SEWAGE("Sewage"),
        POWER_BACKUP("Power Backup"),
        LIFT("Lift"),
        FIRE_ALARM("Fire-Alarm"),
        HOUSE_KEEPER("House-Keeper"),
        PARK("Park"),
        SHOPPING_CENTER("Shopping Center"),
        SWIMMING_POOL("Swimming Pool"),
        INTERCOM("Intercom"),
        VISITOR_PARKING("Visitor Parking"),
        INTERNET("Internet");

        String value;

        OTHER_AMENITIES(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }

    }

    public enum BUILDING_TYPE {
        INDEPENDENT_HOUSE("Independent House"),
        BUSINESS_PARK("Business Park"),
        MALL("Mall"),
        STANDALONE_BUILD("Standalone building"),
        INDEPENDENT_SHOP("Independent shop");
        String value;

        BUILDING_TYPE(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Floor {
        LOWER_BASEMENT("Lower Basement"),
        UPPER_BASEMENT("Upper Basement"),
        FULL_BUILDING("Full Building"),
        GROUND("Ground"),
        GROUND_ONLY("Ground Only");
        String value;

        Floor(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum WASHROOM {
        NO_WASHROOM("No Washroom"),
        SHARED("Shared"),
        PRIVATE("Private");
        String value;

        WASHROOM(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum FOOD {
        EGG_ONLY("Egg Only"),
        VEG("Veg"),
        NON_VEG("Non-Veg");
        String value;

        FOOD(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum GATED_COMMUNITY {
        DOES_NOT_MATTER("Does Not Matter"),
        SECURITY_ONLY("Security Only"),
        SECURITY_WITH_AMENITIES("Security with amenities");
        String value;

        GATED_COMMUNITY(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum POWER_BACKUP_TRM {
        DOES_NOT_MATTER("Does Not Matter"),
        MANDATORY("Mandatory");
        String value;

        POWER_BACKUP_TRM(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum RELOCATION {
        INTER_CITY("Inter-City"),
        DIFF_CITY_MOV("Different City Movement"),
        SAME_CITY_MOV("Same City Movement"),
        INTRA_CITY("Intra-City");
        String value;

        RELOCATION(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum MyServices {
        MY_TENANT_RM("My Tenant Relationship Manager"),
        MY_OWNER_RM("My Owner Relationship Manager");
        String value;

        MyServices(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CustomField {
        INTERESTED("Interested"),
        NOT_INTERESTED(" Not Interested"),
        YET_TO_CONFIRM(" Yet to Confirm");
        String value;

        CustomField(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

  /*  public enum FollowUpReasons {
        FEEDBACK_AFTER_MOVEMENT("Feedback after Movement"),
        PAYMENT_FOLLOWUP("Payment FollowUp"),
        NC_BUSY_CALLBACK("NC/Busy/Call Back"),
        cx_vENDOR("Negotiations with Cx & Vendor"),
        High_Priority_FollowUp("High_Priority_FollowUp");

        String value;

        FollowUpReasons(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }

    }

    public String[] getReport()
    {
        String[] reportPaths = new String[]{"", ""};
        String snapshotPath = System.getProperty("user.dir") + "/ExtentReports/ReportSnapshot.png";
        File reportScreenshotFile = new File(snapshotPath);

        try
        {
            Thread.sleep(3000);
            String htmlPath = ExtentManager.filepath;
            if (htmlPath.isEmpty())
            {
                System.out.println("Report html path not specified");
                return reportPaths;
            }

            // === Custom report === //
            htmlPath = updateReportWithGraph(htmlPath);
            // ===================== //

            System.out.println("Taking report snapshot");
            System.out.println(htmlPath);
            WebDriver driver = webDriverReportDriver();
            driver.get("file://" + htmlPath);

            if (driver != null)
            {
                Dimension dimension = new Dimension(1200, 720);
                driver.manage().window().setSize(dimension);
                By dashboardContainer = By.xpath(".//*[@class='container-fluid p-4 view dashboard-view']");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("document.head.appendChild(document.createElement(\"style\")).innerHTML = \"div.header.navbar {position: relative; }\"");
                WebElement dc = driver.findElement(dashboardContainer);
                System.out.println("Obtaining Dashboard Screen");
                Thread.sleep(3000);
                Screenshot screenshot;
                if (System.getProperty("os.name").contains("Server"))
                    screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
                else if (System.getProperty("os.name").contains("Linux"))
                    screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
                else
                    screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(ShootingStrategies.scaling(2f), 1000)).takeScreenshot(driver);

                try
                {
                    Point point = dc.getLocation();
                    int eleWidth = dc.getSize().getWidth();
                    int eleHeight = dc.getSize().getHeight();
                    BufferedImage eleScreenshot = screenshot.getImage().getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
                    ImageIO.write(eleScreenshot, "PNG", reportScreenshotFile);
                } catch (IOException e)
                {
                    System.err.println("Dashboard Exception");
                    e.printStackTrace();
                }
            }
            driver.close();
            reportPaths[0] = htmlPath;
            reportPaths[1] = snapshotPath;
            return reportPaths;

        } catch (Exception e)
        {
            System.err.println("Error opening result file");
            e.printStackTrace();
            return reportPaths;
        }
    }

    public WebDriver webDriverReportDriver()
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--start-maximized");
        options.addArguments("--disable-web-security");
        options.addArguments("--no-proxy-server");
        //options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        if (getProperty(ReportingUtils.CommonConstants.PROJECT.common.toString(), CommonConstants.HTML_REPORT_HEADLESS).equalsIgnoreCase("true"))
            options.addArguments("--headless");

        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        return webDriver;
    }

    String endTime;
    public String updateReportWithGraph(String htmlPath)
    {
        String customReportFilepath;

        System.out.println("Generating Custom report");

        try
        {
            String workingDir = System.getProperty("user.dir");
            String startTime = ExtentTestManager.time.replace("T", " Time: ");
            if (endTime == null)
                endTime = LocalDateTime.now().toString();
            String reportHtml = FileUtil.readTemplateAsString(workingDir + "/resource/reportTemplates/reportHtml.tmpl");
            customReport = new CustomReport(htmlPath, "UTF-8");
            customReport.updateNodeInHtml(reportHtml, "div.card-body.pt-0 > div", "div.card-body.pt-0");
            customReport.updateHtml("div.card-body>p+h3", startTime, 0);
            customReport.updateHtml("div.card-body>p+h3", endTime, 1);
            String reportData = FileUtil.readTemplateAsString(workingDir + "/resource/reportTemplates/reportData.tmpl");
            String jsonData = FileUtil.readTemplateAsString(workingDir + "/resource/reportTemplates/reportLabel.tmpl");
            String reportScript = FileUtil.readTemplateAsString(workingDir + "/resource/reportTemplates/reportScript.tmpl");

            StringBuffer passBuffer = new StringBuffer();
            StringBuffer failBuffer = new StringBuffer();
            StringBuffer skipBuffer = new StringBuffer();
            System.out.println(CommonConstants.EXTENT_METHOD_COUNT);
            for (String e : CommonConstants.EXTENT_METHOD_COUNT.keySet())
            {
                int passCount = CommonConstants.EXTENT_METHOD_COUNT.get(e).get("PASS");
                int failCount = CommonConstants.EXTENT_METHOD_COUNT.get(e).get("FAIL");
                int skipCount = CommonConstants.EXTENT_METHOD_COUNT.get(e).get("SKIP");
                passBuffer.append(jsonData.replace("_className_", e).replace("_value_", String.valueOf(passCount)));
                failBuffer.append(jsonData.replace("_className_", e).replace("_value_", String.valueOf(failCount)));
                skipBuffer.append(jsonData.replace("_className_", e).replace("_value_", String.valueOf(skipCount)));
            }
            customReport.updateHtml("div.card-header > p", "Automation Status");
            reportData = reportData
                    .replace("_passLabel_", passBuffer.substring(0, (passBuffer.length() - 1)))
                    .replace("_failLabel_", failBuffer.substring(0, (passBuffer.length() - 1)))
                    .replace("_skipLabel_", skipBuffer.substring(0, (skipBuffer.length() - 1)));

            reportScript = reportScript.replace("_dataset_", reportData);

            customReport.addScriptInHtml(reportScript, "body.spa.-report.standard");
            customReportFilepath = customReport.generateCustomReport();
            System.out.println("Custom report generated at [" + customReportFilepath + "]");
            return customReportFilepath;
        } catch (IOException e)
        {
            e.printStackTrace();
            return htmlPath;
        }
    }

    public static final String CHROME_USR_DIR_WIN = "chrome.usr.dir.win";
    public static final String CHROME_USR_DIR_MAC = "chrome.usr.dir.mac";

    public static String getProfileDirPath(String projectCode)
    {
        String dirPath = "_";
        try
        {
            dirPath = System.getProperty("os.name").contains("Server") ?
                    getProperty(projectCode, CommonConstants.CHROME_USR_DIR_WIN) :
                    getProperty(projectCode, CommonConstants.CHROME_USR_DIR_MAC);

            dirPath = System.getProperty("user.name").contains("admin") ? dirPath : dirPath.replace("admin", System.getProperty("user.name"));
        } catch (Exception e)
        {
            System.err.println("Profile dir path not found " + e.getMessage());
        } catch (Error e)
        {
            System.err.println("Profile dir path error " + e.getMessage());
        }

        return dirPath;
    }
*/


}
