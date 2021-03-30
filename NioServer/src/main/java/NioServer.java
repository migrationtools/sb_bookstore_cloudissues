
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
public class NioServer {
    public static void main(String[] args) throws IOException {

        // Selector: multiplexor of SelectableChannel objectsss
        Selector selector = Selector.open(); // selector is open here

        // ServerSocketChannel: selectable channel for stream-oriented listening sockets
        ServerSocketChannel nioMessageSocket = ServerSocketChannel.open();
        InetSocketAddress nioMessageAddr = new InetSocketAddress("localhost", 1111);

        // Binds the channel's socket to a local address and configures the socket to listen for connections
        nioMessageSocket.bind(nioMessageAddr);

        // Adjusts this channel's blocking mode.
        nioMessageSocket.configureBlocking(false);

        int ops = nioMessageSocket.validOps();
        SelectionKey selectKy = nioMessageSocket.register(selector, ops, null);

        // Infinite loop..
        // Keep server running
        while (true) {

            log("i'm a server and i'm waiting for new connection and buffer select...");
            // Selects a set of keys whose corresponding channels are ready for I/O operations
            selector.select();

            // token representing the registration of a SelectableChannel with a Selector
            Set<SelectionKey> nioMessageKeys = selector.selectedKeys();
            Iterator<SelectionKey> nioMessageIterator = nioMessageKeys.iterator();

            while (nioMessageIterator.hasNext()) {
                SelectionKey myKey = nioMessageIterator.next();

                // Tests whether this key's channel is ready to accept a new socket connection
                if (myKey.isAcceptable()) {
                    SocketChannel nioMessageClient = nioMessageSocket.accept();

                    // Adjusts this channel's blocking mode to false
                    nioMessageClient.configureBlocking(false);

                    // Operation-set bit for read operations
                    nioMessageClient.register(selector, SelectionKey.OP_READ);
                    log("Connection Accepted: " + nioMessageClient.getLocalAddress() + "\n");

                    // Tests whether this key's channel is ready for reading
                } else if (myKey.isReadable()) {

                    SocketChannel nioMessageClient = (SocketChannel) myKey.channel();
                    ByteBuffer nioMessageBuffer = ByteBuffer.allocate(256);
                    nioMessageClient.read(nioMessageBuffer);
                    String result = new String(nioMessageBuffer.array()).trim();

                    log("Message received: " + result);

                    if (result.equals("nioMessage")) {
                        nioMessageClient.close();
                        log("\nIt's time to close connection as we got last company name 'nioMessage'");
                        log("\nServer will keep running. Try running client again to establish new connection");
                    }
                }
                nioMessageIterator.remove();
            }
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }
}
