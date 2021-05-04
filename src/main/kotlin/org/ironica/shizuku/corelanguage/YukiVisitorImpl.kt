package org.ironica.shizuku.corelanguage

import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import yukiVisitor

class YukiVisitorImpl: yukiVisitor<Any> {
    override fun visit(tree: ParseTree?): Any {
        TODO("Not yet implemented")
    }

    override fun visitChildren(node: RuleNode?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTerminal(node: TerminalNode?): Any {
        TODO("Not yet implemented")
    }

    override fun visitErrorNode(node: ErrorNode?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTop_level(ctx: yukiParser.Top_levelContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitLiteral(ctx: yukiParser.LiteralContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitNumeric_literal(ctx: yukiParser.Numeric_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitBoolean_literal(ctx: yukiParser.Boolean_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitChar_sequence_literal(ctx: yukiParser.Char_sequence_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitNil_literal(ctx: yukiParser.Nil_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitInteger_literal(ctx: yukiParser.Integer_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDouble_literal(ctx: yukiParser.Double_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitString_literal(ctx: yukiParser.String_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitInterpolated_string_literal(ctx: yukiParser.Interpolated_string_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType(ctx: yukiParser.TypeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_annotation(ctx: yukiParser.Type_annotationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_identifier(ctx: yukiParser.Type_identifierContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_name(ctx: yukiParser.Type_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_type(ctx: yukiParser.Tuple_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_type_element(ctx: yukiParser.Tuple_type_elementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitElement_name(ctx: yukiParser.Element_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_type(ctx: yukiParser.Function_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_type_argument_clause(ctx: yukiParser.Function_type_argument_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_type_argument(ctx: yukiParser.Function_type_argumentContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitArgument_label(ctx: yukiParser.Argument_labelContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitArray_type(ctx: yukiParser.Array_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitMap_type(ctx: yukiParser.Map_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitAny_type(ctx: yukiParser.Any_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSelf_type(ctx: yukiParser.Self_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_composition_type(ctx: yukiParser.Prototype_composition_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_inheritance_clause(ctx: yukiParser.Type_inheritance_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExpression(ctx: yukiParser.ExpressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitAssignment_operator(ctx: yukiParser.Assignment_operatorContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitConditional_operator(ctx: yukiParser.Conditional_operatorContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_casting_operator(ctx: yukiParser.Type_casting_operatorContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_list(ctx: yukiParser.Type_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitVariable_expression(ctx: yukiParser.Variable_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitLiteral_expression(ctx: yukiParser.Literal_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitArray_literal(ctx: yukiParser.Array_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitArray_literal_item(ctx: yukiParser.Array_literal_itemContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitMap_literal(ctx: yukiParser.Map_literalContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitMap_literal_item(ctx: yukiParser.Map_literal_itemContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSelf_expression(ctx: yukiParser.Self_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSuperclass_expression(ctx: yukiParser.Superclass_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSuperclass_initialization_expression(ctx: yukiParser.Superclass_initialization_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitInitializer_delegate_expression(ctx: yukiParser.Initializer_delegate_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitClosure_expression(ctx: yukiParser.Closure_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitClosure_parameter_clause(ctx: yukiParser.Closure_parameter_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitClosure_parameter(ctx: yukiParser.Closure_parameterContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitClosure_parameter_name(ctx: yukiParser.Closure_parameter_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitParenthesized_expression(ctx: yukiParser.Parenthesized_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_expression(ctx: yukiParser.Tuple_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_elements(ctx: yukiParser.Tuple_elementsContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_element(ctx: yukiParser.Tuple_elementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitWildcard_expression(ctx: yukiParser.Wildcard_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSpread_expression(ctx: yukiParser.Spread_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_call_expression(ctx: yukiParser.Function_call_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_call_argument_clause(ctx: yukiParser.Function_call_argument_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_call_argument_list(ctx: yukiParser.Function_call_argument_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_call_argument(ctx: yukiParser.Function_call_argumentContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitArgument_names(ctx: yukiParser.Argument_namesContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitArgument_name(ctx: yukiParser.Argument_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitOverhang_arguments(ctx: yukiParser.Overhang_argumentsContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExplicit_member_callee(ctx: yukiParser.Explicit_member_calleeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExplicit_member_expression(ctx: yukiParser.Explicit_member_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStatic_member_expression(ctx: yukiParser.Static_member_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_member_expression(ctx: yukiParser.Enum_member_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_subscript_expression(ctx: yukiParser.Type_subscript_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStatements(ctx: yukiParser.StatementsContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStatement(ctx: yukiParser.StatementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitLoop_statement(ctx: yukiParser.Loop_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFor_in_statement(ctx: yukiParser.For_in_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitWhile_statement(ctx: yukiParser.While_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitCondition_list(ctx: yukiParser.Condition_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitCondition(ctx: yukiParser.ConditionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitOptional_binding_condition(ctx: yukiParser.Optional_binding_conditionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitRepeat_while_statement(ctx: yukiParser.Repeat_while_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitBranch_statement(ctx: yukiParser.Branch_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitIf_statement(ctx: yukiParser.If_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitElse_clause(ctx: yukiParser.Else_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSwitch_statement(ctx: yukiParser.Switch_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSwitch_cases(ctx: yukiParser.Switch_casesContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSwitch_case(ctx: yukiParser.Switch_caseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitCase_label(ctx: yukiParser.Case_labelContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitCase_item_list(ctx: yukiParser.Case_item_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitCase_item(ctx: yukiParser.Case_itemContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDefault_label(ctx: yukiParser.Default_labelContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitNil_case_item(ctx: yukiParser.Nil_case_itemContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitWhere_clause(ctx: yukiParser.Where_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitWhere_expression(ctx: yukiParser.Where_expressionContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitControl_transfer_statement(ctx: yukiParser.Control_transfer_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitBreak_statement(ctx: yukiParser.Break_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitContinue_statement(ctx: yukiParser.Continue_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFallthrough_statement(ctx: yukiParser.Fallthrough_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitReturn_statement(ctx: yukiParser.Return_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitThrow_statement(ctx: yukiParser.Throw_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitYield_statement(ctx: yukiParser.Yield_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitAssert_statement(ctx: yukiParser.Assert_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDo_statement(ctx: yukiParser.Do_statementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDeclaration(ctx: yukiParser.DeclarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitCode_block(ctx: yukiParser.Code_blockContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitConstant_declaration(ctx: yukiParser.Constant_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPattern_initializer_list(ctx: yukiParser.Pattern_initializer_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPattern_initializer(ctx: yukiParser.Pattern_initializerContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSimple_pattern_initializer(ctx: yukiParser.Simple_pattern_initializerContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDestruct_pattern_initializer(ctx: yukiParser.Destruct_pattern_initializerContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitInitializer(ctx: yukiParser.InitializerContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitVariable_declaration(ctx: yukiParser.Variable_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitComputed_var_declaration(ctx: yukiParser.Computed_var_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGetter_block(ctx: yukiParser.Getter_blockContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGetter_setter_block(ctx: yukiParser.Getter_setter_blockContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGetter_clause(ctx: yukiParser.Getter_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSetter_clause(ctx: yukiParser.Setter_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGet_set_block(ctx: yukiParser.Get_set_blockContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSetter_name(ctx: yukiParser.Setter_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitConstant_name(ctx: yukiParser.Constant_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitVariable_name(ctx: yukiParser.Variable_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTypealias_declaration(ctx: yukiParser.Typealias_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTypealias_name(ctx: yukiParser.Typealias_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTypealias_assignment(ctx: yukiParser.Typealias_assignmentContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_declaration(ctx: yukiParser.Function_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_head(ctx: yukiParser.Function_headContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExtension_func_type(ctx: yukiParser.Extension_func_typeContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_name(ctx: yukiParser.Function_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_signature(ctx: yukiParser.Function_signatureContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_result(ctx: yukiParser.Function_resultContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitFunction_body(ctx: yukiParser.Function_bodyContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitParameter_clause(ctx: yukiParser.Parameter_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitParameter_list(ctx: yukiParser.Parameter_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitParameter(ctx: yukiParser.ParameterContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitNo_default_parameter(ctx: yukiParser.No_default_parameterContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDefault_name(ctx: yukiParser.Default_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitParameter_name(ctx: yukiParser.Parameter_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitDefault_argument_clause(ctx: yukiParser.Default_argument_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_declaration(ctx: yukiParser.Enum_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_members(ctx: yukiParser.Enum_membersContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_member(ctx: yukiParser.Enum_memberContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_case_clause(ctx: yukiParser.Enum_case_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_case_list(ctx: yukiParser.Enum_case_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_case(ctx: yukiParser.Enum_caseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_assignment(ctx: yukiParser.Enum_assignmentContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_name(ctx: yukiParser.Enum_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_case_name(ctx: yukiParser.Enum_case_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_declaration(ctx: yukiParser.Struct_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_modifiers(ctx: yukiParser.Struct_modifiersContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_modifier(ctx: yukiParser.Struct_modifierContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_name(ctx: yukiParser.Struct_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_body(ctx: yukiParser.Struct_bodyContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_primary_initializer(ctx: yukiParser.Struct_primary_initializerContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_primary_parameter_list(ctx: yukiParser.Struct_primary_parameter_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_primary_parameter(ctx: yukiParser.Struct_primary_parameterContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitInitializer_declaration(ctx: yukiParser.Initializer_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_secondary_initializer(ctx: yukiParser.Struct_secondary_initializerContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_secondary_parameter_list(ctx: yukiParser.Struct_secondary_parameter_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_property_declaration(ctx: yukiParser.Type_property_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_enum_body_member(ctx: yukiParser.Struct_enum_body_memberContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStruct_dangling_constant_declaration(ctx: yukiParser.Struct_dangling_constant_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_declaration(ctx: yukiParser.Prototype_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_name(ctx: yukiParser.Prototype_nameContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_body(ctx: yukiParser.Prototype_bodyContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_member_declaration(ctx: yukiParser.Prototype_member_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_property_declaration(ctx: yukiParser.Prototype_property_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_computed_property_declaration(ctx: yukiParser.Prototype_computed_property_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_method_declaration(ctx: yukiParser.Prototype_method_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_property_implementation(ctx: yukiParser.Prototype_property_implementationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_computed_property_implementation(ctx: yukiParser.Prototype_computed_property_implementationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_method_implementation(ctx: yukiParser.Prototype_method_implementationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_subscript_declaration(ctx: yukiParser.Prototype_subscript_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_subscript_implementation(ctx: yukiParser.Prototype_subscript_implementationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPrototype_associated_type_declaration(ctx: yukiParser.Prototype_associated_type_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExtension_declaration(ctx: yukiParser.Extension_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExtension_body(ctx: yukiParser.Extension_bodyContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitExtension_member(ctx: yukiParser.Extension_memberContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript_declaration(ctx: yukiParser.Subscript_declarationContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript_head(ctx: yukiParser.Subscript_headContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript_result(ctx: yukiParser.Subscript_resultContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitPattern(ctx: yukiParser.PatternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitWildcard_pattern(ctx: yukiParser.Wildcard_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitIdentifier_pattern(ctx: yukiParser.Identifier_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitValue_binding_pattern(ctx: yukiParser.Value_binding_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_pattern(ctx: yukiParser.Tuple_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitTuple_pattern_element(ctx: yukiParser.Tuple_pattern_elementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitEnum_case_pattern(ctx: yukiParser.Enum_case_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitOptional_pattern(ctx: yukiParser.Optional_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitType_casting_pattern(ctx: yukiParser.Type_casting_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitIs_pattern(ctx: yukiParser.Is_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitAs_pattern(ctx: yukiParser.As_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitMember_pattern(ctx: yukiParser.Member_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitStatic_member_pattern(ctx: yukiParser.Static_member_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript_pattern(ctx: yukiParser.Subscript_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript(ctx: yukiParser.SubscriptContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitReverse_subscript(ctx: yukiParser.Reverse_subscriptContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSlice_subscript(ctx: yukiParser.Slice_subscriptContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSlice_subscript_part(ctx: yukiParser.Slice_subscript_partContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript_list(ctx: yukiParser.Subscript_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSubscript_list_element(ctx: yukiParser.Subscript_list_elementContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSelf_pattern(ctx: yukiParser.Self_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitSuper_pattern(ctx: yukiParser.Super_patternContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGeneric_parameter_clause(ctx: yukiParser.Generic_parameter_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGeneric_parameter_list(ctx: yukiParser.Generic_parameter_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGeneric_parameter(ctx: yukiParser.Generic_parameterContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGeneric_argument_clause(ctx: yukiParser.Generic_argument_clauseContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGeneric_argument_list(ctx: yukiParser.Generic_argument_listContext?): Any {
        TODO("Not yet implemented")
    }

    override fun visitGeneric_argument(ctx: yukiParser.Generic_argumentContext?): Any {
        TODO("Not yet implemented")
    }
}