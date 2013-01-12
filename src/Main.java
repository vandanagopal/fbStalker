import ch.lambdaj.function.matcher.Predicate;
import domain.Record;
import domain.Result;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import utils.EmailUtil;
import utils.HttpUtil;
import utils.PropertyUtil;
import utils.VelocityTemplateUtil;

import java.io.*;
import java.util.List;

import static ch.lambdaj.Lambda.filter;

public class Main {

    private static DateTime lastPhotoAlertDate = DateTime.now().minusDays(20), lastFeedAlertDate=DateTime.now().minusDays(20);

    public static void main(String[] args) throws IOException, InterruptedException {

        while (true) {
            List<Record> latestPhotoRecords = getLatestRecords(PropertyUtil.getPhotoUrl(), lastPhotoAlertDate);
            updateLastCheckTime(latestPhotoRecords);

            List<Record> latestFeedRecords = getLatestRecords(PropertyUtil.getFeedUrl(), lastFeedAlertDate);
            if (latestFeedRecords != null && latestFeedRecords.size() > 0)
                lastFeedAlertDate = latestFeedRecords.get(0).getCreatedTime();

            sendEmail(latestPhotoRecords, latestFeedRecords);
            System.out.println("Time now "+new DateTime()+" sleeping now");
            Thread.sleep(PropertyUtil.getPollIntervalInMillis());
        }
    }

    private static void updateLastCheckTime(List<Record> latestPhotoRecords) {
        if (latestPhotoRecords != null && latestPhotoRecords.size() > 0)
            lastPhotoAlertDate = latestPhotoRecords.get(0).getCreatedTime();
    }

    private static void sendEmail(List<Record> latestPhotoRecords, List<Record> latestFeedRecords) {
        if(latestPhotoRecords.size()==0 && latestFeedRecords.size()==0)
            return;
        String emailBody = VelocityTemplateUtil.constructEmailBody(latestPhotoRecords, latestFeedRecords);
        if (!emailBody.equals("")) {
            System.out.println("Sending Email\n");
            EmailUtil.sendEmail("New FB Updates", emailBody);
        }
    }

    private static List<Record> getLatestRecords(String url, final DateTime referenceDate) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = objectMapper.readValue(HttpUtil.getLatestUpdate(url), Result.class);
        Predicate predicate = new Predicate() {
            @Override
            public boolean apply(Object o) {
                Record record = (Record) o;
                return record.getCreatedTime().isAfter(referenceDate);
            }
        };
        List<Record> latestRecords = filter(predicate, result.getRecords());
        return latestRecords;
    }



}

