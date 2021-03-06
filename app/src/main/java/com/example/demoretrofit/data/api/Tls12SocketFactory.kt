package com.example.demoretrofit.data.api

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

class Tls12SocketFactory(var base: SSLSocketFactory) : SSLSocketFactory() {

    private var TLS_V12_ONLY = arrayOf("TLSv1.2")
    internal var delegate: SSLSocketFactory? = null

//    public Tls12SocketFactory(SSLSocketFactory base) {
//        this.delegate = base;
//    }

    init {
        this.delegate = base
    }

    override fun getDefaultCipherSuites(): Array<String> {
        return delegate!!.defaultCipherSuites
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate!!.supportedCipherSuites
    }

    @Throws(IOException::class)
    override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket? {
        return patch(delegate!!.createSocket(s, host, port, autoClose))
    }

    @Throws(IOException::class)
    override fun createSocket(host: String, port: Int): Socket? {
        return patch(delegate!!.createSocket(host, port))
    }

    @Throws(IOException::class)
    override fun createSocket(
        host: String,
        port: Int,
        localHost: InetAddress,
        localPort: Int
    ): Socket? {
        return patch(delegate!!.createSocket(host, port, localHost, localPort))
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket? {
        return patch(delegate!!.createSocket(host, port))
    }

    @Throws(IOException::class)
    override fun createSocket(
        address: InetAddress,
        port: Int,
        localAddress: InetAddress,
        localPort: Int
    ): Socket? {
        return patch(delegate!!.createSocket(address, port, localAddress, localPort))
    }

    private fun patch(s: Socket): Socket {
        if (s is SSLSocket) {
            s.enabledProtocols = TLS_V12_ONLY
        }
        return s
    }
}
