package matrix.com.util

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
