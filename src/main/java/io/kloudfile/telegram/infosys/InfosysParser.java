package io.kloudfile.telegram.infosys;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

final class InfosysParser {

    List<InfosysMessageBean> getRelevantMessages(CloseableHttpResponse response, List<String> keywords) throws IOException {
        final List<InfosysMessageBean> relevantMessages = new ArrayList<>();

        parse(response).forEach(infosysMessageBean -> {
            keywords.forEach(keyword -> {
                if (infosysMessageBean.getTitle().contains(keyword)) {
                    relevantMessages.add(infosysMessageBean);
                }
            });
        });

        return relevantMessages;
    }

    List<InfosysMessageBean> getAllMessages(CloseableHttpResponse response) throws IOException {
        return parse(response);
    }

    private List<InfosysMessageBean> parse(CloseableHttpResponse response) throws IOException {

        Gson gson = new Gson();

        Type token = new TypeToken<List<InfosysMessageBean>>() {
        }.getType();

        return gson.fromJson(EntityUtils.toString(response.getEntity()), token);


    }
}
