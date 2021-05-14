package org.ironica.shizuku.bridge.tests

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TestAction(
    val id: Int
)
