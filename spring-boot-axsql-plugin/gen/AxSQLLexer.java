// Generated from /Users/zed/IdeaProjects/spring-boot-starter-jdbc-axsql/spring-boot-axsql-plugin/src/main/antlr4/com/axgrid/jdbc/axsql/AxSQLLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AxSQLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		START_COMMENT=1, EOL=2, COMMENT=3;
	public static final int
		CMT=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "CMT"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"START_COMMENT", "EOL", "COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "START_COMMENT", "EOL", "COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public AxSQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AxSQLLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\5 \b\1\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\6\3\23\n\3\r\3\16\3"+
		"\24\3\4\7\4\30\n\4\f\4\16\4\33\13\4\3\4\3\4\3\4\3\4\3\31\2\5\4\3\6\4\b"+
		"\5\4\2\3\2\2!\2\4\3\2\2\2\2\6\3\2\2\2\3\b\3\2\2\2\4\n\3\2\2\2\6\22\3\2"+
		"\2\2\b\31\3\2\2\2\n\13\7%\2\2\13\f\3\2\2\2\f\r\b\2\2\2\r\16\b\2\3\2\16"+
		"\5\3\2\2\2\17\23\7\f\2\2\20\21\7\17\2\2\21\23\7\f\2\2\22\17\3\2\2\2\22"+
		"\20\3\2\2\2\23\24\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\7\3\2\2\2\26"+
		"\30\13\2\2\2\27\26\3\2\2\2\30\33\3\2\2\2\31\32\3\2\2\2\31\27\3\2\2\2\32"+
		"\34\3\2\2\2\33\31\3\2\2\2\34\35\5\6\3\2\35\36\3\2\2\2\36\37\b\4\4\2\37"+
		"\t\3\2\2\2\7\2\3\22\24\31\5\7\3\2\b\2\2\6\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}