package test;

import com.isofh.service.model.LessonLaoEntity;
import com.isofh.service.utils.GsonUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TestMain {
    public static void main(String[] args) {
//        Map<String,ClsTest> map = new HashMap<>();
//        ClsTest clsTest = new ClsTest();
//        clsTest.setName("Tuan");
//        map.put("T", clsTest);
//
//        ClsTest clsTest2 = new ClsTest();
//        clsTest2.setName("Nam");
//        clsTest = clsTest2;
//
//        System.out.println(map.get("T").getName());
//        System.out.println(clsTest.getName());
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(LocalDateTime.now().getMinute());
//        System.out.println(LocalDateTime.now().getMinute());


//        List<ClsTest> clsTestList = new ArrayList<>();
//        for (int i = 0; i < 100000; i++) {
//            ClsTest clsTest = new ClsTest();
//            clsTest.setId(i);
//            clsTest.setName("name" + i);
//            clsTestList.add(clsTest);
//
//        }
//
//        System.out.println(clsTestList.size());
//        Long start = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
//        System.out.println("start " + start);
//        Stream<ClsTest> newDatas = clsTestList.stream().filter(clsTest -> clsTest.getId() % 10 == 0);
////        newDatas.filter(clsTest -> clsTest.getId()%100==0).map(clsTest -> {
////             clsTest.setId(clsTest.getId()+1);
////             return clsTest;
////        }).forEach(clsTest -> System.out.println(clsTest.getId()));
////        newDatas.map(clsTest -> )
//
//        Stream.of("d2", "a2", "b1", "b3", "c")
//                .sorted((s1, s2) -> {
//                    System.out.printf("sort: %s; %s\n", s1, s2);
//                    return s1.compareTo(s2);
//                })
//                .forEach(s -> System.out.println("forEach: " + s));
//
//        Long end = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
//        System.out.println("diff " + (end-start));
//        System.out.println(newDatas.length);

//        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(7)));


//        ChildrenCls childrenCls = new ChildrenCls(1,2);
//        System.out.println(childrenCls.getAge());
//        System.out.println(childrenCls.getName());


//        List<Integer> lst = Arrays.asList(10,3,4,2,5,6);
//        lst.sort(Comparator.comparingInt(o -> o));
//        lst.re
//        for (Integer i:lst
//             ) {
//            System.out.println(i + " ");
//        }

//        List<ClsTest>  datas = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ClsTest clsTest = new ClsTest();
//            clsTest.setId(i%5);
//            datas.add(clsTest);
//        }
//        datas.sort(Comparator.comparing(ClsTest::getId));
//        datas.forEach(clsTest -> System.out.println(clsTest.getId()));
//        Collections.reverse(datas);
//        datas.forEach(clsTest -> System.out.println(clsTest.getId()));

//        LocalDateTime date = LocalDateTime.now().plusDays(5);
//        System.out.println(date);

//        System.out.println(RandomUtils.getRandomId());

//        if (CourseTestType.PRE_TEST.getValue().equals(1)) {
//            System.out.println("pre test");
//        } else {
//            System.out.println("post test");
//        }
//String str = null;
//        Long[] data = GsonUtils.toObject(str, Long[].class);
//        System.out.println(data.length);

//        List<Long> datas = new ArrayList<>();
//        Long[] arr= new Long[]{1L,2L,3L};
//        datas.addAll(Arrays.asList(arr));
//        datas.add(4L);
//        System.out.println("done");

//        likedUserIds = Arrays.asList(arrLikedUserIds);
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jonh", 17));
        students.add(new Student("Peter", 19));
        students.add(new Student("Henry", 18));
        //students.sort(Comparator.comparing(obj -> obj.getAge()));
        students.sort((o1, o2) -> Long.compare(o2.getAge(), o1.getAge()));
        students.forEach(e -> System.out.println(e));
    }


    static class ClsTest {
        private Integer id;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
