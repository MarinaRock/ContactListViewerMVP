package ru.marina.contactlistviewermvp.ui.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.marina.contactlistviewermvp.R
import ru.marina.contactlistviewermvp.network.NoNetworkException
import ru.marina.contactlistviewermvp.network.ServerException
import ru.marina.contactlistviewermvp.ui.ErrorEvent

fun Fragment.getMsgFromError(error: ErrorEvent): String {
    return error.msg ?: error.exception.let {
        when (it) {
            is ServerException -> it.message ?: getString(R.string.error_server)
            is NoNetworkException -> getString(R.string.error_network)
            else -> getString(R.string.error_unknown)
        }
    }
}

fun Fragment.showSnackBar(msg: String) {
    view?.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_LONG).show()
    }
}
