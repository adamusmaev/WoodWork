package codesjava.entyties;

import lombok.Data;

@Data
public class Question implements Node{

    private Integer id;
    private String name;
    private Node yes;
    private Node no;
}
