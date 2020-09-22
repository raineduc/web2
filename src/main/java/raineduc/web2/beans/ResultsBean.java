package raineduc.web2.beans;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

@Stateful
public class ResultsBean implements Serializable {
    private Deque<Result> results = new ArrayDeque<>();
    private Float[] allowedXValues = {-2f, -1.5f, -1f, -0.5f, 0f, 0.5f, 1f, 1.5f, 2f};
    private Float[] allowedRadiusValues = {1f, 1.5f, 2f, 2.5f, 3f};

    public void addResult(String x, String y, String radius) throws ResultInputException {
        validateX(x);
        validateY(y);
        validateRadius(radius);
        float xCoord = getFloat(x);
        float yCoord = getFloat(y);
        float radiusNumber = getFloat(radius);
        Result result = new Result(xCoord, yCoord, radiusNumber, isPointInArea(xCoord, yCoord, radiusNumber));
        results.addFirst(result);
    }

    public Deque<Result> getResults() {
        return results;
    }

    public void setResults(Deque<Result> results) {
        this.results = results;
    }

    public void validateX(String x) throws ResultInputException {
        validateX(getFloat(x));
    }

    public void validateX(Float x) throws ResultInputException {
        if (!Arrays.asList(allowedXValues).contains(x)) {
            throw new ResultInputException("Координата X должна быть одной из чисел: { -2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2 }");
        }
    }

    public void validateY(String y) throws ResultInputException {
        validateY(getFloat(y));
    }

    public void validateY(float y) throws ResultInputException {
        if (y <= -3 || y >= 5) {
            throw new ResultInputException("Координата Y должна лежать в пределах (-3, 5)");
        }
    }

    public void validateRadius(String radius) throws ResultInputException {
        validateRadius(getFloat(radius));
    }

    public void validateRadius(float radius) throws ResultInputException {
        if (!Arrays.asList(allowedRadiusValues).contains(radius)) {
            throw new ResultInputException("Радиус должен быть одним из чисел: { 1, 1.5, 2, 2.5, 3 }");
        }
    }

    public float getFloat(String value) throws ResultInputException {
        try {
            return Float.parseFloat(value);
        } catch (NullPointerException | NumberFormatException e) {
            throw new ResultInputException("Задано не число");
        }
    }

    public boolean isPointInArea(float x, float y, float radius) {
        return isPointInCircle(x, y, radius) || isPointInRectangle(x, y, radius) || isPointInTriangle(x, y, radius);
    }

    protected boolean isPointInRectangle(float x, float y, float radius) {
        return x <= 0 && x >= -radius && y <= 0 && y >= -radius/2;
    }

    protected boolean isPointInCircle(float x, float y, float radius) {
        return x >= 0 && y >= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(radius, 2);
    }

    protected boolean isPointInTriangle(float x, float y, float radius) {
        return x <= 0 && y >= 0 && y <= (0.5*x + radius/2);
    }
}
