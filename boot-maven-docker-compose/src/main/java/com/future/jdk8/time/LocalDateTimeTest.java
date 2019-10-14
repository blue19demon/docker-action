package com.future.jdk8.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

import org.junit.Test;

public class LocalDateTimeTest {
	@Test
	public void testLocalDateTime() {

		// 获取当前的日期时间
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println("当前时间: " + ldt);

		LocalDateTime ldt2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
		System.out.println("使用指定数据: " + ldt2);

		LocalDateTime ldt3 = ldt2.plusYears(20);
		System.out.println("加20年: " + ldt3);

		LocalDateTime ldt4 = ldt2.minusMonths(2);
		System.out.println("减2个月: " + ldt4);

		System.out.println("年: " + ldt.getYear());
		System.out.println("月: " + ldt.getMonthValue());
		System.out.println("日: " + ldt.getDayOfMonth());
		System.out.println("时: " + ldt.getHour());
		System.out.println("分: " + ldt.getMinute());
		System.out.println("秒: " + ldt.getSecond());
	}
	
	/**
	 JDK1.7
	 */
	@Test
	public void testDateTime() {

		// 获取当前的日期时间
		Calendar rightNow = Calendar.getInstance();
		System.out.println("当前时间: " + rightNow);

		Calendar otherTime = Calendar.getInstance();
		otherTime.set(2014,2,2,11,11,33);
		
		System.out.println("使用指定数据: " + otherTime.getTime().toLocaleString());

		otherTime.add(Calendar.YEAR, 20);
		System.out.println("加20年: " + otherTime.getTime().toLocaleString());

		otherTime.add(Calendar.MONTH, -2);
		System.out.println("减2个月: " + otherTime.getTime().toLocaleString());

		System.out.println("年: " + otherTime.get(Calendar.YEAR));
		System.out.println("月: " + (otherTime.get(Calendar.MONTH)+1));
		System.out.println("日: " + otherTime.get(Calendar.DAY_OF_MONTH));
		System.out.println("时: " + otherTime.get(Calendar.HOUR_OF_DAY));
		System.out.println("分: " + otherTime.get(Calendar.MINUTE));
		System.out.println("秒: " + otherTime.get(Calendar.SECOND));
	}
	
	
	@Test                                                                  
	public void testDurationAndPeriod() {                                  
	    try {                                                              
	        Instant ins1 = Instant.now();                                  
	        Thread.sleep(1000); // 休息一秒                                    
	        Instant ins2 = Instant.now();                                  
	        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));  

	        System.out.println("----------------------------------");      

	        LocalDate ld1 = LocalDate.now();                               
	        LocalDate ld2 = LocalDate.of(2011, 1, 1);                      

	        Period pe = Period.between(ld2, ld1);                          
	        System.out.println(pe.getYears());                             
	        System.out.println(pe.getMonths());                            
	        System.out.println(pe.getDays()); 
	    } catch (InterruptedException e) {                                 
	    }                                                                  
	}
	
	@Test                                                                       
	public void testTemporalAdjuster() {                                        

	    LocalDateTime ldt = LocalDateTime.now();                                
	    System.out.println("当前日期: " + ldt);                                     

	    LocalDateTime ldt2 = ldt.withDayOfMonth(10);                            
	    System.out.println("设置为这个月的10号: " + ldt2);                              

	    LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
	    System.out.println("下个周日: " + ldt3);                                    

	    // 自定义：下一个工作日                                                           
	    LocalDateTime ldt5 = ldt.with((l) -> {                                  
	        LocalDateTime ldt4 = (LocalDateTime) l;                             

	        DayOfWeek dow = ldt4.getDayOfWeek();                                

	        if (dow.equals(DayOfWeek.FRIDAY)) {                                 
	            return ldt4.plusDays(3);                                        
	        } else if (dow.equals(DayOfWeek.SATURDAY)) {                        
	            return ldt4.plusDays(2);                                        
	        } else {                                                            
	            return ldt4.plusDays(1);                                        
	        }                                                                   
	    });                                                                     

	    System.out.println("自定义：下一个工作日: " + ldt5);                              
	}
	
	
	@Test                                                                             
	public void testDateTimeFormatter() {                                             
	    // DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;                  

	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");

	    LocalDateTime ldt = LocalDateTime.now();                                      
	    String strDate = ldt.format(dtf);                                             
	    System.out.println(strDate);                                                  

	    LocalDateTime newLdt = ldt.parse(strDate, dtf);                               
	    System.out.println(newLdt);                                                   
	}
}
