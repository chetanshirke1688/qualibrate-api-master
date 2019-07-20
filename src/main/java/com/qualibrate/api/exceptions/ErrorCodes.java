package com.qualibrate.api.exceptions;

/**
 * Standardized error codes
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 *
 */
public class ErrorCodes {

    public static final String INTERNAL_SERVER_ERROR = "ISE_0001";
    public static final String VALIDATION_EXCEPTION = "VE_0001";
    public static final String NOT_FOUND_EXCEPTION = "NF_0001";
    public static final String FORMAT_NOT_SUPPORTED = "FNS_0001";
    public static final String METHOD_NOT_SUPPORTED = "MNS_0001";
    public static final String INPUT_NOT_READABLE = "INR_0001";
    public static final String UNAUTHOIRZED = "403";
    public static final String CONFLICT = "409";

    public static class UserEntityAPIErrors {
        public static final String USERENTITY_ALREADY_EXISTS = "USRE_001";
        public static final String USERENTITY_NOT_FOUND = "USRE_002";
        public static final String UNAUTHORIZED_ACCESS = "USRE_003";
        public static final String USERENTITY_VALUE_ALREADY_EXISTS = "USRE_004";
        public static final String USERENTITY_VALUE_NOT_FOUND = "USRE_005";
        public static final String USERENTITY_VALUE_RESOURCE_ALREADY_EXISTS = "USRE_006";
        public static final String USERENTITY_VALUE_RESOURCE_NOT_FOUND = "USRE_007";
    }

    public static class ProjectEntityAPIErrors {
        public static final String PROJECTENTITY_ALREADY_EXISTS = "PRJ_001";
        public static final String PROJECTENTITY_NOT_FOUND = "PRJ_002";
        public static final String UNAUTHORIZED_ACCESS = "PRJ_003";
        public static final String PROJECTENTITY_VALUE_ALREADY_EXISTS = "PRJ_004";
        public static final String PROJECTENTITY_VALUE_NOT_FOUND = "PRJ_005";
        public static final String PROJECTENTITY_VALUE_RESOURCE_ALREADY_EXISTS = "PRJ_006";
        public static final String PROJECTENTITY_VALUE_RESOURCE_NOT_FOUND = "PRJ_007";
    }

    public static class AuthorizationAPIErrors {
        public static final String USERID_NOT_FOUND = "AEC_001";
    }

}
