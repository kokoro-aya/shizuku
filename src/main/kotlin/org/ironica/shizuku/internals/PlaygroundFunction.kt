package org.ironica.shizuku.internals

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PlaygroundFunction(
    val type: PF,
    val self: PFType,
    val ret: PFType,
    val arg1: PFType = PFType.TNone,
    val arg2: PFType = PFType.TNone,
    val arg3: PFType = PFType.TNone,
    val arg4: PFType = PFType.TNone,
    val arg5: PFType = PFType.TNone,
)
