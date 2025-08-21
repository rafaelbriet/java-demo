package br.com.rafaelbriet.springapidemo.dtos;

public class DecadeCount {

    private final Integer decade;
    private final Long count;

    public DecadeCount(Integer decade, Long count) {
        this.decade = decade;
        this.count = count;
    }

    public Integer getDecade() {
        return decade;
    }

    public Long getCount() {
        return count;
    }
}
