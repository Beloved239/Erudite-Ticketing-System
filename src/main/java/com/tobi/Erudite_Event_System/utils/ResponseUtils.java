package com.tobi.Erudite_Event_System.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.tobi.Erudite_Event_System.dto.EventData;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestValueException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Component
public class ResponseUtils {

    private ResponseUtils(){

    }
    public static final String EMAIL_EXISTS_CODE = "001";
    public static final String EMAIL_EXISTS_MESSAGE = "User with this email address already exists";
    public static final String LOG_IN_CODE = "002";
    public static final String UNSUCCESSFUL_LOG_IN_CODE = "007";
    public static final String UNSUCCESSFUL_LOG_IN_MESSAGE = "USERNAME_OR_PASSWORD_INCORRECT";
    public static final String LOG_IN_MESSAGE = "Log in Successfully";
    public static final int LENGTH_OF_PASSWORD = 15;
    public static final String ACCOUNT_CREATION_SUCCESS_CODE = "003";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "User Account has been successfully created!";
    public static final String ACCOUNT_CREATION_ALERT_SUBJECT = "ACCOUNT CREATION ALERT!!!";
    public static final String ACCOUNT_UPDATED_CODE = "004";
    public static final String ACCOUNT_UPDATED_MESSAGE = "Account updated Successfully";
    public static final String USER_ROLE_SET_CODE = "005";
    public static final String USER_ROLE_SET_MESSAGE = "User role has been successfully updated";
    public static final String INVALID_TOKEN_CODE = "006";
    public static final String INVALID_TOKEN_MESSAGE = "Invalid Token.";
    public static final String ORGANIZER_DOES_NOT_EXIST_CODE = "007";
    public static final String ORGANIZER_DOES_NOT_EXIST_MESSAGE = "Organizer with the provided id does not exist ";
    public static final String ATTENDEE_DOES_NOT_EXIST_MESSAGE = "Attendee with the provided id does not exist ";
    public static final String USER_ROLE_EXIST_CODE = "008";
    public static final String USER_ROLE_EXIST_MESSAGE = "The Provided Role Exist";
    public static final String EMAIL_IS_NOT_VALID_CODE = "009";
    public static final String EMAIL_IS_NOT_VALID_MESSAGE = "Enter a valid email format";

    public static final String UN_SUCCESSFUL_CODE = "010";
    public static final String UN_SUCCESSFUL_MESSAGE = "Unsuccessful";
    public static final String SUCCESS_MESSAGE = "Successful";
    public static final String SUCCESS_CODE = "010";
    public static final String ROLE_DELETED_MESSAGE = "Role deleted Successfully";
    public static final String ROLE_DELETED_CODE = "011";
    public static final String ROLE_DOES_NOT_EXIST_CODE = "012";
    public static final String ROLE_DOES_NOT_EXIST_MESSAGE = "This Role does not exist";
    public static final String ATTENDEE_DOES_NOT_EXIST_CODE = "013";
    public static final String REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";
    public static final String EMAIL_SENT_SUCCESSFULLY_CODE = "001";
    public static final String EMAIL_SENT_SUCCESSFULLY_MESSAGE = "Mail sent Successfully";
    public static final String EMAIL_DOES_NOT_EXIST_MESSAGE = "User with this email address exists";
    public static final Integer STATUS_CODE_OK = 200;
    public static final Integer STATUS_CODE_CREATED = 201;
    public static final String PAYSTACK_INIT = "https://api.paystack.co/plan";
    public static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
    public static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
    public static final String GETATTENDEEURL = "http://localhost:8080/api/identity/attendee/single/attendee?email=";


//    public static String generateClientCode(int length) {
//        String clientCode = "";
//        int x;
//        char[] stringChars = new char[length];
//
//        for (int i = 0; i < length; i++) {
//            Random random = new Random();
//            x = random.nextInt(9);
//            stringChars[i] = Integer.toString(x).toCharArray()[0];
//        }
//
//        clientCode = new String(stringChars);
//        return clientCode.trim();
//    }

    private static final String ALPHABET = "EVENT";
    private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateUniqueAlphaNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        // Generate the first 4 characters as alphabets
        for (int i = 0; i < 4; i++) {
            int randomIndex = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(randomIndex));
        }
        // Generate the remaining characters as digits
        for (int i = 4; i < length; i++) {
            int randomIndex = RANDOM.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(randomIndex));
        }

        return sb.toString();
    }





    public static String generateTemporaryPassword(){
        StringBuilder tempPassword = new StringBuilder();
        int sum = 0;
        Random randomPassword = new Random();
        while (sum < LENGTH_OF_PASSWORD){
            tempPassword.append((char) randomPassword.nextInt(33, 127));
            sum++;
        }
        return tempPassword.toString();
    }



//    public static void generateQrCode(EventResponse request, HttpServletResponse response) throws MissingRequestValueException,
//            WriterException, IOException {
//        if( request==null) {
//            throw new MissingRequestValueException("QR String is required");
//        }else {
//            createQrCode(request, response.getOutputStream());
//            response.getOutputStream().flush();
//        }
//    }

    public static void createQrCode(EventData data) throws WriterException,
            IOException, MissingRequestValueException {
        if( data==null) {
            throw new MissingRequestValueException("QR String is required");
        }else {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = new QRCodeWriter().encode(String.valueOf(data), BarcodeFormat.QR_CODE, 300, 300);
            Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG",path );
//        outputStream.flush();
        }

    }




}
