package com.future.fast.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class TestOptional {

    @Test
    public void test1() {
        Optional<Employee> op = Optional.of(new Employee());
        Employee emp = op.get();
        System.out.println(emp);
    }

    @Test
    public void test2() {
        Optional<Employee> op = Optional.ofNullable(null);
        System.out.println(op.get());

        Optional<Employee> op2 = Optional.empty();
        System.out.println(op2.get());
    }

    @Test
    public void test3() {
        Optional<Employee> op = Optional.ofNullable(null);

        if (op.isPresent()) {
            System.out.println(op.get());
        }

        Employee emp = op.orElse(new Employee("张三"));
        System.out.println(emp);

        Employee emp2 = op.orElseGet(() -> new Employee());
        System.out.println(emp2);
    }

    @Test
    public void test4() {
        Optional<Employee> op = Optional.of(new Employee(101, "张三", 18, 9999.99));

        Optional<String> op2 = op.map(Employee::getName);
        System.out.println(op2.get());

        Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(op3.get());
    }

    @Test
    public void test5() {
        Man man = new Man();

        String name = getGodnessName(man);
        System.out.println(name);
    }

    // 需求：获取一个男人心中女神的名字
    public String getGodnessName(Man man) {
        if (man != null) {
            Godness g = man.getGod();

            if (g != null) {
                return g.getName();
            }
        }
        return "苍老师";
    }

    // 运用 Optional的实体类
    @Test
    public void test6() {
        Optional<Godness> godness = Optional.ofNullable(new Godness("林志玲"));

        Optional<NewMan> op = Optional.ofNullable(new NewMan(godness));
        String name = getGodnessName2(op);
        System.out.println(name);
    }

    public String getGodnessName2(Optional<NewMan> man) {
        return man.orElse(new NewMan()).getGodness().orElse(new Godness("苍老师")).getName();
    }
}