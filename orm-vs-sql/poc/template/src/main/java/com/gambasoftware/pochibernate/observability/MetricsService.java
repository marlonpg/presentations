package com.gambasoftware.pochibernate.observability;

import com.gambasoftware.pochibernate.data.entities.Metrics;
import com.gambasoftware.pochibernate.data.repositories.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.groupingBy;

@Service
public class MetricsService {
    private static final Map<String, ConcurrentLinkedQueue<AtomicLong>> mapCounters = new ConcurrentHashMap<>();
    @Autowired
    public ExecutorService executorService;
    @Autowired
    public MetricsRepository metricsRepository;

    public boolean saveMetric(String classMethod, String method, AtomicLong value, String scenario) {
        executorService.execute(() -> {
            if (mapCounters.containsKey(classMethod)) {
                mapCounters.get(classMethod).add(value);
            } else {
                ConcurrentLinkedQueue<AtomicLong> list = new ConcurrentLinkedQueue<>();
                list.add(value);
                mapCounters.put(classMethod, list);
            }
            Metrics metrics = new Metrics();
            metrics.setId(UUID.randomUUID().toString());
            metrics.setClassName(classMethod);
            metrics.setMethod(method);
            metrics.setNanoseconds(value.longValue());
            metrics.setScenario(scenario);
            metricsRepository.save(metrics);
        });

        return true;
    }

    public boolean saveMetric(String classMethod, String method, AtomicLong value) {
        executorService.execute(() -> {
            if (mapCounters.containsKey(classMethod)) {
                mapCounters.get(classMethod).add(value);
            } else {
                ConcurrentLinkedQueue<AtomicLong> list = new ConcurrentLinkedQueue<>();
                list.add(value);
                mapCounters.put(classMethod, list);
            }
            Metrics metrics = new Metrics();
            metrics.setId(UUID.randomUUID().toString());
            metrics.setClassName(classMethod);
            metrics.setMethod(method);
            metrics.setNanoseconds(value.longValue());
            metricsRepository.save(metrics);
        });

        return true;
    }

    public String getMetricsFromDatabase() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Metrics> metricsList = metricsRepository.findAll();

        var result = metricsList.stream()
                .collect(groupingBy(Metrics::getClassName));
        for (String metricsByClassname : result.keySet()) {
            Optional<Metrics> maxValue = result.get(metricsByClassname).stream().reduce((metrics, metrics2) -> {
                if (metrics.getNanoseconds() > metrics2.getNanoseconds()) {
                    return metrics;
                } else {
                    return metrics2;
                }
            });
            Optional<Metrics> minValue = result.get(metricsByClassname).stream().reduce((metrics, metrics2) -> {
                if (metrics.getNanoseconds() < metrics2.getNanoseconds()) {
                    return metrics;
                } else {
                    return metrics2;
                }
            });
            Long meanValue = result.get(metricsByClassname).stream().mapToLong(Metrics::getNanoseconds).sum();

            stringBuilder.append("\n" + metricsByClassname + " meanValue " + Math.abs(meanValue / result.get(metricsByClassname).size()) + " nanoseconds");
            stringBuilder.append("\n" + metricsByClassname + " maxValue " + maxValue.get().getNanoseconds() + " nanoseconds");
            stringBuilder.append("\n" + metricsByClassname + " minValue " + minValue.get().getNanoseconds() + " nanoseconds");
        }
        return stringBuilder.toString();
    }

    public String getMetrics() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String object : mapCounters.keySet()) {
            ConcurrentLinkedQueue<AtomicLong> atomicLongs = mapCounters.get(object);
            Optional<AtomicLong> maxValue = atomicLongs.stream().reduce((atomicLong, atomicLong2) ->
                    new AtomicLong(Math.max(atomicLong.longValue(), atomicLong2.longValue())));
            Optional<AtomicLong> minValue = atomicLongs.stream().reduce((atomicLong, atomicLong2) ->
                    new AtomicLong(Math.min(atomicLong.longValue(), atomicLong2.longValue())));
            Optional<AtomicLong> meanValue = atomicLongs.stream().reduce((atomicLong, atomicLong2) ->
                    new AtomicLong(atomicLong.addAndGet(atomicLong2.longValue())));
            stringBuilder.append("\n" + object + " meanValue " + Math.abs(meanValue.get().intValue() / atomicLongs.size()) + " nanoseconds");
            stringBuilder.append("\n" + object + " maxValue " + maxValue.get() + " nanoseconds");
            stringBuilder.append("\n" + object + " minValue " + minValue.get() + " nanoseconds");
        }
        return stringBuilder.toString();
    }
}
