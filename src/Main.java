import ch.lambdaj.function.matcher.Predicate;
import domain.Record;
import domain.ResultType;
import domain.Result;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import utils.EmailUtil;
import utils.HttpUtil;
import utils.PropertyUtil;
import utils.VelocityTemplateUtil;

import java.io.IOException;
import java.util.*;

import static ch.lambdaj.Lambda.*;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        DateTime lastCheckTime = DateTime.now().minusDays(2);
        while (true) {
            ArrayList<Result> resultList = new ArrayList<>();

            try {
                HashMap pollingUrls = PropertyUtil.getPollingUrls();
                for (Object key : pollingUrls.keySet()) {
                    ResultType resultTypeKey = (ResultType) key;
                    Result result = getLatestRecords((String) pollingUrls.get(resultTypeKey), resultTypeKey,lastCheckTime);
                    if (result != null)
                        resultList.add(result);
                }
                lastCheckTime = getLatestCheckTime(resultList,lastCheckTime);
                sendEmail(resultList);
                System.out.println("Time now " + new DateTime() + " sleeping now");
                Thread.sleep(PropertyUtil.getPollIntervalInMillis());
            } catch (Exception e) {
                System.out.println("An error has occurred - retrying " + e.getMessage());
            }
        }
    }

    private static DateTime getLatestCheckTime(List<Result> resultList, DateTime lastCheckTime) {
        DateTime latestTime=lastCheckTime;
        for(Result result : resultList){
            DateTime latestRecordTime = result.getRecords().get(0).getTime();
            if(latestRecordTime.isAfter(latestTime))
               latestTime= latestRecordTime;
        }
       return latestTime;
    }

    private static void sendEmail(ArrayList<Result> results) {
        if (results.isEmpty())
            return;
        String emailBody = VelocityTemplateUtil.constructEmailBody(results);
        if (!emailBody.equals("")) {
            System.out.println("Sending Email\n");
            EmailUtil.sendEmail("New FB Updates", emailBody);
        }
    }

    private static Result getLatestRecords(String url, ResultType resultType, final DateTime lastCheckTime) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = objectMapper.readValue(HttpUtil.getLatestUpdate(url), Result.class);
        Predicate predicate = new Predicate() {
            @Override
            public boolean apply(Object o) {
                Record record = (Record) o;
                return record.getTime().isAfter(lastCheckTime);
            }
        };

        List<Record> latestRecords = filter(predicate, result.getRecords());
        return latestRecords.isEmpty() ? null : new Result(latestRecords, resultType);
    }


}