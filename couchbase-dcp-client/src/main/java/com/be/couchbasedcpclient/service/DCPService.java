package com.be.couchbasedcpclient.service;

import com.couchbase.client.dcp.Client;
import com.couchbase.client.dcp.ControlEventHandler;
import com.couchbase.client.dcp.DataEventHandler;
import com.couchbase.client.dcp.StreamFrom;
import com.couchbase.client.dcp.StreamTo;
import com.couchbase.client.dcp.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.dcp.message.DcpDeletionMessage;
import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.dcp.transport.netty.ChannelFlowController;
import com.couchbase.client.java.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class DCPService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DCPService.class);

    public void start(Boolean isLoop) throws InterruptedException {
        final Client client = Client.builder()
                .hostnames("localhost")
                .bucket("Company")
                .credentials("admin", "123456")
                .build();

        client.controlEventHandler(new ControlEventHandler() {
            @Override
            public void onEvent(ChannelFlowController flowController, ByteBuf event) {
                flowController.ack(event);
                event.release();
            }
        });

        final AtomicLong changes = new AtomicLong(0);
        client.dataEventHandler(new DataEventHandler() {
            @Override
            public void onEvent(ChannelFlowController flowController, ByteBuf event) {
                if (DcpMutationMessage.is(event)) {
                    // Using the Java SDKs JsonObject for simple access to the document
                    JsonObject content = JsonObject.fromJson(
                            DcpMutationMessage.content(event).toString(UTF_8)
                    );

                    /*
                    if ("airport".equals(content.getString("type"))
                            && content.getString("country").toLowerCase().equals("france")) {
                        numAirports.incrementAndGet();
                    }
                    */
                    LOGGER.info("Mutation Content -> {}", content.toString());
                } else if(DcpDeletionMessage.is(event)) {
                    LOGGER.info("Delete Metadata -> {}", DcpDeletionMessage.toString(event));
                }
                changes.incrementAndGet();
                event.release();
            }
        });
/*
        client.systemEventHandler(new SystemEventHandler() {
            @Override
            public void onEvent(CouchbaseEvent event) {
                if (event instanceof StreamEndEvent) {
                    StreamEndEvent streamEnd = (StreamEndEvent) event;
                    if (streamEnd.partition() == 42) {
                        System.out.println("Stream for partition 42 has ended (reason: " + streamEnd.reason() + ")");
                    }
                }
            }
        });
*/
        // Connect the sockets
        client.connect().block();

        // Initialize the state (start now, never stop)
        client.initializeState(StreamFrom.BEGINNING, StreamTo.INFINITY).block();

        // Start streaming on all partitions
        client.startStreaming().block();

        if(!isLoop){
            // Proper Shutdown
            client.disconnect().block();
        }

        while (true) {
            LOGGER.info("Saw " + changes.get() + " changes so far.");
            Thread.sleep(1000);
        }
    }
}
