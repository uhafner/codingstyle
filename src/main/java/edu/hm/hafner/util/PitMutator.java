package edu.hm.hafner.util;

/**
 * Represents the mutators available in PIT.
 * This enum maps each mutator name to its fully qualified class name.
 */
public enum PitMutator {
    CONDITIONALS_BOUNDARY("org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator"),
    CONSTRUCTOR_CALLS("org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallMutator"),
    INCREMENTS("org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator"),
    INLINE_CONSTS("org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator"),
    INVERT_NEGS("org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator"),
    MATH("org.pitest.mutationtest.engine.gregor.mutators.MathMutator"),
    VOID_METHOD_CALLS("org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator"),
    NEGATE_CONDITIONALS("org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator"),
    NON_VOID_METHOD_CALLS("org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallMutator"),
    FALSE_RETURNS("org.pitest.mutationtest.engine.gregor.mutators.returns.BooleanFalseReturnValsMutator"),
    TRUE_RETURNS("org.pitest.mutationtest.engine.gregor.mutators.returns.BooleanTrueReturnValsMutator"),
    EMPTY_RETURNS("org.pitest.mutationtest.engine.gregor.mutators.returns.EmptyObjectReturnValsMutator"),
    NULL_RETURNS("org.pitest.mutationtest.engine.gregor.mutators.returns.NullReturnValsMutator"),
    PRIMITIVE_RETURNS("org.pitest.mutationtest.engine.gregor.mutators.returns.PrimitiveReturnsMutator"),
    EXPERIMENTAL_ARGUMENT_PROPAGATION("org.pitest.mutationtest.engine.gregor.mutators.experimental.ArgumentPropagationMutator"),
    EXPERIMENTAL_BIG_DECIMAL("org.pitest.mutationtest.engine.gregor.mutators.experimental.BigDecimalMutator"),
    EXPERIMENTAL_BIG_INTEGER("org.pitest.mutationtest.engine.gregor.mutators.experimental.BigIntegerMutator"),
    EXPERIMENTAL_MEMBER_VARIABLE("org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator"),
    EXPERIMENTAL_NAKED_RECEIVER("org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator"),
    REMOVE_INCREMENTS("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator"),
    REMOVE_CONDITIONALS_EQUAL_IF("org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator_EQUAL_IF"),
    REMOVE_CONDITIONALS_EQUAL_ELSE("org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator_EQUAL_ELSE"),
    REMOVE_CONDITIONALS_ORDER_IF("org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator_ORDER_IF"),
    REMOVE_CONDITIONALS_ORDER_ELSE("org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator_ORDER_ELSE"),
    EXPERIMENTAL_SWITCH("org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_0("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_0"), //todo maybe find a better solution for n cases
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_1("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_1"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_2("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_2"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_3("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_3"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_4("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_4"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_5("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_5"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_6("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_6"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_7("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_7"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_8("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_8"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_9("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_9"),
    EXPERIMENTAL_REMOVE_SWITCH_MUTATOR_10("org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator_10"),
    NONE("");

    private final String fqcn;

    /**
     * Constructor to associate a mutator constant with its fqcn.
     *
     * @param fqcn the fully qualified class name of the mutator
     */
    PitMutator(final String fqcn) {
        this.fqcn = fqcn;
    }

    public String getFqcn() {
        return fqcn;
    }
}
