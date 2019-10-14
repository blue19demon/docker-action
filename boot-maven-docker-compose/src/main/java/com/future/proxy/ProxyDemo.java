package com.future.proxy;
//测试方法
public class ProxyDemo {

    public static void main(String[] args) {
        OrderService orderService = new OrderServiceImpl();
        OrderProxy proxy = new OrderProxy(orderService);
        orderService = (OrderService) proxy.getProxy();
        /**
         * 这里可以很清楚的看出来test1(）走的是代理，而test2（）走的是普通的方法，没有经过代理！
         * 这是因为在Java中test1（）中调用test2（）中的方法，
         * 本质上就相当于把test2（）的方法体放入到test1（）中，也就是内部方法，
         * 同样的不管你嵌套了多少层，只有代理对象proxy 直接调用的那一个方法才是真正的走代理的
         */
        orderService.test1();
        //orderService.test2();
    }
}