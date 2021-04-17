//
//package hu.fitforfun.aspect;
//
//import hu.fitforfun.services.UserService;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//
//@Aspect
//@Component
//public class LoggingAspect {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    UserService userService;
//
//
////    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
////            " || within(@org.springframework.stereotype.Service *)" +
////            " || within(@org.springframework.web.bind.annotation.RestController *)")
//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void springBeanPointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the advices.
//    }
//
//
//
//    @Pointcut("within(hu.fitforfun.converter.*)" +
//            " || within(hu.fitforfun.services..*)" +
//            " || within(hu.fitforfun.controller..*)")
//    public void applicationPackagePointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the advices.
//    }
//
//
//
////    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
//    @AfterThrowing(pointcut = "springBeanPointcut()", throwing = "e")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
//    }
//
//
//    @Around("applicationPackagePointcut() && springBeanPointcut()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.err.println("shit");
//        System.err.println(log.isInfoEnabled());
//        if (log.isInfoEnabled()) {
//            System.err.println("shit2");
//            log.debug(userService.getUserById(1L) +"asd"+ "Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
//                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//            System.err.println(userService.getUserById(1L)+ "Enter: {}.{}() with argument[s] = {}"+ joinPoint.getSignature().getDeclaringTypeName()+
//                    joinPoint.getSignature().getName()+ Arrays.toString(joinPoint.getArgs()));
//        }
//        try {
//            Object result = joinPoint.proceed();
//            if (log.isInfoEnabled()) {
//                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
//                        joinPoint.getSignature().getName(), result);
//                System.err.println("Enter: {}.{}()" + " called by: " + userService.getUserById(1L) + " with argument[s] = {}"+ joinPoint.getSignature().getDeclaringTypeName()+
//                        joinPoint.getSignature().getName()+ Arrays.toString(joinPoint.getArgs()));
//            }
//            return result;
//        } catch (IllegalArgumentException e) {
//            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
//                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
//            throw e;
//        }
//    }
//}
//
