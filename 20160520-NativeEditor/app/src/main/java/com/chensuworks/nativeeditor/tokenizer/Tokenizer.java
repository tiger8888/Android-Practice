package com.chensuworks.nativeeditor.tokenizer;

import java.util.LinkedList;

public class Tokenizer {

    public static final int FUNCTION = 1;
    public static final int NESTED = 2;
    public static final int IF = 3;
    public static final int SWITCH = 4;
    public static final int TRY = 5;
    public static final int WHILE = 6;
    public static final int FOR = 7;
    public static final int END = 8;
    public static final int ELSE = 9;
    public static final int ELSEIF = 10;
    public static final int BREAK = 11;
    public static final int RETURN = 12;
    public static final int PARFOR = 13;
    public static final int GLOBAL = 15;
    public static final int PERSISTENT = 16;
    public static final int CATCH = 20;
    public static final int CONTINUE = 21;
    public static final int CASE = 22;
    public static final int OTHERWISE = 23;
    public static final int CLASSDEF = 25;
    public static final int PROPERTIES = 28;
    public static final int METHODS = 30;
    public static final int EVENTS = 31;
    public static final int ENUMERATION = 32;
    public static final int SPMD = 33;
    public static final int PARSECTION = 34;
    public static final int SECTION = 35;
    public static final int ID = 40;
    public static final int EEND = 41;
    public static final int INT = 42;
    public static final int FLOAT = 43;
    public static final int STRING = 44;
    public static final int DUAL = 45;
    public static final int BANG = 46;
    public static final int QUEST = 47;
    public static final int SEMI = 50;
    public static final int COMMA = 51;
    public static final int LP = 52;
    public static final int RP = 53;
    public static final int LB = 54;
    public static final int RB = 55;
    public static final int LC = 56;
    public static final int RC = 57;
    public static final int FEEND = 58;
    public static final int TRANS = 60;
    public static final int DOTTRANS = 61;
    public static final int NOT = 62;
    public static final int AT = 63;
    public static final int DOLLAR = 64;
    public static final int BACKQUOTE = 65;
    public static final int DOUBLEQUOTE = 66;
    public static final int SHARP = 67;
    public static final int PLUS = 70;
    public static final int MINUS = 71;
    public static final int MUL = 72;
    public static final int DIV = 73;
    public static final int LDIV = 74;
    public static final int EXP = 75;
    public static final int COLON = 76;
    public static final int DOT = 80;
    public static final int DOTMUL = 81;
    public static final int DOTDIV = 82;
    public static final int DOTLDIV = 83;
    public static final int DOTEXP = 84;
    public static final int AND = 85;
    public static final int OR = 86;
    public static final int ANDAND = 87;
    public static final int OROR = 88;
    public static final int LT = 89;
    public static final int GT = 90;
    public static final int LE = 91;
    public static final int GE = 92;
    public static final int EQ = 93;
    public static final int NE = 94;
    public static final int EQUALS = 95;
    public static final int CNE = 96;
    public static final int EOL = 100;
    public static final int SEOL = 101;
    public static final int CEOL = 102;
    public static final int IEOL = 103;
    public static final int COMMENT = 105;
    public static final int BLKSTART = 106;
    public static final int BLKCOM = 107;
    public static final int BLKEND = 108;
    public static final int CPAD = 109;
    public static final int PRAGMA = 110;
    public static final int DOTDOTDOT = 111;
    public static final int DOTDOT = 112;
    public static final int DEEP_NEST = 113;
    public static final int DEEP_STMT = 114;
    public static final int WHITE = 116;
    public static final int NEGERR = 118;
    public static final int SEMERR = 119;
    public static final int EOLERR = 120;
    public static final int UNTERM = 121;
    public static final int BADCHAR = 122;
    public static final int DEEP_PAREN = 123;
    public static final int FP_ERR = 124;
    public static final int RES_ERR = 125;
    public static final int DEEP_COM = 126;
    public static final int BEGIN_STMT = 0;
    public static final int DUAL_ARG = 1;
    public static final int SAW_INDXNM = 2;
    public static final int SAW_EXPR = 3;
    public static final int SAW_OPER = 4;
    public static final int SAW_AT = 5;
    public static final int SAW_EQUALS = 6;
    public static final int NORES = 7;
    public static final int LAST_STATE = 8;
    public static final int AT_BEGINNING = 0;
    public static final int IN_FILE = 1;
    public static final int IN_CLASS = 2;
    public static final int IN_METHOD = 3;

    public static final String[] toknames = {
        null, "FUNCTION", "NESTED", "IF", "SWITCH",
        "TRY", "WHILE", "FOR", "END", "ELSE",
        "ELSEIF", "BREAK", "RETURN", "PARFOR", null,
        "GLOBAL", "PERSISTENT", null, null, null,
        "CATCH", "CONTINUE", "CASE", "OTHERWISE", null,
        "CLASSDEF", null, null, "PROPERTIES", null,
        "METHODS", "EVENTS", "ENUMERATION", "SPMD", "PARSECTION",
        "SECTION", null, null, null, null,
        "ID", "EEND", "INT", "FLOAT", "STRING",
        "DUAL", "BANG", "QUEST", null, null,
        "SEMI", "COMMA", "LP", "RP", "LB",
        "RB", "LC", "RC", "FEEND", null,
        "TRANS", "DOTTRANS", "NOT", "AT", "DOLLAR",
        "BACKQUOTE", "DOUBLEQUOTE", null, null, null,
        "PLUS", "MINUS", "MUL", "DIV", "LDIV",
        "EXP", "COLON", null, null, null,
        "DOT", "DOTMUL", "DOTDIV", "DOTLDIV", "DOTEXP",
        "AND", "OR", "ANDAND", "OROR", "LT",
        "GT", "LE", "GE", "EQ", "NE",
        "EQUALS", "CNE", "ARROW", null, null,
        "EOL", "SEOL", "CEOL", "IEOL", null,
        "COMMENT", "BLKSTART", "BLKCOM", "BLKEND", "CPAD",
        "PRAGMA", "DOTDOTDOT", "DOTDOT", "DEEP_NEST", "DEEP_STMT",
        null, "WHITE", null, "NEGERR", "SEMERR",
        "EOLERR", "UNTERM", "BADCHAR", "DEEP_PAREN", "FP_ERR",
        "RES_ERR", "DEEP_COM", "LAST_TOKEN"
    };

    private int off = 0;
    private int eoff = 0;
    private int first = 0;
    private String ln;
    private String lln;
    private int bpt = 0;
    private int all_nl = 0;
    private int ld_sv = 0;
    private int new_ends = 0;
    private int[] tbug = {0, 0, 0, 0, 0};
    private int nextix = 0;
    private int ntok = 0;
    private LexState ls = make_lex_state();

    private boolean add_nl;

    private int F_LS = 1;
    private int V_LS = 2;

    private static final int NOTHING_SEEN = 0;
    private static final int STMT_BEGINNING = 1;
    private static final int BREAKPOINT = 2;
    private static final int NO_BREAKPOINT = 3;

    private static final int MAXPAR = 32;
    private static final int MAXINFUN = 7;
    private static final int MAXCOMLEV = 7;
    private static final int C_ERR = 0;
    private static final int C_LET = 1;
    private static final int C_DIG = 2;
    private static final int C_UND = 3;
    private static final int C_LP = 4;
    private static final int C_RP = 5;
    private static final int C_LC = 6;
    private static final int C_RC = 7;
    private static final int C_LB = 8;
    private static final int C_RB = 9;
    private static final int C_AT = 10;
    private static final int C_PLUS = 11;
    private static final int C_MINUS = 12;
    private static final int C_MUL = 13;
    private static final int C_DIV = 14;
    private static final int C_BSLASH = 15;
    private static final int C_EXP = 16;
    private static final int C_COLON = 17;
    private static final int C_DOT = 18;
    private static final int C_AND = 19;
    private static final int C_OR = 20;
    private static final int C_LT = 21;
    private static final int C_GT = 22;
    private static final int C_NOT = 23;
    private static final int C_SQ = 24;
    private static final int C_SEMI = 25;
    private static final int C_COMMA = 26;
    private static final int C_EOL = 27;
    private static final int C_EQUALS = 28;
    private static final int C_PCT = 29;
    private static final int C_SHARP = 30;
    private static final int C_WHITE = 31;
    private static final int C_BANG = 32;
    private static final int C_QUEST = 33;
    private static final int C_DOLLAR = 34;
    private static final int C_BACKQUOTE = 35;
    private static final int C_DOUBLEQUOTE = 36;
    private static final int C_EOI = 37;

    private static final int[] cdope = {
            C_PCT,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_WHITE,
            C_EOL,
            C_EOL,
            C_EOL,
            C_EOL,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_EOI,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_ERR,
            C_WHITE,
            C_BANG,
            C_DOUBLEQUOTE,
            C_SHARP,
            C_DOLLAR,
            C_PCT,
            C_AND,
            C_SQ,
            C_LP,
            C_RP,
            C_MUL,
            C_PLUS,
            C_COMMA,
            C_MINUS,
            C_DOT,
            C_DIV,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_DIG,
            C_COLON,
            C_SEMI,
            C_LT,
            C_EQUALS,
            C_GT,
            C_QUEST,
            C_AT,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LB,
            C_BSLASH,
            C_RB,
            C_EXP,
            C_UND,
            C_BACKQUOTE,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LET,
            C_LC,
            C_OR,
            C_RC,
            C_NOT,
            C_ERR,

            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR,
            C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR, C_ERR
    };

    public LexState make_lex_state() {
        return new LexState(AT_BEGINNING, BEGIN_STMT, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
    }

    public void copy_lex_state(LexState dest, LexState src) {
        dest.set(src.getCstate(), src.getLstate(), src.getIndent(), src.getInfun(), src.getNpars(),
                src.getNcoms(), src.getLdsv(), src.getAtlp(), src.getContin(), src.getElist(),
                src.getHaveends(), src.getBegin(), src.getSpare(), src.getStack());
    }

    public void initialize_state(LexState p) {
        p.setLstate(BEGIN_STMT);
        p.setCstate(AT_BEGINNING);
        p.setAtlp(0);
        p.setContin(0);
        p.setElist(0);
        p.setHaveends(0);
        p.setIndent(0);
        p.setInfun(0);
        p.setNpars(0);
        p.setNcoms(0);
        p.setStack(0);
        p.setBegin(1);
        p.setLdsv(0);
        p.setSpare(0);
    }

    public void initialize(String st, int n, boolean add_newline) {
        ls = make_lex_state();
        initialize_state(ls);
        ln = st;
        lln = ln;
        off = 0;
        eoff = n;
        nextix = 0;
        ntok = 0;
        add_nl = add_newline;
        bpt = NOTHING_SEEN;
    }

    // TODO: return LexResults.
    // TODO: refer to src/js/MW/rtc/plugins/language/matlab/tokenizer/Tokenizer.js
    // TODO: refer to src/js/MW/rtc/plugins/language/matlab/tokenizer/TokenState.js
    // TODO: refer to test/ui/webwidgets/rtc/unit_tests/plugins/tokenizer/TokenStateUnitTest.js
    public LexResults lex_line(LinkedList<Integer> t, int ntmax, LexState lex_state, String line, int length) {

        // driver routine, calls get_next_token to do the real work
        bpt = NOTHING_SEEN;
        copy_lex_state(ls, lex_state);
        ln = line;
        off = 0;
        eoff = length;
        first = 0;
        add_nl = true;

        int j, k, nt;

        // if the input is an empty string, just generate a newline and
        // return (after updating the state)
        lln = ln;
        if (length == 0) {
            nt = eol_token(t, 0);
            for (k = 0; k < nt; ++k) {
                fix_state(lln, t.get(k));
            }
            return new LexResults(nt, ls, bpt == BREAKPOINT);
        }

        // otherwise, get the tokens, one by one
        // and add newline at end if needed
        for (j = 0; j < ntmax - 3; j += nt) {
            nt = get_next_token(t, j);
            if (nt == 0) {
                break;
            }
            for (k = 0; k < nt; ++k) {
                fix_state(lln, t.get(j + k));
                if (((t.get(j + k) & 0x7F)) == FEEND) {
                    if ((j + k) >= t.size()) {
                        t.add((((((t.get(j + k)) >> 8)) << 8) | ((new_ends > 0 ? EEND : END))));
                    } else {
                        t.set((j + k), (((((t.get(j + k)) >> 8)) << 8) | ((new_ends > 0 ? EEND : END))));
                    }
                }
            }
        }

        if ((j >= ntmax - 3) && ls.getBegin() == 0) {
            return new LexResults(-1, lex_state, bpt == BREAKPOINT);
        }

        return new LexResults(j, ls, bpt == BREAKPOINT);
    }

    public int eol_token(LinkedList<Integer> t, int N) {
        int kind = (off == first) ? IEOL : EOL;
        if (ls.getNpars() != 0) {
            if ((ls.getStack() & 1) != 0) {
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (SEOL)));
                } else {
                    t.set(N, (((off - first) << 8) | (SEOL)));
                }
                return 1;
            }
            if (N + 0 >= t.size()) {
                t.add((((0) << 8) | (EOLERR)));
            } else {
                t.set(N + 0, (((0) << 8) | (EOLERR)));
            }
            if (N + 1 >= t.size()) {
                t.add((((off - first) << 8) | (kind)));
            } else {
                t.set(N + 1, (((off - first) << 8) | (kind)));
            }
            return 2;
        }
        if (N >= t.size()) {
            t.add((((off - first) << 8) | (kind)));
        } else {
            t.set(N, (((off - first) << 8) | (kind)));
        }
        return 1;
    }

    public void fix_state(String lineOfCode, int token) {
        if (ls.getLstate() == BEGIN_STMT && bpt == NOTHING_SEEN) {
            bpt = STMT_BEGINNING;
        }
        get_next_state(lineOfCode, token);
        switch (((LEX_KIND(token)))) {
            case COMMENT:
            case BLKSTART:
            case BLKCOM:
            case BLKEND:
            case WHITE:
            case DOTDOTDOT:
            case CPAD:
            case PRAGMA:
            case EOL:
            case SEOL:
            case IEOL:
            case CEOL:
                break;
            case FUNCTION:
            case NESTED:
            case CLASSDEF:
            case UNTERM:
            case DEEP_PAREN:
            case DEEP_COM:
            case DEEP_NEST:
            case DEEP_STMT:
            case FP_ERR:
            case RES_ERR:
            case SEMERR:
            case EOLERR:
            case NEGERR:
            case SHARP:
            case DOLLAR:
            case BACKQUOTE:
            case DOUBLEQUOTE:
                bpt = NO_BREAKPOINT;
                break;
            default:
                if (bpt == STMT_BEGINNING) {
                    bpt = BREAKPOINT;
                }
        }
    }

    public int LEX_KIND(int token) {
        return ((token) & 0x7F);
    }

    public int get_next_token(LinkedList<Integer> tokenArray, int N) {
        int kind;
        int i;

        if (off >= eoff) {

            if (add_nl && ls.begin == 0) {
                first = off = eoff;
                return( eol_token(tokenArray, N) );
            }
            return( 0 );
        }

        first = off;

        if (ls.begin != 0) {
            i = process_comments(tokenArray, N);
            if (i != 0) {
                return( i );
            }
        }

        if (ls.lstate == DUAL_ARG) {
            return( dual_arg_token(tokenArray, N) );
        }


        if (ls.begin == 1 && (ls.stack & 1) != 0 && ls.contin != 0 &&
                (ls.lstate == SAW_EXPR || ls.lstate == SAW_INDXNM ) &&
                generate_comma() != 0) {
            if (off > first) {
                if (N + 0 >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (WHITE)));
                } else {
                    tokenArray.set(N + 0, (((off - first) << 8) | (WHITE)));
                }
                first = off;
                if (N + 1 >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (COMMA)));
                } else {
                    tokenArray.set(N + 1, (((off - first) << 8) | (COMMA)));
                }
                return(2);
            }
            if (N > tokenArray.size()) {
                tokenArray.add((((off - first) << 8) | (COMMA)));
            } else {
                tokenArray.set(N, (((off - first) << 8) | (COMMA)));
            }
            return(1);
        }

        switch (CLINE(off)) {
            case C_EOI:
                off = eoff;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (WHITE)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (WHITE)));
                }
                return(1);
            case C_WHITE:
                for (++off; CLINE(off) == C_WHITE; ++off) {
                }
                if ((ls.stack & 1) != 0 && (ls.lstate == SAW_EXPR || ls.lstate == SAW_INDXNM ) &&
                        generate_comma() != 0) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (WHITE)));
                    } else {
                        tokenArray.set(N + 0, (((off - first) << 8) | (WHITE)));
                    }
                    first = off;
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (COMMA)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (COMMA)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (WHITE)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (WHITE)));
                }
                return(1);

            case C_EOL:
                past_eol();
                return( eol_token(tokenArray, N) );

            case C_LET:
                for (i = off + 1; i < eoff; ++i) {
                    if (CLINE(i) != C_LET && CLINE(i) != C_DIG && CLINE(i) != C_UND) {
                        break;
                    }
                }
                off = i;
                if (ls.lstate == NORES) {
                    kind = ID;
                }
                else {
                    kind = reserved_word(ln, first, off - first, (ls.cstate) == IN_CLASS);
                }

                if (kind == ID || (kind == END && (ls.npars != 0 || ls.lstate == SAW_EQUALS))) {
                    if (kind == END) {
                        kind = ls.npars != 0 ? EEND : FEEND;
                    }

                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (kind)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (kind)));
                    }
                    return(1);
                }

                switch (kind) {
                    case FUNCTION:
                        if ((ls.infun != 0 && (ls.haveends != 0 || ls.begin == 0) ) ||
                                (ls.cstate == IN_CLASS || ls.cstate == IN_METHOD )) {
                            kind = NESTED;
                        }
                        break;
                    case TRY:
                    case METHODS:
                    case PROPERTIES:
                    case EVENTS:
                    case ENUMERATION:
                    case IF:
                    case WHILE:
                    case FOR:
                    case PARFOR:
                    case SWITCH:

                    case SPMD:
                    case PARSECTION:
                    case SECTION:
                        break;
                    default:
                        break;
                }
                if (ls.npars != 0) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (RES_ERR)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (RES_ERR)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (kind)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (kind)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (kind)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (kind)));
                }
                return(1);

            case C_DIG:
                for (++off; CLINE(off) == C_DIG; ++off) {
                }
                if (CLINE(off) == C_DOT) {
                    switch (CLINE(off + 1)) {
                        case C_MUL:
                        case C_DIV:
                        case C_BSLASH:
                        case C_EXP:
                        case C_SQ:
                            break;
                        default:
                            ++off;
                            kind = get_d_e();
                            if (N >= tokenArray.size()) {
                                tokenArray.add((((off - first) << 8) | (kind)));
                            } else {
                                tokenArray.set(N, (((off - first) << 8) | (kind)));
                            }
                            return(1);
                    }
                }
                if (CLINE(off) == C_LET) {
                    kind = get_d_e();
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (kind)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (kind)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (INT)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (INT)));
                }
                return(1);

            case C_DOT:
                if (CLINE(off + 1) == C_DOT && CLINE(off + 2) == C_DOT) {
                    return( do_dots(tokenArray, N) );
                }
                ++off;
                switch (CLINE(off)) {
                    case C_DIG:
                        kind = get_d_e();
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (kind)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (kind)));
                        }
                        return(1);
                    case C_MUL:
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOTMUL)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOTMUL)));
                        }
                        return(1);
                    case C_DIV:
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOTDIV)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOTDIV)));
                        }
                        return(1);
                    case C_BSLASH:
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOTLDIV)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOTLDIV)));
                        }
                        return(1);
                    case C_EXP:
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOTEXP)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOTEXP)));
                        }
                        return(1);
                    case C_SQ:
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOTTRANS)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOTTRANS)));
                        }
                        return(1);
                    case C_DOT:
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOTDOT)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOTDOT)));
                        }
                        return(1);
                    default:
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (DOT)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (DOT)));
                        }
                        return(1);
                }

            case C_PCT:

                if (ln.charAt(first) == '0'  && first >= eoff - 1) {

                    eoff = first;
                    return( 0 );
                }
                find_eol();
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (classify_comment())));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (classify_comment())));
                }
                first = off;
                past_eol();
                return(1 + eol_token(tokenArray, N + 1));


            case C_NOT:
                ++off;
                if (CLINE(off) == C_EQUALS) {
                    ++off;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (NE)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (NE)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (NOT)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (NOT)));
                }
                return(1);

            case C_EQUALS:
                ++off;
                if (CLINE(off) == C_EQUALS) {
                    ++off;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (EQ)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (EQ)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (EQUALS)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (EQUALS)));
                }
                return(1);

            case C_LT:
                ++off;
                if (CLINE(off) == C_EQUALS) {
                    ++off;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (LE)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (LE)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (LT)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (LT)));
                }
                return(1);

            case C_GT:
                ++off;
                if (CLINE(off) == C_EQUALS) {
                    ++off;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (GE)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (GE)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (GT)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (GT)));
                }
                return(1);

            case C_AND:
                ++off;
                if (CLINE(off) == C_AND) {
                    ++off;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (ANDAND)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (ANDAND)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (AND)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (AND)));
                }
                return(1);

            case C_OR:
                ++off;
                if (CLINE(off) == C_OR) {
                    ++off;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (OROR)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (OROR)));
                    }
                    return(1);
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (OR)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (OR)));
                }
                return(1);

            case C_COMMA:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (COMMA)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (COMMA)));
                }
                return(1);
            case C_SEMI:
                ++off;
                if (ls.npars != 0 && (ls.stack & 1) == 0) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (SEMERR)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (SEMERR)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (SEMI)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (SEMI)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (SEMI)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (SEMI)));
                }
                return(1);
            case C_COLON:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (COLON)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (COLON)));
                }
                return(1);
            case C_LP:
                ++off;
                if (ls.npars == MAXPAR) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (DEEP_PAREN)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (DEEP_PAREN)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (LP)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (LP)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (LP)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (LP)));
                }
                return(1);
            case C_LC:
                ++off;
                if (ls.npars == MAXPAR) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (DEEP_PAREN)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (DEEP_PAREN)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (LC)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (LC)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (LC)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (LC)));
                }
                return(1);
            case C_LB:
                ++off;
                if (ls.npars == MAXPAR) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (DEEP_PAREN)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (DEEP_PAREN)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (LB)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (LB)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (LB)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (LB)));
                }
                return(1);
            case C_RP:
                ++off;
                if (ls.npars == 0) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (NEGERR)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (NEGERR)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (RP)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (RP)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (RP)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (RP)));
                }
                return(1);
            case C_RB:
                ++off;
                if (ls.npars == 0) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (NEGERR)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (NEGERR)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (RB)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (RB)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (RB)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (RB)));
                }
                return(1);
            case C_RC:
                ++off;
                if (ls.npars == 0) {
                    if (N + 0 >= tokenArray.size()) {
                        tokenArray.add((((0) << 8) | (NEGERR)));
                    } else {
                        tokenArray.set(N + 0, (((0) << 8) | (NEGERR)));
                    }
                    if (N + 1 >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (RC)));
                    } else {
                        tokenArray.set(N + 1, (((off - first) << 8) | (RC)));
                    }
                    return( 2 );
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (RC)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (RC)));
                }
                return(1);
            case C_QUEST:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (QUEST)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (QUEST)));
                }
                return(1);
            case C_SHARP:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (SHARP)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (SHARP)));
                }
                return(1);
            case C_DOLLAR:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (DOLLAR)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (DOLLAR)));
                }
                return(1);
            case C_BACKQUOTE:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (BACKQUOTE)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (BACKQUOTE)));
                }
                return(1);
            case C_DOUBLEQUOTE:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (DOUBLEQUOTE)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (DOUBLEQUOTE)));
                }
                return(1);
            case C_AT:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (AT)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (AT)));
                }
                return(1);
            case C_PLUS:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (PLUS)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (PLUS)));
                }
                return(1);
            case C_MINUS:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (MINUS)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (MINUS)));
                }
                return(1);
            case C_MUL:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (MUL)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (MUL)));
                }
                return(1);
            case C_DIV:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (DIV)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (DIV)));
                }
                return(1);
            case C_BSLASH:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (LDIV)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (LDIV)));
                }
                return(1);
            case C_EXP:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (EXP)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (EXP)));
                }
                return(1);

            case C_SQ:
                if (off > 0 && ls.lstate != SAW_OPER && ls.lstate != SAW_EQUALS) {

                    switch (CLINE(off - 1)) {
                        case C_RP:
                            if (ls.atlp != 0) {
                                break;
                            }

                        case C_LET:
                        case C_DIG:
                        case C_UND:
                        case C_DOT:
                        case C_RC:
                        case C_RB:
                        case C_SQ:
                            ++off;
                            if (N >= tokenArray.size()) {
                                tokenArray.add((((off - first) << 8) | (TRANS)));
                            } else {
                                tokenArray.set(N, (((off - first) << 8) | (TRANS)));
                            }
                            return(1);

                        default:
                            break;
                    }
                }

                for (++off; CLINE(off) != C_EOL; ++off) {
                    if (CLINE(off) == C_SQ) {
                        if (CLINE(off + 1) == C_SQ) {
                            ++off;
                            continue;
                        }
                        ++off;
                        if (N >= tokenArray.size()) {
                            tokenArray.add((((off - first) << 8) | (STRING)));
                        } else {
                            tokenArray.set(N, (((off - first) << 8) | (STRING)));
                        }
                        return(1);
                    }
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (UNTERM)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (UNTERM)));
                }
                return(1);

            case C_BANG:
                if (ls.lstate != BEGIN_STMT && CLINE(off + 1) == C_EQUALS) {

                    off += 2;
                    if (N >= tokenArray.size()) {
                        tokenArray.add((((off - first) << 8) | (CNE)));
                    } else {
                        tokenArray.set(N, (((off - first) << 8) | (CNE)));
                    }
                    return(1);
                }

                find_eol();
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (BANG)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (BANG)));
                }
                first = off;
                past_eol();
                return( 1 + eol_token(tokenArray, N + 1) );

            case C_UND:
            case C_ERR:

                ++off;
                for (; ;) {
                    switch (CLINE(off)) {
                        case C_UND:
                        case C_ERR:
                            ++off;
                            continue;
                        default:
                            break;
                    }
                    break;
                }
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (BADCHAR)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (BADCHAR)));
                }
                return(1);

            default:
                ++off;
                if (N >= tokenArray.size()) {
                    tokenArray.add((((off - first) << 8) | (BADCHAR)));
                } else {
                    tokenArray.set(N, (((off - first) << 8) | (BADCHAR)));
                }
                return(1);
        }


    }

    public int process_comments(LinkedList<Integer> t, int N) {
        int i = off;
        first = off;
        if (off >= eoff) {
            return(0);
        }
        while (CLINE(i) == C_WHITE) {
            ++i;
        }
        if (ls.ncoms != 0) {
            int kind = BLKCOM;
            if (CLINE(i) == C_PCT &&
                    (CLINE(i + 1) == C_LC || CLINE(i + 1) == C_RC) &&
                    check_whitespace(i + 2) != 0) {


                if (CLINE(i + 1) == C_LC) {
                    if (ls.ncoms >= MAXCOMLEV) {
                        kind = DEEP_COM;
                    }
                    else {
                        kind = BLKSTART;
                    }
                }
                else {
                    kind = BLKEND;
                }
            }
            past_eol();
            if (N >= t.size()) {
                t.add((((off - first) << 8) | (kind)));
            } else {
                t.set(N, (((off - first) << 8) | (kind)));
            }
            return( 1 );
        }
        if (CLINE(i) == C_EOL) {
            return( 0 );
        }
        if (CLINE(i) == C_PCT) {
            if (CLINE(i + 1) == C_PCT && (CLINE(i + 2) == C_EOL || CLINE(i + 2) == C_WHITE)) {
                find_eol();
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (CPAD)));
                } else {
                    t.set(N, (((off - first) << 8) | (CPAD)));
                }
                return( 1 );
            }
            if (CLINE(i + 1) == C_LC && check_whitespace(i + 2) != 0) {
                past_eol();
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (BLKSTART)));
                } else {
                    t.set(N, (((off - first) << 8) | (BLKSTART)));
                }
                return( 1 );
            }
            return( 0 );
        }
        return( 0 );
    }

    public int is_contin(LexState s) {
        return s.contin;
    }

    public int check_whitespace(int i) {
        for (; i < eoff; ++i) {
            if (CLINE(i) == C_EOL) {
                if (CLINE(i + 1) == C_EOL && ln.charAt(i) == '\r') {
                    off = i + 1;
                }
                else {
                    off = i;
                }
                return( 1 );
            }
            if (CLINE(i) != C_WHITE) {
                return( 0 );
            }
        }
        off = eoff;
        return( 1 );
    }

    public void past_eol() {
        for (; CLINE(off) != C_EOL; ++off) {
        }
        if ((off + 1) < eoff) {
            char c0 = ln.charAt(off);
            char c1 = ln.charAt(off + 1);
            if ((c0 == '\n' && c1 == '\r') || (c0 == '\r' && c1 == '\n')) {
                ++off;
            }
        }
        if (off < eoff) {
            ++off;
        }
    }

    public void find_eol() {
        for (; CLINE(off) != C_EOL; ++off) {
        }
    }

    public int classify_comment() {
        if (CLINE(first + 1) == C_SHARP) {
            int i;
            for (i = 2; CLINE(first + i) == C_WHITE; ++i) {
            }
            if (CLINE(first + i) == C_LET) {
                return( PRAGMA );
            }
            return( COMMENT );
        }
        return( COMMENT );
    }

    public int do_dots(LinkedList<Integer> t, int N) {
        int n = 0;
        first = off;
        off += 3;

        n++;
        if (N + n >= t.size()) {
            t.add((((off - first) << 8) | (DOTDOTDOT)));
        } else {
            t.set(N + n, (((off - first) << 8) | (DOTDOTDOT)));
        }

        first = off;
        find_eol();
        if (first != off) {
            n++;
            if (N + n >= t.size()) {
                t.add((((off - first) << 8) | (COMMENT)));
            } else {
                t.set(N + n, (((off - first) << 8) | (COMMENT)));
            }
            first = off;
        }
        past_eol();

        n++;
        if (N + n >= t.size()) {
            t.add((((off - first) << 8) | (CEOL)));
        } else {
            t.set(N + n, (((off - first) << 8) | (CEOL)));
        }
        return( n );
    }

    public int get_d_e() {
        for (; off < eoff; ++off) {
            if (CLINE(off) != C_DIG) {
                break;
            }
        }
        if (off < eoff && CLINE(off) == C_LET) {
            switch (ln.charAt(off)) {
                case 'd':
                case 'D':
                case 'e':
                case 'E':

                    ++off;
                    if (off >= eoff) {
                        return( FP_ERR );
                    }
                    if (CLINE(off) == C_PLUS || CLINE(off) == C_MINUS) {
                        ++off;
                        if (off >= eoff) {
                            return( FP_ERR );
                        }
                    }
                    if (CLINE(off) != C_DIG) {
                        return( FP_ERR );
                    }
                    while (off < eoff && CLINE(off) == C_DIG) {
                        ++off;
                    }
                    break;
                default:
                    break;
            }
        }
        if (off < eoff && CLINE(off) == C_LET &&
                (ln.charAt(off) == 'i' || ln.charAt(off) == 'j')) {
            ++off;
        }
        return( FLOAT );
    }

    public int reserved_word(String s, int N, int len, boolean cres) {
        s = s.substring(N);
        switch (len) {
            case 2:
                if (steqn(s, "if", 2) != 0) {
                    return( IF );
                }
                break;
            case 3:
                if (steqn(s, "for", 3) != 0) {
                    return( FOR );
                }
                if (steqn(s, "end", 3) != 0) {
                    return( END );
                }
                if (steqn(s, "try", 3) != 0) {
                    return( TRY );
                }
                break;
            case 4:
                if (steqn(s, "case", 4) != 0) {
                    return( CASE );
                }
                if (steqn(s, "else", 4) != 0) {
                    return( ELSE );
                }
                if (steqn(s, "spmd", 4) != 0) {
                    return( SPMD );
                }
                break;
            case 5:
                if (steqn(s, "catch", 5) != 0) {
                    return( CATCH );
                }
                if (steqn(s, "while", 5) != 0) {
                    return( WHILE );
                }
                if (steqn(s, "break", 5) != 0) {
                    return( BREAK );
                }
                break;
            case 6:
                if (steqn(s, "elseif", 6) != 0) {
                    return( ELSEIF );
                }
                if (steqn(s, "switch", 6) != 0) {
                    return( SWITCH );
                }
                if (steqn(s, "global", 6) != 0) {
                    return( GLOBAL );
                }
                if (steqn(s, "return", 6) != 0) {
                    return( RETURN );
                }
                if (cres && steqn(s, "events", 6) != 0) {
                    return( EVENTS );
                }
                if (steqn(s, "parfor", 6) != 0) {
                    return( PARFOR );
                }
                break;
            case 7:
                if (cres && steqn(s, "methods", 7) != 0) {
                    return( METHODS );
                }
                break;
            case 8:
                if (steqn(s, "function", 8) != 0) {
                    return( FUNCTION );
                }
                if (steqn(s, "continue", 8) != 0) {
                    return( CONTINUE );
                }
                if (steqn(s, "classdef", 8) != 0) {
                    return( CLASSDEF );
                }
                break;
            case 9:
                if (steqn(s, "otherwise", 9) != 0) {
                    return( OTHERWISE );
                }
                break;
            case 10:
                if (steqn(s, "persistent", 10) != 0) {
                    return( PERSISTENT );
                }
                if (cres && steqn(s, "properties", 10) != 0) {
                    return( PROPERTIES );
                }
                break;
            case 11:
                if (cres && steqn(s, "enumeration", 11) != 0) {
                    return( ENUMERATION );
                }
                break;
            default:
                break;
        }
        return( ID );
    }

    public int generate_comma() {
        if (off >= eoff) {
            return( 0 );
        }
        switch (CLINE(off)) {
            case C_PLUS:
            case C_MINUS:

                if (CLINE(off + 1) == C_WHITE) {
                    return( 0 );
                }
                return( 1 );

            case C_NOT:
                if (CLINE(off + 1) == C_EQUALS) {
                    return( 0 );
                }
                return( 1 );

            case C_DOT:
                if (CLINE(off + 1) == C_DIG) {
                    return( 1 );
                }
                return( 0 );

            case C_LET:
            case C_DIG:
            case C_UND:
            case C_LP:
            case C_LB:
            case C_LC:
            case C_SQ:
            case C_AT:
            case C_QUEST:
                return( 1 );

            default:
                return( 0 );
        }
    }

    public int dual_arg_token(LinkedList<Integer> t, int N) {
        int level;
        int inquotes;
        int nt;
        nt = dual_special_cases(t, N);
        if (nt != 0) {
            return( nt );
        }
        level = 0;
        inquotes = 0;
        for (; off < eoff; ++off) {
            switch (CLINE(off)) {
                case C_SQ:
                    if (level != 0) {
                        continue;
                    }
                    if (inquotes == 1) {
                        if (off + 1 >= eoff || CLINE(off + 1) != C_SQ) {

                            inquotes = 0;
                        }
                        else {
                            ++off;
                            continue;
                        }
                    }
                    else {
                        inquotes = 1;
                    }
                    continue;

                case C_LP:
                case C_LB:
                case C_LC:
                    if (inquotes == 0) {
                        ++level;
                    }
                    continue;

                case C_RP:
                case C_RB:
                case C_RC:
                    if (inquotes == 0) {
                        --level;
                    }
                    continue;

                case C_WHITE:
                    if (inquotes != 0 || level != 0) {
                        continue;
                    }
                    break;

                case C_SEMI:
                    if (inquotes != 0) {
                        continue;
                    }
                    break;

                case C_EOL:
                    break;

                case C_PCT:
                    if (inquotes != 0) {
                        continue;
                    }

                    break;

                case C_DOT:
                    if (inquotes != 0) {
                        continue;
                    }

                    if (CLINE(off + 1) == C_DOT && CLINE(off + 2) == C_DOT) {
                        break;
                    }
                    continue;

                case C_COMMA:
                    if (inquotes != 0 || level != 0) {
                        continue;
                    }
                    break;

                default:
                    continue;
            }
            break;
        }

        if (inquotes != 0 && level == 0) {
            if (N >= t.size()) {
                t.add((((off - first) << 8) | (UNTERM)));
            } else {
                t.set(N, (((off - first) << 8) | (UNTERM)));
            }
        }
        else {

            if (ls.ldsv == V_LS && CLINE(first) == C_LET) {
                int i;
                for (i = first + 1; i < off; ++i) {
                    if (CLINE(i) != C_LET && CLINE(i) != C_DIG && CLINE(i) != C_UND) {
                        break;
                    }
                }
                if (i == off) {
                    if (N >= t.size()) {
                        t.add((((off - first) << 8) | (ID)));
                    } else {
                        t.set(N, (((off - first) << 8) | (ID)));
                    }
                    return(1);
                }
            }
            if (N >= t.size()) {
                t.add((((off - first) << 8) | (DUAL)));
            } else {
                t.set(N, (((off - first) << 8) | (DUAL)));
            }
        }
        return( 1 );
    }

    public int dual_special_cases(LinkedList<Integer> t, int N) {
        switch (CLINE(off)) {
            case C_WHITE:
                for (++off; CLINE(off) == C_WHITE; ++off) {
                }
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (WHITE)));
                } else {
                    t.set(N, (((off - first) << 8) | (WHITE)));
                }
                return( 1 );
            case C_PCT:
                find_eol();
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (classify_comment())));
                } else {
                    t.set(N, (((off - first) << 8) | (classify_comment())));
                }
                first = off;
                past_eol();
                return( eol_token(t, N + 1) + 1 );
            case C_DOT:
                if (CLINE(off + 1) == C_DOT && CLINE(off + 2) == C_DOT) {
                    return( do_dots(t, N) );
                }
                return( 0 );
            case C_EOL:
                past_eol();
                return( eol_token(t, N) );
            case C_SEMI:
                ++off;
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (SEMI)));
                } else {
                    t.set(N, (((off - first) << 8) | (SEMI)));
                }
                ls.lstate = BEGIN_STMT;
                return( 1 );
            case C_COMMA:
                ++off;
                if (N >= t.size()) {
                    t.add((((off - first) << 8) | (COMMA)));
                } else {
                    t.set(N, (((off - first) << 8) | (COMMA)));
                }
                ls.lstate = BEGIN_STMT;
                return( 1 );
            default:
                return( 0 );
        }
    }

    public void get_next_state(String pln, int t) {
        int boff;
        int M = ((t) >> 8);

        ls.setBegin(0);
        switch ((((t) & 0x7F))) {
            case COMMENT:
            case DOTDOTDOT:
                break;
            case BLKSTART:
            case BLKCOM:
            case BLKEND:
                ls.setBegin(1);
                break;
            case EOL:
            case IEOL:
                ls.setBegin(1);
                ls.setContin(0);
                break;
            case SEOL:
            case CEOL:
                ls.setContin(1);
                ls.setBegin(1);
                break;
            case LP:
                if (ls.getLstate() == SAW_AT) {
                    ls.setAtlp(1);
                }
            case ID:
            case EEND:
            case FEEND:
                if (ls.getLstate() == SAW_AT) {
                    ls.setLstate(SAW_OPER);
                }
            case COMMA:
            default:
                ls.setContin(0);
        }

        if (ls.getCstate() == AT_BEGINNING) {
            switch ((((t) & 0x7F))) {
                case WHITE:
                case COMMENT:
                case BLKSTART:
                case BLKCOM:
                case BLKEND:
                case DOTDOTDOT:
                case EOL:
                case IEOL:
                case CEOL:
                    break;

                case CLASSDEF:
                    ls.setCstate(IN_CLASS);
                    break;

                default:
                    ls.setCstate(IN_FILE);
                    break;
            }
        }

        switch ((((t) & 0x7F))) {
            case ID:
            case EEND:
            case FEEND: {
                switch (ls.lstate) {
                    case DUAL_ARG:
                    case LAST_STATE:
                        return;
                    case BEGIN_STMT:
                        boff = off - ((t) >> 8);
                        //TODO: diff from js
                        if (non_dual_name(ln, boff) != 0) {
                            ls.lstate = SAW_INDXNM;
                            return;
                        }

                        ls.lstate = command_lookahead(boff, ((t) >> 8) == 4);
                        if (ld_sv != 0 && ((t) >> 8) == 4 && ls.lstate != SAW_INDXNM &&
                                (steqn(pln, "load", 4, M) != 0 || steqn(pln, "save", 4, M) != 0)) {
                            ls.ldsv = F_LS;
                        }
                        return;

                    case NORES:
                    case SAW_OPER:
                    case SAW_EQUALS:
                        ls.lstate = SAW_INDXNM;
                        return;
                    case SAW_EXPR:
                    case SAW_INDXNM:
                        if (ls.npars != 0 || ls.elist != 0 || ls.cstate == IN_CLASS) {
                            return;
                        }
                        break;
                    default:
                        break;
                }
                return;
            }
            case TRY:
                ++ls.indent;

            case ELSE:
            case OTHERWISE:
            case CATCH:
            case RETURN:
            case BREAK:
            case CONTINUE:
                ls.npars = 0;
                ls.stack = 0;
                ls.lstate = BEGIN_STMT;
                break;
            case FUNCTION:
                ls.npars = 0;
                ls.stack = 0;
                ls.indent = 1;
                if (ls.cstate == IN_CLASS) {
                    ls.cstate = IN_METHOD;
                    ls.indent = 3;
                }
                ls.infun = ls.indent;
                ls.lstate = SAW_OPER;
                break;

            case NESTED:
                ls.npars = 0;
                ls.stack = 0;
                if (ls.indent < MAXINFUN) {
                    ls.infun = ++ls.indent;
                }
                if (ls.cstate == IN_CLASS) {
                    ls.cstate = IN_METHOD;
                    ls.indent = 3;
                }
                ls.haveends = 1;
                ls.lstate = SAW_OPER;
                break;

            case END:
                ls.npars = 0;
                ls.stack = 0;
                if (ls.indent > 0) {
                    --ls.indent;
                }
                if (ls.cstate == IN_METHOD && ls.indent == 2) {
                    ls.cstate = IN_CLASS;
                    ls.lstate = SAW_OPER;
                    break;
                }
                if (ls.cstate == IN_CLASS && ls.indent == 0) {
                    ls.cstate = IN_FILE;
                    ls.infun = 0;
                }
                if (ls.indent < ls.infun) {
                    ls.haveends = 1;
                    --ls.infun;
                }
                ls.lstate = BEGIN_STMT;
                break;

            case METHODS:
            case PROPERTIES:
            case EVENTS:
            case ENUMERATION:
                ls.npars = 0;
                ls.stack = 0;
                ++ls.indent;
                ls.lstate = SAW_OPER;
                break;

            case IF:
            case WHILE:
            case FOR:
            case PARFOR:

            case SPMD:
            case PARSECTION:
            case SECTION:
            case SWITCH:
                ++ls.indent;

            case ELSEIF:
            case CASE:
                ls.npars = 0;
                ls.stack = 0;
                ls.lstate = SAW_OPER;
                break;

            case GLOBAL:
            case PERSISTENT:
                ls.npars = 0;
                ls.stack = 0;
                ls.elist = 1;
                ls.lstate = SAW_OPER;
                break;

            case BLKSTART:
                if (ls.ncoms < MAXCOMLEV) {
                    ++ls.ncoms;
                }
                break;

            case BLKEND:
                if (ls.ncoms > 0) {
                    --ls.ncoms;
                }
                break;

            case COMMA:

                if (ls.npars != 0) {
                    ls.lstate = SAW_OPER;
                }
                else {
                    ls.ldsv = 0;
                    ls.stack = 0;
                    ls.lstate = (ls.cstate == IN_CLASS ? SAW_OPER : BEGIN_STMT);
                }
                ls.elist = 0;
                break;

            case SEMI:
                if (ls.npars == 0) {
                    ls.ldsv = 0;
                    ls.stack = 0;
                    ls.lstate = (ls.cstate == IN_CLASS ? SAW_OPER : BEGIN_STMT);
                }
                else {
                    if ((ls.stack & 1) != 0) {
                        ls.lstate = SAW_OPER;
                    }
                    else {
                        ls.elist = 0;
                        ls.npars = 0;
                        ls.stack = 0;
                        ls.ldsv = 0;
                        ls.lstate = (ls.cstate == IN_CLASS ? SAW_OPER : BEGIN_STMT);
                    }
                }
                break;

            case RB:
                if (ls.npars > 0) {
                    ls.npars--;
                    ls.stack >>= 1;
                }
                if (ls.ldsv == F_LS && ls.npars == 0) {
                    ls.lstate = DUAL_ARG;
                    ls.ldsv = V_LS;
                }
                else {
                    ls.lstate = SAW_EXPR;
                }
                break;

            case RC:
                if (ls.npars > 0) {
                    ls.npars--;
                    ls.stack >>= 1;
                }
                ls.lstate = SAW_INDXNM;
                break;
            case RP:
                if (ls.npars > 0) {
                    ls.npars--;
                    ls.stack >>= 1;
                }
                ls.lstate = SAW_INDXNM;
                if (ls.atlp != 0) {
                    ls.lstate = SAW_OPER;
                    ls.atlp = 0;
                }
                break;

            case LP:
                if (ls.npars < MAXPAR) {
                    ls.npars++;
                    ls.stack <<= 1;
                }
                ls.lstate = SAW_OPER;
                break;

            case LB:
                if (ls.npars < MAXPAR) {
                    ls.npars++;
                    ls.stack <<= 1;
                    ls.stack |= 1;
                }
                ls.lstate = SAW_OPER;
                break;

            case LC:
                if (ls.npars < MAXPAR) {
                    ls.npars++;
                    ls.stack <<= 1;
                    if (ls.lstate != SAW_INDXNM) {
                        ls.stack |= 1;
                    }
                }
                ls.lstate = SAW_OPER;
                break;

            case COMMENT:
            case WHITE:
            case PRAGMA:
            case CPAD:
            case DOTDOTDOT:

                switch (ls.lstate) {
                    case SAW_INDXNM:
                    case SAW_EXPR:
                        if (ls.npars != 0 || ls.elist != 0 || ls.cstate == IN_CLASS) {
                            return;
                        }
                        switch (CLINE(off)) {
                            case C_LET:
                            case C_DIG:
                            case C_SQ:
                            case C_LB:
                            case C_NOT:
                                ls.lstate = BEGIN_STMT;
                                break;
                            case C_DOT:
                                if (CLINE(off + 1) == C_DIG) {
                                    ls.lstate = BEGIN_STMT;
                                }
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;

            case AT:
                switch (ls.lstate) {
                    case SAW_INDXNM:
                    case SAW_EXPR:

                        ls.elist = 1;
                        break;
                    default:
                        ls.lstate = SAW_AT;
                }
                break;

            case PLUS:
            case MINUS:
            case MUL:
            case DIV:
            case LDIV:
            case EXP:
            case COLON:
            case AND:
            case ANDAND:
            case OR:
            case OROR:
            case NOT:
            case LT:
            case LE:
            case GT:
            case GE:
            case EQ:
            case NE:
            case CNE:
            case DOTMUL:
            case DOTDIV:
            case DOTLDIV:
            case DOTEXP:
                ls.lstate = SAW_OPER;
                break;

            case EQUALS:
                ls.lstate = SAW_EQUALS;
                ls.elist = 0;
                break;
            case DOTTRANS:
            case FLOAT:
            case INT:
            case STRING:
            case TRANS:
                ls.lstate = SAW_EXPR;
                break;

            case DOT:
                ls.lstate = NORES;
                break;

            case SEOL:
                ls.elist = 0;
                ls.lstate = SAW_OPER;
                break;

            case EOL:
            case IEOL:
                ls.elist = 0;
                ls.npars = 0;
                ls.stack = 0;
                ls.ldsv = 0;
                ls.lstate = ( ls.cstate == IN_CLASS ? SAW_OPER : BEGIN_STMT );
                break;

            case CEOL:
                ls.contin = 1;
                break;

            case BANG:
                ls.lstate = BEGIN_STMT;
                break;

            case SEMERR:
            case EOLERR:

                ls.stack = 0;
                ls.npars = 0;
                ls.elist = 0;
                ls.lstate = BEGIN_STMT;
                break;
            case NEGERR:
                ls.stack = 0;
                ls.npars = 0;
                ls.elist = 0;
                ls.lstate = SAW_EXPR;
                break;
            case DEEP_PAREN:
            case DEEP_COM:
            case DEEP_NEST:
            case DEEP_STMT:

                ls.stack = 0;
                ls.npars = 0;
                ls.elist = 0;
                ls.lstate = SAW_OPER;
                break;

            case RES_ERR:
            case FP_ERR:
            case UNTERM:
            case BADCHAR:
            case SHARP:
            case DOLLAR:
            case BACKQUOTE:
            case DOUBLEQUOTE:
                ls.lstate = SAW_EXPR;
                break;

            case CLASSDEF:
                ls.cstate = IN_CLASS;
                ls.lstate = SAW_OPER;
                ls.indent = 1;
                ls.haveends = 1;
                break;

            case BLKCOM:
                break;
            case DUAL:
                if (ls.ldsv == F_LS && pln.charAt(M + 0) != '-') {
                    ls.ldsv = V_LS;
                }
                break;

            default:
                break;
        }

    }

    public int non_dual_name(String s, int len) {
        String[] non_dual_funs = { "pi", "eps", "nan", "NaN", "inf", "Inf", "i", "j", "ans" };
        int[] non_dual_size = {2, 3, 3, 3, 3, 3, 1, 1, 3};

        int i;
        if (s.length() == 0) {
            return 1;
        }
        for (i = 0; i < non_dual_funs.length; ++i) {
            if (len == non_dual_size[i] && steqn(s, non_dual_funs[i], len) != 0) {
                return 1;
            }
        }

        return 0;
    }

    public int steqn(String s, String t, int n) {
        int N = 0;
        if (s.length() + N < n) {
            return 0;
        }
        for (; n > 0; --n) {
            if (s.charAt(N + n - 1) != t.charAt(n - 1)) {
                return 0;
            }
        }
        return 1;
    }

    public int steqn(String s, String t, int n, int N) {
        if (s.length() + N < n) {
            return 0;
        }
        for (; n > 0; --n) {
            if (s.charAt(N + n - 1) != t.charAt(n - 1)) {
                return 0;
            }
        }
        return 1;
    }

    public int CLINE(int x) {
        if (x >= eoff) {
            return C_EOL;
        }
        else {
            return char_classifier((int) ln.charAt(x));
        }
    }

    public int char_classifier(int u) {
        return ((u & ~0x7f) == 0 ? cdope[u] : C_ERR);
    }

    public int command_lookahead(int boff, boolean sz4) {
        int i;
        int depth = 0, quote = 0;
        boolean lsflag = false;
        if (CLINE(off) != C_WHITE) {
            return SAW_INDXNM;
        }
        for (i = off; CLINE(i) == C_WHITE; ++i) {
        }
        if (i >= eoff) {
            return SAW_INDXNM;
        }

        if (ld_sv != 0 && (steqn(ln, "load", 4, boff) != 0 || steqn(ln, "save", 4, boff) != 0)) {
            lsflag = true;
        }

        switch (CLINE(i)) {
            case C_DOT:
                if (CLINE(i + 1) == C_DOT && CLINE(i + 2) == C_DOT) {
                    return( SAW_INDXNM );
                }

                switch (CLINE(i + 1)) {
                    case C_DIG:
                        return( DUAL_ARG );

                    case C_WHITE:


                        break;


                    case C_EXP:
                    case C_MUL:
                    case C_DIV:
                    case C_BSLASH:
                    case C_SQ:
                    case C_LP:
                        return( dual_oper(i + 2) );

                    case C_LET:
                        break;

                    default:
                        return( DUAL_ARG );
                }
                break;

            case C_EQUALS:
                if (CLINE(i + 1) == C_EQUALS) {
                    return( dual_oper(i + 2) );
                }

            case C_LP:
            case C_RP:
                return( SAW_INDXNM );

            case C_EOL:
            case C_SEMI:
            case C_COMMA:
            case C_PCT:
                return( SAW_INDXNM );

            case C_LC:
                break;

            case C_AND:
                if (CLINE(i + 1) == C_AND) {
                    return( dual_oper(i + 2) );
                }
                return( dual_oper(i + 1) );

            case C_OR:
                if (CLINE(i + 1) == C_OR) {
                    return( dual_oper(i + 2) );
                }
                return( dual_oper(i + 1) );

            case C_NOT:
                if (CLINE(i + 1) == C_EQUALS) {
                    return( dual_oper(i + 2) );
                }
                return( DUAL_ARG );
            case C_LT:
            case C_GT:
            case C_BANG:
                if (CLINE(i + 1) == C_EQUALS) {
                    return( dual_oper(i + 2) );
                }
                return( dual_oper(i + 1) );

            case C_COLON:
            case C_PLUS:
            case C_MINUS:
            case C_MUL:
            case C_DIV:
            case C_BSLASH:
            case C_EXP:
            case C_AT:
                return( dual_oper(i + 1) );

            case C_LB:
                if (lsflag || ls.ldsv == F_LS) {
                    return( SAW_OPER );
                }

            default:
                return( DUAL_ARG);
        }

        for (quote = depth = 0; i < eoff; ++i) {
            switch (CLINE(i)) {
                case C_EQUALS:
                    if (quote == 0 && CLINE(i + 1) != C_EQUALS) {
                        return( depth != 0 ? DUAL_ARG : SAW_INDXNM );
                    }
                    continue;

                case C_COMMA:
                    if (quote == 0 && depth == 0) {
                        break;
                    }
                    continue;

                case C_DOT:
                    if (CLINE(i + 1) == C_DOT && CLINE(i + 2) == C_DOT) {
                        break;
                    }
                    continue;

                case C_SEMI:
                    if (quote == 0 || depth == 0) {
                        break;
                    }
                    continue;

                case C_PCT:
                    if (quote == 0) {
                        break;
                    }
                    continue;

                case C_EOL:
                    break;

                case C_SQ:
                    quote ^= 1;
                    continue;

                case C_LP:
                case C_LC:
                case C_LB:
                    if (quote == 0) {
                        ++depth;
                    }
                    continue;

                case C_RP:
                case C_RC:
                case C_RB:
                    if (quote == 0) {
                        --depth;
                    }
                    continue;

                default:
                    continue;
            }
            break;
        }

        return DUAL_ARG;
    }

    public int dual_oper(int j) {
        if (CLINE(j) == C_WHITE) {
            while (CLINE(j) == C_WHITE) {
                ++j;
            }

            switch (CLINE(j)) {
                case C_DOT:
                    if (CLINE(j + 1) == C_DOT && CLINE(j + 2) == C_DOT) {
                        return( SAW_INDXNM );
                    }
                    if (CLINE(j + 1) == C_DIG) {
                        return( SAW_INDXNM );
                    }
                    return( DUAL_ARG );
                case C_COMMA:
                case C_SEMI:
                case C_EOL:
                case C_RP:
                case C_RC:
                case C_RB:
                case C_PCT:
                case C_AND:
                case C_OR:
                case C_LT:
                case C_GT:
                case C_EQUALS:
                case C_COLON:
                case C_MUL:
                case C_DIV:
                case C_BSLASH:
                case C_AT:
                case C_EXP:
                    return( DUAL_ARG );
                default:
                    return( SAW_INDXNM );
            }
        }


        return( DUAL_ARG );
    }

    public boolean are_states_equal(LexState p, LexState q) {
        return p.lstate == q.lstate && p.cstate == q.cstate &&
                p.indent == q.indent && p.contin == q.contin &&
                p.atlp == q.atlp && p.begin == q.begin &&
                p.infun == q.infun && p.ldsv == q.ldsv &&
                p.ncoms == q.ncoms && p.elist == q.elist &&
                p.npars == q.npars && p.stack == q.stack &&
                p.haveends == q.haveends;
    }






    public class LexResults {
        private int numTokens;
        private LexState newState;
        private boolean isExecutable;

        public LexResults(int numTokens, LexState newState, boolean isExecutable) {
            this.numTokens = numTokens;
            this.newState = newState;
            this.isExecutable = isExecutable;
        }

        public int getNumTokens() {
            return numTokens;
        }

        public void setNumTokens(int numTokens) {
            this.numTokens = numTokens;
        }

        public LexState getNewState() {
            return newState;
        }

        public void setNewState(LexState newState) {
            this.newState = newState;
        }

        public boolean isExecutable() {
            return isExecutable;
        }

        public void setIsExecutable(boolean isExecutable) {
            this.isExecutable = isExecutable;
        }
    }

    public static class LexState {
        public int cstate;
        public int lstate;
        public int indent;
        public int infun;
        public int npars;
        public int ncoms;
        public int ldsv;
        public int atlp;
        public int contin;
        public int elist;
        public int haveends;
        public int begin;
        public int spare;
        public int stack;

        public LexState(int cstate, int lstate, int indent, int infun, int npars, int ncoms,
                        int ldsv, int atlp, int contin, int elist, int haveends, int begin,
                        int spare, int stack) {
            this.cstate = cstate;
            this.lstate = lstate;
            this.indent = indent;
            this.infun = infun;
            this.npars = npars;
            this.ncoms = ncoms;
            this.ldsv = ldsv;
            this.atlp = atlp;
            this.contin = contin;
            this.elist = elist;
            this.haveends = haveends;
            this.begin = begin;
            this.spare = spare;
            this.stack = stack;
        }

        public void set(int cstate, int lstate, int indent, int infun, int npars, int ncoms,
                        int ldsv, int atlp, int contin, int elist, int haveends, int begin,
                        int spare, int stack) {
            this.cstate = cstate;
            this.lstate = lstate;
            this.indent = indent;
            this.infun = infun;
            this.npars = npars;
            this.ncoms = ncoms;
            this.ldsv = ldsv;
            this.atlp = atlp;
            this.contin = contin;
            this.elist = elist;
            this.haveends = haveends;
            this.begin = begin;
            this.spare = spare;
            this.stack = stack;
        }

        public int getCstate() {
            return cstate;
        }

        public void setCstate(int cstate) {
            this.cstate = cstate;
        }

        public int getLstate() {
            return lstate;
        }

        public void setLstate(int lstate) {
            this.lstate = lstate;
        }

        public int getIndent() {
            return indent;
        }

        public void setIndent(int indent) {
            this.indent = indent;
        }

        public int getInfun() {
            return infun;
        }

        public void setInfun(int infun) {
            this.infun = infun;
        }

        public int getNpars() {
            return npars;
        }

        public void setNpars(int npars) {
            this.npars = npars;
        }

        public int getNcoms() {
            return ncoms;
        }

        public void setNcoms(int ncoms) {
            this.ncoms = ncoms;
        }

        public int getLdsv() {
            return ldsv;
        }

        public void setLdsv(int ldsv) {
            this.ldsv = ldsv;
        }

        public int getAtlp() {
            return atlp;
        }

        public void setAtlp(int atlp) {
            this.atlp = atlp;
        }

        public int getContin() {
            return contin;
        }

        public void setContin(int contin) {
            this.contin = contin;
        }

        public int getElist() {
            return elist;
        }

        public void setElist(int elist) {
            this.elist = elist;
        }

        public int getHaveends() {
            return haveends;
        }

        public void setHaveends(int haveends) {
            this.haveends = haveends;
        }

        public int getBegin() {
            return begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public int getSpare() {
            return spare;
        }

        public void setSpare(int spare) {
            this.spare = spare;
        }

        public int getStack() {
            return stack;
        }

        public void setStack(int stack) {
            this.stack = stack;
        }


    }

}
