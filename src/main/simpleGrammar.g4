
grammar simpleGrammar;

top_level: statements? EOF;

// Lexer Part

IDENTIFIER: IDENT_HEAD IDENT_CHAR*;
fragment IDENT_HEAD: [a-z] | [A-Z] | '_';
fragment IDENT_CHAR: [0-9] | IDENT_HEAD;

DECIMAL_LITERAL: DECIMAL_DIGIT+;
fragment DECIMAL_DIGIT: '0' .. '9';

STRING_LITERAL: '"' (ESC|.)*? '"';
fragment ESC: '\\' | '\\\\';

// String Interpolation
STRING_HEAD: '"' (ESC|.)*? '\\(';
STRING_INTERM: ')' (ESC|.)*? '\\(';
STRING_END: ')' (ESC|.)*? '"';

// Multiline String
STRING_LINE: MSTR_TEXT? NEWLINE;
fragment MSTR_TEXT: (~["\r\n\\])+;
fragment NEWLINE: '\r' | '\n';
MQUOTE: '"""';

CHARACTER_LITERAL: '\'' CHAR '\'';
fragment CHAR: ~["\\EOF\n];

COMMENT: '//' ~('\r' | '\n')* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;

WS: [ \t\n\r]+ -> skip;


WHEN_ARROW: '=>';
FALLTHROUGH: ':||';

SET: '=';

ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
MOD: '%';
EXP: '^';

GT: '>'; LT: '<'; GEQ: '>='; LEQ: '<='; EQ: '=='; NEQ: '!=';
MULEQ: '*='; DIVEQ: '/='; MODEQ: '%='; ADDEQ: '+='; SUBEQ: '-=';

AND: '&&';
OR: '||';
NOT: '!';

UNTIL: '..<';
THROUGH: '...';
DUNTIL: '>>.';
DTHROUGH: '>..';
STEP: '@';

ARROW: '->';
GENERATOR_IDENT: '**';
REF: '&';
DOT: '.';

// Parser Part

// Literals

literal: numeric_literal | boolean_literal| char_sequence_literal | nil_literal;
numeric_literal: '-'? (integer_literal | double_literal);
boolean_literal: 'true' | 'false';
char_sequence_literal: STRING_LITERAL | CHARACTER_LITERAL | multiline_string_literal;
nil_literal: 'nil';

multiline_string_literal: MQUOTE STRING_LINE+ MQUOTE;
integer_literal: DECIMAL_LITERAL;
double_literal: DECIMAL_LITERAL '.' DECIMAL_LITERAL?
              | DECIMAL_LITERAL? ',' DECIMAL_LITERAL
              ;

// Types

type: function_type
    | array_type
    | map_type
    | type_identifier
    | tuple_type
    | optional_type
    | implicitly_unwrapped_optional_type
    | any_type
    | self_type
    | '(' type ')';

type_annotation: ':' type;

type_identifier: type_name generic_argument_clause?
               | type_name generic_argument_clause? type_identifier;

type_name: IDENTIFIER;

tuple_type: '(' ')' | '(' tuple_type_element (',' tuple_type_element)* ')';
tuple_type_element: element_name type_annotation | type;
element_name: IDENTIFIER;

function_type: function_type_argument_clause ':' type;
function_type_argument_clause: '(' ')'
                             | '(' function_type_argument (',' function_type_argument)* ')';
function_type_argument: type | argument_label type_annotation;
argument_label: IDENTIFIER;

array_type: '[' type ']';
map_type: '[' type ':' type ']';
optional_type: type '?';

implicitly_unwrapped_optional_type: type '!!';

any_type: 'Any';
self_type: 'Self';

type_inheritance_clause: type_identifier | type_identifier (',' type_identifier);

// Expressions

expression: prefix_expression binary_expressions?;

// Prefix Expressions
prefix_expression: prefix_operator? postfix_expression
                 | in_out_expression;

in_out_expression: REF expression;

// Binary Expressions
binary_expression: binary_operator prefix_expression
                 | assignment_operator prefix_expression
                 | special_assignment_operator prefix_expression
                 | conditional_operator prefix_expression
                 | type_casting_operator
                 | binary_expression
                 ;
binary_expressions: binary_expression+;

assignment_operator: SET;
special_assignment_operator: MULEQ | DIVEQ | ADDEQ | SUBEQ | MODEQ;

conditional_operator: '?' expression ':';

type_casting_operator: 'is' type | 'as' type | 'as' '?' type | 'as' '!' type;

// Primary Expressions
primary_expression: IDENTIFIER generic_argument_clause?
                  | literal_expression
                  | self_expression
                  | superclass_expression
                  | closure_expression
                  | parenthesized_expression
                  | tuple_expression
                  | implicit_member_expression
                  | wildcard_expression
                  ;

literal_expression: literal | array_literal | map_literal | interpolation_string_expression;

array_literal: '[]' | '[' array_literal_item (',' array_literal_item)* ']';
array_literal_item: expression;

map_literal: '[' map_literal_item (',' map_literal_item)* ']' | '[' ':' ']';
map_literal_item: expression ':' expression;

interpolation_string_expression: STRING_HEAD expression (STRING_INTERM expression)* STRING_END;
// Need to be tested

self_expression: 'self' | ('self' '.' IDENTIFIER);
superclass_expression: 'super' '.' IDENTIFIER;

closure_expression: '{' closure_parameter_clause ARROW statements '}';
closure_parameter_clause: '(' ')' | '(' closure_parameter (',' closure_parameter)* ')';
closure_parameter: closure_parameter_name type_annotation;
closure_parameter_name: IDENTIFIER;

implicit_member_expression: '.' IDENTIFIER;

parenthesized_expression: '(' expression ')';

tuple_expression: '(' ')' | '(' tuple_elements ')';
tuple_elements: tuple_element (',' tuple_element)*;
tuple_element: expression | IDENTIFIER ':' expression;

wildcard_expression: '_';

// Postfix Expressions
postfix_expression: primary_expression
                  | postfix_expression postfix_operator
                  | function_call_expression
                  | explicit_member_expression
                  | postfix_self_expression
                  | subscript_expression
                  | forced_value_expression
                  | optional_chaining_expression
                  ;

function_call_expression: postfix_expression function_call_argument_clause;
function_call_argument_clause: '(' ')' | '(' function_call_argument_list ')';
function_call_argument_list: function_call_argument (',' function_call_argument)*;
function_call_argument: IDENTIFIER ':' expression;

explicit_member_expression: postfix_expression '.' DECIMAL_LITERAL
                          | postfix_expression '.' IDENTIFIER generic_argument_clause?
                          | postfix_expression '.' IDENTIFIER '(' argument_names ')';
argument_names: argument_name (',' argument_name)*;
argument_name: IDENTIFIER ':_';

postfix_self_expression: postfix_expression '.' 'self';

subscript_expression: postfix_expression '[' function_call_argument_list ']';

forced_value_expression: postfix_expression '!!';

optional_chaining_expression: postfix_expression '?';

// Statements

statements: statement+;

statement: expression ';'?
         | declaration ';'?
         | loop_statement ';'?
         | branch_statement ';'?
         | control_transfer_statement ';'?
         | do_statement ';'?
         | assert_statement ';'?
         ;

loop_statement: for_in_statement
              | while_statement
              | repeat_while_statement
              ;

for_in_statement: 'for' pattern 'in' expression code_block;

while_statement: 'while' condition_list code_block;
condition_list: condition (',' condition)*;
condition: expression | optional_binding_condition;
optional_binding_condition: 'cst' pattern initializer | 'var' pattern initializer;

repeat_while_statement: 'repeat' code_block 'while' expression;

branch_statement: if_statement | switch_statement;

if_statement: 'if' condition_list code_block else_clause?;
else_clause: 'else' code_block | 'else' if_statement;

switch_statement: 'switch' expression '{' switch_cases? '}';
switch_cases: switch_case+;
switch_case: case_label code_block
           | default_label code_block;
case_label: 'case' case_item_list ':';
case_item_list: case_item(',' case_item)*;
case_item: pattern where_clause?;
default_label: 'default' ':';

where_clause: 'where' where_expression;
where_expression: expression;


control_transfer_statement: break_statement | continue_statement | fallthrough_statement | return_statement | throw_statement;

break_statement: 'break';
continue_statement: 'continue';
fallthrough_statement: 'fallthrough';
return_statement: 'return' expression?;
throw_statement: 'throw' expression;
yield_statement: 'yield' expression;

assert_statement: 'assert' expression;

do_statement: 'do' code_block;

// Declarations

declaration: constant_declaration
           | variable_declaration
           | typealias_declaration
           | function_declaration
           | enum_declaration
           | struct_declaration
           | proto_declaration
           | extension_declaration
           | subscript_declaration
           ;

code_block: '{' statements? '}';

// Patterns

pattern: identifier_pattern
       | wildcard_pattern
       | value_binding_pattern
       | tuple_pattern
       | enum_case_pattern
       | optional_pattern
       | type_casting_pattern
       | expression_pattern
       ;

wildcard_pattern: '_';
identifier_pattern: IDENTIFIER;

value_binding_pattern: 'var' pattern | 'cst' pattern;

tuple_pattern: '(' ')' | '(' tuple_pattern_element (',' tuple_pattern_element)* ')';
tuple_pattern_element: pattern;

enum_case_pattern: type_identifier '.' enum_case_name;

optional_pattern: identifier_pattern '?';

type_casting_pattern: is_pattern | as_pattern;
is_pattern: 'is' type;
as_pattern: 'as' type;

expression_pattern: expression;


// Operators

operator: operator_head operator_chars?
        | dot_operator_head dot_operator_chars;

operator_head: '/' | '=' | '-' | '+' | '!' | '*' | '%' | '<' | '>' | '&' | '|' | '^' | '~' | '?';
operator_chars: operator_head+;

dot_operator_head: '.';
dot_operator_char: '.' | operator_head;
dot_operator_chars: dot_operator_char+;

binary_operator: operator;
prefix_operator: operator;
postfix_operator: operator;

// Generic Parameters and Arguments

generic_parameter_clause: '<' generic_parameter_list '>';
generic_parameter_list: generic_parameter (',' generic_parameter)*;
generic_parameter: type_name
                 | type_name ':' type_identifier;

generic_argument_clause: '<' generic_argument_list '>';
generic_argument_list: generic_argument (',' generic_argument)*;
generic_argument: type;