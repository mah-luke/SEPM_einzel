package at.ac.tuwien.sepm.assignment.individual.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Order(value = 2)
public class LoggingAspect {

    @Around("execution(* at.ac.tuwien.sepm.assignment.individual..*.*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        final Logger LOGGER = LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
        Object retVal;


        StringBuilder startMessageStringBuilder = new StringBuilder();

        startMessageStringBuilder.append("Start method ");
        startMessageStringBuilder.append(joinPoint.getSignature().getName());
        startMessageStringBuilder.append("(");

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            startMessageStringBuilder.append(arg).append(",");
        }
        if (args.length > 0) {
            startMessageStringBuilder.deleteCharAt(startMessageStringBuilder.length() - 1);
        }
        startMessageStringBuilder.append(")");
        LOGGER.trace(startMessageStringBuilder.toString());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        retVal = joinPoint.proceed();

        stopWatch.stop();

        String endMessage = "Finish method " +
                joinPoint.getSignature().getName() +
                "(..); execution time: " +
                stopWatch.getTotalTimeMillis() +
                " ms;";

        LOGGER.trace(endMessage);

        return retVal;

    }
}
