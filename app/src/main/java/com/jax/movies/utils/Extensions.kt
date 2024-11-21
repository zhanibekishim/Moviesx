package com.jax.movies.utils

import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.home.Movie

fun ActorType.toPlayRole(): String {
    return when (this) {
        ActorType.WRITE -> "Играет писателя"
        ActorType.OPERATOR -> "Играет оператора"
        ActorType.EDITOR -> "Играет эдитора"
        ActorType.COMPOSER -> "Играет компоузера"
        ActorType.PRODUCER_USSR -> "Играет  юсср"
        ActorType.TRANSLATOR -> "Играет переводчика"
        ActorType.DIRECTOR -> "Играет директора"
        ActorType.DESIGN ->  "Играет дизайнера"
        ActorType.PRODUCER -> "Играет продюсера"
        ActorType.ACTOR -> "Играет саму себя"
        ActorType.VOICE_DIRECTOR -> "Играет войс продюсера"
        ActorType.UNKNOWN -> "Неизвестный роль"
    }
}

fun List<Movie>.checkRealCount(): Int {
    return this.filter {
        it.id.toInt() != -1
    }.size
}