LOG4J2的MDC应用
MDC的概念：Manufacturing Data Collection 生产数据实时采集和分析。
有时实际开发过程没有发现的BUG在生产环境才出现，需要到生产环境去分析实时日志来进行BUG跟踪；
LOG4J2提供了MDC功能可以将特定用户的日志单独处理输出到特定的文件中。

配置关键字ThreadContext ，DynamicThresholdFilter ，ThreadContextMapFilter

1.DynamicThresholdFilter 从整体上控制日志默认输出级别，对于特定的值可以调整日志级别
<!--ThreadContext.put("loginId", "User1");
除了User1之外其他的日志都默认是ERROR级别，USER1相关的日志是DEBUG级别 -->

<DynamicThresholdFilter key="loginId"  defaultThreshold="ERROR" 
onMatch="ACCEPT" onMismatch="DENY">
    <KeyValuePair key="User1" value="DEBUG" />
</DynamicThresholdFilter>

2.ThreadContextMapFilter 控制文件的记录内容，符合条件才记录到文件，其他都拒绝写入日志

<!-- 我只记录USER1的日志信息，找生产环境BUG用的。 -->

<File name="testUserLog" fileName="target/testUserLog2" append="true">
    <ThreadContextMapFilter onMatch="ACCEPT" onMismatch="DENY">
        <KeyValuePair key="loginId" value="User1" />
    </ThreadContextMapFilter>
    <PatternLayout pattern="%n%t %-5p %c{2} MDC%X - %m" />
</File>

3.在WEB应用中可以在过滤器filter中加入以下代码：
ThreadContext.put("loginId",Session.getAttribute("userId")); //userId代表的就是登陆会话的工号信息

如果是APP，可以使用代理模式（如果用SPRING,可以使用aop，如果没有SPRING，可以使用CGLIB或者其他）来实现
