grammar AxSQLGrammar;

queries: query* EOF;
query: docstring? name docstring? (param|mapper|object)* statement;

statement: line (line | comment)*;
docstring: comment+;

name: WS? COMMENT_MARKER WS? NAME_TAG WS? execute_name WS? NEWLINE;
param: WS? COMMENT_MARKER WS? PARAM_TAG WS? path WS? variable? WS? cast? WS? NEWLINE;
mapper: WS? COMMENT_MARKER WS? MAPPER_TAG WS? path WS? NEWLINE;
object: WS? COMMENT_MARKER WS? OBJECT_TAG WS? path WS? variable? WS? NEWLINE;
comment: WS? COMMENT_MARKER WS? ~(NAME_TAG|PARAM_TAG|MAPPER_TAG|OBJECT_TAG) (NON_WS|WS|CHAR|ATOM|PATH)+ NEWLINE;
line: WS? sql (COMMENT_MARKER (NON_WS|WS|CHAR|ATOM|PATH)*)? NEWLINE;
emptyline: WS? NEWLINE;
path: (ATOM|PATH);
variable: (CHAR|ATOM);
execute_name: (CHAR|ATOM|EXECUTE_NAME);

sql: ~COMMENT_MARKER (NON_WS|WS|CHAR|ATOM|PATH)*;

cast
    : FROM_METHOD
    | path
    | 'json'
    | 'proto'
    ;

NAME_TAG: 'name:';
PARAM_TAG: ('@param '|'@param:');
MAPPER_TAG: ('@mapper '|'@mapper:');
OBJECT_TAG: ('@object '|'@object:');

COMMENT_MARKER: '--';
NEWLINE: ('\n' | '\r\n')+;
WS: (' ' | '\t')+;
CHAR: [A-Za-z0-9_];
ATOM: CHAR+;
PATH: ATOM ('.' ATOM)*;
EXECUTION: ('!'|'<!');
EXECUTE_NAME: (CHAR|ATOM)EXECUTION;
FROM_METHOD: '.' PATH '()'?;
NON_WS: ~(' ' | '\t' | '\n')+;
