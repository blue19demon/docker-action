package com.future.jdk8.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamTest {
	/**
	 * 1. 创建 Stream
	 */
	@Test
	public void createStram() {
		/**
		 * 1. Collection 提供了两个方法 stream() 与 parallelStream()
		 */
		List<String> list = new ArrayList<>();
		Stream<String> lsStream = list.stream(); // 获取一个顺序流
		Stream<String> lsParallelStream = list.parallelStream(); // 获取一个并行流

		/**
		 * 2. 通过 Arrays 中的 stream() 获取一个数组流
		 */
		Integer[] nums = new Integer[10];
		Stream<Integer> arrayStream = Arrays.stream(nums);

		/**
		 * 3. 通过 Stream 类中静态方法 of()
		 */
		Stream<Integer> intStream = Stream.of(1, 2, 3, 4, 5, 6);

		/**
		 * 4. 创建无限流
		 */
		// 迭代
		Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
		stream3.forEach(System.out::println);

		// 生成
		Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
		stream4.forEach(System.out::println);
	}

	List<Employee> emps = Arrays.asList(new Employee(102, "李四", 79, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE), new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Status.BUSY), new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE), new Employee(105, "田七", 38, 5555.55, Status.BUSY));

	/**
	 * allMatch——检查是否匹配所有元素 anyMatch——检查是否至少匹配一个元素 noneMatch——检查是否没有匹配的元素
	 * findFirst——返回第一个元素 findAny——返回当前流中的任意元素 count——返回流中元素的总个数 max——返回流中最大值
	 * min——返回流中最小值
	 */
	@Test
	public void testMatch() {
		boolean bl = emps.stream().allMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(bl);

		boolean bl1 = emps.stream().anyMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(bl1);

		boolean bl2 = emps.stream().noneMatch((e) -> e.getStatus().equals(Status.BUSY));
		System.out.println(bl2);
	}

	@Test
	public void testFind() {
		Optional<Employee> op = emps.stream().sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
				.findFirst();
		System.out.println(op.get());

		System.out.println("--------------------------------");

		op = emps.parallelStream().filter((e) -> e.getStatus().equals(Status.FREE)).findAny();
		System.out.println(op.get());
	}

	@Test
	public void testMaxOrMin() {
		long count = emps.stream().filter((e) -> e.getStatus().equals(Status.FREE)).count();
		System.out.println(count);

		Optional<Double> op = emps.stream().map(Employee::getSalary).max(Double::compare);
		System.out.println(op.get());

		Optional<Employee> op2 = emps.stream().min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
		System.out.println(op2.get());

		// 注意：流进行了终止操作后，不能再次使用
	}
	
	/**
	 * reduce(T identity, BinaryOperator)
	 * reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void testReduce() {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
						  .reduce(0, (x, y) -> x + y);
		System.out.println(sum);
		
		System.out.println("----------------------------------------");
		
		Optional<Double> op = emps.stream()
			   					  .map(Employee::getSalary)
								  .reduce(Double::sum);
		System.out.println(op.get());
	}
	
	//需求：搜索名字中 “六” 出现的次数
	@Test
	public void test2() {
		Optional<Integer> sum = emps.stream()
									.map(Employee::getName)
									.flatMap(StreamTest::filterCharacter)
									.map((ch) -> {
										if(ch.equals('六'))
											return 1;
										else 
											return 0;
									})
									.reduce(Integer::sum);
		System.out.println(sum.get());
	}
	
	public static Stream<Character> filterCharacter(String str){
		List<Character> list = new ArrayList<>();
		for (Character ch : str.toCharArray()) {
			list.add(ch);
		}
		return list.stream();
	}
	
	
	//collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
		@Test
		public void testCollect(){
			
			System.out.println("\n---------Collectors.toList()---------------------------------------------");
			List<String> list = emps.stream()
									.map(Employee::getName)
									.collect(Collectors.toList());
			list.forEach(System.out::println);
			
			
			System.out.println("\n---------Collectors.toSet()---------------------------------------------");
			Set<String> set = emps.stream()
								  .map(Employee::getName)
								  .collect(Collectors.toSet());
			set.forEach(System.out::println);

			
			System.out.println("\n---------Collectors.toCollection(HashSet::new)---------------------------------------------");
			HashSet<String> hs = emps.stream()
									 .map(Employee::getName)
									 .collect(Collectors.toCollection(HashSet::new));
			hs.forEach(System.out::println);
			
			
			System.out.println("\n---------Collectors.maxBy()---------------------------------------------");
			Optional<Double> max = emps.stream()
									   .map(Employee::getSalary)
									   .collect(Collectors.maxBy(Double::compare));
			System.out.println(max.get());
			
			
			System.out.println("\n---------Collectors.minBy()---------------------------------------------");
			Optional<Employee> op = emps.stream()
										.collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
			System.out.println(op.get());
			
			
			System.out.println("\n---------Collectors.toList()---------------------------------------------");
			Double sum = emps.stream()
					 		 .collect(Collectors.summingDouble(Employee::getSalary));
			System.out.println(sum);
			
			
			System.out.println("\n---------Collectors.averagingDouble()---------------------------------------------");
			Double avg = emps.stream()
					  		 .collect(Collectors.averagingDouble(Employee::getSalary));
			System.out.println(avg);
			
			
			System.out.println("\n---------Collectors.counting()---------------------------------------------");
			Long count = emps.stream()
					 		 .collect(Collectors.counting());
			System.out.println(count);
			
			
			System.out.println("\n---------Collectors.summarizingDouble()---------------------------------------------");
			DoubleSummaryStatistics dss = emps.stream()
									  		  .collect(Collectors.summarizingDouble(Employee::getSalary));
			System.out.println(dss.getMax());
			
			
			System.out.println("\n---------Collectors.groupingBy()---------------------------------------------");
			// 分组
			Map<Status, List<Employee>> map = emps.stream()
												  .collect(Collectors.groupingBy(Employee::getStatus));
			System.out.println(map);
			
			
			System.out.println("\n---------Collectors.groupingBy()---------------------------------------------");
			// 多级分组
			Map<Status, Map<String, List<Employee>>> map2 = emps.stream()
								.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
									if (e.getAge() >= 60)
										return "老年";
									else if (e.getAge() >= 35)
										return "中年";
									else
										return "成年";
								})));
			System.out.println(map2);
			
			
			System.out.println("\n---------Collectors.partitioningBy()---------------------------------------------");
			// 分区
			Map<Boolean, List<Employee>> map3 = emps.stream()
												   .collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
			System.out.println(map3);
			
			
			System.out.println("\n---------Collectors.joining()---------------------------------------------");
			String str = emps.stream()
							 .map(Employee::getName)
							 .collect(Collectors.joining("," , "----", "----"));
			System.out.println(str);
			
			
			System.out.println("\n---------Collectors.reducing()---------------------------------------------");
			Optional<Double> op2 = emps.stream()
									   .map(Employee::getSalary)
									   .collect(Collectors.reducing(Double::sum));
			System.out.println(op2.get());
		}
		/**
		 * 筛选与切片 
		 * 		filter——接收 Lambda ， 从流中排除某些元素。
		 * 		limit——截断流，使其元素不超过给定数量。 
		 * 		skip(n)——跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		 * 		distinct——筛选，通过流所生成元素的 hashCode()和 equals()去除重复元素
		 */
		// 内部迭代：迭代操作 Stream API 内部完成
		@Test
		public void testFilter() {
			// 所有的中间操作不会做任何的处理
			Stream<Employee> stream = emps.stream()
										.filter((e) -> {
													System.out.println("测试中间操作");
													return e.getAge() <= 35;
												});

			// 只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
			stream.forEach(System.out::println);
			
			System.out.println("-------------------------------------------");
			
			emps.stream()
				.filter((e) -> {
							System.out.println("短路！"); // && ||
							return e.getSalary() >= 5000;
						})
				.limit(1)
				.forEach(System.out::println);
			
			System.out.println("-------------------------------------------");
			
			emps.parallelStream()
				.filter((e) -> e.getSalary() >= 5000)
				.skip(1)
				.forEach(System.out::println);
			
			System.out.println("-------------------------------------------");
			
			emps.stream()
				.distinct()
				.forEach(System.out::println);
		}
		
		/**
		 * 	映射
		 * 		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		 * 		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
		 */
		@Test
		public void testMapping(){
			
			Stream<String> strStream = emps.stream().map((e) -> e.getName());
			strStream.forEach(System.out::println);
			
			System.out.println("-------------------------------------------");
			
			List<String> strList = Arrays.asList("aaa", "bbb", "ccc");
			strStream = strList.stream().map(String::toUpperCase);
			strStream.forEach(System.out::println);
			
			System.out.println("-------------------------------------------");
			
			Stream<Stream<Character>> streamOfStream = strList.stream().map(StreamTest::filterCharacter);
			streamOfStream.forEach((sm) -> {sm.forEach(System.out::println);});
			
			System.out.println("---------------------------------------------");
			
			// 将所有流连接成一个流
			Stream<Character> stream = strList.stream().flatMap(StreamTest::filterCharacter);
			stream.forEach(System.out::println);
		}

		
		/**
		 * sorted()——自然排序
		 * sorted(Comparator com)——定制排序
		 */
		@Test
		public void testSorted(){
			emps.stream()
				.map(Employee::getName)
				.sorted()
				.forEach(System.out::println);
			
			System.out.println("------------------------------------");
			
			emps.stream()
				.sorted((x, y) -> {
					if(x.getAge() == y.getAge()){
						return x.getName().compareTo(y.getName());
					}else{
						return Integer.compare(x.getAge(), y.getAge());
					}
				}).forEach(System.out::println);
		}
}

class Employee {

	private int id;
	private String name;
	private int age;
	private double salary;
	private Status status;

	public Employee() {
	}

	public Employee(String name) {
		this.name = name;
	}

	public Employee(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Employee(int id, String name, int age, double salary) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public Employee(int id, String name, int age, double salary, Status status) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String show() {
		return "测试方法引用！";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age != other.age)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", salary=" + salary + ", status=" + status
				+ "]";
	}

}

enum Status {
	FREE, BUSY, VOCATION;
}
