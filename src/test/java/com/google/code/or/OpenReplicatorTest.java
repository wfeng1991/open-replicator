package com.google.code.or;

import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.QueryEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEvent;
import com.google.code.or.binlog.impl.event.UpdateRowsEventV2;
import com.google.code.or.binlog.impl.event.WriteRowsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class OpenReplicatorTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenReplicatorTest.class);

//	private static final String host = "localhost";
//	private static final int port = 3306;
//	private static final String user = "root";
//	private static final String password = "root";

	private static final String host = "10.1.21.230";
	private static final int port = 3307;
	private static final String user = "test";
	private static final String password = "test";

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

                if(event instanceof UpdateRowsEventV2){
                    UpdateRowsEventV2 updateRowsEvent = (UpdateRowsEventV2)event;
                    LOGGER.info("{},{}",updateRowsEvent.getRows(),updateRowsEvent.toString());
                }else if(event instanceof UpdateRowsEvent){
                    UpdateRowsEvent updateRowsEvent = (UpdateRowsEvent)event;
                    LOGGER.info("{}",updateRowsEvent.getRows());
                }else if(event instanceof WriteRowsEvent){
                    WriteRowsEvent writeRowsEvent = (WriteRowsEvent)event;
                    LOGGER.info("{}",writeRowsEvent.getRows());
                }else if(event instanceof QueryEvent){
                    QueryEvent queryEvent = (QueryEvent)event;
//                    LOGGER.info("{}",queryEvent.getStatusVariables());
                }

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
