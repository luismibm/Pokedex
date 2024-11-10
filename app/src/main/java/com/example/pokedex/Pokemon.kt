package com.example.pokedex

class Pokemon {

    var id:Int = 0
    var name:String = ""
    var sprite:String = ""
    var weight:Double = 0.0

    override fun toString(): String {
        return name
    }

}