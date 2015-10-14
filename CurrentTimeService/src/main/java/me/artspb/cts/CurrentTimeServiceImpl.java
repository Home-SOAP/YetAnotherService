package me.artspb.cts;

import org.apache.log4j.Logger;

import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * User: art
 * Date: 02.02.12
 * Time: 15:59
 */
public class CurrentTimeServiceImpl implements CurrentTimeService {
    private Logger logger = Logger.getLogger(CurrentTimeServiceImpl.class);
    
    public XMLGregorianCalendar getCurrentTime(
            @WebParam(partName = "timeZoneId", name = "timeZoneId", targetNamespace = "http://artspb.me/cts")
            String timeZoneId) {

        logger.debug("Operation getCurrentTime was requested.");

        XMLGregorianCalendar gregorianCalendar;

        try {
            gregorianCalendar = getXmlGregorianCalendar(timeZoneId);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        logger.debug("Successful.");

        return gregorianCalendar;
    }

    private XMLGregorianCalendar getXmlGregorianCalendar(String id) throws DatatypeConfigurationException {
        TimeZone timeZone;

        if (!"".equals(id)) {
            logger.debug("TimeZoneId isn't null: " + id);

            timeZone = TimeZone.getTimeZone(id);
        } else {
            logger.debug("TimeZoneId is null. Will use default value.");

            timeZone = TimeZone.getDefault();
        }

        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}
