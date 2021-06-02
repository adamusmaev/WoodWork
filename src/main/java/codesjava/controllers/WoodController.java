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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping(value = "/wood", produces = MediaType.APPLICATION_JSON_VALUE)
public class WoodController {

    List<Node> listNode = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();

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
        String strRes = "";
        while (sc.hasNext()) {
            strRes = strRes + sc.nextLine();
        }
        JSONObject jsonObject = new JSONObject(strRes);
        model.addAttribute("json", jsonObject);
        return "allwoods";
    }

    @PostMapping("/rename")
    public String renameWood(@RequestParam Integer idNode,
                             @RequestParam String nameNode,
                             @RequestParam String typeNode,
                             Model model) throws IOException, URISyntaxException {
        if (typeNode.equals("Question")) {
                restTemplate.postForEntity("http://localhost:8081/updatequestion", new Question(idNode, nameNode), Question.class);
        }
        if (typeNode.equals("Wood")) {
                restTemplate.postForEntity("http://localhost:8081/newwood", new Wood(idNode, nameNode), Wood.class);
           }
        return "redirect:/wood/all";
    }

    @PostMapping("/newbranch")
    public String addNewBranch(@RequestParam Integer idNode,
                               @RequestParam String value) {
        try {
            restTemplate.postForEntity("http://localhost:8081/newquestion", new Question(idNode, "NULL Question", value), Question.class);
        } catch (Exception e) {
        }
        return "redirect:/wood/all";
    }

    @PostMapping("/delete")
    public String deleteNode(@RequestParam Integer idNode,
                             @RequestParam String nameNode,
                             @RequestParam String value,
                             Model model) {
        try {
            restTemplate.postForEntity("http://localhost:8081/delete", new Question(idNode, "NULL Question", value), Question.class);
        } catch (Exception e) {
        }
        return "redirect:/wood/all";
    }

    @PostMapping("/restart")
    public String restart(Model model) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/restart");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        return getCurrentNode(model);
    }

    @PostMapping("/back")
    public String back(Model model) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/back");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        return getCurrentNode(model);
    }
}

//HttpClient httpclient = HttpClients.createDefault();
//HttpPost httppost = new HttpPost("http://localhost:8081/updatequestion");
//List<NameValuePair> params = new ArrayList<>(2);
//params.add(new BasicNameValuePair("id", idNode));
//params.add(new BasicNameValuePair("name", nameNode));
//httppost.setEntity(new UrlEncodedFormEntity(params, "ISO-8859-1"));
            /*org.apache.http.HttpEntity
            httppost.setEntity(h);
            HttpPost
            URI uri = new URIBuilder(httppost.getURI())
                    .add("id", "a")
                    .addParameter("name", "q")
                    .build();
            ((HttpRequestBase) httppost).setURI(uri);
            HttpResponse response = httpclient.execute(httppost);
            System.out.println(EntityUtils.toString(response.getEntity()));*/