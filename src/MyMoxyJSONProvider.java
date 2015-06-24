import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import marketClient.MessageBean;


@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_OCTET_STREAM)
public class MyMoxyJSONProvider  implements MessageBodyReader<MessageBean> {

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return mediaType.equals(MediaType.valueOf("application/octet-stream"));
	}

	@Override
	public MessageBean readFrom(Class<MessageBean> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		// TODO Auto-generated method stub
		 try {
		        JAXBContext jaxbContext = JAXBContext.newInstance(MessageBean.class);
		        MessageBean myBean = (MessageBean) jaxbContext.createUnmarshaller()
		            .unmarshal(entityStream);
		        return myBean;
		    } catch (JAXBException jaxbException) {
		        throw new ProcessingException("Error deserializing a MessageBean.",
		            jaxbException);
		    }
	}
}