package com.google.code.or;

import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class OpenReplicatorTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenReplicatorTest.class);

	private static final String host = "localhost";
//	private static final String host = "rm-2ze3h8614te5zs16uo.mysql.rds.aliyuncs.com";
	private static final int port = 3306;
	private static final String user = "root";
	private static final String password = "root";

//	-en:1#P<Itrt

	/**
	 * 
	 */
	public static void main(String args[]) throws Exception {
		MysqlConnection.setConnection(host, port, user, password);
		final OpenReplicator or = new OpenReplicator();
		or.setUser(user);
		or.setPassword(password);
		or.setHost(host);
		or.setPort(port);
		or.setServerId(MysqlConnection.getServerId());
		or.setBinlogPosition(MysqlConnection.getBinlogMasterStatus().getPosition());
		or.setBinlogFileName(MysqlConnection.getBinlogMasterStatus().getBinlogName());

		or.setBinlogEventListener(new BinlogEventListener() {
		    public void onEvents(BinlogEventV4 event) {
		    	LOGGER.info("{}", event);
		    }
		});
		or.start();

		//
		LOGGER.info("press 'q' to stop");
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(String line = br.readLine(); line != null; line = br.readLine()) {
		    if(line.equals("q")) {
		        or.stop(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		        break;
		    }
		}
	}
}
