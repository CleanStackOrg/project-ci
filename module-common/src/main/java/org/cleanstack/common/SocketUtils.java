package org.cleanstack.common;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;

import javax.net.ServerSocketFactory;

public class SocketUtils {
    public static final int PORT_RANGE_MAX = 65535;
    private static final Random random = new Random(System.currentTimeMillis());

    public static int findAvailableTcpPort(int minPort) {
	return SocketType.TCP.findAvailablePort(minPort, PORT_RANGE_MAX);
    }

    private enum SocketType {

	TCP {
	    @Override
	    protected boolean isPortAvailable(int port) {
		try {
		    ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1,
		            InetAddress.getByName("localhost"));
		    serverSocket.close();
		    return true;
		} catch (Exception ex) {
		    return false;
		}
	    }
	};

	protected abstract boolean isPortAvailable(int port);

	private int findRandomPort(int minPort, int maxPort) {
	    int portRange = maxPort - minPort;
	    return minPort + random.nextInt(portRange + 1);
	}

	int findAvailablePort(int minPort, int maxPort) {
	    // TODO checks

	    int portRange = maxPort - minPort;
	    int candidatePort;
	    int searchCounter = 0;
	    do {
		if (++searchCounter > portRange) {
		    throw new IllegalStateException(
		            String.format("Could not find an available %s port in the range [%d, %d] after %d attempts",
		                    name(), minPort, maxPort, searchCounter));
		}
		candidatePort = findRandomPort(minPort, maxPort);
	    } while (!isPortAvailable(candidatePort));

	    return candidatePort;
	}

    }

}
