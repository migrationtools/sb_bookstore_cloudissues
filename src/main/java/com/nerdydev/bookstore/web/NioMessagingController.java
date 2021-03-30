package com.nerdydev.bookstore.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@RestController
public class NioMessagingController {

    @PostMapping("/niomessage")
    public String sendNioMessagages(@RequestParam String companyName) throws IOException {
        InetSocketAddress nioMessageAddr= new InetSocketAddress("localhost", 1111);
        SocketChannel nioMessageClient = SocketChannel.open(nioMessageAddr);
        byte[] message = new String(companyName).getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(message);
        nioMessageClient.write(buffer);

        return "message sent successfully" + " - " + companyName;
    }
    
}
