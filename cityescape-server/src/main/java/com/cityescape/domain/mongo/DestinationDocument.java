package com.cityescape.domain.mongo;

import com.cityescape.enums.HolidayLengthEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Document(collection = "destinations")
public class DestinationDocument {

    @Id
    private String id;

    private String name;

    private String description;

    private String imageUrl;

    private double oneDayStayScore;

    private double weekendStayScore;

    private double longStayScore;

    private Map<String, Double> tagScoreMap = new HashMap<>();

    private List<TravelTimeDistance> travelTimes = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getTagScoreMap() {
        return tagScoreMap;
    }

    public void setTagScoreMap(Map<String, Double> tagScoreMap) {
        this.tagScoreMap = tagScoreMap;
    }

    public double getOneDayStayScore() {
        return oneDayStayScore;
    }

    public void setOneDayStayScore(double oneDayStayScore) {
        this.oneDayStayScore = oneDayStayScore;
    }

    public double getWeekendStayScore() {
        return weekendStayScore;
    }

    public void setWeekendStayScore(double weekendStayScore) {
        this.weekendStayScore = weekendStayScore;
    }

    public double getLongStayScore() {
        return longStayScore;
    }

    public void setLongStayScore(double longStayScore) {
        this.longStayScore = longStayScore;
    }

    public List<TravelTimeDistance> getTravelTimes() {
        return travelTimes;
    }

    public void setTravelTimes(List<TravelTimeDistance> travelTimes) {
        this.travelTimes = travelTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinationDocument that = (DestinationDocument) o;

        if (Double.compare(that.oneDayStayScore, oneDayStayScore) != 0) return false;
        if (Double.compare(that.weekendStayScore, weekendStayScore) != 0) return false;
        if (Double.compare(that.longStayScore, longStayScore) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tagScoreMap != null ? !tagScoreMap.equals(that.tagScoreMap) : that.tagScoreMap != null) return false;
        return travelTimes != null ? travelTimes.equals(that.travelTimes) : that.travelTimes == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(oneDayStayScore);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(weekendStayScore);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longStayScore);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (tagScoreMap != null ? tagScoreMap.hashCode() : 0);
        result = 31 * result + (travelTimes != null ? travelTimes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }

    public double getHolidayLengthScore(HolidayLengthEnum holidayLength) {
        if (holidayLength == null) {
            return 0;
        }

        switch (holidayLength) {
            case ONE_DAY:
                return getOneDayStayScore();
            case WEEKEND:
                return getWeekendStayScore();
            case LONG_STAY:
                return getLongStayScore();
            default:
                return 0;
        }
    }
}
