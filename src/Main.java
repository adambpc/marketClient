import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

public class Main extends JerseyClient {

	public static void main(String[] args) {
		try {
			//consumeEventStream("http://localhost:8080/services/bpc/marketservice/stream_price?sym=eur/usd");
			getPriceSingle("http://localhost:8080/services/bpc/marketservice/get_price?sym=eur/usd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void consumeEventStream(String url) throws Exception {
		Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		WebTarget target = client.target(url);
		//while(true){
			EventInput eventInput = target.request().get(EventInput.class);
			try { 
				while (!eventInput.isClosed()) {
					eventInput.setChunkType(MediaType.WILDCARD_TYPE);
					final InboundEvent inboundEvent = eventInput.read();
					if (inboundEvent != null) {
						String theString = inboundEvent.readData();
						System.out.println(theString);
				    }
					if (eventInput.isClosed()){
						System.out.print("reconnect.");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}
	}
	
	public static void getPriceSingle(String url) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		while(true){
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			HttpEntity responseEntity = response.getEntity();
			if(responseEntity!=null) {
				System.out.println(EntityUtils.toString(responseEntity));
			}
			EntityUtils.consume(responseEntity);
			Thread.sleep(200);
		}
		

	}
}