package com.interswitchgroup.discoverpostinjectweb.service;

import com.interswitchgroup.discoverpostinjectweb.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



@Service
public class ProcessFileContents {

    final static Logger logger = LoggerFactory.getLogger(ProcessFileContents.class);
    private static final String CRLF = "\r\n"; // CR  = 13,    LF  = 10;
    private static final String SEPARATOR = "|";
    private static final String FILE_FORMAT = "T";
    private static final String PRIMARY_FIELD = "F";
    private static final String PRIVATE_FIELD = "P";
    private static final String TRAILER = "TRAILER";
    private static final String RESPONSE_CODE = "0220";
    private static final String HEADER_CODE = "00";
    private static final String HEADER_HEADER = "REQUEST HEADER";
    /*
     * DETAIL TRANSACTION CODE TYPE
     */
    private static final String DEATIL_TRANSACTION_CODE = "05";
    private static final String DEATIL_TRANSACTION_CODE_SDR = "06";
    private static final String DEATIL_TRANSACTION_CODE_SDR_DATA = "07";
    private static final String DEATIL_TRANSACTION_CODE_SDR_CURRENCY = "08";
    private static final String FIELD_2_16 = "0020016";
    private static final String FIELD_2_19 = "0020019";
    private static final String FIELD_3 = "0030006";
    private static final String FIELD_3_PAD = "0000";
    private static final String FIELD_4 = "0040012";
    private static final String FIELD_5 = "0050012";
    private static final String FIELD_7 = "0070010";
    private static final String FIELD_9 = "0090008";
    private static final String FIELD_11 = "0110006";
    private static final String FIELD_12 = "0120006";
    private static final String FIELD_13 = "0130004";
    private static final String FIELD_16 = "0160004";
    private static final String FIELD_18 = "0180004";
    private static final String FIELD_22 = "0220003";
    private static final String FIELD_25 = "0250002";
    private static final String FIELD_38 = "0380006";
    private static final String FIELD_39 = "0390002";
    private static final String FIELD_41 = "0410008";
    private static final String FIELD_42 = "0420015";
    private static final String FIELD_43 = "0430040";
    private static final String FIELD_49 = "0490003";
    private static final String FIELD_50 = "0500003";
    //private static final String FIELD_59 = "0590500";
    private static final String FIELD_123 = "1230015";
    private static final String DISPUTE_ADJUSTMENT = "F";
    private static final String DEPOSIT = "R";
    /*
     *
     * INITIALISE CONSTANT FIELDS
     */
    private static String FIELD_2 = "";
    private static String POS_DATA_CODE = "010101005004101";// SAMPLE CODE: 510101504344101
    StringBuilder convertedContent = new StringBuilder();
    private final long currencyConversionValue = 1000000L;
    private String headerContent;
    private String detailContent;
    private final String FIELD_P_VAL = "00200";//"00200320220:"
    private final String P_FILED_12="012";
    private String FIELD_P_VAL_TEMP = "";
    private String TRAILER_VAL = "0000000000";
    private String pan;

    public ProcessFileContents() {

    }

    public String processContent(String inDirectory,String outDirectory,  String terminalId) {

        logger.info("START PROCESSING FILE");
        String result ="";

        StringFunctions sf = new StringFunctions();
        String recordType;
        int recordCount = 0;
        String field7_Val;
        String fileNameOut;
        String fileNameInp = "";
        String fileNameBk;
        String currencyCode;
        Path fileNameOutPath;
        boolean headerSet = false;


        try {

            Date date = new Date();
            SimpleDateFormat dateFormatField7 = new SimpleDateFormat("MMdd");

            String expectedPattern = "yyyyMMdd";
            String expectedPatternSw = "yyMMdd";
            String expectedPatternSwTM = "HHmm";

            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
            SimpleDateFormat formatterSW = new SimpleDateFormat(expectedPatternSw);
            SimpleDateFormat formatterSWTM = new SimpleDateFormat(expectedPatternSwTM);

            SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String fullDate = ft.format(date);

            // GenerateConfigFile gc = new GenerateConfigFile();
            //String[] splitVals = gc.loadConfigValues(configFilename).split("_");

            //if (splitVals.length < 4) {
            //  throw new Exception("Invalid number of items in config.properties");
            //}

            String inpDir = inDirectory;
            String outDir = outDirectory;
            String backUpDir = "C:/BackupDiscoverFiles";

            Util.createDirectory(backUpDir);

            String absoluteFilePath = outDir;
            String absoluteFilePathInp = inpDir;
            currencyCode = "840";

            Path absoluteFilePathNew = Paths.get(absoluteFilePathInp);
            /**
             * GET FILES FROM DIRECTORY
             */
            DirectoryOperations fileList = new DirectoryOperations(absoluteFilePathNew.toAbsolutePath());
            String[] fileLists = fileList.getFilesInDirectory();

            //BACK UP tlv_request
            fileNameOut = absoluteFilePath + "/tlv_request" + ".txt";
            fileNameOutPath = Paths.get(fileNameOut);
            String fileTlvRequest =absoluteFilePath + "/tlvrequest_" + fullDate + ".txt";
            //

            Path oldFileTlvRequest = Paths.get(fileNameOut);
            Path newFileTlvRequest = Paths.get(fileTlvRequest);

            fileList.RenameFile(oldFileTlvRequest, newFileTlvRequest);

            if (fileLists.length > 0) {

                for (String file : fileLists) {// TODO TO BE to process more than one files
                    if (file != null) {

                        String originalCurrencyCode;
                        String originalAmount;
                        String convRate;

                        fileNameInp = absoluteFilePathInp + "/" + file;
                        fileNameBk = backUpDir + "/" + fullDate + file;

                        Path fileNameInpPath = Paths.get(fileNameInp);
                        FileReader srcFile = new FileReader(fileNameInpPath.toAbsolutePath());
                        srcFile.openFile();
                        String line;

                        /*
                        * Read file for content
                        * */

                        while ((line = srcFile.fetchNextLine()) != null) {

                            originalCurrencyCode = null;
                            originalAmount = null;
                            convRate = null;

                            if (!"05".equalsIgnoreCase(line.substring(0, 2))
                                    && !"06".equalsIgnoreCase(line.substring(0, 2))
                                    && !"00".equalsIgnoreCase(line.substring(0, 2))) {
                                continue;
                            }


                            DetailTransactionRecord dtRecord = new DetailTransactionRecord(line);
                            if("6011".equalsIgnoreCase(dtRecord.getMerchantCategoryCode())){
                                continue;
                            }

                            recordType = dtRecord.getRecordType();
                            System.out.println(recordType);
                            if (recordType.equals(HEADER_CODE) && !headerSet) { //just taking the time from the 1st file. should this be done?
                                headerSet = true;
                                headerContent = HEADER_HEADER
                                        + SEPARATOR
                                        + sf.repalceAllChar(line.substring(46, 60), ":", "")
                                        + SEPARATOR + FILE_FORMAT + CRLF;

                                convertedContent.append(headerContent);
                                continue;
                            }

                            /*
                             *
                             * DEATIL_TRANSACTION_CODE = 05 or 06
                             */
                            if (recordType.equals(DEATIL_TRANSACTION_CODE) || recordType.equals(DEATIL_TRANSACTION_CODE_SDR)) {

                                String merchantReferenceType = "" + dtRecord.getMerchantReferenceType();  //line.substring(75, 76);

                                if ('Y' == dtRecord.getCurrencyConversionIndicator())//  Currency Conversion Indicator MUST BE Y
                                {

                                    if (recordType.equals(DEATIL_TRANSACTION_CODE_SDR)) {
                                        //PICK CURRENCY CODE FORM RECORD TYPE 08
                                        //int x = 2;
                                        String line08 = null;
                                        while (true) { // Until record type 08 is encountered
                                            line08 = srcFile.fetchNextLine();
                                            if (null == line08) {
                                                throw new Exception("Unexpected end of file while looking for Currency Conversion Addenda Record");
                                            }

                                            if (DEATIL_TRANSACTION_CODE_SDR_CURRENCY.equals(line08.substring(0, 2))) {
                                                break;
                                            }

                                            //x--;
                                        }

                                        //if (x < 1) {
                                        //    throw new Exception("Unable to locate Currency Conversion Addenda Record");
                                        //}

                                        originalAmount = dtRecord.getTransactionAmount(); // this should go to field 5
                                        dtRecord.setTransactionAmount(line08.substring(43, 55));
                                        originalCurrencyCode = line08.substring(70, 73);
                                        convRate = formatAmount(line08.substring(55, 69));
                                        char[] cArray = convRate.toCharArray();
                                        int convRateLen = convRate.length();
                                        int decimalPos = 7;

                                        while (cArray[convRateLen - 1] == '0' && convRateLen > 0 && decimalPos > 0) {
                                            decimalPos--;
                                            convRateLen--;
                                        }

                                        convRate = convRate.substring(0, convRateLen);
                                        if (decimalPos < 1) {
                                            if (cArray[0] != '0') {
                                                throw new Exception("Conversion rate in file to large to represent in postilion post inject TLV format");
                                            }
                                        } else {
                                            convRate = StringUtils.stripStart(convRate, "0");
                                            if (convRate.length() <= 7) {

                                                convRate = String.format("%d%s", decimalPos, StringUtils.leftPad(convRate, 7, '0'));

                                            } else {
                                                //throw new Exception("Conversion rate in file to large to represent in postilion post inject TLV format");

                                                return "Conversion rate in the file:" + file + " is too large to represent in postilion post inject TLV format" + "Length:" + convRate.length();
                                            }
                                        }
                                    }
                                }

                                if (DEPOSIT.equals(merchantReferenceType)) {
                                    pan = dtRecord.getCardNumber();
                                    //HANDLE PAN WITH LENGTH 19
                                    FIELD_2 = pan.length() == 16 ? FIELD_2_16 : FIELD_2_19;

                                    // FIELD_2 and FIELD_3
                                    detailContent = RESPONSE_CODE + SEPARATOR
                                            + PRIMARY_FIELD + FIELD_2
                                            + pan + SEPARATOR
                                            + PRIMARY_FIELD + FIELD_3 + mapField3(dtRecord.getProcessCode());

                                    convertedContent.append(detailContent);

                                    // FIELD_4, FIELD_5 and FIELD_7 :
                                    String userInput = dtRecord.getOriginalTransactionDate();
                                    Date dateField7 = formatter.parse(userInput);
                                    field7_Val = dateFormatField7.format(dateField7);

                                    detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_4 + formatAmount(dtRecord.getTransactionAmount()) +
                                            ((originalAmount != null) ? SEPARATOR + PRIMARY_FIELD + FIELD_5 + formatAmount(originalAmount) : "")
                                            + SEPARATOR + PRIMARY_FIELD + FIELD_7 + field7_Val + "000000";

                                    convertedContent.append(detailContent);
                                    // FIELD_9,FIELD_11 and FIELD_12
                                    detailContent =
                                            ((convRate != null) ? SEPARATOR + PRIMARY_FIELD + FIELD_9 + convRate : "")
                                                    + SEPARATOR + PRIMARY_FIELD + FIELD_11
                                                    + formattedStan(dtRecord.getSystemTraceAuditNumber())
                                                    + SEPARATOR
                                                    + PRIMARY_FIELD + FIELD_12 + "000000";
                                    convertedContent.append(detailContent);

                                    //  FIELD_13,FIELD_16, FIELD_18 and FIELD_22
                                    detailContent =
                                            SEPARATOR + PRIMARY_FIELD + FIELD_13 + field7_Val
                                                    + (('Y' == dtRecord.getCurrencyConversionIndicator()) ? SEPARATOR + PRIMARY_FIELD + FIELD_16 + field7_Val : "")
                                                    + SEPARATOR + PRIMARY_FIELD + FIELD_18 + dtRecord.getMerchantCategoryCode()
                                                    + SEPARATOR + PRIMARY_FIELD + FIELD_22 + dtRecord.getPosEntryMode();
                                    convertedContent.append(detailContent);

                                    // FIELD_25
                                    detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_25
                                            + getPOSConditionCode(dtRecord);  //line.substring(323, 325); // REDUCED TO LENGTH OF 2
                                    convertedContent.append(detailContent);

                                    // FIELD_38 and  FIELD_39
                                    detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_38 + dtRecord.getApprovalCode()
                                            + SEPARATOR + PRIMARY_FIELD + FIELD_39 + "00";//DEFAULT RESPONSE CODE TO 00 dtRecord.getResponseCode() ;
                                    convertedContent.append(detailContent);


                                    // FIELD_41
                                    detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_41 + terminalId;
                                    convertedContent.append(detailContent);

                                    // FIELD_42 (Discover Merchant Number:44-58 INSTEAD OF 73-75)and FIELD_43
                                    String paddedField43 = paddedField43(dtRecord);

                                    detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_42
                                            + dtRecord.getDiscoverMerchantNumber()
                                            + SEPARATOR + PRIMARY_FIELD + FIELD_43 + paddedField43;
                                    //  + dtRecord.getMerchantName() + dtRecord.getMerchantCity()
                                    //  + CurrencyAlphaCodes.getAlpha2Code(dtRecord.getMerchantState());
                                    convertedContent.append(detailContent);

                                    // FIELD_49 , FIELD_50, FIELD_59 and FIELD_123

                                    detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_49 + ((originalCurrencyCode != null) ? originalCurrencyCode : currencyCode)
                                            + (('Y' == dtRecord.getCurrencyConversionIndicator()) ? SEPARATOR + PRIMARY_FIELD + FIELD_50 + currencyCode : "")
                                            +SEPARATOR+ PRIMARY_FIELD + FIELD_123 + convertDiscoverPosToPositionPos(dtRecord);

                                    // detailContent = SEPARATOR + PRIMARY_FIELD + FIELD_49  + currencyCode +
                                    //       ((orignalCurrencyCode != null) ? SEPARATOR + PRIMARY_FIELD + FIELD_50 + orignalCurrencyCode : "")
                                    //       + SEPARATOR + PRIMARY_FIELD + FIELD_123 + POS_DATA_CODE;


                                    recordCount = recordCount + 1;
                                    convertedContent.append(detailContent);

                                    /*
                                     * ADD PRIVATE BITMAP
                                     */
                                    String last4 = pan.substring(pan.length() - 4);
                                    String first5 = pan.substring(0, 5);

                                    FIELD_P_VAL_TEMP = "320220:" + dtRecord.getApprovalCode() + ":"
                                            + formatterSW.format(date) + formatterSWTM.format(date)
                                            + ":" + last4 + first5;

                                    convertedContent.append(SEPARATOR + PRIVATE_FIELD + FIELD_P_VAL).append(FIELD_P_VAL_TEMP);
                                    //convertedContent.append(SEPARATOR + PRIVATE_FIELD + FIELD_P_VAL + FIELD_P_VAL_TEMP);
                                    String netRID=dtRecord.getNetworkReferenceId();
                                    if(netRID!=null && !netRID.trim().equals("")){
                                        convertedContent.append(SEPARATOR + PRIVATE_FIELD + P_FILED_12 + String.format("%04d", netRID.length()) + netRID);
                                    }
                                    convertedContent.append(CRLF);

                                }
                            }

                        }// END OF LOOP
                        srcFile.safeClose();


                        WriteFile data = new WriteFile(fileNameOutPath.toAbsolutePath(), true);
                        data.writeToFile(convertedContent.toString());
                        convertedContent = new StringBuilder();

                        /*
                         * MOVE DISCOVER FILE
                         */
                        Path oldFile = Paths.get(fileNameInp);
                        Path newFile = Paths.get(fileNameBk);

                        fileList.RenameFile(oldFile, newFile);

                        // logger.info("Discover FileConverter Service: End MOVE DISCOVER FILE");

                    }
                }//end of for each loop
                /*
                 *
                 * ADD TRAILER RECORD
                 */
                if (recordCount > 0) {

                    int lenTrailer = Integer.toString(recordCount).length();
                    //	TRAILER_VAL
                    TRAILER_VAL = TRAILER_VAL.substring(0, TRAILER_VAL.length() - lenTrailer) + recordCount;

                    convertedContent.append(TRAILER + SEPARATOR).append(TRAILER_VAL).append(CRLF);

                    //   convertedContent.append(TRAILER + SEPARATOR + TRAILER_VAL + CRLF);
                    /*
                     *
                     * WRITE RECORD TO FILE POST INJECT FORMAT
                     */

                    WriteFile data = new WriteFile(fileNameOutPath.toAbsolutePath(), true);

                    data.writeToFile(convertedContent.toString());
                    logger.info("End writing file");

                    result = recordCount+" transactions(s) written to output file,"
                            + " Original file(s) moved to " + backUpDir;

                }
                else {
                    result="No transaction written to output file";
                }

            } else {
                logger.info("Discover FileConverter Service:No file to process at the moment");
                // System.out.println("Discover FileConverter Service:No file to process at the moment");
                result ="No file(s) to process at the moment";

            }
        } catch (Exception e) {
            logger.error("[processContent()] Error on ProcessFileContents: " + e.getMessage());
            LoggerUtil.logError(logger, e);
            result = "Error occured: Cannot complete processing of " + fileNameInp;
            System.out.println("Error");
            java.util.logging.Logger.getLogger(ProcessFileContents.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }

        logger.info(" [processContent()] Discover FileConverter Service: End Process File Contents");
        return result;
    }


    private String mapField3(String processCode) {
        String mappedCode;

        switch (processCode) {
            case "13":
            case "14":
            case "15":
            case "16": {
                mappedCode = "00" + FIELD_3_PAD;
                break;
            }
            case "18": {
                mappedCode = "32" + FIELD_3_PAD;
                break;
            }
            case "28": {
                mappedCode = "22" + FIELD_3_PAD;
                break;
            }
            case "20": {
                mappedCode="20" + FIELD_3_PAD;
                break;
            }
            default: {
                mappedCode = "00" + FIELD_3_PAD;
                break;
            }
        }


        return mappedCode;
    }

    private String paddedField43(DetailTransactionRecord dtRecord) {

        String paddedFld43;

        String alpha2Code = CurrencyAlphaCodes.getAlpha2Code(dtRecord.getMerchantState(), dtRecord.getCurrencyConversionIndicator());
        paddedFld43 = StringUtils.rightPad(dtRecord.getMerchantName() + dtRecord.getMerchantCity(), 40, "");
        if(paddedFld43.length()>40){
            paddedFld43=paddedFld43.substring(0,40);
        }

        if ('Y' == dtRecord.getCurrencyConversionIndicator()) {
            paddedFld43 = paddedFld43.substring(0, paddedFld43.length() - alpha2Code.length()) + alpha2Code;

        } else {
            String codes=alpha2Code.trim()+"US";
            paddedFld43 = paddedFld43.substring(0, paddedFld43.length() - codes.length()) + codes;
        }


        return paddedFld43;
    }

    /**
     * @param amount
     * @return
     */
    private String formatAmount(String amount) {

        char amtLastDigit = Consts.getDigit(amount.charAt(amount.length() - 1));

        amount = amount.substring(0, amount.length() - 1) + amtLastDigit;

        return amount;
    }

    private String formattedStan(String stan) {
        Random random = new Random();
        String formattedStan = "";
        /*
        formattedStan = stan.replaceAll("[^0-9]", "0");
        */
        for (int n = 0; n < stan.length(); n++){
            if (stan.charAt(n) == ' '){
                int randomNum = random.nextInt(10);
                formattedStan += randomNum;
            }else{
                formattedStan += stan.charAt(n);
            }
        }
        return formattedStan;
    }

    private String formatEchoData(String field59){
        //Logics for future modification of echo data should be here
        if(field59==null || field59.trim().equals("")){
            return "";
        }
        //String echoData="00000000001096000000000000840^53B2      "+field59+"^";
        return field59;
    }

    private String getPOSConditionCode(DetailTransactionRecord dtRecord) {

        String discoverPosCode = dtRecord.getPosData();

        String pos_condition_code = "00";

        try {
            String pos_terminal_attendance = discoverPosCode.substring(0, 1);
            String partial_approval_indicator = discoverPosCode.substring(1, 2);

            String cardholder_presence = discoverPosCode.substring(4, 5);
            String tran_security_indicator = discoverPosCode.substring(7, 8);


            if ((partial_approval_indicator != null) && ("1".equals(partial_approval_indicator))) {
                pos_condition_code = "44";
            } else if ((pos_terminal_attendance != null) && ("1".equals(pos_terminal_attendance))) {
                pos_condition_code = "27";
            } else if (((cardholder_presence != null) && ("1".equals(cardholder_presence)))
                    || ("2".equals(cardholder_presence)) || ("3".equals(cardholder_presence)) || ("4".equals(cardholder_presence))) {
                pos_condition_code = "01";
            } else if ((tran_security_indicator != null) && ("1".equals(tran_security_indicator))) {
                pos_condition_code = "03";
            } else if ((tran_security_indicator != null) && ("2".equals(tran_security_indicator))) {
                pos_condition_code = "10";
            } else {
                pos_condition_code = "00";
            }
        } catch (Exception ex) {
            logger.error("[getPOSConditionCode()]: " + ex.getMessage());
            LoggerUtil.logError(logger, ex);
        }

        return "00";

    }

    private String convertDiscoverPosToPositionPos(DetailTransactionRecord dtRecord) {
        String discoverPosCode = dtRecord.getPosData();

        String cardDataInputCapability = discoverPosCode.substring(10, 11);

        String terminal_device_type = discoverPosCode.substring(9, 10);// Position 10 from Discover
        String cardDataInputMode = discoverPosCode.substring(10, 11);// Position 11 from Discover


        String postilionPos = "";
        String terminal_type ="";


        try {

            if ("M".equals(terminal_device_type))
            {
                terminal_type = "27";
            }
            else if ("V".equals(cardDataInputMode))
            {
                terminal_type = "90";
            }
            else if ("T".equals(cardDataInputMode))
            {
                terminal_type = "91";
            }
            else if ("S".equals(cardDataInputMode))
            {
                terminal_type = "92";
            }
            else if ("U".equals(cardDataInputMode))
            {
                terminal_type = "95";
            }
            else
            {
                terminal_type = "01";
            }

            postilionPos =
                    dtRecord.getPosEntryMode().substring(2, 3) == "1" ? "1" : "6"
                            + discoverPosCode.substring(5, 6)
                            + discoverPosCode.substring(2, 3)
                            + discoverPosCode.substring(3, 4)
                            + discoverPosCode.substring(4, 5)
                            + discoverPosCode.substring(10, 11)
                            + discoverPosCode.substring(7, 8)
                            + "5"
                            + "0"
                            + "0"
                            + dtRecord.getPosEntryMode().substring(2, 3)
                            + discoverPosCode.substring(0, 1)
                            + terminal_type;
            // + discoverPosCode.substring(9, 10) + discoverPosCode.substring(10, 11);
        } catch (Exception ex) {
            logger.error("[convertDiscoverPosToPositionPos()]: " + ex.getMessage());
            LoggerUtil.logError(logger, ex);
        }


        //return postilionPos == "" ? "" : cardDataInputCapability + postilionPos;
        return "761000705002001";
    }



}
