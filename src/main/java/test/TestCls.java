package test;

public class TestCls {

    boolean data;
    private int age;
    private int name;
    public TestCls() {
    }

    public TestCls(int age, int name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void print() {
//        System.out.println("TestCls print");
    }

    public boolean isData() {
        return data;
    }
}

