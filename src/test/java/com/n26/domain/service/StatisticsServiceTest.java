package com.n26.domain.service;

import com.n26.domain.dto.StatisticDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static com.n26.domain.service.TransactionCreator.createTx;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static java.math.BigDecimal.ZERO;

public class StatisticsServiceTest {
    private static final int SIZE_IN_SECONDS = 60;
    private static final long SLOT_SIZE_IN_MILLI_SECS = 1000L;
    private final StatisticsService service = new StatisticsService(SIZE_IN_SECONDS, SLOT_SIZE_IN_MILLI_SECS);

    @BeforeEach
    public void clean() {
        service.deleteAll();
    }

    @Test
    public void testAllAlive() {
        IntStream.range(0, SIZE_IN_SECONDS -2 ).forEach( i -> service.process(createTx(i)));
        service.process(createTx(0, "139"));
        service.process(createTx(0, "1"));

        assertThatStatistic(
                service.getStatistic(),
                Long.valueOf(SIZE_IN_SECONDS),
                new BigDecimal(12),
                new BigDecimal(139),
                BigDecimal.ONE
        );

    }


    @Test
    public void testDeleteAll() {
        IntStream.range(0, 5).forEach( i -> service.process(createTx(i)));
        var statistics = service.getStatistic();
        assertThat(5L,  comparesEqualTo(statistics.getCount()));
        service.deleteAll();
        assertThat(0L,  comparesEqualTo(service.getStatistic().getCount()));
    }

    @Test
    @SneakyThrows
    public void testExpiration() {
        StatisticsService service = new StatisticsService(3, SLOT_SIZE_IN_MILLI_SECS);
        service.process(createTx(1, "40"));
        service.process(createTx(1, "110"));
        service.process(createTx(2, "0"));
        var dto = service.getStatistic();
        assertThatStatistic(dto, 3L, new BigDecimal(50), new BigDecimal(110), ZERO);

        Thread.sleep(5*1000);

        assertThatStatistic(service.getStatistic(), 0L, ZERO, ZERO, ZERO);

    }

    private void assertThatStatistic(StatisticDto dto, long count, BigDecimal avg, BigDecimal max, BigDecimal min){
        assertThat(count,  comparesEqualTo(dto.getCount()));
        assertThat(dto.getMax(), comparesEqualTo(max));
        assertThat(dto.getMin(), comparesEqualTo(min));
        assertThat(dto.getAvg(), comparesEqualTo(avg));
    }
}
