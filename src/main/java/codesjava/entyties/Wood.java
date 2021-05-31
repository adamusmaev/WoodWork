package codesjava.entyties;

import lombok.Data;

@Data
public class Wood implements Node{

    private Integer id;
    private String name;

    public Wood() {
    }

    public Wood(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getType(){return "Wood";}

    @Override
    public String toString() {
        return "Wood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
