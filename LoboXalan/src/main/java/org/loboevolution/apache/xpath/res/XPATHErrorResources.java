/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.loboevolution.apache.xpath.res;

import java.util.ListResourceBundle;

/**
 * Set up error messages. We build a two dimensional array of message keys and message strings. In
 * order to add a new message here, you need to first add a Static string constant for the Key and
 * update the contents array with Key, Value pair Also you need to update the count of
 * messages(MAX_CODE)or the count of warnings(MAX_WARNING) [ Information purpose only]
 */
public class XPATHErrorResources extends ListResourceBundle {

    /** String to use if a bad message code is used. */
    protected static final String BAD_CODE = "BAD_CODE";

    /** String to use if the message format operation failed.  */
    protected static final String FORMAT_FAILED = "FORMAT_FAILED";
    public static final String ER_CURRENT_NOT_ALLOWED_IN_MATCH =
            "ER_CURRENT_NOT_ALLOWED_IN_MATCH";
    public static final String ER_CURRENT_TAKES_NO_ARGS =
            "ER_CURRENT_TAKES_NO_ARGS";
    public static final String ER_DOCUMENT_REPLACED = "ER_DOCUMENT_REPLACED";
    public static final String ER_CONTEXT_HAS_NO_OWNERDOC =
            "ER_CONTEXT_HAS_NO_OWNERDOC";
    public static final String ER_LOCALNAME_HAS_TOO_MANY_ARGS =
            "ER_LOCALNAME_HAS_TOO_MANY_ARGS";
    public static final String ER_NAMESPACEURI_HAS_TOO_MANY_ARGS =
            "ER_NAMESPACEURI_HAS_TOO_MANY_ARGS";
    public static final String ER_NORMALIZESPACE_HAS_TOO_MANY_ARGS =
            "ER_NORMALIZESPACE_HAS_TOO_MANY_ARGS";
    public static final String ER_NUMBER_HAS_TOO_MANY_ARGS =
            "ER_NUMBER_HAS_TOO_MANY_ARGS";
    public static final String ER_NAME_HAS_TOO_MANY_ARGS =
            "ER_NAME_HAS_TOO_MANY_ARGS";
    public static final String ER_STRING_HAS_TOO_MANY_ARGS =
            "ER_STRING_HAS_TOO_MANY_ARGS";
    public static final String ER_STRINGLENGTH_HAS_TOO_MANY_ARGS =
            "ER_STRINGLENGTH_HAS_TOO_MANY_ARGS";
    public static final String ER_TRANSLATE_TAKES_3_ARGS =
            "ER_TRANSLATE_TAKES_3_ARGS";
    public static final String ER_UNPARSEDENTITYURI_TAKES_1_ARG =
            "ER_UNPARSEDENTITYURI_TAKES_1_ARG";
    public static final String ER_NAMESPACEAXIS_NOT_IMPLEMENTED =
            "ER_NAMESPACEAXIS_NOT_IMPLEMENTED";
    public static final String ER_UNKNOWN_AXIS = "ER_UNKNOWN_AXIS";
    public static final String ER_UNKNOWN_MATCH_OPERATION =
            "ER_UNKNOWN_MATCH_OPERATION";
    public static final String ER_INCORRECT_ARG_LENGTH = "ER_INCORRECT_ARG_LENGTH";
    public static final String ER_CANT_CONVERT_TO_NUMBER =
            "ER_CANT_CONVERT_TO_NUMBER";
    public static final String ER_CANT_CONVERT_XPATHRESULTTYPE_TO_NUMBER =
            "ER_CANT_CONVERT_XPATHRESULTTYPE_TO_NUMBER";
    public static final String ER_CANT_CONVERT_TO_NODELIST =
            "ER_CANT_CONVERT_TO_NODELIST";
    public static final String ER_CANT_CONVERT_TO_MUTABLENODELIST =
            "ER_CANT_CONVERT_TO_MUTABLENODELIST";
    public static final String ER_CANT_CONVERT_TO_TYPE = "ER_CANT_CONVERT_TO_TYPE";
    public static final String ER_EXPECTED_MATCH_PATTERN =
            "ER_EXPECTED_MATCH_PATTERN";
    public static final String ER_COULDNOT_GET_VAR_NAMED =
            "ER_COULDNOT_GET_VAR_NAMED";
    public static final String ER_UNKNOWN_OPCODE = "ER_UNKNOWN_OPCODE";
    public static final String ER_EXTRA_ILLEGAL_TOKENS = "ER_EXTRA_ILLEGAL_TOKENS";
    public static final String ER_EXPECTED_DOUBLE_QUOTE =
            "ER_EXPECTED_DOUBLE_QUOTE";
    public static final String ER_EXPECTED_SINGLE_QUOTE =
            "ER_EXPECTED_SINGLE_QUOTE";
    public static final String ER_EMPTY_EXPRESSION = "ER_EMPTY_EXPRESSION";
    public static final String ER_EXPECTED_BUT_FOUND = "ER_EXPECTED_BUT_FOUND";
    public static final String ER_INCORRECT_PROGRAMMER_ASSERTION =
            "ER_INCORRECT_PROGRAMMER_ASSERTION";
    public static final String ER_BOOLEAN_ARG_NO_LONGER_OPTIONAL =
            "ER_BOOLEAN_ARG_NO_LONGER_OPTIONAL";
    public static final String ER_FOUND_COMMA_BUT_NO_PRECEDING_ARG =
            "ER_FOUND_COMMA_BUT_NO_PRECEDING_ARG";
    public static final String ER_FOUND_COMMA_BUT_NO_FOLLOWING_ARG =
            "ER_FOUND_COMMA_BUT_NO_FOLLOWING_ARG";
    public static final String ER_PREDICATE_ILLEGAL_SYNTAX =
            "ER_PREDICATE_ILLEGAL_SYNTAX";
    public static final String ER_ILLEGAL_AXIS_NAME = "ER_ILLEGAL_AXIS_NAME";
    public static final String ER_UNKNOWN_NODETYPE = "ER_UNKNOWN_NODETYPE";
    public static final String ER_PATTERN_LITERAL_NEEDS_BE_QUOTED =
            "ER_PATTERN_LITERAL_NEEDS_BE_QUOTED";
    public static final String ER_COULDNOT_BE_FORMATTED_TO_NUMBER =
            "ER_COULDNOT_BE_FORMATTED_TO_NUMBER";
    public static final String ER_COULDNOT_CREATE_XMLPROCESSORLIAISON =
            "ER_COULDNOT_CREATE_XMLPROCESSORLIAISON";
    public static final String ER_DIDNOT_FIND_XPATH_SELECT_EXP =
            "ER_DIDNOT_FIND_XPATH_SELECT_EXP";
    public static final String ER_COULDNOT_FIND_ENDOP_AFTER_OPLOCATIONPATH =
            "ER_COULDNOT_FIND_ENDOP_AFTER_OPLOCATIONPATH";
    public static final String ER_ERROR_OCCURED = "ER_ERROR_OCCURED";
    public static final String ER_ILLEGAL_VARIABLE_REFERENCE =
            "ER_ILLEGAL_VARIABLE_REFERENCE";
    public static final String ER_AXES_NOT_ALLOWED = "ER_AXES_NOT_ALLOWED";
    public static final String ER_KEY_HAS_TOO_MANY_ARGS =
            "ER_KEY_HAS_TOO_MANY_ARGS";
    public static final String ER_COUNT_TAKES_1_ARG = "ER_COUNT_TAKES_1_ARG";
    public static final String ER_COULDNOT_FIND_FUNCTION =
            "ER_COULDNOT_FIND_FUNCTION";
    public static final String ER_UNSUPPORTED_ENCODING = "ER_UNSUPPORTED_ENCODING";
    public static final String ER_PROBLEM_IN_DTM_NEXTSIBLING =
            "ER_PROBLEM_IN_DTM_NEXTSIBLING";
    public static final String ER_CANNOT_WRITE_TO_EMPTYNODELISTIMPL =
            "ER_CANNOT_WRITE_TO_EMPTYNODELISTIMPL";
    public static final String ER_SETDOMFACTORY_NOT_SUPPORTED =
            "ER_SETDOMFACTORY_NOT_SUPPORTED";
    public static final String ER_PREFIX_MUST_RESOLVE = "ER_PREFIX_MUST_RESOLVE";
    public static final String ER_PARSE_NOT_SUPPORTED = "ER_PARSE_NOT_SUPPORTED";
    public static final String ER_SAX_API_NOT_HANDLED = "ER_SAX_API_NOT_HANDLED";
    public static final String ER_IGNORABLE_WHITESPACE_NOT_HANDLED =
            "ER_IGNORABLE_WHITESPACE_NOT_HANDLED";
    public static final String ER_DTM_CANNOT_HANDLE_NODES =
            "ER_DTM_CANNOT_HANDLE_NODES";
    public static final String ER_XERCES_CANNOT_HANDLE_NODES =
            "ER_XERCES_CANNOT_HANDLE_NODES";
    public static final String ER_XERCES_PARSE_ERROR_DETAILS =
            "ER_XERCES_PARSE_ERROR_DETAILS";
    public static final String ER_XERCES_PARSE_ERROR = "ER_XERCES_PARSE_ERROR";
    public static final String ER_INVALID_UTF16_SURROGATE =
            "ER_INVALID_UTF16_SURROGATE";
    public static final String ER_OIERROR = "ER_OIERROR";
    public static final String ER_CANNOT_CREATE_URL = "ER_CANNOT_CREATE_URL";
    public static final String ER_XPATH_READOBJECT = "ER_XPATH_READOBJECT";
    public static final String ER_FUNCTION_TOKEN_NOT_FOUND =
            "ER_FUNCTION_TOKEN_NOT_FOUND";
    public static final String ER_CANNOT_DEAL_XPATH_TYPE =
            "ER_CANNOT_DEAL_XPATH_TYPE";
    public static final String ER_NODESET_NOT_MUTABLE = "ER_NODESET_NOT_MUTABLE";
    public static final String ER_NODESETDTM_NOT_MUTABLE =
            "ER_NODESETDTM_NOT_MUTABLE";
    /**
     * Variable not resolvable:
     */
    public static final String ER_VAR_NOT_RESOLVABLE = "ER_VAR_NOT_RESOLVABLE";
    /**
     * Null error handler
     */
    public static final String ER_NULL_ERROR_HANDLER = "ER_NULL_ERROR_HANDLER";
    /**
     * Programmer's assertion: unknown opcode
     */
    public static final String ER_PROG_ASSERT_UNKNOWN_OPCODE =
            "ER_PROG_ASSERT_UNKNOWN_OPCODE";
    /**
     * 0 or 1
     */
    public static final String ER_ZERO_OR_ONE = "ER_ZERO_OR_ONE";
    /**
     * rtf() not supported by XRTreeFragSelectWrapper
     */
    public static final String ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER =
            "ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER";
    /**
     * asNodeIterator() not supported by XRTreeFragSelectWrapper
     */
    public static final String ER_ASNODEITERATOR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER = "ER_ASNODEITERATOR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER";
    /**
     * fsb() not supported for XStringForChars
     */
    public static final String ER_FSB_NOT_SUPPORTED_XSTRINGFORCHARS =
            "ER_FSB_NOT_SUPPORTED_XSTRINGFORCHARS";
    /**
     * Could not find variable with the name of
     */
    public static final String ER_COULD_NOT_FIND_VAR = "ER_COULD_NOT_FIND_VAR";
    /**
     * XStringForChars can not take a string for an argument
     */
    public static final String ER_XSTRINGFORCHARS_CANNOT_TAKE_STRING =
            "ER_XSTRINGFORCHARS_CANNOT_TAKE_STRING";
    /**
     * The FastStringBuffer argument can not be null
     */
    public static final String ER_FASTSTRINGBUFFER_CANNOT_BE_NULL =
            "ER_FASTSTRINGBUFFER_CANNOT_BE_NULL";
    /**
     * 2 or 3
     */
    public static final String ER_TWO_OR_THREE = "ER_TWO_OR_THREE";
    /**
     * Variable accessed before it is bound!
     */
    public static final String ER_VARIABLE_ACCESSED_BEFORE_BIND =
            "ER_VARIABLE_ACCESSED_BEFORE_BIND";
    /**
     * XStringForFSB can not take a string for an argument!
     */
    public static final String ER_FSB_CANNOT_TAKE_STRING =
            "ER_FSB_CANNOT_TAKE_STRING";
    /**
     * Error! Setting the root of a walker to null!
     */
    public static final String ER_SETTING_WALKER_ROOT_TO_NULL =
            "ER_SETTING_WALKER_ROOT_TO_NULL";
    /**
     * This NodeSetDTM can not iterate to a previous node!
     */
    public static final String ER_NODESETDTM_CANNOT_ITERATE =
            "ER_NODESETDTM_CANNOT_ITERATE";
    /**
     * This NodeSet can not iterate to a previous node!
     */
    public static final String ER_NODESET_CANNOT_ITERATE =
            "ER_NODESET_CANNOT_ITERATE";
    /**
     * This NodeSetDTM can not do indexing or counting functions!
     */
    public static final String ER_NODESETDTM_CANNOT_INDEX =
            "ER_NODESETDTM_CANNOT_INDEX";
    /**
     * This NodeSet can not do indexing or counting functions!
     */
    public static final String ER_NODESET_CANNOT_INDEX =
            "ER_NODESET_CANNOT_INDEX";
    /**
     * Can not call setShouldCacheNodes after nextNode has been called!
     */
    public static final String ER_CANNOT_CALL_SETSHOULDCACHENODE =
            "ER_CANNOT_CALL_SETSHOULDCACHENODE";
    /**
     * {0} only allows {1} arguments
     */
    public static final String ER_ONLY_ALLOWS = "ER_ONLY_ALLOWS";
    /**
     * Programmer's assertion in getNextStepPos: unknown stepType: {0}
     */
    public static final String ER_UNKNOWN_STEP = "ER_UNKNOWN_STEP";
    /**
     * Problem with RelativeLocationPath
     */
    public static final String ER_EXPECTED_REL_LOC_PATH =
            "ER_EXPECTED_REL_LOC_PATH";
    /**
     * Problem with LocationPath
     */
    public static final String ER_EXPECTED_LOC_PATH = "ER_EXPECTED_LOC_PATH";
    public static final String ER_EXPECTED_LOC_PATH_AT_END_EXPR =
            "ER_EXPECTED_LOC_PATH_AT_END_EXPR";
    /**
     * Problem with Step
     */
    public static final String ER_EXPECTED_LOC_STEP = "ER_EXPECTED_LOC_STEP";
    /**
     * Problem with NodeTest
     */
    public static final String ER_EXPECTED_NODE_TEST = "ER_EXPECTED_NODE_TEST";
    /**
     * Expected step pattern
     */
    public static final String ER_EXPECTED_STEP_PATTERN =
            "ER_EXPECTED_STEP_PATTERN";
    /**
     * Expected relative path pattern
     */
    public static final String ER_EXPECTED_REL_PATH_PATTERN =
            "ER_EXPECTED_REL_PATH_PATTERN";
    /**
     * ER_CANT_CONVERT_XPATHRESULTTYPE_TO_BOOLEAN
     */
    public static final String ER_CANT_CONVERT_TO_BOOLEAN =
            "ER_CANT_CONVERT_TO_BOOLEAN";
    /**
     * Field ER_CANT_CONVERT_TO_SINGLENODE
     */
    public static final String ER_CANT_CONVERT_TO_SINGLENODE =
            "ER_CANT_CONVERT_TO_SINGLENODE";
    /**
     * Field ER_CANT_GET_SNAPSHOT_LENGTH
     */
    public static final String ER_CANT_GET_SNAPSHOT_LENGTH =
            "ER_CANT_GET_SNAPSHOT_LENGTH";
    /**
     * Field ER_NON_ITERATOR_TYPE
     */
    public static final String ER_NON_ITERATOR_TYPE = "ER_NON_ITERATOR_TYPE";
    /**
     * Field ER_DOC_MUTATED
     */
    public static final String ER_DOC_MUTATED = "ER_DOC_MUTATED";
    public static final String ER_INVALID_XPATH_TYPE = "ER_INVALID_XPATH_TYPE";
    public static final String ER_EMPTY_XPATH_RESULT = "ER_EMPTY_XPATH_RESULT";
    public static final String ER_INCOMPATIBLE_TYPES = "ER_INCOMPATIBLE_TYPES";
    public static final String ER_NULL_RESOLVER = "ER_NULL_RESOLVER";
    public static final String ER_CANT_CONVERT_TO_STRING =
            "ER_CANT_CONVERT_TO_STRING";
    public static final String ER_NON_SNAPSHOT_TYPE = "ER_NON_SNAPSHOT_TYPE";
    public static final String ER_WRONG_DOCUMENT = "ER_WRONG_DOCUMENT";
    /* Note to translators:  The XPath expression cannot be evaluated with respect
     * to this type of node.
     */
    /**
     * Field ER_WRONG_NODETYPE
     */
    public static final String ER_WRONG_NODETYPE = "ER_WRONG_NODETYPE";
    public static final String ER_XPATH_ERROR = "ER_XPATH_ERROR";

    //BEGIN: Keys needed for exception messages of  JAXP 1.3 XPath API implementation
    public static final String ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED = "ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED";
    public static final String ER_RESOLVE_VARIABLE_RETURNS_NULL = "ER_RESOLVE_VARIABLE_RETURNS_NULL";
    public static final String ER_UNSUPPORTED_RETURN_TYPE = "ER_UNSUPPORTED_RETURN_TYPE";
    public static final String ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL = "ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL";
    public static final String ER_ARG_CANNOT_BE_NULL = "ER_ARG_CANNOT_BE_NULL";

    public static final String ER_OBJECT_MODEL_NULL = "ER_OBJECT_MODEL_NULL";
    public static final String ER_OBJECT_MODEL_EMPTY = "ER_OBJECT_MODEL_EMPTY";
    public static final String ER_FEATURE_NAME_NULL = "ER_FEATURE_NAME_NULL";
    public static final String ER_FEATURE_UNKNOWN = "ER_FEATURE_UNKNOWN";
    public static final String ER_GETTING_NULL_FEATURE = "ER_GETTING_NULL_FEATURE";
    public static final String ER_GETTING_UNKNOWN_FEATURE = "ER_GETTING_UNKNOWN_FEATURE";
    public static final String ER_NULL_XPATH_FUNCTION_RESOLVER = "ER_NULL_XPATH_FUNCTION_RESOLVER";
    public static final String ER_NULL_XPATH_VARIABLE_RESOLVER = "ER_NULL_XPATH_VARIABLE_RESOLVER";
    //END: Keys needed for exception messages of  JAXP 1.3 XPath API implementation

    public static final String WG_LOCALE_NAME_NOT_HANDLED =
            "WG_LOCALE_NAME_NOT_HANDLED";
    public static final String WG_PROPERTY_NOT_SUPPORTED =
            "WG_PROPERTY_NOT_SUPPORTED";
    public static final String WG_DONT_DO_ANYTHING_WITH_NS =
            "WG_DONT_DO_ANYTHING_WITH_NS";
    public static final String WG_SECURITY_EXCEPTION = "WG_SECURITY_EXCEPTION";
    public static final String WG_QUO_NO_LONGER_DEFINED =
            "WG_QUO_NO_LONGER_DEFINED";
    public static final String WG_NEED_DERIVED_OBJECT_TO_IMPLEMENT_NODETEST =
            "WG_NEED_DERIVED_OBJECT_TO_IMPLEMENT_NODETEST";
    public static final String WG_FUNCTION_TOKEN_NOT_FOUND =
            "WG_FUNCTION_TOKEN_NOT_FOUND";
    public static final String WG_COULDNOT_FIND_FUNCTION =
            "WG_COULDNOT_FIND_FUNCTION";
    public static final String WG_CANNOT_MAKE_URL_FROM = "WG_CANNOT_MAKE_URL_FROM";
    public static final String WG_EXPAND_ENTITIES_NOT_SUPPORTED =
            "WG_EXPAND_ENTITIES_NOT_SUPPORTED";
    public static final String WG_ILLEGAL_VARIABLE_REFERENCE =
            "WG_ILLEGAL_VARIABLE_REFERENCE";
    public static final String WG_UNSUPPORTED_ENCODING = "WG_UNSUPPORTED_ENCODING";

    /**
     * detach() not supported by XRTreeFragSelectWrapper
     */
    public static final String ER_DETACH_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER =
            "ER_DETACH_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER";
    /**
     * num() not supported by XRTreeFragSelectWrapper
     */
    public static final String ER_NUM_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER =
            "ER_NUM_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER";
    /**
     * xstr() not supported by XRTreeFragSelectWrapper
     */
    public static final String ER_XSTR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER =
            "ER_XSTR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER";
    /**
     * str() not supported by XRTreeFragSelectWrapper
     */
    public static final String ER_STR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER =
            "ER_STR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER";

    public static final String ER_FUNCTION_NOT_SUPPORTED = "ER_FUNCTION_NOT_SUPPORTED";
    public static final String ER_CANNOT_OVERWRITE_CAUSE = "ER_CANNOT_OVERWRITE_CAUSE";
    public static final String ER_NO_DEFAULT_IMPL = "ER_NO_DEFAULT_IMPL";
    public static final String ER_CHUNKEDINTARRAY_NOT_SUPPORTED = "ER_CHUNKEDINTARRAY_NOT_SUPPORTED";
    public static final String ER_OFFSET_BIGGER_THAN_SLOT = "ER_OFFSET_BIGGER_THAN_SLOT";
    public static final String ER_COROUTINE_NOT_AVAIL = "ER_COROUTINE_NOT_AVAIL";
    public static final String ER_COROUTINE_CO_EXIT = "ER_COROUTINE_CO_EXIT";
    public static final String ER_COJOINROUTINESET_FAILED = "ER_COJOINROUTINESET_FAILED";
    public static final String ER_COROUTINE_PARAM = "ER_COROUTINE_PARAM";
    public static final String ER_PARSER_DOTERMINATE_ANSWERS = "ER_PARSER_DOTERMINATE_ANSWERS";
    public static final String ER_NO_PARSE_CALL_WHILE_PARSING = "ER_NO_PARSE_CALL_WHILE_PARSING";
    public static final String ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED = "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED";
    public static final String ER_ITERATOR_AXIS_NOT_IMPLEMENTED = "ER_ITERATOR_AXIS_NOT_IMPLEMENTED";
    public static final String ER_ITERATOR_CLONE_NOT_SUPPORTED = "ER_ITERATOR_CLONE_NOT_SUPPORTED";
    public static final String ER_UNKNOWN_AXIS_TYPE = "ER_UNKNOWN_AXIS_TYPE";
    public static final String ER_AXIS_NOT_SUPPORTED = "ER_AXIS_NOT_SUPPORTED";
    public static final String ER_NO_DTMIDS_AVAIL = "ER_NO_DTMIDS_AVAIL";
    public static final String ER_NOT_SUPPORTED = "ER_NOT_SUPPORTED";
    public static final String ER_NODE_NON_NULL = "ER_NODE_NON_NULL";
    public static final String ER_COULD_NOT_RESOLVE_NODE = "ER_COULD_NOT_RESOLVE_NODE";
    public static final String ER_STARTPARSE_WHILE_PARSING = "ER_STARTPARSE_WHILE_PARSING";
    public static final String ER_STARTPARSE_NEEDS_SAXPARSER = "ER_STARTPARSE_NEEDS_SAXPARSER";
    public static final String ER_COULD_NOT_INIT_PARSER = "ER_COULD_NOT_INIT_PARSER";
    public static final String ER_EXCEPTION_CREATING_POOL = "ER_EXCEPTION_CREATING_POOL";
    public static final String ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE = "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE";
    public static final String ER_SCHEME_REQUIRED = "ER_SCHEME_REQUIRED";
    public static final String ER_NO_SCHEME_IN_URI = "ER_NO_SCHEME_IN_URI";
    public static final String ER_NO_SCHEME_INURI = "ER_NO_SCHEME_INURI";
    public static final String ER_PATH_INVALID_CHAR = "ER_PATH_INVALID_CHAR";
    public static final String ER_SCHEME_FROM_NULL_STRING = "ER_SCHEME_FROM_NULL_STRING";
    public static final String ER_SCHEME_NOT_CONFORMANT = "ER_SCHEME_NOT_CONFORMANT";
    public static final String ER_HOST_ADDRESS_NOT_WELLFORMED = "ER_HOST_ADDRESS_NOT_WELLFORMED";
    public static final String ER_PORT_WHEN_HOST_NULL = "ER_PORT_WHEN_HOST_NULL";
    public static final String ER_INVALID_PORT = "ER_INVALID_PORT";
    public static final String ER_FRAG_FOR_GENERIC_URI = "ER_FRAG_FOR_GENERIC_URI";
    public static final String ER_FRAG_WHEN_PATH_NULL = "ER_FRAG_WHEN_PATH_NULL";
    public static final String ER_FRAG_INVALID_CHAR = "ER_FRAG_INVALID_CHAR";
    public static final String ER_PARSER_IN_USE = "ER_PARSER_IN_USE";
    public static final String ER_CANNOT_CHANGE_WHILE_PARSING = "ER_CANNOT_CHANGE_WHILE_PARSING";
    public static final String ER_SELF_CAUSATION_NOT_PERMITTED = "ER_SELF_CAUSATION_NOT_PERMITTED";
    public static final String ER_NO_USERINFO_IF_NO_HOST = "ER_NO_USERINFO_IF_NO_HOST";
    public static final String ER_NO_PORT_IF_NO_HOST = "ER_NO_PORT_IF_NO_HOST";
    public static final String ER_NO_QUERY_STRING_IN_PATH = "ER_NO_QUERY_STRING_IN_PATH";
    public static final String ER_NO_FRAGMENT_STRING_IN_PATH = "ER_NO_FRAGMENT_STRING_IN_PATH";
    public static final String ER_CANNOT_INIT_URI_EMPTY_PARMS = "ER_CANNOT_INIT_URI_EMPTY_PARMS";
    public static final String ER_METHOD_NOT_SUPPORTED = "ER_METHOD_NOT_SUPPORTED";
    public static final String ER_INCRSAXSRCFILTER_NOT_RESTARTABLE = "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE";
    public static final String ER_XMLRDR_NOT_BEFORE_STARTPARSE = "ER_XMLRDR_NOT_BEFORE_STARTPARSE";
    public static final String ER_AXIS_TRAVERSER_NOT_SUPPORTED = "ER_AXIS_TRAVERSER_NOT_SUPPORTED";
    public static final String ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER = "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER";
    public static final String ER_SYSTEMID_UNKNOWN = "ER_SYSTEMID_UNKNOWN";
    public static final String ER_LOCATION_UNKNOWN = "ER_LOCATION_UNKNOWN";
    public static final String ER_CREATEDOCUMENT_NOT_SUPPORTED = "ER_CREATEDOCUMENT_NOT_SUPPORTED";
    public static final String ER_CHILD_HAS_NO_OWNER_DOCUMENT = "ER_CHILD_HAS_NO_OWNER_DOCUMENT";
    public static final String ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT = "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT";
    public static final String ER_CANT_OUTPUT_TEXT_BEFORE_DOC = "ER_CANT_OUTPUT_TEXT_BEFORE_DOC";
    public static final String ER_CANT_HAVE_MORE_THAN_ONE_ROOT = "ER_CANT_HAVE_MORE_THAN_ONE_ROOT";
    public static final String ER_ARG_LOCALNAME_NULL = "ER_ARG_LOCALNAME_NULL";
    public static final String ER_ARG_LOCALNAME_INVALID = "ER_ARG_LOCALNAME_INVALID";
    public static final String ER_ARG_PREFIX_INVALID = "ER_ARG_PREFIX_INVALID";
    public static final String ER_NAME_CANT_START_WITH_COLON = "ER_NAME_CANT_START_WITH_COLON";

    // Error messages...


    /**
     * Get the association list.
     *
     * @return The association list.
     */
    public Object[][] getContents() {
        return new Object[][]{

                {"ERROR0000", "{0}"},

                {ER_CURRENT_NOT_ALLOWED_IN_MATCH, "The current() function is not allowed in a match pattern!"},

                {ER_CURRENT_TAKES_NO_ARGS, "The current() function does not accept arguments!"},

                {ER_DOCUMENT_REPLACED,
                        "document() function implementation has been replaced by org.apache.xalan.xslt.FuncDocument!"},

                {ER_CONTEXT_HAS_NO_OWNERDOC,
                        "context does not have an owner document!"},

                {ER_LOCALNAME_HAS_TOO_MANY_ARGS,
                        "local-name() has too many arguments."},

                {ER_NAMESPACEURI_HAS_TOO_MANY_ARGS,
                        "namespace-uri() has too many arguments."},

                {ER_NORMALIZESPACE_HAS_TOO_MANY_ARGS,
                        "normalize-space() has too many arguments."},

                {ER_NUMBER_HAS_TOO_MANY_ARGS,
                        "number() has too many arguments."},

                {ER_NAME_HAS_TOO_MANY_ARGS,
                        "name() has too many arguments."},

                {ER_STRING_HAS_TOO_MANY_ARGS,
                        "string() has too many arguments."},

                {ER_STRINGLENGTH_HAS_TOO_MANY_ARGS,
                        "string-length() has too many arguments."},

                {ER_TRANSLATE_TAKES_3_ARGS,
                        "The translate() function takes three arguments!"},

                {ER_UNPARSEDENTITYURI_TAKES_1_ARG,
                        "The unparsed-entity-uri function should take one argument!"},

                {ER_NAMESPACEAXIS_NOT_IMPLEMENTED,
                        "namespace axis not implemented yet!"},

                {ER_UNKNOWN_AXIS,
                        "unknown axis: {0}"},

                {ER_UNKNOWN_MATCH_OPERATION,
                        "unknown match operation!"},

                {ER_INCORRECT_ARG_LENGTH,
                        "Arg length of processing-instruction() node test is incorrect!"},

                {ER_CANT_CONVERT_TO_NUMBER,
                        "Can not convert {0} to a number"},

                {ER_CANT_CONVERT_TO_NODELIST,
                        "Can not convert {0} to a NodeList!"},

                {ER_CANT_CONVERT_TO_MUTABLENODELIST,
                        "Can not convert {0} to a NodeSetDTM!"},

                {ER_CANT_CONVERT_TO_TYPE,
                        "Can not convert {0} to a type#{1}"},

                {ER_EXPECTED_MATCH_PATTERN,
                        "Expected match pattern in getMatchScore!"},

                {ER_COULDNOT_GET_VAR_NAMED,
                        "Could not get variable named {0}"},

                {ER_UNKNOWN_OPCODE,
                        "ERROR! Unknown op code: {0}"},

                {ER_EXTRA_ILLEGAL_TOKENS,
                        "Extra illegal tokens: {0}"},

                {ER_EXPECTED_DOUBLE_QUOTE,
                        "misquoted literal... expected double quote!"},

                {ER_EXPECTED_SINGLE_QUOTE,
                        "misquoted literal... expected single quote!"},

                {ER_EMPTY_EXPRESSION,
                        "Empty expression!"},

                {ER_EXPECTED_BUT_FOUND,
                        "Expected {0}, but found: {1}"},

                {ER_INCORRECT_PROGRAMMER_ASSERTION,
                        "Programmer assertion is incorrect! - {0}"},

                {ER_BOOLEAN_ARG_NO_LONGER_OPTIONAL,
                        "boolean(...) argument is no longer optional with 19990709 XPath draft."},

                {ER_FOUND_COMMA_BUT_NO_PRECEDING_ARG,
                        "Found ',' but no preceding argument!"},

                {ER_FOUND_COMMA_BUT_NO_FOLLOWING_ARG,
                        "Found ',' but no following argument!"},

                {ER_PREDICATE_ILLEGAL_SYNTAX,
                        "'..[predicate]' or '.[predicate]' is illegal syntax.  Use 'self::node()[predicate]' instead."},

                {ER_ILLEGAL_AXIS_NAME,
                        "illegal axis name: {0}"},

                {ER_UNKNOWN_NODETYPE,
                        "Unknown nodetype: {0}"},

                {ER_PATTERN_LITERAL_NEEDS_BE_QUOTED,
                        "Pattern literal ({0}) needs to be quoted!"},

                {ER_COULDNOT_BE_FORMATTED_TO_NUMBER,
                        "{0} could not be formatted to a number!"},

                {ER_COULDNOT_CREATE_XMLPROCESSORLIAISON,
                        "Could not create XML TransformerFactory Liaison: {0}"},

                {ER_DIDNOT_FIND_XPATH_SELECT_EXP,
                        "Error! Did not find xpath select expression (-select)."},

                {ER_COULDNOT_FIND_ENDOP_AFTER_OPLOCATIONPATH,
                        "ERROR! Could not find ENDOP after OP_LOCATIONPATH"},

                {ER_ERROR_OCCURED,
                        "Error occured!"},

                {ER_ILLEGAL_VARIABLE_REFERENCE,
                        "VariableReference given for variable out of context or without definition!  Name = {0}"},

                {ER_AXES_NOT_ALLOWED,
                        "Only child:: and attribute:: axes are allowed in match patterns!  Offending axes = {0}"},

                {ER_KEY_HAS_TOO_MANY_ARGS,
                        "key() has an incorrect number of arguments."},

                {ER_COUNT_TAKES_1_ARG,
                        "The count function should take one argument!"},

                {ER_COULDNOT_FIND_FUNCTION,
                        "Could not find function: {0}"},

                {ER_UNSUPPORTED_ENCODING,
                        "Unsupported encoding: {0}"},

                {ER_PROBLEM_IN_DTM_NEXTSIBLING,
                        "Problem occured in DTM in getNextSibling... trying to recover"},

                {ER_CANNOT_WRITE_TO_EMPTYNODELISTIMPL,
                        "Programmer error: EmptyNodeList can not be written to."},

                {ER_SETDOMFACTORY_NOT_SUPPORTED,
                        "setDOMFactory is not supported by XPathContext!"},

                {ER_PREFIX_MUST_RESOLVE,
                        "Prefix must resolve to a namespace: {0}"},

                {ER_PARSE_NOT_SUPPORTED,
                        "parse (InputSource source) not supported in XPathContext! Can not open {0}"},

                {ER_SAX_API_NOT_HANDLED,
                        "SAX API characters(char ch[]... not handled by the DTM!"},

                {ER_IGNORABLE_WHITESPACE_NOT_HANDLED,
                        "ignorableWhitespace(char ch[]... not handled by the DTM!"},

                {ER_DTM_CANNOT_HANDLE_NODES,
                        "DTMLiaison can not handle nodes of type {0}"},

                {ER_XERCES_CANNOT_HANDLE_NODES,
                        "DOM2Helper can not handle nodes of type {0}"},

                {ER_XERCES_PARSE_ERROR_DETAILS,
                        "DOM2Helper.parse error: SystemID - {0} line - {1}"},

                {ER_XERCES_PARSE_ERROR,
                        "DOM2Helper.parse error"},

                {ER_INVALID_UTF16_SURROGATE,
                        "Invalid UTF-16 surrogate detected: {0} ?"},

                {ER_OIERROR,
                        "IO error"},

                {ER_CANNOT_CREATE_URL,
                        "Cannot create url for: {0}"},

                {ER_XPATH_READOBJECT,
                        "In XPath.readObject: {0}"},

                {ER_FUNCTION_TOKEN_NOT_FOUND,
                        "function token not found."},

                {ER_CANNOT_DEAL_XPATH_TYPE,
                        "Can not deal with XPath type: {0}"},

                {ER_NODESET_NOT_MUTABLE,
                        "This NodeSet is not mutable"},

                {ER_NODESETDTM_NOT_MUTABLE,
                        "This NodeSetDTM is not mutable"},

                {ER_VAR_NOT_RESOLVABLE,
                        "Variable not resolvable: {0}"},

                {ER_NULL_ERROR_HANDLER,
                        "Null error handler"},

                {ER_PROG_ASSERT_UNKNOWN_OPCODE,
                        "Programmer''s assertion: unknown opcode: {0}"},

                {ER_ZERO_OR_ONE,
                        "0 or 1"},

                {ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER,
                        "rtf() not supported by XRTreeFragSelectWrapper"},

                {ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER,
                        "asNodeIterator() not supported by XRTreeFragSelectWrapper"},

                /*  detach() not supported by XRTreeFragSelectWrapper   */
                {ER_DETACH_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER,
                        "detach() not supported by XRTreeFragSelectWrapper"},

                /*  num() not supported by XRTreeFragSelectWrapper   */
                {ER_NUM_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER,
                        "num() not supported by XRTreeFragSelectWrapper"},

                /*  xstr() not supported by XRTreeFragSelectWrapper   */
                {ER_XSTR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER,
                        "xstr() not supported by XRTreeFragSelectWrapper"},

                /*  str() not supported by XRTreeFragSelectWrapper   */
                {ER_STR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER,
                        "str() not supported by XRTreeFragSelectWrapper"},

                {ER_FSB_NOT_SUPPORTED_XSTRINGFORCHARS,
                        "fsb() not supported for XStringForChars"},

                {ER_COULD_NOT_FIND_VAR,
                        "Could not find variable with the name of {0}"},

                {ER_XSTRINGFORCHARS_CANNOT_TAKE_STRING,
                        "XStringForChars can not take a string for an argument"},

                {ER_FASTSTRINGBUFFER_CANNOT_BE_NULL,
                        "The FastStringBuffer argument can not be null"},

                {ER_TWO_OR_THREE,
                        "2 or 3"},

                {ER_VARIABLE_ACCESSED_BEFORE_BIND,
                        "Variable accessed before it is bound!"},

                {ER_FSB_CANNOT_TAKE_STRING,
                        "XStringForFSB can not take a string for an argument!"},

                {ER_SETTING_WALKER_ROOT_TO_NULL,
                        "\n !!!! Error! Setting the root of a walker to null!!!"},

                {ER_NODESETDTM_CANNOT_ITERATE,
                        "This NodeSetDTM can not iterate to a previous node!"},

                {ER_NODESET_CANNOT_ITERATE,
                        "This NodeSet can not iterate to a previous node!"},

                {ER_NODESETDTM_CANNOT_INDEX,
                        "This NodeSetDTM can not do indexing or counting functions!"},

                {ER_NODESET_CANNOT_INDEX,
                        "This NodeSet can not do indexing or counting functions!"},

                {ER_CANNOT_CALL_SETSHOULDCACHENODE,
                        "Can not call setShouldCacheNodes after nextNode has been called!"},

                {ER_ONLY_ALLOWS,
                        "{0} only allows {1} arguments"},

                {ER_UNKNOWN_STEP,
                        "Programmer''s assertion in getNextStepPos: unknown stepType: {0}"},

                //Note to translators:  A relative location path is a form of XPath expression.
                // The message indicates that such an expression was expected following the
                // characters '/' or '//', but was not found.
                {ER_EXPECTED_REL_LOC_PATH,
                        "A relative location path was expected following the '/' or '//' token."},

                // Note to translators:  A location path is a form of XPath expression.
                // The message indicates that syntactically such an expression was expected,but
                // the characters specified by the substitution text were encountered instead.
                {ER_EXPECTED_LOC_PATH,
                        "A location path was expected, but the following token was encountered\u003a  {0}"},

                // Note to translators:  A location path is a form of XPath expression.
                // The message indicates that syntactically such a subexpression was expected,
                // but no more characters were found in the expression.
                {ER_EXPECTED_LOC_PATH_AT_END_EXPR,
                        "A location path was expected, but the end of the XPath expression was found instead."},

                // Note to translators:  A location step is part of an XPath expression.
                // The message indicates that syntactically such an expression was expected
                // following the specified characters.
                {ER_EXPECTED_LOC_STEP,
                        "A location step was expected following the '/' or '//' token."},

                // Note to translators:  A node test is part of an XPath expression that is
                // used to test for particular kinds of nodes.  In this case, a node test that
                // consists of an NCName followed by a colon and an asterisk or that consists
                // of a QName was expected, but was not found.
                {ER_EXPECTED_NODE_TEST,
                        "A node test that matches either NCName:* or QName was expected."},

                // Note to translators:  A step pattern is part of an XPath expression.
                // The message indicates that syntactically such an expression was expected,
                // but the specified character was found in the expression instead.
                {ER_EXPECTED_STEP_PATTERN,
                        "A step pattern was expected, but '/' was encountered."},

                // Note to translators: A relative path pattern is part of an XPath expression.
                // The message indicates that syntactically such an expression was expected,
                // but was not found.
                {ER_EXPECTED_REL_PATH_PATTERN,
                        "A relative path pattern was expected."},

                // Note to translators:  The substitution text is the name of a data type.  The
                // message indicates that a value of a particular type could not be converted
                // to a value of type boolean.
                {ER_CANT_CONVERT_TO_BOOLEAN,
                        "The XPathResult of XPath expression ''{0}'' has an XPathResultType of {1} which cannot be converted to a boolean."},

                // Note to translators: Do not translate ANY_UNORDERED_NODE_TYPE and
                // FIRST_ORDERED_NODE_TYPE.
                {ER_CANT_CONVERT_TO_SINGLENODE,
                        "The XPathResult of XPath expression ''{0}'' has an XPathResultType of {1} which cannot be converted to a single node. The method getSingleNodeValue applies only to types ANY_UNORDERED_NODE_TYPE and FIRST_ORDERED_NODE_TYPE."},

                // Note to translators: Do not translate UNORDERED_NODE_SNAPSHOT_TYPE and
                // ORDERED_NODE_SNAPSHOT_TYPE.
                {ER_CANT_GET_SNAPSHOT_LENGTH,
                        "The method getSnapshotLength cannot be called on the XPathResult of XPath expression ''{0}'' because its XPathResultType is {1}. This method applies only to types UNORDERED_NODE_SNAPSHOT_TYPE and ORDERED_NODE_SNAPSHOT_TYPE."},

                {ER_NON_ITERATOR_TYPE,
                        "The method iterateNext cannot be called on the XPathResult of XPath expression ''{0}'' because its XPathResultType is {1}. This method applies only to types UNORDERED_NODE_ITERATOR_TYPE and ORDERED_NODE_ITERATOR_TYPE."},

                // Note to translators: This message indicates that the document being operated
                // upon changed, so the iterator object that was being used to traverse the
                // document has now become invalid.
                {ER_DOC_MUTATED,
                        "Document mutated since result was returned. Iterator is invalid."},

                {ER_INVALID_XPATH_TYPE,
                        "Invalid XPath type argument: {0}"},

                {ER_EMPTY_XPATH_RESULT,
                        "Empty XPath result object"},

                {ER_INCOMPATIBLE_TYPES,
                        "The XPathResult of XPath expression ''{0}'' has an XPathResultType of {1} which cannot be coerced into the specified XPathResultType of {2}."},

                {ER_NULL_RESOLVER,
                        "Unable to resolve prefix with null prefix resolver."},

                // Note to translators:  The substitution text is the name of a data type.  The
                // message indicates that a value of a particular type could not be converted
                // to a value of type string.
                {ER_CANT_CONVERT_TO_STRING,
                        "The XPathResult of XPath expression ''{0}'' has an XPathResultType of {1} which cannot be converted to a string."},

                // Note to translators: Do not translate snapshotItem,
                // UNORDERED_NODE_SNAPSHOT_TYPE and ORDERED_NODE_SNAPSHOT_TYPE.
                {ER_NON_SNAPSHOT_TYPE,
                        "The method snapshotItem cannot be called on the XPathResult of XPath expression ''{0}'' because its XPathResultType is {1}. This method applies only to types UNORDERED_NODE_SNAPSHOT_TYPE and ORDERED_NODE_SNAPSHOT_TYPE."},

                // Note to translators:  XPathEvaluator is a Java interface name.  An
                // XPathEvaluator is created with respect to a particular XML document, and in
                // this case the expression represented by this object was being evaluated with
                // respect to a context node from a different document.
                {ER_WRONG_DOCUMENT,
                        "Context node does not belong to the document that is bound to this XPathEvaluator."},

                // Note to translators:  The XPath expression cannot be evaluated with respect
                // to this type of node.
                {ER_WRONG_NODETYPE,
                        "The context node type is not supported."},

                {ER_XPATH_ERROR,
                        "Unknown error in XPath."},

                {ER_CANT_CONVERT_XPATHRESULTTYPE_TO_NUMBER,
                        "The XPathResult of XPath expression ''{0}'' has an XPathResultType of {1} which cannot be converted to a number"},

                //BEGIN:  Definitions of error keys used  in exception messages of  JAXP 1.3 XPath API implementation

                /** Field ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED                       */

                {ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED,
                        "Extension function: ''{0}'' can not be invoked when the XMLConstants.FEATURE_SECURE_PROCESSING feature is set to true."},

                /** Field ER_RESOLVE_VARIABLE_RETURNS_NULL                       */

                {ER_RESOLVE_VARIABLE_RETURNS_NULL,
                        "resolveVariable for variable {0} returning null"},

                /** Field ER_UNSUPPORTED_RETURN_TYPE                       */

                {ER_UNSUPPORTED_RETURN_TYPE,
                        "UnSupported Return Type : {0}"},

                /** Field ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL                       */

                {ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL,
                        "Source and/or Return Type can not be null"},

                /** Field ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL                       */

                {ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL,
                        "Source and/or Return Type can not be null"},

                /** Field ER_ARG_CANNOT_BE_NULL                       */

                {ER_ARG_CANNOT_BE_NULL,
                        "{0} argument can not be null"},

                /** Field ER_OBJECT_MODEL_NULL                       */

                {ER_OBJECT_MODEL_NULL,
                        "{0}#isObjectModelSupported( String objectModel ) cannot be called with objectModel == null"},

                /** Field ER_OBJECT_MODEL_EMPTY                       */

                {ER_OBJECT_MODEL_EMPTY,
                        "{0}#isObjectModelSupported( String objectModel ) cannot be called with objectModel == \"\""},

                /** Field ER_OBJECT_MODEL_EMPTY                       */

                {ER_FEATURE_NAME_NULL,
                        "Trying to set a feature with a null name: {0}#setFeature( null, {1})"},

                /** Field ER_FEATURE_UNKNOWN                       */

                {ER_FEATURE_UNKNOWN,
                        "Trying to set the unknown feature \"{0}\":{1}#setFeature({0},{2})"},

                /** Field ER_GETTING_NULL_FEATURE                       */

                {ER_GETTING_NULL_FEATURE,
                        "Trying to get a feature with a null name: {0}#getFeature(null)"},

                /** Field ER_GETTING_NULL_FEATURE                       */

                {ER_GETTING_UNKNOWN_FEATURE,
                        "Trying to get the unknown feature \"{0}\":{1}#getFeature({0})"},

                /** Field ER_NULL_XPATH_FUNCTION_RESOLVER                       */

                {ER_NULL_XPATH_FUNCTION_RESOLVER,
                        "Attempting to set a null XPathFunctionResolver:{0}#setXPathFunctionResolver(null)"},

                /** Field ER_NULL_XPATH_VARIABLE_RESOLVER                       */

                {ER_NULL_XPATH_VARIABLE_RESOLVER,
                        "Attempting to set a null XPathVariableResolver:{0}#setXPathVariableResolver(null)"},

                //END:  Definitions of error keys used  in exception messages of  JAXP 1.3 XPath API implementation

                // Warnings...

                {WG_LOCALE_NAME_NOT_HANDLED,
                        "locale name in the format-number function not yet handled!"},

                {WG_PROPERTY_NOT_SUPPORTED,
                        "XSL Property not supported: {0}"},

                {WG_DONT_DO_ANYTHING_WITH_NS,
                        "Do not currently do anything with namespace {0} in property: {1}"},

                {WG_SECURITY_EXCEPTION,
                        "SecurityException when trying to access XSL system property: {0}"},

                {WG_QUO_NO_LONGER_DEFINED,
                        "Old syntax: quo(...) is no longer defined in XPath."},

                {WG_NEED_DERIVED_OBJECT_TO_IMPLEMENT_NODETEST,
                        "XPath needs a derived object to implement nodeTest!"},

                {WG_FUNCTION_TOKEN_NOT_FOUND,
                        "function token not found."},

                {WG_COULDNOT_FIND_FUNCTION,
                        "Could not find function: {0}"},

                {WG_CANNOT_MAKE_URL_FROM,
                        "Can not make URL from: {0}"},

                {WG_EXPAND_ENTITIES_NOT_SUPPORTED,
                        "-E option not supported for DTM parser"},

                {WG_ILLEGAL_VARIABLE_REFERENCE,
                        "VariableReference given for variable out of context or without definition!  Name = {0}"},

                {WG_UNSUPPORTED_ENCODING,
                        "Unsupported encoding: {0}"},

                {"ER0000", "{0}"},

                {ER_FUNCTION_NOT_SUPPORTED,
                        "Function not supported!"},

                {ER_CANNOT_OVERWRITE_CAUSE,
                        "Cannot overwrite cause"},

                {ER_NO_DEFAULT_IMPL,
                        "No default implementation found "},

                {ER_CHUNKEDINTARRAY_NOT_SUPPORTED,
                        "ChunkedIntArray({0}) not currently supported"},

                {ER_OFFSET_BIGGER_THAN_SLOT,
                        "Offset bigger than slot"},

                {ER_COROUTINE_NOT_AVAIL,
                        "Coroutine not available, id={0}"},

                {ER_COROUTINE_CO_EXIT,
                        "CoroutineManager received co_exit() request"},

                {ER_COJOINROUTINESET_FAILED,
                        "co_joinCoroutineSet() failed"},

                {ER_COROUTINE_PARAM,
                        "Coroutine parameter error ({0})"},

                {ER_PARSER_DOTERMINATE_ANSWERS,
                        "\nUNEXPECTED: Parser doTerminate answers {0}"},

                {ER_NO_PARSE_CALL_WHILE_PARSING,
                        "parse may not be called while parsing"},

                {ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED,
                        "Error: typed iterator for axis  {0} not implemented"},

                {ER_ITERATOR_AXIS_NOT_IMPLEMENTED,
                        "Error: iterator for axis {0} not implemented "},

                {ER_ITERATOR_CLONE_NOT_SUPPORTED,
                        "Iterator clone not supported"},

                {ER_UNKNOWN_AXIS_TYPE,
                        "Unknown axis traversal type: {0}"},

                {ER_AXIS_NOT_SUPPORTED,
                        "Axis traverser not supported: {0}"},

                {ER_NO_DTMIDS_AVAIL,
                        "No more DTM IDs are available"},

                {ER_NOT_SUPPORTED,
                        "Not supported: {0}"},

                {ER_NODE_NON_NULL,
                        "Node must be non-null for getDTMHandleFromNode"},

                {ER_COULD_NOT_RESOLVE_NODE,
                        "Could not resolve the node to a handle"},

                {ER_STARTPARSE_WHILE_PARSING,
                        "startParse may not be called while parsing"},

                {ER_STARTPARSE_NEEDS_SAXPARSER,
                        "startParse needs a non-null SAXParser"},

                {ER_COULD_NOT_INIT_PARSER,
                        "could not initialize parser with"},

                {ER_EXCEPTION_CREATING_POOL,
                        "exception creating new instance for pool"},

                {ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE,
                        "Path contains invalid escape sequence"},

                {ER_SCHEME_REQUIRED,
                        "Scheme is required!"},

                {ER_NO_SCHEME_IN_URI,
                        "No scheme found in URI: {0}"},

                {ER_NO_SCHEME_INURI,
                        "No scheme found in URI"},

                {ER_PATH_INVALID_CHAR,
                        "Path contains invalid character: {0}"},

                {ER_SCHEME_FROM_NULL_STRING,
                        "Cannot set scheme from null string"},

                {ER_SCHEME_NOT_CONFORMANT,
                        "The scheme is not conformant."},

                {ER_HOST_ADDRESS_NOT_WELLFORMED,
                        "Host is not a well formed address"},

                {ER_PORT_WHEN_HOST_NULL,
                        "Port cannot be set when host is null"},

                {ER_INVALID_PORT,
                        "Invalid port number"},

                {ER_FRAG_FOR_GENERIC_URI,
                        "Fragment can only be set for a generic URI"},

                {ER_FRAG_WHEN_PATH_NULL,
                        "Fragment cannot be set when path is null"},

                {ER_FRAG_INVALID_CHAR,
                        "Fragment contains invalid character"},

                {ER_PARSER_IN_USE,
                        "Parser is already in use"},

                {ER_CANNOT_CHANGE_WHILE_PARSING,
                        "Cannot change {0} {1} while parsing"},

                {ER_SELF_CAUSATION_NOT_PERMITTED,
                        "Self-causation not permitted"},

                {ER_NO_USERINFO_IF_NO_HOST,
                        "Userinfo may not be specified if host is not specified"},

                {ER_NO_PORT_IF_NO_HOST,
                        "Port may not be specified if host is not specified"},

                {ER_NO_QUERY_STRING_IN_PATH,
                        "Query string cannot be specified in path and query string"},

                {ER_NO_FRAGMENT_STRING_IN_PATH,
                        "Fragment cannot be specified in both the path and fragment"},

                {ER_CANNOT_INIT_URI_EMPTY_PARMS,
                        "Cannot initialize URI with empty parameters"},

                {ER_METHOD_NOT_SUPPORTED,
                        "Method not yet supported "},

                {ER_INCRSAXSRCFILTER_NOT_RESTARTABLE,
                        "IncrementalSAXSource_Filter not currently restartable"},

                {ER_XMLRDR_NOT_BEFORE_STARTPARSE,
                        "XMLReader not before startParse request"},

                {ER_AXIS_TRAVERSER_NOT_SUPPORTED,
                        "Axis traverser not supported: {0}"},

                {ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER,
                        "ListingErrorHandler created with null PrintWriter!"},

                {ER_SYSTEMID_UNKNOWN,
                        "SystemId Unknown"},

                {ER_LOCATION_UNKNOWN,
                        "Location of error unknown"},

                {ER_PREFIX_MUST_RESOLVE,
                        "Prefix must resolve to a namespace: {0}"},

                {ER_CREATEDOCUMENT_NOT_SUPPORTED,
                        "createDocument() not supported in XPathContext!"},

                {ER_CHILD_HAS_NO_OWNER_DOCUMENT,
                        "Attribute child does not have an owner document!"},

                {ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT,
                        "Attribute child does not have an owner document element!"},

                {ER_CANT_OUTPUT_TEXT_BEFORE_DOC,
                        "Warning: can't output text before document element!  Ignoring..."},

                {ER_CANT_HAVE_MORE_THAN_ONE_ROOT,
                        "Can't have more than one root on a DOM!"},

                {ER_ARG_LOCALNAME_NULL,
                        "Argument 'localName' is null"},

                // Note to translators:  A QNAME has the syntactic form [NCName:]NCName
                // The localname is the portion after the optional colon; the message indicates
                // that there is a problem with that part of the QNAME.
                {ER_ARG_LOCALNAME_INVALID,
                        "Localname in QNAME should be a valid NCName"},

                // Note to translators:  A QNAME has the syntactic form [NCName:]NCName
                // The prefix is the portion before the optional colon; the message indicates
                // that there is a problem with that part of the QNAME.
                {ER_ARG_PREFIX_INVALID,
                        "Prefix in QNAME should be a valid NCName"},

                {ER_NAME_CANT_START_WITH_COLON,
                        "Name cannot start with a colon"},

                {"BAD_CODE", "Parameter to createMessage was out of bounds"},
                {"FORMAT_FAILED", "Exception thrown during messageFormat call"},
                {"line", "Line #"},
                {"column", "Column #"},


                // Other miscellaneous text used inside the code...
                {"ui_language", "en"},
                {"help_language", "en"},
                {"language", "en"},
                {"BAD_CODE", "Parameter to createMessage was out of bounds"},
                {"FORMAT_FAILED", "Exception thrown during messageFormat call"},
                {"version", ">>>>>>> Xalan Version "},
                {"version2", "<<<<<<<"},
                {"yes", "yes"},
                {"line", "Line #"},
                {"column", "Column #"},
                {"xsldone", "XSLProcessor: done"},
                {"xpath_option", "xpath options: "},
                {"optionIN", "   [-in inputXMLURL]"},
                {"optionSelect", "   [-select xpath expression]"},
                {"optionMatch", "   [-match match pattern (for match diagnostics)]"},
                {"optionAnyExpr", "Or just an xpath expression will do a diagnostic dump"},
                {"noParsermsg1", "XSL Process was not successful."},
                {"noParsermsg2", "** Could not find parser **"},
                {"noParsermsg3", "Please check your classpath."},
                {"noParsermsg4", "If you don't have IBM's XML Parser for Java, you can download it from"},
                {"noParsermsg5", "IBM's AlphaWorks: http://www.alphaworks.ibm.com/formula/xml"},
                {"gtone", ">1"},
                {"zero", "0"},
                {"one", "1"},
                {"two", "2"},
                {"three", "3"}

        };
    }
}
