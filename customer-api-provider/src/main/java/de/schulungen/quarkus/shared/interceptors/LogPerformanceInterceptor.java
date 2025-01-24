package de.schulungen.quarkus.shared.interceptors;

import io.quarkus.arc.log.LoggerName;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

@Interceptor
@LogPerformance
@Priority(10)
public class LogPerformanceInterceptor {

  @LoggerName("performance")
  Logger logger;

  private Logger.Level findLevel(InvocationContext ic) {
    return AnnotationUtils
      .findAnnotation(ic.getMethod(), LogPerformance.class)
      .map(LogPerformance::value)
      .orElse(Logger.Level.INFO);
  }

  @AroundInvoke
  public Object logPerformance(InvocationContext invocationContext) throws Exception {
    var methodName = invocationContext.getMethod().getName();
    final var level = findLevel(invocationContext);
    var ts = System.currentTimeMillis();
    try {
      return invocationContext.proceed();
    } finally {
      var ts2 = System.currentTimeMillis();
      logger.logf(
        level,
        "Methode %s brauchte %d ms",
        new Object[]{methodName, ts2 - ts}
      );
    }
  }

}
