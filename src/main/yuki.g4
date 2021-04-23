
grammar yuki;

top_level: statements? EOF;

// Lexical Part

IDENTIFIER: IDENT_HEAD IDENT_CHAR*;
fragment IDENT_HEAD: [A-Z] | [a-z]
                   | '_'
                   | '\u00a8' .. '\u00aa' | '\u00ad' .. '\u00af' | '\u00b2' .. '\u00b5' | '\u00b7' .. '\u00ba'
                   | '\u00bc' .. '\u00be' | '\u00c0' .. '\u00d6' | '\u00d8' .. '\u00f6' | '\u00f8' .. '\u00ff'
                   | '\u0100' .. '\u02ff' | '\u0370' .. '\u167f' | '\u1681' .. '\u180d' | '\u180f' .. '\u1dbf'
                   | '\u1e00' .. '\u1fff'
                   | '\u200b' .. '\u200d' | '\u202a' .. '\u202e' | '\u203f' .. '\u2040' | '\u2054' | '\u2060' .. '\u206f'
                   | '\u2070' .. '\u20cf' | '\u2100' .. '\u218f' | '\u2460' .. '\u24ff' | '\u2776' .. '\u2793'
                   | '\u2c00' .. '\u2dff' | '\u2e80' .. '\u2fff'
                   | '\u3004' .. '\u3007' | '\u3021' .. '\u302f' | '\u3031' .. '\u303f' | '\u3040' .. '\ud7ff'
                   | '\uf900' .. '\ufd3d' | '\ufd40' .. '\ufdcf' | '\ufdf0' .. '\ufe1f' | '\ufe30' .. '\ufe44'
                   | '\ufe47' .. '\ufffd'
                   | '\u{10000}' .. '\u{1fffd}' | '\u{20000}' .. '\u{2fffd}' | '\u{30000}' .. '\u{3fffd}' | '\u{40000}' .. '\u{4fffd}'
                   | '\u{50000}' .. '\u{5fffd}' | '\u{60000}' .. '\u{6fffd}' | '\u{70000}' .. '\u{7fffd}' | '\u{80000}' .. '\u{8fffd}'
                   | '\u{90000}' .. '\u{9fffd}' | '\u{a0000}' .. '\u{afffd}' | '\u{b0000}' .. '\u{bfffd}' | '\u{c0000}' .. '\u{cfffd}'
                   | '\u{d0000}' .. '\u{dfffd}' | '\u{e0000}' .. '\u{efffd}' ;
fragment IDENT_CHAR: [0-9]
                   | '\u0300' .. '\u036f' | '\u1dc0' .. '\u1dff' | '\u20d0' .. '\u20ff' | '\ufe20' .. '\ufe2f'
                   | IDENT_HEAD;

DECIMAL_LITERAL: DECIMAL_DIGIT+;
fragment DECIMAL_DIGIT: '0' .. '9';

STATIC_STRING_LITERAL: '"' (ESC|.)*? '"';
fragment ESC: '\\' | '\\\\';

STRING_HEAD: '""' (ESC|~[\\(){}])*? '\\({';
STRING_INTERM: '})' (ESC|~[\\(){}])*? '\\({';
STRING_END: '})' (ESC|~[\\(){}])*? '""';

CHARACTER_LITERAL: '\'' CHAR '\'';
fragment CHAR: ~['"\\EOF\n];

COMMENT: '//' ~('\r' | '\n')* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;

WS: [ \t\n\r]+ -> skip;

WHEN_ARROW: '=>';
FALLTHROUGH: ':||';
ARROW: '->';
REF: '&';

ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
MOD: '%';
EQ: '==';
NEQ: '!=';
CPT: '~=';
AND: '&&';
OR: '||';
XOR: '^^';
NOT: '!';
EXP: '^';
GT: '>';
LT: '<';
GEQ: '>=';
LEQ: '<=';
ADDEQ: '+=';
SUBEQ: '-=';
MULEQ: '*=';
DIVEQ: '/=';
MODEQ: '%=';

THROUGH: '...';
UNTIL: '..<';
DOWNTO: '>>.';
DOWNTHROUGH: '>..';

STEP: '@';

APPEND: '++';

DOT_OPERATOR: '.';

MIXINS: ':::';

// Parser Part


// Literals

literal: numeric_literal | boolean_literal | char_sequence_literal | nil_literal;
numeric_literal: (ADD | SUB)? (integer_literal | double_literal);
boolean_literal: 'true' | 'false';
char_sequence_literal: string_literal | CHARACTER_LITERAL;
nil_literal: 'nil';

integer_literal: DECIMAL_LITERAL;
double_literal: DECIMAL_LITERAL '.' DECIMAL_LITERAL?
              | DECIMAL_LITERAL? '.' DECIMAL_LITERAL
              ;
string_literal: STATIC_STRING_LITERAL | interpolated_string_literal;
interpolated_string_literal: STRING_HEAD expression (STRING_INTERM expression)* STRING_END;



// Types

type: function_type
    | array_type
    | map_type
    | type_identifier
    | tuple_type
    | any_type
    | self_type
    | '(' type ')'
    | type '?'  // optional_type
    | type '!!' // implicitly_unwrapped_optional_type
    ;

type_annotation: ':' type;

type_identifier: type_name generic_argument_clause?
               | type_name generic_argument_clause? '.' type_identifier;

type_name: IDENTIFIER;

tuple_type: '(' ')' | '(' tuple_type_element (',' tuple_type_element)* ')';
tuple_type_element: element_name type_annotation | type;
element_name: IDENTIFIER;

function_type: function_type_argument_clause '->' type;
function_type_argument_clause: '(' ')'
                             | '(' function_type_argument (',' function_type_argument)* ')';
function_type_argument: type | argument_label type_annotation;
argument_label: IDENTIFIER;

array_type: '[' type ']';
map_type: '[' type ':' type ']';

any_type: 'Any';
self_type: 'Self';

type_inheritance_clause: MIXINS type_identifier (',' type_identifier)*;



// Expressions

expression:
          // Postfix expressions aka explicit member expressions
            explicit_member_expression
          // Primary Expressions
          | variable_expression
          | literal_expression
          | self_expression
          | superclass_initialization_expression
          | closure_expression
          | function_call_expression
          | parenthesized_expression
          | wildcard_expression
          | spread_expression
          // Binary Expressions
          | NOT expression
          | (ADD | SUB) expression
          | expression op=('<<' | '>>') expression
          | expression op=(MUL | DIV | MOD) expression
          | expression op=(ADD | SUB) expression
          | <assoc=right> expression EXP expression
          | expression APPEND expression
          | expression op=(THROUGH | UNTIL | DOWNTO | DOWNTHROUGH) expression (STEP expression)?
          | expression type_casting_operator type_list
          | expression op=(NEQ | EQ | CPT) expression
          | expression op=(GT | LT | GEQ | LEQ) expression
          | expression op=AND expression
          | expression op=(OR | XOR) expression
          | expression conditional_operator expression
          | pattern assignment_operator expression
          | pattern op=(ADDEQ | SUBEQ | MULEQ | DIVEQ | MODEQ) expression
          ;

assignment_operator: '=';

conditional_operator: '?' expression ':';

type_casting_operator: 'is' | 'as' | 'as' '?' | 'as' '!';

type_list: type ('|' type)*;

variable_expression: IDENTIFIER (':' type)?;

literal_expression: literal | array_literal | map_literal;

array_literal: '[' ']' | '[' array_literal_item (',' array_literal_item)* ','? ']';
array_literal_item: expression;

map_literal: '[' map_literal_item (',' map_literal_item)* ','? ']' | '[' ':' ']';
map_literal_item: expression ':' expression;


self_expression: 'self';
superclass_expression: 'super';
superclass_initialization_expression: superclass_expression '(' parameter_name (',' parameter_name)* ')';

closure_expression: '{' closure_parameter_clause ARROW statements '}';
closure_parameter_clause: '(' ')' | '(' closure_parameter (',' closure_parameter)* ')';
closure_parameter: closure_parameter_name type_annotation;
closure_parameter_name: IDENTIFIER;

parenthesized_expression: '(' expression ')';

tuple_expression: '(' ')' | '(' tuple_elements ')';
tuple_elements: tuple_element (',' tuple_element)*;
tuple_element: expression | IDENTIFIER ':' expression;

wildcard_expression: '_';

spread_expression: '...' expression;


function_call_expression: function_name generic_argument_clause? function_call_argument_clause
                        | function_name generic_argument_clause? code_block
                        ;
function_call_argument_clause: '(' ')' | '(' function_call_by_name ')' | '(' function_call_argument_list ')';
function_call_by_name: '::' IDENTIFIER;
function_call_argument_list: function_call_argument (',' function_call_argument)*;
function_call_argument: REF? expression | IDENTIFIER ':' REF? expression;

argument_names: argument_name (',' argument_name)*;
argument_name: IDENTIFIER ':_';

explicit_member_callee: variable_expression
                      | literal_expression
                      | self_expression
                      | superclass_expression
                      | tuple_expression;
explicit_member_expression: explicit_member_callee ('?')? '.' function_call_expression
                          | explicit_member_callee ('?')? '.' DECIMAL_LITERAL
                          | explicit_member_callee ('?')? '.' IDENTIFIER
                          | explicit_member_callee ('?')? '.' IDENTIFIER '(' argument_names ')'
                          | explicit_member_callee ('?')? '.' self_expression
                          | explicit_member_callee ('?')? '.' superclass_expression
                          | explicit_member_callee ('?')? '[' subscript ']'
                          | explicit_member_callee '!!'
                          | explicit_member_callee '?:' expression
                          | explicit_member_expression ('?')? '.' function_call_expression
                          | explicit_member_expression ('?')? '.' DECIMAL_LITERAL
                          | explicit_member_expression ('?')? '.' IDENTIFIER
                          | explicit_member_expression ('?')? '.' IDENTIFIER '(' argument_names ')'
                          | explicit_member_expression ('?')? '.' self_expression
                          | explicit_member_expression ('?')? '.' superclass_expression
                          | explicit_member_expression ('?')? '[' subscript ']'
                          | explicit_member_expression '!!'
                          | explicit_member_expression '?:' expression
                          ;



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
case_label: 'case' case_item_list '=>';
case_item_list: case_item(',' case_item)*;
case_item: (pattern | literal) where_clause? | nil_case_item;
default_label: 'default' '=>';

nil_case_item: nil_literal;

where_clause: 'where' where_expression;
where_expression: expression;


control_transfer_statement: break_statement | continue_statement | fallthrough_statement | return_statement | throw_statement;

break_statement: 'break';
continue_statement: 'continue';
fallthrough_statement: FALLTHROUGH | 'fallthrough';
return_statement: 'return' expression?;
throw_statement: 'throw' expression;
yield_statement: 'yield' expression;

assert_statement: 'assert' expression (',' expression)?;

do_statement: 'do' code_block;



// Declarations

declaration: constant_declaration
           | variable_declaration
           | lateinit_var_declaration
           | typealias_declaration
           | function_declaration
           | enum_declaration
           | struct_declaration
           | initializer_declaration
           ;

code_block: '{' statements? '}';

constant_declaration: 'cst' pattern_initializer_list;
pattern_initializer_list: pattern_initializer (',' pattern_initializer)*;
pattern_initializer: simple_pattern_initializer | destruct_pattern_initializer;
simple_pattern_initializer: identifier_pattern (initializer | type_annotation | type_annotation initializer);
destruct_pattern_initializer: tuple_pattern type_annotation? initializer;
initializer: '=' expression;

variable_declaration: 'var' pattern_initializer_list;
lateinit_var_declaration: 'late var' pattern_initializer_list;

typealias_declaration: 'typealias' typealias_name generic_parameter_clause? typealias_assignment;
typealias_name: IDENTIFIER;
typealias_assignment: '=' type;

function_declaration: function_head (extension_type '.')? function_name generic_parameter_clause? function_signature function_body?;
function_head: 'func';
extension_type: type;
function_name: IDENTIFIER;

function_signature: parameter_clause function_result?;
function_result: ':' type;
function_body: code_block;

parameter_clause: '(' ')' | '(' parameter_list ')';
parameter_list: parameter (',' parameter)*;
parameter: parameter_name type_annotation default_argument_clause?;
parameter_name: IDENTIFIER;
default_argument_clause: '=' expression;

enum_declaration: 'enum' enum_name '{' enum_members '}';
enum_members: enum_member+;
enum_member: declaration | enum_case_clause;
enum_case_clause: 'case' enum_case_list;
enum_case_list: enum_case (',' enum_case)*;
enum_case: enum_case_name enum_assignment?;
enum_assignment: '=' raw_value_literal;
raw_value_literal: numeric_literal | boolean_literal;
enum_name: IDENTIFIER;
enum_case_name: IDENTIFIER;

struct_declaration: open? 'struct' struct_name generic_parameter_clause? struct_primary_initializer type_inheritance_clause? struct_body?;
open: 'open';
struct_name: IDENTIFIER;
struct_body: '{' declaration+ '}';

struct_primary_initializer: '(' struct_primary_parameter_list? ')';
struct_primary_parameter_list: struct_primary_parameter (',' struct_primary_parameter)*;
struct_primary_parameter: ('cst'|'var'|) REF? parameter default_argument_clause?;

initializer_declaration: 'init' code_block;



// Patterns

pattern: identifier_pattern type_annotation?
       | wildcard_pattern
       | value_binding_pattern
       | tuple_pattern type_annotation?
       | enum_case_pattern
       | optional_pattern
       | type_casting_pattern
       | member_pattern
       | subscript_pattern
       ;

wildcard_pattern: '_';
identifier_pattern: IDENTIFIER;

value_binding_pattern: 'var' pattern | 'cst' pattern;

tuple_pattern: '(' ')' | '(' tuple_pattern_element (',' tuple_pattern_element)* ')';
tuple_pattern_element: pattern | literal;

enum_case_pattern: type_identifier '.' enum_case_name;

optional_pattern: identifier_pattern '?';

type_casting_pattern: is_pattern | as_pattern;
is_pattern: 'is' type;
as_pattern: 'as' type;

member_pattern: identifier_pattern '.' IDENTIFIER
              | member_pattern '.' IDENTIFIER
              | self_pattern '.' IDENTIFIER
              | super_pattern '.' IDENTIFIER
              ;
subscript_pattern: identifier_pattern '[' subscript ']'
                 | explicit_member_expression '[' subscript ']'
                 | subscript_pattern '[' subscript ']';
subscript: IDENTIFIER | DECIMAL_LITERAL | reverse_subscript | slice_subscript | expression;
reverse_subscript: '^' expression;
slice_subscript: slice_subscript_part '..' slice_subscript_part
               | '..' slice_subscript_part
               | slice_subscript_part '..'
               ;
slice_subscript_part: reverse_subscript | expression;


self_pattern: self_expression;
super_pattern: superclass_expression;

// Generic Parameters and Arguments

generic_parameter_clause: '<' generic_parameter_list '>';
generic_parameter_list: generic_parameter (',' generic_parameter)*;
generic_parameter: type_name
                 | type_name ':' type_identifier;

generic_argument_clause: '<' generic_argument_list '>';
generic_argument_list: generic_argument (',' generic_argument)*;
generic_argument: type;