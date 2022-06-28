package com.igorrpessoa.bowling.exception;

public enum ErrorMessagesEnum {

    INPUT_ERROR_FILE_NOT_FOUND(101, "Could not find the file. Please insert a valid path"),
    INPUT_ERROR_EMPTY_ENTRIES(102, "The path is null or empty. Please insert a correct value"),
    FILE_FORMAT_ERROR_PATTERN_NOT_ACCEPTED(201, "The file format inserted was not accepted. The file must contain a number 0 to 10 or F for fault cases"),
    FILE_FORMAT_ERROR_EMPTY_ENTRIES(202, "The file format inserted was not accepted. The file cannot be empty entries"),
    RULE_ERROR_EXTRA_ROUNDS(302, "It was found an invalid rule in the file. There is more than 10 rounds found");

    private final int code;
    private final String description;

    private ErrorMessagesEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

}
