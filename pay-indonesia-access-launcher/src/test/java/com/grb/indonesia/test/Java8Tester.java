package com.grb.indonesia.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * https://www.tutorialspoint.com/java8/java8_streams.htm
 * @author wangyinbin
 *
 */
public class Java8Tester {

	public static void main(String[] args) {
		Java8Tester tester = new Java8Tester();
		
	      /*//with type declaration
	      MathOperation addition = (int a, int b) -> a + b;
			
	      //with out type declaration
	      MathOperation subtraction = (a, b) -> a - b;
			
	      //with return statement along with curly braces
	      MathOperation multiplication = (int a, int b) -> { return a * b; };
			
	      //without return statement and without curly braces
	      MathOperation division = (int a, int b) -> a / b;
			
	      System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
	      System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
	      System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
	      System.out.println("10 / 5 = " + tester.operate(10, 5, division));
			
	      //with parenthesis
	      GreetingService greetService1 = message -> System.out.println("Hello " + message);
			
	      //without parenthesis
	      GreetingService greetService2 = (message) -> System.out.println("Hello " + message);
			
	      greetService1.sayMessage("Mahesh");
	      greetService2.sayMessage("Suresh");
	      
	      List<String> names2 = new ArrayList<String>();
	      names2.add("Mahesh ");
	      names2.add("Suresh ");
	      names2.add("Ramesh ");
	      names2.add("Naresh ");
	      names2.add("Kalpesh ");
	      tester.sortUsingJava8(names2);
	      System.out.println(names2);
	      
	      List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	      eval(list, n-> n%2 == 0 );*/
	      // Count empty strings
	      List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
	      long count;
	      List<String> filtered;
	      String mergedString;
	      List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
			
	      //get list of square of distinct numbers
	      List<Integer> squaresList = null;
	      List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);
			
			
	      //print ten random numbers
	      Random random = new Random();
			
	      System.out.println("Using Java 8: ");
	      System.out.println("List: " +strings);
			
	      count = strings.stream().filter(string->string.isEmpty()).count();
	      System.out.println("Empty Strings: " + count);
			
	      count = strings.stream().filter(string -> string.length() == 3).count();
	      System.out.println("Strings of length 3: " + count);
			
	      filtered = strings.stream().filter(string ->!string.isEmpty()).collect(Collectors.toList());
	      System.out.println("Filtered List: " + filtered);
			
	      mergedString = strings.stream().filter(string ->!string.isEmpty()).collect(Collectors.joining(", "));
	      System.out.println("Merged String: " + mergedString);
			
	      squaresList = numbers.stream().map( i ->i*i).distinct().collect(Collectors.toList());
	      System.out.println("Squares List: " + squaresList);
	      System.out.println("List: " +integers);
			
	      IntSummaryStatistics stats = integers.stream().mapToInt((x) ->x).summaryStatistics();
			
	      System.out.println("Highest number in List : " + stats.getMax());
	      System.out.println("Lowest number in List : " + stats.getMin());
	      System.out.println("Sum of all numbers : " + stats.getSum());
	      System.out.println("Average of all numbers : " + stats.getAverage());
	      System.out.println("Random Numbers: ");
			
	      random.ints().limit(10).sorted().forEach(System.out::println);
			
	      //parallel processing
	      count = strings.parallelStream().filter(string -> string.isEmpty()).count();
	      System.out.println("Empty Strings: " + count);
	   }
		
	   interface MathOperation {
	      int operation(int a, int b);
	   }
		
	   interface GreetingService {
	      void sayMessage(String message);
	   }
		
	   private int operate(int a, int b, MathOperation mathOperation){
	      return mathOperation.operation(a, b);
	   }
	   private void sortUsingJava8(List<String> names){
		      Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
	   }
	   public static void eval(List<Integer> list, Predicate<Integer> predicate) {
		      for(Integer n: list) {
		         if(predicate.test(n)) {
		            System.out.println(n + " ");
		         }
		      }
	}
	   
			
			
}

