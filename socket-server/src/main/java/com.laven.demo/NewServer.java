package com.laven.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NewServer {

    static Selector selector;

    public static void main(String[] args) {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8089));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将连接时间注册到多路复用器上
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) { // 连接事件已就绪
                        handleAccept(key);
                    } else if (key.isReadable()) { // 读取事件已就绪
                        handleRead(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleAccept(SelectionKey key) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap("Hello Client, I'm NIO Server!".getBytes()));
            socketChannel.register(selector, SelectionKey.OP_READ); // 注册读取事件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handleRead(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(24);

        try {
            socketChannel.read(byteBuffer);
            System.out.println("Server Receive MSG:" + new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
