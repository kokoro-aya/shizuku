package org.ironica.shizuku.internals

enum class PF { Method, Property, GlobalFunction }
enum class PFType {
    TWorld,
    TPlayer, TSpecialist,
    TItem, TPortal, TStair,
    TBlock,
    TCoordinate,
    TInt, TBool, TString,
    TColor,
    TNone,
}