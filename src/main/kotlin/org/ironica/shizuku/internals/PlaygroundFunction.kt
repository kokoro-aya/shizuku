package org.ironica.shizuku.internals

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PlaygroundFunction(
    val type: PF,
    val self: Array<PFType>,
    val ret: PFType,
    val arg1: Array<PFType> = [PFType.TNone],
    val arg2: Array<PFType> = [PFType.TNone],
    val arg3: Array<PFType> = [PFType.TNone],
    val arg4: Array<PFType> = [PFType.TNone],
    val arg5: Array<PFType> = [PFType.TNone],
)
