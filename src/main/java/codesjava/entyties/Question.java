package codesjava.entyties;

import lombok.Data;

@Data
public class Question implements Node{

    private Integer id;
    private String name;
    private String value;
    private Node yes;
    private Node no;

    public Question() {
    }

    public Question(Integer id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
    public Question(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getType(){return "Question";}

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yes=" + yes +
                ", no=" + no +
                '}';
    }
}
