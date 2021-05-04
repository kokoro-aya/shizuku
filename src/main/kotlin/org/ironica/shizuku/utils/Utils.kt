package org.ironica.shizuku.utils

fun <A, B, C, D>zipThree2DArrayToObjArrayWith(
    first: Array<Array<A>>,
    second: Array<Array<B>>,
    third: Array<Array<C>>,
    with: (A, B, C) -> D
): List<List<D>> {
    val x = first.size
    val y = first[0].size
    assert (second.size == x && third.size == x
            && second[0].size == y && third[0].size == y
    ) { "three arrays should have same dimensions" }
    return first.mapIndexed { i1, p ->
        p.mapIndexed { i2, a ->
            with(a, second[i1][i2], third[i1][i2])
        }
    }
}