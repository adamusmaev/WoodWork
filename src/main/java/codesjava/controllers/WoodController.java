package codesjava.controllers;


import codesjava.entyties.Node;
import codesjava.entyties.Question;
import codesjava.entyties.Wood;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/wood", produces = MediaType.APPLICATION_JSON_VALUE)
public class WoodController {

    List<Node> listNode = new ArrayList<>();

    @GetMapping()
    public String getCurrentNode(Model model) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/current");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        JSONObject jsonObject = new JSONObject(sc.nextLine() + "}");
        if (jsonObject.get("type").equals("Wood")) {
            Wood wood = new Wood();
            wood.setId(jsonObject.getInt("id"));
            wood.setName(jsonObject.getString("name"));
            model.addAttribute("answer", wood);
            return "main";
        } else {
            Question question = new Question();
            question.setId(jsonObject.getInt("id"));
            question.setName(jsonObject.getString("name"));
            model.addAttribute("answer", question);
            return "main";
        }

    }

    @PostMapping("/yes")
    public String yesAnswer(Model model) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8081/yes");
        httpclient.execute(httpPost);
        return getCurrentNode(model);
    }

    @PostMapping("/no")
    public String noAnswer(Model model) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8081/no");
        httpclient.execute(httpPost);
        return getCurrentNode(model);
    }

    @GetMapping("/all")
    public String findAllWoods(Model model) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/all");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        System.out.println(sc.hasNext());
        String strRes = "";
        while (sc.hasNext()) {
            strRes = strRes + sc.nextLine();
        }
        JSONObject jsonObject = new JSONObject(strRes);
        model.addAttribute("json", jsonObject);
        return "allwoods";
    }
}

