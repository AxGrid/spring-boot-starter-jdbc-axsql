grammar AxSQLGrammar;

queries: meta* query* EOF;
query: docstring? name docstring? (param|mapper|object)* statement;

statement: line (line | comment)*;
docstring: comment+;

meta: (meta_version|meta_package|meta_author|meta_date);

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

meta_version: WS? COMMENT_MARKER WS? VERSION_TAG WS? (NON_WS|WS|CHAR|ATOM|PATH) WS? NEWLINE;
meta_package: WS? COMMENT_MARKER WS? PACKAGE_TAG WS? path WS? (NON_WS|WS|CHAR|ATOM|PATH)? WS? NEWLINE;
meta_author: WS? COMMENT_MARKER WS? AUTHOR_TAG WS? (NON_WS|WS|CHAR|ATOM|PATH) WS? NEWLINE;
meta_date: WS? COMMENT_MARKER WS? DATE_TAG WS? (NON_WS|WS|CHAR|ATOM|PATH) WS? NEWLINE;

sql: ~COMMENT_MARKER (NON_WS|WS|CHAR|ATOM|PATH)*;

cast
    : FROM_METHOD
    | path
    | 'json'
    | 'proto'
    | 'like' WS like_param
    ;

like_param
    : (NON_WS|CHAR|ATOM|PATH) (NON_WS|WS|CHAR|ATOM|PATH)*
    ;

NAME_TAG: 'name:';
PARAM_TAG: ('@param '|'@param:');
MAPPER_TAG: ('@mapper '|'@mapper:');
OBJECT_TAG: ('@object '|'@object:');
VERSION_TAG: ('@version '|'@version:' | '@ver ' | '@ver:');
AUTHOR_TAG: ('@author '|'@author:');
DATE_TAG: ('@date '|'@date:');
PACKAGE_TAG: ('@package '|'@package:');
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
