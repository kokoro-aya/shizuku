
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

BINARY_LITERAL: '0b' BINARY_DIGIT (BINARY_DIGIT | '_')*;
fragment BINARY_DIGIT: '0' | '1';

DECIMAL_LITERAL: DECIMAL_DIGIT (DECIMAL_DIGIT | '_')*;
fragment DECIMAL_DIGIT: '0' .. '9';

HEXADECIMAL_LITERAL: '0x' HEXADECIMAL_DIGIT (HEXADECIMAL_DIGIT | '_')*;
fragment HEXADECIMAL_DIGIT: DECIMAL_DIGIT | [A-F] | [a-f];

OCTAL_LITERAL: '0o' OCTAL_DIGIT (OCTAL_DIGIT | '_')*;
fragment OCTAL_DIGIT: '0' .. '7';

MULTILINE_STRING_LITERAL: '"""' (ESC | .)*? '"""';

STATIC_STRING_LITERAL: '"' (ESC | .)*? '"';
fragment ESC: '\\' | '\\\\';

STRING_HEAD: '""' (ESC|~[\\(){}])*? '\\({';
STRING_INTERM: '})' (ESC|~[\\(){}])*? '\\({';
STRING_END: '})' (ESC|~[\\(){}])*? '""';
// cannot have only one delimiter otherwise it can't be distinguished with static string literal

CHARACTER_LITERAL: '\'' (CHAR | UNICODE_POINT) '\'';
fragment CHAR: ~['"\\EOF\n];
fragment UNICODE_POINT: UNICODE_PART /* UNICODE_PART? */;
fragment UNICODE_PART: '\\u{' HEXADECIMAL_DIGIT+ '}';

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
IDT: '~=';
NIDT: '!~=';
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
numeric_literal: (ADD | SUB)? (double_literal | integer_literal);
boolean_literal: 'true' | 'false';
char_sequence_literal: string_literal | CHARACTER_LITERAL;
nil_literal: 'nil';

integer_literal: DECIMAL_LITERAL | HEXADECIMAL_LITERAL | OCTAL_LITERAL | BINARY_LITERAL;
double_literal: DECIMAL_LITERAL '.' DECIMAL_LITERAL
              ; // illegal: 0. or .5
string_literal: MULTILINE_STRING_LITERAL | STATIC_STRING_LITERAL | interpolated_string_literal;
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
    | type ('.' type)+ // nested_type, only allow to have left and right as simple struct/enum type
    | prototype_composition_type
    ;

type_annotation: ':' type;

type_identifier: type_name generic_argument_clause?
               | type_name generic_argument_clause? '.' type_identifier;

type_name: IDENTIFIER; // T_I

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

prototype_composition_type: IDENTIFIER ('&' IDENTIFIER)*; // T_I

type_inheritance_clause: MIXINS type_identifier (',' type_identifier)*;



// Expressions

expression:
          // Binary Expressions
            NOT expression
          | (ADD | SUB) expression
          | expression op=(MUL | DIV | MOD) expression
          | expression op=(ADD | SUB) expression
          | expression op=('<<' | '>>') expression
          | <assoc=right> expression EXP expression
          | expression APPEND expression
          | expression op=(THROUGH | UNTIL | DOWNTO | DOWNTHROUGH) expression (STEP expression)?
          | expression type_casting_operator type_list
          | expression op=(GT | LT | GEQ | LEQ) expression
          | expression op=AND expression
          | expression op=(OR | XOR) expression
          | expression op=(NEQ | EQ | IDT | NIDT) expression
          | expression conditional_operator expression
          | pattern assignment_operator expression
          | pattern op=(ADDEQ | SUBEQ | MULEQ | DIVEQ | MODEQ) expression
          // Postfix expressions aka explicit member expressions
          | static_member_expression
          | explicit_member_expression
          | enum_member_expression
          | type_subscript_expression
          | expression '!!'
          // Primary Expressions
          | superclass_initialization_expression // only allowed in struct body
          | initializer_delegate_expression // only allowed in struct body
          | parenthesized_expression
          | closure_expression
          | function_call_expression
       /* | struct_construct_expression */
          | tuple_expression
          | variable_expression
          | literal_expression
          | self_expression
          | '$' DECIMAL_LITERAL // in closure
          | wildcard_expression
          | spread_expression
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
superclass_initialization_expression: 'super' '.' 'init' function_call_argument_clause;

initializer_delegate_expression: 'self' '.' 'init' function_call_argument_clause;

closure_expression: '{' closure_parameter_clause type_annotation? ARROW statements '}';
closure_parameter_clause: '(' ')' | '(' closure_parameter (',' closure_parameter)* ')';
closure_parameter: closure_parameter_name type_annotation;
closure_parameter_name: IDENTIFIER;

parenthesized_expression: '(' expression ')';

tuple_expression: '(' ')' | '(' tuple_elements ')';
tuple_elements: tuple_element (',' tuple_element)*;
tuple_element: expression | IDENTIFIER ':' expression;

wildcard_expression: '_';

spread_expression: '...' expression;


function_call_expression: function_name generic_argument_clause? function_call_argument_clause // serves also struct initializer
                                                     // no default name (without identifier) allowed if is initializing a struct
                        | function_name generic_argument_clause? overhang_arguments? code_block
                        | function_name generic_argument_clause? overhang_arguments? closure_expression
                        ;
function_call_argument_clause: '(' ')' /*| '(' function_call_by_name ')'*/ | '(' function_call_argument_list ')';
//function_call_by_name: static_member_expression | IDENTIFIER ':' static_member_expression;
function_call_argument_list: function_call_argument (',' function_call_argument)*;
function_call_argument: IDENTIFIER ':' REF? expression | REF? expression;

/*
struct_construct_expression: struct_name generic_argument_clause? struct_construct_argument_clause;
struct_construct_argument_clause: '(' struct_construct_argument_list? ')';
struct_construct_argument_list: struct_construct_argument (',' struct_construct_argument)*;
struct_construct_argument: IDENTIFIER ':' REF? expression; */

argument_names: argument_name+;
argument_name: IDENTIFIER ':';
overhang_arguments: '(' argument_names ')';

explicit_member_callee: literal_expression
                      | variable_expression
                      | self_expression
                      | superclass_expression
                      | static_member_expression
                      | enum_member_expression
                      | parenthesized_expression
                      | tuple_expression;
explicit_member_expression: explicit_member_callee ('?' | '!!')? '.' function_call_expression
                          | explicit_member_callee ('?' | '!!')? '.' '$' DECIMAL_LITERAL
                          | explicit_member_callee ('?' | '!!')? '.' IDENTIFIER
                          | explicit_member_callee ('?' | '!!')? '.' IDENTIFIER '(' argument_names ')'
                          | explicit_member_callee ('?' | '!!')? '.' self_expression
                          | explicit_member_callee ('?' | '!!')? '.' superclass_expression
                          | explicit_member_callee ('?' | '!!')? '[' subscript ']'
                          | explicit_member_callee '?:' expression
                          | explicit_member_expression ('?' | '!!')? '.' function_call_expression
                          | explicit_member_expression ('?' | '!!')? '.' '$' DECIMAL_LITERAL
                          | explicit_member_expression ('?' | '!!')? '.' IDENTIFIER
                          | explicit_member_expression ('?' | '!!')? '.' IDENTIFIER '(' argument_names ')'
                          | explicit_member_expression ('?' | '!!')? '.' self_expression
                          | explicit_member_expression ('?' | '!!')? '.' superclass_expression
                          | explicit_member_expression ('?' | '!!')? '[' subscript ']'
                          | explicit_member_expression '?:' expression
                          ;
static_member_expression: type? '::' IDENTIFIER '(' argument_names ')'
                        | type? '::' function_call_expression
                        | type? '::' explicit_member_expression
                        | type? '::' IDENTIFIER // if no type then point to current struct, not to confront with function pointer
                        ;
enum_member_expression: enum_name '.' enum_case_name;

type_subscript_expression: type_name '[' subscript ']';

// Statements

statements: statement+;

statement: declaration ';'?
         | expression ';'?
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
condition: optional_binding_condition | expression;
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
case_item: (pattern | literal) where_clause? | nil_case_item; // only accepts value-binding-pattern or tuple-pattern or enum_case_pattern
                                                              // if it's tuple-pattern, element must be either identifier pattern or literal
                                                              // the case of only identifiers replace the default case
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
        /* | lateinit_var_declaration */
           | computed_var_declaration
           | typealias_declaration
           | function_declaration
           | enum_declaration
           | struct_declaration
           | prototype_declaration
           | extension_declaration
           | subscript_declaration // only in enum body or struct body
           | initializer_declaration
           ;

code_block: '{' statements? '}';

constant_declaration: 'cst' constant_name type_annotation? initializer (getter_block | code_block)?
                    | 'cst' pattern_initializer_list
                    ;
pattern_initializer_list: pattern_initializer (',' pattern_initializer)*;
pattern_initializer: simple_pattern_initializer | destruct_pattern_initializer;
simple_pattern_initializer: identifier_pattern (initializer | type_annotation | type_annotation initializer);
destruct_pattern_initializer: tuple_pattern type_annotation? initializer;
initializer: '=' expression;

variable_declaration: 'var' variable_name
                      (type_annotation | initializer | type_annotation initializer)
                      (getter_setter_block | get_set_block | code_block)?
                    | 'var' pattern_initializer_list // must be [identifier_pattern]
                    ;
                    // Should not have getter_setter_block if out of struct
/* lateinit_var_declaration: 'late var' pattern_initializer_list
                        | 'late var' variable_name type_annotation (getter_setter_block | get_set_block | code_block)?
                        ; */
computed_var_declaration: 'comp var' variable_name type_annotation (getter_setter_block | code_block);

getter_block: '{' getter_clause '}';
getter_setter_block: '{' getter_clause ','? setter_clause? '}'
                   | '{' setter_clause ','? getter_clause '}'
                   ;
getter_clause: 'get' code_block?;
setter_clause: 'set' setter_name? code_block;
get_set_block: '{' 'get' ','? 'set'? '}'
             | '{' 'set' ','? 'get' '}'
             ;
setter_name: '(' IDENTIFIER ')';

constant_name: IDENTIFIER;
variable_name: IDENTIFIER;

typealias_declaration: 'typealias' typealias_name generic_parameter_clause? typealias_assignment;
typealias_name: IDENTIFIER; // T_I
typealias_assignment: '=' type;

function_declaration: function_head (extension_func_type '.')? function_name generic_parameter_clause? function_signature function_body?;
function_head: 'operator'? 'func';
extension_func_type: type;
function_name: IDENTIFIER;

function_signature: parameter_clause function_result?;
function_result: ':' type;
function_body: code_block;

parameter_clause: '(' ')' | '(' parameter_list ')';
parameter_list: parameter (',' parameter)*;
parameter: default_name? REF? parameter_name type_annotation default_argument_clause?;
no_default_parameter: REF? parameter_name type_annotation default_argument_clause?;
default_name: '_';
parameter_name: IDENTIFIER;
default_argument_clause: '=' expression;

enum_declaration: 'enum' enum_name type_inheritance_clause? '{' enum_members '}'; // only allowed to inherit Int, Double, Bool, String and Character
                                                                                  // need to rethink how inheritance is represented
enum_members: enum_member+;
enum_member: struct_enum_body_member | enum_case_clause;
enum_case_clause: 'case' enum_case_list;
enum_case_list: enum_case (',' enum_case)* ','?;
enum_case: enum_case_name enum_assignment?;
enum_assignment: '=' literal;
enum_name: IDENTIFIER; // T_I
enum_case_name: IDENTIFIER;

struct_declaration: struct_modifiers? 'struct' struct_name generic_parameter_clause? struct_primary_initializer? type_inheritance_clause? struct_body?;
struct_modifiers: struct_modifier+; // only two is allowed
struct_modifier: 'open' | 'partial';

struct_name: IDENTIFIER; // T_I
struct_body: '{' struct_enum_body_member+ '}';

struct_primary_initializer: '(' struct_primary_parameter_list? ')';
struct_primary_parameter_list: struct_primary_parameter (',' struct_primary_parameter)*;
struct_primary_parameter: ('cst'|'var')? no_default_parameter;

initializer_declaration: 'init' struct_secondary_initializer? code_block;
struct_secondary_initializer: '(' struct_secondary_parameter_list? ')';
struct_secondary_parameter_list: no_default_parameter (',' no_default_parameter)*;

type_property_declaration: 'static' constant_declaration // static members
                         | 'static' variable_declaration
                         | 'static' computed_var_declaration
                         | 'static' function_declaration
                         | 'static' subscript_declaration
                         ;
struct_enum_body_member: declaration | type_property_declaration | struct_dangling_constant_declaration;
struct_dangling_constant_declaration: 'cst' constant_name type_annotation (getter_block | code_block);

prototype_declaration: 'prototype' prototype_name prototype_body;
prototype_name: IDENTIFIER; // T_I
prototype_body: '{' prototype_member_declaration+ '}';
prototype_member_declaration: prototype_property_declaration
                            | prototype_computed_property_declaration
                            | prototype_method_declaration
                            | prototype_subscript_declaration
                            | prototype_associated_type_declaration
                            ;
prototype_property_declaration: 'static'? 'var' variable_name type_annotation prototype_property_implementation;
prototype_computed_property_declaration: 'static'? 'comp var' variable_name type_annotation prototype_computed_property_implementation;
prototype_method_declaration: 'static'? function_head function_name generic_parameter_clause? function_signature prototype_method_implementation?;
prototype_property_implementation: 'internal'? 'default' initializer (getter_setter_block | get_set_block)? // default stored property
                                 | 'internal'? 'default' (getter_setter_block | get_set_block | code_block) // default computed property
                                 | 'internal'? get_set_block?; // can be both
prototype_computed_property_implementation: 'internal'? 'default' getter_setter_block | 'internal'? get_set_block;
prototype_method_implementation: 'internal' | 'default' function_body | 'internal' 'default' function_body;

prototype_subscript_declaration: subscript_head subscript_result prototype_subscript_implementation;
prototype_subscript_implementation: 'internal'
                                  | 'default' (getter_setter_block | get_set_block | code_block)
                                  | get_set_block;

prototype_associated_type_declaration: 'associated' typealias_name type_inheritance_clause? typealias_assignment?;

extension_declaration: 'extension' type type_inheritance_clause
                     | 'extension' type type_inheritance_clause? extension_body;
extension_body: '{' extension_member+ '}';
extension_member: 'static'? computed_var_declaration
                | 'static'? variable_declaration
                | 'static'? function_declaration
                | 'static'? subscript_declaration
                | typealias_declaration
                ;

subscript_declaration: subscript_head subscript_result (getter_setter_block /*| get_set_block*/ | code_block);
subscript_head: 'subscript' generic_parameter_clause? parameter_clause;
subscript_result: ':' type;

// Patterns

pattern: identifier_pattern type_annotation?
       | wildcard_pattern
       | value_binding_pattern
       | tuple_pattern type_annotation?
       | enum_case_pattern
       | optional_pattern
       | type_casting_pattern
       | member_pattern
       | static_member_pattern
       | subscript_pattern
       | self_pattern // only used in enum case
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

member_pattern: identifier_pattern ('?' | '!!')? '.' IDENTIFIER
              | member_pattern ('?' | '!!')? '.' IDENTIFIER
              | self_pattern '.' IDENTIFIER
              | super_pattern '.' IDENTIFIER
              | subscript_pattern ('?' | '!!')? '.' IDENTIFIER
              ;
static_member_pattern: type? '::' IDENTIFIER;
subscript_pattern: identifier_pattern ('?' | '!!')? '[' subscript ']'
                 | explicit_member_expression ('?' | '!!')? '[' subscript ']'
              /* | subscript_pattern ('?' | '!!')? '[' subscript ']' */;
subscript: IDENTIFIER | literal | reverse_subscript | slice_subscript | expression
         | subscript_list
         ;
reverse_subscript: '^' expression;
slice_subscript: slice_subscript_part '..' slice_subscript_part
               | '..' slice_subscript_part
               | slice_subscript_part '..'
               ;
slice_subscript_part: reverse_subscript | expression;

subscript_list: subscript_list_element (',' subscript_list_element)+;
subscript_list_element: IDENTIFIER | literal | expression;

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