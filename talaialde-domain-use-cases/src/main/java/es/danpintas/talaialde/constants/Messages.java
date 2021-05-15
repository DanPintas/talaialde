package es.danpintas.talaialde.constants;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class Messages {
    
    private static final String BUNDLE_NAME = "text/i18n";
    
    public static final String APP_TITLE = "APP_TITLE";
    public static final String APP_NAME = "APP_NAME";

    public static final String ERROR_CONVERSION_DATE = "ERROR_CONVERSION_DATE";
    public static final String ERROR_CONVERSION_DOUBLE = "ERROR_CONVERSION_DOUBLE";
    public static final String ERROR_CONVERSION_INTEGER = "ERROR_CONVERSION_INTEGER";
    public static final String ERROR_CONVERSION_LIST = "ERROR_CONVERSION_LIST";
    
    public static final String ERROR_EDITED_ELEMENTS = "ERROR_EDITED_ELEMENTS";
    
    public static final String ERROR_FORM = "ERROR_FORM";
    public static final String ERROR_PDF_EXPORT = "ERROR_PDF_EXPORT";
    
    public static final String ERROR_GENERIC_ERROR = "ERROR_GENERIC_ERROR";
    public static final String ERROR_GENERIC_WARN = "ERROR_GENERIC_WARN";
    
    public static final String ERROR_REQUIRED_DATE = "ERROR_REQUIRED_DATE";
    public static final String ERROR_REQUIRED_INTEGER = "ERROR_REQUIRED_INTEGER";
    public static final String ERROR_REQUIRED_TEXT = "ERROR_REQUIRED_TEXT";
    
    public static final String LOGIN_USER = "LOGIN_USER";
    public static final String LOGIN_PASS = "LOGIN_PASS";
    public static final String LOGIN_CANCEL = "LOGIN_CANCEL";
    public static final String LOGIN_DO_LOGIN = "LOGIN_DO_LOGIN";
    
    public static final String LOGIN_ERROR_TITLE = "LOGIN_ERROR_TITLE";
    public static final String LOGIN_ERROR_ALREADY_LOGGED = "LOGIN_ERROR_ALREADY_LOGGED";
    public static final String LOGIN_ERROR_NO_USER = "LOGIN_ERROR_NO_USER";
    public static final String LOGIN_ERROR_WRONG_PASSWORD = "LOGIN_ERROR_WRONG_PASSWORD";
    public static final String LOGIN_ERROR_EXCEPTION = "LOGIN_ERROR_EXCEPTION";

    public static final String MENU_ADMIN = "MENU_ADMIN";
    public static final String MENU_ADMIN_USERS = "MENU_ADMIN_USERS";
    public static final String MENU_ADMIN_ROLES = "MENU_ADMIN_ROLES";

    public static final String MENU_BILLING = "MENU_BILLING";
    public static final String MENU_BILLING_NEW_LINE = "MENU_BILLING_NEW_LINE";
    public static final String MENU_BILLING_SEE_BILL = "MENU_BILLING_SEE_BILL";
    
    public static final String MENU_LOGOUT = "MENU_LOGOUT";
    
    public static final String MENU_MANAGEMENT = "MENU_MANAGEMENT";
    public static final String MENU_MANAGEMENT_CLIENTS = "MENU_MANAGEMENT_CLIENTS";
    public static final String MENU_MANAGEMENT_ROUTES = "MENU_MANAGEMENT_ROUTES";
    public static final String MENU_MANAGEMENT_TRACTORS = "MENU_MANAGEMENT_TRACTORS";
    
    public static final String PROMPT_YES = "PROMPT_YES";
    public static final String PROMPT_NO = "PROMPT_NO";
    
    public static final String PROMPT_ACCEPT = "PROMPT_ACCEPT";
    public static final String PROMPT_CANCEL = "PROMPT_CANCEL";
    
    public static final String PROMPT_SEARCH = "PROMPT_SEARCH";
    
    public static final String PROMPT_NEW = "PROMPT_NEW";
    public static final String PROMPT_EDIT = "PROMPT_EDIT";
    public static final String PROMPT_DELETE = "PROMPT_DELETE";

    public static final String PROMPT_DELETE_DESC = "PROMPT_DELETE_DESC";
    
    public static final String PROMPT_SAVE = "PROMPT_SAVE";
    
    public static final String PROMPT_SAVED_DATA = "PROMPT_SAVED_DATA";
    public static final String PROMPT_DELETED_DATA = "PROMPT_DELETED_DATA";
    
    public static final String PROMPT_REFRESH = "PROMPT_REFRESH";
    public static final String PROMPT_CLEAR = "PROMPT_CLEAR";
    public static final String PROMPT_MARKASREAD = "PROMPT_MARKASREAD";
    public static final String PROMPT_EXCEL = "PROMPT_EXCEL";
    public static final String PROMPT_PDF = "PROMPT_PDF";
    public static final String PROMPT_MESSAGE = "PROMPT_MESSAGE";
    public static final String PROMPT_REQUIRED = "PROMPT_REQUIRED";

    public static final String PROP_ACCOUNT_NUMBER = "PROP_ACCOUNT_NUMBER";
    public static final String PROP_ACTIVE = "PROP_ACTIVE";
    public static final String PROP_ADDRESS = "PROP_ADDRESS";
    public static final String PROP_AMOUNT = "PROP_AMOUNT";
    public static final String PROP_BILL_NUMBER = "PROP_BILL_NUMBER";
    public static final String PROP_CLIENT = "PROP_CLIENT";
    public static final String PROP_CONTACT = "PROP_CONTACT";
    public static final String PROP_DATE = "PROP_DATE";
    public static final String PROP_DESTINY = "PROP_DESTINY";
    public static final String PROP_EMAIL = "PROP_EMAIL";
    public static final String PROP_FEE = "PROP_FEE";
    public static final String PROP_HOUR_FEE = "PROP_HOUR_FEE";
    public static final String PROP_KM = "PROP_KM";
    public static final String PROP_LINE = "PROP_LINE";
    public static final String PROP_LINE_TYPE = "PROP_LINE_TYPE";
    public static final String PROP_LOCALITY = "PROP_LOCALITY";
    public static final String PROP_NAME = "PROP_NAME";
    public static final String PROP_OBSERVATIONS = "PROP_OBSERVATIONS";
    public static final String PROP_ORIGIN = "PROP_ORIGIN";
    public static final String PROP_PASSWORD = "PROP_PASSWORD";
    public static final String PROP_PHONE_NUMBER = "PROP_PHONE_NUMBER";
    public static final String PROP_PLATE = "PROP_PLATE";
    public static final String PROP_PRODUCT = "PROP_PRODUCT";
    public static final String PROP_REGION = "PROP_REGION";
    public static final String PROP_ROLE = "PROP_ROLE";
    public static final String PROP_ROUTE = "PROP_ROUTE";
    public static final String PROP_SURNAME = "PROP_SURNAME";
    public static final String PROP_SPECIFICS = "PROP_SPECIFICS";
    public static final String PROP_TIN = "PROP_TIN";
    public static final String PROP_TON_FEE = "PROP_TON_FEE";
    public static final String PROP_TRIP_FEE = "PROP_TRIP_FEE";
    public static final String PROP_USERNAME = "PROP_USERNAME";
    public static final String PROP_USUAL_DRIVER = "PROP_USUAL_DRIVER";
    public static final String PROP_TOTAL = "PROP_TOTAL";
    public static final String PROP_TRACTOR = "PROP_TRACTOR";
    public static final String PROP_VALUE = "PROP_VALUE";
    public static final String PROP_VAT = "PROP_VAT";
    public static final String PROP_VI_EXPIRY = "PROP_VI_EXPIRY";
    
    private static ReloadableResourceBundleMessageSource messages;
    
    private Messages() {
        // constants
    }

    public static String getMessage(String id, Object[] args, Locale locale) {
        String message;
        if(messages == null) {
            messages = new ReloadableResourceBundleMessageSource();
            messages.setBasename(BUNDLE_NAME);
            messages.setDefaultEncoding("UTF-8");
        }
        try {
            message = messages.getMessage(id, args, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            message = "!- " + id;
        }
        return message;
    }

}
