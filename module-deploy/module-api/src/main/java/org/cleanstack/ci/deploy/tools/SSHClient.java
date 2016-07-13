package org.cleanstack.ci.deploy.tools;

import java.io.InputStream;

import com.google.common.base.Throwables;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Custom SSH connection manager.
 */
public class SSHClient implements AutoCloseable {

	private JSch channel;
	private Session session;

	public SSHClient(String host, String user, String pass, int port) {
		channel = new JSch();
		try {
			channel.setKnownHosts("");
		} catch (JSchException jschX) {
			// do nothing
		}
		try {
			// OPEN
			session = channel.getSession(user, host, port);
			session.setPassword(pass);
			// UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
			session.setConfig("StrictHostKeyChecking", "no");
			session.setConfig("PreferredAuthentications",
					"publickey,keyboard-interactive,password");
			session.connect(6000);
		} catch (JSchException jschX) {
			jschX.getMessage();
			throw Throwables.propagate(jschX);
		}
	}

	public String exec(String command) {
		StringBuilder outputBuffer = new StringBuilder();
		try {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();

			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}

			channel.disconnect();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		return outputBuffer.toString();
	}

	public void close() {
		session.disconnect();
	}

}
