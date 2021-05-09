package org.ironica.shizuku.utils

fun <A, B, C, D, E, F, G, H, I, J>zipNine2DArrayToObjArrayWith(
    first: Array<Array<A>>,
    second: Array<Array<B>>,
    third: Array<Array<C>>,
    forth: Array<Array<D>>,
    fifth: Array<Array<E>>,
    sixth: Array<Array<F>>,
    seventh: Array<Array<G>>,
    eighth: Array<Array<H>>,
    ninth: Array<Array<I>>,
    with: (A, B, C, D, E, F, G, H, I) -> J
): List<List<J>> {
    val x = first.size
    val y = first[0].size
    assert (second.size == x && third.size == x && forth.size == x && fifth.size == x
            && sixth.size == x && seventh.size == x && eighth.size == x && ninth.size == x
            && second[0].size == y && third[0].size == y && forth[0].size == y && fifth[0].size == y
            && sixth[0].size == y && seventh[0].size == y && eighth[0].size == y && ninth[0].size == y
    ) { "nine arrays should have same dimensions" }
    return first.mapIndexed { i1, p ->
        p.mapIndexed { i2, a ->
            with(a, second[i1][i2], third[i1][i2], forth[i1][i2], fifth[i1][i2],
                sixth[i1][i2], seventh[i1][i2], eighth[i1][i2], ninth[i1][i2]
                )
        }
    }
}