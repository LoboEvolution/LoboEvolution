/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * This class implements the JavaScript scanner.
 *
 * It is based on the C source files jsscan.c and jsscan.h
 * in the jsref package.
 *
 * @see org.mozilla.javascript.Parser
 * Author Mike McCabe
 * Author Brendan Eich
 *
 */
public class Token
{
    public static enum CommentType {
        LINE, BLOCK_COMMENT, JSDOC, HTML
    }

    // debug flags
    /** Constant <code>printTrees=false</code> */
    public static final boolean printTrees = false;
    static final boolean printICode = false;
    static final boolean printNames = printTrees || printICode;

    /**
     * Token types.  These values correspond to JSTokenType values in
     * jsscan.c.
     */

    /** Constant <code>EOF=0</code> */
    /** Constant <code>EOL=1</code> */
    /** Constant <code>FIRST_BYTECODE_TOKEN=2</code> */
    /** Constant <code>ENTERWITH=2</code> */
    /** Constant <code>LEAVEWITH=3</code> */
    /** Constant <code>RETURN=4</code> */
    /** Constant <code>GOTO=5</code> */
    /** Constant <code>IFEQ=6</code> */
    /** Constant <code>IFNE=7</code> */
    /** Constant <code>SETNAME=8</code> */
    /** Constant <code>BITOR=9</code> */
    /** Constant <code>BITXOR=10</code> */
    /** Constant <code>BITAND=11</code> */
    /** Constant <code>EQ=12</code> */
    /** Constant <code>NE=13</code> */
    /** Constant <code>LT=14</code> */
    /** Constant <code>LE=15</code> */
    /** Constant <code>GT=16</code> */
    /** Constant <code>GE=17</code> */
    /** Constant <code>LSH=18</code> */
    /** Constant <code>RSH=19</code> */
    /** Constant <code>URSH=20</code> */
    /** Constant <code>ADD=21</code> */
    /** Constant <code>SUB=22</code> */
    /** Constant <code>MUL=23</code> */
    /** Constant <code>DIV=24</code> */
    /** Constant <code>MOD=25</code> */
    /** Constant <code>NOT=26</code> */
    /** Constant <code>BITNOT=27</code> */
    /** Constant <code>POS=28</code> */
    /** Constant <code>NEG=29</code> */
    /** Constant <code>NEW=30</code> */
    /** Constant <code>DELPROP=31</code> */
    /** Constant <code>TYPEOF=32</code> */
    /** Constant <code>GETPROP=33</code> */
    /** Constant <code>GETPROPNOWARN=34</code> */
    /** Constant <code>SETPROP=35</code> */
    /** Constant <code>GETELEM=36</code> */
    /** Constant <code>SETELEM=37</code> */
    /** Constant <code>CALL=38</code> */
    /** Constant <code>NAME=39</code> */
    /** Constant <code>NUMBER=40</code> */
    /** Constant <code>STRING=41</code> */
    /** Constant <code>NULL=42</code> */
    /** Constant <code>THIS=43</code> */
    /** Constant <code>FALSE=44</code> */
    /** Constant <code>TRUE=45</code> */
    /** Constant <code>SHEQ=46</code> */
    /** Constant <code>SHNE=47</code> */
    /** Constant <code>REGEXP=48</code> */
    /** Constant <code>BINDNAME=49</code> */
    /** Constant <code>THROW=50</code> */
    /** Constant <code>RETHROW=51</code> */
    /** Constant <code>IN=52</code> */
    /** Constant <code>INSTANCEOF=53</code> */
    /** Constant <code>LOCAL_LOAD=54</code> */
    /** Constant <code>GETVAR=55</code> */
    /** Constant <code>SETVAR=56</code> */
    /** Constant <code>CATCH_SCOPE=57</code> */
    /** Constant <code>ENUM_INIT_KEYS=58</code> */
    /** Constant <code>ENUM_INIT_VALUES=59</code> */
    /** Constant <code>ENUM_INIT_ARRAY=60</code> */
    /** Constant <code>ENUM_INIT_VALUES_IN_ORDER=61</code> */
    /** Constant <code>ENUM_NEXT=62</code> */
    /** Constant <code>ENUM_ID=63</code> */
    /** Constant <code>THISFN=64</code> */
    /** Constant <code>RETURN_RESULT=65</code> */
    /** Constant <code>ARRAYLIT=66</code> */
    /** Constant <code>OBJECTLIT=67</code> */
    /** Constant <code>GET_REF=68</code> */
    /** Constant <code>SET_REF=69</code> */
    /** Constant <code>DEL_REF=70</code> */
    /** Constant <code>REF_CALL=71</code> */
    /** Constant <code>REF_SPECIAL=72</code> */
    /** Constant <code>YIELD=73</code> */
    /** Constant <code>STRICT_SETNAME=74</code> */
    /** Constant <code>EXP=75</code> */
    /** Constant <code>DEFAULTNAMESPACE=76</code> */
    /** Constant <code>ESCXMLATTR=77</code> */
    /** Constant <code>ESCXMLTEXT=78</code> */
    /** Constant <code>REF_MEMBER=79</code> */
    /** Constant <code>REF_NS_MEMBER=80</code> */
    /** Constant <code>REF_NAME=81</code> */
    /** Constant <code>REF_NS_NAME=82</code> */
    public final static int
    // start enum
        ERROR          = -1, // well-known as the only code < EOF
        EOF            = 0,  // end of file token - (not EOF_CHAR)
        EOL            = 1,  // end of line

        // Interpreter reuses the following as bytecodes
        FIRST_BYTECODE_TOKEN    = 2,

        ENTERWITH      = 2,
        LEAVEWITH      = 3,
        RETURN         = 4,
        GOTO           = 5,
        IFEQ           = 6,
        IFNE           = 7,
        SETNAME        = 8,
        BITOR          = 9,
        BITXOR         = 10,
        BITAND         = 11,
        EQ             = 12,
        NE             = 13,
        LT             = 14,
        LE             = 15,
        GT             = 16,
        GE             = 17,
        LSH            = 18,
        RSH            = 19,
        URSH           = 20,
        ADD            = 21,
        SUB            = 22,
        MUL            = 23,
        DIV            = 24,
        MOD            = 25,
        NOT            = 26,
        BITNOT         = 27,
        POS            = 28,
        NEG            = 29,
        NEW            = 30,
        DELPROP        = 31,
        TYPEOF         = 32,
        GETPROP        = 33,
        GETPROPNOWARN  = 34,
        SETPROP        = 35,
        GETELEM        = 36,
        SETELEM        = 37,
        CALL           = 38,
        NAME           = 39,
        NUMBER         = 40,
        STRING         = 41,
        NULL           = 42,
        THIS           = 43,
        FALSE          = 44,
        TRUE           = 45,
        SHEQ           = 46,   // shallow equality (===)
        SHNE           = 47,   // shallow inequality (!==)
        REGEXP         = 48,
        BINDNAME       = 49,
        THROW          = 50,
        RETHROW        = 51, // rethrow caught exception: catch (e if ) use it
        IN             = 52,
        INSTANCEOF     = 53,
        LOCAL_LOAD     = 54,
        GETVAR         = 55,
        SETVAR         = 56,
        CATCH_SCOPE    = 57,
        ENUM_INIT_KEYS = 58,
        ENUM_INIT_VALUES = 59,
        ENUM_INIT_ARRAY= 60,
        ENUM_INIT_VALUES_IN_ORDER = 61,
        ENUM_NEXT      = 62,
        ENUM_ID        = 63,
        THISFN         = 64,
        RETURN_RESULT  = 65, // to return previously stored return result
        ARRAYLIT       = 66, // array literal
        OBJECTLIT      = 67, // object literal
        GET_REF        = 68, // *reference
        SET_REF        = 69, // *reference    = something
        DEL_REF        = 70, // delete reference
        REF_CALL       = 71, // f(args)    = something or f(args)++
        REF_SPECIAL    = 72, // reference for special properties like __proto
        YIELD          = 73,  // JS 1.7 yield pseudo keyword
        STRICT_SETNAME = 74,
        EXP            = 75,  // Exponentiation Operator

        // For XML support:
        DEFAULTNAMESPACE = 76, // default xml namespace =
        ESCXMLATTR     = 77,
        ESCXMLTEXT     = 78,
        REF_MEMBER     = 79, // Reference for x.@y, x..y etc.
        REF_NS_MEMBER  = 80, // Reference for x.ns::y, x..ns::y etc.
        REF_NAME       = 81, // Reference for @y, @[y] etc.
        REF_NS_NAME    = 82; // Reference for ns::y, @ns::y@[y] etc.

        // End of interpreter bytecodes
    /** Constant <code>LAST_BYTECODE_TOKEN=REF_NS_NAME</code> */
    /** Constant <code>TRY=83</code> */
    /** Constant <code>SEMI=84</code> */
    /** Constant <code>LB=85</code> */
    /** Constant <code>RB=86</code> */
    /** Constant <code>LC=87</code> */
    /** Constant <code>RC=88</code> */
    /** Constant <code>LP=89</code> */
    /** Constant <code>RP=90</code> */
    /** Constant <code>COMMA=91</code> */
    /** Constant <code>ASSIGN=92</code> */
    /** Constant <code>ASSIGN_BITOR=93</code> */
    /** Constant <code>ASSIGN_BITXOR=94</code> */
    /** Constant <code>ASSIGN_BITAND=95</code> */
    /** Constant <code>ASSIGN_LSH=96</code> */
    /** Constant <code>ASSIGN_RSH=97</code> */
    /** Constant <code>ASSIGN_URSH=98</code> */
    /** Constant <code>ASSIGN_ADD=99</code> */
    /** Constant <code>ASSIGN_SUB=100</code> */
    /** Constant <code>ASSIGN_MUL=101</code> */
    /** Constant <code>ASSIGN_DIV=102</code> */
    /** Constant <code>ASSIGN_MOD=103</code> */
    /** Constant <code>ASSIGN_EXP=104</code> */
    public final static int
        LAST_BYTECODE_TOKEN    = REF_NS_NAME,

        TRY            = 83,
        SEMI           = 84,  // semicolon
        LB             = 85,  // left and right brackets
        RB             = 86,
        LC             = 87,  // left and right curlies (braces)
        RC             = 88,
        LP             = 89,  // left and right parentheses
        RP             = 90,
        COMMA          = 91,  // comma operator

        ASSIGN         = 92,  // simple assignment  (=)
        ASSIGN_BITOR   = 93,  // |=
        ASSIGN_BITXOR  = 94,  // ^=
        ASSIGN_BITAND  = 95,  // |=
        ASSIGN_LSH     = 96,  // <<=
        ASSIGN_RSH     = 97,  // >>=
        ASSIGN_URSH    = 98,  // >>>=
        ASSIGN_ADD     = 99,  // +=
        ASSIGN_SUB     = 100, // -=
        ASSIGN_MUL     = 101, // *=
        ASSIGN_DIV     = 102, // /=
        ASSIGN_MOD     = 103, // %=
        ASSIGN_EXP     = 104; // **=

    /** Constant <code>FIRST_ASSIGN=ASSIGN</code> */
    /** Constant <code>LAST_ASSIGN=ASSIGN_EXP</code> */
    /** Constant <code>HOOK=105</code> */
    /** Constant <code>COLON=106</code> */
    /** Constant <code>OR=107</code> */
    /** Constant <code>AND=108</code> */
    /** Constant <code>INC=109</code> */
    /** Constant <code>DEC=110</code> */
    /** Constant <code>DOT=111</code> */
    /** Constant <code>FUNCTION=112</code> */
    /** Constant <code>EXPORT=113</code> */
    /** Constant <code>IMPORT=114</code> */
    /** Constant <code>IF=115</code> */
    /** Constant <code>ELSE=116</code> */
    /** Constant <code>SWITCH=117</code> */
    /** Constant <code>CASE=118</code> */
    /** Constant <code>DEFAULT=119</code> */
    /** Constant <code>WHILE=120</code> */
    /** Constant <code>DO=121</code> */
    /** Constant <code>FOR=122</code> */
    /** Constant <code>BREAK=123</code> */
    /** Constant <code>CONTINUE=124</code> */
    /** Constant <code>VAR=125</code> */
    /** Constant <code>WITH=126</code> */
    /** Constant <code>CATCH=127</code> */
    /** Constant <code>FINALLY=128</code> */
    /** Constant <code>VOID=129</code> */
    /** Constant <code>RESERVED=130</code> */
    /** Constant <code>EMPTY=131</code> */
    /** Constant <code>BLOCK=132</code> */
    /** Constant <code>LABEL=133</code> */
    /** Constant <code>TARGET=134</code> */
    /** Constant <code>LOOP=135</code> */
    /** Constant <code>EXPR_VOID=136</code> */
    /** Constant <code>EXPR_RESULT=137</code> */
    /** Constant <code>JSR=138</code> */
    /** Constant <code>SCRIPT=139</code> */
    /** Constant <code>TYPEOFNAME=140</code> */
    /** Constant <code>USE_STACK=141</code> */
    /** Constant <code>SETPROP_OP=142</code> */
    /** Constant <code>SETELEM_OP=143</code> */
    /** Constant <code>LOCAL_BLOCK=144</code> */
    /** Constant <code>SET_REF_OP=145</code> */
    /** Constant <code>DOTDOT=146</code> */
    /** Constant <code>COLONCOLON=147</code> */
    /** Constant <code>XML=148</code> */
    /** Constant <code>DOTQUERY=149</code> */
    /** Constant <code>XMLATTR=150</code> */
    /** Constant <code>XMLEND=151</code> */
    /** Constant <code>TO_OBJECT=152</code> */
    /** Constant <code>TO_DOUBLE=153</code> */
    /** Constant <code>GET=154</code> */
    /** Constant <code>SET=155</code> */
    /** Constant <code>LET=156</code> */
    /** Constant <code>CONST=157</code> */
    /** Constant <code>SETCONST=158</code> */
    /** Constant <code>SETCONSTVAR=159</code> */
    /** Constant <code>ARRAYCOMP=160</code> */
    /** Constant <code>LETEXPR=161</code> */
    /** Constant <code>WITHEXPR=162</code> */
    /** Constant <code>DEBUGGER=163</code> */
    /** Constant <code>COMMENT=164</code> */
    /** Constant <code>GENEXPR=165</code> */
    /** Constant <code>METHOD=166</code> */
    /** Constant <code>ARROW=167</code> */
    /** Constant <code>YIELD_STAR=168</code> */
    /** Constant <code>LAST_TOKEN=169</code> */
    public final static int
        FIRST_ASSIGN   = ASSIGN,
        LAST_ASSIGN    = ASSIGN_EXP,

        HOOK           = 105, // conditional (?:)
        COLON          = 106,
        OR             = 107, // logical or (||)
        AND            = 108, // logical and (&&)
        INC            = 109, // increment/decrement (++ --)
        DEC            = 110,
        DOT            = 111, // member operator (.)
        FUNCTION       = 112, // function keyword
        EXPORT         = 113, // export keyword
        IMPORT         = 114, // import keyword
        IF             = 115, // if keyword
        ELSE           = 116, // else keyword
        SWITCH         = 117, // switch keyword
        CASE           = 118, // case keyword
        DEFAULT        = 119, // default keyword
        WHILE          = 120, // while keyword
        DO             = 121, // do keyword
        FOR            = 122, // for keyword
        BREAK          = 123, // break keyword
        CONTINUE       = 124, // continue keyword
        VAR            = 125, // var keyword
        WITH           = 126, // with keyword
        CATCH          = 127, // catch keyword
        FINALLY        = 128, // finally keyword
        VOID           = 129, // void keyword
        RESERVED       = 130, // reserved keywords

        EMPTY          = 131,

        /* types used for the parse tree - these never get returned
         * by the scanner.
         */

        BLOCK          = 132, // statement block
        LABEL          = 133, // label
        TARGET         = 134,
        LOOP           = 135,
        EXPR_VOID      = 136, // expression statement in functions
        EXPR_RESULT    = 137, // expression statement in scripts
        JSR            = 138,
        SCRIPT         = 139, // top-level node for entire script
        TYPEOFNAME     = 140, // for typeof(simple-name)
        USE_STACK      = 141,
        SETPROP_OP     = 142, // x.y op= something
        SETELEM_OP     = 143, // x[y] op= something
        LOCAL_BLOCK    = 144,
        SET_REF_OP     = 145, // *reference op= something

        // For XML support:
        DOTDOT         = 146,  // member operator (..)
        COLONCOLON     = 147,  // namespace::name
        XML            = 148,  // XML type
        DOTQUERY       = 149,  // .() -- e.g., x.emps.emp.(name == "terry")
        XMLATTR        = 150,  // @
        XMLEND         = 151,

        // Optimizer-only-tokens
        TO_OBJECT      = 152,
        TO_DOUBLE      = 153,

        GET            = 154,  // JS 1.5 get pseudo keyword
        SET            = 155,  // JS 1.5 set pseudo keyword
        LET            = 156,  // JS 1.7 let pseudo keyword
        CONST          = 157,
        SETCONST       = 158,
        SETCONSTVAR    = 159,
        ARRAYCOMP      = 160,  // array comprehension
        LETEXPR        = 161,
        WITHEXPR       = 162,
        DEBUGGER       = 163,
        COMMENT        = 164,
        GENEXPR        = 165,
        METHOD         = 166,  // ES6 MethodDefinition
        ARROW          = 167,  // ES6 ArrowFunction
        YIELD_STAR     = 168,  // ES6 "yield *", a specialization of yield
        LAST_TOKEN     = 169;
        

    /**
     * Returns a name for the token.  If Rhino is compiled with certain
     * hardcoded debugging flags in this file, it calls {@code #typeToName};
     * otherwise it returns a string whose value is the token number.
     *
     * @param token a int.
     * @return a {@link java.lang.String} object.
     */
    public static String name(int token)
    {
        if (!printNames) {
            return String.valueOf(token);
        }
        return typeToName(token);
    }

    /**
     * Always returns a human-readable string for the token name.
     * For instance, {@link #FINALLY} has the name "FINALLY".
     *
     * @param token the token code
     * @return the actual name for the token code
     */
    public static String typeToName(int token) {
        switch (token) {
          case ERROR:           return "ERROR";
          case EOF:             return "EOF";
          case EOL:             return "EOL";
          case ENTERWITH:       return "ENTERWITH";
          case LEAVEWITH:       return "LEAVEWITH";
          case RETURN:          return "RETURN";
          case GOTO:            return "GOTO";
          case IFEQ:            return "IFEQ";
          case IFNE:            return "IFNE";
          case SETNAME:         return "SETNAME";
          case BITOR:           return "BITOR";
          case BITXOR:          return "BITXOR";
          case BITAND:          return "BITAND";
          case EQ:              return "EQ";
          case NE:              return "NE";
          case LT:              return "LT";
          case LE:              return "LE";
          case GT:              return "GT";
          case GE:              return "GE";
          case LSH:             return "LSH";
          case RSH:             return "RSH";
          case URSH:            return "URSH";
          case ADD:             return "ADD";
          case SUB:             return "SUB";
          case MUL:             return "MUL";
          case DIV:             return "DIV";
          case MOD:             return "MOD";
          case NOT:             return "NOT";
          case BITNOT:          return "BITNOT";
          case POS:             return "POS";
          case NEG:             return "NEG";
          case NEW:             return "NEW";
          case DELPROP:         return "DELPROP";
          case TYPEOF:          return "TYPEOF";
          case GETPROP:         return "GETPROP";
          case GETPROPNOWARN:   return "GETPROPNOWARN";
          case SETPROP:         return "SETPROP";
          case GETELEM:         return "GETELEM";
          case SETELEM:         return "SETELEM";
          case CALL:            return "CALL";
          case NAME:            return "NAME";
          case NUMBER:          return "NUMBER";
          case STRING:          return "STRING";
          case NULL:            return "NULL";
          case THIS:            return "THIS";
          case FALSE:           return "FALSE";
          case TRUE:            return "TRUE";
          case SHEQ:            return "SHEQ";
          case SHNE:            return "SHNE";
          case REGEXP:          return "REGEXP";
          case BINDNAME:        return "BINDNAME";
          case THROW:           return "THROW";
          case RETHROW:         return "RETHROW";
          case IN:              return "IN";
          case INSTANCEOF:      return "INSTANCEOF";
          case LOCAL_LOAD:      return "LOCAL_LOAD";
          case GETVAR:          return "GETVAR";
          case SETVAR:          return "SETVAR";
          case CATCH_SCOPE:     return "CATCH_SCOPE";
          case ENUM_INIT_KEYS:  return "ENUM_INIT_KEYS";
          case ENUM_INIT_VALUES:return "ENUM_INIT_VALUES";
          case ENUM_INIT_ARRAY: return "ENUM_INIT_ARRAY";
          case ENUM_INIT_VALUES_IN_ORDER: return "ENUM_INIT_VALUES_IN_ORDER";
          case ENUM_NEXT:       return "ENUM_NEXT";
          case ENUM_ID:         return "ENUM_ID";
          case THISFN:          return "THISFN";
          case RETURN_RESULT:   return "RETURN_RESULT";
          case ARRAYLIT:        return "ARRAYLIT";
          case OBJECTLIT:       return "OBJECTLIT";
          case GET_REF:         return "GET_REF";
          case SET_REF:         return "SET_REF";
          case DEL_REF:         return "DEL_REF";
          case REF_CALL:        return "REF_CALL";
          case REF_SPECIAL:     return "REF_SPECIAL";
          case DEFAULTNAMESPACE:return "DEFAULTNAMESPACE";
          case ESCXMLTEXT:      return "ESCXMLTEXT";
          case ESCXMLATTR:      return "ESCXMLATTR";
          case REF_MEMBER:      return "REF_MEMBER";
          case REF_NS_MEMBER:   return "REF_NS_MEMBER";
          case REF_NAME:        return "REF_NAME";
          case REF_NS_NAME:     return "REF_NS_NAME";
          case TRY:             return "TRY";
          case SEMI:            return "SEMI";
          case LB:              return "LB";
          case RB:              return "RB";
          case LC:              return "LC";
          case RC:              return "RC";
          case LP:              return "LP";
          case RP:              return "RP";
          case COMMA:           return "COMMA";
          case ASSIGN:          return "ASSIGN";
          case ASSIGN_BITOR:    return "ASSIGN_BITOR";
          case ASSIGN_BITXOR:   return "ASSIGN_BITXOR";
          case ASSIGN_BITAND:   return "ASSIGN_BITAND";
          case ASSIGN_LSH:      return "ASSIGN_LSH";
          case ASSIGN_RSH:      return "ASSIGN_RSH";
          case ASSIGN_URSH:     return "ASSIGN_URSH";
          case ASSIGN_ADD:      return "ASSIGN_ADD";
          case ASSIGN_SUB:      return "ASSIGN_SUB";
          case ASSIGN_MUL:      return "ASSIGN_MUL";
          case ASSIGN_DIV:      return "ASSIGN_DIV";
          case ASSIGN_MOD:      return "ASSIGN_MOD";
          case ASSIGN_EXP:      return "ASSIGN_EXP";
          case HOOK:            return "HOOK";
          case COLON:           return "COLON";
          case OR:              return "OR";
          case AND:             return "AND";
          case INC:             return "INC";
          case DEC:             return "DEC";
          case DOT:             return "DOT";
          case FUNCTION:        return "FUNCTION";
          case EXPORT:          return "EXPORT";
          case IMPORT:          return "IMPORT";
          case IF:              return "IF";
          case ELSE:            return "ELSE";
          case SWITCH:          return "SWITCH";
          case CASE:            return "CASE";
          case DEFAULT:         return "DEFAULT";
          case WHILE:           return "WHILE";
          case DO:              return "DO";
          case FOR:             return "FOR";
          case BREAK:           return "BREAK";
          case CONTINUE:        return "CONTINUE";
          case VAR:             return "VAR";
          case WITH:            return "WITH";
          case CATCH:           return "CATCH";
          case FINALLY:         return "FINALLY";
          case VOID:            return "VOID";
          case RESERVED:        return "RESERVED";
          case EMPTY:           return "EMPTY";
          case BLOCK:           return "BLOCK";
          case LABEL:           return "LABEL";
          case TARGET:          return "TARGET";
          case LOOP:            return "LOOP";
          case EXPR_VOID:       return "EXPR_VOID";
          case EXPR_RESULT:     return "EXPR_RESULT";
          case JSR:             return "JSR";
          case SCRIPT:          return "SCRIPT";
          case TYPEOFNAME:      return "TYPEOFNAME";
          case USE_STACK:       return "USE_STACK";
          case SETPROP_OP:      return "SETPROP_OP";
          case SETELEM_OP:      return "SETELEM_OP";
          case LOCAL_BLOCK:     return "LOCAL_BLOCK";
          case SET_REF_OP:      return "SET_REF_OP";
          case DOTDOT:          return "DOTDOT";
          case COLONCOLON:      return "COLONCOLON";
          case XML:             return "XML";
          case DOTQUERY:        return "DOTQUERY";
          case XMLATTR:         return "XMLATTR";
          case XMLEND:          return "XMLEND";
          case TO_OBJECT:       return "TO_OBJECT";
          case TO_DOUBLE:       return "TO_DOUBLE";
          case GET:             return "GET";
          case SET:             return "SET";
          case LET:             return "LET";
          case YIELD:           return "YIELD";
          case EXP:             return "EXP";
          case CONST:           return "CONST";
          case SETCONST:        return "SETCONST";
          case ARRAYCOMP:       return "ARRAYCOMP";
          case WITHEXPR:        return "WITHEXPR";
          case LETEXPR:         return "LETEXPR";
          case DEBUGGER:        return "DEBUGGER";
          case COMMENT:         return "COMMENT";
          case GENEXPR:         return "GENEXPR";
          case METHOD:          return "METHOD";
          case ARROW:           return "ARROW";
          case YIELD_STAR:      return "YIELD_STAR";
        }

        // Token without name
        throw new IllegalStateException(String.valueOf(token));
    }

    /**
     * Convert a keyword token to a name string for use with the
     * {@link org.mozilla.javascript.Context#FEATURE_RESERVED_KEYWORD_AS_IDENTIFIER} feature.
     *
     * @param token A token
     * @return the corresponding name string
     */
    public static String keywordToName(int token) {
        switch (token) {
            case Token.BREAK:      return "break";
            case Token.CASE:       return "case";
            case Token.CONTINUE:   return "continue";
            case Token.DEFAULT:    return "default";
            case Token.DELPROP:    return "delete";
            case Token.DO:         return "do";
            case Token.ELSE:       return "else";
            case Token.FALSE:      return "false";
            case Token.FOR:        return "for";
            case Token.FUNCTION:   return "function";
            case Token.IF:         return "if";
            case Token.IN:         return "in";
            case Token.LET:        return "let";
            case Token.NEW:        return "new";
            case Token.NULL:       return "null";
            case Token.RETURN:     return "return";
            case Token.SWITCH:     return "switch";
            case Token.THIS:       return "this";
            case Token.TRUE:       return "true";
            case Token.TYPEOF:     return "typeof";
            case Token.VAR:        return "var";
            case Token.VOID:       return "void";
            case Token.WHILE:      return "while";
            case Token.WITH:       return "with";
            case Token.YIELD:      return "yield";
            case Token.CATCH:      return "catch";
            case Token.CONST:      return "const";
            case Token.DEBUGGER:   return "debugger";
            case Token.FINALLY:    return "finally";
            case Token.INSTANCEOF: return "instanceof";
            case Token.THROW:      return "throw";
            case Token.TRY:        return "try";
            default:               return null;
        }
    }

    /**
     * Return true if the passed code is a valid Token constant.
     *
     * @param code a potential token code
     * @return true if it's a known token
     */
    public static boolean isValidToken(int code) {
        return code >= ERROR
                && code <= LAST_TOKEN;
    }
}
