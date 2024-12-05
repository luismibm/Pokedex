package com.example.pokedex

import java.io.Serializable

class Pokemon : Serializable {

    var id:Int = 0
    var name:String = ""
    var sprite:String = ""
    var weight:Double = 0.0
    override fun toString(): String {
        return "Pokemon(id=$id, name='$name', sprite='$sprite', weight=$weight)"
    }


}