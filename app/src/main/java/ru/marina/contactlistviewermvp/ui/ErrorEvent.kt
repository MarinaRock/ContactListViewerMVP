package ru.marina.contactlistviewermvp.ui

class ErrorEvent() {

    var msg: String? = null
        private set
    var exception: Throwable? = null
        private set

    constructor(msg: String) : this() {
        this.msg = msg
    }

    constructor(exception: Throwable) : this() {
        this.exception = exception
    }
}