
package com.airhacks.firehose.collector.boundary;

import com.airhacks.firehose.collector.control.DataCollector;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author airhacks.com
 */
@Stateless
@Path("metrics")
@Produces(MediaType.TEXT_PLAIN)
public class MetricsResource {

    @Inject
    DataCollector dataCollector;

    @GET
    @Path("{name}")
    public void metrics(@Suspended AsyncResponse response, @PathParam("name") String name) {
        response.setTimeout(1, TimeUnit.SECONDS);
        dataCollector.fetchRemoteMetrics(name).map(m -> m.toMetric()).
                ifPresent(response::resume);
    }

}