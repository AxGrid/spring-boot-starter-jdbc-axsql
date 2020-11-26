grammar AxSQLQueryGrammar;

sql: WS? (part WS?)* EOF;

part: (query|param);
query: (ATOM|CHAR|NON_WS);

param: (UNNAMED_PARAM|NAMED_PARAM);

NEWLINE: ('\n' | '\r\n')+;
WS: (' ' | '\t')+;
UNNAMED_PARAM: (':'ATOM|'?');
fragment AGROUP: (ATOM|CHAR|NON_WS);
NAMED_PARAM: AGROUP'='(':'ATOM|'?');
CHAR: [A-Za-z0-9_];
ATOM: CHAR+;
NON_WS: ~(' ' | '\t' | '\n')+;
